package apartament;
//Fie datele de intrare (in directorul date):
//
//        a) intretinere_apartamente.txt: lista de apartamente (numar apartament - întreg, suprafata - întreg, numar persoane - întreg) - fișier text de forma:
//        1,100,1
//        2,50,1
//        3,75,2
//        ...
//
//        b) intretinere_facturi.json: fișier text în format JSON cu următoarea structură:
//
//        [
//        {
//        "denumire": "Apa calda",
//        "repartizare": "persoane",
//        "valoare": 800
//        },
//        {
//        "denumire": "Caldura",
//        "repartizare": "suprafata",
//        "valoare": 300
//        },
//        ...
//        ]
//
//        Să se scrie o aplicație Java care să îndeplinească următoarele cerințe:
//
//        1) Să se afișeze la consolă numărul apartamentului cu suprafața maximă
//        Punctaj: 1 punct.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        2) Să se afișeze la consolă numărul total de persoane care locuiesc în imobil.
//        Punctaj: 1 punct.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        3) Să se afișeze la consolă valoarea totală a facturilor pe fiecare tip de repartizare.
//        Punctaj: 1 punct.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        4) Să implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:
//        - componenta client trimite serverului un număr de apartament
//        - componenta server va întoarce clientului valoarea de plată defalcată pe cele trei tipuri cheltuieli pentru apartamentul respectiv.
//        Serverul se va opri după servirea unei cereri.
//
//        Punctaj:
//        1 punct - afișarea la consolă de către server a numărului apartamentului
//        1 punct - afișarea la consolă de către client a celor trei valori calculate
//        Criteriu de acordare a punctajului: afișarea corectă la consolă

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Apartament{
    private int numarApartament;
    private int suprafata;
    private int numarPersoane;

    public Apartament(int numarApartament, int suprafata, int numarPersoane) {
        this.numarApartament = numarApartament;
        this.suprafata = suprafata;
        this.numarPersoane = numarPersoane;
    }

    public int getNumarApartament() {
        return numarApartament;
    }

    public void setNumarApartament(int numarApartament) {
        this.numarApartament = numarApartament;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getNumarPersoane() {
        return numarPersoane;
    }

    public void setNumarPersoane(int numarPersoane) {
        this.numarPersoane = numarPersoane;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Apartament{");
        sb.append("numarApartament=").append(numarApartament);
        sb.append(", suprafata=").append(suprafata);
        sb.append(", numarPersoane=").append(numarPersoane);
        sb.append('}');
        return sb.toString();
    }
}

class Factura{
    private String denumire;
    private String repartizare;
    private double valoare;

    public Factura(String denumire, String reprezentare, double valoare) {
        this.denumire = denumire;
        this.repartizare = reprezentare;
        this.valoare = valoare;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getRepartizare() {
        return repartizare;
    }

    public void setRepartizare(String reprezentare) {
        this.repartizare = reprezentare;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Factura{");
        sb.append("denumire='").append(denumire).append('\'');
        sb.append(", reprezentare='").append(repartizare).append('\'');
        sb.append(", valoare=").append(valoare);
        sb.append('}');
        return sb.toString();
    }
}


public class main {
    static List<Apartament> apartaments= new ArrayList<>();
    static List<Factura> facturas= new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //1) Să se afișeze la consolă numărul apartamentului cu suprafața maximă
        apartaments=Citire();
        int suprafata_max=0;
        int suprafata_maxima=0;
        suprafata_max=apartaments.stream().sorted((a1,a2)->a1.getSuprafata()<a2.getSuprafata()?1:-1).findFirst().get().getNumarApartament();
        System.out.println(suprafata_max);
        suprafata_maxima=apartaments.stream().sorted((a1,a2)->a1.getSuprafata()<a2.getSuprafata()? 1:-1).mapToInt(Apartament::getSuprafata).findFirst().getAsInt();
        System.out.println(suprafata_max);

//        suprafata_maxima=apartaments.stream().sorted((a1,a2)-> a1.getNumarPersoane()>a2.getNumarPersoane()?1:-1).findFirst().get().getNumarPersoane();
//        System.out.println(suprafata_maxima);
//        suprafata_maxima=apartaments.stream().findFirst().get().getSuprafata();

        apartaments.stream().forEach(System.out::println);
        var ceva=
                apartaments.stream().filter(p->p.getNumarPersoane()<2)
                        .map(Apartament::getNumarApartament).collect(Collectors.toList());
        //System.out.println(ceva);
        System.out.println();
        ceva.stream().forEach(System.out::println);
        System.out.println("Cerinta 2");
        int nr_total=0;
        nr_total= apartaments.stream().mapToInt(Apartament::getNumarPersoane).sum();
        System.out.println(nr_total);

        facturas=CitireJson();
        facturas.stream().forEach(System.out::println);
        System.out.println(facturas);

        int persoane = 0;
        int apartament = 0;
        int suprafata = 0;
        for (var f : facturas){
            if(f.getRepartizare().equals("persoane")){
                persoane += f.getValoare();
            }else if( f.getRepartizare().equals("suprafata")){
                suprafata += f.getValoare();
            }else{
                apartament += f.getValoare();
            }
        }

        System.out.printf("\nTotal persoane: %d", persoane);
        System.out.printf("\nTotal suprafata: %d", suprafata);
        System.out.printf("\nTotal apartament: %d", apartament);

    }


    private static List<Apartament> Citire() throws IOException {
        try(BufferedReader r =new BufferedReader(new FileReader("date\\intretinere_apartamente.txt"))){
            List<Apartament> list = new ArrayList<>();
            list = r.lines().map(linie -> new Apartament(
                    Integer.parseInt(linie.split(",")[0]),
                    Integer.parseInt(linie.split(",")[1]),
                    Integer.parseInt(linie.split(",")[2])
            )).collect(Collectors.toList());
            return list;
        }

    }

    private static List<Factura> CitireJson() throws IOException{
        try(BufferedReader in = new BufferedReader(new FileReader("date\\intretinere_facturi.json"))){
            JSONTokener t =new JSONTokener(in);
            JSONArray jsFacturi = new JSONArray(t);
            List<Factura> list = new ArrayList<>();
            for(int i = 0; i < jsFacturi.length(); i++){
                JSONObject jsFactura = jsFacturi.getJSONObject(i);
                Factura factura = new Factura(jsFactura.getString("denumire"),
                        jsFactura.getString("repartizare"),
                        jsFactura.getInt("valoare"));
                list.add(factura);
            }
            return list;
        }
    }
}
