package engleza;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static List<ElectronicDevice> list= new ArrayList<>();

    public static List<ElectronicDevice> createPhones(int n) throws Exception{
        for(int i=0;i<n;i++)
        {
            list.add(new Phone());
        }
        return list;
    }
    public static List<ElectronicDevice> readPhones(String file) throws Exception {
        try(var fisier= new BufferedReader(new FileReader(file))) {
            return fisier.lines().map(Phone::new).collect(Collectors.toList());
        }
    }

    public static void writeBinaryPhones(String file, List<ElectronicDevice> listP) throws Exception{
        try(var fisier = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))){
            fisier.writeObject(listP);
        }
    }

    public static List<ElectronicDevice> readBinaryPhones(String file) throws Exception{
        try(var fisier = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
            return (List<ElectronicDevice>) fisier.readObject();
        }
    }


}
