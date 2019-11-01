package by.attrade.excel;

import by.attrade.util.UrlUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelCellReader {
    public List<String> getStringColumnAsStringURLList(
            int iRowStart,
            int iRowEnd,
            int iColumn,
            String path
    ) throws IOException {
        evaluateArgs(iRowStart, iRowEnd);

        FileInputStream inputStream = new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        for (int i = 0; i < iRowStart; i++) {
            rowIterator.next();
        }

        List<String> list = new ArrayList<>();
        int countElems = getCountElems(iRowStart, iRowEnd);
        for (int i = 0; i < countElems && rowIterator.hasNext(); i++) {
            Row r = rowIterator.next();
            Cell c = r.getCell(iColumn);
            if (c == null) continue;
            CellType t = c.getCellTypeEnum();
            if (t != CellType.STRING) continue;
            String url = c.getStringCellValue();
            if(UrlUtils.isValidUrl(url)){
                list.add(url);
            }
        }
        return list;
    }

    private int getCountElems(int iRowStart, int iRowEnd) {
        return iRowEnd - iRowStart + 1;
    }

    private void evaluateArgs(int iRowStart, int iRowEnd) {
        if (iRowStart >= iRowEnd)
            throw new IllegalArgumentException(
                    "iRowStart: " + iRowStart + " cannot be more iRowEnd " + iRowEnd + "."
            );
        if (iRowStart < 0 || iRowEnd < 0) {
            throw new IllegalArgumentException(
                    "iRow cannot be less than 0. iRowStart: " + iRowStart + " iRowEnd: " + iRowEnd + "."
            );
        }
    }
}

