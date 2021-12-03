package run.freshr.service;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import run.freshr.domain.common.dto.request.AttachCreateRequest;

public interface CommonService {

  ResponseEntity<?> createAttach(AttachCreateRequest dto) throws IOException;

  ResponseEntity<?> existAttach(Long id);

  ResponseEntity<?> getAttach(Long id);

  ResponseEntity<?> removeAttach(Long id);

  ResponseEntity<?> getAttachDownload(Long id) throws IOException;

}
