package engleza;

import java.io.Serializable;

public class Phone implements ElectronicDevice, Serializable, Cloneable {

    public float weight;
    public double diagonal;
    public String producer;

    public Phone() {
    }

    public Phone(String line){
        weight = Float.parseFloat(line.split(",")[0]);
        diagonal = Double.parseDouble(line.split(",")[1]);
        producer = line.split(",")[2];
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) throws Exception{
        if (weight <0)throw new Exception();
        else this.weight = weight;

    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) throws Exception{
        if(diagonal<0)throw new Exception();
        else this.diagonal = diagonal;

    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) throws Exception{
        if(producer==null || producer.length()<=1) throw new Exception();
        else this.producer = producer;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    @Override
    public String infoDevice() {
        return this.producer;
    }
}
