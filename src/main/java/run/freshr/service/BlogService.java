package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.blog.dto.request.PostCreateRequest;

public interface BlogService {

  ResponseEntity<?> createPost(PostCreateRequest dto);

  ResponseEntity<?> existPost(Long id);

  ResponseEntity<?> getPost(Long id);

  ResponseEntity<?> deletePost(Long id);

}
