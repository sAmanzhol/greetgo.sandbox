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
import java.util.List;

@Bean
public class ClientRegistrImpl implements ClientRegister {
    public BeanGetter<ClientDao> clientDao;
    public BeanGetter<Jdbc> jdbcBean;

    private final String INSERT_STATEMENT = "insert into clients (id,surname,name,patronymic,gender,birth_date,charm,actual) VALUES (default,?,?,?,?::gender,?,?,true) ";

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
                setInsertStatement(preparedStatement, client);
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
                    setInsertStatement(preparedStatement, clientList.get(0));
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

    @Override
    public List<Client> getByParam(Long start, Long offset) {
        return null;
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

    void setInsertStatement(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.surname);
        statement.setString(2, client.name);
        statement.setString(3, client.patronymic);
        statement.setString(4, client.gender.toString());
        statement.setTimestamp(5, client.birthDate);
        statement.setLong(6, client.charm.id);
    }


}
