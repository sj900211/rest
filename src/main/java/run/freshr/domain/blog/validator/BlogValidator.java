package run.freshr.domain.blog.validator;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.util.RestUtil.rejectRequired;
import static run.freshr.common.util.RestUtil.rejectWrong;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.enumeration.PostPermission;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.mapping.dto.request.PostHashtagMappingForPostCreateRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogValidator {

  public void getPostPage(BlogSearch search, Errors errors) {
    if (isNull(search.getPage())) {
      rejectRequired("page", errors);
    }

    if (isNull(search.getSize())) {
      rejectRequired("size", errors);
    }
  }

  public void createPost(PostCreateRequest dto, BindingResult bindingResult) {
    checkHashtag(dto.getHashtagList(), bindingResult);
    checkGrant(dto.getManagerGrant(), dto.getLeaderGrant(), dto.getCoachGrant(),
        dto.getUserGrant(), dto.getAnonymousGrant(), bindingResult);
  }

  public void updatePost(PostUpdateRequest dto, BindingResult bindingResult) {
    checkHashtag(dto.getHashtagList(), bindingResult);
    checkGrant(dto.getManagerGrant(), dto.getLeaderGrant(), dto.getCoachGrant(),
        dto.getUserGrant(), dto.getAnonymousGrant(), bindingResult);
  }

  private void checkHashtag(List<PostHashtagMappingForPostCreateRequest> hashtagList,
      BindingResult bindingResult) {
    if (!isNull(hashtagList) && hashtagList.stream().anyMatch(item -> !(!isNull(item)
        && !isNull(item.getHashtag())
        && hasLength(item.getHashtag().getId())))) {
      rejectRequired("hashtagList[0].hashtag.id", bindingResult);
    }
  }

  private void checkGrant(Boolean managerGrant, Boolean leaderGrant, Boolean coachGrant,
      Boolean userGrant, Boolean anonymousGrant, BindingResult bindingResult) {
    PostPermission permission = PostPermission.find(List.of(true, managerGrant, leaderGrant,
        coachGrant, userGrant, anonymousGrant));

    if (isNull(permission)) {
      rejectWrong("managerGrant", bindingResult);
      rejectWrong("leaderGrant", bindingResult);
      rejectWrong("coachGrant", bindingResult);
      rejectWrong("userGrant", bindingResult);
      rejectWrong("anonymousGrant", bindingResult);
    }
  }

}
