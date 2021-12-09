package run.freshr.service;

import static run.freshr.common.util.RestUtil.buildId;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.getExceptions;
import static run.freshr.common.util.RestUtil.getSignedAccount;
import static run.freshr.common.util.RestUtil.getSignedRole;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_SUPER;
import static run.freshr.util.CryptoUtil.decryptRsa;
import static run.freshr.util.MapperUtil.map;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.dto.response.PostListResponse;
import run.freshr.domain.blog.dto.response.PostResponse;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.PostPermission;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.common.redis.RsaPair;
import run.freshr.domain.common.unit.RsaPairUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogServiceImpl implements BlogService {

  private final PostUnit postUnit;

  private final RsaPairUnit rsaPairUnit;

  @Override
  public ResponseEntity<?> getPostPage(BlogSearch search) {
    log.info("BlogService.getPostPage");

    return ok(postUnit.getPage(search, getSignedRole())
        .map(item -> map(item, PostListResponse.class)));
  }

  @Override
  @Transactional
  public ResponseEntity<?> getPost(Long id) {
    log.info("BlogService.getPost");

    Role signedRole = getSignedRole();
    Post entity = postUnit.get(id);
    boolean checkPermission = entity.checkPermission(signedRole.getPermission());
    boolean checkOwner = !signedRole.equals(ROLE_ANONYMOUS)
        && entity.checkOwner(getSignedAccount());

    if (!checkPermission && !checkOwner) {
      return error(getExceptions().getAccessDenied());
    }

    PostResponse data = map(entity, PostResponse.class);

    data.setPermission(entity.getPermission().getPostFlagList());

    return ok(data);
  }

  @Override
  @Transactional
  public ResponseEntity<?> createPost(PostCreateRequest dto) {
    log.info("BlogService.createPost");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getEncodePrivateKey();

    Account signedAccount = getSignedAccount();

    PostPermission permission = PostPermission.find(List.of(true, dto.getManagerGrant(),
        dto.getLeaderGrant(), dto.getCoachGrant(), dto.getUserGrant(), dto.getAnonymousGrant()));

    Long id = postUnit.create(Post.createEntity(decryptRsa(dto.getTitle(), encodePrivateKey),
        decryptRsa(dto.getContents(), encodePrivateKey), permission, signedAccount));

    return ok(buildId(id));
  }

  @Override
  @Transactional
  public ResponseEntity<?> updatePost(Long id, PostUpdateRequest dto) {
    log.info("BlogService.updatePost");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getEncodePrivateKey();

    Account signedAccount = getSignedAccount();
    Role signedRole = getSignedRole();
    Post entity = postUnit.get(id);

    if (!(!signedRole.equals(ROLE_SUPER) && !signedRole.equals(ROLE_MANAGER))
        && !entity.checkOwner(signedAccount)) {
      return error(getExceptions().getAccessDenied());
    }

    PostPermission permission = PostPermission.find(List.of(true, dto.getManagerGrant(),
        dto.getLeaderGrant(), dto.getCoachGrant(), dto.getUserGrant(), dto.getAnonymousGrant()));

    entity.updateEntity(decryptRsa(dto.getTitle(), encodePrivateKey),
        decryptRsa(dto.getContents(), encodePrivateKey), permission, signedAccount);

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> removePost(Long id) {
    log.info("BlogService.removePost");

    Account signedAccount = getSignedAccount();
    Post entity = postUnit.get(id);

    if (!entity.checkOwner(signedAccount)) {
      return error(getExceptions().getAccessDenied());
    }

    entity.removeEntity(signedAccount);

    return ok();
  }

}
