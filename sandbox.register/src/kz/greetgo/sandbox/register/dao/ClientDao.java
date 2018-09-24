package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.model.db.ClientAddrDb;
import kz.greetgo.sandbox.controller.model.db.ClientDb;
import kz.greetgo.sandbox.controller.model.db.ClientPhoneDb;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClientDao {


  @Select("select id, surname, name, patronymic, gender, birth_date, charm from client where id=#{id}")
  ClientDb getClientDb(@Param("id") int id);

  @Select("select client, type, street, house, flat from client_addr where client=#{client} and type = #{type}")
  ClientAddrDb getClientAddrDb(@Param("id") int id,
                               @Param("type") String type);
//  @Insert("insert into ")

  @Select("select client, number, type from client_phone where client=#{client} and number=#{number}")
  ClientPhoneDb getClientPhoneDb(@Param("client") int client,
                                 @Param("number") String number);

  @Select("select distinct name from charm")
  List<String> getCharacters();

  @Select("select distinct gender from client")
  List<String> getGenders();

  @Select("select distinct type\n" +
    "from client_phone")
  List<String> getPhoneD();

  @Select("select charm.name from charm join client c on charm.id = c.charm and c.id = #{id}")
  String getCharm(@Param("id") int id);

  @Select("select addr.street, addr.house, addr.flat " +
    "from client_addr addr " +
    "join client c on addr.client = c.id and addr.type = #{type} and c.id = #{id}")
  Address getAddress(@Param("id") int id,
                     @Param("type") String type);

  @Select("select * from client_phone where client = #{id}")
  List<ClientPhoneDb> phoneList(@Param("id") int id);
}
