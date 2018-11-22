package kz.greetgo.sandbox.backend.configuration.util;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamUtil {
  public static <T> Stream<T> nullSafeToStream(T[] array) {
    if (array == null) {
      return Stream.empty();
    }
    return Arrays.stream(array);
  }
}
