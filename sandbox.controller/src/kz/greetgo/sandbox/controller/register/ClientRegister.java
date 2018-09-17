package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.model.*;


import java.util.Collection;
import java.util.List;

public interface ClientRegister {

    List<ClientRecord> getClientList(ClientFilter clientFilter);

    Integer getClientTotalRecord(ClientFilter clientFilter);

    List<Charm> clientCharm();

    ClientRecord clientDetailsSave(ClientDetails clientDetails);

    ClientDetails clientDetailsSet(Integer clientMarkId);

    ClientDetails clientDetailsDelete(Integer clientMarkId);

    Charm getClientAddCharmId(Integer charmId);
}
