package run.freshr;

import static run.freshr.domain.auth.enumeration.Privilege.COACH;
import static run.freshr.domain.auth.enumeration.Privilege.LEADER;
import static run.freshr.domain.auth.enumeration.Privilege.MANAGER;
import static run.freshr.domain.auth.enumeration.Privilege.SUPER;
import static run.freshr.domain.auth.enumeration.Privilege.USER;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.document.IdDocument;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.document.PostHashtagMappingForPostDocument;
import run.freshr.service.TestService;
import run.freshr.util.StringUtil;

@Slf4j
@Profile("test")
@Component
public class DataRunner implements ApplicationRunner {

  public static long superId; // 수퍼 관리자 계정 일련 번호
  public static long managerId; // 관리자 계정 일련 번호
  public static long leaderId; // 리더 계정 일련 번호
  public static long coachId; // 코치 계정 일련 번호
  public static long userId; // 사용자 계정 일련 번호
  public static long attachId; // 파일 일련 번호
  public static List<String> hashtagList = new ArrayList<>(); // 해시태그 목록
  public static List<Long> postList = new ArrayList<>(); // 포스팅 목록

  @Autowired
  private TestService testService;

  @Override
  public void run(ApplicationArguments args) {
    log.debug(
        " _______       ___   .___________.    ___         .______       _______     _______. _______ .___________.");
    log.debug(
        "|       \\     /   \\  |           |   /   \\        |   _  \\     |   ____|   /       ||   ____||           |");
    log.debug(
        "|  .--.  |   /  ^  \\ `---|  |----`  /  ^  \\       |  |_)  |    |  |__     |   (----`|  |__   `---|  |----`");
    log.debug(
        "|  |  |  |  /  /_\\  \\    |  |      /  /_\\  \\      |      /     |   __|     \\   \\    |   __|      |  |     ");
    log.debug(
        "|  '--'  | /  _____  \\   |  |     /  _____  \\     |  |\\  \\----.|  |____.----)   |   |  |____     |  |     ");
    log.debug(
        "|_______/ /__/     \\__\\  |__|    /__/     \\__\\    | _| `._____||_______|_______/    |_______|    |__|     ");

    setAuth();
    setCommon();
    setBlog();
    setMapping();
  }

  private void setAuth() {
    superId = testService.createAccount(SUPER, "super", "수퍼 관리자");
    managerId = testService.createAccount(MANAGER, "manager", "관리자");
    leaderId = testService.createAccount(LEADER, "leader", "리더");
    coachId = testService.createAccount(COACH, "coach", "관리자");
    userId = testService.createAccount(USER, "user", "사용자");
  }

  private void setCommon() {
    attachId = testService
        .createAttach("test.png", "temp", testService.getAccount(userId));

    for (int i = 0; i < 15; i++) {
      String padding = StringUtil.padding(i + 1, 3);

      hashtagList.add(testService.createHashtag(padding));
    }
  }

  private void setBlog() {
    Account superAccount = testService.getAccount(superId);
    Account managerAccount = testService.getAccount(managerId);
    Account leaderAccount = testService.getAccount(leaderId);
    Account coachAccount = testService.getAccount(coachId);
    List<Hashtag> hashtags = hashtagList
        .stream()
        .map(pk -> testService.getHashtag(pk))
        .collect(Collectors.toList());

    for (int i = 0; i < 15; i++) {
      String padding = StringUtil.padding(i, 3);
      long superId = testService.createPost(padding, superAccount);
      long managerId = testService.createPost(padding, managerAccount);
      long leaderId = testService.createPost(padding, leaderAccount);
      long coachId = testService.createPost(padding, coachAccount);
      Post superPost = testService.getPost(superId);
      Post managerPost = testService.getPost(managerId);
      Post leaderPost = testService.getPost(leaderId);
      Post coachPost = testService.getPost(coachId);
      Hashtag hashtag = hashtags.get(i);

      testService.createPostHashtagMapping(superPost, hashtag);
      testService.createPostHashtagMapping(managerPost, hashtag);
      testService.createPostHashtagMapping(leaderPost, hashtag);
      testService.createPostHashtagMapping(coachPost, hashtag);

      testService.savePostSearch(superPost.getTitle(), superPost.getId(), superAccount,
          List.of(PostHashtagMappingForPostDocument
              .createDocument(IdDocument.createDocument(hashtag.getId()))));
      testService.savePostSearch(managerPost.getTitle(), managerPost.getId(), managerAccount,
          List.of(PostHashtagMappingForPostDocument
              .createDocument(IdDocument.createDocument(hashtag.getId()))));
      testService.savePostSearch(leaderPost.getTitle(), leaderPost.getId(), leaderAccount,
          List.of(PostHashtagMappingForPostDocument
              .createDocument(IdDocument.createDocument(hashtag.getId()))));
      testService.savePostSearch(coachPost.getTitle(), coachPost.getId(), coachAccount,
          List.of(PostHashtagMappingForPostDocument
              .createDocument(IdDocument.createDocument(hashtag.getId()))));

      postList.add(superId);
      postList.add(managerId);
      postList.add(leaderId);
      postList.add(coachId);
    }
  }

  private void setMapping() {
    hashtagList.stream()
        .map(item -> testService.getHashtag(item))
        .forEach(hashtag -> {
          testService.createAccountHashtagMapping(testService.getAccount(superId), hashtag);
          testService.createAccountHashtagMapping(testService.getAccount(managerId), hashtag);
          testService.createAccountHashtagMapping(testService.getAccount(leaderId), hashtag);
          testService.createAccountHashtagMapping(testService.getAccount(coachId), hashtag);
          testService.createAccountHashtagMapping(testService.getAccount(userId), hashtag);
        });
  }

}
