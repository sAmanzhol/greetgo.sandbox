package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;


import java.util.Collection;

public interface ClientRegister {



    Collection<ClientRecord> clientFilter(ClientFilter clientFilter);

    Integer clientFilterSet();

    Collection<Charm> clientCharm();

    ClientRecord clientDetailsSave(ClientDetails clientDetails);

    ClientDetails clientDetailsSet(ClientRecord clientMark);

    ClientDetails clientDetailsDelete(ClientRecord clientMark);

}
