package run.freshr.domain.auth.service;

import javax.persistence.EntityNotFoundException;
import run.freshr.domain.auth.entity.Sign;
import run.freshr.domain.auth.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Sign service.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 계정 관리 Service
 * @since 2021. 3. 16. 오후 2:31:28
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {

  /**
   * The Repository
   */
  private final SignRepository repository;

  /**
   * Exists boolean.
   *
   * @param username the username
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by Username
   * @since 2021. 3. 16. 오후 2:31:28
   */
  public Boolean exists(String username) {
    return repository.existsByUsername(username);
  }

  /**
   * Get sign.
   *
   * @param id the id
   * @return the sign
   * @author [류성재]
   * @implNote Data 조회 by ID
   * @since 2021. 3. 16. 오후 2:31:28
   */
  public Sign get(Long id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Get sign.
   *
   * @param username the username
   * @return the sign
   * @author [류성재]
   * @implNote Data 조회 by Username
   * @since 2021. 3. 16. 오후 2:31:28
   */
  public Sign get(String username) {
    return repository.findByUsername(username);
  }

}
