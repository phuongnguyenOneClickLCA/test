package com.bionova.optimi.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class LocalizationKeyResolver {
    private static final String[] supportedLocales = {"en", "fi", "no", "de", "fr", "nl", "es", "se", "it", "hu", "jp"};

    public static void main(String[] args) {
        getLocalizationProperties("/home/pmm/Projects/360optimi/grails-app/i18n/");
    }

    private static void getLocalizationProperties(String localizationFilesFolder) {
        String filePrefix = "messages_";
        InputStream inputStream;
        Workbook workbook = new HSSFWorkbook();
        String sheetName = WorkbookUtil.createSafeSheetName("localizationSheet");
        Sheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        Cell localizationKeyCell = row.createCell(0);
        localizationKeyCell.setCellValue("key"); // Create cell with value key for localization key

        // Create cells with each having language code as value
        for (int i = 0; i < supportedLocales.length; i++) {
            row.createCell(i + 1).setCellValue(supportedLocales[i]);
        }
        List<String> localizationKeys = new ArrayList<String>();

        try {
            inputStream = new FileInputStream(localizationFilesFolder + "messages_" + supportedLocales[0] + ".properties");
            Properties prop = new Properties();
            prop.load(inputStream);

            if (inputStream != null) {
                Enumeration propertyNames = prop.propertyNames();
                int rowIndex = 1;

                while (propertyNames.hasMoreElements()) {
                    Row keyRow = sheet.createRow(rowIndex);
                    String key = propertyNames.nextElement().toString();
                    keyRow.createCell(0).setCellValue(key);
                    keyRow.createCell(1).setCellValue(prop.getProperty(key));
                    localizationKeys.add(key);
                    rowIndex++;
                }
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Problem in loading localization file: " + localizationFilesFolder + "messages_" + supportedLocales[0] + ".properties: " + e.getMessage());
        }

        if (!localizationKeys.isEmpty()) {
            for (int i = 1; i < supportedLocales.length; i++) {
                try {
                    inputStream = new FileInputStream(localizationFilesFolder + "messages_" + supportedLocales[i] + ".properties");
                    Properties prop = new Properties();
                    prop.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

                    for (int j = 0; j < localizationKeys.size(); j++) {
                        String key = localizationKeys.get(j);
                        Row keyRow = sheet.getRow(j + 1);
                        String value = prop.getProperty(key);
                        keyRow.createCell(i + 1).setCellValue(value);
                    }
                    inputStream.close();
                } catch (Exception e) {

                }
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("localizations.xlsx");
            workbook.write(fileOutputStream);
        } catch (Exception e) {

        }
    }
}
