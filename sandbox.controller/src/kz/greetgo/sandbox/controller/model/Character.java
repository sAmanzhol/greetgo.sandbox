package kz.greetgo.sandbox.controller.model;

/**
 * Created by msultanova on 9/7/18.
 */
public class Character {
    public CharacterType characterType;
    public String characterTypeRuss;

    public Character(CharacterType characterType, String characterTypeRuss) {
        this.characterType = characterType;
        this.characterTypeRuss = characterTypeRuss;
    }

    public Character() {
    }
}
