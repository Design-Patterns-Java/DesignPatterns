package uz.maniac4j.solid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//DIP - Dependency inversion principle
public class DIP {
    public static void main(String[] args) {
        Person parent = new Person("Ali");
        Person child1 = new Person("Vali");
        Person child2 = new Person("G'ani");

        // low-level module
        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships,"Ali");
    }
}

enum Relationship {
    PARENT,
    CHILD
}

class Person {
    public String name;
    // dob etc.


    public Person(String name) {
        this.name = name;
    }
}
class Test<A,B,C> {
    private A a;
    private B b;
    private C c;

    public Test(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }
}

interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

class Relationships implements RelationshipBrowser {
    public List<Person> findAllChildrenOf(String name) {

        return relations.stream()
                .filter(x -> Objects.equals(x.getA().name, name)
                        && x.getB() == Relationship.PARENT)
                .map(Test::getC)
                .collect(Collectors.toList());
    }

    // Test class requires javatuples
    private List<Test<Person, Relationship, Person>> relations =
            new ArrayList<>();

    public List<Test<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Test<>(parent, Relationship.PARENT, child));
        relations.add(new Test<>(child, Relationship.CHILD, parent));
    }
}

class Research
{
    public Research(Relationships relationships,String name)
    {
        // high-level: find all of john's children
        List<Test<Person, Relationship, Person>> relations = relationships.getRelations();
        relations.stream()
                .filter(x -> x.getA().name.equals(name)
                        && x.getB() == Relationship.PARENT)
                .forEach(ch -> System.out.println(name+" has a child called " + ch.getC().name));
    }

    public Research(RelationshipBrowser browser,String name)
    {
        List<Person> children = browser.findAllChildrenOf(name);
        for (Person child : children)
            System.out.println(name+" has a child called " + child.name);
    }
}



