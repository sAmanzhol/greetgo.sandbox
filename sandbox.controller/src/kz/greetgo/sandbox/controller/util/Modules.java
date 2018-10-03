///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

  private static Path findGreetgoProjectNamePath() {
    Path markerPath = Paths.get(".greetgo", "project-name.txt");
    {
      Path point = Paths.get(".");
      if (Files.exists(point.resolve(markerPath))) {
        return point;
      }
    }

    {
      Path points = Paths.get("..");
      if (Files.exists(points.resolve(markerPath))) {
        return points;
      }

      for (int i = 0; i < 7; i++) {
        points = points.resolve("..");

        if (Files.exists(points.resolve(markerPath))) {
          return points;
        }
      }
    }

    throw new RuntimeException("Cannot find greetgo/project-name.txt" +
      " from " + new File(".").getAbsoluteFile().toPath().normalize());
  }

  private static File getDir(String moduleName) {

    Path modulePath = findGreetgoProjectNamePath().resolve(moduleName);

    if (Files.isDirectory(modulePath)) {
      return modulePath.toFile();
    }

    throw new IllegalArgumentException("Cannot find directory " + moduleName
      + " from " + new File(".").getAbsoluteFile().toPath().normalize());
  }

  public static File clientDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.client");
  }

  public static File registerDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.register");
  }

  public static File debugDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.server/debug");
  }

  public static File controllerDir() {
///MODIFY replace sandbox {PROJECT_NAME}
    return getDir("sandbox.controller");
  }
}
