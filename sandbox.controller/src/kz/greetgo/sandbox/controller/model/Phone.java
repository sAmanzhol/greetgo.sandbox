package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/5/18.
 */
public class Phone {
    public PhoneDetail phoneDetail;
    public String number;

    public Phone() {
    }

    public Phone(PhoneDetail phoneDetail, String number) {
        this.phoneDetail = phoneDetail;
        this.number = number;
    }
}
