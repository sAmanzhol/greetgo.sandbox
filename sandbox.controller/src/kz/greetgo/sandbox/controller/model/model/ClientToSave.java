package kz.greetgo.sandbox.controller.model.model;

import java.util.ArrayList;
import java.util.List;

public class ClientToSave {

		public Integer id;
		public String firstname;
		public String lastname;
		public String patronymic;
		public GenderType gender;
		public String dateOfBirth;
		public int characterId;
		public ClientAddr addressOfResidence;
		public ClientAddr addressOfRegistration;
		public List<ClientPhone> phone=new ArrayList<>();

	}
