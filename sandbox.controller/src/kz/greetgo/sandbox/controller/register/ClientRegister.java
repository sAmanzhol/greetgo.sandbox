package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;


import java.util.Collection;

public interface ClientRegister {

    Collection<ClientRecord> clientList();

    Collection<ClientRecord> clientFilter(ClientFilter clientFilter);

    ClientFilter clientFilterSet();

    Collection<Charm> clientCharm();

    ClientRecord clientDetailsSave(ClientDetails clientDetails);

    ClientDetails clientDetailsSet(ClientRecord clientMark);

    ClientDetails clientDetailsDelete(ClientRecord clientMark);


//    Collection<Client> getUserInfo();
//
//    Collection<Client> getFilter(String firstname, String lastname, String patronymic);
//
//    Collection<Client> getUserSort(String sort);
//
//    Double pagination();
//
//    Collection<Client> getPagination(Integer index);
//
//    Collection<Client> addUserInfo(Client client);
//
//    Collection<Client> deleteUserInfo(Integer id);
//
//    Collection<Client> getClientForEdit(Integer id);
//
//    Collection<Client> editUserInfo(Client edit);
//
//    List<String> getCharacter();
//
//	Collection<ClientRecord> clientFilter(ClientFilter clientFilter);
//
//
//
//    ClientFilter clientListSet();
//
//    ClientRecord clientDetailsSave(ClientDetails clientDetails);
}
