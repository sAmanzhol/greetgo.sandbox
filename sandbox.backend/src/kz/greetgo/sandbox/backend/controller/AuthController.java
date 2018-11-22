package kz.greetgo.sandbox.backend.controller;

import kz.greetgo.sandbox.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @GetMapping("/probe")
  public String probe() {
    System.out.println("Hello from " + getClass() + ", at now " + new Date());
    return authService.probe();
  }

  @PostMapping("/login")
  public String login(@RequestParam("username") String username,
                      @RequestParam("password") String password) {

    System.out.println("LOGIN " + username + " with " + password);

    return "asd";
  }

}
