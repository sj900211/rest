package run.freshr.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.auth.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Boolean existsByUsername(String username);

  Account findByUsername(String username);

}
