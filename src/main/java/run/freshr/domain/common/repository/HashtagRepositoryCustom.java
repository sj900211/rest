package run.freshr.domain.common.repository;

import java.util.List;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.common.dto.response.HashtagListResponse;

public interface HashtagRepositoryCustom {

  List<HashtagListResponse> getList(Role role);

}
