package run.freshr.domain.auth.unit;

import run.freshr.common.extension.UnitDefaultExtension;
import run.freshr.domain.auth.entity.Account;

public interface AccountUnit extends UnitDefaultExtension<Account, Long> {

  Boolean exists(String username);

  Account get(String username);

}
