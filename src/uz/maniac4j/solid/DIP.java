package uz.maniac4j.solid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//DIP - Dependency Inversion principle
/** Yuqori darajadagi modullar past darajadagi modullarga bog'liq bo'lmasligi kerak. Ikkalasi ham abstraktsiyalarga bog'liq bo'lishi kerak.
    Abstraktsiyalar tafsilotlar (detallar) ga bog'liq bo'lmasligi kerak. Tafsilotlar abstraktsiyalarga bog'liq bo'lishi kerak. **/
public class DIP {
    public static void main(String[] args) {
        Person parent=new Person("Ali");
        Person child1=new Person("Vali");
        Person child2=new Person("G'ani");

        Relationships relationships=new Relationships();
        relationships.addParentAndChild(parent,child1);
        relationships.addParentAndChild(parent,child2);

        new Research(relationships,"Ali");
    }
}

class Person{
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

enum Relationship{
    PARENT,
    CHILD
}

//DIP zid
//class Relationships { //Low-level - Quyi daraja
//    private List<Triple<Person,Relationship,Person>> relations=new ArrayList<>();
//    public List<Triple<Person, Relationship, Person>> getRelations() {
//        return relations;
//    }
//    public void addParentAndChild(Person parent, Person child){
//        this.relations.add(new Triple<>(parent,Relationship.PARENT,child));
//        this.relations.add(new Triple<>(child,Relationship.CHILD,parent));
//    }
//}
//
//class Research { // High-level - Yuqori daraja
//    public Research(Relationships relationships,String name) {
//        List<Triple<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream().filter(x->x.getA().name.equals(name)&&x.getB()==Relationship.PARENT)
//                .forEach(ch-> System.out.println(name+" has a child called "+ch.getC().name));
//    }
//
//}


//DIP

interface RelationshipsBrowser{
    List<Person> findAllChildrenOf(String name);
}
class Relationships implements RelationshipsBrowser{ //Low-level - Quyi daraja
    private List<Triple<Person,Relationship,Person>> relations=new ArrayList<>();
    public List<Triple<Person, Relationship, Person>> getRelations() {
        return relations;
    }
    public void addParentAndChild(Person parent, Person child){
        this.relations.add(new Triple<>(parent,Relationship.PARENT,child));
        this.relations.add(new Triple<>(child,Relationship.CHILD,parent));
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream().filter(x->x.getA().name.equals(name)&&x.getB()==Relationship.PARENT)
                .map(Triple::getC)
                .collect(Collectors.toList());
    }
}

class Research { // High-level - Yuqori daraja
//    public Research(Relationships relationships,String name) {
//        List<Triple<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream().filter(x->x.getA().name.equals(name)&&x.getB()==Relationship.PARENT)
//                .forEach(ch-> System.out.println(name+" has a child called "+ch.getC().name));
//    }
    public Research(RelationshipsBrowser browser,String name) {
        List<Person> children = browser.findAllChildrenOf(name);
        for (Person child : children) {
            System.out.println(name+" has a child called "+child.name);
        }
    }
}


class Triple<A,B,C>{
    private A a;
    private B b;
    private C c;

    public Triple(A a, B b, C c) {
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