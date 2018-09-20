package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

    public BeanGetter<ClientTestDao> clientTestDao;

    public BeanGetter<ClientRegister> clientRegister;

    public Charm charm;

    public Client client;

    public ClientAddr clientAddr;

    public ClientPhone clientPhone;

    public ClientAccount clientAccount;

    public ClientAccountTransaction clientAccountTransaction;

    public TransactionType transactionType;

    private Client addClient() {

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

    private ClientAddr addClientAddr() {

        ClientAddr clientAddr = new ClientAddr();
        clientAddr.type = AddrType.REG;
        clientAddr.street = RND.str(9);
        clientAddr.house = RND.str(9);
        clientAddr.flat = RND.str(9);
        return clientAddr;
    }

    private ClientPhone addClientPhone() {

        ClientPhone clientPhone = new ClientPhone();
        clientPhone.type = PhoneType.MOBILE;
        clientPhone.number = RND.plusInt(110000) + "";
        return clientPhone;
    }

    private ClientAccount addClientAccount() {

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

    private ClientAccountTransaction addClientAccountTransaction() {

        ClientAccountTransaction clientAccountTransaction = new ClientAccountTransaction();
        clientAccountTransaction.id = RND.plusInt(546);
        clientAccountTransaction.account = clientAccount.id;
        clientAccountTransaction.money = RND.plusInt(1000);
        clientAccountTransaction.finishedAt = new Timestamp(RND.plusInt(532));
        clientAccountTransaction.type = transactionType.id;
        return clientAccountTransaction;
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

        for (int i = 0; i < 5; i++) {
            charm = addCharm();
            clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, i, charm.actually);
        }
        //
        //
        List<Charm> listCharm = clientRegister.get().getCharm();
        //
        //
        assertThat(listCharm).hasSize(5);
        assertThat(listCharm.get(4).id).isEqualTo(charm.id);
        assertThat(listCharm.get(4).name).isEqualTo(charm.name);
        assertThat(listCharm.get(4).description).isEqualTo(charm.description);
        assertThat(listCharm.get(4).energy).isEqualTo(4);
        assertThat(listCharm.get(4).actually).isEqualTo(charm.actually);
        assertThat(listCharm.get(1).energy).isEqualTo(1);

    }

    @Test
    public void testGetCharmById() {

        Integer id = 10;
        Integer id1 = 0;
        Integer id2 = null;

//
//
        Charm charm = clientRegister.get().getCharmById(id);
        Charm charm1 = clientRegister.get().getCharmById(id1);
        Charm charm2 = clientRegister.get().getCharmById(id2);
//
//

        assertThat(charm.id).isEqualTo(10);
        assertThat(charm1.id).isEqualTo(0);
        assertThat(charm2).isNull();
    }

    @Test
    public void testDeleteClient() {

        for (int i = 0; i < 5; i++) {
            charm = addCharm();
            client = addClient();
            clientTestDao.get().insertCharm(i, charm.name, charm.description, charm.energy, charm.actually);
            clientTestDao.get().insertClient(i, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, i);
        }
//
//
        clientRegister.get().deleteClient(1);

//
//
        Integer clientOfDeleted = clientTestDao.get().selectClientFromId(1);
        assertThat(clientOfDeleted).isNull();
        assertThat(clientTestDao.get().selectClientFromId(0)).isNotNull();
        assertThat(clientTestDao.get().selectClientFromId(2)).isNotNull();
        assertThat(clientTestDao.get().selectClientFromId(3)).isNotNull();
        assertThat(clientTestDao.get().selectClientFromId(4)).isNotNull();

    }

    @Test
    public void testGetClientDetails() {

        charm = addCharm();
        client = addClient();
        clientPhone = addClientPhone();
        clientAddr = addClientAddr();
        clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
        clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
        clientTestDao.get().insertClientPhone(client.id, clientPhone.number, PhoneType.MOBILE);
        clientTestDao.get().insertClientAddr(client.id, AddrType.FACT, clientAddr.street, clientAddr.house, clientAddr.flat);
        clientTestDao.get().insertClientAddr(client.id, AddrType.REG, clientAddr.street, clientAddr.house, clientAddr.flat);

//
//
        ClientDetails clientDetails = clientRegister.get().getClientDetails(client.id);
        System.err.println(clientDetails);
//
//
        assertThat(client.id).isEqualTo(clientDetails.id);


    }

    @Test
    public void testSaveClient() {

        charm = addCharm();
        client = addClient();
        clientPhone = addClientPhone();
        clientAddr = addClientAddr();
        transactionType = addTransactionType();
        clientAccount = addClientAccount();
        clientAccountTransaction = addClientAccountTransaction();

        clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
        clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
        clientTestDao.get().insertClientPhone(client.id, clientPhone.number, PhoneType.MOBILE);
        clientTestDao.get().insertClientAddr(client.id, AddrType.FACT, clientAddr.street, clientAddr.house, clientAddr.flat);
        clientTestDao.get().insertTransaction_type(transactionType.id, transactionType.code, transactionType.name);
        clientTestDao.get().insertClientAccount(clientAccount.id, clientAccount.client, clientAccount.money, clientAccount.number, clientAccount.registeredAt);
        clientTestDao.get().insertClientAccountTransaction(
                clientAccountTransaction.id, clientAccountTransaction.account, clientAccountTransaction.money,
                clientAccountTransaction.finishedAt, clientAccountTransaction.type);

        ClientToSave clientDetails = new ClientToSave();
        clientDetails.id = client.id;
        clientDetails.firstname = client.firstname;
        clientDetails.lastname = client.lastname;
        clientDetails.patronymic = client.patronymic;
        clientDetails.gender = client.gender;
        clientDetails.dateOfBirth = client.birthDate;
        clientDetails.characterId = charm.id;
        clientDetails.addressOfRegistration.street = clientAddr.street;
        clientDetails.addressOfRegistration.house = clientAddr.house;
        clientDetails.addressOfRegistration.flat = clientAddr.flat;
        clientDetails.addressOfRegistration.type = AddrType.REG;
        clientDetails.addressOfResidence.street = clientAddr.street;
        clientDetails.addressOfResidence.house = clientAddr.house;
        clientDetails.addressOfResidence.flat = clientAddr.flat;
        clientDetails.addressOfResidence.type = AddrType.REG;
        clientDetails.phone.add(clientPhone);

        //
        //
        ClientRecord clientRecord = clientRegister.get().saveClient(clientDetails);
        System.err.println(clientRecord);
        //
        //


    }

    @Test
    public void testGetClientList() {

        for (int i = 0; i < 20; i++) {
            charm = addCharm();
            client = addClient();
            clientAccount = addClientAccount();
            clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
            clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
            clientTestDao.get().insertClientAccount(clientAccount.id, clientAccount.client, clientAccount.money, clientAccount.number, clientAccount.registeredAt);
            clientTestDao.get().insertClientAccount(clientAccount.id + 1, clientAccount.client, clientAccount.money + 50, clientAccount.number, clientAccount.registeredAt);
        }
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.firstname = "";
        clientFilter.lastname = "";
        clientFilter.patronymic = "";
        clientFilter.orderBy = "totalAccountBalance";
        clientFilter.recordSize = 20;
        clientFilter.page = 0;
        clientFilter.recordTotal = 100;
        clientFilter.sort = true;

        ClientRecord clientRecord = new ClientRecord();
        List<ClientRecord> clientRecords = clientRegister.get().getClientList(clientFilter);
        System.err.println(clientRecords);
        System.err.println(clientRecords.size());
    }

    @Test
    public void testGetClientTotalRecord() {
        for (int i = 0; i < 20; i++) {
            charm = addCharm();
            client = addClient();
            clientAccount = addClientAccount();
            clientTestDao.get().insertCharm(charm.id, charm.name, charm.description, charm.energy, charm.actually);
            clientTestDao.get().insertClient(client.id, client.firstname, client.lastname, client.patronymic, client.gender, client.birthDate, client.charm);
            clientTestDao.get().insertClientAccount(clientAccount.id, clientAccount.client, clientAccount.money, clientAccount.number, clientAccount.registeredAt);
            clientTestDao.get().insertClientAccount(clientAccount.id + 1, clientAccount.client, clientAccount.money + 50, clientAccount.number, clientAccount.registeredAt);
        }
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
        assertThat(clientFilter.recordTotal).isEqualTo(20);
    }
}