package run.freshr.domain.blog.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
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
  private String rsa;

  @NotEmpty
  private String title;

  @NotEmpty
  private String contents;

  private Boolean managerGrant;

  private Boolean leaderGrant;

  private Boolean coachGrant;

  private Boolean userGrant;

  private Boolean anonymousGrant;

  private List<PostHashtagMappingForPostCreateRequest> hashtagList;

}
