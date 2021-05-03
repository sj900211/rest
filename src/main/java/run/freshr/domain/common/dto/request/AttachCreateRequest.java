package run.freshr.domain.common.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class Attach create request.
 *
 * @author [류성재]
 * @implNote 첨부파일 등록 요청 객체
 * @since 2020 -08-10 @author 류성재
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachCreateRequest {

  /**
   * 파일 ALT 값
   */
  String alt; // IMG ATTRIBUTE > ALT
  /**
   * 파일 TITLE 값
   */
  String title; // IMG ATTRIBUTE > TITLE
  /**
   * 저장할 디렉토리
   */
  @NotEmpty
  String directory;
  /**
   * 업로드 파일 목록
   */
  @NotEmpty
  List<MultipartFile> files;

}
