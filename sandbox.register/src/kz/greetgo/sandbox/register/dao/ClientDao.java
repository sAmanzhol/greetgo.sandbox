package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ClientDao {
    @Select("select * from client where id=#{id}")
    ClientRecord selectClientById(@Param("id") int id);

    @Update("update client set ${fieldName} = #{fieldValue} where id = #{id}")
    void updateClientField(@Param("id") int id,
                           @Param("fieldName") String fieldName,
                           @Param("fieldValue") Object fieldValue);

    @Select("update client set actual=0 where id=#{id}")
    void deleteClient(@Param("id") int id);

    @Insert("insert into client (id, surname, name, actual) " +
            "values (#{id}, #{surname}, #{name}, #{actual})")
    void addClient(@Param("id") int id,
                   @Param("surname") String surname,
                   @Param("name") String name,
                   @Param("actual") int actual
    );
}
