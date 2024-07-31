package factories;

import documents.Document;
import documents.ExcelImpl;

public class ExcelFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new ExcelImpl();
    }
}
