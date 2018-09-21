package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.sandbox.db.register_impl.calculator.Calculator;
import kz.greetgo.sandbox.db.register_impl.calculator.Calculator.Command;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.fest.assertions.api.Assertions;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.*;

public class CaclulatorTest extends ParentTestNg {
    private Calculator calculator;
    private final static int ZERO = 0;
    private int argument1;
    private int argument2;
    private int result;

    private void given_arguments(int argument1, int argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }
    private void when_command_is(Command command) {
        this.result = calculator.calculate(command, argument1, argument2);
    }

    private void then_result_is(int expectedValue) {
        Assert.assertThat(this.result, Is.is(expectedValue));
    }

    private void then_result_Exception(String expectedValue) {
        Assertions.fail(expectedValue);
    }

    @BeforeMethod
    public void setUp() {
        calculator = new Calculator();
    }

    @AfterMethod
    public void tearDown() {
        calculator = null;
    }

    @Test
    public void test_sum() {
        given_arguments(5, 5);
        when_command_is(Command.SUM);
        then_result_is(10);
    }

    @Test
    public void test_sum_abs() {
        given_arguments(5, -5);
        when_command_is(Command.SUM);
        then_result_is(10);
    }

    @Test
    public void test_sum1() {
        given_arguments(5, 4);
        when_command_is(Command.SUM);
        then_result_is(9);
    }

    @Test
    public void test_div() {
        given_arguments(10, 2);
        when_command_is(Command.DIV);
        then_result_is(5);
    }

    @Test
    public void test_div_abs() {
        given_arguments(10, -2);
        when_command_is(Command.DIV);
        then_result_is(5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test_div_on_zero() {
        given_arguments(10, ZERO);
        when_command_is(Command.DIV);
        then_result_Exception("DIVISION can not be zero");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void test_new_command() {
        given_arguments(10, ZERO);
        when_command_is(Command.SUBSTRACT);
        then_result_Exception("NOT know is that command");
    }
}
