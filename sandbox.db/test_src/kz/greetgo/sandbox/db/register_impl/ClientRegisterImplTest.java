package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.model.ClientAccount;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

    public BeanGetter<ClientTestDao> clientTestDao1;

    public BeanGetter<ClientRegister> clientRegister;


    private Client addClient(Charm charm) {


        Client client = new Client();
        client.id = RND.plusInt(10000);
        client.firstname = RND.str(10);
        client.lastname = RND.str(10);
        client.patronymic = RND.str(10);
        client.gender = GenderType.MALE;
        client.birthDate = new Date();
        if (charm != null)
            client.charm = charm.id;
        return client;
    }

    private Client addClientKnowValue(int i, Charm charm) {

        Client client = new Client();
        client.id = i;
        client.firstname = String.valueOf(i);
        client.lastname = String.valueOf(i);
        client.patronymic = String.valueOf(i);
        client.gender = GenderType.MALE;
        client.birthDate = new Date();
        if (charm != null)
            client.charm = charm.id;
        return client;
    }


    private Charm addCharm() {

        Charm charm = new Charm();
        charm.id = RND.plusInt(1000);
        charm.name = RND.str(10);
        charm.description = RND.str(10);
        charm.energy = RND.plusInt(1000);
        charm.actually = rndBoolean();
        return charm;
    }

    private Charm addCharmKnowValue(int i) {

        Charm charm = new Charm();
        charm.id = i;
        charm.name = String.valueOf(i);
        charm.description = String.valueOf(i);
        charm.energy = RND.plusInt(1505);
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
        clientAccount.money = 1000;
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

    private List<Integer> addDatasDao(int sizeForCycle) {


        Charm charm;
        Client client;
        ClientAccount clientAccount;

        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < sizeForCycle; i++) {
            charm = addCharmKnowValue(i);
            client = addClientKnowValue(i,charm);
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

        clientTestDao1.get().deleteAllClientAccountTransaction();
        clientTestDao1.get().deleteAllClientAccount();
        clientTestDao1.get().deleteAllClientAddr();
        clientTestDao1.get().deleteAllClientPhone();
        clientTestDao1.get().deleteAllClient();
        clientTestDao1.get().deleteAllTransactionType();
        clientTestDao1.get().deleteAllCharm();

    }

    private boolean rndBoolean() {

        int IntRnd = (int) (Math.random() * 10 - 5);
        return IntRnd >= 0;
    }


    @Test
    public void testGetCharm() {
        Charm charm;
        int count = 0;
        for (int i = 0; i < 5; i++) {
            charm = addCharmKnowValue(i);
            if (charm.actually)
                count++;
            clientTestDao1.get().insertCharm(charm);
        }

        //
        //
        List<Charm> listCharm = clientRegister.get().getCharm();
        //
        //

        System.err.println(listCharm);
        assertThat(listCharm.size()).isEqualTo(count);
        for (int i = 0; i < count; i++) {
            int id = listCharm.get(i).id;
            assertThat(listCharm.get(i).id).isEqualTo(clientTestDao1.get().selectCharmById(id).id);
            assertThat(listCharm.get(i).name).isEqualTo(clientTestDao1.get().selectCharmById(id).name);
            assertThat(listCharm.get(i).description).isEqualTo(clientTestDao1.get().selectCharmById(id).description);
            assertThat(listCharm.get(i).energy).isEqualTo(clientTestDao1.get().selectCharmById(id).energy);
            assertThat(listCharm.get(i).actually).isEqualTo(clientTestDao1.get().selectCharmById(id).actually);
        }

    }


    @Test
    public void testGetCharmById() {
        Charm ch;
        ch = addCharm();
        int id = ch.id;
        clientTestDao1.get().insertCharm(ch);

        ch = addCharm();
        int id1 = ch.id;
        Integer idNull = null;
        int count = 0;


        for (int i = 1; i < 5; i++) {
            ch = addCharmKnowValue(i);
            if (ch.actually)
                count++;
            clientTestDao1.get().insertCharm(ch);
        }
//
//
        Charm charm = clientRegister.get().getCharmById(id);
        Charm charm1 = clientRegister.get().getCharmById(id1);
        Charm charmNull = clientRegister.get().getCharmById(idNull);
        List<Charm> charms = clientRegister.get().getCharm();
//
//

        assertThat(charm.id).isEqualTo(id);
        assertThat(charm1.id).isEqualTo(id1);
        assertThat(charmNull).isEqualTo(null);
        for (int i = 0; i < count; i++) {
            int charmId = charms.get(i).id;
            assertThat(charms.get(i).name).isEqualTo(clientTestDao1.get().selectCharmById(charmId).name);
            assertThat(charms.get(i).energy).isEqualTo(clientTestDao1.get().selectCharmById(charmId).energy);

        }

    }


    @Test
    public void testDeleteClient() {

        Client client = new Client();
        List<Integer> listId = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            client = addClientKnowValue(i, null);
            listId.add(client.id);
            clientTestDao1.get().insertClientWithoutCharm(client);
        }
        int clientIdDeleted = 2;
        listId.remove(2);
        int clientIdDeleted1 = 3;
        listId.remove(2);

//
//
        clientRegister.get().deleteClient(clientIdDeleted);
        clientRegister.get().deleteClient(clientIdDeleted1);

//
//

        System.err.println(listId);
        int count = clientTestDao1.get().getClientCount();
        assertThat(count).isEqualTo(3);
        assertThat(clientTestDao1.get().getClientId(clientIdDeleted)).isNull();
        assertThat(clientTestDao1.get().getClientId(clientIdDeleted1)).isNull();
        for (int i = 0; i < count; i++) {
            Client cl = clientTestDao1.get().getClientById(listId.get(i));
            assertThat(cl.id).isEqualTo(listId.get(i));
            assertThat(cl.firstname).isEqualTo(String.valueOf(listId.get(i)));
            assertThat(cl.lastname).isEqualTo(String.valueOf(listId.get(i)));

        }
    }


    @Test
    public void testGetClientDetails() {
        Charm charm = new Charm();
        Client client = new Client();
        ClientAddr clientAddr = new ClientAddr();
        ClientPhone clientPhone = new ClientPhone();
        for (int i = 1; i < 3; i++) {
            charm = addCharmKnowValue(i);
            client = addClientKnowValue(i, charm);
            clientPhone = addClientPhone(client);
            clientAddr = addClientAddr(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientPhone(clientPhone);
            clientPhone.type = PhoneType.HOME;
            clientPhone.number = "9999999";
            clientTestDao1.get().insertClientPhone(clientPhone);
            clientTestDao1.get().insertClientAddr(clientAddr);
            clientAddr.type = AddrType.FACT;
            clientTestDao1.get().insertClientAddr(clientAddr);
        }
//
//
        ClientDetails clientDetails = clientRegister.get().getClientDetails(2);
        System.err.println("clientDETAILS:" + clientDetails);
//
//
        assertThat(clientDetails.id).isEqualTo(2);
        assertThat(clientDetails.firstname).isEqualTo("2");
        assertThat(clientDetails.lastname).isEqualTo("2");
        assertThat(clientDetails.patronymic).isEqualTo(client.patronymic);
        assertThat(clientDetails.characterId).isEqualTo(charm.id);
        assertThat(clientDetails.gender).isEqualTo(client.gender);
        assertThat(clientDetails.addressOfResidence.street).isEqualTo(clientAddr.street);
        assertThat(clientDetails.addressOfResidence.house).isEqualTo(clientAddr.house);
        assertThat(clientDetails.addressOfResidence.flat).isEqualTo(clientAddr.flat);
        assertThat(clientDetails.addressOfRegistration.street).isEqualTo(clientAddr.street);
        assertThat(clientDetails.addressOfRegistration.house).isEqualTo(clientAddr.house);
        assertThat(clientDetails.addressOfRegistration.flat).isEqualTo(clientAddr.flat);
        assertThat(clientDetails.phone.get(1).number).isEqualTo(clientPhone.number);

    }

    @Test
    public void testSaveClientEdit() {
        Charm charm = new Charm();
        Client client = new Client();
        ClientAddr clientAddr = new ClientAddr();
        ClientPhone clientPhone = new ClientPhone();
        ClientAccountTransaction clientAccountTransaction;
        TransactionType transactionType;
        ClientAccount clientAccount = new ClientAccount();
        for (int i = 1; i < 3; i++) {
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
        }
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
        System.err.println("clientToSAVE:" + clientToSave);

        //
        //
        ClientRecord clientRecord = clientRegister.get().saveClient(clientToSave);
        System.err.println(clientRecord);
        //
        //
        assertThat(clientRecord).isNotNull();
        assertThat(clientRecord.id).isEqualTo(clientToSave.id);
        assertThat(clientRecord.firstname).isEqualTo(clientToSave.firstname);
        assertThat(clientRecord.lastname).isEqualTo(clientToSave.lastname);
        assertThat(clientRecord.patronymic).isEqualTo(clientToSave.patronymic);
        assertThat(clientRecord.characterName).isEqualTo(charm.name);
        assertThat(clientRecord.maximumBalance).isEqualTo((int) clientAccount.money);
        assertThat(clientRecord.minimumBalance).isEqualTo((int) clientAccount.money);
        assertThat(clientRecord.totalAccountBalance).isEqualTo((int) clientAccount.money);

    }

    @Test
    public void testSaveClientSave() {

        Charm charm;
        Client client;
        ClientAddr clientAddr;
        ClientPhone clientPhone;
        charm = addCharm();
        client = addClient(charm);
        clientPhone = addClientPhone(client);
        clientAddr = addClientAddr(client);

        clientTestDao1.get().insertCharm(charm);
        clientTestDao1.get().insertClient(client);
        ClientToSave clientToSave = new ClientToSave();
        clientToSave.id = null;
        clientToSave.firstname = client.firstname;
        clientToSave.lastname = client.lastname;
        clientToSave.patronymic = client.patronymic;
        clientToSave.gender = client.gender;
        clientToSave.dateOfBirth = client.birthDate;
        clientToSave.characterId = charm.id;
        clientToSave.addressOfRegistration.client = null;
        clientToSave.addressOfRegistration.street = clientAddr.street;
        clientToSave.addressOfRegistration.house = clientAddr.house;
        clientToSave.addressOfRegistration.flat = clientAddr.flat;
        clientToSave.addressOfRegistration.type = AddrType.REG;
        clientToSave.addressOfResidence.client = null;
        clientToSave.addressOfResidence.street = clientAddr.street;
        clientToSave.addressOfResidence.house = clientAddr.house;
        clientToSave.addressOfResidence.flat = clientAddr.flat;
        clientToSave.addressOfResidence.type = AddrType.FACT;
        clientPhone.client = null;
        clientToSave.phone.add(clientPhone);
        Integer id = clientToSave.id;
        System.err.println("CLIENTTOSAVE:" + clientToSave);

        //
        //
        ClientRecord clientRecord = clientRegister.get().saveClient(clientToSave);
        //
        //


        assertThat(clientRecord).isNotNull();
        assertThat(clientRecord.id).isNotEqualTo(id);
        assertThat(clientTestDao1.get().getClientById(clientRecord.id).id).isEqualTo(clientRecord.id);
        assertThat(clientRecord.firstname).isEqualTo(clientToSave.firstname);
        assertThat(clientRecord.lastname).isEqualTo(clientToSave.lastname);
        assertThat(clientRecord.patronymic).isEqualTo(clientToSave.patronymic);
        assertThat(clientRecord.characterName).isEqualTo(charm.name);
        assertThat(clientRecord.maximumBalance).isEqualTo(0);
        assertThat(clientRecord.minimumBalance).isEqualTo(0);
        assertThat(clientRecord.totalAccountBalance).isEqualTo(0);
    }


    @Test
    public void testGetClientList() {

        Charm charm;
        Client client;
        ClientAccount clientAccount = new ClientAccount();
        for (int i = 0; i < 10; i++) {
            charm = addCharmKnowValue(i);
            client = addClientKnowValue(i, charm);
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }

        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "firstname";
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

        assertThat(clientRecords.size()).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            int idClientRecord = clientRecords.get(i).id;
            client = clientTestDao1.get().getClientById(idClientRecord);
            assertThat(clientRecords.get(i).characterName).isEqualTo(clientTestDao1.get().selectCharmById(idClientRecord).name);
            assertThat(clientRecords.get(i).firstname).isEqualTo(client.firstname);
            assertThat(clientRecords.get(i).lastname).isEqualTo(client.lastname);
            assertThat(clientRecords.get(i).patronymic).isEqualTo(client.patronymic);
            assertThat(clientRecords.get(i).id).isEqualTo(client.id);
            assertThat(clientRecords.get(i).maximumBalance).isEqualTo((int) clientAccount.money);
            assertThat(clientRecords.get(i).minimumBalance).isEqualTo((int) clientAccount.money - 50);
            assertThat(clientRecords.get(i).totalAccountBalance).isEqualTo((int) clientAccount.money - 25);
        }


    }


    @Test
    public void testGetClientListByFilter() {
        Charm charm;
        Client client = new Client();
        ClientAccount clientAccount = new ClientAccount();
        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            charm = addCharmKnowValue(i);
            client = addClientKnowValue(i, charm);
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
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        Collections.sort(id);
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = client.firstname;
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "firstname";
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
            int idClientRecord = clientRecords.get(i).id;
            client = clientTestDao1.get().getClientById(idClientRecord);
            assertThat(clientRecords.get(i).characterName).isEqualTo(clientTestDao1.get().selectCharmById(idClientRecord).name);
            assertThat(clientRecords.get(i).firstname).isEqualTo("Nazar");
            assertThat(clientRecords.get(i).firstname).isEqualTo(client.firstname);
            assertThat(clientRecords.get(i).lastname).isEqualTo(client.lastname);
            assertThat(clientRecords.get(i).patronymic).isEqualTo(client.patronymic);
            assertThat(clientRecords.get(i).id).isEqualTo(client.id);
            assertThat(clientRecords.get(i).maximumBalance).isEqualTo((int) clientAccount.money);
            assertThat(clientRecords.get(i).minimumBalance).isEqualTo((int) clientAccount.money - 100);
            assertThat(clientRecords.get(i).totalAccountBalance).isEqualTo((int) clientAccount.money - 50);
        }


    }


    @Test
    public void testGetClientListSort() {

        Charm charm;
        Client client;
        ClientAccount clientAccount = new ClientAccount();
        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            charm = addCharmKnowValue(i);
            client = addClientKnowValue(i,charm);
            id.add(client.id);
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 60;
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 60;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        Collections.sort(id);
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "character";
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
        for (int i = 0; i < 12; i++) {
            int idClientRecord = clientRecords.get(i).id;
            client = clientTestDao1.get().getClientById(idClientRecord);
            assertThat(clientRecords.get(i).characterName).isEqualTo(clientTestDao1.get().selectCharmById(idClientRecord).name);
            assertThat(clientRecords.get(i).firstname).isEqualTo(client.firstname);
            assertThat(clientRecords.get(i).lastname).isEqualTo(client.lastname);
            assertThat(clientRecords.get(i).patronymic).isEqualTo(client.patronymic);
            assertThat(clientRecords.get(i).id).isEqualTo(client.id);
            assertThat(clientRecords.get(i).maximumBalance).isEqualTo((int) clientAccount.money);
            assertThat(clientRecords.get(i).minimumBalance).isEqualTo((int) clientAccount.money - 100);
            assertThat(clientRecords.get(i).totalAccountBalance).isEqualTo((int) clientAccount.money - 50);
        }
    }


    @Test
    public void testGetClientListRecordSize() {

        Charm charm;
        Client client = new Client();
        ClientAccount clientAccount=new ClientAccount();
        for (int i = 0; i < 10; i++) {
            charm = addCharmKnowValue(i);
            client = addClientKnowValue(i, charm);
            clientAccount = addClientAccount(client);
            clientTestDao1.get().insertCharm(charm);
            clientTestDao1.get().insertClient(client);
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 50;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
            clientAccount.money = clientAccount.money + 70;
            clientAccount.id = clientAccount.id + 50;
            clientTestDao1.get().insertClientAccount(clientAccount);
        }
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "character";
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

        assertThat(clientRecords.size()).isEqualTo(clientFilter.recordSize);

        for (int i = 0; i < clientFilter.recordSize; i++) {
            int idClientRecord = clientRecords.get(i).id;
            client = clientTestDao1.get().getClientById(idClientRecord);
            assertThat(clientRecords.get(i).characterName).isEqualTo(clientTestDao1.get().selectCharmById(idClientRecord).name);
            assertThat(clientRecords.get(i).firstname).isEqualTo(client.firstname);
            assertThat(clientRecords.get(i).lastname).isEqualTo(client.lastname);
            assertThat(clientRecords.get(i).patronymic).isEqualTo(client.patronymic);
            assertThat(clientRecords.get(i).id).isEqualTo(client.id);
            assertThat(clientRecords.get(i).maximumBalance).isEqualTo((int) clientAccount.money);
            assertThat(clientRecords.get(i).minimumBalance).isEqualTo((int) clientAccount.money - 120);
            assertThat(clientRecords.get(i).totalAccountBalance).isEqualTo((int) clientAccount.money - 64);
        }

    }

    @Test
    public void testGetClientTotalRecord() {


        addDatasDao(10);
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