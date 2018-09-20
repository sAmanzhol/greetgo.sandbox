package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao1;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest1 extends ParentTestNg {

    public BeanGetter<ClientTestDao1> clientTestDao1;

    public BeanGetter<ClientTestDao> clientTestDao;

    public BeanGetter<ClientRegister> clientRegister;


    private Client addClient(Charm charm) {

        Client client = new Client();
        client.id = RND.plusInt(1000);
        client.firstname = RND.str(10);
        client.lastname = RND.str(10);
        client.patronymic = RND.str(10);
        client.gender = GenderType.MALE;
        client.birthDate = new Date();
        client.charm = charm.id;
        return client;
    }

    private Charm addCharm() {

        Charm charm = new Charm();
        charm.id = RND.plusInt(1000);
        charm.name = RND.str(10);
        charm.description = RND.str(10);
        charm.energy = RND.plusInt(100);
        charm.actually = rndBoolean();
        return charm;
    }

    private ClientAddr addClientAddr(Client client) {

        ClientAddr clientAddr = new ClientAddr();
        clientAddr.client = client.id;
        clientAddr.type = AddrType.REG;
        clientAddr.street = RND.str(9);
        clientAddr.house = RND.str(9);
        clientAddr.flat = RND.str(9);
        return clientAddr;
    }

    private ClientPhone addClientPhone(Client client) {

        ClientPhone clientPhone = new ClientPhone();
        clientPhone.client = client.id;
        clientPhone.type = PhoneType.MOBILE;
        clientPhone.number = RND.plusInt(110000) + "";
        return clientPhone;
    }

    private ClientAccount addClientAccount(Client client) {

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.id = RND.plusInt(10000);
        clientAccount.client = client.id;
        clientAccount.money = RND.plusInt(456);
        clientAccount.number = RND.str(10);
        clientAccount.registeredAt = new Timestamp(RND.plusInt(532));
        return clientAccount;

    }

    private TransactionType addTransactionType() {

        TransactionType transactionType = new TransactionType();

        transactionType.id = RND.plusInt(546);
        transactionType.code = RND.str(10);
        transactionType.name = RND.str(10);
        return transactionType;
    }

    private ClientAccountTransaction addClientAccountTransaction(ClientAccount clientAccount, TransactionType transactionType) {

        ClientAccountTransaction clientAccountTransaction = new ClientAccountTransaction();
        clientAccountTransaction.id = RND.plusInt(546);
        clientAccountTransaction.account = clientAccount.id;
        clientAccountTransaction.money = RND.plusInt(1000);
        clientAccountTransaction.finishedAt = new Timestamp(RND.plusInt(532));
        clientAccountTransaction.type = transactionType.id;
        return clientAccountTransaction;
    }

    private List<Integer> addDatatoDAO() {

        Charm charm;
        Client client;
        ClientAccount clientAccount;
        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            charm = addCharm();
            client = addClient(charm);
            id.add(client.id);
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        return id;

    }

    @BeforeMethod
    public void setUp() {

        clientTestDao.get().deleteAllClientAccountTransaction();
        clientTestDao.get().deleteAllClientAccount();
        clientTestDao.get().deleteAllClientAddr();
        clientTestDao.get().deleteAllClientPhone();
        clientTestDao.get().deleteAllClient();
        clientTestDao.get().deleteAllTransactionType();
        clientTestDao.get().deleteAllCharm();

    }

    private boolean rndBoolean() {

        int IntRnd = (int) (Math.random() * 10 - 5);
        if (IntRnd < 0) {
            return false;
        } else
            return true;
    }

    @Test
    public void testGetCharm() {

        Charm charm = new Charm();

        for (int i = 0; i < 5; i++) {
            charm = addCharm();
            clientTestDao1.get().insertCharm(charm);
        }
        //
        //
        List<Charm> listCharm = clientRegister.get().getCharm();
        //
        //
        System.err.println(listCharm);
        assertThat(listCharm).hasSize(5);
        assertThat(listCharm.get(4).id).isEqualTo(charm.id);
        assertThat(listCharm.get(4).name).isEqualTo(charm.name);
        assertThat(listCharm.get(4).description).isEqualTo(charm.description);
        assertThat(listCharm.get(4).energy).isEqualTo(charm.energy);
        assertThat(listCharm.get(4).actually).isEqualTo(charm.actually);

    }

    @Test
    public void testGetCharmById() {

        Charm ch = new Charm();
        for (int i = 0; i < 5; i++) {
            ch = addCharm();
            clientTestDao1.get().insertCharm(ch);
        }
        Integer id = 1000;
        Integer id1 = 0;
        Integer id2 = null;

//
//
        Charm charm = clientRegister.get().getCharmById(id);
        Charm charm1 = clientRegister.get().getCharmById(id1);
        Charm charm2 = clientRegister.get().getCharmById(id2);
        Charm charm3 = clientRegister.get().getCharmById(ch.id);
//
//

        assertThat(charm.id).isEqualTo(1000);
        assertThat(charm1.id).isEqualTo(0);
        assertThat(charm2).isNull();
        assertThat(clientTestDao1.get().selectCharmById(ch.id).id).isEqualTo(charm3.id);
    }

    @Test
    public void testDeleteClient() {

        Charm charm = new Charm();
        Client client = new Client();
        for (int i = 0; i < 5; i++) {
            charm = addCharm();
            client = addClient(charm);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
        }

//
//
        clientRegister.get().deleteClient(client.id);

//
//
        Integer clientOfDeleted = clientTestDao1.get().getClientId(client.id);
        assertThat(clientOfDeleted).isNull();

    }

    @Test
    public void testGetClientDetails() {

        Charm charm = new Charm();
        Client client = new Client();
        ClientAddr clientAddr = new ClientAddr();
        ClientPhone clientPhone = new ClientPhone();
        charm = addCharm();
        client = addClient(charm);
        clientPhone = addClientPhone(client);
        clientAddr = addClientAddr(client);
        clientTestDao1.get().insertCharm(charm);
        clientTestDao1.get().insertClient(client);
        clientTestDao1.get().insertClientPhone(clientPhone);
        clientTestDao1.get().insertClientAddr(clientAddr);
        clientAddr.type = AddrType.FACT;
        clientTestDao1.get().insertClientAddr(clientAddr);

//
//
        ClientDetails clientDetails = clientRegister.get().getClientDetails(client.id);
//
//
        assertThat(client.id).isEqualTo(clientDetails.id);

    }

    @Test
    public void testSaveClient() {

        Charm charm;
        Client client;
        ClientAddr clientAddr;
        ClientPhone clientPhone;
        ClientAccountTransaction clientAccountTransaction;
        TransactionType transactionType;
        ClientAccount clientAccount;
        charm = addCharm();
        client = addClient(charm);
        clientPhone = addClientPhone(client);
        clientAddr = addClientAddr(client);
        transactionType = addTransactionType();
        clientAccount = addClientAccount(client);
        clientAccountTransaction = addClientAccountTransaction(clientAccount, transactionType);

        clientTestDao1.get().insertCharm(charm);
        clientTestDao1.get().insertClient(client);
        clientTestDao1.get().insertClientPhone(clientPhone);
        clientTestDao1.get().insertClientAddr(clientAddr);
        clientAddr.type = AddrType.FACT;
        clientTestDao1.get().insertClientAddr(clientAddr);
        clientTestDao1.get().insertTransaction_type(transactionType);
        clientTestDao1.get().insertClientAccount(clientAccount);
        clientTestDao1.get().insertClientAccountTransaction(clientAccountTransaction);

        ClientToSave clientToSave = new ClientToSave();
        clientToSave.id = client.id;
        clientToSave.firstname = client.firstname;
        clientToSave.lastname = client.lastname;
        clientToSave.patronymic = client.patronymic;
        clientToSave.gender = client.gender;
        clientToSave.dateOfBirth = client.birthDate;
        clientToSave.characterId = charm.id;
        clientToSave.addressOfRegistration.client = clientAddr.client;
        clientToSave.addressOfRegistration.street = clientAddr.street;
        clientToSave.addressOfRegistration.house = clientAddr.house;
        clientToSave.addressOfRegistration.flat = clientAddr.flat;
        clientToSave.addressOfRegistration.type = AddrType.REG;
        clientToSave.addressOfResidence.client = clientAddr.client;
        clientToSave.addressOfResidence.street = clientAddr.street;
        clientToSave.addressOfResidence.house = clientAddr.house;
        clientToSave.addressOfResidence.flat = clientAddr.flat;
        clientToSave.addressOfResidence.type = AddrType.FACT;
        clientToSave.phone.add(clientPhone);

        //
        //
        ClientRecord clientRecord = clientRegister.get().saveClient(clientToSave);
        System.err.println(clientRecord);
        //
        //


    }

    @Test
    public void testGetClientList() {

        Charm charm;
        Client client;
        ClientAccount clientAccount;
        List<Integer> id;
        id = addDatatoDAO();
        Collections.sort(id);

        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "id";
        clientFilter.recordSize = 20;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = true;
//
//
        ClientRecord clientRecord = new ClientRecord();
        List<ClientRecord> clientRecords = clientRegister.get().getClientList(clientFilter);
        System.err.println(clientRecords);
        System.err.println(clientRecords.size());
//
//


        System.err.println(id);
        assertThat(clientRecords.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            assertThat(clientRecords.get(i).id).isEqualTo(id.get(i));
        }

    }

    @Test
    public void testGetClientListByFilter() {

        Charm charm;
        Client client = new Client();
        ClientAccount clientAccount;
        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            charm = addCharm();
            client = addClient(charm);
            if (i >= 8) {
                id.add(client.id);
                client.firstname = "Nazar";
            }
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        Collections.sort(id);
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = client.firstname;
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "id";
        clientFilter.recordSize = 100;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = true;

//
//
        ClientRecord clientRecord = new ClientRecord();
        List<ClientRecord> clientRecords = clientRegister.get().getClientList(clientFilter);
        System.err.println(clientRecords);
        System.err.println(clientRecords.size());
//
//

        assertThat(clientRecords.size()).isEqualTo(2);
        for (int i = 0; i < 2; i++) {
            assertThat(clientRecords.get(i).id).isEqualTo(id.get(i));
            assertThat(clientRecords.get(i).firstname).isEqualTo("Nazar");
        }
    }

    @Test
    public void testGetClientListSort() {

        Charm charm;
        Client client = new Client();
        ClientAccount clientAccount;
        List<Integer> id= new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            charm = addCharm();
            client = addClient(charm);
            id.add(client.id);
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        Collections.sort(id);
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "id";
        clientFilter.recordSize = 100;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = false;

//
//
        ClientRecord clientRecord = new ClientRecord();
        List<ClientRecord> clientRecords = clientRegister.get().getClientList(clientFilter);
        System.err.println(clientRecords);
        System.err.println(clientRecords.size());
//
//

        assertThat(clientRecords.size()).isEqualTo(12);
        int k=11;
        for (int i = 0; i < 12; i++) {
            assertThat(clientRecords.get(i).id).isEqualTo(id.get(k));
            k--;
        }
    }

    @Test
    public void testGetClientListRecordSize() {

        Charm charm;
        Client client = new Client();
        ClientAccount clientAccount;
        List<Integer> id;
        id = addDatatoDAO();
        Collections.sort(id);
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "id";
        clientFilter.recordSize = 2;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = false;

//
//
        ClientRecord clientRecord = new ClientRecord();
        List<ClientRecord> clientRecords = clientRegister.get().getClientList(clientFilter);
        System.err.println(clientRecords);
        System.err.println(clientRecords.size());
//
//

        assertThat(clientRecords.size()).isEqualTo(2);
        int k=9;
        for (int i = 0; i < 2; i++) {
            assertThat(clientRecords.get(i).id).isEqualTo(id.get(k));
            k--;
        }
    }

    @Test
    public void testGetClientTotalRecord() {

        Charm charm = new Charm();
        Client client = new Client();
        ClientAccount clientAccount = new ClientAccount();
        addDatatoDAO();
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "charm";
        clientFilter.recordSize = 10;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = false;
        //
        //
        //
        clientFilter.recordTotal = clientRegister.get().getClientTotalRecord(clientFilter);
        System.err.println("count: " + clientFilter.recordTotal);
        //
        //
        //
        assertThat(clientFilter.recordTotal).isEqualTo(10);
    }

    @Test
    public void testGetClientTotalRecordFilter() {

        Charm charm = new Charm();
        Client client = new Client();
        ClientAccount clientAccount = new ClientAccount();
        for (int i = 0; i < 10; i++) {
            charm = addCharm();
            client = addClient(charm);
            if (i >= 8)
                client.firstname = "Nazar";
            client.lastname = "nazar";
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = client.firstname;
        clientFilter.lastname = client.lastname;
        clientFilter.patronymic = "";
        clientFilter.orderBy = "charm";
        clientFilter.recordSize = 10;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = false;
        //
        //
        //
        clientFilter.recordTotal = clientRegister.get().getClientTotalRecord(clientFilter);
        System.err.println("count: " + clientFilter.recordTotal);
        //
        //
        //
        assertThat(clientFilter.recordTotal).isEqualTo(2);
    }
}