package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignChangePasswordRequest {

  @NotEmpty
  private String rsa;

  @NotEmpty
  private String originPassword;

  @NotEmpty
  private String password;

}
