package run.freshr.domain.blog.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.mapping.dto.request.PostHashtagMappingForPostCreateRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

  @NotEmpty
  @Size(max = 100)
  private String title;

  @NotEmpty
  private String contents;

  private List<PostHashtagMappingForPostCreateRequest> hashtagList;

}
