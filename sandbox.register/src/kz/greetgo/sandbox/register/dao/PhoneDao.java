package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Phone;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PhoneDao {


    @Select("select * from client_phone where actual = true")
    List<Phone> list();

    @Select("insert into client_phone(id,client,number,type,actual) VALUES (DEFAULT,#{phone.clientId},#{phone.number},#{phone.type}::phone_type,true) " +
            "RETURNING id")
    Long insert(@Param("phone") Phone phone);

    @Select("select * from client_phone where id = #{id} and actual = true")
    Phone load(Long id);

    @Select("select * from client_phone where client = clientId and actual = true")
    List<Phone>  loadByClientId(Long client_id);

    @Delete("update client_phone set actual = false where id = #{id} and actual = true")
    void delete(Long id);

    @Delete("update client_phone set actual = false where client = #{client_id} and actual = true")
    void deleteByClientId(Long client_id);

    @Select("update client_phone set client = #{phone.clientId},number = #{phone.number},type = #{phone.type}::phone_type WHERE id = #{phone.id} and actual = true RETURNING *")
    Phone update(@Param("phone") Phone phone);
}
