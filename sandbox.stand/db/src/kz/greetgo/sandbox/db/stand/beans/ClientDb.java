package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.register.model.Address;
import kz.greetgo.sandbox.controller.register.model.Client;
import kz.greetgo.sandbox.controller.register.model.Phone;
import kz.greetgo.util.RND;

import java.util.*;

@Bean
public class ClientDb implements HasAfterInject {
    public Map<Integer, Client> client = new HashMap <Integer, Client>();
    public static List<String> character = new ArrayList<>();


    @Override
    public void afterInject() throws Exception {

        for(int i =0; i<10;i++){
            character.add(RND.str(6));

        }
        for(int i=0;i<55;i++){
            Client c = new Client();
            c.id=client.size();
            c.firstname=RND.str(8);
            c.lastname=RND.str(8);
            c.patronymic=RND.str(8);
            c.gender=RND.str(8);
            c.dateOfBirth=dateOfBirth();
            c.character=RndCharacter();
            c.setTotalAccountBalance(RND.plusInt(1000));
            c.setMaximumBalance(RND.plusInt(1000));
            c.setMinimumBalance(RND.plusInt(1000));
            c.addressOfRegistration=rndAddress();
            c.addressOfResidence=rndAddress();
            c.phone =rndPhone();
            client.put(i,c);
        }

        Client.setCounter();

    }

    private Address rndAddress() {

        Address address = new Address();
        address.street= RND.str(9);
        address.home=RND.str(9);
        address.apartment=RND.str(9);
        return address;

    }
    private Phone rndPhone(){
        Phone phone =new Phone();
        phone.home =RND.plusInt(100000000);
        phone.work =RND.plusInt(100000000);
        phone.mobile1 =RND.plusInt(100000000);
        phone.mobile2 =RND.plusInt(100000000);
        phone.mobile3 =RND.plusInt(100000000);
        return phone;
    }
    private String dateOfBirth(){
        return (int) (Math.random() * 1000 + 1000) + "-"  + (int) (Math.random() + 10)  + "-"+ (int) ((Math.random() + 10)  + (Math.random() *10 ) + 1 );
		}

		private static String RndCharacter(){

        int rand = (int) (Math.random()*9+1);

        return character.get(rand);
    }


}

