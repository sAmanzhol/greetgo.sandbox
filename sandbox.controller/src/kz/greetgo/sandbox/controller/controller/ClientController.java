package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.Collection;

@Bean
@Mapping("/client")
public class ClientController implements Controller {
	public BeanGetter<ClientRegister> clientRegister;

	@ToJson
	@Mapping("/client-list")
	public Collection<ClientRecord> clientList(@Par("clientFilter") @Json ClientFilter clientFilter) {
		System.out.println(clientFilter);
		return clientRegister.get().getClientList(clientFilter);
	}

	@ToJson
	@Mapping("/client-total-record")
	public Integer clientTotalRecord(@Par("clientFilter") @Json ClientFilter clientFilter) {
		return clientRegister.get().getClientTotalRecord(clientFilter);
	}

	@ToJson
	@Mapping("/client-charm")
	public Collection<Charm> clientCharm() {
		return clientRegister.get().clientCharm();
	}

	@ToJson
	@Mapping("/client-details-save")
	public ClientRecord clientDetailsSave(@Par("clientDetails") @Json ClientDetails clientDetails) {
		return clientRegister.get().clientDetailsSave(clientDetails);
	}

	@ToJson
	@Mapping("/client-details-delete")
	public ClientDetails clientDetailsDelete(@Par("clientMark") @Json ClientRecord clientMark) {
		return clientRegister.get().clientDetailsDelete(clientMark);
	}

	@ToJson
	@Mapping("/client-details-set")
	public ClientDetails clientDetailsSet(@Par("clientMark") @Json ClientRecord clientMark) {
		return clientRegister.get().clientDetailsSet(clientMark);
	}

}
