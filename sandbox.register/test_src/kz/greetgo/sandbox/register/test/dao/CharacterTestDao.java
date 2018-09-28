package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.CharacterDisplay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CharacterTestDao {
  @Insert("insert into Charm (name, description, energy) " +
    "values (#{name}, #{description}, #{energy})")
  void insertCharacter(@Param("name") String name,
                       @Param("description") String description,
                       @Param("energy") float energy);

  @Select("select * from Charm " +
    "order by id desc limit 1")
  CharacterDisplay getLastCharacter();
}
