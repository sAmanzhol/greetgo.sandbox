package kz.greetgo.sandbox.server.app;

import kz.greetgo.sandbox.backend.util.ScannerBackend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ScannerBackend.class)
public class SandboxApplication {
  public static void main(String[] args) {
    SpringApplication.run(SandboxApplication.class, args);
  }
}
