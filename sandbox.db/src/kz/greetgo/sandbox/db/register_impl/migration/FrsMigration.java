package kz.greetgo.sandbox.db.register_impl.migration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FrsMigration {


    public static void main(String[] args) {
        int i =0;

        BufferedReader objReader = null;

        try {

            String strCurrentLine;

            objReader = new BufferedReader(new FileReader("D:\\git Repositories\\greetgo.sandbox-1\\sandbox.db\\src_resources\\out_source_file\\from_frs_2018-02-21-154543-1-30009json.txt"));

            while ((strCurrentLine = objReader.readLine()) != null) {

//                System.out.println(strCurrentLine);

                ObjectMapper mapper = new ObjectMapper();

                User user = mapper.readValue(strCurrentLine, User.class);


                System.err.println("NEW LINE: " + i++);
                System.err.println("ID: "+user.client_id);
                System.err.println("REGISTERED_AT: "+user.registered_at);
                System.err.println("FINISHED_AT: "+user.finished_at);
                System.err.println("ACCOUNT_NUMBER: "+user.account_number);
                System.err.println("MONEY: "+user.money);
                System.err.println("TRANSACTION_TYPE: "+user.transaction_type);
                System.err.println("TYPE: "+user.type);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (objReader != null)

                    objReader.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

}

class User {
    public String finished_at;
    public String registered_at;
    public String account_number;
    public String money;
    public String type;
    public String transaction_type;
    public String client_id;
    /*money
account_number
finished_at
type
transaction_type
registered_at
client_id
*/

    @Override
    public String toString() {
        return "User{" +
                "finished_at='" + finished_at + '\'' +
                ", account_number='" + account_number + '\'' +
                ", money='" + money + '\'' +
                ", type='" + type + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", registered_at='" + registered_at + '\'' +
                ", client_id='" + client_id + '\'' +
                '}';
    }
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