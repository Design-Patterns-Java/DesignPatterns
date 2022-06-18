package uz.maniac4j.solid;

import java.util.List;
import java.util.stream.Collectors;

//OCP - Open-Closed principle
/** Dasturiy ta’minot obyektlari (sinflar, funksiyalar, metodlar, modullar va boshqalar) kengaytirish uchun ochiq, o’zgartirish uchun yopiq bo’lishi kerak. **/

//1. Bitta xususiyat bo'yicha filter
//2. 2 ta xususiyat bo'yicha filter

public class OCP {
    public static void main(String[] args) {
        Thing white_glasses = new Thing("White Glasses", Color.WHITE, Size.SMALL);
        Thing black_pen = new Thing("Black pen", Color.BLACK, Size.SMALL);
        Thing car = new Thing("Car", Color.BLACK, Size.MEDIUM);

        List<Thing> things = List.of(white_glasses, black_pen, car);
        WorseFilter worseFilter=new WorseFilter();
        System.out.println(worseFilter.filterByColor(things,Color.WHITE));
        System.out.println(worseFilter.filterBySize(things,Size.SMALL));
        System.out.println(worseFilter.filterByColorAndSize(things,Color.BLACK,Size.MEDIUM));

        System.out.println("\nOCPFilter");
        OCPFilter ocpFilter=new OCPFilter();
        System.out.println(ocpFilter.filter(things,new ColorSpecification(Color.WHITE)));
        System.out.println(ocpFilter.filter(things,new SizeSpecification(Size.SMALL)));
        System.out.println(ocpFilter.filter(things,new TwoSpecification(
                new ColorSpecification(Color.BLACK),
                new SizeSpecification(Size.MEDIUM)
        )));

    }
}

enum Color{
    WHITE,BLACK,RED
}

enum Size{
    SMALL,MEDIUM,LARGE
}

class Thing{
    public String name;
    public Color color;
    public Size size;

    public Thing(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", size=" + size +
                '}';
    }
}

//Worse filter
class WorseFilter{
    //Rang bo'yicha filter
    public List<Thing> filterByColor(List<Thing> things,Color color){
        return things.stream().filter(thing->thing.color==color).collect(Collectors.toList());
    }

    //O'lcham bo'yicha filter
    public List<Thing> filterBySize(List<Thing> things,Size size){
        return things.stream().filter(thing->thing.size==size).collect(Collectors.toList());
    }

    //Ikkita xususiyat bo'yicha filter
    public List<Thing> filterByColorAndSize(List<Thing> things,Color color,Size size){
        return things.stream().filter(thing->thing.color==color&&thing.size==size).collect(Collectors.toList());
    }

    //2 ta xususiyat uchun 3 ta metod, 9 qator     (2^n-1)
    //3 ta xususiyat uchun 7 ta metod, 7*3 qator
    //4 ta xususiyat uchun 15 ta metod, 3*15 qator
    //2 ta xususiyat uchun 31 ta metod, 3*31 qator
    //30 ta xususiyat uchun 2^30-1 ta metod, 3(2^30-1) qator
}





//Mavhum xususiyat
interface Specification<T>{
    //Mavhum obyekt mavhum xususiyatga egami?
    boolean isSatisfied(T item);
}

//Mavhum filter
interface Filter<T>{
    //Mavhum obyektlardan mavhum xususiyatga ega bo'lganlarini qaytaradigan metod
    List<T> filter(List<T> items,Specification<T> specification);
}


//Rang xususiyati
class ColorSpecification implements Specification<Thing>{
    //Rang qiymati
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    //Berilgan obyekt shu rang xususiyatiga egami?
    @Override
    public boolean isSatisfied(Thing item) {
        return item.color==color;
    }
}

//O'lcham xususiyati
class SizeSpecification implements Specification<Thing>{
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    //Berilgan obyekt shu o'lcham xususiyatiga egami?
    @Override
    public boolean isSatisfied(Thing item) {
        return item.size==size;
    }
}


//Ikki xususiyat birlashmasi
class TwoSpecification implements Specification<Thing>{

    //Ikkita berilgan xususiyatlar
    private Specification<Thing> first,second;

    public TwoSpecification(Specification<Thing> first, Specification<Thing> second) {
        this.first = first;
        this.second = second;
    }


    //Berilgan obyekt ikkala xususiyatni qanoatlantiradimi?
    @Override
    public boolean isSatisfied(Thing item) {
        return first.isSatisfied(item)&&second.isSatisfied(item);
    }
}



//OCPFilter
class OCPFilter implements Filter<Thing>{

    //Berilgan obyektlar ichidan berilgan xususiyatlarni qanoatlantiradiganlarini qaytaradi
    @Override
    public List<Thing> filter(List<Thing> items, Specification<Thing> specification) {
        return items.stream().filter(item->specification.isSatisfied(item)).collect(Collectors.toList());
    }

    //30 ta xususiyat uchun tahminan 1000-1500 qator kod

}


