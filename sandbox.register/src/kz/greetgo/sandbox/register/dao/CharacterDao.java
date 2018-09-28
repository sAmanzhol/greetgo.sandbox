package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.CharacterRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CharacterDao {
  @Select("select * from charm")
  List<CharacterRecord> list();
}
