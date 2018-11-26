package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.StarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StarsServiceImpl implements StarsService {


  @Override
  public String hello() {
    return "Hello From Aldebaran : " + new Date();
  }

  @Autowired
  private JdbcTemplate jdbcTemplate;


}
