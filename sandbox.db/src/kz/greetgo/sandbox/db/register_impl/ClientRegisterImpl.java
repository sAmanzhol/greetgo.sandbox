package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbc;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbcListRecord;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

	public BeanGetter<ClientDao> clientDao;

	public BeanGetter<Jdbc> jdbc;

	@Override
	public List<Charm> getCharm() {
		// TODO: asset 9/21/18 Tolko actualnye dannye
		return clientDao.get().listCharm();
	}

	@Override
	public void deleteClient(Integer clientMarkId) {

		clientDao.get().deleteClientById(clientMarkId);
	}

	@Override
	public Charm getCharmById(Integer charmId) {

		if (charmId == null) {
			return null;
		}
		Charm charm = new Charm();

		Integer id = clientDao.get().idCharmById(charmId);
		if (id != null) {
			// TODO: asset 9/21/18 Davai pomenyaem na getCharmById() tak budet pravilnee, I idCharmById lishny
			return clientDao.get().selectCharmById(charmId);
		}
		if (id == null) {
			charm.id = charmId;
			charm.name = String.valueOf(charmId);
			charm.description = String.valueOf(charmId);
			charm.energy = charmId;
			charm.actually = false;
		}

		return charm;
	}

	@Override
	public ClientDetails getClientDetails(Integer clientMarkId) {

		ClientDetails clientDetails = new ClientDetails();
		// TODO: asset 9/21/18 Davai poprobuyem select client details
		Client client = clientDao.get().selectClientById(clientMarkId);
		clientDetails.addressOfResidence = clientDao.get().selectClientAddrById(clientMarkId, AddrType.FACT);
		clientDetails.addressOfRegistration = clientDao.get().selectClientAddrById(clientMarkId, AddrType.REG);
		// TODO: asset 9/21/18 U listta est method addAll
		for (ClientPhone clientPhone : clientDao.get().selectClientPhoneById(clientMarkId)) {
			clientDetails.phone.add(clientPhone);
		}
		clientDetails.id = client.id;
		clientDetails.firstname = client.firstname;
		clientDetails.lastname = client.lastname;
		clientDetails.patronymic = client.patronymic;
		clientDetails.dateOfBirth = client.birthDate;
		clientDetails.gender = client.gender;
		clientDetails.characterId = client.charm;

		return clientDetails;
	}

	@Override
	public ClientRecord saveClient(ClientToSave clientToSave) {

		ClientAddr clientAddr;
		ClientRecord clientRecord = new ClientRecord();
		Client client = new Client();

		if (clientToSave.id != null) {
			clientDao.get().updateClient(clientToSave);
			for (ClientPhone clientPhone : clientToSave.phone) {
				clientDao.get().updateClientPhone(clientPhone, clientToSave.id);
			}
			clientAddr = clientToSave.addressOfResidence;
			// TODO: asset 9/21/18 U Potgres est insert ili update method
			clientDao.get().updateClientAddr(clientToSave.id, clientAddr);
			clientAddr = clientToSave.addressOfRegistration;
			clientDao.get().updateClientAddr(clientToSave.id, clientAddr);


		}
		if (clientToSave.id == null) {
			Integer maxes = clientDao.get().getmaxClientId();
			if(maxes ==null)
				maxes=0;
			clientToSave.id = (int) ((Math.random()+1)*maxes);
			clientDao.get().insertClient(clientToSave);
			clientDao.get().insertClientAddr(clientToSave.addressOfResidence, clientToSave.id);
			clientDao.get().insertClientAddr(clientToSave.addressOfRegistration, clientToSave.id);
			clientDao.get().insertClientAccount(clientToSave.id,new Timestamp(2));
			for (ClientPhone clientPhone : clientToSave.phone) {
				clientDao.get().insertClientPhone(clientPhone, clientToSave.id);
			}

		}

		// TODO: asset 9/21/18 Postaraisya sdelat v odnom zaprose
		client = clientDao.get().selectClientById(clientToSave.id);
		clientRecord.id = client.id;
		clientRecord.firstname = client.firstname;
		clientRecord.lastname = client.lastname;
		clientRecord.patronymic = client.patronymic;
		clientRecord.dateOfBirth = client.birthDate;
		clientRecord.characterName=clientDao.get().nameCharmById(client.charm);
		clientRecord.totalAccountBalance = clientDao.get().selectTotalAccountBalance(clientToSave.id);
		if(clientRecord.totalAccountBalance==null)
			clientRecord.totalAccountBalance=0;
		clientRecord.maximumBalance = clientDao.get().selectMaximumBalance(clientToSave.id);
		if(clientRecord.maximumBalance==null)
			clientRecord.maximumBalance=0;
		clientRecord.minimumBalance = clientDao.get().selectMinimumBalance(clientToSave.id);
		if(clientRecord.minimumBalance==null)
			clientRecord.minimumBalance=0;


		System.err.println("ClientRecordsss:" + clientRecord);
		return clientRecord;
	}

	@Override
	public List<ClientRecord> getClientList(ClientFilter clientFilter) {


		List<ClientRecord> clientRecords = new ArrayList<>();
		clientRecords = jdbc.get().execute(new ClientJdbcListRecord(clientFilter));
		return clientRecords;
	}

	@Override
	public Integer getClientTotalRecord(ClientFilter clientFilter) {

		clientFilter.recordTotal = jdbc.get().execute(new ClientJdbc(clientFilter));
		return clientFilter.recordTotal;

	}


}
