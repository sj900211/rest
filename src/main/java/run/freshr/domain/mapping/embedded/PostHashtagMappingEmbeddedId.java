package run.freshr.domain.mapping.embedded;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostHashtagMappingEmbeddedId implements Serializable {

  private Long postId;

  private String hashtagId;

  private PostHashtagMappingEmbeddedId(Long postId, String hashtagId) {
    this.postId = postId;
    this.hashtagId = hashtagId;
  }

  public static PostHashtagMappingEmbeddedId createId(Long postId, String hashtagId) {
    return new PostHashtagMappingEmbeddedId(postId, hashtagId);
  }

}
