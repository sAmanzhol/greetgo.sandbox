package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/5/18.
 */
public class Phone {
    public PhoneType phoneType;
    public String phoneTypeRuss;
    public String number;

    public Phone(PhoneType phoneType, String phoneTypeRuss, String number) {
        this.phoneType = phoneType;
        this.phoneTypeRuss = phoneTypeRuss;
        this.number = number;
    }
}
