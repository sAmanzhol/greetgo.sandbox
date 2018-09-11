package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.model.Client;
import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.db.stand.beans.ClientDb;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.util.RND;

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
	public int recordTotal;


	@Override
	public Collection<Charm> clientCharm() {
		return cdb.get().charm;
	}


	@Override
	public ClientRecord clientDetailsSave(ClientDetails clientDetails) {
		ClientRecord clientRecord = new ClientRecord();
		System.out.println(clientDetails.id);
		String key = RND.str(10);
		if (clientDetails.id == null) {
			Client client = new Client();
			client.id = cdb.get().client.size() + 1;
			client.firstname = clientDetails.firstname;
			client.lastname = clientDetails.lastname;
			client.patronymic = clientDetails.patronymic;
			client.gender = clientDetails.gender;
			client.dateOfBirth = clientDetails.dateOfBirth;
			for (Charm ch : cdb.get().charm) {
				if (ch.id == clientDetails.characterId) {
					client.character = ch;
				}
			}
			client.addressOfRegistration = clientDetails.addressOfRegistration;
			client.addressOfResidence = clientDetails.addressOfResidence;
			client.phone = clientDetails.phone;
			cdb.get().client.put(key, client);
			client = cdb.get().client.get(key);
			clientRecord.id = client.id;
			clientRecord.firstname = client.firstname;
			clientRecord.lastname = client.lastname;
			clientRecord.patronymic = client.patronymic;
			clientRecord.characterName = client.character.name;
			clientRecord.dateOfBirth = client.dateOfBirth;
			clientRecord.totalAccountBalance = client.totalAccountBalance;
			clientRecord.maximumBalance = client.maximumBalance;
			clientRecord.minimumBalance = client.minimumBalance;
			return clientRecord;
		} else {
			for (Map.Entry<String, Client> item : cdb.get().client.entrySet()) {
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
			Client client = cdb.get().client.get(key);
			clientRecord.id = client.id;
			clientRecord.firstname = client.firstname;
			clientRecord.lastname = client.lastname;
			clientRecord.patronymic = client.patronymic;
			clientRecord.characterName = client.character.name;
			clientRecord.dateOfBirth = client.dateOfBirth;
			clientRecord.totalAccountBalance = client.totalAccountBalance;
			clientRecord.maximumBalance = client.maximumBalance;
			clientRecord.minimumBalance = client.minimumBalance;
			return clientRecord;

		}

	}

	@Override
	public ClientDetails clientDetailsSet(ClientRecord clientMark) {
		ClientDetails clientDetails = new ClientDetails();
		for (Map.Entry<String, Client> item : cdb.get().client.entrySet()) {
			if (clientMark.id == item.getValue().id) {
				clientDetails.id = item.getValue().id;
				clientDetails.firstname = item.getValue().firstname;
				clientDetails.lastname = item.getValue().lastname;
				clientDetails.patronymic = item.getValue().patronymic;
				clientDetails.gender = item.getValue().gender;
				clientDetails.characterId = item.getValue().character.id;
				clientDetails.dateOfBirth = item.getValue().dateOfBirth;
				clientDetails.addressOfRegistration = item.getValue().addressOfRegistration;
				clientDetails.addressOfResidence = item.getValue().addressOfResidence;
				clientDetails.phone = item.getValue().phone;
			}
		}
		return clientDetails;
	}

	@Override
	public ClientDetails clientDetailsDelete(ClientRecord clientMark) {
		String key = "";
		for (Map.Entry<String, Client> item : cdb.get().client.entrySet()) {
			if (item.getValue().id == clientMark.id) {
				key = item.getKey();
			}
		}
		cdb.get().client.remove(key);
		return null;
	}



	public List<ClientRecord> filterInner(ClientFilter clientFilter){
		if (clientFilter.page > clientFilter.pageTotal) clientFilter.page = clientFilter.pageTotal;
		if (clientFilter.page < 0) clientFilter.page = 0;

		List<ClientRecord> list = new ArrayList<>();
		List<ClientRecord> list1 = new ArrayList<>();
		for (Map.Entry<String, Client> cl : cdb.get().client.entrySet()) {
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
			for (ClientRecord clientRecord1 : list) {
				System.out.print(clientRecord1.id + "ITS ---- LIST222-----");
			}

			return list;
//			return cicleClientRecord(list, clientFilter);
		}


		else {
			ClientRecord clientRecord = new ClientRecord();
			clientRecord.firstname = clientFilter.firstname;
			clientRecord.lastname = clientFilter.lastname;
			clientRecord.patronymic = clientFilter.patronymic;
			for (ClientRecord clientRecord1 : list) {
				if ((clientRecord1.firstname.equals(clientRecord.firstname) ||clientFilter.firstname.isEmpty() )
						&& (clientRecord1.lastname.equals(clientRecord.lastname) || clientFilter.lastname.isEmpty())
						&& (clientRecord1.patronymic.equals(clientRecord.patronymic) || clientFilter.patronymic.isEmpty())) {
					System.out.println("done");
					list1.add(clientRecord1);
				} else {
					System.out.println("NOT FOUND");
				}
			}
			for (ClientRecord clientRecord1 : list1) {
				System.out.print(clientRecord1.id + "ITS --- LIST1 ----");
			}

			return list1;
//			return cicleClientRecord(list1, clientFilter);
		}
	}

	@Override
	public Integer getClientTotalRecord(ClientFilter clientFilter) {
		return  filterInner(clientFilter).size();
	}

	@Override
	public Collection<ClientRecord> getClientList(ClientFilter clientFilter) {
		return cicleClientRecord(filterInner(clientFilter),clientFilter);
	}

	/*public int total(ClientFilter filter){
		List<ClientRecord> list = filterInner(filter);

		return list.size();
	}

	public List<ClientRecord> cList(){
		List<ClientRecord> list = filterInner(filter);

		return cutList(list, page, pageSize);
	}*/


	public Collection<ClientRecord> cicleClientRecord(Collection<ClientRecord> listClientRecord, ClientFilter clientFilter) {
		List<ClientRecord> list = new ArrayList<>();
		int i = 0;
		recordTotal= listClientRecord.size();
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

			System.out.print(clientRecord1.id + "ITS ---- LIST3333-----");
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

			String str1 = obj1.dateOfBirth;
			String str2 = obj2.dateOfBirth;
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