package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.stand.beans.ClientDb;
import kz.greetgo.sandbox.db.stand.model.ClientDot;
import kz.greetgo.util.RND;

import java.util.*;

@Bean
public class ClientRegisterStand implements ClientRegister {

	public BeanGetter<ClientDb> cdb;


	@Override
	public List<Charm> getCharm() {
		List<Charm> list = new ArrayList<>();
		for (Charm c : cdb.get().charm) {
			if (c.actually)
				list.add(c);
		}

		return list;
	}


	@Override
	public ClientRecord saveClient(ClientToSave clientDetails) {
		ClientRecord clientRecord = new ClientRecord();

		String key = RND.str(10);
		if (clientDetails.id == null) {
			ClientDot clientDot = new ClientDot();
			clientDot.id = cdb.get().client.size() + 1;
			clientDot.firstname = clientDetails.firstname;
			clientDot.lastname = clientDetails.lastname;
			clientDot.patronymic = clientDetails.patronymic;
			clientDot.gender = clientDetails.gender;
			clientDot.dateOfBirth = clientDetails.dateOfBirth;
			for (Charm ch : cdb.get().charm) {
				if (ch.id == clientDetails.characterId) {
					clientDot.character = ch;
				}
			}
			clientDot.addressOfRegistration = clientDetails.addressOfRegistration;
			clientDot.addressOfResidence = clientDetails.addressOfResidence;
			clientDot.phone = clientDetails.phone;
			cdb.get().client.put(key, clientDot);
			clientDot = cdb.get().client.get(key);
			clientRecord.id = clientDot.id;
			clientRecord.firstname = clientDot.firstname;
			clientRecord.lastname = clientDot.lastname;
			clientRecord.patronymic = clientDot.patronymic;
			clientRecord.characterName = clientDot.character.name;
			clientRecord.dateOfBirth = clientDot.dateOfBirth;
			clientRecord.totalAccountBalance = clientDot.totalAccountBalance;
			clientRecord.maximumBalance = clientDot.maximumBalance;
			clientRecord.minimumBalance = clientDot.minimumBalance;
			return clientRecord;
		} else {
			for (Map.Entry<String, ClientDot> item : cdb.get().client.entrySet()) {
				if (item.getValue().id == clientDetails.id) {
					key = item.getKey();
					item.getValue().firstname = clientDetails.firstname;
					item.getValue().lastname = clientDetails.lastname;
					item.getValue().patronymic = clientDetails.patronymic;
					item.getValue().gender = clientDetails.gender;
					for (Charm ch : cdb.get().charm) {
						if (ch.id == clientDetails.characterId) {
							item.getValue().character = ch;
						}
					}
					item.getValue().dateOfBirth = clientDetails.dateOfBirth;
					item.getValue().addressOfResidence = clientDetails.addressOfResidence;
					item.getValue().addressOfRegistration = clientDetails.addressOfRegistration;
					item.getValue().phone = clientDetails.phone;
				}
			}
			ClientDot clientDot = cdb.get().client.get(key);
			clientRecord.id = clientDot.id;
			clientRecord.firstname = clientDot.firstname;
			clientRecord.lastname = clientDot.lastname;
			clientRecord.patronymic = clientDot.patronymic;
			clientRecord.characterName = clientDot.character.name;
			clientRecord.dateOfBirth = clientDot.dateOfBirth;
			clientRecord.totalAccountBalance = clientDot.totalAccountBalance;
			clientRecord.maximumBalance = clientDot.maximumBalance;
			clientRecord.minimumBalance = clientDot.minimumBalance;
			return clientRecord;

		}

	}

	@Override
	public ClientDetails getClientDetails(Integer clientMarkId) {
		ClientDetails clientDetails = new ClientDetails();
		for (Map.Entry<String, ClientDot> item : cdb.get().client.entrySet()) {
			if (clientMarkId == item.getValue().id) {
				clientDetails.id = item.getValue().id;
				clientDetails.firstname = item.getValue().firstname;
				clientDetails.lastname = item.getValue().lastname;
				clientDetails.patronymic = item.getValue().patronymic;
				clientDetails.gender = item.getValue().gender;
				clientDetails.characterId = item.getValue().character.id;
//				clientDetails.dateOfBirth = item.getValue().dateOfBirth;
				clientDetails.addressOfRegistration = item.getValue().addressOfRegistration;
				clientDetails.addressOfResidence = item.getValue().addressOfResidence;
				clientDetails.phone = item.getValue().phone;
			}
		}
		return clientDetails;
	}

	@Override
	public void deleteClient(Integer clientMarkId) {
		String key = "";
		for (Map.Entry<String, ClientDot> item : cdb.get().client.entrySet()) {
			if (item.getValue().id == clientMarkId) {
				key = item.getKey();
			}
		}
		cdb.get().client.remove(key);
	}

	@Override
	public Charm getCharmById(Integer charmId) {
		if (charmId == null) {
			return null;
		}
		Charm charm = new Charm();
		for (int i = 0; i < cdb.get().charm.size(); i++) {
			if (charmId == cdb.get().charm.get(i).id) {
				return cdb.get().charm.get(i);
			}
		}
		charm.id = charmId;
		charm.name = String.valueOf(charmId);
		charm.actually = false;
		return charm;
	}



