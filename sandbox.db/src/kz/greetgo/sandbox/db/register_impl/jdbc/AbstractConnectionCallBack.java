package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.db.ConnectionCallback;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalQueries.offset;

public abstract class AbstractConnectionCallBack<T, R> implements ConnectionCallback<T> {
    protected List<Object> param = new ArrayList<>();
    protected SQL sql = new SQL();

    protected final void prepareSql() {
        select();
        innerJoin();
        leftJoin();
        where();
        groupBy();
        orderBcy();
        limit();
        offSet();
        addRow();
        getResult();
    }

    protected abstract R getResult();

    protected abstract void innerJoin();

    protected abstract void offSet();

    protected abstract void select();

    protected abstract void leftJoin();

    protected abstract void where();

    protected abstract void orderBcy();

    protected abstract void groupBy();

    protected abstract void limit();

    protected abstract void addRow();


    @Override
    public T doInConnection(Connection connection) {
        return null;
    }
}
