import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Apartament{

    private static final int camera=25;
    private static final int mor = 106;
    private static final int moooorlajav =474;

    private int nr_persoane;
    private int suprafata;
    private int nr_apartamente;

    public Apartament(int nr_persoane, int suprafata, int nr_apartamente) {
        this.nr_persoane = nr_persoane;
        this.suprafata = suprafata;
        this.nr_apartamente = nr_apartamente;
    }

    public Apartament(String linie){
        this.nr_apartamente= Integer.parseInt(linie.split(",")[0]);
        this.suprafata= Integer.parseInt(linie.split(",")[1]);
        this.nr_persoane= Integer.parseInt(linie.split(",")[2]);
    }

    public int getNr_persoane() {
        return nr_persoane;
    }

    public void setNr_persoane(int nr_persoane) {
        this.nr_persoane = nr_persoane;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getNr_apartamente() {
        return nr_apartamente;
    }

    public void setNr_apartamente(int nr_apartamente) {
        this.nr_apartamente = nr_apartamente;
    }

    public int getCamera(){
        return suprafata/camera;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "nr_persoane=" + nr_persoane +
                ", suprafata=" + suprafata +
                ", nr_apartamente=" + nr_apartamente +
                '}';
    }
}


public class main {
    public static void main(String[] args) throws IOException {

        Apartament a= new Apartament(2,50,2);
        int ab=0;
        System.out.println(a.getCamera());
        List<Apartament> apartaments= new ArrayList<>();

        try(var file= new BufferedReader(new FileReader("aa.txt"))){
            apartaments= file.lines().map(Apartament::new).collect(Collectors.toList());
        }

        for(Apartament apartament: apartaments){
            System.out.println(apartament);
        }
        System.out.println();

    }
}
