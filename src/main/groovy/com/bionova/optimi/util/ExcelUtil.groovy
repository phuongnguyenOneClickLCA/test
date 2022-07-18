package com.bionova.optimi.util

import com.bionova.optimi.core.Constants
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory
import org.apache.poi.hssf.eventusermodel.HSSFListener
import org.apache.poi.hssf.eventusermodel.HSSFRequest
import org.apache.poi.hssf.record.Record
import org.apache.poi.hssf.record.RowRecord
import org.apache.poi.poifs.filesystem.POIFSFileSystem


public class ExcelUtil {


    /**
     * This method tries to validate max columns constraint with smallest memory footprint (xls format only)
     * Ref: https://poi.apache.org/components/spreadsheet/how-to.html#record_aware_event_api
     * @param xlsFile
     */
    public static void validateMaxColumnConstrain(final File xlsFile) {
        // create a new org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(xlsFile)
        // get the Workbook (excel part) stream in a InputStream
        InputStream din = poifs.createDocumentInputStream("Workbook")
        // construct out HSSFRequest object
        HSSFRequest req = new HSSFRequest()
        // lazy listen for ALL records with the listener shown above
        req.addListenerForAllRecords(new CustomHSSFEventListener())
        // create our event factory
        HSSFEventFactory factory = new HSSFEventFactory()
        // process our events based on the document input stream
        factory.processEvents(req, din)
        // and our document input stream (don't want to leak these!)
        din.close()
    }
}

class CustomHSSFEventListener implements HSSFListener {

    @Override
    public void processRecord(Record record) {
        /* There are several types of record here (Beginning of the sheet record, bound sheet record, row record, number record, SSTRecord, ...
         * But we care about the RowRecord only for max columns validation purpose
        */
        if (record.getSid() == RowRecord.sid) {
            RowRecord rowRec = (RowRecord) record
            if (rowRec.getLastCol() > Constants.MAX_COLUMNS) {
                throw new IndexOutOfBoundsException()
            }
        }
    }
}