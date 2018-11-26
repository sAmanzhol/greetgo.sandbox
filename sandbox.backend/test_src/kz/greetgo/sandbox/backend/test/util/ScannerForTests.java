package kz.greetgo.sandbox.backend.test.util;

import kz.greetgo.sandbox.backend.configuration.util.BeanScannerBackend;
import kz.greetgo.sandbox.backend.test.beans.BeanScannerTestBeans;
import kz.greetgo.sandbox.backend.test.dao.BeanScannerTestDao;
import org.springframework.context.annotation.Import;

@Import({
    BeanScannerBackend.class,
    BeanScannerTestBeans.class,
    BeanScannerTestDao.class,
})
public class ScannerForTests {}
