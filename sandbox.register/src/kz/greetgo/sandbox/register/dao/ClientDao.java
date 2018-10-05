package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClientDao {

    @Select("select c.id,c.surname,c.name,c.patronymic,c.gender,c.birth_date,c.charm as charm_id, ch.name as charm_name,ch.description,ch.energy  from clients c,charms ch\n" +
            "where c.id = #{id} and c.actual = true and c.charm = ch.id limit 1")
    Client load(@Param("id") Long id);

    // TODO: 10/5/18
    @Select("update clients set name = #{charm.name},description = #{charm.description},energy = #{charm.energy} where id = #{charm.id} and actual = true RETURNING *")
    Client update(@Param("client") Client client);

    @Select("select count(*) from clients where actual = true")
    Long getCount();

    @Select("update clients set actual = false where id = #{id} and actual = true")
    void delete(Long id);

}
