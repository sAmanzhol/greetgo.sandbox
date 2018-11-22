package kz.greetgo.sandbox.backend.util;

import kz.greetgo.sandbox.backend.controller.ScannerController;
import kz.greetgo.sandbox.backend.security.beans.ScannerSecurity;
import kz.greetgo.sandbox.backend.service.impl.ScannerService;
import kz.greetgo.sandbox.backend.spring_configuration.ScannerSpringConfiguration;
import org.springframework.context.annotation.Import;

@Import({
    ScannerService.class,
    ScannerController.class,
    ScannerSecurity.class,
    ScannerSpringConfiguration.class,
})
public class ScannerBackend {}
