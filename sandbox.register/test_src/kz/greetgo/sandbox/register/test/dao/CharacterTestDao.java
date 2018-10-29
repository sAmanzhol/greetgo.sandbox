package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.register.dao_model.Character;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface CharacterTestDao {
  @Insert("insert into Charm (id, name, description, energy) " +
    "values (nextval('id'), #{name}, #{description}, #{energy}) " +
    "on conflict (name) do update set actual = 1")
  void insertCharacter(Character character);

  @Update("" +
    "update Charm set actual=0 where actual=1;")
  void removeAll();
}
