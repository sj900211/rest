package run.freshr.domain.user.validator;

import static run.freshr.util.CryptoUtil.decodeBase64;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.auth.dto.request.AccountCreateRequest;

/**
 * The Class User validator.
 *
 * @author [류성재]
 * @implNote 사용자 관리 Validator
 * @since 2021. 3. 16. 오후 2:52:52
 */
@Component
@RequiredArgsConstructor
public class UserValidator {

  /**
   * The Ban list
   */
  private final List<String> banList = Arrays.asList(
      "adm", "admin", "administrator",
      "super", "superAdmin", "superAdministrator",
      "supervisor", "supervisorAdmin", "supervisorAdministrator",
      "manager", "master", "boss"
  );

  /**
   * Create account validate.
   *
   * @param dto           the dto
   * @param bindingResult the binding result
   * @author [류성재]
   * @implNote 사용자 관리 > 계정 등록 validator
   * @since 2021. 3. 16. 오후 2:52:52
   */
  public void createAccount(AccountCreateRequest dto, BindingResult bindingResult) {
    String username = decodeBase64(dto.getUsername());

    if (username.contains("@")) {
      username = username.substring(0, username.indexOf("@"));
    }

    String finalUsername = username;

    boolean banFlag = banList.stream()
        .anyMatch(banUsername -> banUsername.equalsIgnoreCase(finalUsername));

    if (banFlag) {
      RestUtil.rejectWrong("username", bindingResult);
    }
  }

}
