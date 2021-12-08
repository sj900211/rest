package run.freshr.domain.blog.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.ResponseAuditExtension;
import run.freshr.domain.mapping.dto.response.PostHashtagMappingForPostResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponse extends ResponseAuditExtension<Long> {

  private String title;

  private Integer hits;

  private List<PostHashtagMappingForPostResponse> hashtagList;

}
