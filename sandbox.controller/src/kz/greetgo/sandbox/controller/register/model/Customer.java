package kz.greetgo.sandbox.controller.register.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedReader;

public class Customer {
	public String mProduct;
	public String mSurname;
	public String mSurname2;
	//		через интегер
//	private int mAmount;


	@Override
	public String toString() {
		return "Customer{" +
			"mProduct='" + mProduct + '\'' +
			", mSurname='" + mSurname + '\'' +
			", mSurname2='" + mSurname2 + '\'' +
			'}';
	}

	public Customer() { }



	public String getmSurname() {
		return mSurname;
	}

	public String getmProduct() {
		return mProduct;
	}

	public String getmSurname2(){ return mSurname2; }

//		через интегер
//	public int getAmount() {
//		return mAmount;
//	}
}



