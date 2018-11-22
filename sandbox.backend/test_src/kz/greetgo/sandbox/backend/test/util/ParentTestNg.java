package kz.greetgo.sandbox.backend.test.util;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = {ScannerForTests.class})
public abstract class ParentTestNg extends AbstractTestNGSpringContextTests {}
