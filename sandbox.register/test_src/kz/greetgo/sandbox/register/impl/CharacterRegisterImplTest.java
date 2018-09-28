package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.CharacterDisplay;
import kz.greetgo.sandbox.controller.register.CharacterRegister;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CharacterRegisterImplTest extends ParentTestNg {

  public BeanGetter<CharacterRegister> characterRegister;

  public BeanGetter<CharacterTestDao> characterTestDao;

  @Test
  public void create_test() {
    String name = RND.str(255);
    String description = RND.str(500);
    int energy = RND.plusInt(2342);

    characterTestDao.get().insertCharacter(name, description, energy);

    CharacterDisplay characterDisplay = characterTestDao.get().getLastCharacter();

    assertThat(characterDisplay.id).isNotNull();
    assertThat(characterDisplay.name).isEqualTo(name);
    assertThat(characterDisplay.description).isEqualTo(description);
    assertThat(characterDisplay.energy).isEqualTo(energy);
  }


}
