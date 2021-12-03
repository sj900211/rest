package run.freshr.domain.common.dto.send;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.context.Context;

@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class EmailSend {

  private String address;

  private Context context;

  private List<EmailAttachSend> attachList;

}
