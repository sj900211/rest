package run.freshr.domain.common.dto.response;

import java.net.URL;
import run.freshr.common.extension.ResponseExtension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class Attach response.
 *
 * @author [류성재]
 * @implNote 첨부파일 기본 반환 객체
 * @since 2020 -08-10 @author 류성재
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachResponse extends ResponseExtension {

  /**
   * 파일 유형
   */
  private String contentType;
  /**
   * 파일 원래 이름
   */
  private String filename;
  /**
   * URL
   */
  private URL url;
  /**
   * 파일 크기
   */
  private Long size;
  /**
   * 파일 ALT 값
   */
  private String alt;
  /**
   * 파일 TITLE 값
   */
  private String title;

}
