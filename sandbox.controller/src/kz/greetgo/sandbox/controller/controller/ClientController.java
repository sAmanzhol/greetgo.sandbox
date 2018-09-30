package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@Mapping("/client")
public class ClientController implements Controller {

	public BeanGetter<ClientRegister> clientRegister;



	@ToJson
	@Mapping("/client-list")
	public List<ClientRecord> clientList(@Par("clientFilter") @Json ClientFilter clientFilter) {
		System.out.println(clientFilter);
		return clientRegister.get().getClientList(clientFilter);
	}

	@ToJson
	@Mapping("/client-total-record")
	public Integer clientTotalRecord(@Par("clientFilter") @Json ClientFilter clientFilter) {
		return clientRegister.get().getClientTotalRecord(clientFilter);
	}

	@ToJson
	@Mapping("/get-charm")
	public List<Charm> getCharm() {
		return clientRegister.get().getCharm();
	}

	@ToJson
	@Mapping("/save-client")
	public ClientRecord saveClient(@Par("clientToSave") @Json ClientToSave clientToSave) {
		return clientRegister.get().saveClient(clientToSave);
	}

	@ToJson
	@Mapping("/delete-client")
	public void deleteClient(@Par("clientMark") Integer clientMarkId) {
		clientRegister.get().deleteClient(clientMarkId);
	}

	@ToJson
	@Mapping("/get-client-details")
	public ClientDetails getClientDetails(@Par("clientMark") Integer clientMarkId) {
		return clientRegister.get().getClientDetails(clientMarkId);
	}

	@ToJson
	@Mapping("/get-charm-by-id")
	public Charm getCharmById(@Par("charmId") Integer charmId){
		return clientRegister.get().getCharmById(charmId);
	}



}
