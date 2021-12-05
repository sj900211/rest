package run.freshr.domain.mapping.embedded;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AccountHashtagMappingEmbeddedId implements Serializable {

  private Long accountId;

  private String hashtagId;

  private AccountHashtagMappingEmbeddedId(Long accountId, String hashtagId) {
    this.accountId = accountId;
    this.hashtagId = hashtagId;
  }

  public static AccountHashtagMappingEmbeddedId createId(Long accountId, String hashtagId) {
    return new AccountHashtagMappingEmbeddedId(accountId, hashtagId);
  }

}
