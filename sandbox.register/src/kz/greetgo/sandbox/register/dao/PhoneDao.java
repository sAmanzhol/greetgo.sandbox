package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.PhoneTypeRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PhoneDao {
  @Select("select unnest(enum_range(null::phone))")
  List<PhoneTypeRecord> list();
}
