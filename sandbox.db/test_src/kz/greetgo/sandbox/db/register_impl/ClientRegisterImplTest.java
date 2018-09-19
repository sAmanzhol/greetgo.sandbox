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

	public ClientAddr clientAddr;

	public ClientPhone clientPhone;

	public ClientAccount clientAccount;

	public ClientAccountTransaction clientAccountTransaction;

	public TransactionType transactionType;

	private Client addClient() {

		Client client = new Client();
		client.id = RND.plusInt(100);
		client.firstname = RND.str(10);
		client.lastname = RND.str(10);
		client.patronymic = RND.str(10);
		client.gender = GenderType.MALE;
		client.birthDate = new Date();
		client.charm = charm.id;
		return client;
	}

	private Charm addCharm() {

		Charm charm = new Charm();
		charm.id = RND.plusInt(100);
		charm.name = RND.str(10);
		charm.description = RND.str(10);
		charm.energy = RND.plusInt(100);
		charm.actually = rndBoolean();
		return charm;
	}

	private ClientAddr addClientAddr() {

		ClientAddr clientAddr = new ClientAddr();
		clientAddr.type = AddrType.REG;
		clientAddr.street = RND.str(9);
		clientAddr.house = RND.str(9);
		clientAddr.flat = RND.str(9);
		return clientAddr;
	}

	private ClientPhone addClientPhone() {

		ClientPhone clientPhone = new ClientPhone();
		clientPhone.type = PhoneType.MOBILE;
		clientPhone.number = RND.plusInt(110000) + "";
		return clientPhone;
	}

	private ClientAccount addClientAccount() {

		ClientAccount clientAccount = new ClientAccount();
		clientAccount.id = RND.plusInt(564);
		clientAccount.client = client.id;
		clientAccount.money = RND.plusInt(456);
		clientAccount.number = RND.str(10);
		clientAccount.registered_at = new Timestamp(RND.plusInt(532));
		return clientAccount;

	}

	private TransactionType addTransactionType() {

		TransactionType transactionType = new TransactionType();

		transactionType.id = RND.plusInt(546);
		transactionType.code = RND.str(10);
		transactionType.name = RND.str(10);
		return transactionType;
	}

	private ClientAccountTransaction addClientAccountTransaction() {

		ClientAccountTransaction clientAccountTransaction = new ClientAccountTransaction();
		clientAccountTransaction.id = RND.plusInt(546);
		clientAccountTransaction.account = clientAccount.id;
		clientAccountTransaction.money = RND.plusInt(1000);
		clientAccountTransaction.finished_at = new Timestamp(RND.plusInt(532));
		clientAccountTransaction.type = transactionType.id;
		return clientAccountTransaction;
	}


	@BeforeMethod
	public void setUp() {

		clientTestDao.get().deleteAllClientAccountTransaction();
		clientTestDao.get().deleteAllClientAccount();
		clientTestDao.get().deleteAllClientAddr();
		clientTestDao.get().deleteAllClientPhone();
		clientTestDao.get().deleteAllClient();
		clientTestDao.get().deleteAllTransactionType();
		clientTestDao.get().deleteAllCharm();

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

		charm = addCharm();

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

		charm = addCharm();
		client = addClient();

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

		charm = addCharm();
		client = addClient();
		clientPhone = addClientPhone();
		clientAddr = addClientAddr();
		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
		clientTestDao.get().insertClientPhone(client.id, clientPhone.number, PhoneType.MOBILE);
		clientTestDao.get().insertClientAddr(client.id, AddrType.FACT, clientAddr.street, clientAddr.house, clientAddr.flat);
		clientTestDao.get().insertClientAddr(client.id, AddrType.REG, clientAddr.street, clientAddr.house, clientAddr.flat);

//
//
		ClientDetails clientDetails = clientRegister.get().getClientDetails(client.id);
		System.err.println(clientDetails);
//
//
		assertThat(client.id).isEqualTo(clientDetails.id);


	}

	@Test
	public void testSaveClient() {

		charm = addCharm();
		client = addClient();
		clientPhone = addClientPhone();
		clientAddr = addClientAddr();
		transactionType = addTransactionType();
		clientAccount = addClientAccount();
		clientAccountTransaction = addClientAccountTransaction();

		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
		clientTestDao.get().insertClientPhone(client.id, clientPhone.number, PhoneType.MOBILE);
		clientTestDao.get().insertClientAddr(client.id, AddrType.FACT, clientAddr.street, clientAddr.house, clientAddr.flat);
		clientTestDao.get().insertTransaction_type(transactionType.id, transactionType.code, transactionType.name);
		clientTestDao.get().insertClientAccount(clientAccount.id, clientAccount.client, clientAccount.money, clientAccount.number, clientAccount.registered_at);
		clientTestDao.get().insertClientAccountTransaction(
			clientAccountTransaction.id, clientAccountTransaction.account, clientAccountTransaction.money,
			clientAccountTransaction.finished_at, clientAccountTransaction.type);

		ClientToSave clientDetails = new ClientToSave();
		clientDetails.id = client.id;
		clientDetails.firstname = client.firstname;
		clientDetails.lastname = client.lastname;
		clientDetails.patronymic = client.patronymic;
		clientDetails.gender = client.gender;
		clientDetails.dateOfBirth = client.birthDate;
		clientDetails.characterId = charm.id;
		clientDetails.addressOfRegistration.street = clientAddr.street;
		clientDetails.addressOfRegistration.house = clientAddr.house;
		clientDetails.addressOfRegistration.flat = clientAddr.flat;
		clientDetails.addressOfRegistration.type = AddrType.REG;
		clientDetails.addressOfResidence.street = clientAddr.street;
		clientDetails.addressOfResidence.house = clientAddr.house;
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

		charm = addCharm();
		client = addClient();
		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 1), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 2), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
		clientTestDao.get().insertClient((client.id + 1), client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 1);
		clientTestDao.get().insertClient((client.id + 2), client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 2);


		ClientFilter clientFilter = new ClientFilter();
		clientFilter.patronymic = "";
		clientFilter.lastname = "";
		clientFilter.firstname = "";
		clientFilter.orderBy = "charm";
		clientFilter.recordSize = 1;
		clientFilter.page = 0;
		clientFilter.recordTotal = 100;


		List<Client> client = clientTestDao.get().selectCLientListByFilter(clientFilter.firstname + "%", clientFilter.lastname + "%", clientFilter.patronymic + "%",
			clientFilter.orderBy, "asc", clientFilter.recordSize, (clientFilter.page * clientFilter.recordSize));
		System.err.println(client);

	}

	@Test
	public void testGetClientTotalRecord() {

		charm = addCharm();
		client = addClient();
		clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 1), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 2), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 3), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 4), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertCharm((charm.id + 5), charm.name, charm.description, charm.energy, charm.actually);
		clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
		clientTestDao.get().insertClient((client.id + 1), client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 1);
		clientTestDao.get().insertClient((client.id + 2), client.lastname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 2);
		clientTestDao.get().insertClient((client.id + 3), client.lastname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 3);
		clientTestDao.get().insertClient((client.id + 4), client.patronymic, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 4);
		clientTestDao.get().insertClient((client.id + 5), client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm + 5);

		ClientFilter clientFilter = new ClientFilter();
		clientFilter.firstname = "";
		clientFilter.lastname = "";
		clientFilter.patronymic = "";
		clientFilter.orderBy = "charm";
		clientFilter.recordSize = 10;
		clientFilter.page = 0;
		clientFilter.recordTotal = 100;
		clientFilter.sort=false;

		clientFilter.recordTotal= clientRegister.get().getClientTotalRecord(clientFilter);
		System.err.println("count: " +clientFilter.recordTotal);


		assertThat(clientFilter.recordTotal).isEqualTo(6);
	}
}