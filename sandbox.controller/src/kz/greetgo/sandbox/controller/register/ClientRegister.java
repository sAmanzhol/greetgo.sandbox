package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;

import java.util.List;

public interface ClientRegister {


    Client getById (Long id);

    Client update(Client charm);

    Long insert(Client client);

    void insertBatch(List<Client> clientList);

    List<Client> getByParam(Long start,Long offset);

    Long getCount();

    void delete(Long id);
}
