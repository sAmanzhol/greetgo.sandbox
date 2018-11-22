package kz.greetgo.sandbox.backend.test.util;

import kz.greetgo.sandbox.backend.test.beans.BeanScannerTestBeans;
import kz.greetgo.sandbox.backend.util.BeanScannerBackend;
import org.springframework.context.annotation.Import;

@Import({
    BeanScannerBackend.class,
    BeanScannerTestBeans.class,
})
public class ScannerForTests {}
