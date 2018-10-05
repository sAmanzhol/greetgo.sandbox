package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressDao {

    @Select("select * from client_addr where actual = true")
    List<Address> list();

    @Select("select * from client_addr where client = #{client_id} and actual = true")
    List<Address> loadByClientId(Long client_id);

    @Select("insert into client_addr(id,client,type,street,house,flat,actual) VALUES (DEFAULT,#{address.clientId}," +
            "#{address.type}::addr_type,#{address.street},#{address.house},#{address.flat},true) " +
            "RETURNING id")
    Long insert(@Param("address") Address address);

    @Select("select * from client_addr where id = #{id} and actual = true")
    Address load(Long id);

    @Select("update client_addr set client = #{address.clientId}, type = #{address.type}::addr_type,street = #{address.street},house = #{address.house},flat = #{address.flat}  where id = #{address.id} RETURNING *")
    Address update(@Param("address") Address address);

    @Select("update client_addr set actual = false where id = #{id}")
    void delete(Long id);

    @Select("update client_addr set actual = false where client = #{client_id} and actual = true")
    void deleteByClientId(Long client_id);

}
