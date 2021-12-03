package run.freshr.domain.common.dto.send;

import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class EmailAttachSend {

  private String display;

  private String physical;

}
