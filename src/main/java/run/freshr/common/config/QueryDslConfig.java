package run.freshr.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jPAQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

}
