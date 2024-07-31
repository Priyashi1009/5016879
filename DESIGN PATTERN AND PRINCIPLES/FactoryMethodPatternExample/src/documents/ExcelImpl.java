package documents;

public class ExcelImpl implements ExcelDocument {

    @Override
    public void Using() {
        System.out.println("Using Excel document");
    }

    @Override
    public void compute() {
        System.out.println("Computing in Excel document");
    }
}

