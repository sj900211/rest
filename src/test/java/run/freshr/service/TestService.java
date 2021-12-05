package run.freshr.service;

import static run.freshr.common.util.SignUtil.signedAccess;
import static run.freshr.common.util.SignUtil.signedRefresh;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.redis.AuthRefresh;
import run.freshr.domain.auth.unit.AccountUnitImpl;
import run.freshr.domain.auth.unit.AuthAccessUnitImpl;
import run.freshr.domain.auth.unit.AuthRefreshUnitImpl;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.unit.AttachUnitImpl;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.unit.AccountHashtagMappingUnit;
import run.freshr.domain.mapping.unit.PostHashtagMappingUnit;
import run.freshr.mapper.EnumMapper;
import run.freshr.mapper.EnumValue;

@Service
@RequiredArgsConstructor
public class TestService {

  private final PasswordEncoder passwordEncoder;

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|
  private final EnumMapper enumMapper;

  public Map<String, List<EnumValue>> getEnumAll() {
    return enumMapper.getAll();
  }

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|
  private final AuthAccessUnitImpl authAccessUnit;
  private final AuthRefreshUnitImpl authRefreshUnit;

  public void createAuth(Long id, Role role) {
    signedAccess.remove();
    signedRefresh.remove();

    // 토큰 발급
    String accessToken = SecurityUtil.createAccessToken(id);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    signedAccess.set(accessToken);
    signedRefresh.set(refreshToken);

    // 토큰 등록
    authAccessUnit.save(AuthAccess.createRedis(accessToken, id, role));
    authRefreshUnit.save(AuthRefresh.createRedis(refreshToken, authAccessUnit.get(accessToken)));
  }

  public void createAccess(String accessToken, Long id, Role role) {
    authAccessUnit.save(AuthAccess.createRedis(accessToken, id, role));
  }

  public AuthAccess getAccess(String id) {
    return authAccessUnit.get(id);
  }

  public void createRefresh(String refreshToken, String access) {
    authRefreshUnit.save(AuthRefresh.createRedis(refreshToken, getAccess(access)));
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final AccountUnitImpl accountUnit;

  public long createAccount(Privilege privilege, String username, String name) {
    return accountUnit.create(Account.createEntity(
        privilege, username, passwordEncoder.encode("1234"), name));
  }

  public Account getAccount(long id) {
    return accountUnit.get(id);
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  private final AttachUnitImpl attachUnit;

  public long createAttach(String filename, String path, Account creator) {
    return attachUnit.create(Attach.createEntity(
        "image/png",
        filename + ".png",
        path + "/" + filename + ".png",
        2048L,
        "alt",
        "title",
        creator
    ));
  }

  public Attach getAttach(long id) {
    return attachUnit.get(id);
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  private final HashtagUnit hashtagUnit;

  public String createHashtag(String hashtag) {
    return hashtagUnit.create(Hashtag.createEntity(hashtag));
  }

  public Hashtag getHashtag(String hashtag) {
    return hashtagUnit.get(hashtag);
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  private final PostUnit postUnit;

  public long createPost(String padding, Account creator) {
    String contents = "# CONTENTS " + padding
        + "``` java"
        + "private void handler() {"
        + "    System.out.println(\"LOG " + padding + "\");"
        + "}"
        + "```";

    return postUnit.create(Post.createEntity("TITLE " + padding, contents, creator));
  }

  public Post getPost(long id) {
    return postUnit.get(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  // .___  ___.      ___      .______   .______    __  .__   __.   _______
  // |   \/   |     /   \     |   _  \  |   _  \  |  | |  \ |  |  /  _____|
  // |  \  /  |    /  ^  \    |  |_)  | |  |_)  | |  | |   \|  | |  |  __
  // |  |\/|  |   /  /_\  \   |   ___/  |   ___/  |  | |  . `  | |  | |_ |
  // |  |  |  |  /  _____  \  |  |      |  |      |  | |  |\   | |  |__| |
  // |__|  |__| /__/     \__\ | _|      | _|      |__| |__| \__|  \______|
  private final AccountHashtagMappingUnit accountHashtagMappingUnit;

  public void createAccountHashtagMapping(Account account, Hashtag hashtag) {
    accountHashtagMappingUnit.create(AccountHashtagMapping.createEntity(account, hashtag));
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  // .___  ___.      ___      .______   .______    __  .__   __.   _______
  // |   \/   |     /   \     |   _  \  |   _  \  |  | |  \ |  |  /  _____|
  // |  \  /  |    /  ^  \    |  |_)  | |  |_)  | |  | |   \|  | |  |  __
  // |  |\/|  |   /  /_\  \   |   ___/  |   ___/  |  | |  . `  | |  | |_ |
  // |  |  |  |  /  _____  \  |  |      |  |      |  | |  |\   | |  |__| |
  // |__|  |__| /__/     \__\ | _|      | _|      |__| |__| \__|  \______|
  private final PostHashtagMappingUnit postHashtagMappingUnit;

  public void createPostHashtagMapping(Post post, Hashtag hashtag) {
    postHashtagMappingUnit.create(PostHashtagMapping.createEntity(post, hashtag));
  }

}
