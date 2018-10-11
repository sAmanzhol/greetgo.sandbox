package kz.greetgo.sandbox.register.dao;

import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.sandbox.controller.model.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountDao {

    @Select("insert into client_account (id,client,money,number,registered_at,actual)" +
            " VALUES (DEFAULT,#{account.client}," +
            "#{account.money},#{account.number},#{account.registered_at},true) " +
            "RETURNING id")
    Long insert(@Param("account") Account account);

    @Select("Select * from client_account where id = #{id} and actual = true")
    Account getById(Long id);

    @Select("Select * from client_account where client = #{client_id} and actual = true")
    List<Account> getByClientId(Long client_id);

   //TODO
    Account update(Account account);

    @Select("update client_account set actual = false where id = #{id} and actual = true")
    void delete(Long id);

    @Select("update client_account set actual = false where id = #{id} and client = #{client_id} and actual = true")
    void deleteByClientId(Long client_id);
}
