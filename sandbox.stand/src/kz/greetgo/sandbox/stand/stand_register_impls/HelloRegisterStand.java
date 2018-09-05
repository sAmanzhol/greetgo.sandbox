package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.errors.AuthError;
import kz.greetgo.sandbox.controller.register.HelloRegister;
import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.db.stand.beans.CustomerDb;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.controller.register.model.Customer;
import kz.greetgo.sandbox.db.stand.model.PersonDot;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

@Bean
public class HelloRegisterStand implements HelloRegister {

	private final Path storageDir = new File("build/user_param_storage").toPath();

	private File getUserParamFile(UserParamName name) {
		return storageDir.resolve(name.name() + ".txt").toFile();
	}


	public BeanGetter<StandDb> db;
	public BeanGetter<CustomerDb> cdb;
	public double page = 1;
	@Override
	public String greeting(String accountName) {

		StringBuilder err = new StringBuilder();
		err.append("Use one of: ");

		for (PersonDot personDot : db.get().personStorage.values()) {
			if (!personDot.disabled) err.append(personDot.accountName).append(", ");
			if (accountName == null) continue;
			if (accountName.equals(personDot.accountName)) {
				if (personDot.disabled) throw new AuthError("Account " + accountName + " is disabled");
				return "Hello " + personDot.accountName;
			}
		}

		err.setLength(err.length() - 2);

		throw new AuthError(err.toString());
	}

//	@Override
//	public String getUserInfo() {
//		int sizes = cdb.get().customers.size();
//		Customer[] array1 = new Customer[1000];
//		for (int i=0; i<cdb.get().customers.size(); i++) {
//			array1[i] = cdb.get().customers.get(i);
//			Customer customer = cdb.get().customers.get(i);
//			System.out.println(customer.getSurname() + " | " + customer.getProduct() + " | " + customer.getmSurname2());
//
//		}
//		return String.valueOf(array1[0]);
//
//	}

	@Override
	public ArrayList<Customer> getUserInfo() {
		ArrayList<Customer> cust = new ArrayList<>();

		int sizes = cdb.get().customers.size();
		page = Math.ceil(sizes/10);
		for(int i =0;i<10; i++ ){
			cust.add(cdb.get().customers.get(i));
		}

		return cust;

	}


	@Override
	public ArrayList<Customer> addUserInfo(String mProduct, String mSurname, String mSurname2) {
		Customer c = new Customer();
		c.mProduct = mProduct;
		c.mSurname = mSurname;
		c.mSurname2= mSurname2;
		cdb.get().customers.add(c);
		return getUserInfo();
	}

	@Override
	public ArrayList<Customer> deleteUserInfo(String mProduct, String mSurname, String mSurname2, Integer index) {
		Customer c = new Customer();
//		ArrayList<Customer> customers1 = null;
		c.mProduct = mProduct;
		c.mSurname = mSurname;
		c.mSurname2= mSurname2;

		int i = index;
		if(index == 100){

			cdb.get().customers.remove(0);
		}

		else {
			cdb.get().customers.remove(i);
		}
//		customers1.add(c);
//		int i =0;
//		cdb.get().customers.remove(customers1);

//		for(Customer custom: cdb.get().customers){
//			i++;
//			if(custom.mProduct.equals(c.mProduct) || custom.mProduct.equals(c.mSurname) || custom.mProduct.equals(c.mSurname2)){
//
//				cdb.get().customers.remove(customers1);
//			}
//
//		}
		 return getUserInfo();
	}


	@Override
	public ArrayList<Customer> editUserInfo(String mProduct, String mSurname, String mSurname2, Integer index) {
		Customer c = new Customer();
//		ArrayList<Customer> customers1 = null;
		c.mProduct = mProduct;
		c.mSurname = mSurname;
		c.mSurname2= mSurname2;

		int i = index;
		if(index == 100){

			cdb.get().customers.remove(0);
			cdb.get().customers.add(c);
		}

		else {
			cdb.get().customers.remove(i);
			cdb.get().customers.add(c);

		}
		return getUserInfo();
	}


	public Double pagination(){

		int sizes = cdb.get().customers.size();
		page = Math.floor(sizes/10);
		if(sizes%10 != 0){
			++page;
		}


		return  page;
	}

	@Override
	public ArrayList<Customer> getPagination(Integer index) {



		ArrayList<Customer> cust = new ArrayList<>();


		System.out.println(index);


		if(index ==null)
		{
			page =0;
		}
		else
		page = index;

		System.out.println(page);



		for(int i =((int) page*10)+0  ;i<((int) page*10)+10; i++ ){
			if(i==cdb.get().customers.size())
				return cust;

			cust.add(cdb.get().customers.get(i));
		}

		return cust;

	}

	@Override
	public ArrayList<Customer> getFilter(String firstname, String lastname, String patronymic) {

		ArrayList<Customer> cust = new ArrayList<>();

		Customer c = new Customer();
		c.mProduct = firstname;
		c.mSurname = lastname;
		c.mSurname2= patronymic;


		int i=0;
		int i1 =0;



		for (Customer custom : cdb.get().customers) {

//			|| custom.mProduct.equals(c.mProduct) || custom.mSurname2.equals(c.mProduct)
			if (custom.mSurname.equals(c.mProduct) ) {
				i1 = i;// n+1
				System.out.println("done");
				cust.add(cdb.get().customers.get(i1));//n+1
			}
			System.out.println("not done");

			i++;
		}

		System.out.println(cust);


			return cust;


//
//
//
//		return cust;

	}

}
