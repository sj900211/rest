package run.freshr.domain.auth.dto.response;

import java.time.LocalDateTime;
import run.freshr.common.extension.ResponseExtension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class Account response.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 사용자 계정 반환 객체
 * @since 2021. 3. 16. 오후 12:26:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse extends ResponseExtension {

  /**
   * 고유 아이디
   */
  private String username;
  /**
   * 이름
   */
  private String name;
  /**
   * 최근 접속 날짜 시간
   */
  private LocalDateTime signDt;

}
