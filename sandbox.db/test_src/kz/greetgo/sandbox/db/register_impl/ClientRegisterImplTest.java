package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

		clientTestDao.get().deleteAllClientAddr();
		clientTestDao.get().deleteAllClientPhone();
		clientTestDao.get().deleteAllClient();
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
	public void testGetClientList() {

	}

	@Test
	public void testGetClientTotalRecord() {

	}

	@Test
	public void testSaveClient() {


	}
}