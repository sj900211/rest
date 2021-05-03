package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Account response.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 작성자 정보 반환 객체
 * @since 2021. 3. 16. 오후 12:26:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponse {

  /**
   * 일련 번호
   */
  private Long id;
  /**
   * 고유 아이디
   */
  private String username;
  /**
   * 이름
   */
  private String name;

}
