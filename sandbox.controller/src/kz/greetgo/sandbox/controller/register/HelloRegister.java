package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.register.model.Customer;

import java.util.ArrayList;

public interface HelloRegister {


	String greeting(String accountName);

	ArrayList<Customer> getUserInfo();

	ArrayList<Customer> addUserInfo(String mProduct, String mSurname, String mSurname2);

	ArrayList<Customer> deleteUserInfo(String mProduct, String mSurname, String mSurname2, Integer index);

	ArrayList<Customer> editUserInfo(String mProduct, String mSurname, String mSurname2, Integer index);

	Double pagination();

	ArrayList<Customer> getPagination(Integer index);

	ArrayList<Customer> getFilter(String firstname, String lastname, String patronymic);
}
