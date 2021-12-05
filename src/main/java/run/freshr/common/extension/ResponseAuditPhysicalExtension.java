package run.freshr.common.extension;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.auth.dto.response.AuditResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuditPhysicalExtension<ID extends Serializable> {

  protected ID id;

  protected LocalDateTime createDt;

  protected LocalDateTime updateDt;

  protected AuditResponse creator;

}
