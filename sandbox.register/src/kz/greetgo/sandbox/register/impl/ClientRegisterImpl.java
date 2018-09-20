package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.FilterParams;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.util.JdbcSandbox;
import kz.greetgo.util.RND;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

    public BeanGetter<ClientDao> clientDao;
    public BeanGetter<JdbcSandbox> jdbc;

    @Override
    public List<ClientRecord> getClients(FilterParams params) {

        //JDBC
        return jdbc.get().execute(con -> {
            StringBuilder sql = new StringBuilder("Select * from client where actual = 1");

            if (!params.filter.isEmpty() && !params.filterCol.isEmpty()) {
                sql.append(" and lower(" + params.filterCol + ") like '" + params.filter + "%'");
            }

            if (!params.sortBy.isEmpty() && !params.sortDir.isEmpty()) {
                sql.append(" ORDER BY " + params.sortBy + " " + params.sortDir);
            }
            List<ClientRecord> clientList = new ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ClientRecord record = new ClientRecord();
                        record.fio = rs.getString("surname") + " " + rs.getString("name");
                        record.id = rs.getInt("id");
                        clientList.add(record);
                    }
                    return clientList;
                }
            }
        });
    }

    @Override
    public void deleteClient(int id) {
        clientDao.get().deleteClientByID(id);
    }

    @Override
    public void editClient(ClientDetail cd) {
        if (cd.id == 0) {
            addNewClient(cd);
        } else {
            editExistedClient(cd);
        }
    }

    private void editExistedClient(ClientDetail cd) {
        if (cd.surname!=null)
            clientDao.get().updateClientField(cd.id, "surname", cd.surname);

        if (cd.name!=null)
            clientDao.get().updateClientField(cd.id, "name", cd.name);

        if (cd.patronymic!=null)
            clientDao.get().updateClientField(cd.id, "patronymic", cd.patronymic);

        if (cd.gender!=null)
            clientDao.get().updateClientField(cd.id, "gender", cd.gender);

        if (cd.birthDate!=null) {
            clientDao.get().updateClientField(cd.id, "birth_date", cd.birthDate);
        }

        if (cd.charm != 0)
            clientDao.get().updateClientField(cd.id, "charm", cd.charm);

        if (cd.factStreet!=null)
            clientDao.get().updateClientAddrFACTField(cd.id, "street", cd.factStreet);

        if (cd.factNo!=null)
            clientDao.get().updateClientAddrFACTField(cd.id, "house", cd.factNo);

        if (cd.factFlat!=null)
            clientDao.get().updateClientAddrFACTField(cd.id, "flat", cd.factFlat);

        //reg
        if (cd.regStreet!=null)
            clientDao.get().updateClientAddrREGField(cd.id, "street", cd.regStreet);

        if (cd.regNo!=null)
            clientDao.get().updateClientAddrREGField(cd.id, "house", cd.regNo);

        if (cd.regFlat!=null)
            clientDao.get().updateClientAddrREGField(cd.id, "flat", cd.regFlat);

        //phones
        if (cd.homePhoneNumber!=null)
            clientDao.get().updateClientPhoneNumber(cd.id, cd.homePhoneNumber, "HOME");

        if (cd.workPhoneNumber!=null)
            clientDao.get().updateClientPhoneNumber(cd.id, cd.workPhoneNumber, "WORK");

        if (cd.mobileNumber1!=null)
            clientDao.get().updateClientPhoneNumber(cd.id, cd.mobileNumber1, "MOBILE");

        if (cd.mobileNumber2!=null)
            clientDao.get().updateClientPhoneNumber(cd.id, cd.mobileNumber2, "MOBILE");

        if (cd.mobileNumber3!=null)
            clientDao.get().updateClientPhoneNumber(cd.id, cd.mobileNumber3, "MOBILE");

    }

    private void addNewClient(ClientDetail cd) {
        System.out.println("adding... ");
        int id = RND.plusInt(1000000);

        clientDao.get().insertIntoClient(id, cd);
        cd.id = id;

        if (cd.factStreet!=null && cd.factNo!=null && cd.factFlat!=null) {
            clientDao.get().insertIntoClientAddrFACT(cd);
        }

        if (cd.regStreet!=null && cd.regNo!=null && cd.regFlat!=null)
            clientDao.get().insertIntoClientAddrREG(cd);

        if (cd.homePhoneNumber!=null)
            clientDao.get().insertIntoClientPhone(id, "HOME", cd.homePhoneNumber);

        if (cd.workPhoneNumber!=null)
            clientDao.get().insertIntoClientPhone(id, "WORK", cd.workPhoneNumber);

        if (cd.mobileNumber1!=null)
            clientDao.get().insertIntoClientPhone(id, "MOBILE", cd.mobileNumber1);

        if (cd.mobileNumber2!=null)
            clientDao.get().insertIntoClientPhone(id, "MOBILE", cd.mobileNumber2);

        if (cd.mobileNumber3!=null)
            clientDao.get().insertIntoClientPhone(id, "MOBILE", cd.mobileNumber3);
    }
}
