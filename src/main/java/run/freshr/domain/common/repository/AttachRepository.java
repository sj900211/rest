package run.freshr.domain.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.common.entity.Attach;

public interface AttachRepository extends JpaRepository<Attach, Long> {

}
