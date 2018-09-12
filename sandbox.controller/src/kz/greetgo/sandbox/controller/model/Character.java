package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/7/18.
 */
public class Character {
    public CharacterType type;
    public String typeRuss;

    public Character(CharacterType type, String typeRuss) {
        this.type = type;
        this.typeRuss = typeRuss;
    }

    public Character() {
    }
}
