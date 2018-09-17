package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;

import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

	public BeanGetter<ClientDao> clientDao;

	@Override
	public List<Charm> getCharm() {

		return clientDao.get().listCharm();
	}

	@Override
	public void deleteClient(Integer clientMarkId) {

		clientDao.get().deleteClientById(clientMarkId);
	}

	@Override
	public Charm getCharmById(Integer charmId) {

		Integer id = clientDao.get().idCharmById(charmId);
		if (id == null) {
			clientDao.get().insertCharm(charmId, String.valueOf(charmId), String.valueOf(charmId), charmId, false);
		}

		return clientDao.get().selectCharmById(charmId);

	}

	@Override
	public ClientDetails getClientDetails(Integer clientMarkId) {

		ClientDetails clientDetails = new ClientDetails();
		Client client = clientDao.get().selectClientById(clientMarkId);
		clientDetails.addressOfResidence = clientDao.get().selectClientAddrById(clientMarkId, AddrType.FACT);
		clientDetails.addressOfRegistration = clientDao.get().selectClientAddrById(clientMarkId, AddrType.REG);
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
	public ClientRecord saveClient(ClientToSave clientDetails) {


		return null;
	}

	@Override
	public List<ClientRecord> getClientList(ClientFilter clientFilter) {

		return null;
	}

	@Override
	public Integer getClientTotalRecord(ClientFilter clientFilter) {

		return null;
	}


}
