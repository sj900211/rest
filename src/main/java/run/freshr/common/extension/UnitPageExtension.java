package run.freshr.common.extension;

import org.springframework.data.domain.Page;

public interface UnitPageExtension<E, ID, S extends SearchExtension>
    extends UnitDefaultExtension<E, ID> {

  Page<E> getPage(S search);

}
