package run.freshr.controller;

import run.freshr.common.config.URIConfig;
import run.freshr.common.util.RestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The Class Docs controller.
 *
 * @author [류성재]
 * @implNote 문서 관리
 * @since 2021. 3. 16. 오후 12:18:20
 */
@RestController
public class DocsController {

  /**
   * View docs model and view.
   *
   * @param mav the mav
   * @return the model and view
   * @author [류성재]
   * @implNote 요청 URI 가 없으면 index 페이지로 이동
   * @since 2021. 3. 16. 오후 12:18:20
   */
  @GetMapping(URIConfig.uriDocsIndex)
  public ModelAndView viewDocs(ModelAndView mav) {
    mav.setView(new RedirectView("/docs/index", true));

    return mav;
  }

  /**
   * View docs model and view.
   *
   * @param depth1 the depth 1
   * @param mav    the mav
   * @return the model and view
   * @author [류성재]
   * @implNote 요청 URI 정보에 따라 API 정의 문서로 연결
   * @since 2021. 3. 16. 오후 12:18:20
   */
  @GetMapping(URIConfig.uriDocsDepth1)
  public ModelAndView viewDocs(@PathVariable String depth1, ModelAndView mav) {
    return RestUtil.view(mav, "docs", depth1);
  }

  /**
   * View docs model and view.
   *
   * @param depth1 the depth 1
   * @param depth2 the depth 2
   * @param mav    the mav
   * @return the model and view
   * @author [류성재]
   * @implNote 요청 URI 정보에 따라 API 정의 문서로 연결
   * @since 2021. 3. 16. 오후 12:18:20
   */
  @GetMapping(URIConfig.uriDocsDepth2)
  public ModelAndView viewDocs(@PathVariable String depth1, @PathVariable String depth2,
      ModelAndView mav) {
    return RestUtil.view(mav, "docs", depth1, depth2);
  }

  /**
   * View docs model and view.
   *
   * @param depth1 the depth 1
   * @param depth2 the depth 2
   * @param depth3 the depth 3
   * @param mav    the mav
   * @return the model and view
   * @author [류성재]
   * @implNote 요청 URI 정보에 따라 API 정의 문서로 연결
   * @since 2021. 3. 16. 오후 12:18:20
   */
  @GetMapping(URIConfig.uriDocsDepth3)
  public ModelAndView viewDocs(@PathVariable String depth1, @PathVariable String depth2,
      @PathVariable String depth3, ModelAndView mav) {
    return RestUtil.view(mav, "docs", depth1, depth2, depth3);
  }

}
