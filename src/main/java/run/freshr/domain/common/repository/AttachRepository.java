package run.freshr.domain.common.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.freshr.domain.common.entity.Attach;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {

  Boolean existsByIdAndDelFlagFalseAndUseFlagTrue(Long id);

  Optional<Attach> findByIdAndDelFlagFalseAndUseFlagTrue(Long id);

}
