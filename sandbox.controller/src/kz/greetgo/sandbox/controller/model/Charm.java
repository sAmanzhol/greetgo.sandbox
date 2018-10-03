package kz.greetgo.sandbox.controller.model;

public class Charm {
    public Long id;
    public String name;
    public String description;
    public Double energy;


    public Charm(Long id, String name, String description, Double energy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.energy = energy;
    }
}
