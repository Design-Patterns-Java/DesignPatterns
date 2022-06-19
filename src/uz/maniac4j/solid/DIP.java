package uz.maniac4j.solid;

import org.javatuples.Triplet;

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

interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

class Relationships implements RelationshipBrowser {
    public List<Person> findAllChildrenOf(String name) {

        return relations.stream()
                .filter(x -> Objects.equals(x.getValue0().name, name)
                        && x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }

    // Triplet class requires javatuples
    private List<Triplet<Person, Relationship, Person>> relations =
            new ArrayList<>();

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }
}

class Research
{
    public Research(Relationships relationships,String name)
    {
        // high-level: find all of john's children
        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
        relations.stream()
                .filter(x -> x.getValue0().name.equals(name)
                        && x.getValue1() == Relationship.PARENT)
                .forEach(ch -> System.out.println(name+" has a child called " + ch.getValue2().name));
    }

    public Research(RelationshipBrowser browser,String name)
    {
        List<Person> children = browser.findAllChildrenOf(name);
        for (Person child : children)
            System.out.println(name+" has a child called " + child.name);
    }
}
