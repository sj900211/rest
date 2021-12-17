package run.freshr.domain.mapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.common.dto.response.IdStringResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostHashtagMappingForPostResponse {

  private IdStringResponse hashtag;

}
