package run.freshr.domain.setting.validator;

import run.freshr.common.util.RestUtil;
import run.freshr.domain.auth.enumeration.SignPrivilege;
import run.freshr.exception.UnAuthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The Class Setting validator.
 *
 * @author [류성재]
 * @implNote 설정 관리 Validator
 * @since 2021. 3. 16. 오후 2:52:09
 */
@Component
@RequiredArgsConstructor
public class SettingValidator {

  /**
   * Manager super validate.
   *
   * @author [류성재]
   * @implNote 수퍼 관리자 권한 체크
   * @since 2021. 3. 16. 오후 2:52:09
   */
  public void managerSuperValidate() {
    if (RestUtil.getSignedManager().getPrivilege().equals(SignPrivilege.MANAGER)) {
      throw new UnAuthenticatedException();
    }
  }

}
