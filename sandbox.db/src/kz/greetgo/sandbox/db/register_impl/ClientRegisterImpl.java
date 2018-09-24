package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientDetailsJdbc;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbc;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbcListRecord;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbcRecord;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

    public BeanGetter<ClientDao> clientDao;

    public BeanGetter<Jdbc> jdbc;

    @Override
    public List<Charm> getCharm() {
        return clientDao.get().listCharm();
    }

    @Override
    public void deleteClient(Integer clientMarkId) {

        clientDao.get().deleteClientById(clientMarkId);
    }

    @Override
    public Charm getCharmById(Integer charmId) {
        Charm charm;

        if (charmId == null) {
            return null;
        }

        charm = clientDao.get().getCharmById(charmId);
        if (charm != null) {
            return clientDao.get().getCharmById(charmId);
        }
        if (charm == null) {
            charm = new Charm();
            charm.id = charmId;
            charm.name = String.valueOf(charmId);
            charm.description = String.valueOf(charmId);
            charm.energy = charmId;
            charm.actually = false;
        }

        return charm;
    }

    @Override
    public ClientDetails getClientDetails(Integer clientMarkId) {
        ClientDetails clientDetails;
        clientDetails = jdbc.get().execute(new ClientDetailsJdbc(clientMarkId));
        return clientDetails;
    }

    @Override
    public ClientRecord saveClient(ClientToSave clientToSave) {

        ClientRecord clientRecord;

        if (clientToSave.id == null) {
            Integer maxes = clientDao.get().getmaxClientId();
            if (maxes == null)
                maxes = 0;
            clientToSave.id = (int) (maxes + 1);
        }

        clientDao.get().deleteClientPhone(clientToSave.id);
        clientDao.get().upsertClient(clientToSave);
        clientDao.get().upsertClientAddr(clientToSave.addressOfResidence, clientToSave.id);
        clientDao.get().upsertClientAddr(clientToSave.addressOfRegistration, clientToSave.id);
        for (ClientPhone clientPhone : clientToSave.phone) {
            clientDao.get().upsertClientPhone(clientPhone, clientToSave.id);
        }
        clientDao.get().upsertClientAccount(clientToSave.id, new Timestamp(2));

        clientRecord = jdbc.get().execute(new ClientJdbcRecord(clientToSave.id));
        System.err.println("ClientRecordsss:" + clientRecord);
        return clientRecord;

    }

    @Override
    public List<ClientRecord> getClientList(ClientFilter clientFilter) {

        List<ClientRecord> clientRecords = new ArrayList<>();
        clientRecords = jdbc.get().execute(new ClientJdbcListRecord(clientFilter));
        return clientRecords;
    }

    @Override
    public Integer getClientTotalRecord(ClientFilter clientFilter) {

        clientFilter.recordTotal = jdbc.get().execute(new ClientJdbc(clientFilter));
        return clientFilter.recordTotal;

    }

}
