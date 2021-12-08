package run.freshr.domain.mapping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.common.dto.request.IdRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostHashtagMappingForPostCreateRequest {

  private IdRequest<String> hashtag;

}
