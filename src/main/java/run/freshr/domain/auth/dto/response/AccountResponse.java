package run.freshr.domain.auth.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.ResponseExtension;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse extends ResponseExtension<Long> {

  private String username;

  private String name;

  private LocalDateTime signDt;

}
