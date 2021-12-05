package run.freshr.domain.mapping.unit;

import java.util.List;
import run.freshr.common.extension.UnitDefaultExtension;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.AccountHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;

public interface AccountHashtagMappingUnit
    extends UnitDefaultExtension<AccountHashtagMapping, AccountHashtagMappingEmbeddedId> {

  List<AccountHashtagMapping> getList(Account entity);

  List<AccountHashtagMapping> getList(Hashtag entity);

  void delete(Account entity);

  void delete(Hashtag entity);

}
