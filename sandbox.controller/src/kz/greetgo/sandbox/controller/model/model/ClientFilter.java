package kz.greetgo.sandbox.controller.model.model;



public class ClientFilter {
    public String firstname;
    public String lastname;
    public String patronymic;
    public String orderBy;
    public boolean sort;
    public int page;
    public int pageTotal;
    public int recordSize;
    public int recordTotal;

    public ClientFilter() {
        this.firstname = "";
        this.lastname = "";
        this.patronymic = "";
        this.orderBy = "";
        this.sort = true;
        this.page = 0;
        this.pageTotal = 0;
        this.recordSize = 10;
        this.recordTotal =0;
    }
}
