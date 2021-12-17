package run.freshr.common.routing;

import static run.freshr.common.enumeration.DataSourceType.MASTER;
import static run.freshr.common.enumeration.DataSourceType.SLAVE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Profile({"dev", "staging", "prod"})
@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? SLAVE : MASTER;
  }

}
