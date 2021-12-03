package run.freshr.domain.common.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdSortRequest {

  @NotNull
  private Long id;

  @NotNull
  @Size(min = 1)
  private Integer sort;

}
