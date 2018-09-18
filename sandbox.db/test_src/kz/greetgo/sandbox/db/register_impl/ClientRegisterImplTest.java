package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

	public BeanGetter<ClientTestDao> clientTestDao;

	public BeanGetter<ClientRegister> clientRegister;

	public Charm charm;

	public Client client;

	@BeforeMethod
	public void setUp() {

		clientTestDao.get().deleteAllClientAccountTransaction();
		clientTestDao.get().deleteAllClientAccount();
		clientTestDao.get().deleteAllClientAddr();
		clientTestDao.get().deleteAllClientPhone();
		clientTestDao.get().deleteAllClient();
		clientTestDao.get().deleteAllTransactionType();
		clientTestDao.get().deleteAllCharm();

		clientTestDao.get();
		charm = new Charm();
		charm.id = RND.plusInt(100);
		charm.name = RND.str(10);
		charm.description = RND.str(10);
		charm.energy = RND.plusInt(100);
		charm.actually = rndBoolean();
		client = new Client();
		client.id = 0;
		client.firstname = RND.str(10);
		client.lastname = RND.str(10);
		client.patronymic = RND.str(10);
		client.gender = GenderType.MALE;
		client.birthDate = new Date();
		client.charm = charm.id;

	}

	private boolean rndBoolean() {

		int IntRnd = (int) (Math.random() * 10 - 5);
		if (IntRnd < 0) {
			return false;
		} else
			return true;
	}

	@Test
	public void testGetCharm() {

		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		//
		//
		List<Charm> listCharm = clientRegister.get().getCharm();
		//
		//
		assertThat(listCharm).hasSize(1);
		assertThat(listCharm.get(0).id).isNotNull();
		assertThat(listCharm.get(0).name).isEqualTo(charm.name);
		assertThat(listCharm.get(0).description).isEqualTo(charm.description);
		assertThat(listCharm.get(0).energy).isEqualTo(charm.energy);
		assertThat(listCharm.get(0).actually).isEqualTo(charm.actually);

	}

	@Test
	public void testDeleteClient() {

		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);

//
//
		clientRegister.get().deleteClient(client.id);

//
//

		Integer clientOfDeleted = clientTestDao.get().selectClientFromId(client.id);
		assertThat(clientOfDeleted).isNull();

	}

	@Test
	public void testGetCharmById() {

		Integer id = RND.plusInt(10);

//
//
		Charm charm = clientRegister.get().getCharmById(id);
//
//
		Charm charm1 = clientTestDao.get().selectCharmById(id);
		assertThat(charm.id).isEqualTo(charm1.id);
	}

	@Test
	public void testGetClientDetails() {

		ClientPhone clientPhone = new ClientPhone();
		clientPhone.number = "8797546546";

		ClientAddr clientAddr = new ClientAddr();
		clientAddr.street = "dadassa";
		clientAddr.house = "dadassa";
		clientAddr.flat = "dadassa";
		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
		clientTestDao.get().insertClientPhone(client.id, clientPhone.number, PhoneType.MOBILE);
		clientTestDao.get().insertClientAddr(client.id, AddrType.FACT, clientAddr.street, clientAddr.house, clientAddr.flat);

//
//
		ClientDetails clientDetails =clientRegister.get().getClientDetails(client.id);
		System.err.println(clientDetails);
//
//
		assertThat(client.id).isEqualTo(clientDetails.id);

	}

	@Test
	public void testSaveClient() {
		ClientPhone clientPhone = new ClientPhone();
		clientPhone.number = "8797546546";
		clientPhone.type = PhoneType.MOBILE;
		ClientAddr clientAddr = new ClientAddr();
		clientAddr.street = "dadassa";
		clientAddr.house = "dadassa";
		clientAddr.flat = "dadassa";
		clientAddr.type=AddrType.REG;

		int Tid = RND.plusInt(546);
		String Tcode = RND.str(10);
		String Tname = RND.str(10);

		int CAid = RND.plusInt(564);
		int CAclient = client.id;
		float CAmoney = RND.plusInt(456);
		String CAnumber =RND.str(10);
		Timestamp CAregistered_at = new Timestamp(RND.plusInt(532));

		int CATid = RND.plusInt(546);
		int CATaccount = CAid;
		float CATmoney = RND.plusInt(1000);
		Timestamp CATfinished_at = new Timestamp(RND.plusInt(532));
		int CATtype = Tid;


		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
		clientTestDao.get().insertClientPhone(client.id, clientPhone.number, PhoneType.MOBILE);
		clientTestDao.get().insertClientAddr(client.id, AddrType.FACT, clientAddr.street, clientAddr.house, clientAddr.flat);
		clientTestDao.get().insertTransaction_type(Tid,Tcode,Tname);
		clientTestDao.get().insertClientAccount(CAid,CAclient,CAmoney,CAnumber,CAregistered_at);
		clientTestDao.get().insertClientAccountTransaction(CATid,CATaccount,CATmoney,CATfinished_at,CATtype);

		ClientToSave clientDetails = new ClientToSave();
		clientDetails.id = client.id;
		clientDetails.firstname =client.firstname;
		clientDetails.lastname =client.lastname;
		clientDetails.patronymic =client.patronymic;
		clientDetails.gender =client.gender;
		clientDetails.dateOfBirth =client.birthDate;
		clientDetails.characterId =charm.id;
		clientDetails.addressOfRegistration.street = clientAddr.street;
		clientDetails.addressOfRegistration.house= clientAddr.house;
		clientDetails.addressOfRegistration.flat = clientAddr.flat;
		clientDetails.addressOfRegistration.type = AddrType.REG;
		clientDetails.addressOfResidence.street = clientAddr.street;
		clientDetails.addressOfResidence.house= clientAddr.house;
		clientDetails.addressOfResidence.flat = clientAddr.flat;
		clientDetails.addressOfResidence.type = AddrType.REG;
		clientDetails.phone.add(clientPhone);

		//
		//
		ClientRecord clientRecord = clientRegister.get().saveClient(clientDetails);
		System.err.println(clientRecord);
		//
		//



	}

	@Test
	public void testGetClientList() {

	}

	@Test
	public void testGetClientTotalRecord() {

	}
}