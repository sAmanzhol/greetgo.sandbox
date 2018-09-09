package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.model.Client;
import kz.greetgo.util.RND;

import java.util.*;

@Bean
public class ClientDb implements HasAfterInject {
    public Map<String, Client> client = new HashMap<String, Client>();
    public List<Charm> charm = new ArrayList<>();

    @Override
    public void afterInject() throws Exception {
        for (int i = 0; i < 10; i++) {
            Charm c = new Charm();
            c.id = i;
            c.energy = i + 10;
            c.description = RND.str(10);
            c.name = RND.str(10);
            charm.add(c);
        }
        for (int i = 1; i <= 58; i++) {
            Client c = new Client();
            c.id = i;
            c.firstname = "nazar";
            c.lastname = RND.str(8);
            c.patronymic = RND.str(8);
            c.character = RndCharacter();
            c.dateOfBirth = RndDateOfBirth();
            c.totalAccountBalance = RND.plusInt(1000);
            c.maximumBalance = RND.plusInt(1000);
            c.minimumBalance = RND.plusInt(1000);
            c.gender = GenderType.MALE;
            c.addressOfResidence = rndAddress();
            c.addressOfResidence.type = AddrType.FACT;
            c.addressOfRegistration = rndAddress();
            c.addressOfRegistration.type = AddrType.REG;
            c.phone = rndPhoneClient();
            client.put(RND.str(10),c);
        }



    }


    public static ClientAddr rndAddress() {
        ClientAddr address = new ClientAddr();
        address.street = RND.str(9);
        address.house = RND.str(9);
        address.flat = RND.str(9);
        return address;
    }

    public static List<ClientPhone> rndPhoneClient() {
        List<ClientPhone> list = new ArrayList<>();
        ClientPhone phone = new ClientPhone();
        for (int i = 0; i < 4; i++) {
            phone.number = "+7" + (int) (Math.random() * 1000 + 100000);
            phone.type = rndPhoneType(i);
            list.add(phone);
        }
        return list;
    }

    public static PhoneType rndPhoneType(int i) {
        List<PhoneType> list = new ArrayList<>();
        list.add(PhoneType.HOME);
        list.add(PhoneType.WORK);
        list.add(PhoneType.MOBILE);
        list.add(PhoneType.EMBEDDED);
        return list.get(i);

    }

    public static String RndDateOfBirth() {
        return (int) (Math.random() * 1000 + 1000) + "-" + (int) (Math.random() + 10) + "-" + (int) ((Math.random() + 10) + (Math.random() * 10) + 1);
    }

    public  Charm RndCharacter() {

        int rand = (int) (Math.random() * 9 + 1);

        return charm.get(rand);
    }


}

