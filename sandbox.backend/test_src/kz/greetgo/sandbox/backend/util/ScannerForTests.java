package kz.greetgo.sandbox.backend.util;

import kz.greetgo.sandbox.backend.service.impl.ScannerService;
import org.springframework.context.annotation.Import;

@Import(ScannerService.class)
public class ScannerForTests {}
