package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.sandbox.controller.model.ClientAsd;
import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.model.Client;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;
import kz.greetgo.util.RND;

import java.util.Collection;
import java.util.List;

@Bean
@Mapping("/client")
// TODO: asset 9/4/18 Nado ubrat lishnie kody
public class ClientController implements Controller {

	public BeanGetter<ClientRegister> clientRegister;

	@ToJson
	@NoSecurity
	@Mapping("/userInfo")
	// TODO: asset 9/4/18 List<Asd> i imena Mappingov dolzhno sovpodat s imenem methoda dlya chitablenosti
	public Collection<Client> userInfo() {
		return clientRegister.get().getUserInfo();
	}

	@ToJson
	@NoSecurity
	@Mapping("/getFilter")
	public Collection<Client> getFilter(@Par("firstname") String firstname, @Par("lastname") String lastname, @Par("patronymic") String patronymic) {
		return clientRegister.get().getFilter(firstname, lastname, patronymic);
	}

	@ToJson
	@NoSecurity
	@Mapping("/userSort")
	public Collection<Client> userSort(@Par("sort") String sort) {
		return clientRegister.get().getUserSort(sort);
	}

	@AsIs
	@NoSecurity
	@Mapping("/pagination")
	public Double pagination() {
		return clientRegister.get().pagination();
	}


	@ToJson
	@NoSecurity
	@Mapping("/getCharacter")
	public List<String> getCharacter() {
		return clientRegister.get().getCharacter();
	}


	@ToJson
	@NoSecurity
	@Mapping("/getPagination")
	public Collection<Client> getPagination(@Par("index") Integer index) {
		return clientRegister.get().getPagination(index);
	}

	@ToJson
	@NoSecurity
	@Mapping("/addUserInfo")
	public Collection<Client> addUserInfo(@Par("client") @Json Client client) {
		System.out.println(client.firstname + "SOSOSO");
		return clientRegister.get().addUserInfo(client);
	}

	@ToJson
	@NoSecurity
	@Mapping("/deleteUserInfo")
	public Collection<Client> deleteUserInfo(@Par("id") Integer id) {
		return clientRegister.get().deleteUserInfo(id);
	}

	@ToJson
	@NoSecurity
	@Mapping("/editUserInfo")
	public Collection<Client> editUserInfo(@Par("edit") @Json Client edit) {
		return clientRegister.get().editUserInfo(edit);
	}


	@ToJson
	@NoSecurity
	@Mapping("/getClientForEdit")
	public Collection<Client> getClientForEdit(@Par("id") Integer id) {
		return clientRegister.get().getClientForEdit(id);
	}


	@ToJson
	@NoSecurity
	@Mapping("/test-debug")
	// TODO: asset 9/5/18 Udalit 
	public ClientDetails testDebug(@Par("test") @Json ClientAsd jsons) {
		System.out.println("test: " + jsons);

		ClientDetails ret = new ClientDetails();
		ret.aaa = RND.str(10);
		ret.sss = RND.str(10);

		System.out.println("ret: " + ret);

		return ret;

	}


}
