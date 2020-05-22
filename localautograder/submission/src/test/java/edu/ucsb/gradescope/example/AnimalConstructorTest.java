package edu.ucsb.gradescope.example;

import autograder.GradescopeTestClass;
import com.github.tkutcher.jgrade.gradedtest.GradedTest;
import org.junit.Test;

@GradescopeTestClass
public class AnimalConstructorTest {
    @Test
    @GradedTest(name="Default constructor - new Animal()", points=10)
    public void testDefaultConstructor() {
        Animal animal = new Animal();
        assert(animal.getName().equals("Ellie"));
        assert(animal.getAge() == 12);
        assert(animal.getWeight() == 15);
    }

    @Test
    @GradedTest(name="Constructor with arguments - new Animal(\"Elewardo\", 5, 6)", points=10)
    public void testConstructorWithParams() {
        Animal animal = new Animal("Elewardo", 5, 6);
        assert(animal.getName().equals("Elewardo"));
        assert(animal.getAge() == 5);
        assert(animal.getWeight() == 6);
    }

}
