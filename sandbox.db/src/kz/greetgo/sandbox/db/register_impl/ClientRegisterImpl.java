package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientCountSql;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientListSql;

import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

	public BeanGetter<ClientDao> clientDao;

	public BeanGetter<Jdbc> jdbc;

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

		Charm charm;

		if (charmId == null) return null;

		charm = clientDao.get().getCharmById(charmId);
		if (charm != null) return charm;

		charm = new Charm();
		charm.id = charmId;
		charm.name = String.valueOf(charmId);
		charm.description = String.valueOf(charmId);
		charm.energy = charmId;
		charm.actually = false;

		return charm;
	}

	@Override
	public ClientDetails getClientDetails(Integer clientMarkId) {

		ClientDetails clientDetails = clientDao.get().selectClient(clientMarkId);
		clientDetails.addressOfResidence = clientDao.get().selectClientAddrById(clientMarkId, AddrType.FACT);
		clientDetails.addressOfRegistration = clientDao.get().selectClientAddrById(clientMarkId, AddrType.REG);
		clientDetails.phone.addAll(clientDao.get().selectClientPhoneById(clientMarkId));
		return clientDetails;

	}

	@Override
	public ClientRecord saveClient(ClientToSave clientToSave) {

		if (clientToSave.id == null) {
			Integer maxes = clientDao.get().getmaxClientId();
			if (maxes == null)
				maxes = 0;
			clientToSave.id = maxes + 1;
		}


		List<String> clientListPhoneNumber = clientDao.get().selectClientPhoneNumberById(clientToSave.id);

		clientDao.get().upsertClient(clientToSave);
		clientDao.get().upsertClientAddr(clientToSave.addressOfResidence, clientToSave.id);
		clientDao.get().upsertClientAddr(clientToSave.addressOfRegistration, clientToSave.id);

		for (ClientPhone clientPhone : clientToSave.phone) {
			if (clientListPhoneNumber.contains(clientPhone.number)) {
				clientListPhoneNumber.remove(clientPhone.number);
			}
		}
		for (String phoneNumber : clientListPhoneNumber) {
			clientDao.get().deleteClientPhoneByIdandNumber(clientToSave.id, phoneNumber);
		}
		for (ClientPhone clientPhone1 : clientToSave.phone) {
			clientDao.get().upsertClientPhone(clientPhone1, clientToSave.id);
		}

		return clientDao.get().selectClientRecord(clientToSave.id);
	}

	@Override
	public List<ClientRecord> getClientList(ClientFilter clientFilter) {


		return jdbc.get().execute(new ClientListSql(clientFilter));

	}

	@Override
	public Integer getClientTotalRecord(ClientFilter clientFilter) {


		return jdbc.get().execute(new ClientCountSql(clientFilter));

	}

}
