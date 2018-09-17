package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.controller.model.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface ClientDao {

	@Select("select * from charm")
	List<Charm> listCharm();


	@Select("select * from client")
	List<Client> listClient();

	@Delete("delete from client where id = #{id}")
	void deleteClientById(@Param("id") int id);

	@Insert("insert into charm (id, name, description, energy, actually) values (#{id}, #{name}, #{description}, #{energy}, #{actually})")
	void insertCharm(@Param("id") int id,
									 @Param("name") String name,
									 @Param("description") String description,
									 @Param("energy") float energy,
									 @Param("actually") boolean actually
	);

	@Select("select id from charm where id = #{id}")
	Integer idCharmById(@Param("id") int id);

	@Select("select * from charm where id = #{id}")
	Charm selectCharmById(@Param("id") int id);


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

	@Select("select * from client where id = #{id}")
	Client selectClientById(@Param("id") int id);

	@Select("select birth_date from client")
	Date selDate();

	@Select("select type, street, house, flat from client_addr where client = #{id} and type =#{type}")
	ClientAddr selectClientAddrById(@Param("id") int id,
																	@Param("type") AddrType type);

	@Select("select type, number from client_phone where client = #{id}")
	List<ClientPhone> selectClientPhoneById(@Param("id") int id);


}
