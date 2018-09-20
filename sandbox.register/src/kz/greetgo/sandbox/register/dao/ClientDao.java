package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ClientDao {
    @Select("select * from client where id=#{id}")
    ClientRecord selectClientByID(@Param("id") int id);

    @Select("update client set actual=0 where id=#{id}")
    void deleteClientByID(@Param("id") int id);

    @Insert("insert into client (id, surname, name, patronymic, gender, birth_date, actual, charm) " +
            "values (#{id}, #{cd.surname}, #{cd.name}, #{cd.patronymic}, #{cd.gender}, " +
            "#{cd.birthDate}, #{cd.actual}, #{cd.charm})")
//charm: (select id from charm where name=#{cd.charm})
    void insertIntoClient(@Param("id") int id, @Param("cd") ClientDetail cd);

    @Update("update client set ${fieldName} = #{fieldValue} where id = #{id}")
    void updateClientField(@Param("id") int id,
                           @Param("fieldName") String fieldName,
                           @Param("fieldValue") Object fieldValue);

    @Insert("insert into client_addr (client, type, street, house, flat) " +
            "values (#{cd.id}, 'FACT', #{cd.factStreet}, #{cd.factNo}, #{cd.factFlat})")
    void insertIntoClientAddrFACT(@Param("cd") ClientDetail cd);

    @Update("update client_addr set ${fieldName} = #{fieldValue} where client = #{id} and type = 'FACT'")
    void updateClientAddrFACTField(@Param("id") int id,
                                   @Param("fieldName") String fieldName,
                                   @Param("fieldValue") String fieldValue);

    @Insert("insert into client_addr (client, type, street, house, flat) " +
            "values (#{cd.id}, 'REG', #{cd.regStreet}, #{cd.regNo}, #{cd.regFlat})")
    void insertIntoClientAddrREG(@Param("cd") ClientDetail cd);

    @Update("update client_addr set ${fieldName} = #{fieldValue} where client = #{id} and type = 'REG'")
    void updateClientAddrREGField(@Param("id") int id,
                                  @Param("fieldName") String fieldName,
                                  @Param("fieldValue") String fieldValue);

    @Insert("insert into client_phone (client, type, number) " +
            "values (#{client}, #{type}, #{number})")
    void insertIntoClientPhone(@Param("client") int client,
                               @Param("type") String type,
                               @Param("number") String number);

    @Update("update client_phone set number = #{number} where client = #{id} and type = #{type}")
    void updateClientPhoneNumber(@Param("id") int id,
                                 @Param("number") String number,
                                 @Param("type") String type);
}