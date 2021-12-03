package run.freshr.service;

import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.ok;

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
import run.freshr.domain.common.dto.response.AttachResponse;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.common.dto.response.IsResponse;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.service.AttachUnitImpl;
import run.freshr.response.PhysicalAttachResultResponse;
import run.freshr.util.MapperUtil;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonServiceImpl implements CommonService {

  private final AttachUnitImpl attachUnit;

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
    List<IdResponse> idList = new ArrayList<>();
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
              dto.getTitle()
          )
      );

      idList.add(IdResponse.builder().id(id).build());
    }

    return RestUtil.ok(idList);
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

    Attach attach = attachUnit.get(id);

    return ok(MapperUtil.map(attach, AttachResponse.class));
  }

  @Override
  @Transactional
  public ResponseEntity<?> removeAttach(Long id) {
    log.info("CommonService.removeAttach");

    Attach entity = attachUnit.get(id);

    entity.remove();

    return RestUtil.ok();
  }

  @Override
  public ResponseEntity<?> getAttachDownload(Long id) throws IOException {
    log.info("CommonService.getAttachDownload");

    if (RestUtil.checkProfile("test")) {
      return RestUtil.ok();
    }

    Attach attach = attachUnit.get(id);

    return physicalAttachService.download(attach.getFilename(), attach.getPath());
  }

}
