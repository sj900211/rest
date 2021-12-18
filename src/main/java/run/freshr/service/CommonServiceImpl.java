package run.freshr.service;

import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.buildId;
import static run.freshr.common.util.RestUtil.checkProfile;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.getExceptions;
import static run.freshr.common.util.RestUtil.getSignedAccount;
import static run.freshr.common.util.RestUtil.getSignedRole;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.util.MapperUtil.map;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.request.IdRequest;
import run.freshr.domain.common.dto.response.AttachResponse;
import run.freshr.domain.common.dto.response.HashtagListResponse;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.common.dto.response.IsResponse;
import run.freshr.domain.common.dto.response.KeyResponse;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.redis.RsaPair;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.domain.common.unit.RsaPairUnit;
import run.freshr.domain.mapping.unit.AccountHashtagMappingUnit;
import run.freshr.domain.mapping.unit.PostHashtagMappingUnit;
import run.freshr.response.PutResultResponse;
import run.freshr.util.CryptoUtil;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonServiceImpl implements CommonService {

  private final AttachUnit attachUnit;
  private final HashtagUnit hashtagUnit;
  private final AccountHashtagMappingUnit accountHashtagMappingUnit;
  private final PostHashtagMappingUnit postHashtagMappingUnit;

  private final RsaPairUnit rsaPairUnit;

  private final MinioService minioService;

  //   ______ .______     ____    ____ .______   .___________.  ______
  //  /      ||   _  \    \   \  /   / |   _  \  |           | /  __  \
  // |  ,----'|  |_)  |    \   \/   /  |  |_)  | `---|  |----`|  |  |  |
  // |  |     |      /      \_    _/   |   ___/      |  |     |  |  |  |
  // |  `----.|  |\  \----.   |  |     |  |          |  |     |  `--'  |
  //  \______|| _| `._____|   |__|     | _|          |__|      \______/

  @Override
  @Transactional
  public ResponseEntity<?> getPublicKey() {
    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    rsaPairUnit.save(RsaPair.createRedis(encodePublicKey, encodePrivateKey, LocalDateTime.now()));

    return ok(KeyResponse.<String>builder().key(encodePublicKey).build());
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|

  @Override
  @Transactional
  public ResponseEntity<?> createAttach(AttachCreateRequest dto)
      throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException {
    log.info("CommonService.createAttach");

    List<MultipartFile> files = dto.getFiles();
    List<IdResponse<?>> idList = new ArrayList<>();
    String directory = ofNullable(dto.getDirectory()).orElse("");

    for (MultipartFile file : files) {
      String contentType = ofNullable(file.getContentType()).orElse("");
      String filename = ofNullable(file.getOriginalFilename()).orElse("");

      PutResultResponse uploadResult = minioService.upload(directory, file);

      Long id = attachUnit.create(
          Attach.createEntity(
              contentType,
              filename,
              uploadResult.getPhysical(),
              file.getSize(),
              dto.getAlt(),
              dto.getTitle(),
              getSignedAccount()
          )
      );

      idList.add(buildId(id));
    }

    return ok(idList);
  }

  @Override
  public ResponseEntity<?> existAttach(Long id) {
    log.info("CommonService.existAttach");

    boolean flag = attachUnit.exists(id);

    return ok(IsResponse.builder().is(flag).build());
  }

  @Override
  public ResponseEntity<?> getAttach(Long id) {
    log.info("CommonService.getAttach");

    Attach entity = attachUnit.get(id);

    return ok(map(entity, AttachResponse.class));
  }

  @Override
  @Transactional
  public ResponseEntity<?> deleteAttach(Long id) {
    log.info("CommonService.removeAttach");

    Attach entity = attachUnit.get(id);

    if (!entity.checkOwner(getSignedAccount())) {
      return error(getExceptions().getAccessDenied());
    }

    attachUnit.delete(id);

    return ok();
  }

  @Override
  public ResponseEntity<?> getAttachDownload(Long id)
      throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException {
    log.info("CommonService.getAttachDownload");

    if (checkProfile("test")) {
      return ok();
    }

    Attach entity = attachUnit.get(id);

    return minioService.download(entity.getFilename(), entity.getPath());
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|

  @Override
  public ResponseEntity<?> getHashtagAll() {
    log.info("CommonService.getHashtagAll");

    List<Hashtag> list = hashtagUnit.getList();

    return ok(map(list, HashtagListResponse.class));
  }

  @Override
  public ResponseEntity<?> getHashtagList() {
    log.info("CommonService.getHashtagList");

    return ok(hashtagUnit.getList(getSignedRole()));
  }

  @Override
  @Transactional
  public ResponseEntity<?> createHashtag(IdRequest<String> dto) {
    log.info("CommonService.createHashtag");

    String id = dto.getId();

    if (!hashtagUnit.exists(id)) {
      hashtagUnit.create(Hashtag.createEntity(id));
    }

    return ok();
  }

  @Override
  public ResponseEntity<?> existHashtag(String id) {
    log.info("CommonService.existHashtag");

    return ok(IsResponse.builder().is(hashtagUnit.exists(id)).build());
  }

  @Override
  public ResponseEntity<?> getHashtag(String id) {
    log.info("CommonService.getHashtag");

    return ok(buildId(hashtagUnit.get(id).getId()));
  }

  @Override
  @Transactional
  public ResponseEntity<?> deleteHashtag(String id) {
    log.info("CommonService.deleteHashtag");

    if (hashtagUnit.exists(id)) {
      Hashtag entity = hashtagUnit.get(id);

      accountHashtagMappingUnit.delete(entity);
      postHashtagMappingUnit.delete(entity);

      hashtagUnit.delete(id);
    }

    return ok();
  }

}
