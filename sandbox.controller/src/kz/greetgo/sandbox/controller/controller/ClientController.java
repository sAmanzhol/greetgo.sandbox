package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.CharmRegister;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.ArrayList;
import java.util.List;

@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {
    public BeanGetter<ClientRegister> clientRegister;

    public BeanGetter<CharmRegister> charmRegister;

    @ToJson
    @OnGet("/getAll")
    public List<Client> getAll(@Par("nameFilter") String nameFilter,
                               @Par("surnameFilter") String surnameFilter,
                               @Par("patronymicFilter") String patronymicFilter,
                               @Par("offset") Integer offset,
                               @Par("rowSize") Integer limit,
                               @Par("sortCol") String colName,
                               @Par("order") Integer order
    ) {
        List<String> fio = new ArrayList<>();
        if (nameFilter != null) {
            fio.add(nameFilter);
        }
        if (surnameFilter != null) {
            fio.add(surnameFilter);
        }
        if (patronymicFilter != null) {
            fio.add(patronymicFilter);
        }
        if(colName != null && colName.equalsIgnoreCase("age"))
            colName = "birth_date";

        if (colName == null || colName.equalsIgnoreCase("fio")) {
            colName = "surname";
        }
        if (offset != null)
            return clientRegister.get().getListByParam(fio, limit, offset, colName, order);

        return null;
    }

    @ToJson
    @OnGet("/getCount")
    public Long getCount() {
        return clientRegister.get().getCount();
    }

    @ToJson
    @OnGet("/getById")
    public Client getById(@Par("id") Long id){
        return clientRegister.get().getById(id);
    }

    @ToJson
    @OnGet("/getCharms")
    public List<Charm> getCharms() {
        return charmRegister.get().list();
    }

    @OnPost("/update")
    public void update(@Par("client") @Json Client client){ clientRegister.get().update(client);}

    @OnPost("/insert")
    public void insert(@Par("client") @Json Client client){
        clientRegister.get().insert(client);
    }

    @OnPost("/delete")
    public void delete(@Par("id") Long id) {
        clientRegister.get().delete(id);
    }
}
