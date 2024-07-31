package factories;

import documents.Document;
import documents.WordImpl;

public class WordFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new WordImpl();
    }
}
