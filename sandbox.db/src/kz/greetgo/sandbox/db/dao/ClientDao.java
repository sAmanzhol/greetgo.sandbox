package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.controller.model.model.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface ClientDao {


	@Delete("delete from client where id = #{id}")
	void deleteClientById(@Param("id") int id);

	@Select("select * from charm order by energy asc")
	List<Charm> listCharm();

	@Select("select * from client where id = #{id}")
	Client selectClientById(@Param("id") int id);

	@Select("select name from charm where id = #{id}")
	String nameCharmById(@Param("id") int id);

	@Select("select id from client where id = #{id}")
	Integer idClientById(@Param("id") int id);

	@Select("select type, street, house, flat from client_addr where client = #{id} and type =#{type}")
	ClientAddr selectClientAddrById(@Param("id") int id,
																	@Param("type") AddrType type);

	@Select("select type, number from client_phone where client = #{id}")
	List<ClientPhone> selectClientPhoneById(@Param("id") int id);

	@Select("select id from charm where id = #{id}")
	Integer idCharmById(@Param("id") int id);

	@Select("select * from charm where id = #{id}")
	Charm selectCharmById(@Param("id") int id);

	@Select("select * from client")
	List<Client> listClient();

	@Select("select birth_date from client")
	Date selDate();


	/*public Integer id;
		public String firstname;
		public String lastname;
		public String patronymic;
		public GenderType gender;
		public String dateOfBirth;
		public int characterId;
		public ClientAddr addressOfResidence;
		public ClientAddr addressOfRegistration;
		public List<ClientPhone> phone=new ArrayList<>();*/


	@Insert("insert into charm (id, name, description, energy, actually) values (#{id}, #{name}, #{description}, #{energy}, #{actually})")
	void insertCharm(@Param("id") int id,
									 @Param("name") String name,
									 @Param("description") String description,
									 @Param("energy") float energy,
									 @Param("actually") boolean actually
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

	@Update("update client set firstname = #{firstname},lastname= #{lastname}, patronymic = #{patronymic}, gender= #{gender}, birth_date = #{birthDate},charm = #{charm} where id = #{id}")
	void updateClient(@Param("id") int id,
										@Param("firstname") String firstname,
										@Param("lastname") String lastname,
										@Param("patronymic") String patronymic,
										@Param("gender") GenderType gender,
										@Param("birthDate") Date birthDate,
										@Param("charm") int charm

	);

	@Update("update client_phone set number = #{number}, type =#{type} where client = #{client}")
	void updateClientPhone(@Param("client") int client,
												 @Param("number") String number,
												 @Param("type") PhoneType type
	);

	@Update("update client_addr set type =#{type}, street = #{street}, house= #{house}, flat = #{flat} where client = #{client}")
	void updateClientAddr(@Param("client") int client,
												@Param("type") AddrType type,
												@Param("street") String street,
												@Param("house") String house,
												@Param("flat") String flat

	);

	/*public String firstname;
	public String lastname;
	public String patronymic;
	public String orderBy;
	public boolean sort;
	public int page;
	public int pageTotal;
	public int recordSize;
	public int recordTotal;*/

	@Select("select count(*) from client where firstname like #{firstname} or lastname like #{lastname} or patronymic like #{patronymic}")
	Integer selectTotalRecordClient(@Param("firstname") String firstname,
																	@Param("lastname") String lastname,
																	@Param("patronymic") String patronymic

	);

	@Select("select avg(money) from client_account where client= #{id} group by client")
	Integer selectTotalAccountBalance(@Param("id") int id );
	@Select("select max(money) from client_account where client= #{id} group by client")
	Integer selectMaximumBalance(@Param("id") int id );
	@Select("select min(money) from client_account where client= #{id} group by client")
	Integer selectMinimumBalance(@Param("id") int id );

}
