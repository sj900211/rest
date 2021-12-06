package run.freshr.domain.common.entity;

import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.util.RestUtil.checkProfile;
import static run.freshr.util.BeanUtil.getBean;

import java.net.MalformedURLException;
import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityAuditPhysicalExtension;
import run.freshr.domain.auth.entity.Account;
import run.freshr.service.PhysicalAttachService;

@Slf4j
@Entity
@Table(name = "TB_COM_ATTACH")
@TableComment(value = "공통 관리 > 첨부파일 관리", extend = "EntityAuditPhysicalExtension")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Attach extends EntityAuditPhysicalExtension {

  @ColumnComment("파일 유형")
  private String contentType;

  @Column(nullable = false)
  @ColumnComment("파일 이름")
  private String filename;

  @Column(nullable = false)
  @ColumnComment("파일 경로")
  private String path;

  @Column(nullable = false)
  @ColumnComment("파일 크기")
  private Long size;

  @ColumnComment("대체 문구")
  private String alt;

  @ColumnComment("제목")
  private String title;

  @Transient
  private URL url;

  private Attach(String contentType, String filename, String path, Long size, String alt,
      String title, Account creator) {
    log.info("Attach.Constructor");

    this.contentType = contentType;
    this.filename = filename;
    this.path = path;
    this.size = size;
    this.alt = alt;
    this.title = title;
    this.creator = creator;
  }

  public static Attach createEntity(String contentType, String filename, String path, Long size,
      String alt, String title, Account creator) {
    log.info("Attach.createEntity");

    return new Attach(contentType, filename, path, size, alt, title, creator);
  }

  public URL getUrl() throws MalformedURLException {
    log.info("Attach.getUrl");

    if (checkProfile("test")) {
      return new URL("http://localhost:8900/" + this.path);
    }

    PhysicalAttachService service = getBean(PhysicalAttachService.class);

    return service.getResourceUrl(this.path);
  }

}
