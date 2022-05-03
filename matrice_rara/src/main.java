//În fișierul date\s01_matricerara.txt este salvată o matrice rară. Pe fiecare linie sunt informații despre
//        un element, astfel: index linie (int), index coloană (int) și valoare element (double). Valorile sunt
//        despărțite prin virgulă. Cei doi indecși arată poziția elementului în matrice.
//        Exemplu:
//        0,1,30
//        0,3,50
//        1,1,100
//        2,3,200
//        4,4,-10
//        5,7,-9
//        Să se scrie o aplicație care să îndeplinească următoarele cerințe:
//        1) Să se construiască o clasă Element care să permită stocarea informațiilor despre un element
//        conform structurii de mai sus. Clasa va avea implementate:
//        - constructori (de inițializare și default), metode de acces, toString();
//        - testare egalitate după index linie, index coloană și valoare (două elemente sun egale dacă au aceeași
//        poziție și valori egale);
//        - comparabilitate între elemente după valoare.
//        Punctaj: 2 puncte
//        2) Să se citească matricea rară într-o listă (List<Element>) și să se afișeze la consolă numărul de
//        elemente negative
//        Punctaj: 2 puncte
//        3) Să se afișeze mediile pe coloane ale matricei, astfel:
//        index_coloana:valoare
//        ...
//        index_coloana:valoare
//        Punctaj: 3 puncte
//        4) Să se salveze în fișierul binar date\diagonal.dat elementele de pe diagonala principală. Să se
//        citească elementele de pe diagonala principală din fișier și să se afișeze la consolă.
//        Punctaj: 2 puncte

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Element implements Comparable<Element>,Serializable{
    private int index_linie;
    private int index_coloana;
    private double valoare_elemen;

    public Element() {
    }

    public Element(int index_linie, int index_coloana, double valoare_elemen) {
        this.index_linie = index_linie;
        this.index_coloana = index_coloana;
        this.valoare_elemen = valoare_elemen;
    }

    public Element(String linie){
        this.index_linie = Integer.parseInt(linie.split(",")[0]);
        this.index_coloana = Integer.parseInt(linie.split(",")[1]);
        this.valoare_elemen = Double.parseDouble(linie.split(",")[2]);
    }

    public int getIndex_linie() {
        return index_linie;
    }

    public void setIndex_linie(int index_linie) {
        this.index_linie = index_linie;
    }

    public int getIndex_coloana() {
        return index_coloana;
    }

    public void setIndex_coloana(int index_coloana) {
        this.index_coloana = index_coloana;
    }

    public double getValoare_elemen() {
        return valoare_elemen;
    }

    public void setValoare_elemen(double valoare_elemen) {
        this.valoare_elemen = valoare_elemen;
    }

    @Override
    public String toString() {
        return "Element{" +
                "index_linie=" + index_linie +
                ", index_coloana=" + index_coloana +
                ", valoare_elemen=" + valoare_elemen +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Element){
            Element e=(Element) obj;
            return (getIndex_coloana()==e.getIndex_coloana() &&
                    getIndex_linie()==e.getIndex_linie() &&
                    getIndex_coloana()==e.getIndex_coloana());
        }else return false;
    }

    @Override
    public int compareTo(Element e) {
        int nr=0;
        if(this.valoare_elemen>e.valoare_elemen) nr= 1;
        if (this.valoare_elemen<e.valoare_elemen) nr= -1;
        return nr;
    }
}




public class main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Element> elemente= new ArrayList<>();

        try(var fisier= new BufferedReader(new FileReader("date\\matricerara.txt"))){
           elemente=fisier.lines().map(Element::new).collect(Collectors.toList());
        }
        System.out.println("mor");
        for(Element e:elemente)
        {
            System.out.println(e);
        }

        System.out.println("-----cerinta 2-----");
        //cu tream
        System.out.println("Numarul elementelor negative este "+ elemente.stream().filter(c->c.getValoare_elemen()<0).count());
        //elemente.stream().filter(c->c.getValoare_elemen()<0).count();

        //cum am invatat in liceu
        int nr=0;
        for(Element e: elemente)
        {
            if(e.getValoare_elemen()<0) nr++;
        }
        System.out.println("Numarul elementelor negative este "+ nr);

        System.out.println("-----cerinta 4-----");

        List<Element> diagonala= new ArrayList<>();

        for(Element e: elemente){
            if(e.getIndex_linie()==e.getIndex_coloana())
            {
                diagonala.add(e);
            }
        }

        List<Element> d=new ArrayList<>();

        //scriere fisier binar
        try(ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("date\\diagonal.dat"))){
            for(Element e:diagonala){
                out.writeObject(e);
            }
        }

        //citire fisier binar
        try(FileInputStream in= new FileInputStream("date\\diagonal.dat");
                ObjectInputStream in2= new ObjectInputStream(in)){
            diagonala.clear();
            while(in.available()!=0){
                diagonala.add((Element)in2.readObject());
            }

        }

        for(Element e: diagonala){
            System.out.println(e);

        }

    }
}
