package run.freshr.domain.common.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachCreateRequest {

  private String alt; // IMG ATTRIBUTE > ALT

  private String title; // IMG ATTRIBUTE > TITLE

  @NotEmpty
  private String directory;

  @NotEmpty
  private List<MultipartFile> files;

}
