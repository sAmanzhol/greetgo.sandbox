package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.db.register_impl.migration.AbstractParse;
import kz.greetgo.sandbox.db.register_impl.migration.CiaMigration;
import kz.greetgo.sandbox.db.register_impl.migration.FrsMigration;

import java.io.File;
import java.sql.Connection;

@Bean
public class MigrationRegisterImpl implements MigrationRegister {

	public BeanGetter<Jdbc> jdbc;

	private AbstractParse migration = null;

	@Override
	public void getMigration(String type) {

		File inFile = new File(type);
		Integer batchSize = 100;

		jdbc.get().execute(new ConnectionCallback<Void>() {

			@Override
			public Void doInConnection(Connection connection) throws Exception {

				switch (type) {
					case "D:\\git Repositories\\greetgo.sandbox-1\\sandbox.db\\src_resources\\out_source_file\\from_frs_2018-02-21-154543-1-30009json.txt":
						migration = new FrsMigration(connection, inFile, batchSize /*oufile*/);
						break;
					case "D:\\git Repositories\\greetgo.sandbox-1\\sandbox.db\\src_resources\\out_source_file\\from_cia_2018-02-21-154532-1-300.xml":
						migration = new CiaMigration(connection, inFile, batchSize/*oufile*/);

				}
				migration.migrate();

				return null;
			}
		});

	}
}
