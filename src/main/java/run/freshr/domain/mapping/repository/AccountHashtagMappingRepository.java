package run.freshr.domain.mapping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.AccountHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;

public interface AccountHashtagMappingRepository
    extends JpaRepository<AccountHashtagMapping, AccountHashtagMappingEmbeddedId> {

  List<AccountHashtagMapping> findAllByAccount(Account entity);

  List<AccountHashtagMapping> findAllByHashtag(Hashtag entity);

  void deleteAllByAccount(Account entity);

  void deleteAllByHashtag(Hashtag entity);
}
