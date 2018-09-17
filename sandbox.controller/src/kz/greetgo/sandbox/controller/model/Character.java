package kz.greetgo.sandbox.controller.model;

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
