package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientRecordListWrapper;
import kz.greetgo.sandbox.controller.model.ClientToSave;

/**
 * Created by msultanova on 9/5/18.
 */
public interface ClientRegister {
    ClientDetail getClientDetailById(int id);
    ClientRecord saveClient(ClientToSave toSave);
    void deleteClient(int id);
    ClientRecordListWrapper filterClients(ClientFilter clientFilter);

}
