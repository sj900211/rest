package run.freshr.common.extension;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.auth.dto.response.AuditResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuditLogicalExtension {

  protected Long id;

  protected LocalDateTime createDt;

  protected LocalDateTime updateDt;

  protected AuditResponse creator;

  protected AuditResponse updater;

}
