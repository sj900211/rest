package run.freshr.domain.common.entity;

import static run.freshr.common.util.RestUtil.checkProfile;
import static run.freshr.util.BeanUtil.getBean;
import static lombok.AccessLevel.PROTECTED;

import java.net.MalformedURLException;
import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import run.freshr.annotation.ColumnComment;
import run.freshr.annotation.TableComment;
import run.freshr.common.extension.EntityExtension;
import run.freshr.service.PhysicalAttachService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @apiNote AWS S3 업로드로 기능 구성
 * @since 2020 -08-10 @author 류성재
 */
@Entity
@Table(name = "TB_COM_ATTACH")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@TableComment(value = "공통 관리 > 첨부파일 관리", extend = "EntityExtension")
public class Attach extends EntityExtension {

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

  /**
   * Instantiates a new Attach.
   *
   * @param contentType the content type
   * @param filename    the filename
   * @param path        the path
   * @param size        the size
   * @param alt         the alt
   * @param title       the title
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:38:57
   */
  private Attach(String contentType, String filename, String path, Long size, String alt,
      String title) {
    this.contentType = contentType;
    this.filename = filename;
    this.path = path;
    this.size = size;
    this.alt = alt;
    this.title = title;
  }

  /**
   * Create entity attach.
   *
   * @param contentType the content type
   * @param filename    the filename
   * @param path        the path
   * @param size        the size
   * @param alt         the alt
   * @param title       the title
   * @return the attach
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:38:57
   */
  public static Attach createEntity(String contentType, String filename, String path, Long size,
      String alt, String title) {
    return new Attach(contentType, filename, path, size, alt, title);
  }

  /** AWS S3 Settings
   *  public URL getUrl() throws IOException {
   *   if (checkProfile("test")) {
   *     return new URL("http://localhost:8900/" + this.path);
   *   }
   *
   *    S3Service service = getBean(S3Service.class);
   *
   *    return service.getResourceUrl(this.path, 1);
   *  }
   */
  /** AWS Cloud Front Settings
   * public URL getUrl() throws MalformedURLException {
   *   if (checkProfile("test")) {
   *     return new URL("http://localhost:8900/" + this.path);
   *   }
   *
   *   CloudFrontService service = getBean(CloudFrontService.class);
   *
   *   return service.getSignedUrl(this.path);
   * }
   */
  /**
   * Physical Attach Settings public URL getUrl() throws MalformedURLException { if
   * (checkProfile("test")) { return new URL("http://localhost:8900/" + this.path); }
   * <p>
   * PhysicalAttachService service = getBean(PhysicalAttachService.class);
   * <p>
   * return service.getResourceUrl(this.path); }
   */
  public URL getUrl() throws MalformedURLException {
    if (checkProfile("test")) {
      return new URL("http://localhost:8900/" + this.path);
    }

    PhysicalAttachService service = getBean(PhysicalAttachService.class);

    return service.getResourceUrl(this.path);
  }

}
