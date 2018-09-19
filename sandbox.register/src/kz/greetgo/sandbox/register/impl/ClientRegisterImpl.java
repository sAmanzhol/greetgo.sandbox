package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.FilterParams;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.util.JdbcSandbox;

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
    public ClientRecord getClient(int id) {
        return clientDao.get().selectClientById(id);
    }

    @Override
    public void updateClientField(int id, String fieldName, Object fieldValue) {
        clientDao.get().updateClientField(id, fieldName, fieldValue);
    }

    @Override
    public void deleteClient(int id) {
        clientDao.get().deleteClient(id);
    }

    @Override
    public void addClient(int id, String surname, String name, int actual) {
        clientDao.get().addClient(id, surname, name, actual);
    }
}
