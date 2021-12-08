package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.vo.BlogSearch;

public interface BlogService {

  ResponseEntity<?> createPost(PostCreateRequest dto);

  ResponseEntity<?> getPost(Long id);

  ResponseEntity<?> updatePost(Long id, PostUpdateRequest dto);

  ResponseEntity<?> removePost(Long id);

  ResponseEntity<?> getPostPage(BlogSearch search);

}
