package kz.greetgo.sandbox.controller.model.enums;

public enum Sort {
    ASC(1),
    DESC(0);

    private final int id;
    Sort(int id) {
        this.id = id; }
    public int getValue() { return id; }
}
