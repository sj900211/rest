package run.freshr.domain.common.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachSortRequest {

  @NotEmpty
  private IdRequest attach;

  @NotEmpty
  @Size(min = 1)
  private Integer sort;

}
