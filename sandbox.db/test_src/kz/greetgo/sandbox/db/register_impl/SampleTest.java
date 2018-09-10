package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SampleTest extends ParentTestNg {

	public SampleRegister register = new SampleRegister();

	@Test
	public void plusTest() throws Exception {

		//
		//
		int value = register.plus(2,5);
		//
		//

		assertThat(value).isEqualTo(7);

	}

	private class SampleRegister {


		public int plus(int a, int b) {
			return a+b;
		}
	}
}
