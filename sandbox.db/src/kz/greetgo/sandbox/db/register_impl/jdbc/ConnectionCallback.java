package kz.greetgo.sandbox.db.register_impl.jdbc;

public interface ConnectionCallback<T> {
	T doInConnection(kz.greetgo.db.ConnectionCallback connectionCallback);
}
