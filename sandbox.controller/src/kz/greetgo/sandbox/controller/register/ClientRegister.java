package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.model.Client;

import java.util.Collection;
import java.util.List;

public interface ClientRegister {

    Collection<Client> getUserInfo();

    Collection<Client> getFilter(String firstname, String lastname, String patronymic);

    Collection<Client> getUserSort(String sort);

    Double pagination();

    Collection<Client> getPagination(Integer index);

    Collection<Client> addUserInfo(Client client);

    Collection<Client> deleteUserInfo(Integer id);

    Collection<Client> getClientForEdit(Integer id);

    Collection<Client> editUserInfo(Client edit);

    List<String> getCharacter();

	Collection<ClientRecord> clientFilter(ClientFilter clientFilter);

	Collection<ClientRecord> clientList();

    ClientFilter clientListSet();

    ClientRecord clientDetailsSave(ClientDetails clientDetails);
}
