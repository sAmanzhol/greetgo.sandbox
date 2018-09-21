package kz.greetgo.sandbox.controller.model.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientToSave {

		public Integer id;
		public String firstname;
		public String lastname;
		public String patronymic;
		public GenderType gender;
		public Date dateOfBirth;
		public int characterId;
		public ClientAddr addressOfResidence = new ClientAddr();
		public ClientAddr addressOfRegistration = new ClientAddr();
		public List<ClientPhone> phone=new ArrayList<>();

	@Override
	public String toString() {

		return "ClientToSave{" +
			"id=" + id +
			", firstname='" + firstname + '\'' +
			", lastname='" + lastname + '\'' +
			", patronymic='" + patronymic + '\'' +
			", gender=" + gender +
			", dateOfBirth=" + dateOfBirth +
			", characterId=" + characterId +
			", addressOfResidence=" + addressOfResidence +
			", addressOfRegistration=" + addressOfRegistration +
			", phone=" + phone +
			'}';
	}
}
