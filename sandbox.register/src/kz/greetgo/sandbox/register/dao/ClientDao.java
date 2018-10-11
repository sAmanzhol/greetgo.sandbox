package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface ClientDao {

    @Select("select c.id,c.surname,c.name,c.patronymic,c.gender,c.birth_date,c.charm as charm_id, ch.name as charm_name," +
            "ch.description,ch.energy" +
            ",(SELECT max(acc.money) as maxbal " +
            "    from client_account acc where acc.client = c.id) " +
            ",(SELECT min(acc.money) as minbal " +
            "    from client_account acc where acc.client = c.id) " +
            ",(SELECT sum(acc.money) as sumbal " +
            "   from client_account acc where acc.client = c.id) " +
            "from clients c,charms ch "+
            "where c.id = #{id} and c.actual = true and c.charm = ch.id limit 1")
    @ConstructorArgs({
            @Arg(column="id", javaType=Long.class),
            @Arg(column="surname", javaType=String.class),
            @Arg(column="name", javaType=String.class),
            @Arg(column="patronymic", javaType=String.class),
            @Arg(column="gender", javaType=Object.class),
            @Arg(column="birth_date", javaType=Timestamp.class),
            @Arg(column="charm_id", javaType=Long.class),
            @Arg(column="charm_name", javaType=String.class),
            @Arg(column="description", javaType=String.class),
            @Arg(column="energy", javaType=Double.class),
            @Arg(column ="maxbal", javaType = Double.class),
            @Arg(column = "minbal",javaType = Double.class),
            @Arg(column = "sumbal",javaType = Double.class)
    })
    Client load(@Param("id") Long id);

    @Select("update clients set name = #{client.name},surname = #{client.surname},patronymic = #{client.patronymic}," +
            "gender = #{client.gender}::gender," +
            "birth_date = #{client.birthDate}, charm = #{client.charm.id}" +
            "where id = #{client.id} and actual = true RETURNING *")
    Client update(@Param("client") Client client);

    @Select("select count(*) from clients where actual = true")
    Long getCount();

    @Select("update clients set actual = false where id = #{id} and actual = true")
    void delete(Long id);

}
