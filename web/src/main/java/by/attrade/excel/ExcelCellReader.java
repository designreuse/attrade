package by.attrade.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelCellReader {
    public List<String> getStringColumnAsList(int iRow, int iColumn, String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
        for (int i = 0; i < iRow; i++) {
            rowIterator.next();
        }

        List<String> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row r = rowIterator.next();
            Cell c = r.getCell(iColumn);
            CellType t = c.getCellTypeEnum();
            if (t != CellType.STRING) {
                throw new RuntimeException("Column is not CellType.STRING.");
            }
            list.add(c.getStringCellValue());
        }
        return list;
    }
}

