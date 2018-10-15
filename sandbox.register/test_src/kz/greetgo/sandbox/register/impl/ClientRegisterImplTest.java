package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientReqParams;
import kz.greetgo.sandbox.controller.model.enums.Gender;
import kz.greetgo.sandbox.controller.model.enums.Sort;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {
    public BeanGetter<ClientRegister> clientRegister;


    @Test
    void clientFilterTest(){
        ClientReqParams params = new ClientReqParams();
        params.limit = 10l;
        params.offset = 0l;
        params.colName = "maxbal";
        params.order = Sort.ASC;
        params.patronymicFilterVal = "";
        params.nameFilterVal = "";
        params.surnameFilterVal = "Л";
        //
        //
        List<Client> clientList = clientRegister.get().getListByParam(params);
        //
        //
        assertThat(clientList).isNotNull();
        assertThat(clientList).isNotEmpty();
        assertThat(clientList.get(clientList.size()-1).id < clientList.get(0).id );
        System.out.println(clientList);
        for (Client client : clientList)
        {
            assertThat(client.surname.charAt(0)).isEqualTo(params.surnameFilterVal.charAt(0));
        }

    }

    @Test
    void clientMaxbalFilterTest(){
        ClientReqParams params = new ClientReqParams();
        params.limit = 10l;
        params.offset = 0l;
        params.colName = "maxbal";
        params.order = Sort.ASC;
        params.patronymicFilterVal = "";
        params.nameFilterVal = "";
        params.surnameFilterVal = "";
        //
        //
        List<Client> clientList = clientRegister.get().getListByParam(params);
        //
        //
        assertThat(clientList).isNotNull();
        assertThat(clientList).isNotEmpty();
        assertThat(clientList.get(clientList.size()-1).maxbal > clientList.get(0).maxbal);
    }

    @Test
    void clientMinbalFilterTest(){
        ClientReqParams params = new ClientReqParams();
        params.limit = 10l;
        params.offset = 0l;
        params.colName = "minbal";
        params.order = Sort.ASC;
        params.patronymicFilterVal = "";
        params.nameFilterVal = "";
        params.surnameFilterVal = "";
        //
        //
        List<Client> clientList = clientRegister.get().getListByParam(params);
        //
        //
        assertThat(clientList).isNotNull();
        assertThat(clientList).isNotEmpty();
        assertThat(clientList.get(clientList.size()-1).minbal > clientList.get(0).minbal);
    }

    @Test
    void insertClient(){

        Charm charm = new Charm(1L, "Strong", "with big power comes big responsibility", 1110d,true);

            Client client = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", Gender.FEMALE, new Timestamp(System.currentTimeMillis()), charm);
            //
            //
            Long retId = clientRegister.get().insert(client);
            Client retClient = clientRegister.get().getById(retId);
            //
            //

            assertThat(retId).isNotNull();
            assertThat(retClient).isNotNull();
            assertThat(retClient.surname).isEqualTo(client.surname);
            assertThat(retClient.name).isEqualTo(client.name);
            assertThat(retClient.patronymic).isEqualTo(client.patronymic);
            assertThat(retClient.gender).isEqualTo(client.gender);
            assertThat(retClient.birthDate).isEqualTo(client.birthDate);
        }

    @Test(expectedExceptions = NullPointerException.class)
    void insertClientWithNullGender(){

        Charm charm = new Charm(1L, "Strong", "with big power comes big responsibility", 1110d,true);

        Client client = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", null, new Timestamp(System.currentTimeMillis()), charm);
        //
        //
        Long retId = clientRegister.get().insert(client);
        //
        //
    }

    @Test(expectedExceptions = NullPointerException.class)
    void insertClientWithNull(){
        Client client = new Client(null, null, null, null, null, null, null);
        //
        //
        Long retId = clientRegister.get().insert(client);
        //
        //
    }

    @Test(expectedExceptions = NullPointerException.class)
    void insertNullClient(){
        Client client = null;
        //
        //
        Long retId = clientRegister.get().insert(client);
        //
        //
    }

    @Test
    void insertBatchClient(){
        Charm charm = new Charm(1L, "Strong", "Will smash everything ", 1110d,true);

        Client client = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", Gender.FEMALE, new Timestamp(System.currentTimeMillis()), charm);
        Client client2 = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", Gender.FEMALE, new Timestamp(System.currentTimeMillis()), charm);
        Client client3 = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", Gender.FEMALE, new Timestamp(System.currentTimeMillis()), charm);
        Client client4 = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", Gender.FEMALE, new Timestamp(System.currentTimeMillis()), charm);

        List<Client> clientList = new ArrayList<>();
        clientList.add(client);
        clientList.add(client2);
        clientList.add(client3);
        clientList.add(client4);
        //
        //
        clientRegister.get().insertBatch(clientList);
    }

    @Test
    void  loadClient(){
        Charm charm = new Charm(1L, "Strong", "Will everything moving ", 1110d,true);

        Client client = new Client(null, "Elgondy", "Ersultanbek", "Konysbekuly", Gender.FEMALE, new Timestamp(System.currentTimeMillis()), charm);
        //
        //
        Long retId = clientRegister.get().insert(client);
        Client retClient = clientRegister.get().getById(retId);
        //
        //

        assertThat(retClient).isNotNull();

        assertThat(retClient.surname).isEqualTo(client.surname);

        assertThat(retClient.name).isEqualTo(client.name);

        assertThat(retClient.patronymic).isEqualTo(client.patronymic);

        assertThat(retClient.gender).isEqualTo(client.gender);

        assertThat(retClient.birthDate).isEqualTo(client.birthDate);
    }

    @Test(expectedExceptions = NullPointerException.class)
    void loadClientWithNullId(){
        Charm charm = new Charm(1L, "Strong", "with big power comes big responsibility", 1110d,true);
        //
        //
        Client retClient = clientRegister.get().getById(null);
        //
        //

        assertThat(retClient).isNull();
    }

    @Test
    void loadClientWithFakeId(){
        //
        //
        Client retClient = clientRegister.get().getById(-1L);
        //
        //
        assertThat(retClient).isNull();
    }

    @Test void getClientWithParams(){
        ClientReqParams params = new ClientReqParams();
        params.limit = 10l;
        params.offset = 0l;
        params.colName = "name";
        params.order = Sort.ASC;
        //
        //
        List<Client> clientList = clientRegister.get().getListByParam(params);
        //
        //
        assertThat(clientList).isNotNull();
        assertThat(clientList).isNotEmpty();
    }
}
