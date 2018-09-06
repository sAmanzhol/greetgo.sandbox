package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msultanova on 9/4/18.
 */
@Bean
public class ClienRegisterImpl implements ClientRegister {

    List<Client> clients = null;
    List<ClientRecord> clientRecords = null;

    public void createClients() {

        clients = new ArrayList<>();
        List<Phone> phones = new ArrayList<>();

        phones.add(new Phone(PhoneType.MOBILE, "7474938358"));
        phones.add(new Phone(PhoneType.HOME, "7273810983"));

        clients.add(new Client(1, "Sultanova", "Madina", "Mahammadnova", CharacterType.OPENNESS, 20, 1000, 5, 5000, null, new Address("Mamyr-4", "311", 38), null));
        clients.add(new Client(2, "Ajs", "Gvlv", "Mahammadnova", CharacterType.OPENNESS, 7, 100, 0, 5, null, new Address("Mamyr-4", "311", 38), null));
        clients.add(new Client(3, "Vsis", "Akkdd", "Mahammadnova", CharacterType.OPENNESS, 75, 100, 8, 5545, null, new Address("Mamyr-4", "311", 38), null));
        clients.add(new Client(4, "Tllxlx", "Bodd", "Mahammadnova", CharacterType.OPENNESS, 1, 100, 825, 574, null, new Address("Mamyr-4", "311", 38), null));

    }

    @Override
    public List<ClientRecord> getClientList() {

        clientRecords = new ArrayList<>();

        createClients();
        if (clients != null) {


            for (Client client : clients) {
                ClientRecord tempClientRecord = new ClientRecord();
                //tempClientRecord.setFio(client.surname + " " + client.name + " " + client.patronymic);
                tempClientRecord.fio = client.surname + " " + client.name + " " + client.patronymic;
                tempClientRecord.age = client.age;
                tempClientRecord.totalBalance = client.totalBalance;
                tempClientRecord.minBalance = client.minBalance;
                tempClientRecord.maxBalance = client.maxBalance;
                tempClientRecord.character = client.character;
                tempClientRecord.clientId = client.id;
                clientRecords.add(tempClientRecord);
            }
        }



//        clientRecords.add(new ClientRecord("Aida Aidova", "melancholic", 14, 10, 0, 11));
//        clientRecords.add(new ClientRecord("Almas Adam", "melancholic", 100, 180, 15, 350));
        return clientRecords;
    }

    public ClientDetail getClienDetailById(long id){
        ClientDetail clientDetail = null;

        if(clients != null){
            for(Client client : clients){
                if(client.id == id){
                    clientDetail = new ClientDetail(client.surname, client.name, client.patronymic, client.character, client.actualAddress, client.registrationAddress, client.phones, client.id);
                }
            }
        }

        return clientDetail;

    }


}


