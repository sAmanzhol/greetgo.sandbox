package kz.greetgo.sandbox.register.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface AuthTestDao {
  @Insert("insert into Person (id, username, encoded_password, blocked) " +
    "values (#{id}, #{username}, #{encodedPassword}, 0)")
  void insertPerson(@Param("id") String id,
                    @Param("username") String username,
                    @Param("encodedPassword") String encodedPassword
  );

  @Update("update Person set ${fieldName} = #{fieldValue} where id = #{id}")
  void updatePersonField(@Param("id") String id,
                         @Param("fieldName") String fieldName,
                         @Param("fieldValue") Object fieldValue);
}
