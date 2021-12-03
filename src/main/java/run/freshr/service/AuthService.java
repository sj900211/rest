package run.freshr.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;

public interface AuthService {

  ResponseEntity<?> signIn(SignInRequest dto);

  ResponseEntity<?> signOut(HttpServletRequest request);

  ResponseEntity<?> updatePassword(SignChangePasswordRequest dto);

  ResponseEntity<?> refreshToken(HttpServletRequest request);

  ResponseEntity<?> getInfo();

  ResponseEntity<?> updateInfo(SignUpdateRequest dto);

  ResponseEntity<?> removeInfo();

}
