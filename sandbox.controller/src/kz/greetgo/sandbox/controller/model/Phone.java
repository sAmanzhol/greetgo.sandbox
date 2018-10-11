package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.enums.PhoneType;

public class Phone {
    public Long id;
    public Long client;
    public String number;
    public PhoneType type;
    public Boolean actual;

    public Phone(Long id, Long client_id, String number, Object type, Boolean actual) {
        this.id = id;
        this.client = client_id;
        this.number = number;
        this.type = PhoneType.valueOf(type.toString());
        this.actual = actual;
    }

    public Phone(Long id, Long client_id, String number, PhoneType type, Boolean actual) {
        this.id = id;
        this.client = client_id;
        this.number = number;
        this.type = type;
        this.actual = actual;
    }

    public Phone(){}


    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", client=" + client +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", actual=" + actual +
                '}';
    }
}
