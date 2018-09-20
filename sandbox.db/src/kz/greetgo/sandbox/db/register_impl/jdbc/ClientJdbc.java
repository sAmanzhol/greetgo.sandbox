package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientJdbc implements ConnectionCallback<Integer> {

    public ClientFilter clientFilter;



    public ClientJdbc(ClientFilter clientFilter) {

        this.clientFilter = clientFilter;

    }


    @Override
    public Integer doInConnection(Connection connection) throws Exception {


        try (PreparedStatement ps = connection.prepareStatement(generateSql().toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                Integer count = 0;
                while (rs.next()) {

                    count = rs.getInt("count");

                }
                rs.close();
                ps.close();
                connection.close();
                return count;

            }
        }

    }


    private StringBuilder generateSql( ) {

        StringBuilder sb = new StringBuilder();
            sb.append("select count(id)");
            sb.append(" from client");
            sb.append(" where firstname like '" + clientFilter.firstname + "%'");
            if (!clientFilter.lastname.equals(""))
                sb.append(" and lastname like '" + clientFilter.patronymic + "%'");
            if (!clientFilter.patronymic.equals(""))
                sb.append(" and patronymic like '" + clientFilter.lastname + "%'");


        return sb;
    }


    private void appendSelect(StringBuilder sb) {
        sb.append("select	*");
    }

}
