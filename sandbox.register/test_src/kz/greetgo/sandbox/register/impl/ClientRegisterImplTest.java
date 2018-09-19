package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.apache.ibatis.exceptions.PersistenceException;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

    public BeanGetter<ClientTestDao> clientTestDao;
    public BeanGetter<ClientRegisterImpl> clientRegister;

    FilterParams params = new FilterParams();
    List<ClientRecord> clients;
    ClientRecord client;

    @Test
    public void getClientList_simple_record() {
        deleteAllClients();

        ClientRecord record = init(RND.plusInt(1000000), RND.str(10), RND.str(10));

        params.filter = "";
        params.filterCol = "";
        params.sortBy = "";
        params.sortDir = "";
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).id).isEqualTo(record.id);
        assertThat(clients.get(0).fio).isEqualTo(record.fio);
    }

    @Test
    public void getClientList_filter() {
        deleteAllClients();

        ClientRecord record1 = init(RND.plusInt(1000000), "Ivanov", "Ivan");
        ClientRecord record2 = init(RND.plusInt(1000000), "Petrov", "Ivan");
        ClientRecord record3 = init(RND.plusInt(1000000), "Petrov", "Petr");

        params.filterCol = FilterBy.NAME.toString();
        params.filter = "iv";
        params.sortBy = "";
        params.sortDir = "";
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(2);
        assertThat(clients.get(0).fio).isEqualTo(record1.fio);
        assertThat(clients.get(1).fio).isEqualTo(record2.fio);
        assertThat(clients.get(0).surname).isNotEqualTo(record3.surname);
        assertThat(clients.get(1).surname).isNotEqualTo(record3.surname);
        assertThat(Objects.equals((clients.get(1)), record2));
    }

    @Test
    public void getClientList_sort_asc() {
        deleteAllClients();

        ClientRecord record1 = init(RND.plusInt(1000000), "Ivanov", "Ivan");
        ClientRecord record2 = init(RND.plusInt(1000000), "Petrov", "Ivan");
        ClientRecord record3 = init(RND.plusInt(1000000), "Coi", "Petr");
        ClientRecord record4 = init(RND.plusInt(1000000), "Anutin", "Petr");
        ClientRecord record5 = init(RND.plusInt(1000000), "Li", "Petr");
        ClientRecord record6 = init(RND.plusInt(1000000), "Kim", "Petr");

        params.filterCol = "";
        params.filter = "";
        params.sortBy = SortBy.SURNAME.toString();
        params.sortDir = SortDir.ASC.toString();
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(6);
        assertThat(clients.get(0).fio).isEqualTo(record4.fio);
        assertThat(clients.get(5).fio).isEqualTo(record2.fio);
    }

    @Test
    public void getClientList_sort_desc() {
        deleteAllClients();

        ClientRecord record1 = init(RND.plusInt(1000000), "Ivanov", "Ivan");
        ClientRecord record2 = init(RND.plusInt(1000000), "Petrov", "A");
        ClientRecord record3 = init(RND.plusInt(1000000), "Coi", "B");
        ClientRecord record4 = init(RND.plusInt(1000000), "Anutin", "Petr");
        ClientRecord record5 = init(RND.plusInt(1000000), "Li", "C");
        ClientRecord record6 = init(RND.plusInt(1000000), "Kim", "D");

        params.filterCol = "";
        params.filter = "";
        params.sortBy = SortBy.NAME.toString();
        params.sortDir = SortDir.DESC.toString();
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(6);
        assertThat(clients.get(0).fio).isEqualTo(record4.fio);
        assertThat(clients.get(5).fio).isEqualTo(record2.fio);
    }

    @Test
    public void getClientList_sort_filter() {
        deleteAllClients();

        ClientRecord record1 = init(RND.plusInt(1000000), "Ivanov", "Ivan");
        ClientRecord record2 = init(RND.plusInt(1000000), "Petrov", "A");
        ClientRecord record3 = init(RND.plusInt(1000000), "Coi", "B");
        ClientRecord record4 = init(RND.plusInt(1000000), "Petrova", "Petr");
        ClientRecord record5 = init(RND.plusInt(1000000), "Li", "C");
        ClientRecord record6 = init(RND.plusInt(1000000), "Kim", "D");

        params.filterCol = FilterBy.SURNAME.toString();
        params.filter = "p";
        params.sortBy = SortBy.NAME.toString();
        params.sortDir = SortDir.ASC.toString();
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(2);
        assertThat(clients.get(0).fio).isEqualTo(record2.fio);
        assertThat(clients.get(1).fio).isEqualTo(record4.fio);
    }

    @Test
    public void getClientList_sort_filter_desc() {
        deleteAllClients();

        ClientRecord record1 = init(RND.plusInt(1000000), "Ivanov", "Ivan");
        ClientRecord record2 = init(RND.plusInt(1000000), "Petrov", "A");
        ClientRecord record3 = init(RND.plusInt(1000000), "Coi", "B");
        ClientRecord record4 = init(RND.plusInt(1000000), "Petrova", "AA");
        ClientRecord record5 = init(RND.plusInt(1000000), "Li", "C");
        ClientRecord record6 = init(RND.plusInt(1000000), "Kim", "AAA");

        params.filterCol = FilterBy.NAME.toString();
        params.filter = "a";
        params.sortBy = SortBy.SURNAME.toString();
        params.sortDir = SortDir.DESC.toString();
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(3);
        assertThat(clients.get(0).fio).isEqualTo(record4.fio);
        assertThat(clients.get(1).fio).isEqualTo(record2.fio);
        assertThat(clients.get(2).fio).isEqualTo(record6.fio);
    }

    @Test
    public void editClient_test() {
        deleteAllClients();

        ClientRecord record = init(RND.plusInt(1000000), "Ivanov", "Ivan");

        clientRegister.get().updateClientField(record.id, "name", "Alex");
        client = clientRegister.get().getClient(record.id);

        assertThat(client).isNotNull();
        assertThat(client.surname).isEqualTo(record.surname);
        assertThat(client.name).isNotEqualTo(record.name);
        assertThat(client.id).isEqualTo(record.id);
    }

    @Test
    public void deleteClient_test() {
        deleteAllClients();

        ClientRecord record1 = init(RND.plusInt(1000000), "Ivanov", "Ivan");
        ClientRecord record2 = init(RND.plusInt(1000000), "Petrov", "A");
        //
        ClientRecord record3 = init(RND.plusInt(1000000), "Coi", "B");
        //
        ClientRecord record4 = init(RND.plusInt(1000000), "Anutin", "Petr");
        ClientRecord record5 = init(RND.plusInt(1000000), "Li", "C");
        ClientRecord record6 = init(RND.plusInt(1000000), "Kim", "D");

        clientRegister.get().deleteClient(record3.id);
        params.filterCol = "";
        params.filter = "";
        params.sortBy = "";
        params.sortDir = "";
        clients = clientRegister.get().getClients(params);

        assertThat(clients).isNotNull();
        assertThat(clients).hasSize(5);
        assertThat(clients.get(0).fio).isNotEqualTo(record3.fio);
        assertThat(clients.get(1).fio).isNotEqualTo(record3.fio);
        assertThat(clients.get(2).fio).isNotEqualTo(record3.fio);
        assertThat(clients.get(3).fio).isNotEqualTo(record3.fio);
        assertThat(clients.get(4).fio).isNotEqualTo(record3.fio);
    }

    @Test
    public void addClient_test() {
        deleteAllClients();

//        ClientRecord record = init(RND.plusInt(1000000), "Ivanov", "Ury");

//            clientRegister.get().addClient(record.id, record.surname, record.name, 1);




//        assertThat(client).isNotNull();
//        assertThat(Objects.equals(client.surname, record.surname));
//        System.out.println(client.surname + " " + record.surname);
//        assertThat(Objects.equals(client.name, record.name));
//        System.out.println(client.name + " " + record.name);
//        assertThat(Objects.equals(client.id, record.id));
    }

    private void deleteAllClients() {
        clientTestDao.get().deleteAllClients();
    }

    private ClientRecord init(int id, String surname, String name) {
        ClientRecord record = new ClientRecord();
        record.fio = surname + " " + name;
        record.id = id;
        record.name = name;
        record.surname = surname;
        clientTestDao.get().insertNotFullClient(record.id, surname, name, 1);
        return record;
    }

}
