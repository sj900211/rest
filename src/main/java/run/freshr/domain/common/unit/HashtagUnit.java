package run.freshr.domain.common.unit;

import java.util.List;
import run.freshr.common.extension.UnitDefaultExtension;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.common.dto.response.HashtagListResponse;
import run.freshr.domain.common.entity.Hashtag;

public interface HashtagUnit extends UnitDefaultExtension<Hashtag, String> {

  List<HashtagListResponse> getList(Role role);

}
