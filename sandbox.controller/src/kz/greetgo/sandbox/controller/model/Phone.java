package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.enums.PhoneType;

public class Phone {
    public Long id;

    public Long clientId;

    public String number;

    public PhoneType type;

    public Phone(Long id, Long client_id, String number, Object type) {
        this.id = id;
        this.clientId = client_id;
        this.number = number;
        this.type = PhoneType.valueOf(type.toString());
    }

    public Phone(Long id, Long client_id, String number, PhoneType type) {
        this.id = id;
        this.clientId = client_id;
        this.number = number;
        this.type = type;
    }


}
