package kz.greetgo.sandbox.controller.model;

public enum CharacterType {
  OPENNESS("открытый"),
  CONSCIENTIOUSNESS("любзеный"),
  EXTRAVERSION("добросовестный"),
  AGREEABLENESS("экстраверт"),
  NEUROTICISM("невротичный");

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
