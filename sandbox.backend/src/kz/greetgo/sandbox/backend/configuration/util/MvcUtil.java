package kz.greetgo.sandbox.backend.configuration.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

import static kz.greetgo.sandbox.backend.configuration.util.StreamUtil.nullSafeToStream;

public class MvcUtil {
  public static Optional<String> extractCookieValue(HttpServletRequest request, String name) {
    return nullSafeToStream(request.getCookies())
        .filter(c -> Objects.equals(name, c.getName()))
        .map(Cookie::getValue)
        .filter(Objects::nonNull)
        .findAny();
  }
}
