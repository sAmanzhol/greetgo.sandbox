package kz.greetgo.sandbox.controller.model;

public class Charm {
    public Long id;
    public String name;
    public String description;
    public Double energy;
    public Boolean actual;


    public Charm(Long id, String name, String description, Double energy,Boolean actual) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.actual = actual;
    }

    public Charm(){}

    @Override
    public String toString() {
        return "Charm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", energy=" + energy +
                ", actual=" + actual +
                '}';
    }
}
