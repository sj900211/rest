package run.freshr.domain.auth.service;

import static java.util.Objects.isNull;

import javax.persistence.EntityNotFoundException;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.redis.AuthRefresh;
import run.freshr.domain.auth.repository.AuthRefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Auth refresh service.
 *
 * @author [류성재]
 * @implNote Refresh 토큰 Service
 * @since 2020 -08-10 @author 류성재
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthRefreshService {

  /**
   * The Repository
   */
  private final AuthRefreshRepository repository;

  /**
   * Save.
   *
   * @param redis the redis
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:30:01
   */
  @Transactional
  public void save(AuthRefresh redis) {
    repository.save(redis);
  }

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by Id
   * @since 2021. 3. 16. 오후 2:30:01
   */
  public Boolean exists(String id) {
    return repository.existsById(id);
  }

  /**
   * Gets detail.
   *
   * @param id the id
   * @return the detail
   * @author [류성재]
   * @implNote Data 조회 by Id
   * @since 2021. 3. 16. 오후 2:30:01
   */
  public AuthRefresh getDetail(String id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @author [류성재]
   * @implNote 삭제 by ID
   * @since 2021. 3. 16. 오후 2:30:02
   */
  @Transactional
  public void delete(String id) {
    repository.deleteById(id);
  }

  /**
   * Delete.
   *
   * @param access the access
   * @author [류성재]
   * @implNote 삭제 by Entity
   * @since 2021. 3. 16. 오후 2:30:02
   */
  @Transactional
  public void delete(AuthAccess access) {
    Iterable<AuthRefresh> refreshIterable = repository.findAll();
    refreshIterable.forEach(refresh -> {
      if (!isNull(refresh)
          && !isNull(refresh.getAccess())
          && refresh.getAccess().getId().equals(access.getId())) {
        repository.delete(refresh);
      }
    });
  }

}
