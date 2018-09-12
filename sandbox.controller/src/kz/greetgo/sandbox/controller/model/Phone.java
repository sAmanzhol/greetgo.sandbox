package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/5/18.
 */
public class Phone {
    public PhoneDetail detail;
    public String number;

    public Phone() {
    }

    public Phone(PhoneDetail detail, String number) {
        this.detail = detail;
        this.number = number;
    }
}
