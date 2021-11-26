package run.freshr.domain.auth.service;

import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.persistence.EntityNotFoundException;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.repository.AuthAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Auth access unit.
 *
 * @author [류성재]
 * @implNote Access 토큰 Service
 * @since 2020 -08-10 @author 류성재
 */
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthAccessUnit {

  /**
   * The Repository
   */
  private final AuthAccessRepository repository;

  /**
   * Gets page.
   *
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Iterable
   * @since 2021. 3. 16. 오후 2:28:51
   */
  public Iterable<AuthAccess> getPage() {
    return repository.findAll();
  }

  /**
   * Save.
   *
   * @param redis the redis
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:28:51
   */
  @Transactional
  public void save(AuthAccess redis) {
    repository.save(redis);
  }

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by ID
   * @since 2021. 3. 16. 오후 2:28:51
   */
  public Boolean exists(String id) {
    return repository.existsById(id);
  }

  /**
   * Exists boolean.
   *
   * @param signId the sign id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by Signed Account ID
   * @since 2021. 3. 16. 오후 2:28:51
   */
  public Boolean exists(Long signId) {
    Iterable<AuthAccess> accesses = repository.findAll();
    Optional<AuthAccess> authAccess = StreamSupport
        .stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId()
            .equals(signId)).findFirst();

    return authAccess.isPresent();
  }

  /**
   * Get auth access.
   *
   * @param id the id
   * @return the auth access
   * @author [류성재]
   * @implNote Data 조회 by ID
   * @since 2021. 3. 16. 오후 2:28:51
   */
  public AuthAccess get(String id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Get auth access.
   *
   * @param signId the sign id
   * @return the auth access
   * @author [류성재]
   * @implNote Data 조회 by Signed Account ID
   * @since 2021. 3. 16. 오후 2:28:51
   */
  public AuthAccess get(Long signId) {
    Iterable<AuthAccess> accesses = repository.findAll();
    Optional<AuthAccess> authAccess = StreamSupport
        .stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId()
            .equals(signId)).findFirst();

    return authAccess.orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @author [류성재]
   * @implNote 삭제 by ID
   * @since 2021. 3. 16. 오후 2:28:51
   */
  @Transactional
  public void delete(String id) {
    repository.deleteById(id);
  }

  /**
   * Delete.
   *
   * @param signId the sign id
   * @author [류성재]
   * @implNote 삭제 by Signed Account ID
   * @since 2021. 3. 16. 오후 2:28:51
   */
  @Transactional
  public void delete(Long signId) {
    Iterable<AuthAccess> accesses = repository.findAll();
    Optional<AuthAccess> authAccess = StreamSupport
        .stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId()
            .equals(signId)).findFirst();

    authAccess.ifPresent(repository::delete);
  }

}
