package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.register.model.Customer;
import kz.greetgo.util.RND;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

@Bean
public class CustomerDb implements HasAfterInject {
	public ArrayList<Customer> customers = new ArrayList<>();
	@Override
	public void afterInject() throws Exception {
		/*try (BufferedReader bufferedReader = new BufferedReader(new FileReader("customer.txt"));) {


			String currentLine;
			while ((currentLine = bufferedReader.readLine()) != null) {
				customers.add(new Customer(currentLine));
			}
			bufferedReader.close();

		}*/

		for(int i=0;i<100; i++){
			Customer c = new Customer();
			c.mProduct = RND.str(5);
			c.mSurname = RND.str(8);
			c.mSurname2= RND.str(5);
			customers.add(c);
		}


	}



}

