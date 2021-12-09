package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriBlogPost;
import static run.freshr.common.config.URIConfig.uriBlogPostId;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.domain.auth.enumeration.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.Secured.COACH;
import static run.freshr.domain.auth.enumeration.Role.Secured.LEADER;
import static run.freshr.domain.auth.enumeration.Role.Secured.MANAGER;
import static run.freshr.domain.auth.enumeration.Role.Secured.SUPER;
import static run.freshr.domain.auth.enumeration.Role.Secured.USER;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.validator.BlogValidator;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.service.BlogService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BlogController {

  private final BlogService service;
  private final BlogValidator validator;

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriBlogPost)
  public ResponseEntity<?> getPostPage(@ModelAttribute @Valid BlogSearch search, Errors errors) {
    log.info("BlogController.getPostPage");

    validator.getPostPage(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getPostPage(search);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH, USER, ANONYMOUS})
  @GetMapping(uriBlogPostId)
  public ResponseEntity<?> getPost(@PathVariable Long id) {
    log.info("BlogController.getPost");

    return service.getPost(id);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH})
  @PostMapping(uriBlogPost)
  public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequest dto,
      BindingResult bindingResult) {
    log.info("BlogController.createPost");

    validator.createPost(dto, bindingResult);

    if (bindingResult.hasErrors()) {
      return error(bindingResult);
    }

    return service.createPost(dto);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH})
  @PutMapping(uriBlogPostId)
  public ResponseEntity<?> updatePost(@PathVariable Long id,
      @RequestBody @Valid PostUpdateRequest dto, BindingResult bindingResult) {
    log.info("BlogController.updatePost");

    validator.updatePost(dto, bindingResult);

    if (bindingResult.hasErrors()) {
      return error(bindingResult);
    }

    return service.updatePost(id, dto);
  }

  @Secured({SUPER, MANAGER, LEADER, COACH})
  @DeleteMapping(uriBlogPostId)
  public ResponseEntity<?> removePost(@PathVariable Long id) {
    log.info("BlogController.removePost");

    return service.removePost(id);
  }

}
