package run.freshr.domain.blog.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import run.freshr.domain.blog.elasticsearch.PostSearch;

public interface PostSearchRepository extends ElasticsearchRepository<PostSearch, Long>,
    PostSearchRepositoryCustom {

}
