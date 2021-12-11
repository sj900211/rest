package run.freshr.service;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.http.ResponseEntity;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.request.IdRequest;

public interface CommonService {

  ResponseEntity<?> getPublicKey();

  ResponseEntity<?> createAttach(AttachCreateRequest dto)
      throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException;

  ResponseEntity<?> existAttach(Long id);

  ResponseEntity<?> getAttach(Long id);

  ResponseEntity<?> deleteAttach(Long id);

  ResponseEntity<?> getAttachDownload(Long id)
      throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException;

  ResponseEntity<?> getHashtagList();

  ResponseEntity<?> createHashtag(IdRequest<String> dto);

  ResponseEntity<?> existHashtag(String id);

  ResponseEntity<?> getHashtag(String id);

  ResponseEntity<?> deleteHashtag(String id);

}
