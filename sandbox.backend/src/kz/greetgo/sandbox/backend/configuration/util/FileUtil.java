package kz.greetgo.sandbox.backend.configuration.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileUtil {
  public static String pathToStr(Path path) {
    try {
      return new String(Files.readAllBytes(path), UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
