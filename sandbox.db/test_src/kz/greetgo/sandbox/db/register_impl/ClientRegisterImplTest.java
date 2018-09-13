package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;


public class ClientRegisterImplTest extends ParentTestNg {
	public BeanGetter<ClientRegister> clientRegister;
	@Test
	public void calculator(){
	int a=5,b=6;
		int c =clientRegister.get().cacl(a,b);

		assertThat(c).isEqualTo(a+b);
	}
}