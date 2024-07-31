package factories;

import documents.Document;
import documents.PdfImpl;

public class PdfFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new PdfImpl();
    }
}
