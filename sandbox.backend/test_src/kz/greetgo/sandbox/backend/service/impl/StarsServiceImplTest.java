package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.StarsService;
import kz.greetgo.sandbox.backend.test.util.ParentTestNg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class StarsServiceImplTest extends ParentTestNg {

  @Autowired
  private StarsService starsService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void name() {

    jdbcTemplate.execute("select 1");

    assertThat(1);
  }
}