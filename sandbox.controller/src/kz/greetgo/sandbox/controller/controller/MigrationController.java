package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.controller.util.Controller;

@SuppressWarnings("WeakerAccess")
@Bean
@ControllerPrefix("/migrate")
public class MigrationController implements Controller {
  public BeanGetter<MigrationRegister> migrationRegister;

  @OnGet("/")
  public void migrate() throws Exception {
    // Find all files
    // And alternatively give to register(cia and frs)

    String currentFileType = "";
    String currentFileLink = "";

    migrationRegister.get().migrate(currentFileType ,currentFileLink);
  }
}
