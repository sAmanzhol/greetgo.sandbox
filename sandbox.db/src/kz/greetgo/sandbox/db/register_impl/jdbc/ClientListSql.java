package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientListSql extends AbstractClientListSql<ClientRecord, List<ClientRecord>> {

    List<ClientRecord> clientRecords = new ArrayList<>();

    public ClientListSql(ClientFilter clientFilter) {

        super(clientFilter);
    }

    @Override
    protected void offSet() {
        sb.append(" offset ? ");
        param.add((clientFilter.recordSize * clientFilter.page));
    }

    @Override
    protected void limit() {
        sb.append(" limit ? ");
        param.add(clientFilter.recordSize);
    }

    @Override
    protected void addRow(ClientRecord clientRecord) {
        clientRecords.add(clientRecord);
    }

    @Override
    protected List<ClientRecord> getResult() {
        return clientRecords;
    }

    @Override
    protected ClientRecord doInSelection(ResultSet rs) throws SQLException {

        ClientRecord clientRecord = new ClientRecord();
        clientRecord.id = rs.getInt("id");
        clientRecord.firstname = rs.getString("firstname");
        clientRecord.lastname = rs.getString("lastname");
        clientRecord.patronymic = rs.getString("patronymic");
        clientRecord.dateOfBirth = rs.getDate("dateOfBirth");
        clientRecord.characterName = rs.getString("character");
        clientRecord.totalAccountBalance = rs.getInt("totalAccountBalance");
        clientRecord.maximumBalance = rs.getInt("maximumBalance");
        clientRecord.minimumBalance = rs.getInt("minimumBalance");

        return clientRecord;

    }
}
