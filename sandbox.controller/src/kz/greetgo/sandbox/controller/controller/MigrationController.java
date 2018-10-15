package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Bean
@ControllerPrefix("/migrate")
public class MigrationController implements Controller {
  public BeanGetter<MigrationRegister> migrationRegister;

  @OnGet("")
  public void migrate() throws Exception {

    String currentFileType = "";
    String currentFileLink = "";

    // Find all files
    // And alternatively give to register(cia and frs)

    // Temporary files in local directory
    File migrationFolder = new File("/home/ssailaubayev/migration");
    ArrayList<File> ciaFiles =  new ArrayList<>(Arrays.asList(migrationFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".xml"))));
    ArrayList<File> frsFiles =  new ArrayList<>(Arrays.asList(migrationFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".json_row"))));


    while (ciaFiles.size() > 0 || frsFiles.size() > 0) {
//      if (ciaFiles.size() > 0) {
//        currentFileLink = ciaFiles.get(0).getPath();
//        currentFileType = "cia";
//
//        migrationRegister.get().migrate(currentFileType ,currentFileLink);
//      } else {
//        currentFileLink = frsFiles.get(0).getPath();
//        currentFileType = "frs";
//
//        migrationRegister.get().migrate(currentFileType ,currentFileLink);
//      }

      if (frsFiles.size() > 0) {
        currentFileLink = frsFiles.get(0).getPath();
        currentFileType = "frs";

        migrationRegister.get().migrate(currentFileType ,currentFileLink);
      } else {
        currentFileLink = ciaFiles.get(0).getPath();
        currentFileType = "frs";

        migrationRegister.get().migrate(currentFileType ,currentFileLink);
      }
    }
  }
}
