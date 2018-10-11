package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;

import java.util.List;

public interface ClientRegister {


    Client getById (Long id);

    Client update(Client charm);

    Long insert(Client client);

    void insertBatch(List<Client> clientList);

    List<Client> getListByParam(List<String> filters,Integer limit,Integer offset,String sortCol,Integer order);

    Long getCount();

    void delete(Long id);
}
