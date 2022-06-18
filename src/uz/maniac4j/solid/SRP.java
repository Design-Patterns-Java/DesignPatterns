package uz.maniac4j.solid;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

//SRP - Single Responsibility Principle - Yagona javobgarlik prinsipi
/** Sinfni o'zgartirish uchun faqat bitta sabab bo'lishi kerak, ya'ni sinfda faqat bitta (bir xil turdagi) vazifa bo'lishi kerak. **/
public class SRP {
    public static void main(String[] args) {
        Notepad notepad=new Notepad();
        notepad.addNote("Hello world! SRP");
        notepad.addNote("Hello Design Patterns! SRP");
//        notepad.saveToFile("hello.txt");
        Saver saver=new Saver();
        saver.saveToFile(notepad,"hello.txt");
    }
}

//Domain model
class Notepad{
    private final List<String> notes=new ArrayList<>();
    private static int count=0;

    public void addNote(String text){
        notes.add((count++)+" : "+text+"\n");
    }

    public void removeNote(int index){
        notes.remove(index);
    }

    @Override
    public String toString() {
        String result="";
        for (String note:notes
             ) {
            result+=note;
        }
        return result;
    }

    //SRP ga zid
    public void saveToFile(String filename){
        try (PrintStream p=new PrintStream(filename)){
            p.println(toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}

//Persistence model
class Saver{
    public void saveToFile(Notepad notepad, String filename){
        try (PrintStream p=new PrintStream(filename)){
            p.println(notepad.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


