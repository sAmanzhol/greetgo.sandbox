package kz.greetgo.sandbox.backend.test.util;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ContextConfiguration(classes = {ScannerForTests.class})
public abstract class ParentTestNg extends AbstractTestNGSpringContextTests {}
