package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.model.*;

import java.util.List;

public interface ClientRegister {

    List<ClientRecord> getClientList(ClientFilter clientFilter);

    Integer getClientTotalRecord(ClientFilter clientFilter);

    List<Charm> getCharm();

    ClientRecord saveClient(ClientToSave clientDetails);

    ClientDetails getClientDetails(Integer clientId);

    void deleteClient(Integer clientId);

    Charm getCharmById(Integer charmId);
}
