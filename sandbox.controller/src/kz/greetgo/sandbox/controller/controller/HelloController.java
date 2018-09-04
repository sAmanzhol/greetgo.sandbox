package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.sandbox.controller.register.HelloRegister;
import kz.greetgo.sandbox.controller.register.model.Customer;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.ArrayList;

@Bean
@Mapping("/hello")
public class HelloController implements Controller {

	public BeanGetter<HelloRegister> helloRegister;

	@AsIs
	@NoSecurity
	@Mapping("/greeting")
	public String greeting(@Par("accountName") String accountName) {
		return helloRegister.get().greeting(accountName);
	}


	@ToJson
	@NoSecurity
	@Mapping("/userInfo")
	public ArrayList<Customer> userInfo() { return helloRegister.get().getUserInfo();
	}

	@ToJson
	@NoSecurity
	@Mapping("/addUserInfo")
	public ArrayList<Customer> addUserInfo(@Par("mProduct") String mProduct, @Par("mSurname") String mSurname, @Par("mSurname2") String mSurname2 ) { return helloRegister.get().addUserInfo(mProduct,mSurname,mSurname2);
	}

	@ToJson
	@NoSecurity
	@Mapping("/deleteUserInfo")
	public ArrayList<Customer> deleteUserInfo(@Par("mProduct") String mProduct, @Par("mSurname") String mSurname, @Par("mSurname2") String mSurname2, @Par("index") Integer index) { return helloRegister.get().deleteUserInfo(mProduct,mSurname,mSurname2, index);
	}

	@ToJson
	@NoSecurity
	@Mapping("/editUserInfo")
	public ArrayList<Customer> editUserInfo(@Par("mProduct") String mProduct, @Par("mSurname") String mSurname, @Par("mSurname2") String mSurname2, @Par("index") Integer index) { return helloRegister.get().editUserInfo(mProduct,mSurname,mSurname2, index);
	}

	@AsIs
	@NoSecurity
	@Mapping("/pagination")
	public Double pagination() { return helloRegister.get().pagination();
	}
	@ToJson
	@NoSecurity
	@Mapping("/getPagination")
	public ArrayList<Customer> getPagination(@Par("index") Integer index) {

		return helloRegister.get().getPagination(index);
	}

	@ToJson
	@NoSecurity
	@Mapping("/getFilter")
	public ArrayList<Customer> getFilter(@Par("firstname") String firstname, @Par("lastname") String lastname, @Par("patronymic") String patronymic) {

		return helloRegister.get().getFilter(firstname, lastname, patronymic);
	}

}
