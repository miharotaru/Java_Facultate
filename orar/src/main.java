//
//În fișierul date\orar.txt se găsesc informații privind activitățile programate în orarul unei serii.
//        Pe fiecare linie se află informații despre o programare, astfel:
//        • disciplina - șir de caractere
//        • zi - număr întreg între 1 și 5
//        • interval - număr întreg între 1 și 8
//        • tip - șir de caractere (CURS sau SEMINAR)
//        • formatie - șir de caractere
//        Valorile sunt despărțite prin virgulă.
//        Să se scrie o aplicație Java care să îndeplinească următoarele cerințe:
//        1) Să se construiască o clasă Programare care să permită stocarea informațiilor despre o programare
//        conform structurii de mai sus. Tipul programării trebuie stocată sub formă de enum. Compararea a
//        două programări trebuie să se realizeze pe baza zilei și a intervalului (o programare este considerată
//        'mai mică' dacă are ziua mai mică sau, în cazul în care zilele sunt egale, dacă are numărul intervalului
//        mai mic).
//        Punctaj: 2 puncte
//        2) Să se citească informațiile într-o listă de tip List<Programare> și să se afișeze la consolă numărul
//        total de cursuri și numărul total de seminarii.
//        Punctaj: 2 puncte
//        3) Știind că fiecare disciplină trebuie sa aibă o programare de tip curs și două programări de tip
//        seminar să se afișeze disciplinele la care orarul este complet, astfel:
//        disciplina_1
//        tip,zi,interval
//        tip,zi,interval
//        tip,zi,interval
//        disciplina_2
//        tip,zi,interval
//        tip,zi,interval
//        tip,zi,interval
//        ...
//        Punctaj: 3 puncte
//        4) Să se salveze în fișierul binar date\programari.dat lista de programări ordonate după zi și interval.
//        Să se citească fișierul binar și să se afișeze lista citită.
//        Punctaj: 2 puncte

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

enum Tip{
   CURS,SEMINAR;
}

class Programare implements Comparable<Programare>, Serializable{
    private String disciplina;
    private int zi;
    private int interval;
    private Tip tip;
    private String formatie;

    public Programare(String linie){
        this.disciplina=linie.split(",")[0];
        this.zi=Integer.parseInt(linie.split(",")[1]);
        this.interval=Integer.parseInt(linie.split(",")[2]);
        this.tip=Tip.valueOf(linie.split(",")[3]);
        this.formatie=linie.split(",")[4];
    }

    public Programare(String disciplina, int zi, int interval, Tip tip, String formatie) throws Exception {

        if(zi<1||zi>5){
            throw new Exception("Zi nu se incadreaza in parametrii");
        }
        if(interval<1 || interval>8){
            throw new Exception("Interval prea mare");
        }
        this.disciplina = disciplina;
        this.zi = zi;
        this.interval = interval;
        this.tip = tip;
        this.formatie = formatie;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public String getFormatie() {
        return formatie;
    }

    public void setFormatie(String formatie) {
        this.formatie = formatie;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "disciplina='" + disciplina + '\'' +
                ", zi=" + zi +
                ", interval=" + interval +
                ", tip=" + tip +
                ", formatie='" + formatie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Programare p) {
        int nr=-1;
        if(this.zi>p.zi){
            nr= 1;
        }else if (this.zi==p.zi)
            {
                if(this.interval>p.interval){
                    nr= 1;
                }else if(this.interval<p.interval){
                    nr=-1;
                }else {
                    nr= 0;
                }
            }
        return  nr;
    }


}

//2) Să se citească informațiile într-o listă de tip List<Programare> și să se afișeze la consolă numărul
//        total de cursuri și numărul total de seminarii.
public class main {




    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Programare> programari= new ArrayList<>();
        List<Programare> programari2= new ArrayList<>();

        int seminarii=0;
        int cursuri=0;

        try(var fisier= new BufferedReader(new FileReader("date\\orar.txt"))){
            programari= fisier.lines().map(Programare::new).collect(Collectors.toList());
        }

        for (var a:programari)
        {
            System.out.println(a);

            if(a.getTip().toString()=="CURS"){
                cursuri++;
            }else seminarii++;
        }
        System.out.println("------cerinta 2 ----");
        System.out.println("Avem "+cursuri+" cursuri si "+seminarii+" seminarii");

        System.out.println("-----cerinat 3------\n");


        System.out.println("-----cerinat 4------");
        programari.sort(Programare::compareTo);
//        for (var a:programari)
//        {
//            System.out.println(a);
//        }

            try(ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("date\\programari.dat"))){
                for(Programare a:programari){
                    out.writeObject(a);
                }
            }

            try (FileInputStream in= new FileInputStream(("date\\programari.dat"));
            ObjectInputStream in1= new ObjectInputStream(in)){
                while(in.available()!=0){
                    programari2.add((Programare) in1.readObject());
                }

            }

            for(var a:programari2){
                System.out.println(a);
            }




    }

}
