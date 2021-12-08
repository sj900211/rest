package run.freshr.domain.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
