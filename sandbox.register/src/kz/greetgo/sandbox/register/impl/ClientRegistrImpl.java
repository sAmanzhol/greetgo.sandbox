package kz.greetgo.sandbox.register.impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.Phone;
import kz.greetgo.sandbox.controller.register.AccountRegister;
import kz.greetgo.sandbox.controller.register.AddressRegister;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.register.dao.AccountDao;
import kz.greetgo.sandbox.register.dao.AddressDao;
import kz.greetgo.sandbox.register.dao.ClientDao;

import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Bean
public class ClientRegistrImpl implements ClientRegister {
    public BeanGetter<ClientDao> clientDao;
    public BeanGetter<AddressRegister> addressRegister;
    public BeanGetter<AccountRegister> accountRegister;
    public BeanGetter<PhoneRegister> phoneRegister;
    public BeanGetter<Jdbc> jdbcBean;

    private final String INSERT_STATEMENT = "insert into clients (id,surname,name,patronymic,gender,birth_date,charm,actual) VALUES (default,?,?,?,?::gender,?,?,true) ";
    private final String SELECT_STATEMENT = "select c.id,c.surname,c.name,c.patronymic,c.gender,c.birth_date,c.charm " +
            "  as charm_id, ch.name as charm_name,ch.description,ch.energy " +
            "  ,(SELECT max(acc.money) as maxbal " +
            "    from client_account acc where acc.client = c.id) " +
            "  ,(SELECT min(acc.money) as minbal " +
            "    from client_account acc where acc.client = c.id) " +
            "  ,(SELECT sum(acc.money) as sumbal " +
            "   from client_account acc where acc.client = c.id) " +
            " " +
            "from clients c,charms ch " +
            "where c.charm = ch.id and c.actual = true";

    @Override
    public Client getById(Long id) {
        if (id == null)
            throw new NullPointerException("ID IS NULL");

        Client client =  clientDao.get().load(id);
        if(client != null) {
            client.addresses = addressRegister.get().getByClientId(client.id);
            client.phones = phoneRegister.get().getAllByClientId(client.id);
        }
        return client;
    }

    @Override
    public Client update(Client client) {
        if (client.id == null || hasNull(client))
            throw new NullPointerException("CLIENT HAS NULL DATA");

        upsertClientDetail(client);

        return clientDao.get().update(client);
    }

    public void upsertClientDetail(Client client){
        if(client.addresses == null)
            return;

        for (Address address:client.addresses) {
            if(address.id != null)
                addressRegister.get().update(address);
            else{
                address.client = client.id;
                addressRegister.get().insert(address);
            }
        }

        for(Phone phone : client.phones) {
            if (phone.id != null)
                phoneRegister.get().update(phone);
            else {
                phone.client = client.id;
                phoneRegister.get().insert(phone);
            }
        }
    }

    @Override
    public Long insert(Client client) {
        if(hasNull(client))
            throw new NullPointerException("CLIENT IS NULL");

        Long resultId = null;

        resultId = jdbcBean.get().execute(con -> {
            Long id = null;
            try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_STATEMENT + "RETURNING id")) {
                setInsertStatementParams(preparedStatement, client);
                preparedStatement.execute();

                try (ResultSet rs = preparedStatement.getResultSet()) {
                    if (rs.next())
                        id = rs.getLong(1);
                }
                catch (Exception e) {
                    e. printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            con.close();
            return id;
        });
        client.id = resultId;
        upsertClientDetail(client);
        return resultId;
    }

    public void insertBatch(List<Client> clientList) {
        List <Long> resultIdList = null;

         jdbcBean.get().execute(con -> {
            try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_STATEMENT)) {
                for (Client client : clientList) {
                    setInsertStatementParams(preparedStatement, clientList.get(0));
                    preparedStatement.addBatch();
                }

                int []resultSet = preparedStatement.executeBatch();

                for(int i = 0;i<resultSet.length;i++)
                {
                    if(resultSet[i] > 0) {
                        //console log

                    }
                }
            }
            catch (BatchUpdateException jdbce) {
                jdbce.getNextException().printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            con.close();
            return 1;
        });
    }
//    c.id,c.surname,c.name,c.patronymic,c.gender,c.birth_date," +
//            "c.charm as charm_id, ch.name as charm_name,ch.description,ch.energy
    @Override
    public List<Client> getListByParam(List<String> FIO,Integer limit, Integer offset,  String sortCol,  Integer orderI) {
        String order = null;
        if(orderI == null || orderI > 0)
            order = "ASC";
        else if(orderI < 0)
            order = "DESC";

        final String selectStmnt = getSelectStatement(FIO,limit,offset,sortCol,order);

        List<Client> clientList = new ArrayList<>();
        jdbcBean.get().execute(con ->
        {
            try(PreparedStatement preparedStatement = con.prepareStatement(selectStmnt))
            {
               try(ResultSet rs = preparedStatement.executeQuery())
               {
                   Client client = null;
                   while (rs.next())
                   {
                       client = new Client(
                               rs.getLong(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getObject(5),
                               rs.getTimestamp(6),
                               rs.getLong(7),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getDouble(10),
                               rs.getDouble(11),
                               rs.getDouble(12),
                               rs.getDouble(13)
                       );
                        client.addresses = addressRegister.get().getByClientId(client.id);
                        client.phones = phoneRegister.get().getAllByClientId(client.id);
                       clientList.add(client);
                   }
               }
            }
         return !clientList.isEmpty();
        });
        return clientList;
    }

    @Override
    public Long getCount() {
        return clientDao.get().getCount();
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new NullPointerException("ID IS NULL");

        clientDao.get().delete(id);
    }

    boolean hasNull(Client client) {
        return client == null
                || client.name == null
                || client.surname == null
                || client.patronymic == null
                || client.charm == null
                || client.birthDate == null;
    }

    void setInsertStatementParams(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.surname);
        statement.setString(2, client.name);
        statement.setString(3, client.patronymic);
        statement.setString(4, client.gender.toString());
        statement.setTimestamp(5, client.birthDate);
        statement.setLong(6, client.charm.id);
    }

    String getSelectStatement(List<String> fio,Integer limit, Integer offset, String sortCol, String order){
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_STATEMENT);
        if(fio.size() >= 1 && fio.get(0) != null) {
            builder.append(" and c.name like '%");
            builder.append(fio.get(0));
            builder.append("%'");
        }
        if(fio.size() >= 2 && fio.get(1) != null) {
            builder.append(" and c.surname like '%");
            builder.append(fio.get(1));
            builder.append("%'");
        }
        if(fio.size() >= 3 && fio.get(2) != null) {
            builder.append(" and c.patronymic like '%");
            builder.append(fio.get(2));
            builder.append("%'");
        }
        builder.append(" order by ");
        builder.append(sortCol);
        builder.append(" ");
        builder.append(order);
        builder.append(" limit ");
        builder.append(limit);
        builder.append(" offset ");
        builder.append(offset);

        return builder.toString();
    }


}
