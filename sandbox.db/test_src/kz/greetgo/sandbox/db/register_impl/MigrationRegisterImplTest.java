package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.testng.annotations.Test;

public class MigrationRegisterImplTest extends ParentTestNg {

	public BeanGetter<MigrationRegister> migrationRegister;

	@Test
	public void testGetMigration() {

		String xmls = "/home/nazar/IdeaProjects/greetgo.sandbox-1/sandbox.db/src_resources/out_source_file/from_cia_2018-02-21-154532-1-300.xml";
		String txts = "/home/nazar/IdeaProjects/greetgo.sandbox-1/sandbox.db/src_resources/out_source_file/from_frs_2018-02-21-154543-1-30009json.txt";

		migrationRegister.get().getMigration(xmls);
		migrationRegister.get().getMigration(txts);

	}
}