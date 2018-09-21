package kz.greetgo.sandbox.db.test.dao;

import kz.greetgo.sandbox.controller.model.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ClientTestDao {

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


	@Insert("insert into charm (id, name, description, energy, actually) values (#{charm.id}, #{charm.name}, #{charm.description}, #{charm.energy}, #{charm.actually})")
	void insertCharm(@Param("charm") Charm charm);


	@Insert("insert into transaction_type (id, code, name) values (#{transactionType.id}, #{transactionType.code}, #{transactionType.name})")
	void insertTransaction_type(@Param("transactionType") TransactionType transactionType
	);

	@Insert("insert into client (id, firstname, lastname, patronymic, gender, birth_date, charm) values " +
		"(#{client.id}, #{client.firstname}, #{client.lastname}, #{client.patronymic}, #{client.gender}, #{client.birthDate}, #{client.charm})")
	void insertClient(@Param("client") Client client
	);


	@Insert("insert into client_account (id, client, money, number, registered_at) values (#{clientAccount.id}, #{clientAccount.client}, " +
		"#{clientAccount.money}, #{clientAccount.number}, #{clientAccount.registeredAt}) ")
	void insertClientAccount(@Param("clientAccount") ClientAccount clientAccount
	);


	@Insert("insert into client_account_transaction (id, account, money, finished_at, type) values (#{clientAccountTransaction.id}, #{clientAccountTransaction.account}," +
		" #{clientAccountTransaction.money}, #{clientAccountTransaction.finishedAt}, #{clientAccountTransaction.type}) ")
	void insertClientAccountTransaction(@Param("clientAccountTransaction") ClientAccountTransaction clientAccountTransaction

	);

	@Insert("insert into client_phone (client, number, type) values (#{clientPhone.client}, #{clientPhone.number}, #{clientPhone.type}) ")
	void insertClientPhone(@Param("clientPhone")ClientPhone clientPhone);


	@Insert("insert into client_addr (client, type, street, house, flat) values (#{clientaddr.client}, #{clientaddr.type}, #{clientaddr.street}, " +
		"#{clientaddr.house}, #{clientaddr.flat}) ")
	void insertClientAddr(@Param("clientaddr") ClientAddr clientaddr);



	@Select("select * from client where id =#{id}")
	Client getClientById(@Param("id") int id);

	@Select("select id from client where id =#{id}")
	Integer getClientId(@Param("id") int id);

	@Select("select * from charm where id = #{id}")
	Charm selectCharmById(@Param("id") int id);

}
