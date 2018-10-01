package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.register.dao_model.Character;
import org.apache.ibatis.annotations.Insert;

public interface CharacterTestDao {
  @Insert("insert into Charm (id, name, description, energy) " +
    "values (#{id}, #{name}, #{description}, #{energy}) " +
    "on conflict (id) do update set actual = 1;")
  void insertCharacter(Character character);


}
