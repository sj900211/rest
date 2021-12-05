package run.freshr.service;

import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.buildId;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.getExceptions;
import static run.freshr.common.util.RestUtil.getSignedAccount;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.util.MapperUtil.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.request.IdRequest;
import run.freshr.domain.common.dto.response.AttachResponse;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.common.dto.response.IsResponse;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.unit.AttachUnit;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.response.PhysicalAttachResultResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonServiceImpl implements CommonService {

  private final AttachUnit attachUnit;
  private final HashtagUnit hashtagUnit;

  private final PhysicalAttachService physicalAttachService;

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|

  @Override
  @Transactional
  public ResponseEntity<?> createAttach(AttachCreateRequest dto) throws IOException {
    log.info("CommonService.createAttach");

    List<MultipartFile> files = dto.getFiles();
    List<IdResponse<?>> idList = new ArrayList<>();
    String directory = ofNullable(dto.getDirectory()).orElse("");

    for (MultipartFile file : files) {
      String contentType = ofNullable(file.getContentType()).orElse("");
      String filename = ofNullable(file.getOriginalFilename()).orElse("");

      PhysicalAttachResultResponse uploadResult = physicalAttachService.upload(directory, file);

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
  public ResponseEntity<?> getAttachDownload(Long id) throws IOException {
    log.info("CommonService.getAttachDownload");

    if (RestUtil.checkProfile("test")) {
      return ok();
    }

    Attach entity = attachUnit.get(id);

    return physicalAttachService.download(entity.getFilename(), entity.getPath());
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|

  @Override
  public ResponseEntity<?> createHashtag(IdRequest<String> dto) {
    hashtagUnit.create(Hashtag.createEntity(dto.getId()));

    return ok();
  }

  @Override
  public ResponseEntity<?> existHashtag(String id) {
    return ok(IsResponse.builder().is(hashtagUnit.exists(id)).build());
  }

  @Override
  public ResponseEntity<?> getHashtag(String id) {
    return ok(buildId(hashtagUnit.get(id).getId()));
  }

  @Override
  public ResponseEntity<?> deleteHashtag(String id) {
    hashtagUnit.delete(id);

    return ok();
  }

}
