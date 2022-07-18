package com.bionova.optimi.xml.ilcd;

public class ILCDNamespacePrefixMapper extends com.sun.xml.bind.marshaller.NamespacePrefixMapper {

    private static final String COMMON_PREFIX = "common";
    private static final String COMMON_URI = "http://lca.jrc.it/ILCD/Common";

    private static final String EPD_PREFIX = "epd";
    private static final String EPD_URI = "http://www.iai.kit.edu/EPD/2013";

    private static final String PROCESS_PREFIX = "";
    private static final String PROCESS_URI = "http://lca.jrc.it/ILCD/Process";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if(COMMON_URI.equals(namespaceUri)) {
            return COMMON_PREFIX;
        } else if (PROCESS_URI.equals(namespaceUri)) {
            return PROCESS_PREFIX;
        } else if (EPD_URI.equals(namespaceUri)) {
            return EPD_PREFIX;
        }
        return "";
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { COMMON_URI, PROCESS_URI, EPD_URI };
    }

}