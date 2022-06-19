package uz.maniac4j.solid;
//ISP - Interface Segregation Principle
/** Barcha mas’uliyatni bitta interfeysga yig’ish o’rniga, bir nechta sodda interfeyslarga ajratish kerak **/
public class ISP {
}



class Document{

}

/**ISP - zid**/
interface Machine{
    void print(Document d);
    void fax(Document d) throws Exception;
    void scan(Document d);
}

class MultiFunctionPrinter implements Machine{
    @Override
    public void print(Document d) {
        //
    }
    @Override
    public void fax(Document d) {
        //
    }
    @Override
    public void scan(Document d) {
        //
    }
}


// YAGNI - You aren't gonna need it
class OldPrinter implements Machine{

    @Override
    public void print(Document d) {
        //
    }


    //Muammo
    @Override
    public void fax(Document d) throws Exception {
        throw new Exception();
    }

    @Override
    public void scan(Document d) {
        //
    }
}



/** ISP **/
interface Printer{
    void print(Document d);
}

interface Scanner{
    void scan(Document d);
}

interface Fax{
    void fax(Document d);
}


class JustPrinter implements Printer{
    @Override
    public void print(Document d) {

    }
}

interface PrinterAndScanner extends Printer,Scanner{
}

class NewPrinter implements PrinterAndScanner{
    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}