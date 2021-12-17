package run.freshr.domain.blog.enumeration;

import static java.util.Arrays.stream;
import static run.freshr.common.functional.GlobalFunctional.searchKeyword;
import static run.freshr.domain.blog.entity.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.freshr.common.model.SearchEnumModel;

@Slf4j
public enum PostPageKeys implements SearchEnumModel {

  ALL("전체", List.of(post.title, post.contents, post.creator.username, post.creator.name)),
  TITLE("제목", List.of(post.title)),
  CONTENTS("내용", List.of(post.contents)),
  USERNAME("작성자 아이디", List.of(post.creator.username)),
  NAME("작성자 이름", List.of(post.creator.name));

  private final String value;
  private final List<StringPath> paths;

  PostPageKeys(String value, List<StringPath> paths) {
    this.value = value;
    this.paths = paths;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public List<StringPath> getPaths() {
    return paths;
  }

  @Override
  public BooleanBuilder search(String word) {
    return searchKeyword(word, paths);
  }

  public static PostPageKeys find(String key) {
    log.info("PostPageKeys.find");

    return stream(PostPageKeys.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(ALL);
  }

}
