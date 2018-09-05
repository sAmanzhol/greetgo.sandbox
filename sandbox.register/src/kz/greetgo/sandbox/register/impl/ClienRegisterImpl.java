package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msultanova on 9/4/18.
 */
@Bean
public class ClienRegisterImpl implements ClientRegister {

    @Override
    public List<ClientRecord> getClientList() {

        List<ClientRecord> clientRecords = new ArrayList<>();


        clientRecords.add(new ClientRecord("Aida Aidova", "melancholic", 14, 10, 0, 11));
        clientRecords.add(new ClientRecord("Almas Adam", "melancholic", 100, 180, 15, 350));

        return clientRecords;
    }
}


