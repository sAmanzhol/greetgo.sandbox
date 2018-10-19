package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.controller.security.NoSecurity;

import java.sql.Connection;

@Bean
@Mapping("/migration")
public class MigrationController {

	public BeanGetter<MigrationRegister> migrationRegister;

	@Mapping("{type}")
	@NoSecurity
	public void getMigration(@ParPath("type") String type) throws Exception {
		Connection connection = null;
		migrationRegister.get().getMigration("/home/nazar/IdeaProjects/greetgo.sandbox-1/sandbox.db/src_resources/out_source_file/from_frs_2018-02-21-154543-1-30009json.txt");
	}
}
