package techcareer.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CsvHelper {
    protected static final Logger logger = LogManager.getLogger(CsvHelper.class);
    private static CsvHelper instance;

    private CsvHelper() {

    }

    public static CsvHelper getInstance() {
        if (instance == null) {
            instance = new CsvHelper();
        }
        return instance;
    }

    /**
     * Verilen satır ve sütun bilgisi ile veri döner.
     *
     * @param filePath    Dosya yolu
     * @param rowIndex    Satir
     * @param columnIndex Sütun
     * @param resources   Dosya resources altında ise true verilir.
     * @return String cellValue
     */
    public String getValueWithRowAndColumn(String filePath, int rowIndex, int columnIndex,boolean resources) {
        String cellValue = null;
        String path = null;
        if (resources) {
            path = FileHelper.getPathFromResources(filePath);
        }
        try (Reader reader = new FileReader(path); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            List<CSVRecord> records = csvParser.getRecords();
            if (rowIndex < records.size()) {
                CSVRecord record = records.get(rowIndex);
                if (columnIndex < record.size()) {
                    cellValue = record.get(columnIndex);
                } else {
                    logger.error("Verilen sütun ile csvde veri bulunamamıştır. Sütun bilgisi 0 dan başlamaktadır. Bu bilgiye dikkat ederek seçim yapınız.");
                }
            } else {
                logger.error("Verilen satır ile csvde veri bulunamamıştır. Ssatır bilgisi 0 dan başlamaktadır. Bu bilgiye dikkat ederek seçim yapınız.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Csv dosyasından okunan değer :{}",cellValue);
        return cellValue;
    }
}
