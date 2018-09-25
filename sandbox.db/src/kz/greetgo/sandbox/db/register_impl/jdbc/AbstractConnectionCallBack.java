package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.db.ConnectionCallback;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConnectionCallBack<T,R> implements ConnectionCallback<R> {
	protected List<Object> param = new ArrayList<>();
	protected SQL sql = new SQL();
	protected final void prepareSql() {
		select();
	}

	protected abstract void select();

	public void innerJoin(){}
	public void leftJoin(){}
	public void where(){}
	public void groupBy(){}
	public void orderBy(){}
	public void limit(){}
	public void offset(){}
	public void addRow(T t){}

	public R getResult(){return null;}

	public R doInConnection(kz.greetgo.db.ConnectionCallback connectionCallback) {

		return null;
	}
}
