package kz.greetgo.sandbox.backend.test.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Modules {
  public static Path backend() {
    return root().resolve("sandbox.backend");
  }

  private static Path root() {

    for (Path path : Arrays.stream(new String[]{
        "",
        "..",
        "../..",
        "../../..",
        "../../../..",
        "../../../../..",
        "../../../../../..",
        "../../../../../../..",
    }).map(Paths::get).collect(Collectors.toList())) {

      if (Files.isRegularFile(path.resolve(".greetgo").resolve("project-name.txt"))) {
        return path;
      }

    }

    throw new RuntimeException("Cannot find root of project from dir: "
        + new File(".").getAbsoluteFile().toPath().normalize());
  }

  public static void main(String[] args) {
    System.out.println("Modules.backend() = " + Modules.backend());
  }
}
