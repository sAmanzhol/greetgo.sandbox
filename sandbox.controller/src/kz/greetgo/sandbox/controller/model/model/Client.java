package kz.greetgo.sandbox.controller.model.model;

import java.util.Date;

public class Client {
	public int id;
	public String firstname;
	public String lastname;
	public String patronymic;
	public GenderType gender;
	public Date birthDate;
	public int charm;

	@Override
	public String toString() {

		return "Client{" +
			"id=" + id +
			", firstname='" + firstname + '\'' +
			", lastname='" + lastname + '\'' +
			", patronymic='" + patronymic + '\'' +
			", gender=" + gender +
			", birthDate=" + birthDate +
			", charm=" + charm +
			'}';
	}
}
