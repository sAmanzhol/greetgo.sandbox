package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.ArrayList;
import java.util.List;

@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {
    public BeanGetter<ClientRegister> clientRegister;

    @ToJson
    @OnGet("/getAll")
    public List<Client> getAll(@Par("nameFilter") String nameFilter,
                         @Par("surnameFilter") String surnameFilter,
                         @Par("patronymicFilter") String patronymicFilter,
                         @Par("page") Integer page,
                         @Par("rowSize") Integer limit,
                         @Par("sortCol") String colName,
                         @Par("order") String order
    )
    {
        List<String> fio = new ArrayList<>();
        if(nameFilter != null)
        {
            fio.add(nameFilter);
        }
        if(surnameFilter != null)
        {
            fio.add(surnameFilter);
        }
        if(patronymicFilter != null)
        {
            fio.add(patronymicFilter);
        }
        if(page != null)
            return clientRegister.get().getListByParam(fio,limit,limit * page,colName,order);

        return null;
    }

    @ToJson
    @OnGet("/getCount")
    public Long getCount()
    {
      return clientRegister.get().getCount();
    }


}
