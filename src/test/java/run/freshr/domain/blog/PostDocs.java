package run.freshr.domain.blog;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.blog.entity.QPost.post;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.util.PrintUtil;
import run.freshr.domain.auth.AccountDocs;
import run.freshr.domain.blog.vo.EBlogSearch;

@Slf4j
public class PostDocs {

  public static class Request {

    public static List<ParameterDescriptor> getPostPage() {
      log.info("PostDocs.Request.getPostPage");

      return PrintUtil
          .builder()

          .parameter(EBlogSearch.page, EBlogSearch.size)

          .prefixOptional()
          .linkParameter("post-page", EBlogSearch.key)
          .parameter(EBlogSearch.word)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPost() {
      log.info("PostDocs.Request.getPost");

      return PrintUtil
          .builder()

          .prefixDescription("포스팅")
          .parameter(post.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> createPost() {
      log.info("PostDocs.Response.createPost");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .field(post.title, "제목 [RSA 암호화]")
          .field(post.contents, "내용 [RSA 암호화]")
          .field("managerGrant", "관리자 읽기 권한", BOOLEAN)
          .field("leaderGrant", "리더 읽기 권한", BOOLEAN)
          .field("coachGrant", "코치 읽기 권한", BOOLEAN)
          .field("userGrant", "사용자 읽기 권한", BOOLEAN)
          .field("anonymousGrant", "게스트 읽기 권한", BOOLEAN)

          .prefixOptional()
          .field(post.hashtagList, "해시태그 목록", ARRAY)
          .field(post.hashtagList.any().hashtag, OBJECT)
          .field(post.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> updatePostPath() {
      log.info("PostDocs.Response.updatePostPath");

      return PrintUtil
          .builder()

          .prefixDescription("포스팅")
          .parameter(post.id)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> updatePost() {
      log.info("PostDocs.Response.createPost");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .field(post.title, "제목 [RSA 암호화]")
          .field(post.contents, "내용 [RSA 암호화]")
          .field("managerGrant", "관리자 읽기 권한", BOOLEAN)
          .field("leaderGrant", "리더 읽기 권한", BOOLEAN)
          .field("coachGrant", "코치 읽기 권한", BOOLEAN)
          .field("userGrant", "사용자 읽기 권한", BOOLEAN)
          .field("anonymousGrant", "게스트 읽기 권한", BOOLEAN)

          .prefixOptional()
          .field(post.hashtagList, "해시태그 목록", ARRAY)
          .field(post.hashtagList.any().hashtag, OBJECT)
          .field(post.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> removePost() {
      log.info("PostDocs.Response.removePost");

      return PrintUtil
          .builder()

          .prefixDescription("포스팅")
          .parameter(post.id)

          .build()
          .getParameterList();
    }

  }

  public static class Response {

    public static List<FieldDescriptor> getPostPage() {
      log.info("PostDocs.Response.getPostPage");

      return ResponseDocs
          .Response
          .page()

          .field(post.id, post.title, post.hits, post.createDt, post.updateDt)

          .addField(AccountDocs.Docs.getAudit("page.content[].creator"))
          .addField(AccountDocs.Docs.getAudit("page.content[].updater"))

          .prefixOptional()
          .field(post.hashtagList, "해시태그 목록", ARRAY)
          .field(post.hashtagList.any().hashtag, OBJECT)
          .field(post.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getPost() {
      log.info("PostDocs.Response.getPost");

      return ResponseDocs
          .Response
          .data()

          .prefixDescription("포스팅")

          .field(post.id, post.title, post.contents, post.hits, post.createDt, post.updateDt)
          .field("managerGrant", "관리자 읽기 권한", BOOLEAN)
          .field("leaderGrant", "리더 읽기 권한", BOOLEAN)
          .field("coachGrant", "코치 읽기 권한", BOOLEAN)
          .field("userGrant", "사용자 읽기 권한", BOOLEAN)
          .field("anonymousGrant", "게스트 읽기 권한", BOOLEAN)

          .addField(AccountDocs.Docs.getAudit("data.creator"))
          .addField(AccountDocs.Docs.getAudit("data.updater"))

          .prefixOptional()
          .field(post.hashtagList, "해시태그 목록", ARRAY)
          .field(post.hashtagList.any().hashtag, OBJECT)
          .field(post.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> createPost() {
      log.info("PostDocs.Request.createPost");

      return ResponseDocs
          .Response
          .data()

          .prefixDescription("포스팅")
          .field(post.id)

          .build()
          .getFieldList();
    }

  }

  public static class Docs {
  }

}
