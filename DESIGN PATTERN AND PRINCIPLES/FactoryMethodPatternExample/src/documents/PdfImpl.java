package documents;

public class PdfImpl implements PdfDocument {

    @Override
    public void Using() {
        System.out.println("Using PDF document");
    }

    @Override
    public void secure() {
        System.out.println("Securing PDF document");
    }
}
