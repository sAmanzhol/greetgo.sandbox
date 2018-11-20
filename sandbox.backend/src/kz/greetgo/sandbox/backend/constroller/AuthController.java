package kz.greetgo.sandbox.backend.constroller;

import kz.greetgo.sandbox.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
  @Autowired
  private AuthService authService;

  @GetMapping("/probe")
  public String probe() {
    return authService.probe();
  }

}
