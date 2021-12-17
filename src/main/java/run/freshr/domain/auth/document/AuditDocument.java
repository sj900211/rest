package run.freshr.domain.auth.document;

import static lombok.AccessLevel.PROTECTED;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AuditDocument {

  private Long id;

  private String username;

  private String name;

  private AuditDocument(Long id, String username, String name) {
    this.id = id;
    this.username = username;
    this.name = name;
  }

  public static AuditDocument createDocument(Long id, String username, String name) {
    return new AuditDocument(id, username, name);
  }

}
