package run.freshr.service;

import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.util.CryptoUtil.decryptBase64;

import run.freshr.common.util.RestUtil;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.service.ManagerService;
import run.freshr.domain.auth.service.SignService;
import run.freshr.domain.auth.dto.request.ManagerCreateRequest;
import run.freshr.domain.auth.dto.request.ManagerUpdateRequest;
import run.freshr.domain.auth.dto.response.ManagerResponse;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.setting.vo.SettingSearch;
import run.freshr.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Setting service.
 *
 * @author [류성재]
 * @implNote 설정 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettingService {

  /**
   * The Sign service
   */
  private final SignService signService;
  /**
   * The Manager service
   */
  private final ManagerService managerService;

  /**
   * The Password encoder
   */
  private final PasswordEncoder passwordEncoder;

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
   * @implNote 관리자 계정 생성
   * @since 2021. 3. 16. 오후 3:08:29
   */
  @Transactional
  public ResponseEntity<?> createManager(ManagerCreateRequest dto) {
    String username = decryptBase64(dto.getUsername());

    if (signService.exists(username)) {
      return error(RestUtil.getExceptions().getDuplicate(), "already username: " + username);
    }

    long id = managerService.create(Manager.createEntity(
        dto.getPrivilege(),
        username,
        passwordEncoder.encode(decryptBase64(dto.getPassword())),
        decryptBase64(dto.getName())
    ));

    return ok(IdResponse.builder().id(id).build());
  }

  /**
   * Gets manager page.
   *
   * @param search the search
   * @return the manager page
   * @author [류성재]
   * @implNote 관리자 계정 조회 - Page
   * @since 2021. 3. 16. 오후 3:08:29
   */
  public ResponseEntity<?> getManagerPage(SettingSearch search) {
    Page<ManagerResponse> page = managerService
        .getPage(search)
        .map(manager -> MapperUtil.map(manager, ManagerResponse.class));

    return RestUtil.ok(page);
  }

  /**
   * Gets manager.
   *
   * @param id the id
   * @return the manager
   * @author [류성재]
   * @implNote 관리자 계정 조회
   * @since 2021. 3. 16. 오후 3:08:29
   */
  public ResponseEntity<?> getManager(Long id) {
    ManagerResponse data = MapperUtil.map(managerService.get(id), ManagerResponse.class);

    return ok(data);
  }

  /**
   * Update manager response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 관리자 계정 수정
   * @since 2021. 3. 16. 오후 3:08:29
   */
  @Transactional
  public ResponseEntity<?> updateManager(Long id, ManagerUpdateRequest dto) {
    Manager entity = managerService.get(id);

    entity.updateEntity(
        dto.getPrivilege(),
        decryptBase64(dto.getName())
    );

    return RestUtil.ok();
  }

  /**
   * Remove manager response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 관리자 계정 삭제
   * @since 2021. 3. 16. 오후 3:08:29
   */
  @Transactional
  public ResponseEntity<?> removeManager(Long id) {
    Manager entity = managerService.get(id);

    entity.removeEntity();

    return RestUtil.ok();
  }

}
