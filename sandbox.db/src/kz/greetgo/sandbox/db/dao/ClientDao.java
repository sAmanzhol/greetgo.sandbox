package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.controller.model.model.*;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface ClientDao {


	@Select("select * from charm ")
	List<Charm> listCharm();

	@Select("select id from charm where id = #{id}")
	Integer idCharmById(@Param("id") int id);

	@Delete("delete from client where id = #{id}")
	void deleteClientById(@Param("id") int id);

	@Select("select * from client where id = #{id}")
	Client selectClientById(@Param("id") int id);

	@Select("select name from charm where id = #{id}")
	String nameCharmById(@Param("id") int id);


	@Select("select * from client_addr where client = #{id} and type =#{type}")
	ClientAddr selectClientAddrById(@Param("id") int id,
																	@Param("type") AddrType type);

	@Select("select * from client_phone where client = #{id}")
	List<ClientPhone> selectClientPhoneById(@Param("id") int id);

	@Select("select * from charm where id = #{id}")
	Charm selectCharmById(@Param("id") int id);


	@Insert("insert into client (id, firstname, lastname, patronymic, gender, birth_date, charm) values " +
		"(#{clientToSave.id}, #{clientToSave.firstname}, #{clientToSave.lastname}, #{clientToSave.patronymic}, #{clientToSave.gender}, #{clientToSave.dateOfBirth}, #{clientToSave.characterId}) ")
	 void insertClient(@Param("clientToSave") ClientToSave clientToSave

	);

	@Select("select max(id) from client")
	Integer getmaxClientId();


	@Insert("insert into client_phone (client, number, type) values (#{client}, #{clientPhone.number}, #{clientPhone.type}) ")
	void insertClientPhone(@Param("clientPhone") ClientPhone clientPhone,
												 @Param("client") Integer client

	);

	@Insert("insert into client_addr (client, type, street, house, flat) values (#{client}, #{clientAddr.type}, #{clientAddr.street}, #{clientAddr.house}, #{clientAddr.flat}) ")
	void insertClientAddr(@Param("clientAddr") ClientAddr clientAddr,
												@Param("client") Integer client
	);
	@Insert("insert into client_account(client, registered_at) values (#{client}, #{registeredAt}) ")
	void insertClientAccount(@Param("client") Integer client,
													 @Param("registeredAt")Timestamp timestamp);


	@Update("update client set firstname = #{clientToSave.firstname}, lastname= #{clientToSave.lastname}, patronymic = #{clientToSave.patronymic}, gender= #{clientToSave.gender}, birth_date = #{clientToSave.dateOfBirth}, charm = #{clientToSave.characterId} where id = #{clientToSave.id}")
	void updateClient(@Param("clientToSave") ClientToSave clientToSave
	);


	@Update("update client_phone set number = #{clientPhone.number}, type =#{clientPhone.type} where client = #{client}")
	void updateClientPhone(@Param("clientPhone") ClientPhone clientPhone,
												 @Param("client") int client
	);

	@Update("update client_addr set street = #{clientAddr.street}, house= #{clientAddr.house}, flat = #{clientAddr.flat} where client = #{client} and type =#{clientAddr.type} ")
	void updateClientAddr(@Param("client") Integer client,
												@Param("clientAddr") ClientAddr clientAddr

	);

	@Select("select avg(money) from client_account where client= #{id} group by client")
	Integer selectTotalAccountBalance(@Param("id") int id);

	@Select("select max(money) from client_account where client= #{id} group by client")
	Integer selectMaximumBalance(@Param("id") int id);

	@Select("select min(money) from client_account where client= #{id} group by client")
	Integer selectMinimumBalance(@Param("id") int id);

}
