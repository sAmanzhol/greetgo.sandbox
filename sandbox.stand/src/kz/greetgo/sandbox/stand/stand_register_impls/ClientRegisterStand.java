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

            String str1 = obj1.character.name;
            String str2 = obj2.character.name;
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
            return compareInt(str1,str2,sort);
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
            return compareInt(str1,str2,sort);
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