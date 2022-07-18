package com.bionova.optimi.gbxmlparser;

import org.apache.commons.lang.StringUtils;

/**
 * @author Pasi-Markus Mäkelä
 */
public class ParserExceptionUtil {

    public static String getExceptionAsMessage(ParserException parserException) {
        String message = "";

        if (parserException != null) {
            String exceptionMessage = parserException.getMessage();

            if (StringUtils.isNotBlank(exceptionMessage)) {
                exceptionMessage = exceptionMessage.toLowerCase();

                if (exceptionMessage.contains("stream has not been parsed")) {
                    message = "Input file could not be read. Please verify the file is correct and try again.";
                } else if (exceptionMessage.contains("referenced space id not found")) {
                    message = "Input gbXML file's space identifiers could not be resolved. Please contact support.";
                } else if (exceptionMessage.contains("expected attribute")) {
                    message = "Input gbXML file's objects do not contain required attributes. Please contact support.";
                } else if (exceptionMessage.contains("couldn't parse number on line")) {
                    message = "Input gbXML file's numbers could not be parsed. Please contact support.";
                } else if (exceptionMessage.contains("not enough coordinates in cartesianpoint")) {
                    message = "Input gbXML file's coordinates could not be parsed. Please contact support.";
                } else {
                    message = "Unknown error in parsing gbXML file. Please contact support.";
                }
            }
        }
        return message;
    }
}
