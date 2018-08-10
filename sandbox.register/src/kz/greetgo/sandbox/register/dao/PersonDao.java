package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.PersonRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PersonDao {
  @Select("select id," +
    "   surname||' '||substring(name from 1 for 1)||'. '||substring(patronymic from 1 for 1)||'.' as fio," +
    "   username," +
    "   to_char(birth_date, 'YYYY-MM-DD') as birthDate" +
    " from person where blocked = 0" +
    " order by surname, name")
  List<PersonRecord> list();
}
