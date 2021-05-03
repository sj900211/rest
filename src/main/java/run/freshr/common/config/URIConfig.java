package run.freshr.common.config;

/**
 * The Class Uri config.
 *
 * @author [류성재]
 * @implNote URI 설정
 * @since 2021. 2. 24. 오후 5:45:17
 */
public class URIConfig {

  public static final String uriH2All = "/h2/**"; // 로컬에서 H2 DB 를 확인하는 URI
  public static final String uriDocsAll = "/docs/**"; // API 문서 URI
  public static final String uriFavicon = "/favicon.ico"; // 파비콘 URL
  public static final String uriEnumAll = "/enum/**"; // ENUM 관련 URI
  public static final String uriFileAll = "/file/**"; // 첨부 파일 관련 URI
  public static final String uriEditorAll = "/editor/**"; // 에디터 파일 업로드 URI

  public static final String uriDocsIndex = "/docs";
  public static final String uriDocsDepth1 = "/docs/{depth1}";
  public static final String uriDocsDepth2 = "/docs/{depth1}/{depth2}";
  public static final String uriDocsDepth3 = "/docs/{depth1}/{depth2}/{depth3}";

  public static final String uriCommonHeartbeat = "/heartbeat"; // 서비스가 실행되었는지 체크하기 위한 URI
  public static final String uriCommonEnum = "/enum";
  public static final String uriCommonEnumPick = "/enum/{pick}";
  public static final String uriCommonAttach = "/file";
  public static final String uriCommonAttachId = "/file/{id}";
  public static final String uriCommonAttachIdDownload = "/file/{id}/download";
  public static final String uriCommonAttachExist = "/file/exist/{id}";
  public static final String uriCommonEditorCK = "/editor";

  public static final String uriAuth = "/auth";
  public static final String uriAuthToken = "/auth/token";
  public static final String uriAuthSignIn = "/auth/sign-in";
  public static final String uriAuthSignOut = "/auth/sign-out";
  public static final String uriAuthPassword = "/auth/password";
  public static final String uriAuthInfo = "/auth/info";

  public static final String uriSetting = "/setting";
  public static final String uriSettingManager = "/setting/manager";
  public static final String uriSettingManagerId = "/setting/manager/{id}";

  public static final String uriUser = "/user";
  public static final String uriUserId = "/user/{id}";

  public static final String uriCommunity = "/community";
  public static final String uriCommunityBoard = "/community/board";
  public static final String uriCommunityBoardId = "/community/board/{id}";
  public static final String uriCommunityBoardIdAttach = "/community/board/{id}/attach";

}
