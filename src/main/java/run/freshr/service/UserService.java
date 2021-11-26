package run.freshr.service;

import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.util.CryptoUtil.decodeBase64;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.auth.dto.request.AccountCreateRequest;
import run.freshr.domain.auth.dto.response.AccountResponse;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.service.AccountUnit;
import run.freshr.domain.auth.service.SignUnit;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.user.vo.UserSearch;
import run.freshr.util.MapperUtil;

/**
 * The Class User service.
 *
 * @author [류성재]
 * @implNote 사용자 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  /**
   * The Sign service
   */
  private final SignUnit signUnit;
  /**
   * The Account service
   */
  private final AccountUnit accountUnit;

  /**
   * The Password encoder
   */
  private final PasswordEncoder passwordEncoder;

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|

  /**
   * Create account response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 사용자 계정 생성
   * @since 2021. 3. 16. 오후 3:09:43
   */
  @Transactional
  public ResponseEntity<?> createAccount(AccountCreateRequest dto) {
    String username = decodeBase64(dto.getUsername());

    if (signUnit.exists(username)) {
      return error(RestUtil.getExceptions().getDuplicate(), "already username: " + username);
    }

    Long id = accountUnit.create(Account.createEntity(
        username,
        passwordEncoder.encode(decodeBase64(dto.getPassword())),
        decodeBase64(dto.getName())
    ));

    return ok(IdResponse.builder().id(id).build());
  }

  /**
   * Gets account page.
   *
   * @param search the search
   * @return the account page
   * @author [류성재]
   * @implNote 사용자 계정 조회 - Page
   * @since 2021. 3. 16. 오후 3:09:43
   */
  public ResponseEntity<?> getAccountPage(UserSearch search) {
    Page<AccountResponse> page = accountUnit
        .getPage(search)
        .map(account -> MapperUtil.map(account, AccountResponse.class));

    return RestUtil.ok(page);
  }

  /**
   * Gets account.
   *
   * @param id the id
   * @return the account
   * @author [류성재]
   * @implNote 사용자 계정 조회
   * @since 2021. 3. 16. 오후 3:09:43
   */
  public ResponseEntity<?> getAccount(Long id) {
    AccountResponse data = MapperUtil.map(accountUnit.get(id), AccountResponse.class);

    return ok(data);
  }

  /**
   * Remove account response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 사용자 계정 삭제
   * @since 2021. 3. 16. 오후 3:09:43
   */
  @Transactional
  public ResponseEntity<?> removeAccount(Long id) {
    Account entity = accountUnit.get(id);

    entity.removeEntity();

    return RestUtil.ok();
  }

}
