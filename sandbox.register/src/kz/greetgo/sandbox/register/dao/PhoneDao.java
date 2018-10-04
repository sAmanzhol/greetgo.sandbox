package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Phone;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PhoneDao {


    @Select("select * from client_phone")
    List<Phone> list();

    @Select("insert into client_phone(id,client,number,type) VALUES (DEFAULT,#{phone.clientId},#{phone.number},#{phone.type}::phone_type) " +
            "RETURNING id")
    Long insert(@Param("phone") Phone phone);

    @Select("select * from client_phone where id = #{id}")
    Phone load(Long id);

    @Select("select * from client_phone where client = clientId")
    List<Phone>  loadByClientId(Long client_id);

    @Delete("delete from client_phone where id = #{id}")
    void delete(Long id);

    @Delete("delete from client_phone where client = #{client_id}")
    void deleteByClientId(Long client_id);

    @Select("update client_phone set client = #{phone.clientId},number = #{phone.number},type = #{phone.type}::phone_type WHERE id = #{phone.id} RETURNING *")
    Phone update(@Param("phone") Phone phone);
}
