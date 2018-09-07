package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.model.Address;
import kz.greetgo.sandbox.controller.register.model.Client;
import kz.greetgo.sandbox.controller.register.model.Phone;
import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.db.stand.beans.ClientDb;
import kz.greetgo.sandbox.db.stand.beans.StandDb;

import java.io.File;
import java.lang.reflect.Array;
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
	public ClientFilter filter = new ClientFilter();
	public double page = 1;
	public int pageList = 0;
	public int countList = 10;
	public int maxPageList;

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
	public Collection<ClientRecord> clientList() {

		List<ClientRecord> list = new ArrayList<>();
		//MAXPAGELIST в онгИНИТЕ
		maxPageList = (cdb.get().client.size() / countList);

		int i = 0;

		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {

			if (i == countList * (pageList + 1)) break;

			if (cdb.get().client.size() == i) break;

			if (i >= pageList * countList) {
				ClientRecord clientRecord = new ClientRecord();
				clientRecord.id = cl.getValue().id;
				clientRecord.firstname = cl.getValue().firstname;
				clientRecord.lastname = cl.getValue().lastname;
				clientRecord.patronymic = cl.getValue().patronymic;
				clientRecord.character = cl.getValue().character;
				clientRecord.dateOfBirth = cl.getValue().dateOfBirth;
				clientRecord.totalAccountBalance = cl.getValue().totalAccountBalance;
				clientRecord.maximumBalance = cl.getValue().maximumBalance;
				clientRecord.minimumBalance = cl.getValue().minimumBalance;
				list.add(clientRecord);
			}
			i++;
		}
		return list;
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
		if (client.firstname == "") {return null;}

		List<Client> list = new ArrayList<>();
		/*Client c = new Client();
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
		}*/


		return list;

	}

	@Override
	public Collection<Client> editUserInfo(Client edit) {
		List<Client> list = new ArrayList<>();
		/*Client c = new Client();
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
		}*/


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

	@Override
	public ClientFilter clientListSet() {

		return null;
	}

	@Override
	public ClientRecord clientDetailsSave(ClientDetails clientDetails) {
		List<ClientRecord> list = new ArrayList<>();
		List<Client> listClient = new ArrayList<>();
		Client c = new Client();

		ClientRecord rec = new ClientRecord();
		c.firstname = clientDetails.firstname;
		c.lastname = clientDetails.lastname;
		c.patronymic = clientDetails.patronymic;
//		c.gender =  clientDetails.gender;
		c.dateOfBirth = clientDetails.dateOfBirth;
		c.character = clientDetails.character.name;
		c.addressOfRegistration = rndAddress(clientDetails.addressOfRegistration);
		c.addressOfResidence = rndAddress(clientDetails.addressOfResidence);
		cdb.get().client.put(cdb.get().client.size(), c);

		rec.minimumBalance = c.minimumBalance ;
		rec.firstname =c.firstname;
		list.add(rec);
		/*for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			list.add(cl.getValue());
		}
			пу
		return list;*/

		return rec;
	}

	@Override
	public Collection<ClientRecord> clientFilter(ClientFilter clientFilter) {
		List<ClientRecord> list = new ArrayList<>();
		if (clientFilter.offSet > maxPageList) {
			filter.offSet = maxPageList;
			return list;
		} else {
			//Eto ya dolzhen otpravit' v ngOnInit
			pageList = clientFilter.offSet;

			filter.offSet = clientFilter.offSet;
			filter.orderBy = clientFilter.orderBy;
			filter.sort = clientFilter.sort;
			filter.lastname = clientFilter.lastname;
			filter.firstname = clientFilter.firstname;
			filter.patronymic = clientFilter.patronymic;
		}
		int i = 0;

		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
			if (i == countList * (pageList + 1)) break;
			if (cdb.get().client.size() == i) break;
			if (i > pageList * countList - 1) {
				ClientRecord clientRecord = new ClientRecord();
				clientRecord.id = cl.getValue().id;
				clientRecord.firstname = cl.getValue().firstname;
				clientRecord.lastname = cl.getValue().lastname;
				clientRecord.patronymic = cl.getValue().patronymic;
				clientRecord.character = cl.getValue().character;
				clientRecord.dateOfBirth = cl.getValue().dateOfBirth;
				clientRecord.totalAccountBalance = cl.getValue().totalAccountBalance;
				clientRecord.maximumBalance = cl.getValue().maximumBalance;
				clientRecord.minimumBalance = cl.getValue().minimumBalance;
				list.add(clientRecord);
			}
			i++;
		}

		switch (clientFilter.orderBy) {
			case "firstname":
				list.sort(new ClientRegisterStand.SortedByFirstname(clientFilter.sort));
				break;
			case "character":
				list.sort(new ClientRegisterStand.SortedByCharacter(clientFilter.sort));
				break;
		}

		if (!clientFilter.firstname.equals("") || !clientFilter.lastname.equals("") || !clientFilter.patronymic.equals("")) {


			List<ClientRecord> list1 = new ArrayList<>();
			ClientRecord clientRecord = new ClientRecord();
			clientRecord.firstname = clientFilter.firstname;
			clientRecord.lastname = clientFilter.lastname;
			clientRecord.patronymic = clientFilter.patronymic;

			for (ClientRecord clientRecord1 : list) {
				if (clientRecord1.firstname.equals(clientRecord.firstname)
					&& (clientRecord1.lastname.equals(clientRecord.lastname) || clientFilter.lastname.isEmpty())
					&& (clientRecord1.patronymic.equals(clientRecord.patronymic) || clientFilter.patronymic.isEmpty())) {
					System.out.println("done");
					list1.add(clientRecord1);

				} else {
					System.out.println("NOT FOUND");
				}
			}
			return list1;
		} else {
			System.out.println(list);
			return list;
		}

	}


	private Address rndAddress(ClientAddr add) {
		Address address = new Address();
		address.street = add.street;
		address.home = add.house;
		address.apartment = add.flat;
		return address;

	}


	// теперь собственно реализуем интерфейс Comparator, для сортировки по названию
	public static class SortedByFirstname implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByFirstname(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2 ) {

			String str1 = obj1.firstname + obj1.lastname;
			String str2 = obj2.firstname + obj2.lastname;
			if(sort)
				return str1.compareTo(str2);
			else {
				return str2.compareTo(str1);
			}
		}

	}
	// теперь собственно реализуем интерфейс Comparator, для сортировки по названию
	public static class SortedByCharacter implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByCharacter(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2 ) {

			String str1 = obj1.character;
			String str2 = obj2.character;
			if(sort)
				return str1.compareTo(str2);
			else {
				return str2.compareTo(str1);
			}
		}

	}


}