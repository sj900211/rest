package run.freshr.common.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

  @Value("${freshr.elasticsearch.url}")
  private String url;

  @Override
  public RestHighLevelClient elasticsearchClient() {
    return RestClients.create(ClientConfiguration
            .builder()
            .connectedTo(url)
            .build())
        .rest();
  }

}
