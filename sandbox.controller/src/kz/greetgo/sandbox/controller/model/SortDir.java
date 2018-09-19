package kz.greetgo.sandbox.controller.model;

public enum SortDir {
    ASC("asc"),
    DESC("desc");

    private final String text;

    SortDir(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}