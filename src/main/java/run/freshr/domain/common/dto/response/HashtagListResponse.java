package run.freshr.domain.common.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagListResponse {

  private String id;

  private Long count;

  @QueryProjection
  public HashtagListResponse(String id, Long count) {
    this.id = id;
    this.count = count;
  }

}
