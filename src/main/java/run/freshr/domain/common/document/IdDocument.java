package run.freshr.domain.common.document;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class IdDocument<ID> {

  private ID id;

  private IdDocument(ID id) {
    this.id = id;
  }

  public static <ID> IdDocument<ID> createDocument(ID id) {
    return new IdDocument<>(id);
  }

}
