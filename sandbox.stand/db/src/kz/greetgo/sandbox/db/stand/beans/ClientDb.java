package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.db.stand.model.ClientDot;
import kz.greetgo.util.RND;

import java.util.*;

@Bean
public class ClientDb implements HasAfterInject {
    public Map<String, ClientDot> client = new HashMap<String, ClientDot>();
    public List<Charm> charm = new ArrayList<>();

    @Override
    public void afterInject() throws Exception {
        createCharm();
        for (int i = 1; i <= 115; i++) {
            ClientDot c = new ClientDot();
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
        for (int i = 116; i <= 230; i++) {
            ClientDot c = new ClientDot();
            c.id = i;
            c.firstname = "abu";
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
            phone.number = "+7" + (int) (Math.random() * 1000 + 100000000);
            phone.type = rndPhoneType(2);
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

    public void createCharm(){
        charm.add(createCharmInner("Ленивый", true));
        charm.add(createCharmInner("Бодрый",true));
        charm.add(createCharmInner("Смелый",true));
        charm.add(createCharmInner("Оптимистичный",true));
        charm.add(createCharmInner("Устойчивый",false));
        charm.add(createCharmInner("Тихий",false));
        charm.add(createCharmInner("Грустный",false));
    }

    public Charm createCharmInner(String name, boolean isActually){
        Charm c = new Charm();
        c.id = RND.plusInt(100);
        c.energy = RND.plusInt(100);
        c.description = RND.str(10);
        c.actually=isActually;
        c.name = name;

        return c;
    }
    public static Boolean RndBoolean(){
    	int i = (int) (Math.random()*10 -5);
    	if(i >= 0)
    	return true;
    	else
    		return false;
		}

    public static String RndDateOfBirth() {
        return (int) (Math.random() * 1000 + 1000) + "-" + (int) (Math.random() + 10) + "-" + (int) ((Math.random() + 10) + (Math.random() * 10) + 1);
    }

    public  Charm RndCharacter() {

        int rand = (int) (Math.random() * 7);

        return charm.get(rand);
    }


}

