package kz.greetgo.sandbox.backend.configuration.util;

import kz.greetgo.sandbox.backend.configuration.beans.BeanScannerConfiguration;
import kz.greetgo.sandbox.backend.controller.BeanScannerController;
import kz.greetgo.sandbox.backend.dao.BeanScannerDao;
import kz.greetgo.sandbox.backend.service.impl.BeanScannerService;
import org.springframework.context.annotation.Import;

@Import({
    BeanScannerService.class,
    BeanScannerController.class,
    BeanScannerConfiguration.class,
    BeanScannerDao.class,
})
public class BeanScannerBackend {}
