package kz.greetgo.sandbox.register.impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.ClientRegister;
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
    public BeanGetter<Jdbc> jdbcBean;

    private final String INSERT_STATEMENT = "insert into clients (id,surname,name,patronymic,gender,birth_date,charm,actual) VALUES (default,?,?,?,?::gender,?,?,true) ";
    private final String SELECT_STATEMENT = "select c.id,c.surname,c.name,c.patronymic,c.gender,c.birth_date," +
            "c.charm as charm_id, ch.name as charm_name,ch.description,ch.energy  from clients c,charms ch "+
            "where c.actual = true and c.charm = ch.id";

    @Override
    public Client getById(Long id) {
        if (id == null)
            throw new NullPointerException("ID IS NULL");

        return clientDao.get().load(id);
    }

    @Override
    public Client update(Client client) {
        if (client.id == null || hasNull(client))
            throw new NullPointerException("CLIENT HAS NULL DATA");

        return clientDao.get().update(client);
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
    public List<Client> getListByParam(List<String> FIO,Integer limit, Integer offset,  String sortCol,  String order) {
        if(sortCol == null)
            sortCol = "id";
        if(order == null)
            order = "ASC";

        final String selectStmnt = getSelectStatement(FIO,limit,offset,sortCol,order);

        System.out.println(selectStmnt);

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
                               rs.getDouble(10)
                       );

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
        if(fio.size() >= 0 && fio.get(0) != null) {
            builder.append(" and c.surname like '");
            builder.append(fio.get(0));
            builder.append("%'");
        }
        if(fio.size() >= 1 && fio.get(1) != null) {
            builder.append(" and c.name like '");
            builder.append(fio.get(1));
            builder.append("%'");
        }
        if(fio.size() >= 2 && fio.get(1) != null) {
            builder.append(" and c.patronymic like '");
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
