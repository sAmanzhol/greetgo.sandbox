package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToSave;

import java.util.List;

/**
 * Created by msultanova on 9/5/18.
 */
public interface ClientRegister {

    List<ClientRecord> getClientList();
    ClientDetail getClienDetailById(long id);
    ClientRecord saveClient(ClientToSave toSave);
    void deleteClient(long id);

}
