package uz.maniac4j.solid;

//LSP - Liskov almashtirish prinsipi
/** Ota sinf (Super class) ni kodni buzmasdan turib bola sinf (Sub class) bilan almashtirish imkoniyati mavjud bo’lishi kerak. **/
public class LSP {
    public static void main(String[] args) {
        FlyingBird eagle=new Eagle();
        eagle.walk();
        eagle.fly();

        Bird penguin=new Penguin();
        penguin.walk();
    }
}



/**LSP zid**/

//class Event{
//    public Event(String str) {
//        System.out.println(str);
//    }
//}
//
//interface Bird{
//    Event walk();
//    Event fly();
//}
//
//class Eagle implements Bird{
//
//    @Override
//    public Event walk() {
//        return new Event("Eagle walking");
//    }
//
//    @Override
//    public Event fly() {
//        return new Event("Eagle flying");
//    }
//}
//
//class Penguin implements Bird{
//    @Override
//    public Event walk() {
//        return new Event("Penuin walking");
//    }
//
//    //Muammo
//    @Override
//    public Event fly() {
//        throw new UnsupportedOperationException("Penguin can't fly");
//    }
//}


/**LSP ga mos**/
class Event{
    public Event(String str) {
        System.out.println(str);
    }
}

interface Bird{
    Event walk();
}

interface FlyingBird extends Bird{
    Event fly();
}

class Eagle implements FlyingBird{

    @Override
    public Event walk() {
        return new Event("Eagle walking");
    }

    @Override
    public Event fly() {
        return new Event("Eagle flying");
    }
}

class Penguin implements Bird{
    @Override
    public Event walk() {
        return new Event("Penguin walking");
    }
}