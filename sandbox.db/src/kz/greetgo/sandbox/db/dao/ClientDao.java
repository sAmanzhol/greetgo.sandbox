package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.controller.model.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClientDao {

    @Select("select * from charm where actually = true order by energy asc")
    List<Charm> listCharm();

    @Delete("delete from client where id = #{id}")
    void deleteClientById(@Param("id") int id);

    @Select("select * from client_addr where client = #{id} and type =#{type}")
    ClientAddr selectClientAddrById(@Param("id") int id,
                                    @Param("type") AddrType type);

    @Select("select number from client_phone where client = #{id}")
    List<String> selectClientPhoneNumberById(@Param("id") int id);

    @Select("select * from client_phone where client = #{id} and type ='MOBILE' ")
    List<ClientPhone> selectClientPhoneByMobile(@Param("id") int id);

    @Select("select * from client_phone where client = #{id} and type != 'MOBILE' ")
    List<ClientPhone> selectClientPhoneByNotMobile(@Param("id") int id);

    @Select("select * from charm where id = #{id}")
    Charm getCharmById(@Param("id") int id);

    @Select("SELECT nextval('serial')")
    Integer selectNextValue();


    @Insert("insert into client (id, firstname, lastname, patronymic, gender, birth_date, charm) values " +
            "(#{clientToSave.id}, #{clientToSave.firstname}, #{clientToSave.lastname}, #{clientToSave.patronymic}, " +
            "#{clientToSave.gender}, #{clientToSave.dateOfBirth}, #{clientToSave.characterId}) " +
            "on conflict (id) do update set id =#{clientToSave.id}, firstname = #{clientToSave.firstname}, " +
            "lastname= #{clientToSave.lastname}, patronymic = #{clientToSave.patronymic}, " +
            "gender= #{clientToSave.gender}, birth_date = #{clientToSave.dateOfBirth}, " +
            "charm = #{clientToSave.characterId} ")
    void upsertClient(@Param("clientToSave") ClientToSave clientToSave);

    @Insert("insert into client_phone (client, number, type) values (#{client}, #{clientPhone.number}, #{clientPhone.type})" +
            " on conflict (client,number) do update set client =#{client}, number =#{clientPhone.number}, type = #{clientPhone.type}")
    void upsertClientPhone(@Param("clientPhone") ClientPhone clientPhone,
                           @Param("client") Integer client);


    @Insert("insert into client_addr (client, type, street, house, flat) values (#{client}, #{clientAddr.type}, " +
            "#{clientAddr.street}, #{clientAddr.house}, #{clientAddr.flat}) on conflict (client, type) do update set " +
            "client =#{client}, type =#{clientAddr.type}, street = #{clientAddr.street}, house= #{clientAddr.house}, flat = #{clientAddr.flat}")
    void upsertClientAddr(@Param("clientAddr") ClientAddr clientAddr,
                          @Param("client") Integer client
    );

    @Delete("delete from client_phone where client = #{id} and number = #{number} ")
    void deleteClientPhoneByIdandNumber(@Param("id") Integer id, @Param("number") String number);


    @Select("select\n" +
            "  c.id         as id,\n" +
            "  c.firstname  as firstname,\n" +
            "  c.lastname   as lastname,\n" +
            "  c.patronymic as patronymic,\n" +
            "  ch.name      as characterName,\n" +
            "  c.birth_date as date_of_birth,\n" +
            "  ca.maximumBalance,\n" +
            "  ca.minimumBalance,\n" +
            "  ca.totalAccountBalance\n" +
            "from client c left join charm ch on c.charm = ch.id\n" +
            "  left join (select\n" +
            "                   ,\n" +
            "               avg(money) as totalAccountBalance,\n" +
            "               max(money) as maximumBalance,\n" +
            "               min(money) as minimumBalance\n" +
            "             from client_account ca\n" +
            "             group by ca.client)\n" +
            "            ca on c.id = ca.client\n" +
            "where c.id = #{id}")
    ClientRecord selectClientRecord(@Param("id") Integer id);

    @Select("select id as id,\n" +
            "  firstname as firstname,\n" +
            "  lastname as lastname,\n" +
            "  patronymic as patronymic,\n" +
            "  gender as gender,\n" +
            "  charm as characterId,\n" +
            "  birth_date as date_of_birth from client where id=#{id}")
    ClientDetails selectClient(@Param("id") Integer id);


}
