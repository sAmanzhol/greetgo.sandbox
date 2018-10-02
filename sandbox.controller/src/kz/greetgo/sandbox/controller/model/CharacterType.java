package kz.greetgo.sandbox.controller.model;

public enum CharacterType {
  OPENNESS("открытый"),
  CONSCIENTIOUSNESS("добросовестный"),
  EXTRAVERSION("экстраверт"),
  AGREEABLENESS("приятный"),
  NEUROTICISM("нервный");

  public String name;

  CharacterType(String name) {
    this.name = name;
  }

  public static CharacterType parseOrNull(String strEnum) {
    try {
      return CharacterType.valueOf(strEnum);
    } catch (Exception e) {
      return null;
    }
  }
}
