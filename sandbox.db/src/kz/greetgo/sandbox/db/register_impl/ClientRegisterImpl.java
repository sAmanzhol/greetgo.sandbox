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
	public void deleteClient(Integer clientId) {

		clientDao.get().deleteClientById(clientId);
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
	public ClientDetails getClientDetails(Integer clientId) {

		ClientDetails clientDetails = clientDao.get().selectClient(clientId);
		clientDetails.addressOfResidence = clientDao.get().selectClientAddrById(clientId, AddrType.FACT);
		clientDetails.addressOfRegistration = clientDao.get().selectClientAddrById(clientId, AddrType.REG);
		// TODO: asset 9/30/18 Prikolno))
		clientDetails.phone.addAll(clientDao.get().selectClientPhoneByMobile(clientId));
		clientDetails.phone.addAll(clientDao.get().selectClientPhoneByNotMobile(clientId));
		return clientDetails;

	}

	@Override
	public ClientRecord saveClient(ClientToSave clientToSave) {

		if (clientToSave.id == null) {
			// TODO: asset 9/30/18 V baze mozhno geeri id dlya clientov
			Integer maxes = clientDao.get().getmaxClientId();
			if (maxes == null)
				maxes = 0;
			clientToSave.id = maxes + 1;
		}



		clientDao.get().upsertClient(clientToSave);
		clientDao.get().upsertClientAddr(clientToSave.addressOfResidence, clientToSave.id);
		clientDao.get().upsertClientAddr(clientToSave.addressOfRegistration, clientToSave.id);

// TODO: asset 9/30/18 Nuzhno ubrat vniz
		List<String> clientListPhoneNumber = clientDao.get().selectClientPhoneNumberById(clientToSave.id);
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
