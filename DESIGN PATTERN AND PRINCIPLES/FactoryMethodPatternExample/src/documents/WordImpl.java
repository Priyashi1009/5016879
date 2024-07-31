package documents;

public class WordImpl implements WordDocument {

    @Override
    public void Using() {
        System.out.println("Using Word document");
    }

    @Override
    public void spellCheck() {
        System.out.println("Performing spell check in Word document");
    }
}
