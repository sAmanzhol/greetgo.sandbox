package kz.greetgo.sandbox.backend.controller;

import kz.greetgo.sandbox.backend.service.StarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stars")
public class StarsController {

  @Autowired
  private StarsService starsService;

  @GetMapping("/hello")
  public String hello() {
    System.out.println("Called stars hello()");
    return starsService.hello();
  }
}
