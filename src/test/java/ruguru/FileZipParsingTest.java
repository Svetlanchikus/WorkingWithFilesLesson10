package ruguru;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileZipParsingTest {


    @Test
    @DisplayName("Checking the contents of a PDF file in a ZIP archive")
    void checkingPdfInZipContains() throws Exception {
        try (ZipInputStream zis = openZipStream("3files.zip")) {
            verifyZipEntryContent(zis, "cechov_rasskazy_1886.pdf", inputStream -> {
                PDF pdf = new PDF(inputStream);
                Assertions.assertTrue(pdf.title.contains("Антон Павлович Чехов. Рассказы 1886."));
                Assertions.assertTrue(pdf.text.contains("П А М Я Т Н И К И  Л И Т Е Р А Т У Р Ы"));
                Assertions.assertTrue(pdf.text.contains("Антон ЧЕХОВ"));
            });
        }
    }

    @Test
    @DisplayName("Checking the contents of an Excel file in a ZIP archive")
    void checkingExcelInZipContains() throws Exception {
        try (ZipInputStream zis = openZipStream("3files.zip")) {
            verifyZipEntryContent(zis, "Report.xls", inputStream -> {
                XLS xls = new XLS(inputStream);
                Assertions.assertEquals("Внешний идентификатор для импорта",
                        xls.excel.getSheetAt(0)
                                .getRow(0)
                                .getCell(0)
                                .getStringCellValue());
                Assertions.assertEquals("Вышестоящий отдел",
                        xls.excel.getSheetAt(0)
                                .getRow(0)
                                .getCell(1)
                                .getStringCellValue());
                Assertions.assertEquals("Название",
                        xls.excel.getSheetAt(0)
                                .getRow(0)
                                .getCell(2)
                                .getStringCellValue());
                Assertions.assertEquals("OU001",
                        xls.excel.getSheetAt(0)
                                .getRow(1)
                                .getCell(0)
                                .getStringCellValue());
                Assertions.assertEquals("Коммерческий департамент",
                        xls.excel.getSheetAt(0)
                                .getRow(1)
                                .getCell(2)
                                .getStringCellValue());
            });
        }
    }

    @Test
    @DisplayName("Checking the contents of a CSV file in a ZIP archive")
    void checkingCsvInZipContains() throws Exception {
        try (ZipInputStream zis = openZipStream("3files.zip")) {
            verifyZipEntryContent(zis, "import_ou_csv.csv", inputStream -> {
                Reader reader = new InputStreamReader(inputStream);
                CSVReader csvReader = new CSVReader(reader);
                List<String[]> content = csvReader.readAll();
                Assertions.assertEquals(6, content.size());
                Assertions.assertArrayEquals(new String[]{"OU001"}, content.get(0));
                Assertions.assertArrayEquals(new String[]{"OU001;;OU003"}, content.get(1));
                Assertions.assertArrayEquals(new String[]{"OU002;OU001"}, content.get(2));
                Assertions.assertArrayEquals(new String[]{"OU003;;OU004"}, content.get(3));
                Assertions.assertArrayEquals(new String[]{"OU004;;OU001;OU005"}, content.get(4));
                Assertions.assertArrayEquals(new String[]{"OU005;OU004OU006"}, content.get(5));
            });
        }
    }

    private ZipInputStream openZipStream(String zipName) {
        ClassLoader cl = FileZipParsingTest.class.getClassLoader();
        InputStream stream = cl.getResourceAsStream(zipName);
        return new ZipInputStream(stream);
    }

    private void verifyZipEntryContent(ZipInputStream zis, String entryName, ZipEntryVerifier verifier) throws Exception {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            final String name = entry.getName();
            if (name.contains(entryName)) {
                verifier.verifyEntry(zis);
            }
        }
    }

    interface ZipEntryVerifier {
        void verifyEntry(InputStream inputStream) throws IOException, CsvException;
    }
}
