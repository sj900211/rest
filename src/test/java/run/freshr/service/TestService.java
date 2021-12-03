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
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.service.AttachUnitImpl;
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
  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|
  private final AuthAccessUnitImpl authAccessUnit;
  private final AuthRefreshUnitImpl authRefreshUnit;
  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final AccountUnitImpl accountUnit;
  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  private final AttachUnitImpl attachUnit;

  public Map<String, List<EnumValue>> getEnumAll() {
    return enumMapper.getAll();
  }

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

  public long createAccount(Privilege privilege, String username, String name) {
    return accountUnit.create(Account.createEntity(
        privilege, username, passwordEncoder.encode("1234"), name));
  }

  public Account getAccount(long id) {
    return accountUnit.get(id);
  }

  public long createAttach(String filename, String path) {
    return attachUnit.create(Attach.createEntity(
        "image/png",
        filename + ".png",
        path + "/" + filename + ".png",
        2048L,
        "alt",
        "title"
    ));
  }

  public Attach getAttach(long id) {
    return attachUnit.get(id);
  }

}
