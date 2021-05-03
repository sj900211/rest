package run.freshr.domain.common.validator;

import static java.util.Objects.isNull;

import run.freshr.exception.ParameterException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class Common validator.
 *
 * @author [류성재]
 * @implNote 공통 관리 Validator
 * @since 2021. 3. 16. 오후 2:43:28
 */
@Component
public class CommonValidator {

  /**
   * Create attach for editor ck.
   *
   * @param file the file
   * @author [류성재]
   * @implNote 파일이 있는지 체크
   * @since 2021. 3. 16. 오후 2:43:28
   */
  public void createAttachForEditorCK(MultipartFile file) {
    if (isNull(file)) {
      throw new ParameterException();
    }
  }

}
