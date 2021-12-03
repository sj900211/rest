package run.freshr.domain.auth.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.annotation.SearchClass;
import run.freshr.common.extension.SearchExtension;

@EqualsAndHashCode(callSuper = true)
@Data
@SearchClass
public class AuthSearch extends SearchExtension {

}
