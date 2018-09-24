package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientJdbcRecord implements ConnectionCallback<ClientRecord> {
    public Integer clientToSaveId;

    ClientRecord clientRecord = new ClientRecord();

    public ClientJdbcRecord(Integer clientToSaveId) {

        this.clientToSaveId = clientToSaveId;

    }


    @Override
    public ClientRecord doInConnection(Connection connection) throws Exception {

        try (PreparedStatement ps = connection.prepareStatement(generateSql().toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    clientRecord.id = rs.getInt("id");
                    clientRecord.firstname = rs.getString("firstname");
                    clientRecord.lastname = rs.getString("lastname");
                    clientRecord.patronymic = rs.getString("patronymic");
                    clientRecord.characterName = rs.getString("character");
                    clientRecord.dateOfBirth = rs.getDate("dateOfBirth");
                    clientRecord.maximumBalance = rs.getInt("maximumBalance");
                    clientRecord.minimumBalance = rs.getInt("minimumBalance");
                    clientRecord.totalAccountBalance = rs.getInt("totalAccountBalance");

                }
                rs.close();
                ps.close();
                connection.close();
                return clientRecord;

            }
        }

    }


    private StringBuilder generateSql() {

        StringBuilder sb = new StringBuilder();
        sb.append("select c.id as id, c.firstname as firstname , c.lastname as lastname, c.patronymic as patronymic, ch.name as character,\n" +
                "       c.birth_date as dateOfBirth, ca.maximumBalance, ca.minimumBalance, ca.totalAccountBalance from client c left join charm ch on c.charm =ch.id\n" +
                "  left join ( select client, avg(money) as totalAccountBalance, max(money) as maximumBalance, min(money) as minimumBalance from client_account ca group by ca.client)\n" +
                "    ca on c.id = ca.client where c.id =" +clientToSaveId);

        return sb;
    }

}
