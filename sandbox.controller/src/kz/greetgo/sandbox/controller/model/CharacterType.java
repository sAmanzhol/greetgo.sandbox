package kz.greetgo.sandbox.controller.model;

public enum CharacterType {
  OPENNESS,
  CONSCIENTIOUSNESS,
  EXTRAVERSION,
  AGREEABLENESS,
  NEUROTICISM;


  public static CharacterType parseOrNull(String strEnum) {
    try {
      return CharacterType.valueOf(strEnum);
    } catch (Exception e) {
      return null;
    }
  }
}
