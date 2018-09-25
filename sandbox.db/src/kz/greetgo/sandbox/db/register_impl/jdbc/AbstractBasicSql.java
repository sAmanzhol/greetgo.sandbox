package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.sandbox.controller.model.model.ClientFilter;

public abstract class AbstractBasicSql<T,R> extends AbstractConnectionCallBack<T,R>{
protected ClientFilter clientFilter;
public AbstractBasicSql(ClientFilter clientFilter){
			this.clientFilter =clientFilter;
		}

	@Override
	public void where() {
	if (true);

	}
}
