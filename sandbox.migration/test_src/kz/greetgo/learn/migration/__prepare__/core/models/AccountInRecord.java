package kz.greetgo.learn.migration.__prepare__.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountInRecord {

    public String account_number;
    public String client_id;
    public Date registered_at;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");


    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("\"type\" : \"new_account\"");
        if(account_number != null)builder.append(",\"account_number\":\"" + account_number + "\"");
        if(client_id != null)     builder.append(",\"client_id\" : \"" + client_id + "\"");
        if(registered_at != null)     builder.append(",\"registered_at\":\"" + sdf.format(registered_at) + '\"');
        builder.append('}');

        return builder.toString();
    }
}
