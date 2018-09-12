package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by msultanova on 9/4/18.
 */
@Bean
public class ClienRegisterImpl implements ClientRegister {

    List<Client> clients = null;
    List<ClientRecord> clientRecords = null;
    List<Character> characters = null;
    List<Gender> genders = null;
    List<PhoneDetail> phoneDetails = null;


    public void createClients() {

        clients = new ArrayList<>();

        phoneDetails = new ArrayList<PhoneDetail>();
        PhoneDetail phoneDetail1 = new PhoneDetail(PhoneType.MOBILE, "Мобильный");
        PhoneDetail phoneDetail2 = new PhoneDetail(PhoneType.HOME, "Домашний");
        PhoneDetail phoneDetail3 = new PhoneDetail(PhoneType.WORK, "Рабочий");
        phoneDetails.add(phoneDetail1);
        phoneDetails.add(phoneDetail2);
        phoneDetails.add(phoneDetail3);


        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(phoneDetail1, "7474938358"));
        phones.add(new Phone(phoneDetail2, "7273810983"));

        genders = new ArrayList<Gender>();
        Gender male = new Gender(GenderType.MALE, "мужской");
        Gender female = new Gender(GenderType.FEMALE, "женский");
        genders.add(male);
        genders.add(female);

        characters = new ArrayList<>();
        Character opennesCharacter = new Character(CharacterType.OPENNESS, "открытый");
        Character agreeablenessCharacter = new Character(CharacterType.AGREEABLENESS, "любзеный");
        Character conscientiousnessCharacter = new Character(CharacterType.CONSCIENTIOUSNESS, "добросовестный");
        Character extraversionCharacter = new Character(CharacterType.EXTRAVERSION, "экстраверт");
        Character neuroticismCharacter = new Character(CharacterType.NEUROTICISM, "невротичный");

        characters.add(opennesCharacter);
        characters.add(agreeablenessCharacter);
        characters.add(conscientiousnessCharacter);
        characters.add(extraversionCharacter);
        characters.add(neuroticismCharacter);

        Date birthdayDate= new Date();

        try {
            birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("20/12/1998");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        clients.add(new Client(1, "Sultanova", "Madina", "Mahammadnova", female, birthdayDate, opennesCharacter, 20, 1000, 5, 5000, null, new Address("Mamyr-4", "311", 38), phones));
        clients.add(new Client(2, "Ajs", "Gvlv", "osoos", male, birthdayDate, agreeablenessCharacter, 7, 100, 0, 5, null, new Address("Mamyr-4", "311", 38), phones));
        clients.add(new Client(3, "Vsis", "Akkdd", "llsls", female, birthdayDate, extraversionCharacter, 75, 100, 8, 5545, null, new Address("Mamyr-4", "311", 38), phones));
        clients.add(new Client(4, "Tllxlx", "Bodd", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 100, 825, 574, null, new Address("Mamyr-4", "311", 38), phones));

    }

    @Override
    public List<ClientRecord> getClientList() {
        clientRecords = new ArrayList<>();
        createClients();
        if (clients != null) {
            for (Client client : clients) {
                //ClientRecord tempClientRecord = createRecordFromClient(client);
                clientRecords.add(createRecordFromClient(client));
            }
        }
        return clientRecords;
    }

    public ClientRecord createRecordFromClient(Client client){
        ClientRecord clientRecord = new ClientRecord();
        clientRecord.fio = client.surname + " " + client.name + " " + client.patronymic;
        clientRecord.age = client.age;
        clientRecord.totalBalance = client.totalBalance;
        clientRecord.minBalance = client.minBalance;
        clientRecord.maxBalance = client.maxBalance;
        clientRecord.character = client.character;
        clientRecord.clientId = client.id;
        return clientRecord;
    }
    public Client createClientFromToSave(ClientToSave toSave){
        Client client = new Client();
        client.name = toSave.name;
        client.surname = toSave.surname;
        client.patronymic = toSave.patronymic;
        client.actualAddress = toSave.actualAddress;
        client.age = toSave.age;
        client.birthDay = toSave.birthDay;
        client.gender = toSave.gender;
        client.character = toSave.character;
        client.id = toSave.clientID;
        client.phones = toSave.phones;
        client.registrationAddress = toSave.registrationAddress;
        return client;
    }

    public Client updateClientFromToSave(ClientToSave toSave, Client client){
        client.name = toSave.name;
        client.surname = toSave.surname;
        client.patronymic = toSave.patronymic;
        client.actualAddress = toSave.actualAddress;
        //client.age = toSave.age;
        client.birthDay = toSave.birthDay;
        client.gender = toSave.gender;
        client.character = toSave.character;
        if(toSave.clientID != -1)
            client.id = toSave.clientID;
        else
            client.id = clients.get(clients.size()-1).id+1;
        client.phones = toSave.phones;
        client.registrationAddress = toSave.registrationAddress;

        return client;
    }
    @Override
    public ClientDetail getClienDetailById(long id) {
        ClientDetail clientDetail = new ClientDetail(genders, characters, phoneDetails);
        ;

        if (clients != null) {
            for (Client client : clients) {
                if (client.id == id) {
                    clientDetail = clientDetail.initalize(new ClientDetail(client.surname, client.name, client.patronymic,client.age, client.gender, genders, client.birthDay, client.character, characters, client.actualAddress, client.registrationAddress, client.phones, phoneDetails, client.id));
                    //new ClientDetail(client.surname, client.name, client.patronymic, client.gender, genders, client.birthDay,  client.character, characters, client.actualAddress, client.registrationAddress, client.phones, client.id);
                }
            }
        }
        return clientDetail;
    }

    @Override
    public ClientRecord saveClient(ClientToSave toSave) {

        if (clients != null) {
            for (Client client : clients) {
                if (client.id == toSave.clientID) {
                    client = updateClientFromToSave(toSave, client);
                    return  createRecordFromClient(client);
                }
            }
            return createRecordFromClient(createClientFromToSave(toSave));
        }
        return null;
    }

    @Override
    public void deleteClient(long id) {
        if (clients != null) {

            for (Client client : clients) {
                if (client.id == id) {
                        clients.remove(client);
                        break;
                    //return createRecordFromClient(client);
                }
            }
        }
    }
}


