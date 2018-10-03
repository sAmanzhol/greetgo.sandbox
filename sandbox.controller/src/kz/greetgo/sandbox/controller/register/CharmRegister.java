package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;

import java.util.List;

public interface CharmRegister {

    Charm getById(Long id);

    Long insert(Charm charm);

    List<Charm> list();

}
