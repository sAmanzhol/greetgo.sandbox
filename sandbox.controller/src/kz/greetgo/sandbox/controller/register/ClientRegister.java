package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientReqParams;

import java.util.List;

public interface ClientRegister {


    Client getById (Long id);

    Client update(Client charm);

    Long insert(Client client);

    int[] insertBatch(List<Client> clientList);

    List<Client> getListByParam(ClientReqParams params);

    Long getCount();

    void delete(Long id);
}
