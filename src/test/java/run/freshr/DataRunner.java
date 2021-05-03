package run.freshr;

import java.util.ArrayList;
import java.util.List;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.common.entity.Attach;
import run.freshr.service.TestService;
import run.freshr.domain.community.entity.Board;
import run.freshr.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class DataRunner implements ApplicationRunner {

  public static long superManagerId; // 수퍼 관리자 계정 일련 번호
  public static List<Long> managerIdList = new ArrayList<>(); // 일반 관리자 계정 일련 번호 목록
  public static List<Long> accountIdList = new ArrayList<>(); // 사용자 계정 일련 번호 목록
  public static List<Long> attachIdList = new ArrayList<>(); // 첨부 파일 일련 번호 목록
  public static List<Long> boardIdList = new ArrayList<>(); // 게시글 일련 번호 목록
  @Autowired
  private TestService testService;

  /**
   * Runner
   */
  @Override
  public void run(ApplicationArguments args) {
    System.out.println(
        " _______       ___   .___________.    ___         .______       _______     _______. _______ .___________.");
    System.out.println(
        "|       \\     /   \\  |           |   /   \\        |   _  \\     |   ____|   /       ||   ____||           |");
    System.out.println(
        "|  .--.  |   /  ^  \\ `---|  |----`  /  ^  \\       |  |_)  |    |  |__     |   (----`|  |__   `---|  |----`");
    System.out.println(
        "|  |  |  |  /  /_\\  \\    |  |      /  /_\\  \\      |      /     |   __|     \\   \\    |   __|      |  |     ");
    System.out.println(
        "|  '--'  | /  _____  \\   |  |     /  _____  \\     |  |\\  \\----.|  |____.----)   |   |  |____     |  |     ");
    System.out.println(
        "|_______/ /__/     \\__\\  |__|    /__/     \\__\\    | _| `._____||_______|_______/    |_______|    |__|     ");

    setAuth();
    setCommon();
    setCommunity();
  }

  /**
   * 계정 구성
   */
  private void setAuth() {
    superManagerId = testService.createSuper("super", "수퍼 관리자");

    for (int i = 0; i < 15; i++) {
      String padding = StringUtil.padding(i + 1, 3);

      managerIdList.add(testService.createManager("manager" + padding, "관리자 " + padding));
      accountIdList.add(testService.createAccount("user" + padding, "사용자 " + padding));
    }
  }

  /**
   * 공통 구성
   */
  private void setCommon() {
    for (int i = 0; i < 15; i++) {
      String padding = StringUtil.padding(i + 1, 3);

      attachIdList.add(testService.createAttach("filename" + padding, "temp"));
    }
  }

  /**
   * 커뮤니티 구성
   */
  private void setCommunity() {
    // 게시글 생성 - 사용자마다 생성
    accountIdList.forEach(accountId -> {
      Account account = testService.getAccount(accountId);

      for (int i = 0; i < 15; i++) {
        String padding = StringUtil.padding(i + 1, 3);
        long id = testService.createBoard("title " + padding, "content " + padding, account);
        Board board = testService.getBoard(id);

        // 게시글 첨부 파일 생성
        for (int j = 0; j < 3; j++) {
          String paddingJ = StringUtil.padding(j + 1, 3);
          long attachId = testService.createAttach("filename" + paddingJ, "board");
          Attach attach = testService.getAttach(attachId);

          testService.createBoardAttachMapping(board, attach, j);
        }

        boardIdList.add(id);
      }
    });
  }

}
