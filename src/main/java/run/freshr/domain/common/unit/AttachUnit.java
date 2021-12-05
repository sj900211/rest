package run.freshr.domain.common.unit;

import java.util.List;
import run.freshr.common.extension.UnitDefaultExtension;
import run.freshr.domain.common.entity.Attach;

public interface AttachUnit extends UnitDefaultExtension<Attach, Long> {

  List<Long> create(List<Attach> entities);

  List<Attach> getList();

}
