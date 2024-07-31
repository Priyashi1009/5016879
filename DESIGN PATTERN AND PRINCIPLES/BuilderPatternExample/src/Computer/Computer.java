package Computer;

public class Computer {
    private String CPU;
    private String RAM;
    private String storage;
    private String motherboard;

    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.motherboard = builder.motherboard;
    }

    @Override
    public String toString() {
        return "Computer Specifications:\n" +
               "CPU: " + CPU + "\n" +
               "RAM: " + RAM + "\n" +
               "Storage: " + storage + "\n" +
               "Motherboard: " + motherboard;
    }

    public static class Builder {
        private String CPU;
        private String RAM;
        private String storage;
        private String motherboard;

        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }

        public Builder setRAM(String RAM) {
            this.RAM = RAM;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setMotherboard(String motherboard) {
            this.motherboard = motherboard;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
