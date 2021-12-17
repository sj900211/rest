package run.freshr.domain.mapping.document;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;
import run.freshr.domain.common.document.IdDocument;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostHashtagMappingForPostDocument {

  private IdDocument<String> hashtag;

  private PostHashtagMappingForPostDocument(IdDocument<String> hashtag) {
    this.hashtag = hashtag;
  }

  public static PostHashtagMappingForPostDocument createDocument(IdDocument<String> hashtag) {
    return new PostHashtagMappingForPostDocument(hashtag);
  }

}
