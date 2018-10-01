package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.db.register_impl.migration.CiaMigration;

import java.sql.Connection;

@Bean
public class CiaMigrationRegisterImpl implements MigrationRegister {

	public BeanGetter<Jdbc> jdbc;

	@Override
	public void getMirgtaion()  {

		/*Connection connection=null;
		jdbc.get().execute(new CiaMigration(connection));*/

		jdbc.get().execute(new ConnectionCallback<Void>() {

			@Override
			public Void doInConnection(Connection connection) throws Exception {
				CiaMigration migration = new CiaMigration(connection/*, inFile, batchSize, outFile*/);
				migration.doInConnection(connection);
				return null;
			}
		});
	}
}


