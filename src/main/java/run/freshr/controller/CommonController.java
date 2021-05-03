package run.freshr.controller;

import static run.freshr.common.util.RestUtil.ok;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import run.freshr.common.config.URIConfig;
import run.freshr.domain.common.validator.CommonValidator;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.mapper.EnumMapper;
import run.freshr.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class Common controller.
 *
 * @author [류성재]
 * @implNote 공통 관리
 * @since 2021. 3. 16. 오후 12:14:55
 */
@RestController
@RequiredArgsConstructor
public class CommonController {

  /**
   * The Enum mapper
   */
  private final EnumMapper enumMapper;
  /**
   * The Service
   */
  private final CommonService service;

  /**
   * The Validator
   */
  private final CommonValidator validator;

  /**
   * Gets heart beat.
   *
   * @return the heart beat
   * @author [류성재]
   * @implNote 서비스가 정상적으로 실행되고 있는지 확인하기 위한 URI
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @GetMapping(URIConfig.uriCommonHeartbeat)
  public String getHeartBeat() {
    return "<!doctype html>"
        + "<html>"
        + "  <head>"
        + "    <meta charset=\"utf-8\">"
        + "    <title>Heart Beat</title>"
        + "  </head>"
        + "  <body>"
        + "    <div id=\"box\">"
        + "      <span class=\"hex-icon-heart\">"
        + "        <svg>"
        + "          <path d=\"M19,1 Q21,0,23,1 L39,10 Q41.5,11,42,14 L42,36 Q41.5,39,39,40 L23,49 Q21,50,19,49 L3,40 Q0.5,39,0,36 L0,14 Q0.5,11,3,10 L19,1\" />"
        + "          <path d=\"M11,17 Q16,14,21,20 Q26,14,31,17 Q35,22,31,27 L21,36 L11,27 Q7,22,11,17\" />"
        + "        </svg>"
        + "      </span>"
        + "    </div>"
        + "    <style>"
        + "      html, body { height: 100%; }"
        + "      body { margin: 0; padding: 0; background: #1a1c24; }"
        + "      #box { position: relative; top: 50%; text-align: center; margin-top: -25px; }"
        + "      #box [class^=\"hex-icon\"] { vertical-align: top; }"
        + "      [class^=\"hex-icon\"] { width: 42px; height: 50px; margin: 0 10px; display: inline-block; transition: all 0.2s cubic-bezier(0.215, 0.610, 0.355, 1.000); -webkit-transition: all 0.2s cubic-bezier(0.215, 0.610, 0.355, 1.000); }"
        + "      [class^=\"hex-icon\"]:hover { transform: scale3d(1.2, 1.2, 1); -webkit-transform: scale3d(1.2, 1.2, 1); transition: all 0.35s cubic-bezier(0.000, 1.270, 0.460, 1.650); -webkit-transition: all 0.35s cubic-bezier(0.000, 1.270, 0.460, 1.650); }"
        + "      [class^=\"hex-icon\"] svg { width: 100%; height: 100%; display: block; }"
        + "      .hex-icon-sun path:first-of-type { fill: #fff; }"
        + "      .hex-icon-sun circle { stroke: #757579; stroke-width: 2px; fill: none; }"
        + "      .hex-icon-sun circle:last-of-type { stroke-width: 2px; stroke-dasharray: 2, 7.4; }"
        + "      .hex-icon-wave path:first-of-type { fill: #219cb5; }"
        + "      .hex-icon-wave circle { stroke: #fff; stroke-width: 2px; fill: none; }"
        + "      .hex-icon-wave mask circle { fill: #fff; stroke: none; }"
        + "      .hex-icon-wave path:last-of-type { fill: #fff; }"
        + "      .hex-icon-heart path:first-of-type { fill: #7b5af7; }"
        + "      .hex-icon-heart path:last-of-type { fill: #fff; transform-origin: 21px 25px; -webkit-transform-origin: 21px 25px; animation: hex-icon-heart-beat 1s linear infinite; }"
        + "      @keyframes hex-icon-heart-beat {"
        + "        0% { transform: scale3d(1, 1, 1); }"
        + "        30% { transform: scale3d(0.75, 0.75, 1); }"
        + "        60% { transform: scale3d(1, 1, 1); }"
        + "      }"
        + "      @-webkit-keyframes hex-icon-heart-beat {"
        + "        0% { -webkit-transform: scale3d(1, 1, 1); }"
        + "        30% { -webkit-transform: scale3d(0.75, 0.75, 1); }"
        + "        60% { -webkit-transform: scale3d(1, 1, 1); }"
        + "      }"
        + "    </style>"
        + "  </body>"
        + "</html>";
  }

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  /**
   * Gets enum list.
   *
   * @return the enum list
   * @author [류성재]
   * @implNote 열거형 Data 조회 - All
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @GetMapping(URIConfig.uriCommonEnum)
  public ResponseEntity<?> getEnumList() {
    return ok(enumMapper.getAll());
  }

  /**
   * Gets enum.
   *
   * @param pick the pick
   * @return the enum
   * @author [류성재]
   * @implNote 열거형 Data 조회 - One To Many
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @GetMapping(URIConfig.uriCommonEnumPick)
  public ResponseEntity<?> getEnum(@PathVariable String pick) {
    return ok(enumMapper.get(pick.toLowerCase()));
  }

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
   * @implNote 파일 업로드
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @PostMapping(value = URIConfig.uriCommonAttach, consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createAttach(@ModelAttribute @Valid AttachCreateRequest dto)
      throws IOException {
    return service.createAttach(dto);
  }

  /**
   * Exist attach response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 파일 존재 여부 확인
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @GetMapping(URIConfig.uriCommonAttachExist)
  public ResponseEntity<?> existAttach(@PathVariable Long id) {
    return service.existAttach(id);
  }

  /**
   * Gets attach.
   *
   * @param id the id
   * @return the attach
   * @author [류성재]
   * @implNote 파일 상세 조회
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @GetMapping(URIConfig.uriCommonAttachId)
  public ResponseEntity<?> getAttach(@PathVariable Long id) {
    return service.getAttach(id);
  }

  /**
   * Remove attach response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 파일 삭제
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @DeleteMapping(URIConfig.uriCommonAttachId)
  public ResponseEntity<?> removeAttach(@PathVariable Long id) {
    return service.removeAttach(id);
  }

  /**
   * Gets attach download.
   *
   * @param id the id
   * @return the attach download
   * @author [류성재]
   * @implNote 파일 다운로드
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @GetMapping(URIConfig.uriCommonAttachIdDownload)
  public ResponseEntity<?> getAttachDownload(@PathVariable Long id) throws IOException {
    return service.getAttachDownload(id);
  }

  //  _______  _______   __  .___________.  ______   .______
  // |   ____||       \ |  | |           | /  __  \  |   _  \
  // |  |__   |  .--.  ||  | `---|  |----`|  |  |  | |  |_)  |
  // |   __|  |  |  |  ||  |     |  |     |  |  |  | |      /
  // |  |____ |  '--'  ||  |     |  |     |  `--'  | |  |\  \----.
  // |_______||_______/ |__|     |__|      \______/  | _| `._____|

  /**
   * Create attach for editor ck.
   *
   * @param response the response
   * @param upload   the upload
   * @throws IOException the io exception
   * @author [류성재]
   * @implNote CK 에디터 파일 업로드
   * @since 2021. 3. 16. 오후 12:14:55
   */
  @Transactional
  @PostMapping(URIConfig.uriCommonEditorCK)
  public void createAttachForEditorCK(HttpServletResponse response,
      @RequestParam MultipartFile upload) throws IOException {
    validator.createAttachForEditorCK(upload);

    HashMap<String, Object> map = new HashMap<>();

    map.put("uri", service.createAttachForEditor(upload));

    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");

    PrintWriter printWriter = response.getWriter();

    printWriter.print(map);
    printWriter.flush();
    printWriter.close();
  }

}
