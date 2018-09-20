package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.ClientDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ClientTestDao {
  @Insert("insert into charm values (#{id}, #{name})")
  void insertCharm(@Param("id") int id,
                   @Param("name") String name);

  @Insert("insert into client (id, surname, name, gender, birth_date, charm) " +
    "values (#{id}, #{surname}, #{name}, #{gender}, #{birth_date}, #{charm})")
  void insertClient(@Param("id") int id,
                    @Param("surname") String surname,
                    @Param("name") String name,
                    @Param("gender") String gender,
                    @Param("birth_date") Object birth_date,
                    @Param("charm") int charm
  );

  @Insert("insert into client (id, surname, name, actual) " +
    "values (#{id}, #{surname}, #{name}, #{actual})")
  void insertNotFullClient(@Param("id") int id,
                    @Param("surname") String surname,
                    @Param("name") String name,
                    @Param("actual") int actual
  );

  @Update("update client set actual = 0")
  void deleteAllClients();

  @Select("select id, surname, name, patronymic, gender, birth_date as birthDate, actual, charm,\n" +
          "(select street from client_addr where client=a.client and type='FACT') as factStreet,\n" +
          "(select house from client_addr where client=a.client and type='FACT') as factNo,\n" +
          "(select flat from client_addr where client=a.client and type='FACT') as factFlat,\n" +
          "(select street from client_addr where client=a.client and type='REG') as regStreet,\n" +
          "(select house from client_addr where client=a.client and type='REG') as regNo,\n" +
          "(select flat from client_addr where client=a.client and type='REG') as regFlat,\n" +
          "(select number from client_phone where client=phone.client and type='HOME') as homePhoneNumber,\n" +
          "(select number from client_phone where client=phone.client and type='WORK') as workPhoneNumber,\n" +
          "(SELECT (array_agg(number))[1] FROM client_phone where type='MOBILE' and client=phone.client) as mobileNumber1,\n" +
          "(SELECT (array_agg(number))[2] FROM client_phone where type='MOBILE' and client=phone.client) as mobileNumber2,\n" +
          "(SELECT (array_agg(number))[3] FROM client_phone where type='MOBILE' and client=phone.client) as mobileNumber3\n" +
          "from client c left join client_addr a on c.id = a.client left join client_phone phone on c.id = phone.client\n" +
          "where c.id=#{id} and c.actual=1 limit 1")
  ClientDetail selectClientByID(@Param("id") int id);

  @Select("select id, surname, name, patronymic, gender, birth_date as birthDate, actual, charm,\n" +
          "(select street from client_addr where client=a.client and type='FACT') as factStreet,\n" +
          "(select house from client_addr where client=a.client and type='FACT') as factNo,\n" +
          "(select flat from client_addr where client=a.client and type='FACT') as factFlat,\n" +
          "(select street from client_addr where client=a.client and type='REG') as regStreet,\n" +
          "(select house from client_addr where client=a.client and type='REG') as regNo,\n" +
          "(select flat from client_addr where client=a.client and type='REG') as regFlat,\n" +
          "(select number from client_phone where client=phone.client and type='HOME') as homePhoneNumber,\n" +
          "(select number from client_phone where client=phone.client and type='WORK') as workPhoneNumber,\n" +
          "(SELECT (array_agg(number))[1] FROM client_phone where type='MOBILE' and client=phone.client) as mobileNumber1,\n" +
          "(SELECT (array_agg(number))[2] FROM client_phone where type='MOBILE' and client=phone.client) as mobileNumber2,\n" +
          "(SELECT (array_agg(number))[3] FROM client_phone where type='MOBILE' and client=phone.client) as mobileNumber3\n" +
          "from client c left join client_addr a on c.id = a.client left join client_phone phone on c.id = phone.client\n" +
          "where c.name=#{name} and c.actual=1 limit 1")
  ClientDetail selectClientByName(@Param("name") String name);
}
