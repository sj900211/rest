package run.freshr.common.config;

import static run.freshr.common.enumeration.DataSourceType.MASTER;
import static run.freshr.common.enumeration.DataSourceType.SLAVE;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import run.freshr.common.routing.ReplicationRoutingDataSource;

@Profile({"dev", "staging", "prod"})
@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class RoutingDataSourceConfig {

  @Bean(name = "dataSourceMaster")
  @ConfigurationProperties(prefix="spring.datasource.master.hikari")
  public DataSource dataSourceMaster() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = "dataSourceSlave")
  @ConfigurationProperties(prefix="spring.datasource.slave.hikari")
  public DataSource dataSourceSlave() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean(name = "routingDataSource")
  public DataSource routingDataSource(
      @Qualifier("dataSourceMaster") final DataSource dataSourceMaster,
      @Qualifier("dataSourceSlave") final DataSource dataSourceSlave) {
    ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
    Map<Object, Object> dataSourceMap = new HashMap<>();

    dataSourceMap.put(MASTER, dataSourceMaster);
    dataSourceMap.put(SLAVE, dataSourceSlave);

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(dataSourceMaster);

    return routingDataSource;
  }

  @Primary
  @Bean(name = "dataSource")
  public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

}
