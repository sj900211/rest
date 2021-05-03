package run.freshr.domain.common.dto.send;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.context.Context;

/**
 * The Class Email send.
 *
 * @author [류성재]
 * @implNote 이메일 발송 객체
 * @since 2021. 3. 16. 오후 2:37:45
 */
@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class EmailSend {

  /**
   * To 이메일
   */
  private String address;
  /**
   * /resources/template/email 하위 양식에 전달할 변수 Context
   */
  private Context context;
  /**
   * 첨부 파일 목록
   */
  private List<EmailAttachSend> attachList;

}
