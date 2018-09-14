package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

	public BeanGetter<ClientRegister> clientRegister;
	public BeanGetter<ClientTestDao> clientTestDao;

	@Test
	public void calculator(){
		int a=5,b=6;
		int c =clientRegister.get().cacl(a,b);

		assertThat(c).isEqualTo(a+b);
	}

	@Test
	public void testClientCharm() {

		String accountName = clientTestDao.get().loadCharm();

		//TODO insert db count


		// TODO: 9/14/18 call register method

		// TODO: 9/14/18 check test result
		assertThat(true);
	}
}