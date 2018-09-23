package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDetailsJdbc implements ConnectionCallback<ClientDetails> {
    Integer clientMarkId;

    ClientDetails clientDetails = new ClientDetails();
    String mobile =null;

    public ClientDetailsJdbc(Integer clientMarkId) {

        this.clientMarkId = clientMarkId;
    }


    @Override
    public ClientDetails doInConnection(Connection connection) throws Exception {

        try (PreparedStatement ps = connection.prepareStatement(generateSql().toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientDetails.id = rs.getInt("id");
                    clientDetails.firstname = rs.getString("firstname");
                    clientDetails.lastname = rs.getString("lastname");
                    clientDetails.patronymic = rs.getString("patronymic");
                    clientDetails.dateOfBirth = rs.getDate("dateOfBirth");
                    clientDetails.gender = GenderType.valueOf(rs.getString("gender"));
                    clientDetails.characterId = rs.getInt("characterId");
                    if (setClientAddr(rs, AddrType.REG) != null && clientDetails.addressOfRegistration.client==null)
                        clientDetails.addressOfRegistration = setClientAddr(rs, AddrType.REG);
                    if (setClientAddr(rs, AddrType.FACT) != null && clientDetails.addressOfResidence.client==null)
                        clientDetails.addressOfResidence = setClientAddr(rs, AddrType.FACT);

                    if(!rs.getString("number").equals(mobile))
                    clientDetails.phone.add(setPhone(rs));
                }
            }
        }

        return clientDetails;
    }


    private StringBuilder generateSql() {

        StringBuilder sb = new StringBuilder();
        sb.append("select c.id as id, c.firstname as firstname , c.lastname as lastname, c.patronymic as patronymic, c.gender as gender,\n" +
                "       c.charm as characterId,c.birth_date as dateOfBirth, ca.type as catype , ca.street, ca.house, ca.flat, cp.type as cptype, cp.number\n" +
                "from client c left join client_addr\n" +
                "    ca on c.id = ca.client left join  client_phone cp on c.id = cp.client\n" +
                "where c.id = " + clientMarkId );
        sb.append(" order by number");
        return sb;
    }

    private ClientPhone setPhone(ResultSet rs) throws SQLException {
        ClientPhone clientPhone = new ClientPhone();
        clientPhone.client = rs.getInt("id");
        clientPhone.type = PhoneType.valueOf(rs.getString("cptype"));
        clientPhone.number = rs.getString("number");
        mobile = rs.getString("number");
        return clientPhone;
    }

    private ClientAddr setClientAddr(ResultSet rs, AddrType type) throws SQLException {
        ClientAddr clientAddr = new ClientAddr();
        if (type.equals(AddrType.valueOf(rs.getString("catype")))) {
            clientAddr.client = rs.getInt("id");
            clientAddr.type = AddrType.valueOf(rs.getString("catype"));
            clientAddr.street = rs.getString("street");
            clientAddr.house = rs.getString("house");
            clientAddr.flat = rs.getString("flat");
            return clientAddr;
        }
        return null;

    }
}
