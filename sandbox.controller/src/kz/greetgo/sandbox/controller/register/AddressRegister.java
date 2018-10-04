package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Address;

import java.util.List;

public interface AddressRegister {
    List<Address> list();

    Long insert(Address address);

    Address getById(Long id);

    List<Address> getByClientId(Long client_id);

    Address update(Address address);

    void delete(Long id);

    void deleteByClientId(Long client_id);
}
