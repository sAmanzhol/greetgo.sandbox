package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/7/18.
 */
public class Gender {
    public GenderType genderType;
    public String genderTypeRuss;

    public Gender(GenderType genderType, String genderTypeRuss) {
        this.genderType = genderType;
        this.genderTypeRuss = genderTypeRuss;
    }
}
