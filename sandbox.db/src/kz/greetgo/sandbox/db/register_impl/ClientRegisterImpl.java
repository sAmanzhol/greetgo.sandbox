package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.Collection;

public class ClientRegisterImpl implements ClientRegister {
	@Override
	public Collection<ClientRecord> getClientList(ClientFilter clientFilter) {
		return null;
	}

	@Override
	public Integer getClientTotalRecord(ClientFilter clientFilter) {
		return null;
	}

	@Override
	public Collection<Charm> clientCharm() {
		return null;
	}

	@Override
	public ClientRecord clientDetailsSave(ClientDetails clientDetails) {
		return null;
	}

	@Override
	public ClientDetails clientDetailsSet(Integer clientMarkId) {
		return null;
	}

	@Override
	public ClientDetails clientDetailsDelete(Integer clientMarkId) {
		return null;
	}

	@Override
	public Charm getClientAddCharmId(Integer charmId) {
		return null;
	}
}
