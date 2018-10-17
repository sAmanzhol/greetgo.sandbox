package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.model.enums.AddressType;
import kz.greetgo.sandbox.controller.register.AddressRegister;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

public class AddressImplTest extends ParentTestNg{
    public BeanGetter<AddressRegister> addressRegister;

    @Test
    public void listAddress(){
        List<Address> addressList = null;
        //
        //
        addressList = addressRegister.get().list();
        //
        //
        assertThat(addressList).isNotNull();
        assertThat(addressList).isNotEmpty();
    }

    @Test
    public void getClientAddress(){
        List<Address> addressList = null;
        //
        //
        Long client_id = insertAddress();
        addressList = addressRegister.get().getByClientId(client_id);
        //
        //
        assertThat(addressList).isNotNull();
        assertThat(addressList).isNotEmpty();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getClientAddressWithNull(){
        List<Address> addressList = null;
        //
        //
        Long client_id = null;
        addressList = addressRegister.get().getByClientId(client_id);
        //
        //
    }

    @Test
    public void getClientAddressWithFakeId(){
        List<Address> addressList = null;
        //
        //
        Long client_id = -111l;
        addressList = addressRegister.get().getByClientId(client_id);
        //
        //
        assertThat(addressList).isEmpty();
    }


    @Test
    public void updateAddress(){

        Long client = -1l;
        String street = RND.str(5);
        String house = RND.intStr(2) + "Dom";
        String flat = RND.intStr(2) + "KV";

        Address address = new Address(null,client,AddressType.FACT,street,house,flat,true);

        //
        //
        Long insRetId = addressRegister.get().insert(address);
        Address retAddress = addressRegister.get().getById(insRetId);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retAddress).isNotNull();
        assertThat(retAddress.street).isEqualTo(street);
        assertThat(retAddress.house).isEqualTo(house);
        assertThat(retAddress.flat).isEqualTo(flat);


        address.id = retAddress.id;
        address.client = -2l;
        address.street = RND.intStr(5);
        address.house = RND.intStr(3) + "DomU";
        address.flat = RND.intStr(3) + "KVu";

        Address updatedAddress = addressRegister.get().update(address);

        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.street).isNotEqualTo(retAddress.street);
        assertThat(updatedAddress.house).isNotEqualTo(retAddress.house);
        assertThat(updatedAddress.flat).isNotEqualTo(retAddress.flat);
    }

    @Test
    public void getAddressById(){
        Random rnd = new Random();

        Long client = -1l;
        String street = RND.intStr(5);
        String house = rnd.nextInt(50) + "Dom";
        String flat = rnd.nextInt(50) + "KV";

        Address address = new Address(null,client,AddressType.REG,street,house,flat,true);
        //
        //
        Long insRetId = addressRegister.get().insert(address);
        Address retAddress = addressRegister.get().getById(insRetId);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retAddress).isNotNull();
        assertThat(retAddress.street).isEqualTo(street);
        assertThat(retAddress.house).isEqualTo(house);
        assertThat(retAddress.flat).isEqualTo(flat);
        assertThat(retAddress.client).isNotNull();
    }

    @Test
    public void getAddressByClientId(){
        Random rnd = new Random();

        Long client = -1l;
        String street = RND.intStr(5);
        String house = rnd.nextInt(50) + "Dom";
        String flat = rnd.nextInt(50) + "KV";

        Address address = new Address(null,client,AddressType.REG,street,house,flat,true);
        //
        //
        Long insRetId = addressRegister.get().insert(address);
        List<Address> retAddress = addressRegister.get().getByClientId(client);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retAddress).isNotNull();
        for(Address address1 : retAddress) {
            assertThat(address1.client).isNotNull();
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getAddressId_null(){
        Long id = null;
        //
        //
        Address retAddress = addressRegister.get().getById(id);
        //
        //
        assertThat(retAddress).isNull();
    }

    @Test
    public void getAddressByFakeId(){
        Long id = -1L;
        //
        //
        Address retAddress = addressRegister.get().getById(id);
        //
        //
        assertThat(retAddress).isNull();
    }

    @Test
    public Long insertAddress(){
        Random rnd = new Random();

        Long client = rnd.nextLong();
        String street = RND.intStr(5);
        String house = rnd.nextInt(50) + "Dom";
        String flat = rnd.nextInt(50) + "KV";

        Address address = new Address(null,client,AddressType.FACT,street,house,flat,true);

        //
        //
        Long insRetId = addressRegister.get().insert(address);
        Address retAddress = addressRegister.get().getById(insRetId);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retAddress).isNotNull();
        assertThat(retAddress.street).isEqualTo(street);
        assertThat(retAddress.house).isEqualTo(house);
        assertThat(retAddress.flat).isEqualTo(flat);

        return client;
    }


    @Test(expectedExceptions = NullPointerException.class)
    public void insertAddressNull(){

        Address address = new Address(null,null,null,null,null,null,false);
        //
        //
        Long insRetId = addressRegister.get().insert(address);
        //
        //
        assertThat(insRetId).isNull();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void insertNullAddress(){
        Address address = null;
        //
        //
        Long insRetId = addressRegister.get().insert(address);
        //
        //
        assertThat(insRetId).isNull();
    }

    @Test
    public void insertAddressWithInjection(){
        Address address = new Address(null,111l,AddressType.FACT,"AND DELETE *","AND SELECT *","AND SELECT *",true);
        //
        //
        Long insRetId = addressRegister.get().insert(address);
        //
        //
        assertThat(insRetId).isNotNull();
    }

    @Test
    public void deleteAddress(){
        Random rnd = new Random();

        Long client = -1l;
        String street = RND.intStr(5);
        String house = rnd.nextInt(50) + "Dom";
        String flat = rnd.nextInt(50) + "KV";

        Address address = new Address(null,client,AddressType.REG,street,house,flat,true);
        //
        //
        Long insRetId = addressRegister.get().insert(address);
        Address retAddress = addressRegister.get().getById(insRetId);
        System.out.println(insRetId);
        //
        //
        assertThat(insRetId).isNotNull();
        assertThat(retAddress).isNotNull();

        addressRegister.get().delete(insRetId);
    }

}
