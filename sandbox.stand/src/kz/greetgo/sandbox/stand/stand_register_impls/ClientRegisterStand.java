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
    public ClientFilter filter = new ClientFilter();

    @Override
    public ClientFilter clientFilterSet() {
        return filter;
    }

    @Override
    public Collection<Charm> clientCharm() {
        return cdb.get().charm;
    }

    @Override
    public ClientRecord clientDetailsSave(ClientDetails clientDetails) {
            ClientRecord clientRecord = new ClientRecord();
            String key = RND.str(10);
        if(clientDetails.id==0){
            Client client = new Client();
            client.id =cdb.get().client.size();
            client.firstname=clientDetails.firstname;
            client.lastname=clientDetails.lastname;
            client.patronymic=clientDetails.patronymic;
            client.gender=clientDetails.gender;
            client.dateOfBirth=clientDetails.dateOfBirth;
            client.character=clientDetails.character;
            client.addressOfRegistration=clientDetails.addressOfRegistration;
            client.addressOfResidence=clientDetails.addressOfResidence;
            client.phone=clientDetails.phone;
            cdb.get().client.put(key, client);
            client = cdb.get().client.get(key);
            clientRecord.id=client.id;
            clientRecord.firstname=client.firstname;
            clientRecord.lastname=client.lastname;
            clientRecord.patronymic=client.patronymic;
            clientRecord.character=client.character;
            clientRecord.dateOfBirth=client.dateOfBirth;
            clientRecord.totalAccountBalance=client.totalAccountBalance;
            clientRecord.maximumBalance=client.maximumBalance;
            clientRecord.minimumBalance=client.minimumBalance;
            return  clientRecord;
        }
        else{
            for(Map.Entry<String, Client> item : cdb.get().client.entrySet()){
                if(item.getValue().id == clientDetails.id){
                    key = item.getKey();
                    item.getValue().firstname=clientDetails.firstname;
                    item.getValue().lastname=clientDetails.lastname;
                    item.getValue().patronymic=clientDetails.patronymic;
                    item.getValue().gender=clientDetails.gender;
                    item.getValue().character=clientDetails.character;
                    item.getValue().dateOfBirth=clientDetails.dateOfBirth;
                    item.getValue().addressOfResidence=clientDetails.addressOfResidence;
                    item.getValue().addressOfRegistration=clientDetails.addressOfRegistration;
                    item.getValue().phone=clientDetails.phone;
                }
            }
            Client client = cdb.get().client.get(key);
            clientRecord.id=client.id;
            clientRecord.firstname=client.firstname;
            clientRecord.lastname=client.lastname;
            clientRecord.patronymic=client.patronymic;
            clientRecord.character=client.character;
            clientRecord.dateOfBirth=client.dateOfBirth;
            clientRecord.totalAccountBalance=client.totalAccountBalance;
            clientRecord.maximumBalance=client.maximumBalance;
            clientRecord.minimumBalance=client.minimumBalance;
            return clientRecord;

        }

    }

    @Override
    public ClientDetails clientDetailsSet(ClientRecord clientMark) {
        ClientDetails clientDetails = new ClientDetails();
        for(Map.Entry<String, Client> item : cdb.get().client.entrySet()){
            if(clientMark.id == item.getValue().id){
                clientDetails.id=item.getValue().id;
                clientDetails.firstname=item.getValue().firstname;
                clientDetails.lastname=item.getValue().lastname;
                clientDetails.patronymic=item.getValue().patronymic;
                clientDetails.gender=item.getValue().gender;
                clientDetails.character=item.getValue().character;
                clientDetails.dateOfBirth=item.getValue().dateOfBirth;
                clientDetails.addressOfRegistration=item.getValue().addressOfRegistration;
                clientDetails.addressOfResidence=item.getValue().addressOfResidence;
                clientDetails.phone=item.getValue().phone;
            }
        }
        return clientDetails;
    }

    @Override
    public ClientDetails clientDetailsDelete(ClientRecord clientMark) {
        String key = "";
        for(Map.Entry<String, Client> item : cdb.get().client.entrySet()){
            if(item.getValue().id == clientMark.id){
                key = item.getKey();
            }
        }
        cdb.get().client.remove(key);
        return null;
    }

    @Override
    public Collection<ClientRecord> clientList() {
        filter.recordTotal = cdb.get().client.size();
        filter.pageTotal = (int) Math.floor(filter.recordTotal / filter.recordSize);
        List<ClientRecord> list = new ArrayList<>();
        int i = 0;
        int page = filter.page;
        for (Map.Entry<String, Client> cl : cdb.get().client.entrySet()) {
            if (i == filter.recordTotal) break;
            if (i > page * filter.recordSize && i < (page + 1) * filter.recordSize) {
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
    public Collection<ClientRecord> clientFilter(ClientFilter clientFilter) {
        filter = clientFilter;
        if (filter.page > filter.pageTotal) filter.page = filter.pageTotal;
        if(filter.page < 0) filter.page =0;

        List<ClientRecord> list = new ArrayList<>();
        List<ClientRecord> list1 = new ArrayList<>();
        for (Map.Entry<String, Client> cl : cdb.get().client.entrySet()) {
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

        if (!filter.firstname.equals("") || !filter.lastname.equals("") || !filter.patronymic.equals("")) {
            ClientRecord clientRecord = new ClientRecord();
            clientRecord.firstname = clientFilter.firstname;
            clientRecord.lastname = clientFilter.lastname;
            clientRecord.patronymic = clientFilter.patronymic;
            for (ClientRecord clientRecord1 : list) {
                if (clientRecord1.firstname.equals(clientRecord.firstname)
                        && (clientRecord1.lastname.equals(clientRecord.lastname) || filter.lastname.isEmpty())
                        && (clientRecord1.patronymic.equals(clientRecord.patronymic) || filter.patronymic.isEmpty())) {
                    System.out.println("done");
                    list1.add(clientRecord1);
                } else {
                    System.out.println("NOT FOUND");
                }
            }

            return cicleClientRecord(list1);
        }

        System.out.println(filter.firstname);
        return cicleClientRecord(list);
    }

    public Collection<ClientRecord> cicleClientRecord(List<ClientRecord> clientRecord) {
        List<ClientRecord> list = new ArrayList<>();
        int i = 0;
        filter.recordTotal = clientRecord.size();
        filter.pageTotal = (int) Math.floor(filter.recordTotal / filter.recordSize);
        if (filter.page > filter.pageTotal) {
            filter.page = filter.pageTotal;
        }
        int page = filter.page;
        for (ClientRecord clientRecord1 : clientRecord) {
            if (i > filter.page * filter.recordSize && i < (page + 1) * filter.recordSize) {
                list.add(clientRecord1);
            }
            i++;
        }
        return list;
    }
    //filter


    //	@Override
//	public Collection<Client> getUserInfo() {
//
//		List<Client> client = new ArrayList<>();
//		int i = 0;
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			if (i == 9) break;
//
//			client.add(cl.getValue());
//			i++;
//		}
//		return client;
//
//	}
//
//
//
//
//	@Override
//	public Collection<Client> getFilter(String firstname, String lastname, String patronymic) {
//
//		List<Client> list = new ArrayList<>();
//
//		Client c = new Client();
//		Client.setCounter();
//		c.firstname = firstname;
//
//		c.lastname = lastname;
//		c.patronymic = patronymic;
//
//
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//
//			if (cl.getValue().firstname.equals(c.firstname) || cl.getValue().lastname.equals(c.lastname) || cl.getValue().patronymic.equals(c.patronymic)) {
//				System.out.println("done");
//				list.add(cl.getValue());
//			}
//		}
//
//		return list;
//
//	}
//
//	@Override
//	public Collection<Client> getUserSort(String sort) {
//
//		List<Client> list = new ArrayList<>();
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			list.add(cl.getValue());
//		}
//
//		switch (sort) {
//			case "firstname":
//				list.sort(new Client.SortedByFirstname());
//				break;
//			case "character":
//				list.sort(new Client.SortedByCharacter());
//				break;
//			case "dateOfBirth":
//				list.sort(new Client.SortedBydateOfBirth());
//				break;
//			case "totalAccountBalance":
//				list.sort(new Client.SortedByTotalAccountBalance());
//				break;
//			case "maximumBalance":
//				list.sort(new Client.SortedByMaximumBalance());
//				break;
//			case "minimumBalance":
//				list.sort(new Client.SortedByMinimumBalance());
//				break;
//		}
//		cdb.get().client.clear();
//		for (int i = 0; i < list.size(); i++) {
//
//			cdb.get().client.put(i, list.get(i));
//		}
//		return getUserInfo();
//
//
//	}
//
//	@Override
//	public Double pagination() {
//
//		int sizes = cdb.get().client.size();
//		page = Math.floor(sizes / 10);
//		if (sizes % 10 != 0) {
//			++page;
//		}
//
//		return page;
//	}
//
//	@Override
//	public Collection<Client> getPagination(Integer index) {
//		List<Client> client = new ArrayList<>();
//
//
//		if (index == null) {
//			page = 0;
//		} else
//			page = index;
//		int counter = 0;
//		int i = (((int) page * 10));
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//
//			if (counter >= i && counter < (((int) page * 10) + 10)) {
//
//				client.add(cl.getValue());
//			}
//
//			counter++;
//		}
//
//
//		return client;
//
//
//	}
//
//	@Override
//	public Collection<Client> addUserInfo(Client client) {
//		if (client.firstname == "") {return null;}
//
//		List<Client> list = new ArrayList<>();
//		/*Client c = new Client();
//		c.firstname = client.firstname;
//		c.lastname = client.lastname;
//		c.patronymic = client.patronymic;
//		c.gender = client.gender;
//		c.dateOfBirth = client.dateOfBirth;
//		c.character = client.character;
//		c.totalAccountBalance = client.totalAccountBalance;
//		c.maximumBalance = client.maximumBalance;
//		c.minimumBalance = client.minimumBalance;
//		c.addressOfRegistration = rndAddress(client.addressOfRegistration);
//		c.addressOfResidence = rndAddress(client.addressOfResidence);
//		c.phone = rndPhone(client.phone);
//		cdb.get().client.put(cdb.get().client.size(), c);
//
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			list.add(cl.getValue());
//		}*/
//
//
//		return list;
//
//	}
//
//	@Override
//	public Collection<Client> editUserInfo(Client edit) {
//		List<Client> list = new ArrayList<>();
//		/*Client c = new Client();
//		Client.setCounter();
//		c.id = edit.id;
//		c.firstname = edit.firstname;
//		c.lastname = edit.lastname;
//		c.patronymic = edit.patronymic;
//		c.gender = edit.gender;
//		c.dateOfBirth = edit.dateOfBirth;
//		c.character = edit.character;
//		c.totalAccountBalance = edit.totalAccountBalance;
//		c.maximumBalance = edit.maximumBalance;
//		c.minimumBalance = edit.minimumBalance;
//		c.addressOfRegistration = rndAddress(edit.addressOfRegistration);
//		c.addressOfResidence = rndAddress(edit.addressOfResidence);
//		c.phone = rndPhone(edit.phone);
//		cdb.get().client.replace(c.id, c);
//
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			list.add(cl.getValue());
//		}*/
//
//
//		return list;
//	}
//
//	@Override
//	public Collection<Client> deleteUserInfo(Integer id) {
//		List<Client> list = new ArrayList<>();
//		if (id == null) {
//			id = 0;
//		}
//
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			if (cl.getValue().id == id) continue;
//			list.add(cl.getValue());
//		}
//		cdb.get().client.clear();
//
//		for (int i = 0; i < list.size(); i++) {
//
//			cdb.get().client.put(i, list.get(i));
//		}
//
//		return getUserInfo();
//
//	}
//
//	@Override
//	public Collection<Client> getClientForEdit(Integer id) {
//		List<Client> list = new ArrayList<>();
//		if (id == null) {
//			id = 0;
//		}
//		for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			if (cl.getValue().id == id) {
//				list.add(cl.getValue());
//			}
//		}
//
//
//		return list;
//	}
//
//
//	@Override
//	public List<String> getCharacter() {
//		return cdb.get().character;
//	}
//
//	@Override
//	public ClientFilter clientListSet() {
//
//		return null;
//	}
//
//	@Override
//	public ClientRecord clientDetailsSave(ClientDetails clientDetails) {
//		List<ClientRecord> list = new ArrayList<>();
//		List<Client> listClient = new ArrayList<>();
//		Client c = new Client();
//
//		ClientRecord rec = new ClientRecord();
//		c.firstname = clientDetails.firstname;
//		c.lastname = clientDetails.lastname;
//		c.patronymic = clientDetails.patronymic;
////		c.gender =  clientDetails.gender;
//		c.dateOfBirth = clientDetails.dateOfBirth;
//		c.character = clientDetails.character.name;
//		c.addressOfRegistration = rndAddress(clientDetails.addressOfRegistration);
//		c.addressOfResidence = rndAddress(clientDetails.addressOfResidence);
//		cdb.get().client.put(cdb.get().client.size(), c);
//
//		rec.minimumBalance = c.minimumBalance ;
//		rec.firstname =c.firstname;
//		list.add(rec);
//		/*for (Map.Entry<Integer, Client> cl : cdb.get().client.entrySet()) {
//			list.add(cl.getValue());
//		}
//			пу
//		return list;*/
//
//		return rec;
//	}
//
//
//
//
//	private Address rndAddress(ClientAddr add) {
//		Address address = new Address();
//		address.street = add.street;
//		address.home = add.house;
//		address.apartment = add.flat;
//		return address;
//
//	}
//
//
    // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
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

    // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
    public static class SortedByCharacter implements Comparator<ClientRecord> {

        private boolean sort;

        public SortedByCharacter(boolean sort) {
            this.sort = sort;
        }

        public int compare(ClientRecord obj1, ClientRecord obj2) {

            String str1 = obj1.character.name;
            String str2 = obj2.character.name;
            if (sort)
                return str1.compareTo(str2);
            else {
                return str2.compareTo(str1);
            }
        }
    }

        // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
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

    // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
    public static class SortedBytotalAccountBalance implements Comparator<ClientRecord> {

        private boolean sort;

        public SortedBytotalAccountBalance(boolean sort) {
            this.sort = sort;
        }

        public int compare(ClientRecord obj1, ClientRecord obj2) {

            int str1 = obj1.totalAccountBalance;
            int str2 = obj2.totalAccountBalance;
            return compareInt(str1,str2,sort);
        }

    }
    // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
    public static class SortedByMaximumBalance implements Comparator<ClientRecord> {

        private boolean sort;

        public SortedByMaximumBalance(boolean sort) {
            this.sort = sort;
        }

        public int compare(ClientRecord obj1, ClientRecord obj2) {

            int str1 = obj1.maximumBalance;
            int str2 = obj2.maximumBalance;
            return compareInt(str1,str2,sort);
        }

    }
    // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
    public static class SortedByMinimumBalance implements Comparator<ClientRecord> {

        private boolean sort;

        public SortedByMinimumBalance(boolean sort) {
            this.sort = sort;
        }

        public int compare(ClientRecord obj1, ClientRecord obj2) {

            int str1 = obj1.minimumBalance;
            int str2 = obj2.minimumBalance;
            return compareInt(str1,str2,sort);
        }

    }
    public static int compareInt(int str1, int str2, boolean sort ){

            if(str1>str2){
                if(sort)
                return 1;
                return -1;}
            else if (str1<str2){
                if(sort)
                return -1;
                return 1;}
            else{
                return 0;
            }
    }



}