package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.register.model.PersonLogin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AuthDao {
  @Select("select * from person where username = #{username} and blocked = 0")
  PersonLogin selectByUsername(@Param("username") String username);
}
