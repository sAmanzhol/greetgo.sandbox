package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.CharacterRecord;
import kz.greetgo.sandbox.controller.register.CharacterRegister;
import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class CharacterRegisterImplTest extends ParentTestNg {

  public BeanGetter<CharacterRegister> characterRegister;

  public BeanGetter<CharacterTestDao> characterTestDao;

  private Character insertCharacterTest(int id, String name, String description, float energy) {
    Character character = new Character();
    character.id = id;
    character.name = name;
    character.description = description;
    character.energy = energy;

    characterTestDao.get().insertCharacter(character);
    return character;
  }

  @Test
  private void list() {
    characterTestDao.get().removeAll();

    Character charm1 = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
    Character charm2 = insertCharacterTest(102, "Характерный", "Характерный Характерный", 90);
    Character charm3 = insertCharacterTest(103, "Самовлюблённый3", "Самовлюблённый Самовлюблённый", 80);
    Character charm4 = insertCharacterTest(104, "Самовлюблённый4", "Самовлюблённый Самовлюблённый", 70);
    Character charm5 = insertCharacterTest(105, "Самовлюблённый5", "Самовлюблённый Самовлюблённый", 60);

    //
    //
    List<CharacterRecord> characters = characterRegister.get().list();
    //
    //

    assertThat(characters).hasSize(5);

    assertThat(characters.get(0).name).isEqualTo(charm1.name);

    assertThat(characters.get(0).id).isEqualTo(charm1.id);
    assertThat(characters.get(1).id).isEqualTo(charm2.id);
    assertThat(characters.get(2).id).isEqualTo(charm3.id);
    assertThat(characters.get(3).id).isEqualTo(charm4.id);
    assertThat(characters.get(4).id).isEqualTo(charm5.id);
  }
}
