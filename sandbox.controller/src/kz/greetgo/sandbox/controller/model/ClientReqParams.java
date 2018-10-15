package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.enums.Sort;

public class ClientReqParams {
    public String nameFilterVal;
    public String surnameFilterVal;
    public String patronymicFilterVal;
    public Long   offset;
    public Long   limit;
    public String colName;
    public Sort order;

    public ClientReqParams() {
    }

    @Override
    public String toString() {
        return "ClientReqParams{" +
                "nameFilterVal='" + nameFilterVal + '\'' +
                ", surnameFilterVal='" + surnameFilterVal + '\'' +
                ", patronymicFilterVal='" + patronymicFilterVal + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", colName='" + colName + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
