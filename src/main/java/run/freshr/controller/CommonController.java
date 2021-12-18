package run.freshr.controller;

import static java.lang.System.lineSeparator;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static run.freshr.common.config.URIConfig.uriCommonAttach;
import static run.freshr.common.config.URIConfig.uriCommonAttachExist;
import static run.freshr.common.config.URIConfig.uriCommonAttachId;
import static run.freshr.common.config.URIConfig.uriCommonAttachIdDownload;
import static run.freshr.common.config.URIConfig.uriCommonCrypto;
import static run.freshr.common.config.URIConfig.uriCommonEnum;
import static run.freshr.common.config.URIConfig.uriCommonEnumPick;
import static run.freshr.common.config.URIConfig.uriCommonHashtag;
import static run.freshr.common.config.URIConfig.uriCommonHashtagAll;
import static run.freshr.common.config.URIConfig.uriCommonHashtagId;
import static run.freshr.common.config.URIConfig.uriCommonHeartbeat;
import static run.freshr.common.util.RestUtil.getConfig;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.domain.auth.enumeration.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.Secured.COACH;
import static run.freshr.domain.auth.enumeration.Role.Secured.LEADER;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER;
import static run.freshr.domain.auth.enumeration.Role.Secured.SUPER;
import static run.freshr.domain.auth.enumeration.Role.Secured.USER;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.common.dto.request.AttachCreateRequest;
import run.freshr.domain.common.dto.request.IdRequest;
import run.freshr.mapper.EnumMapper;
import run.freshr.service.CommonService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommonController {

  private final EnumMapper enumMapper;
  private final CommonService service;

  @GetMapping(uriCommonHeartbeat)
  public String getHeartBeat() throws IOException {
    log.info("CommonController.getHeartBeat");

    return readAllLines(getConfig().getHeartbeat().getFile().toPath())
        .stream()
        .collect(joining(lineSeparator()));
  }

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonEnum)
  public ResponseEntity<?> getEnumList() {
    log.info("CommonController.getEnumList");

    return ok(enumMapper.getAll());
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonEnumPick)
  public ResponseEntity<?> getEnum(@PathVariable String pick) {
    log.info("CommonController.getEnum");

    return ok(enumMapper.get(pick.toLowerCase()));
  }

  //   ______ .______     ____    ____ .______   .___________.  ______
  //  /      ||   _  \    \   \  /   / |   _  \  |           | /  __  \
  // |  ,----'|  |_)  |    \   \/   /  |  |_)  | `---|  |----`|  |  |  |
  // |  |     |      /      \_    _/   |   ___/      |  |     |  |  |  |
  // |  `----.|  |\  \----.   |  |     |  |          |  |     |  `--'  |
  //  \______|| _| `._____|   |__|     | _|          |__|      \______/

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonCrypto)
  public ResponseEntity<?> getPublicKey() {
    return service.getPublicKey();
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|

  @Secured({SUPER, MANAGER, LEADER, COACH, USER})
  @PostMapping(value = uriCommonAttach, consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createAttach(@ModelAttribute @Valid AttachCreateRequest dto)
      throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException {
    log.info("CommonController.createAttach");

    return service.createAttach(dto);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonAttachExist)
  public ResponseEntity<?> existAttach(@PathVariable Long id) {
    log.info("CommonController.existAttach");

    return service.existAttach(id);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonAttachId)
  public ResponseEntity<?> getAttach(@PathVariable Long id) {
    log.info("CommonController.getAttach");

    return service.getAttach(id);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER})
  @DeleteMapping(uriCommonAttachId)
  public ResponseEntity<?> removeAttach(@PathVariable Long id) {
    log.info("CommonController.removeAttach");

    return service.deleteAttach(id);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonAttachIdDownload)
  public ResponseEntity<?> getAttachDownload(@PathVariable Long id)
      throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException {
    log.info("CommonController.getAttachDownload");

    return service.getAttachDownload(id);
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonHashtag)
  public ResponseEntity<?> getHashtagList() {
    log.info("CommonController.getHashtagList");

    return service.getHashtagList();
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriCommonHashtagAll)
  public ResponseEntity<?> getHashtagAll() {
    log.info("CommonController.getHashtagList");

    return service.getHashtagAll();
  }

  @Secured({SUPER, MANAGER})
  @PostMapping(uriCommonHashtag)
  public ResponseEntity<?> createHashtag(@RequestBody @Valid IdRequest<String> dto) {
    log.info("CommonController.createHashtag");

    return service.createHashtag(dto);
  }

  @Secured({SUPER, MANAGER})
  @DeleteMapping(uriCommonHashtagId)
  public ResponseEntity<?> deleteHashtag(@PathVariable String id) {
    log.info("CommonController.deleteHashtag");

    return service.deleteHashtag(id);
  }

}
