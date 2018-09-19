package kz.greetgo.sandbox.db.test.dao;

import kz.greetgo.sandbox.controller.model.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public interface ClientTestDao {


	@Insert("insert into charm (id, name, description, energy, actually) values (#{id}, #{name}, #{description}, #{energy}, #{actually})")
	void insertCharm(@Param("id") int id,
									 @Param("name") String name,
									 @Param("description") String description,
									 @Param("energy") float energy,
									 @Param("actually") boolean actually
	);

	@Insert("insert into transaction_type (id, code, name) values (#{id}, #{code}, #{name})")
	void insertTransaction_type(@Param("id") int id,
															@Param("code") String code,
															@Param("name") String name
	);

	@Insert("insert into client (id, firstname, lastname, patronymic, gender, birth_date, charm) values " +
		"(#{id}, #{firstname}, #{lastname}, #{patronymic}, #{gender}, #{birth_date}, #{charm})")
	void insertClient(@Param("id") int id,
										@Param("firstname") String firstname,
										@Param("lastname") String lastname,
										@Param("patronymic") String patronymic,
										@Param("gender") GenderType gender,
										@Param("birth_date") Date birth_date,
										@Param("charm") int charm
	);


	@Insert("insert into client_account_transaction (id, account, money, finished_at, type) values (#{id}, #{account}, #{money}, #{finished_at}, #{type}) ")
	void insertClientAccountTransaction(@Param("id") int id,
																			@Param("account") int account,
																			@Param("money") float money,
																			@Param("finished_at") Timestamp finished_at,
																			@Param("type") int type

	);

	@Insert("insert into client_account (id, client, money, number, registered_at) values (#{id}, #{client}, #{money}, #{number}, #{registered_at}) ")
	void insertClientAccount(@Param("id") int id,
													 @Param("client") int client,
													 @Param("money") float money,
													 @Param("number") String number,
													 @Param("registered_at") Timestamp registered_at
	);

	@Insert("insert into client_phone (client, number, type) values (#{client}, #{number}, #{type}) ")
	void insertClientPhone(@Param("client") int client,
												 @Param("number") String number,
												 @Param("type") PhoneType type
	);


	@Insert("insert into client_addr (client, type, street, house, flat) values (#{client}, #{type}, #{street}, #{house}, #{flat}) ")
	void insertClientAddr(@Param("client") int client,
												@Param("type") AddrType type,
												@Param("street") String street,
												@Param("house") String house,
												@Param("flat") String flat
	);

	@Delete("delete from client")
	void deleteAllClient();

	@Delete("delete from client_addr")
	void deleteAllClientAddr();

	@Delete("delete from client_phone")
	void deleteAllClientPhone();

	@Delete("delete from client_account")
	void deleteAllClientAccount();

	@Delete("delete from client_account_transaction")
	void deleteAllClientAccountTransaction();

	@Delete("delete from transaction_type")
	void deleteAllTransactionType();

	@Delete("delete from charm")
	void deleteAllCharm();

	@Delete("delete from client where id = #{id}")
	void deleteClientFromId(@Param("id") int id);


	@Select("select id from client where id =#{id}")
	Integer selectClientFromId(@Param("id") int id);

	@Select("select * from charm where id = #{id}")
	Charm selectCharmById(@Param("id") int id);

	@Select("select * from client where firstname like #{firstname} or lastname like #{lastname}  or patronymic like #{patronymic} order by ${order} ${sort} limit ${recordSize} offset ${offset}")
	List<Client> selectCLientListByFilter(@Param("firstname") String firstname,
																@Param("lastname") String lastname,
																@Param("patronymic") String patronymic,
																@Param("order") String order,
																	@Param("sort") String sort,
																	@Param("recordSize") int recordSize,
																	@Param("offset") int offset
	);

	@Select("select id from client where firstname like #{firstname} or lastname like #{lastname} or patronymic like #{patronymic}")
	List<Integer> selectCLientListByFilterCount(@Param("firstname") String firstname,
																				@Param("lastname") String lastname,
																				@Param("patronymic") String patronymic

	);



}
