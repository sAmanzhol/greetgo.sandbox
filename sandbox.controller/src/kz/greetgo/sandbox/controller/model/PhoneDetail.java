package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/12/18.
 */
public class PhoneDetail {
    public PhoneType type;
    public String typeRuss;

    public PhoneDetail() {
    }

    public PhoneDetail(PhoneType type, String typeRuss) {
        this.type = type;
        this.typeRuss = typeRuss;
    }
}
