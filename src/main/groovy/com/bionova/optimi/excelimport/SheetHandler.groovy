package com.bionova.optimi.excelimport

import com.bionova.optimi.core.util.LoggerUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.model.SharedStringsTable
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import com.bionova.optimi.core.Constants

/**
 * @author oof
 */
public class SheetHandler extends DefaultHandler {
    static Log log = LogFactory.getLog(SheetHandler.class)

    private SharedStringsTable sst
    private String tooManyColumn


    public SheetHandler(SharedStringsTable sst, Integer maxColumns) {
        this.sst = sst
        if (maxColumns) {
            this.tooManyColumn = CellReference.convertNumToColString(maxColumns)
        } else {
            this.tooManyColumn = CellReference.convertNumToColString(Constants.MAX_COLUMNS)
        }
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // log.error works here, log.info does not
        // c => cell
        if(name.equals("c")) {
            if (attributes.getValue("r").startsWith(this.tooManyColumn)) {
                throw new IndexOutOfBoundsException("Too many columns!")
            }
        }
    }
}