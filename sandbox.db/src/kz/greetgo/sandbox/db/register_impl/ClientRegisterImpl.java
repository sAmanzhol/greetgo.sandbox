package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbc;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientJdbcListRecord;

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
        if (charmId == null) {
            return null;
        }
        Charm charm = new Charm();
        Integer id = clientDao.get().idCharmById(charmId);
        if (id != null) {
            return clientDao.get().selectCharmById(charmId);
        }
        if (id == null) {
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

        ClientDetails clientDetails = new ClientDetails();
        Client client = clientDao.get().selectClientById(clientMarkId);
        clientDetails.addressOfResidence = clientDao.get().selectClientAddrById(clientMarkId, AddrType.FACT);
        clientDetails.addressOfRegistration = clientDao.get().selectClientAddrById(clientMarkId, AddrType.REG);
        for (ClientPhone clientPhone : clientDao.get().selectClientPhoneById(clientMarkId)) {
            clientDetails.phone.add(clientPhone);
        }
        clientDetails.id = client.id;
        clientDetails.firstname = client.firstname;
        clientDetails.lastname = client.lastname;
        clientDetails.patronymic = client.patronymic;
        clientDetails.dateOfBirth = client.birthDate;
        clientDetails.gender = client.gender;
        clientDetails.characterId = client.charm;

        return clientDetails;
    }

    @Override
    public ClientRecord saveClient(ClientToSave clientDetails) {

        Integer id = clientDao.get().idClientById(clientDetails.id);
        ClientRecord clientRecord = new ClientRecord();
        Client client = new Client();


        if (id == null) {
            clientDao.get().insertClient(clientDetails.id, clientDetails.firstname, clientDetails.lastname, clientDetails.patronymic, clientDetails.gender, clientDetails.dateOfBirth, clientDetails.characterId);
            clientDao.get().insertClientAddr(clientDetails.id, clientDetails.addressOfRegistration.type, clientDetails.addressOfRegistration.street, clientDetails.addressOfRegistration.house, clientDetails.addressOfRegistration.flat);
            clientDao.get().insertClientAddr(clientDetails.id, clientDetails.addressOfResidence.type, clientDetails.addressOfResidence.street, clientDetails.addressOfResidence.house, clientDetails.addressOfResidence.flat);
            for (ClientPhone clientPhone : clientDetails.phone) {
                clientDao.get().insertClientPhone(clientDetails.id, clientPhone.number, clientPhone.type);
            }

        }

        if (id != null) {
            clientDao.get().updateClient(clientDetails.id, clientDetails.firstname, clientDetails.lastname, clientDetails.patronymic, clientDetails.gender, clientDetails.dateOfBirth, clientDetails.characterId);
            for (ClientPhone clientPhone : clientDetails.phone) {
                clientDao.get().updateClientPhone(clientDetails.id, clientPhone.number, clientPhone.type);
            }
            clientDao.get().updateClientAddr(clientDetails.id, clientDetails.addressOfRegistration.type, clientDetails.addressOfRegistration.street, clientDetails.addressOfRegistration.house, clientDetails.addressOfRegistration.flat);
            clientDao.get().updateClientAddr(clientDetails.id, clientDetails.addressOfResidence.type, clientDetails.addressOfResidence.street, clientDetails.addressOfResidence.house, clientDetails.addressOfResidence.flat);
        }
        client = clientDao.get().selectClientById(clientDetails.id);
        clientRecord.id = client.id;
        clientRecord.firstname = client.firstname;
        clientRecord.lastname = client.lastname;
        clientRecord.patronymic = client.patronymic;
        clientRecord.dateOfBirth = client.birthDate;
        clientRecord.characterName = clientDao.get().nameCharmById(clientDetails.characterId);
        clientRecord.totalAccountBalance= clientDao.get().selectTotalAccountBalance(clientDetails.id);
        clientRecord.maximumBalance= clientDao.get().selectMaximumBalance(clientDetails.id);
        clientRecord.minimumBalance= clientDao.get().selectMinimumBalance(clientDetails.id);

        System.err.println("ClientRecordsss:"+clientRecord);
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
