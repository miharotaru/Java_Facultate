package penalizari;
//Fie datele de intrare (in directorul date):
//
//        a) intretinere_apartamente.txt: lista de apartamente (numar apartament - întreg,
//        nume - string, valoare de plata - string) - fișier text de forma:
//        1,Popescu,364.5
//        2,Enescu,164.5
//        3,Testescu,200.2
//        ...
//
//        b) penalizari.json: fișier text în format JSON cu următoarea structură:
//
//        [
//        {
//        "numar_apartament": 1,
//        "penalizare": 5.00
//        },
//        {
//        "numar_apartament": 3,
//        "penalizare": 5.32
//        },
//        ...
//        ]
//
//        Un apartament poate avea cel mult o penalizare.
//
//        Să se scrie o aplicație Java care să îndeplinească următoarele cerințe:
//
//        1) Să se afișeze la consolă valoarea întreținerii (fără penalizări) pentru întreg imobilul în formatul:
//
//        CERINTA 1: total intretinere 1999.12 lei
//
//        Punctaj: 2 puncte.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        2) Să se afișeze la consolă lista de apartamente ordonate descrescător în funcție de suma de plată.
//        Pentru fiecare apartament se va afișa suma de plată, numărul și numele în formatul:
//
//        CERINTA 2:
//        364.5  1 Popescu
//        200.2  3 Testescu
//        164.5  2 Enescu
//
//        Punctaj: 2 puncte.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        3) Să se afișeze la consolă lista apartementelor care au penalizări.
//        Pentru fiecare apartament se va afișa suma finala (suma inițială + penalizare), numărul și numele în formatul:
//
//        CERINTA 3:
//        369.50  1 Popescu
//        205.52  3 Testescu
//
//        Punctaj: 2 puncte.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Apartament{
    private int nr_apartament;
    private String nume;
    private double valoare_plata;
    private double penalizare=0;

    public double getPenalizare() {
        return penalizare;
    }

    public void setPenalizare(double penalizare) {
        this.penalizare = penalizare;
    }

    public double sumaCuPenalizare(){
        double suma_noua=0;
        return valoare_plata-penalizare;
    }

    public Apartament(int nr_apartament, String nume, double valoare_plata) {
        this.nr_apartament = nr_apartament;
        this.nume = nume;
        this.valoare_plata = valoare_plata;
    }

    public int getNr_apartament() {
        return nr_apartament;
    }

    public void setNr_apartament(int nr_apartament) {
        this.nr_apartament = nr_apartament;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getValoare_plata() {
        return valoare_plata;
    }

    public void setValoare_plata(double valoare_plata) {
        this.valoare_plata = valoare_plata;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "nr_apartament=" + nr_apartament +
                ", nume='" + nume + '\'' +
                ", valoare_plata=" + valoare_plata +
                '}';
    }
}


class Penalizari{
    private int nr_apartament;
    private double penalizare;

    public Penalizari(int nr_apartament, double penalizare) {
        this.nr_apartament = nr_apartament;
        this.penalizare = penalizare;
    }

    public int getNr_apartament() {
        return nr_apartament;
    }

    public void setNr_apartament(int nr_apartament) {
        this.nr_apartament = nr_apartament;
    }

    public double getPenalizare() {
        return penalizare;
    }

    public void setPenalizare(double penalizare) {
        this.penalizare = penalizare;
    }

    @Override
    public String toString() {
        return "Penalizari{" +
                "nr_apartament=" + nr_apartament +
                ", penalizare=" + penalizare +
                '}';
    }
}


public class main {
    static List<Apartament> apartaments= new ArrayList<>();
    static List<Penalizari> penalizaris= new ArrayList<>();

    public static void main(String[] args) throws IOException {
        apartaments=citireTXT();
        apartaments.stream().forEach(System.out::println);
        penalizaris=citireJson();
        penalizaris.stream().forEach(System.out::println);
        System.out.println("cerinta 1");
        System.out.println( apartaments.stream().mapToDouble(Apartament::getValoare_plata).sum());
        System.out.println("cerinta 2");

        apartaments.stream().forEach(apartament -> {
            System.out.printf("%3f %2d  %2s %n",
                    apartament.getValoare_plata(),
                    apartament.getNr_apartament(),
                    apartament.getNume());
        });

        System.out.println("cerinta 3");

        for(var a: apartaments){
            for (var p:penalizaris){
                if(p.getNr_apartament()==a.getNr_apartament()){
                    a.setPenalizare(p.getPenalizare());
                }
            }
        }

        System.out.printf("suma+penalizare    nr apartament    nume \n");
        apartaments.stream().filter(p->p.getPenalizare()==0).forEach(apartament -> {
            System.out.printf("%2f  %d  %s %n",
                    apartament.sumaCuPenalizare(),
                    apartament.getNr_apartament(),
                    apartament.getNume()
                    );
        });
    }


    static List<Apartament> citireTXT() throws IOException {
        try(BufferedReader r= new BufferedReader(new FileReader("date\\a_intretinere.txt"))){
            List<Apartament> list= new ArrayList<>();
            list=r.lines().map(linie->new Apartament(
                    Integer.parseInt(linie.split(",")[0]),
                    linie.split(",")[1],
                    Double.parseDouble(linie.split(",")[2])
            )).collect(Collectors.toList());
            return list;
        }
    }
    static List<Penalizari> citireJson() throws IOException {
        try(BufferedReader r= new BufferedReader(new FileReader("date\\penalizari_eu.json"))) {
            JSONTokener t= new JSONTokener(r);
            JSONArray array= new JSONArray(t);
            List<Penalizari>lista= new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject object= array.getJSONObject(i);
                Penalizari penalizari= new Penalizari(
                        object.getInt("numar"), object.getDouble("penalizare")
                );
                lista.add(penalizari);
            }
            return lista;
        }
    }

}
