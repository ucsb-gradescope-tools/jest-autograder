package edu.ucsb.gradescope.example;

import org.junit.Test;

public class AnimalConstructorTest {
    @Test
    public void testDefaultConstructor() {
        Animal animal = new Animal();
        assert(animal.getName().equals("Ellie"));
        assert(animal.getAge() == 12);
        assert(animal.getWeight() == 15);
    }

    @Test
    public void testConstructorWithParams() {
        Animal animal = new Animal("Elewardo", 5, 6);
        assert(animal.getName().equals("Elewardo"));
        assert(animal.getAge() == 5);
        assert(animal.getWeight() == 6);
    }

    @Test
    public void testAdd() {
        assert(Animal.add(1, 2) == 3);
    }

}
