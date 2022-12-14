package run.freshr.common.config;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class H2ServerConfig {

  @Bean
  public Server h2TcpServer() throws SQLException {
    return Server.createTcpServer().start();
  }

}
