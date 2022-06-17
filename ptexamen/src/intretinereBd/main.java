package intretinereBd;
/*
Fie datele de intrare (in directorul date):
a) intretinere_facturi.txt: lista de facturi (denumire - string, repartizare - string, valoare - double) - fișier text de forma:
Gaze naturale,persoane,140
Apa calda,persoane,800
Caldura,suprafata,300
Gunoi,apartament,100
Câmpul repartizare poate avea următoarele valori: apartament, suprafata sau persoane.
Denumirea facturii nu poate conține caracterul virgulă.
b) tabela Apartamente din baza de date SQLite intretinere.db cu următoarele câmpuri:
NumarApartament - integer
Suprafata - integer
NumarPersoane - integer
Să se scrie o aplicație Java care să îndeplinească următoarele cerințe:
1) Să se afișeze la consolă valoarea totală a facturilor.
Punctaj: 1 punct.
Criteriu de acordare a punctajului: afișarea corectă la consolă
2) Să se afișeze la consolă valoarea totală a facturilor pe fiecare tip de repartizare.
Punctaj: 1 punct.
Criteriu de acordare a punctajului: afișarea corectă la consolă
3) Să se afișeze la consolă suprafața totală a apartamentelor din bloc.
Punctaj: 1 punct.
Criteriu de acordare a punctajului: afișarea corectă la consolă
4) Să se scrie în fișierul text date\tabel_intretinere.txt tabelul de intreținere în forma:
Număr apartament, Suprafata, Persoane, Cheltuieli Suprafata, Cheltuieli Persoane, Cheltuieli Apartament, Total de plata
...
Tabelul trebuie să fie sortat în funcție de numărul apartamentului.
Punctaj: 2 puncte
Criteriu de acordare a punctajului: urmărirea fișierului tabel_intretinere.txt
 */
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Factura{
    private String denumire;
    private String repartizare;
    private double valoare;

    public Factura(String denumire, String repartizare, double valoare) {
        this.denumire = denumire;
        this.repartizare = repartizare;
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

    public void setRepartizare(String repartizare) {
        this.repartizare = repartizare;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "denumire='" + denumire + '\'' +
                ", repartizare='" + repartizare + '\'' +
                ", valoare=" + valoare +
                '}';
    }
}

class Apartamente{
    private int nrApratment;
    private int suparafata;
    private int nrPersoane;

    public Apartamente(int nrApratment, int suparafata, int nrPersoane) {
        this.nrApratment = nrApratment;
        this.suparafata = suparafata;
        this.nrPersoane = nrPersoane;
    }

    public int getNrApratment() {
        return nrApratment;
    }

    public void setNrApratment(int nrApratment) {
        this.nrApratment = nrApratment;
    }

    public int getSuparafata() {
        return suparafata;
    }

    public void setSuparafata(int suparafata) {
        this.suparafata = suparafata;
    }

    public int getNrPersoane() {
        return nrPersoane;
    }

    public void setNrPersoane(int nrPersoane) {
        this.nrPersoane = nrPersoane;
    }

    @Override
    public String toString() {
        return "Apartamente{" +
                "nrApratment=" + nrApratment +
                ", suparafata=" + suparafata +
                ", nrPersoane=" + nrPersoane +
                '}';
    }
}



public class main implements Closeable {

    private static Connection c;
    public main() throws SQLException {
        c = DriverManager.getConnection("jdbc:sqlite:date\\intretinere.db");
    }

    static List<Apartamente> apartamentes= new ArrayList<>();
    static List<Factura> facturas= new ArrayList<>();
    public static void main(String[] args) throws IOException, SQLException {

        facturas= citireTXT();
        facturas.stream().forEach(System.out::println);
        System.out.println(facturas.stream().mapToDouble(Factura::getValoare).sum());

        for(var f : facturas){
            System.out.printf("Valoarea facturilor pentru %s este %2.2f\n", f.getRepartizare(), facturas
                    .stream().filter(a-> a.getRepartizare().equals(f.getRepartizare())).mapToDouble(Factura::getValoare).sum());
        }

        double persoane=0, apartament=0, suprafata=0;
        for(var f:facturas)
        {
            if(f.getRepartizare().equals("persoane")){
                persoane+=f.getValoare();
            }else if(f.getRepartizare().equals("apartament")){
                apartament+=f.getValoare();
            }else suprafata+=f.getValoare();
        }
        facturas.stream().sorted(Comparator.comparing(Factura::getValoare).thenComparing(Factura::getValoare).reversed()).forEach(System.out::println);

        System.out.println(persoane);
        System.out.println(apartament);
        System.out.println(suprafata);

        try(main app = new main()){
            apartamentes = CitireBd();
        }

        System.out.println("Suprafta totala este: " + apartamentes.stream().mapToInt(Apartamente::getSuparafata).sum());
    }


    public  static List<Apartamente> CitireBd() throws SQLException {
        try(Statement s = c.createStatement()){
            try(ResultSet date = s.executeQuery("SELECT * FROM Apartamente")){
                List<Apartamente> la = new ArrayList<>();
                while(date.next()){
                    la.add(new Apartamente(date.getInt("NumarApartament"),
                            date.getInt("Suprafata"),
                            date.getInt("NumarPersoane")));
                }
                return la;
            }
        }
    }

    public static List<Apartamente> citireBd() throws SQLException {
        try(Statement s= c.createStatement()){
            try(ResultSet date = s.executeQuery("SELECT * FROM Apartamente")){
                List<Apartamente> lista= new ArrayList<>();
                while (date.next()){
                    lista.add(new Apartamente(
                            date.getInt("NumarApartamente"),
                            date.getInt("Suprafata"),
                            date.getInt("NumarPersoane")
                    ));
                }
                return lista;
            }

        }
    }

    static List<Factura> citireTXT() throws IOException {
        try(BufferedReader r= new BufferedReader(new FileReader("date\\intretinere_facturi.txt"))) {
            List<Factura> lista = new ArrayList<>();
            lista = r.lines().map(linie -> new Factura(
                    linie.split(",")[0],
                    linie.split(",")[1],
                    Double.parseDouble(linie.split(",")[2])
            )).collect(Collectors.toList());
            return lista;
        }
    }

    @Override
    public void close() throws IOException {
        if(c!=null){
            try {
                c.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
