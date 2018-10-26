package kz.greetgo.learn.migration.__prepare__.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionInRecord {
    public String account_number;
    public String transaction_type;
    public Double money;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "<dd-MM-yyyy hh:mm>")
    public Date registered_at;

    public Date finished_at;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"type\" : \"transaction\"");
        if(account_number != null) builder.append(",\"account_number\":\"" + account_number + "\"");
        if(money != null) builder.append(",\"money\":\"" + money + "\"");
        if(transaction_type != null) builder.append(",\"transaction_type\":\"" + transaction_type + "\"");
        if(finished_at != null)  builder.append(",\"finished_at\":\"" + sdf.format(finished_at) +'\"');
        builder.append('}');

        return builder.toString();
    }
}
