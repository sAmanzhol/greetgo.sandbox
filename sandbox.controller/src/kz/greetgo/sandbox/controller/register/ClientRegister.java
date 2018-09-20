package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;

import java.util.List;

/**
 * Created by msultanova on 9/5/18.
 */
public interface ClientRegister {

    List<ClientRecord> getClientList();
    ClientDetail getClientDetailById(long id);
    ClientRecord saveClient(ClientToSave toSave);
    void deleteClient(long id);
    ClientRecordListWrapper filterClients(ClientFilter clientFilter);

//    void test();


}
