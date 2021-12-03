package run.freshr.domain.common.dto.response;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extension.ResponseExtension;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachResponse extends ResponseExtension {

  private String contentType;

  private String filename;

  private URL url;

  private Long size;

  private String alt;

  private String title;

}
