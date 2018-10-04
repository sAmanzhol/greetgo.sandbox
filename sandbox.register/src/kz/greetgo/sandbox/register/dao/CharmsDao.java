package kz.greetgo.sandbox.register.dao;
import kz.greetgo.sandbox.controller.model.Charm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * Вы спросите почему Select? Я отвечу патамушта/
 * А если серьезно, аннотация @Insert не умеет возвращать значение из Returning
 * */

public interface CharmsDao {

    @Select("select * from charms")
    List<Charm> list();

    @Select("select * from charms where id = #{id}")
    Charm load(@Param("id") Long id);

    @Select("insert into charms(id,name,description,energy) VALUES(DEFAULT,#{charm.name}, #{charm.description}, #{charm.energy}) RETURNING id")
    Long insert(@Param("charm") Charm charm);

    @Select("update charms set name = #{charm.name},description = #{charm.description},energy = #{charm.energy} where id = #{charm.id} RETURNING *")
    Charm update(@Param("charm") Charm charm);

    @Delete("delete from charms where id = #{id}")
    void delete(Long id);

}
