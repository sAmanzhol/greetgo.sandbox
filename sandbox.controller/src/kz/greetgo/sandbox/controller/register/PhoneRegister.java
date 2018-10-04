package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Phone;

import java.util.List;

public interface PhoneRegister {

    List<Phone> list();

    Long insert(Phone phone);

    Phone update(Phone phone);

    Phone getById(Long id);

    List<Phone> getAllByClientId(Long client_id);

    void delete(Long id);

    void deleteByClientId(Long client_id);


}
