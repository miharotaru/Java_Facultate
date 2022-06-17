package spital;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//În fișierul date\sectii.json se găsesc informații privind secțiile unui spital de urgență.
//        Fisierul este structurat astfel:
//        [
//        {
//        "cod_sectie": 1,
//        "denumire": "ORL",
//        "numar_locuri":10
//        },
//        ...
//        ]
//        În fișierul date\pacienti.txt se află informații privind pacienții internați, câte o
//        linie pentru fiecare pacient, astfel:
//        cnp_pacient, nume_pacient,varsta,cod_secție
//        ...
//        Cnp-ul este de tip long, numele este șir de caractere iar codul secției si varsta sunt intregi.
//
//        Să se scrie o aplicație Java care să îndeplinească următoarele cerințe:
//
//        1) Să afișeze la consolă lista secțiilor spitalului cu un număr de locuri strict mai mare decât 10
//        Punctaj: 1 punct.
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        2) Să afișeze la consolă lista secțiilor spitalului sortată descrescător după varsta medie a pacientilor
//        internați pe secție.
//        Pentru fiecare secție se va afișa codul, denumirea, numărul de locuri și vârsta medie a pacienților.
//        Punctaj: 1 punct
//        Criteriu de acordare a punctajului: afișarea corectă la consolă
//
//        3) Să se scrie în fișierul text jurnal.txt un raport al internărilor pe secții, de forma:
//        cod_secție_1,nume secție_1,numar_pacienti_1
//        ...
//        Punctaj: 1 punct
//        Criteriu de acordare a punctajului: urmărirea fișierului raport.txt
//
//        4) Să implementeze funcționalitățile de server și client TCP/IP și să se execute următorul scenariu:
//        componenta client trimite serverului codul unei secții iar componenta server va întoarce clientului numărul de locuri libere.
//        Serverul se va opri după servirea unei cereri.
//
//        Punctaj:
//        1 punct - afișarea la consolă de către server a codului primit de la client
//        1 punct - afișarea la consolă de către client a numărului de locuri libere
//        Criteriu de acordare a punctajului: afișarea corectă la consolă


class Sectie{
   private int cod_sectie;
    private String denumire;
    private int nr_locuri;
    private double varsta_medie;
    private int nr_pacienti;

    public int getNr_pacienti() {
        return nr_pacienti;
    }

    public void setNr_pacienti(int nr_pacienti) {
        this.nr_pacienti = nr_pacienti;
    }

    public double getVarsta_medie() {
        return varsta_medie;
    }

    public void setVarsta_medie(double varsta_medie) {
        this.varsta_medie = varsta_medie;
    }

    public Sectie(int cod_sectie, String denumire, int nr_locuri) {
        this.cod_sectie = cod_sectie;
        this.denumire = denumire;
        this.nr_locuri = nr_locuri;
    }

    public int getCod_sectie() {
        return cod_sectie;
    }

    public void setCod_sectie(int cod_sectie) {
        this.cod_sectie = cod_sectie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }


    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public String toString() {
        return "Sectie{" +
                "cod_sectie=" + cod_sectie +
                ", denumire='" + denumire + '\'' +
                ", nr_locuri=" + nr_locuri +
                ", varsta_medie=" + varsta_medie +
                '}';
    }
}

class Pacient{
    private long cnp_pacient;
    private String nume_pacient;
    private int varsta;
    private int cod_sectie;

    public Pacient(long cnp_pacient, String nume_pacient, int varsta, int cod_sectie) {
        this.cnp_pacient = cnp_pacient;
        this.nume_pacient = nume_pacient;
        this.varsta = varsta;
        this.cod_sectie = cod_sectie;
    }

    public long getCnp_pacient() {
        return cnp_pacient;
    }

    public void setCnp_pacient(long cnp_pacient) {
        this.cnp_pacient = cnp_pacient;
    }

    public String getNume_pacient() {
        return nume_pacient;
    }

    public void setNume_pacient(String nume_pacient) {
        this.nume_pacient = nume_pacient;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getCod_sectie() {
        return cod_sectie;
    }

    public void setCod_sectie(int cod_sectie) {
        this.cod_sectie = cod_sectie;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cnp_pacient=" + cnp_pacient +
                ", nume_pacient='" + nume_pacient + '\'' +
                ", varsta=" + varsta +
                ", cod_sectie=" + cod_sectie +
                '}';
    }
}

public class main {
    static List<Sectie> secties= new ArrayList<>();
    static List<Pacient> pacients= new ArrayList<>();
    public static void main(String[] args) throws IOException {
        System.out.println("cerinta 1");
        secties=citireJson();
      secties.stream().filter(p->p.getNr_locuri()>5).forEach(System.out::println);
        System.out.println("cerinta 2");
    //Să afișeze la consolă lista secțiilor spitalului sortată descrescător după varsta medie a pacientilor
//        internați pe secție.
        pacients=citireTXT();
//        for (Pacient f : pacients)
//        {
//            System.out.println(f);
//        }
//        pacients.stream().forEach(System.out::println);


        for(var s : secties){
            int sum=0;
            int count=0;
            for (var p: pacients){
                if(p.getCod_sectie()==s.getCod_sectie()){
                    sum+=p.getVarsta();
                    count++;
                }
            }
            s.setNr_pacienti(count);
            s.setVarsta_medie(sum/count);
        }

        secties.stream().sorted((a,b)-> a.getVarsta_medie()> b.getVarsta_medie()?-1:1).forEach(System.out::println);

        System.out.println("cerinta 3");
        try (BufferedWriter w= new BufferedWriter(new FileWriter("jurnal.txt"))){
            for(var s:secties){
                w.write(s.getCod_sectie()+","+s.getDenumire()+","+ s.getNr_pacienti());
                w.newLine();
            }
        }

    }



     private static List<Sectie> citireJson() throws IOException {
        try(BufferedReader r= new BufferedReader(new FileReader("date\\sectii.json"))){
            JSONTokener t= new JSONTokener(r);
            JSONArray arrayJson= new JSONArray(t);
            List<Sectie> list= new ArrayList<>();
            for (int i=0; i<arrayJson.length();i++){
                JSONObject obiect= arrayJson.getJSONObject(i);
                Sectie sectie= new Sectie(obiect.getInt("cod_sectie"),obiect.getString("denumire"),obiect.getInt("numar_locuri"));
                list.add(sectie);
            }
            return list;
        }
    }

    private static List<Pacient> citireTXT() throws IOException {
        try(BufferedReader r = new BufferedReader(new FileReader("date\\pacienti.txt"))) {
            List<Pacient> list= new ArrayList<>();
            list=r.lines().map(linie-> new Pacient(
                    Long.parseLong(linie.split(",")[0]),
                    linie.split(",")[1],
                    Integer.parseInt(linie.split(",")[2]),
                    Integer.parseInt(linie.split(",")[3])
            )).collect(Collectors.toList());
            return list;
        }
    }

}
