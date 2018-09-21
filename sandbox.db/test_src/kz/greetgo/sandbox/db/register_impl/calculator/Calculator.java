package kz.greetgo.sandbox.db.register_impl.calculator;

public class Calculator {

    public enum Command{
        SUM,
        DIV,
        SUBSTRACT,
    }
    public int calculate(Command command, int argument1, int argument2){
        int argAbs1 = Math.abs(argument1);
        int argAbs2 = Math.abs(argument2);
        switch (command){
            case SUM:
                return sum(argAbs1,argAbs2);
            case DIV:
                return div(argAbs1,argAbs2);
                default:
                    throw new UnsupportedOperationException();
        }

    }
    private Integer sum(int argument1, int argument2) {
//        return 0;
        return argument1 + argument2;
    }

    private Integer div(int divider1, int divider2) {
//        return 0;
        if (divider2 == 0) {
            throw new IllegalArgumentException("Divisor can not be zero");
        } else
            return divider1/divider2;
    }
}
