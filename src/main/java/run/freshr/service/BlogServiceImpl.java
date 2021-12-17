package run.freshr.service;

import static run.freshr.common.util.RestUtil.buildId;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.getExceptions;
import static run.freshr.common.util.RestUtil.getSignedAccount;
import static run.freshr.common.util.RestUtil.getSignedId;
import static run.freshr.common.util.RestUtil.getSignedRole;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_SUPER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;
import static run.freshr.util.CryptoUtil.decryptRsa;
import static run.freshr.util.MapperUtil.map;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.domain.auth.document.AuditDocument;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.dto.response.PostListResponse;
import run.freshr.domain.blog.dto.response.PostResponse;
import run.freshr.domain.blog.elasticsearch.PostSearch;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.PostPermission;
import run.freshr.domain.blog.unit.PostSearchUnit;
import run.freshr.domain.blog.unit.PostUnit;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.common.document.IdDocument;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.redis.RsaPair;
import run.freshr.domain.common.unit.HashtagUnit;
import run.freshr.domain.common.unit.RsaPairUnit;
import run.freshr.domain.mapping.document.PostHashtagMappingForPostDocument;
import run.freshr.domain.mapping.dto.request.PostHashtagMappingForPostCreateRequest;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.unit.PostHashtagMappingUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogServiceImpl implements BlogService {

  private final PostUnit postUnit;
  private final PostSearchUnit postSearchUnit;
  private final HashtagUnit hashtagUnit;
  private final PostHashtagMappingUnit postHashtagMappingUnit;

  private final RsaPairUnit rsaPairUnit;

  @Override
  public ResponseEntity<?> getPostPage(BlogSearch search) {
    log.info("BlogService.getPostPage");

    Page<PostListResponse> page = postSearchUnit.getPage(search, getSignedId(), getSignedRole())
        .map(item -> map(item, PostListResponse.class));

    return ok(page);
  }

  @Override
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
    Boolean managerGrant = dto.getManagerGrant();
    Boolean leaderGrant = dto.getLeaderGrant();
    Boolean coachGrant = dto.getCoachGrant();
    Boolean userGrant = dto.getUserGrant();
    Boolean anonymousGrant = dto.getAnonymousGrant();
    String title = decryptRsa(dto.getTitle(), encodePrivateKey);
    Account signedAccount = getSignedAccount();
    Long signedId = signedAccount.getId();
    String signedUsername = signedAccount.getUsername();
    String signedName = signedAccount.getName();
    PostPermission permission = PostPermission.find(List.of(true,
        managerGrant, leaderGrant, coachGrant, userGrant, anonymousGrant));
    Post entity = Post.createEntity(title, decryptRsa(dto.getContents(), encodePrivateKey),
        permission, signedAccount);
    Long id = postUnit.create(entity);

    List<PostHashtagMappingForPostCreateRequest> hashtagList = dto.getHashtagList();

    saveHashtagMapping(entity, hashtagList);
    postSearchUnit.save(PostSearch.createDocument(id, title,
        managerGrant, leaderGrant, coachGrant, userGrant, anonymousGrant,
        AuditDocument.createDocument(signedId, signedUsername, signedName),
        hashtagList.stream().map(item ->
            PostHashtagMappingForPostDocument
                .createDocument(IdDocument.createDocument(item.getHashtag().getId())))
            .collect(Collectors.toList())));

    rsaPairUnit.delete(encodePublicKey);

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
    Boolean managerGrant = dto.getManagerGrant();
    Boolean leaderGrant = dto.getLeaderGrant();
    Boolean coachGrant = dto.getCoachGrant();
    Boolean userGrant = dto.getUserGrant();
    Boolean anonymousGrant = dto.getAnonymousGrant();

    Account signedAccount = getSignedAccount();
    Long signedId = signedAccount.getId();
    String signedUsername = signedAccount.getUsername();
    String signedName = signedAccount.getName();
    Role signedRole = getSignedRole();
    Post entity = postUnit.get(id);

    if (!(!signedRole.equals(ROLE_SUPER) && !signedRole.equals(ROLE_MANAGER))
        && !entity.checkOwner(signedAccount)) {
      return error(getExceptions().getAccessDenied());
    }

    String title = decryptRsa(dto.getTitle(), encodePrivateKey);
    PostPermission permission = PostPermission.find(List.of(true,
        managerGrant, leaderGrant, coachGrant, userGrant, anonymousGrant));

    entity.updateEntity(title, decryptRsa(dto.getContents(), encodePrivateKey),
        permission, signedAccount);


    List<PostHashtagMappingForPostCreateRequest> hashtagList = dto.getHashtagList();

    postHashtagMappingUnit.delete(entity);
    saveHashtagMapping(entity, hashtagList);

    Boolean exists = postSearchUnit.exists(id);

    AuditDocument auditDocument = AuditDocument
        .createDocument(signedId, signedUsername, signedName);
    List<PostHashtagMappingForPostDocument> hashtagDocumentList = hashtagList.stream().map(item ->
            PostHashtagMappingForPostDocument
                .createDocument(IdDocument.createDocument(item.getHashtag().getId())))
        .collect(Collectors.toList());

    if (!exists) {
      postSearchUnit.save(PostSearch.createDocument(id, title,
          managerGrant, leaderGrant, coachGrant, userGrant, anonymousGrant,
          auditDocument, hashtagDocumentList));
    } else {
      PostSearch document = postSearchUnit.get(id);

      document.updateDocument(title,
          managerGrant, leaderGrant, coachGrant, userGrant, anonymousGrant,
          auditDocument, hashtagDocumentList);

      postSearchUnit.save(document);
    }

    rsaPairUnit.delete(encodePublicKey);

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> addPostHit(Long id) {
    log.info("BlogService.addHit");

    Role signedRole = getSignedRole();
    Post entity = postUnit.get(id);

    if (!(!signedRole.equals(ROLE_USER) && !signedRole.equals(ROLE_ANONYMOUS))) {
      entity.addHits();

      Boolean exists = postSearchUnit.exists(id);

      if (exists) {
        postSearchUnit.get(id).addHits();
      }
    }

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

    Boolean exists = postSearchUnit.exists(id);

    if (exists) {
      postSearchUnit.delete(id);
    }

    return ok();
  }

  private void saveHashtagMapping(Post entity,
      List<PostHashtagMappingForPostCreateRequest> hashtagList) {
    hashtagList.forEach(item -> {
      String hashtag = item.getHashtag().getId();
      Boolean exists = hashtagUnit.exists(hashtag);
      Hashtag hashtagEntity = exists ? hashtagUnit.get(hashtag) : Hashtag.createEntity(hashtag);

      if (exists) {
        hashtagUnit.create(hashtagEntity);
      }

      postHashtagMappingUnit.create(PostHashtagMapping.createEntity(entity, hashtagEntity));
    });
  }

}
