package engleza;

import java.util.List;

public class VechThread implements Runnable{

    private List<ElectronicDevice> phoneList;
    private double avgWeight;

    public VechThread(String file) throws Exception {
       phoneList= Utils.readPhones(file);
    }

    public List<ElectronicDevice> getPhoneList() {
        return phoneList;
    }

    public double getAvgWeight() {
        return avgWeight;
    }



    @Override
    public void run() {

        avgWeight = phoneList.stream()
                .map(p -> (Phone) p)
                .mapToDouble(Phone::getWeight)
                .average()
                .getAsDouble();

    }
}
