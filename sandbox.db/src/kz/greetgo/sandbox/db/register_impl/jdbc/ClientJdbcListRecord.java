package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientJdbcListRecord implements ConnectionCallback<List<ClientRecord>> {
    public ClientFilter clientFilter;

    List<ClientRecord> clientRecordList = new ArrayList<>();
    public ClientJdbcListRecord(ClientFilter clientFilter) {

        this.clientFilter = clientFilter;

    }


    @Override
    public List<ClientRecord> doInConnection(Connection connection) throws Exception {

        try (PreparedStatement ps = connection.prepareStatement(generateSql().toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    ClientRecord clientRecord = new ClientRecord();
                    clientRecord.id=rs.getInt("id");
                    clientRecord.firstname= rs.getString("firstname");
                    clientRecord.lastname= rs.getString("lastname");
                    clientRecord.patronymic=rs.getString("patronymic");
                    clientRecord.characterName=rs.getString("character");
                    clientRecord.dateOfBirth=rs.getDate("dateOfBirth");
                    clientRecord.maximumBalance=rs.getInt("maximumBalance");
                    clientRecord.minimumBalance=rs.getInt("minimumBalance");
                    clientRecord.totalAccountBalance=rs.getInt("totalAccountBalance");
                    clientRecordList.add(clientRecord);
                }
                rs.close();
                ps.close();
                connection.close();
                return clientRecordList;

            }
        }

    }


    private StringBuilder generateSql( ) {

        StringBuilder sb = new StringBuilder();
            sb.append("select client.id as id, client.firstname as firstname , client.lastname as lastname, client.patronymic as patronymic, charm.name as character, client.birth_date as dateOfBirth,\n" +
                    "       max(client_account.money) as maximumBalance , avg(client_account.money) as totalAccountBalance, min(client_account.money) as minimumBalance");
            sb.append(" from client left join charm on client.charm =charm.id left join client_account on client.id = client_account.client ");
            sb.append(" where client.charm = charm.id and");
            sb.append("  client.firstname like '" + clientFilter.firstname + "%'");
            if (!clientFilter.lastname.equals(""))
                sb.append(" and client.lastname like '" + clientFilter.patronymic + "%'");
            if (!clientFilter.patronymic.equals(""))
                sb.append(" and client.patronymic like '" + clientFilter.lastname + "%'");
            sb.append(" group by client.id, charm.name ");
            sb.append(" order by " + clientFilter.orderBy);
            if (clientFilter.sort)
                sb.append(" asc");
            if (!clientFilter.sort)
                sb.append(" desc");
            sb.append(" limit " +clientFilter.recordSize);
            sb.append(" offset " +(clientFilter.recordSize*clientFilter.page));

        return sb;
    }

}
