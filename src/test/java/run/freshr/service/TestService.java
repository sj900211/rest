package run.freshr.service;

import static run.freshr.common.util.ThreadUtil.threadAccess;
import static run.freshr.common.util.ThreadUtil.threadPublicKey;
import static run.freshr.common.util.ThreadUtil.threadRefresh;
import static run.freshr.domain.blog.enumeration.PostPermission.B111111;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.document.AuditDocument;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Privilege;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.unit.AccountUnitImpl;
import run.freshr.domain.auth.unit.AccessRedisUnitImpl;
import run.freshr.domain.auth.unit.RefreshRedisUnitImpl;
import run.freshr.domain.blog.elasticsearch.PostSearch;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.unit.PostSearchUnit;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.redis.RsaPair;
import run.freshr.domain.common.unit.AttachUnitImpl;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.domain.common.unit.RsaPairUnit;
import run.freshr.domain.mapping.document.PostHashtagMappingForPostDocument;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.unit.AccountHashtagMappingUnit;
import run.freshr.domain.mapping.unit.PostHashtagMappingUnit;
import run.freshr.mapper.EnumMapper;
import run.freshr.mapper.EnumValue;
import run.freshr.util.CryptoUtil;

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
  private final AccessRedisUnitImpl authAccessUnit;
  private final RefreshRedisUnitImpl authRefreshUnit;

  public void createAuth(Long id, Role role) {
    threadAccess.remove();
    threadRefresh.remove();

    // 토큰 발급
    String accessToken = SecurityUtil.createAccessToken(id);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    threadAccess.set(accessToken);
    threadRefresh.set(refreshToken);

    // 토큰 등록
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, role));
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, authAccessUnit.get(accessToken)));
  }

  public void createAccess(String accessToken, Long id, Role role) {
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, role));
  }

  public AccessRedis getAccess(String id) {
    return authAccessUnit.get(id);
  }

  public void createRefresh(String refreshToken, String access) {
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, getAccess(access)));
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

  //   ______ .______     ____    ____ .______   .___________.  ______
  //  /      ||   _  \    \   \  /   / |   _  \  |           | /  __  \
  // |  ,----'|  |_)  |    \   \/   /  |  |_)  | `---|  |----`|  |  |  |
  // |  |     |      /      \_    _/   |   ___/      |  |     |  |  |  |
  // |  `----.|  |\  \----.   |  |     |  |          |  |     |  `--'  |
  //  \______|| _| `._____|   |__|     | _|          |__|      \______/
  private final RsaPairUnit rsaPairUnit;

  public void createRsa() {
    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    threadPublicKey.set(encodePublicKey);

    rsaPairUnit.save(RsaPair.createRedis(encodePublicKey, encodePrivateKey, LocalDateTime.now()));
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
  private final PostSearchUnit postSearchUnit;

  public long createPost(String padding, Account creator) {
    String contents = "# CONTENTS " + padding + "\r\n"
        + "``` java" + "\r\n"
        + "private void handler() {" + "\r\n"
        + "    System.out.println(\"LOG " + padding + "\");" + "\r\n"
        + "}" + "\r\n"
        + "```";

    return postUnit.create(Post
        .createEntity("TITLE " + padding, contents, B111111, creator));
  }

  public Post getPost(long id) {
    return postUnit.get(id);
  }

  public void savePostSearch(String title, Long id, Account creator,
      List<PostHashtagMappingForPostDocument> hashtagList) {
    postSearchUnit.save(PostSearch.createDocument(id, title,
        true, true, true, true, true,
        AuditDocument.createDocument(creator.getId(), creator.getUsername(), creator.getName()),
        hashtagList));
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
