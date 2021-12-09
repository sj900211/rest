package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriDocsDepth1;
import static run.freshr.common.config.URIConfig.uriDocsDepth2;
import static run.freshr.common.config.URIConfig.uriDocsDepth3;
import static run.freshr.common.config.URIConfig.uriDocsIndex;
import static run.freshr.common.util.RestUtil.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
public class DocsController {

  private final String DOCS = "docs";

  @GetMapping(uriDocsIndex)
  public ModelAndView viewDocs(ModelAndView mav) {
    log.info("DocsController.viewDocs");

    mav.setView(new RedirectView("/" + DOCS + "/index", true));

    return mav;
  }

  @GetMapping(uriDocsDepth1)
  public ModelAndView viewDocs(@PathVariable String depth1, ModelAndView mav) {
    log.info("DocsController.viewDocs");

    return view(mav, DOCS, depth1);
  }

  @GetMapping(uriDocsDepth2)
  public ModelAndView viewDocs(@PathVariable String depth1, @PathVariable String depth2,
      ModelAndView mav) {
    log.info("DocsController.viewDocs");

    return view(mav, DOCS, depth1, depth2);
  }

  @GetMapping(uriDocsDepth3)
  public ModelAndView viewDocs(@PathVariable String depth1, @PathVariable String depth2,
      @PathVariable String depth3, ModelAndView mav) {
    log.info("DocsController.viewDocs");

    return view(mav, DOCS, depth1, depth2, depth3);
  }

}
