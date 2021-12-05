package run.freshr.common.extension;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExtension<ID> {

  protected ID id;

  protected LocalDateTime createDt;

  protected LocalDateTime updateDt;

}
