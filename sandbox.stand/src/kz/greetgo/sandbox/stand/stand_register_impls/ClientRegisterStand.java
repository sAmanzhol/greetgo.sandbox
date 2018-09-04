package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.model.Address;
import kz.greetgo.sandbox.controller.register.model.Client;
import kz.greetgo.sandbox.controller.register.model.Phone;
import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.db.stand.beans.ClientDb;
import kz.greetgo.sandbox.db.stand.beans.StandDb;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

@Bean
public class ClientRegisterStand implements ClientRegister {

	private final Path storageDir = new File("build/user_param_storage").toPath();

	private File getUserParamFile(UserParamName name) {
		return storageDir.resolve(name.name() + ".txt").toFile();
	}

	public BeanGetter<StandDb> db;
	public BeanGetter<ClientDb> cdb;
	public double page = 1;

	@Override
	public Collection<Client> getUserInfo() {

		List<Client> client = new ArrayList<>();
		int i = 0;
		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			if (i == 9) break;

			client.add(cl.getValue());
			i++;
		}
		return client;

	}

	@Override
	public Collection<Client> getFilter(String firstname, String lastname, String patronymic) {

		List<Client> list = new ArrayList<>();

		Client c = new Client();
		Client.setCounter();
		c.firstname = firstname;

		c.lastname = lastname;
		c.patronymic = patronymic;


		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {

			if (cl.getValue().firstname.equals(c.firstname) || cl.getValue().lastname.equals(c.lastname) || cl.getValue().patronymic.equals(c.patronymic)) {
				System.out.println("done");
				list.add(cl.getValue());
			}
		}

		return list;

	}

	@Override
	public Collection<Client> getUserSort(String sort) {

		List<Client> list = new ArrayList<>();
		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			list.add(cl.getValue());
		}

		switch (sort) {
			case "firstname":
				list.sort(new Client.SortedByFirstname());
				break;
			case "character":
				list.sort(new Client.SortedByCharacter());
				break;
			case "dateOfBirth":
				list.sort(new Client.SortedBydateOfBirth());
				break;
			case "totalAccountBalance":
				list.sort(new Client.SortedByTotalAccountBalance());
				break;
			case "maximumBalance":
				list.sort(new Client.SortedByMaximumBalance());
				break;
			case "minimumBalance":
				list.sort(new Client.SortedByMinimumBalance());
				break;
		}
		cdb.get().client.clear();
		for (int i = 0; i < list.size(); i++) {

			cdb.get().client.put(i, list.get(i));
		}
		return getUserInfo();


	}

	@Override
	public Double pagination() {

		int sizes = cdb.get().client.size();
		page = Math.floor(sizes / 10);
		if (sizes % 10 != 0) {
			++page;
		}


		return page;
	}

	@Override
	public Collection<Client> getPagination(Integer index) {
		List<Client> client = new ArrayList<>();


		if (index == null) {
			page = 0;
		} else
			page = index;
		int counter = 0;
		int i = (((int) page * 10));
		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {

			if (counter >= i && counter < (((int) page * 10) + 10)) {

				client.add(cl.getValue());
			}

			counter++;
		}


		return client;


	}

	@Override
	public Collection<Client> addUserInfo(Client client) {
		if(client.firstname==""){return null;}

		List<Client> list = new ArrayList<>();
		Client c = new Client();
		c.firstname = client.firstname;
		c.lastname = client.lastname;
		c.patronymic = client.patronymic;
		c.gender = client.gender;
		c.dateOfBirth = client.dateOfBirth;
		c.character = client.character;
		c.totalAccountBalance = client.totalAccountBalance;
		c.maximumBalance = client.maximumBalance;
		c.minimumBalance = client.minimumBalance;
		c.addressOfRegistration = rndAddress(client.addressOfRegistration);
		c.addressOfResidence = rndAddress(client.addressOfResidence);
		c.phone = rndPhone(client.phone);
		cdb.get().client.put(cdb.get().client.size(), c);

		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			list.add(cl.getValue());
		}


		return list;

	}

	@Override
	public Collection<Client> editUserInfo(Client edit) {
		List<Client> list = new ArrayList<>();
		Client c = new Client();
		Client.setCounter();
		c.id = edit.id;
		c.firstname = edit.firstname;
		c.lastname = edit.lastname;
		c.patronymic = edit.patronymic;
		c.gender = edit.gender;
		c.dateOfBirth = edit.dateOfBirth;
		c.character = edit.character;
		c.totalAccountBalance = edit.totalAccountBalance;
		c.maximumBalance = edit.maximumBalance;
		c.minimumBalance = edit.minimumBalance;
		c.addressOfRegistration = rndAddress(edit.addressOfRegistration);
		c.addressOfResidence = rndAddress(edit.addressOfResidence);
		c.phone = rndPhone(edit.phone);
		cdb.get().client.replace(c.id, c);

		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			list.add(cl.getValue());
		}


		return list;
	}

	@Override
	public Collection<Client> deleteUserInfo(Integer id) {
		List<Client> list = new ArrayList<>();
		if (id == null) {
			id = 0;
		}

		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			if (cl.getValue().id == id) continue;
			list.add(cl.getValue());
		}
		cdb.get().client.clear();

		for (int i = 0; i < list.size(); i++) {

			cdb.get().client.put(i, list.get(i));
		}

		return getUserInfo();

	}

	@Override
	public Collection<Client> getClientForEdit(Integer id) {
		List<Client> list = new ArrayList<>();
		if (id == null) {
			id = 0;
		}
		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			if (cl.getValue().id == id) {
				list.add(cl.getValue());
			}
		}


		return list;
	}



	@Override
	public List<String> getCharacter() {
		return cdb.get().character;
	}


	private Address rndAddress(Address add) {
		Address address = new Address();
		address.street = add.street;
		address.home = add.home;
		address.apartment = add.apartment;
		return address;

	}

	private Phone rndPhone(Phone ph) {
		Phone phone = new Phone();
		phone.home = ph.home;
		phone.work = ph.work;
		phone.mobile1 = ph.mobile1;
		phone.mobile2 = ph.mobile2;
		phone.mobile3 = ph.mobile3;
		return phone;
	}


}