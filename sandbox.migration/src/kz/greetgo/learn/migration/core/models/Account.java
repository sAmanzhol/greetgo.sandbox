package kz.greetgo.learn.migration.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonFactory;

import java.sql.Date;

public class Account {
    JsonFactory factory = new JsonFactory();
    public Long number;
    public String client_id;
    public String account_number;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    public Date registered_at;

}
