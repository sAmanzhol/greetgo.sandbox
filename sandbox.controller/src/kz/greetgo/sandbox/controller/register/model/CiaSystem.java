package kz.greetgo.sandbox.controller.register.model;

public class CiaSystem {
	public String id;
	public String firstname;
	public String lastname;
	public String patronymic;
	public String birth_date;
	public String gender;
	public String charm;


	@Override
	public String toString() {

		return "CiaSystem{" +
			"id='" + id + '\'' +
			", firstname='" + firstname + '\'' +
			", lastname='" + lastname + '\'' +
			", patronymic='" + patronymic + '\'' +
			", birth_date=" + birth_date +
			", gender='" + gender + '\'' +
			", charm='" + charm + '\'' +
			'}';
	}
}
