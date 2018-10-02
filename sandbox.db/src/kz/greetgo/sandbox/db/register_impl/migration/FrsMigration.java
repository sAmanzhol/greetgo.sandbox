package kz.greetgo.sandbox.db.register_impl.migration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FrsMigration {

	public static void main(String[] args) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		User user = mapper.readValue(new File("/home/nazar/IdeaProjects/greetgo.sandbox-1/sandbox.db/src_resources/out_source_file/from_frs_2018-02-21-154543-1-30009.json"), User.class);

		System.err.println(user.finished_at);

	}

}
class User{
public String finished_at;
public String account_number;
public String money;
public String type;
public String transaction_type;
}
class Car {

	private String color;
	private String type;

	// standard getters setters
}

class UserSimple {

	String name;

	String email;

	int age;

	boolean isDeveloper;
}