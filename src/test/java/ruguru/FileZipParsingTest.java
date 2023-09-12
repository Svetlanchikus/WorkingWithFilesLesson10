package ruguru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZipParsingTest {

    ClassLoader cl = FileZipParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Checking the contents of a CSV file from ZIP")
    void checkingCsvFromZip() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("qwe.txt");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                final String name = entry.getName();
                Assertions.assertTrue(name.contains("cechov_rasskazy_1886.pdf"));
                Assertions.assertTrue(name.contains("import_ou_csv.csv"));
                Assertions.assertTrue(name.contains("Report.xls"));
            }
        }
    }
//
//    @Test
//    @DisplayName("PDF из ZIP")
//    void testPdfTest() throws IOException, CsvException {
//        try (ZipInputStream zis = openZipStream()) {
//            verifyZipEntryContent(zis, "PdfTest.pdf", inputStream -> {
//                PDF pdf = new PDF(inputStream);
//                Assertions.assertTrue(pdf.text.contains("Пример pdf"));
//            });
//        }
//    }
//
//    @Test
//    @DisplayName("Excel из ZIP")
//    void testXlsxTest() throws Exception {
//        try (ZipInputStream zis = openZipStream()) {
//            verifyZipEntryContent(zis, "XlsxTest.xlsx", inputStream -> {
//                XLS xls = new XLS(inputStream);
//                String cellValue = xls.excel.getSheetAt(0)
//                        .getRow(3)
//                        .getCell(3)
//                        .getStringCellValue();
//                Assertions.assertTrue(cellValue.contains("Male"));
//                assertThat(xls.excel.getSheetName(0), equalTo("Sheet1"));
//            });
//        }
//    }

}

