package kz.greetgo.sandbox.controller.model;

public class CharacterDisplay {
  public int id;
  public String name;
  public String description;
  public float energy;

  public CharacterDisplay() {

  }

  public CharacterDisplay(int id, String name, String description, float energy) {
    this.id = id;
    this.name= name;
    this.description = description;
    this.energy = energy;
  }
}
