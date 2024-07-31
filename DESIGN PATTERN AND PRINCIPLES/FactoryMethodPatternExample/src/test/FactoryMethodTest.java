package test;

import documents.Document;
import factories.DocumentFactory;
import factories.WordFactory;
import factories.PdfFactory;
import factories.ExcelFactory;

public class FactoryMethodTest {
    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordFactory();
        Document wordDoc = wordFactory.createDocument();
        wordDoc.Using();
        
        System.out.println();

        DocumentFactory pdfFactory = new PdfFactory();
        Document pdfDoc = pdfFactory.createDocument();
        pdfDoc.Using();
        
        System.out.println();

        DocumentFactory excelFactory = new ExcelFactory();
        Document excelDoc = excelFactory.createDocument();
        excelDoc.Using();
    }
}
