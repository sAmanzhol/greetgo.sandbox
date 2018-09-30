package kz.greetgo.sandbox.db.test.dao;


import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.Client;
import kz.greetgo.sandbox.controller.model.model.ClientPhone;
import kz.greetgo.sandbox.db.model.ClientAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface LoadTestDao {

	@Insert("insert into charm (id, name, description, energy, actually) values (#{charm.id}, #{charm.name}, #{charm.description}," +
		" #{charm.energy}, #{charm.actually})")
	void inserCharm(@Param("charm") Charm charm);

	@Insert("insert into client (id, firstname, lastname, patronymic, gender, birth_date, charm) values (#{client.id}," +
		" #{client.firstname}, #{client.lastname}, #{client.patronymic}, #{client.gender}, #{client.birthDate}, #{client.charm})")
	void insertClient(@Param("client") Client client);

	@Insert("insert into client_phone (client, number, type) " +
		"values (#{clientPhone.client}, #{clientPhone.number}, #{clientPhone.type})")
	void insertClientPhone(@Param("clientPhone") ClientPhone clientPhone);

	@Insert("insert into client_account (client, money, number, registered_at) " +
		" values (#{clientAccount.client}, #{clientAccount.money}, #{clientAccount.number}, #{clientAccount.registeredAt})")
	void insertClientAccount(@Param("clientAccount") ClientAccount clientAccount);
}
