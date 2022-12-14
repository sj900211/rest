package run.freshr.domain.common.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.common.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, String>,
    HashtagRepositoryCustom {

  List<Hashtag> findAllByOrderByIdAsc();
}
