///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.util;

import java.io.File;

@SuppressWarnings("unused")
public class Modules {
  public static File parentDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    if (new File("sandbox.client").isDirectory()) {
      return new File(".");
    }

///MODIFY replace sandbox {PROJECT_NAME}
    if (new File("../sandbox.client").isDirectory()) {
      return new File("..");
    }

///MODIFY replace sandbox {PROJECT_NAME}
    throw new RuntimeException("Cannot find sandbox.root dir");
  }

  private static File findDir(String moduleName) {
    {
      File point = new File(".");
      if (point.getAbsoluteFile().getName().equals(moduleName)) {
        return point;
      }
    }

    {
      File dir = new File(moduleName);
      if (dir.isDirectory()
        && new File("build.gradle").isFile()
        && new File("settings.gradle").isFile()
        && new File("README.md").isFile()
        ) {
        return dir;
      }
    }

    {
      File dir = new File("../" + moduleName);
      if (dir.isDirectory()) return dir;
    }

    throw new IllegalArgumentException("Cannot find directory " + moduleName);
  }

  public static File clientDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return findDir("sandbox.client");
  }

  public static File registerDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return findDir("sandbox.register");
  }

  public static File standDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return findDir("sandbox.debug");
  }

  public static File controllerDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return findDir("sandbox.controller");
  }
}
