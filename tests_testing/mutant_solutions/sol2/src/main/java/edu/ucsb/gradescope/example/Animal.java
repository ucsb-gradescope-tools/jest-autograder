package edu.ucsb.gradescope.example;

public class Animal {
    private String name;
    private int age;
    private double weight;

    public Animal() {
        this.name = "Elephantoronous";
        this.age = 12;
        this.weight = 15;
    }

    public Animal(String name, int age, double weight) throws InvalidAnimalParameterException{
        if (age < 0 || weight < 0) {
            throw new InvalidAnimalParameterException();
        }
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static double add(double a, double b) {
        return a+b;
    }

    public static double sub(double a, double b) {
        return a-b;
    }
}
