package kz.greetgo.sandbox.backend.util;

import kz.greetgo.sandbox.backend.constroller.ScannerController;
import kz.greetgo.sandbox.backend.service.impl.ScannerService;
import org.springframework.context.annotation.Import;

@Import({ScannerService.class, ScannerController.class})
public class ScannerBackend {}
