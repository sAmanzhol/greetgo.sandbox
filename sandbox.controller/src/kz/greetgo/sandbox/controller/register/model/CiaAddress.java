package kz.greetgo.sandbox.controller.register.model;

public class CiaAddress {
	public String type;
	public String street;

	@Override
	public String toString() {

		return "CiaAddress{" +
			"type='" + type + '\'' +
			", street='" + street + '\'' +
			", house='" + house + '\'' +
			", flat='" + flat + '\'' +
			'}';
	}

	public String	house;
	public String flat;
}
