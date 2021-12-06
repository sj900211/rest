package run.freshr.service;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.request.IdRequest;

public interface CommonService {

  ResponseEntity<?> createAttach(AttachCreateRequest dto) throws IOException;

  ResponseEntity<?> existAttach(Long id);

  ResponseEntity<?> getAttach(Long id);

  ResponseEntity<?> deleteAttach(Long id);

  ResponseEntity<?> getAttachDownload(Long id) throws IOException;

  ResponseEntity<?> getHashtagList();

  ResponseEntity<?> createHashtag(IdRequest<String> dto);

  ResponseEntity<?> existHashtag(String id);

  ResponseEntity<?> getHashtag(String id);

  ResponseEntity<?> deleteHashtag(String id);

}
