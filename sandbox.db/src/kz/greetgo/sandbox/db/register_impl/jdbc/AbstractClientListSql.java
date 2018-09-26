package kz.greetgo.sandbox.db.register_impl.jdbc;

import com.itextpdf.text.DocumentException;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractClientListSql<T, R> extends AbstractBasicSql<T, R> {

    public AbstractClientListSql(ClientFilter clientFilter) {
        super(clientFilter);
    }

    @Override
    protected void select() {
        sb.append("select c.id as id, c.firstname as firstname, c.lastname as lastname, c.patronymic as patronymic, ch.name as character, " +
                "c.birth_date as dateOfBirth, ca.maximumBalance, ca.minimumBalance, ca.totalAccountBalance");
    }


    @Override
    protected void from() {
        sb.append(" from client c ");
    }

    @Override
    protected void leftJoin() {
        sb.append("left join charm ch on c.charm =ch.id  left join ( select client, avg(money) as totalAccountBalance, max(money) as maximumBalance, min(money) as minimumBalance " +
                "from client_account ca group by ca.client) ca on c.id = ca.client");
    }

    @Override
    protected void orderBy() {

        if (!clientFilter.orderBy.equals("")) {
            sb.append(" order by " + clientFilter.orderBy);
            if (clientFilter.sort)
                sb.append(" asc");
            if (!clientFilter.sort)
                sb.append(" desc");
        }
    }


    @Override
    protected void innerJoin() {

    }

    @Override
    protected void groupBy() {

    }

    @Override
    protected void offSet() {

    }


    @Override
    protected void limit() {

    }

    @Override
    protected R getResult() {
        return null;
    }

    @Override
    protected void addRow(T t) throws DocumentException {

    }

    @Override
    protected T doInSelection(ResultSet rs) throws SQLException {
        return null;
    }
}
