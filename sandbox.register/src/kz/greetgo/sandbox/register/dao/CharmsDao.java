package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface CharmsDao {

    @Select("select * from charms")
    List<Charm> list();

    @Select("select * from charms where id = #{id}")
    Charm load(@Param("id") Long id);

    /*
    * Вы спросите почему Select? Я отвечу патамушта/
    * А если серьезно, аннотация @Insert не умеет возвращать значение из Returning
    * */
    @Select("insert into charms(id,name,description,energy) VALUES(DEFAULT,#{charm.name}, #{charm.description}, #{charm.energy}) RETURNING id")
    Long insert(@Param("charm") Charm charm);
}
