package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriSettingManager;
import static run.freshr.common.config.URIConfig.uriSettingManagerId;

import javax.validation.Valid;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.dto.request.ManagerCreateRequest;
import run.freshr.domain.auth.dto.request.ManagerUpdateRequest;
import run.freshr.domain.setting.validator.SettingValidator;
import run.freshr.domain.setting.vo.SettingSearch;
import run.freshr.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Setting controller.
 *
 * @author [류성재]
 * @implNote 시스템 관리
 * @since 2021. 3. 16. 오후 12:18:56
 */
@RestController
@RequiredArgsConstructor
public class SettingController {

  /**
   * The Service
   */
  private final SettingService service;

  /**
   * The Validator
   */
  private final SettingValidator validator;

  // .___  ___.      ___      .__   __.      ___       _______  _______ .______
  // |   \/   |     /   \     |  \ |  |     /   \     /  _____||   ____||   _  \
  // |  \  /  |    /  ^  \    |   \|  |    /  ^  \   |  |  __  |  |__   |  |_)  |
  // |  |\/|  |   /  /_\  \   |  . `  |   /  /_\  \  |  | |_ | |   __|  |      /
  // |  |  |  |  /  _____  \  |  |\   |  /  _____  \ |  |__| | |  |____ |  |\  \----.
  // |__|  |__| /__/     \__\ |__| \__| /__/     \__\ \______| |_______|| _| `._____|

  /**
   * Create manager response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 관리자 계정 등록
   * @since 2021. 3. 16. 오후 12:18:56
   */
  @Secured(Role.Secured.SUPER)
  @PostMapping(uriSettingManager)
  public ResponseEntity<?> createManager(@RequestBody @Valid ManagerCreateRequest dto) {
    validator.managerSuperValidate();

    return service.createManager(dto);
  }

  /**
   * Gets manager page.
   *
   * @param search the search
   * @return the manager page
   * @author [류성재]
   * @implNote 관리자 계정 정보 조회 - Page
   * @since 2021. 3. 16. 오후 12:18:56
   */
  @Secured(Role.Secured.SUPER)
  @GetMapping(uriSettingManager)
  public ResponseEntity<?> getManagerPage(SettingSearch search) {
    validator.managerSuperValidate();

    return service.getManagerPage(search);
  }

  /**
   * Gets manager.
   *
   * @param id the id
   * @return the manager
   * @author [류성재]
   * @implNote 관리자 계정 정보 조회
   * @since 2021. 3. 16. 오후 12:18:56
   */
  @Secured(Role.Secured.SUPER)
  @GetMapping(uriSettingManagerId)
  public ResponseEntity<?> getManager(@PathVariable Long id) {
    return service.getManager(id);
  }

  /**
   * Update manager response entity.
   *
   * @param id  the id
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 관리자 계정 정보 수정
   * @since 2021. 3. 16. 오후 12:18:56
   */
  @Secured(Role.Secured.SUPER)
  @PutMapping(uriSettingManagerId)
  public ResponseEntity<?> updateManager(@PathVariable Long id,
      @RequestBody @Valid ManagerUpdateRequest dto) {
    validator.managerSuperValidate();

    return service.updateManager(id, dto);
  }

  /**
   * Remove manager response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 탈퇴 처리
   * @since 2021. 3. 16. 오후 12:18:56
   */
  @Secured(Role.Secured.SUPER)
  @DeleteMapping(uriSettingManagerId)
  public ResponseEntity<?> removeManager(@PathVariable Long id) {
    validator.managerSuperValidate();

    return service.removeManager(id);
  }

}