	public List<ClientRecord> filterInner(ClientFilter clientFilter) {
		if (clientFilter.page > clientFilter.pageTotal) clientFilter.page = clientFilter.pageTotal;
		if (clientFilter.page < 0) clientFilter.page = 0;

		List<ClientRecord> list = new ArrayList<>();
		List<ClientRecord> list1 = new ArrayList<>();
		for (Map.Entry<String, ClientDot> cl : cdb.get().client.entrySet()) {
			ClientRecord clientRecord = new ClientRecord();
			clientRecord.id = cl.getValue().id;
			clientRecord.firstname = cl.getValue().firstname;
			clientRecord.lastname = cl.getValue().lastname;
			clientRecord.patronymic = cl.getValue().patronymic;
			clientRecord.characterName = cl.getValue().character.name;
			clientRecord.dateOfBirth = cl.getValue().dateOfBirth;
			clientRecord.totalAccountBalance = cl.getValue().totalAccountBalance;
			clientRecord.maximumBalance = cl.getValue().maximumBalance;
			clientRecord.minimumBalance = cl.getValue().minimumBalance;
			list.add(clientRecord);
		}

		switch (clientFilter.orderBy) {
			case "firstname":
				list.sort(new SortedByFirstname(clientFilter.sort));
				break;
			case "character":
				list.sort(new SortedByCharacter(clientFilter.sort));
				break;
			case "dateOfBirth":
				list.sort(new SortedByDateOfBirth(clientFilter.sort));
				break;
			case "totalAccountBalance":
				list.sort(new SortedBytotalAccountBalance(clientFilter.sort));
				break;
			case "maximumBalance":
				list.sort(new SortedByMaximumBalance(clientFilter.sort));
				break;
			case "minimumBalance":
				list.sort(new SortedByMinimumBalance(clientFilter.sort));
				break;
		}


		if (clientFilter.firstname.equals("") && clientFilter.lastname.equals("") && clientFilter.patronymic.equals("")) {
			return list;
		} else {
			ClientRecord clientRecord = new ClientRecord();
			clientRecord.firstname = clientFilter.firstname;
			clientRecord.lastname = clientFilter.lastname;
			clientRecord.patronymic = clientFilter.patronymic;
			for (ClientRecord clientRecord1 : list) {
				if ((clientRecord1.firstname.equals(clientRecord.firstname) || clientFilter.firstname.isEmpty())
					&& (clientRecord1.lastname.equals(clientRecord.lastname) || clientFilter.lastname.isEmpty())
					&& (clientRecord1.patronymic.equals(clientRecord.patronymic) || clientFilter.patronymic.isEmpty())) {
					list1.add(clientRecord1);
				}
			}

			return list1;
		}
	}

	@Override
	public Integer getClientTotalRecord(ClientFilter clientFilter) {
		return filterInner(clientFilter).size();
	}

	@Override
	public List<ClientRecord> getClientList(ClientFilter clientFilter) {
		return cicleClientRecord(filterInner(clientFilter), clientFilter);
	}


	public List<ClientRecord> cicleClientRecord(Collection<ClientRecord> listClientRecord, ClientFilter clientFilter) {
		List<ClientRecord> list = new ArrayList<>();
		int i = 0;
		clientFilter.pageTotal = (int) Math.floor(clientFilter.recordTotal / clientFilter.recordSize);
		if (clientFilter.page > clientFilter.pageTotal) {
			clientFilter.page = clientFilter.pageTotal;
		}
		int page = clientFilter.page;
		for (ClientRecord clientRecord1 : listClientRecord) {
			i++;
			if (i > clientFilter.page * clientFilter.recordSize && i < (page + 1) * clientFilter.recordSize) {
				list.add(clientRecord1);
			}

		}
		return list;
	}


	public static class SortedByFirstname implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByFirstname(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2) {

			String str1 = obj1.firstname + obj1.lastname;
			String str2 = obj2.firstname + obj2.lastname;
			if (sort)
				return str1.compareTo(str2);
			else {
				return str2.compareTo(str1);
			}
		}

	}


	public static class SortedByCharacter implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByCharacter(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2) {

			String str1 = obj1.characterName;
			String str2 = obj2.characterName;
			if (sort)
				return str1.compareTo(str2);
			else {
				return str2.compareTo(str1);
			}
		}
	}


	public static class SortedByDateOfBirth implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByDateOfBirth(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2) {

			Date str1 = obj1.dateOfBirth;
			Date str2 = obj2.dateOfBirth;
			if (sort)
				return str1.compareTo(str2);
			else {
				return str2.compareTo(str1);
			}
		}

	}


	public static class SortedBytotalAccountBalance implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedBytotalAccountBalance(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2) {

			int str1 = obj1.totalAccountBalance;
			int str2 = obj2.totalAccountBalance;
			return compareInt(str1, str2, sort);
		}

	}

	public static class SortedByMaximumBalance implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByMaximumBalance(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2) {

			int str1 = obj1.maximumBalance;
			int str2 = obj2.maximumBalance;
			return compareInt(str1, str2, sort);
		}

	}

	public static class SortedByMinimumBalance implements Comparator<ClientRecord> {

		private boolean sort;

		public SortedByMinimumBalance(boolean sort) {
			this.sort = sort;
		}

		public int compare(ClientRecord obj1, ClientRecord obj2) {

			int str1 = obj1.minimumBalance;
			int str2 = obj2.minimumBalance;
			return compareInt(str1, str2, sort);
		}

	}

	public static int compareInt(int str1, int str2, boolean sort) {

		if (str1 > str2) {
			if (sort)
				return 1;
			return -1;
		} else if (str1 < str2) {
			if (sort)
				return -1;
			return 1;
		} else {
			return 0;
		}
	}


}