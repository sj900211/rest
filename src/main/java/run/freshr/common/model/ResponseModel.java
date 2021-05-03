package run.freshr.common.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * The Class Response model.
 *
 * @author [류성재]
 * @implNote
 * @since 2021. 2. 25. 오후 4:57:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ResponseModel {

  /**
   * 이름
   */
  private String name;
  /**
   * 코드
   */
  private String code;
  /**
   * 문구
   */
  private String message;
  /**
   * 메시지 문구 front-end 에서 노출 여부
   */
  private boolean exist = false;
  /**
   * 페이지 객체 반환에 사용
   */
  private Page<?> page;
  /**
   * 목록 객체 반환에 사용
   */
  private Collection<?> list;
  /**
   * 단일 객체 반환에 사용
   */
  private Object data;

}
