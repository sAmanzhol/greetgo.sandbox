package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.model.*;


import java.util.Collection;

public interface ClientRegister {



    Collection<ClientRecord> getClientList(ClientFilter clientFilter);

    Integer getClientTotalRecord(ClientFilter clientFilter);

    Collection<Charm> clientCharm();

    ClientRecord clientDetailsSave(ClientDetails clientDetails);

    ClientDetails clientDetailsSet(Integer clientMarkId);

    ClientDetails clientDetailsDelete(Integer clientMarkId);

	  Charm getClientAddCharmId(Integer charmId);
}
