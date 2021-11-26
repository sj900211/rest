package run.freshr.service;

import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.ok;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.service.AttachUnit;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.response.AttachResponse;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.common.dto.response.IsResponse;
import run.freshr.response.PhysicalAttachResultResponse;
import run.freshr.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class Common service.
 *
 * @author [류성재]
 * @implNote 공통 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonService {

  /**
   * The Attach service
   */
  private final AttachUnit attachUnit;

  /** AWS S3 Settings
   * private final S3Service s3Service;
   */
  /**
   * AWS Cloud Front Settings
   * private final CloudFrontService cloudFrontService;
   */
  /**
   * Physical Attach Settings private final PhysicalAttachService physicalAttachService;
   */
  private final PhysicalAttachService physicalAttachService;

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|

  /**
   * Create attach response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @throws IOException the io exception
   * @author [류성재]
   * @implNote 첨부 파일 생성
   * @since 2021. 3. 16. 오후 2:56:26
   */
  @Transactional
  public ResponseEntity<?> createAttach(AttachCreateRequest dto) throws IOException {
    List<MultipartFile> files = dto.getFiles();
    List<IdResponse> idList = new ArrayList<>();
    String directory = ofNullable(dto.getDirectory()).orElse("");

    for (MultipartFile file : files) {
      String contentType = ofNullable(file.getContentType()).orElse("");
      String filename = ofNullable(file.getOriginalFilename()).orElse("");

      /** AWS S3 Settings & AWS Cloud Front Settings
       * String path = s3Service.upload(directory, file);
       *
       * Long id = attachService.create(
       *     Attach.createEntity(
       *         contentType,
       *         filename,
       *         path,
       *         file.getSize(),
       *         dto.getAlt(),
       *         dto.getTitle()
       *     )
       * );
       */
      /** Physical Attach Settings
       * PhysicalAttachResultResponse uploadResult = physicalAttachService.upload(directory, file);
       *
       * Long id = attachService.create(
       *     Attach.createEntity(
       *         contentType,
       *         filename,
       *         uploadResult.getPhysical(),
       *         file.getSize(),
       *         dto.getAlt(),
       *         dto.getTitle()
       *     )
       * );
       */
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

  /**
   * Exist attach response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 첨부 파일이 있는지 확인
   * @since 2021. 3. 16. 오후 2:56:26
   */
  public ResponseEntity<?> existAttach(Long id) {
    boolean flag = attachUnit.exists(id);

    return ok(IsResponse.builder().is(flag).build());
  }

  /**
   * Gets attach.
   *
   * @param id the id
   * @return the attach
   * @author [류성재]
   * @implNote 첨부 파일 조회
   * @since 2021. 3. 16. 오후 2:56:26
   */
  public ResponseEntity<?> getAttach(Long id) {
    Attach attach = attachUnit.get(id);

    return ok(MapperUtil.map(attach, AttachResponse.class));
  }

  /**
   * Remove attach response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 첨부 파일 삭제
   * @since 2021. 3. 16. 오후 2:56:26
   */
  @Transactional
  public ResponseEntity<?> removeAttach(Long id) {
    Attach entity = attachUnit.get(id);

    entity.remove();

    return RestUtil.ok();
  }

  /**
   * Gets attach download.
   *
   * @param id the id
   * @return the attach download
   * @author [류성재]
   * @implNote 첨부 파일 다운로드
   * @since 2021. 3. 16. 오후 2:56:26
   */
  public ResponseEntity<?> getAttachDownload(Long id) throws IOException {
    if (RestUtil.checkProfile("test")) {
      return RestUtil.ok();
    }

    Attach attach = attachUnit.get(id);

    /** AWS S3 Settings
     * return s3Service.download(attach.getFilename(), attach.getPath());
     */
    /** AWS Cloud Front Settings
     * return s3Service.download(attach.getFilename(), attach.getPath());
     */
    /** Physical Attach Settings
     * return physicalAttachService.download(attach.getFilename(), attach.getPath());
     */
    return physicalAttachService.download(attach.getFilename(), attach.getPath());
  }

  /**
   * Create attach for editor string.
   *
   * @param file the file
   * @return the string
   * @throws IOException the io exception
   * @author [류성재]
   * @implNote 에디터 파일 업로드
   * @since 2021. 3. 16. 오후 2:56:26
   */
  public String createAttachForEditor(MultipartFile file) throws IOException {
    /** AWS S3 Settings & AWS Cloud Front Settings
     * return s3Service.upload("editor", file);
     */
    /** Physical Attach Settings
     * return physicalAttachService.upload("editor", file).getPhysical();
     */
    return physicalAttachService.upload("editor", file).getPhysical();
  }

}
