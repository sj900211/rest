package run.freshr.domain.common.repository.jpa;

import java.util.List;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.common.dto.response.HashtagCountResponse;

public interface HashtagRepositoryCustom {

  List<HashtagCountResponse> getList(Role role);

}
