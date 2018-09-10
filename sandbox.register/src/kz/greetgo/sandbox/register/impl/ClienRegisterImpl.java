package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.register.ClientRegister;

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


    public void createClients() {

        clients = new ArrayList<>();
        List<Phone> phones = new ArrayList<>();

        phones.add(new Phone(PhoneType.MOBILE, "7474938358"));
        phones.add(new Phone(PhoneType.HOME, "7273810983"));

        genders = new ArrayList<Gender>();
        Gender male = new Gender(GenderType.M, "мужской");
        Gender female = new Gender(GenderType.F, "женский");
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

        //Date d = new Date("1998-12-08");
        clients.add(new Client(1, "Sultanova", "Madina", "Mahammadnova", female, "1998-12-08", opennesCharacter, 20, 1000, 5, 5000, null, new Address("Mamyr-4", "311", 38), phones));
        clients.add(new Client(2, "Ajs", "Gvlv", "osoos", male, "1998-12-08", agreeablenessCharacter, 7, 100, 0, 5, null, new Address("Mamyr-4", "311", 38), phones));
        clients.add(new Client(3, "Vsis", "Akkdd", "llsls", female, "1998-12-08", extraversionCharacter, 75, 100, 8, 5545, null, new Address("Mamyr-4", "311", 38), phones));
        clients.add(new Client(4, "Tllxlx", "Bodd", "lslslsl", male, "1998-12-08", neuroticismCharacter, 1, 100, 825, 574, null, new Address("Mamyr-4", "311", 38), phones));

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
        return clientRecords;
    }

    @Override
    public ClientDetail getClienDetailById(long id) {
        ClientDetail clientDetail = null;

        if (clients != null) {
            for (Client client : clients) {
                if (client.id == id) {
                    clientDetail = new ClientDetail(client.surname, client.name, client.patronymic, client.gender, genders, client.birthDay, client.character, characters, client.actualAddress, client.registrationAddress, client.phones, client.id);
                    //new ClientDetail(client.surname, client.name, client.patronymic, client.gender, genders, client.birthDay,  client.character, characters, client.actualAddress, client.registrationAddress, client.phones, client.id);
                }
            }
        }
        return clientDetail;
    }
}


