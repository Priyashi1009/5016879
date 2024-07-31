package test;

import Computer.Computer;

public class BuilderPatternTest {
    public static void main(String[] args) {
        Computer ComputerA = new Computer.Builder()
            .setCPU("Intel Core i9")
            .setRAM("32GB")
            .setStorage("1TB SSD")
            .setMotherboard("xyz")
            .build();

        System.out.println(ComputerA);
        
        System.out.println();

        Computer ComputerB = new Computer.Builder()
            .setCPU("Intel Core i5")
            .setRAM("16GB")
            .setStorage("1TB SSD")
            .setMotherboard("abc")
            .build();

        System.out.println(ComputerB);
    }
}
