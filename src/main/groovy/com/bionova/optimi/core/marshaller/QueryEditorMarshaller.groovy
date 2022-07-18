package com.bionova.optimi.core.marshaller

import grails.web.*

/**
 * @author Pasi-Markus Mäkelä
 */
public class QueryEditorMarshaller {

    def target
    private final static ignoredList = ['id', 'class', 'active', 'domainId', 'importFile', 'importTime', 'loggerUtil']

    public String getJSON() {
        Closure jsonFormat = {
            object = {
                // Set the delegate of buildJSON to ensure that missing methods called thereby are routed to the JSONBuilder
                buildJSON.delegate = delegate
                buildJSON(target)
            }
        }

        def json = new JSONBuilder().build(jsonFormat)
        return json.toString(true)
    }

    private buildJSON = { obj ->

        obj.properties.each { propName, propValue ->

            if (!ignoredList.contains(propName) && propValue != null) {
                if (isSimple(propValue)) {
                    // It seems "propName = propValue" doesn't work when propName is dynamic so we need to
                    // set the property on the builder using this syntax instead
                    setProperty(propName, propValue)
                } else {
                    // create a nested JSON object and recursively call this function to serialize it
                    Closure nestedObject = { name, value ->
                        if (value != null) {
                            buildJSON(propValue)
                        }
                    }
                    setProperty(propName, nestedObject)
                }
            }
        }
    }

    /**
     * A simple object is one that can be set directly as the value of a JSON property, examples include strings,
     * numbers, booleans, etc.
     *
     * @param propValue
     * @return
     */
    private boolean isSimple(propValue) {
        // This is a bit simplistic as an object might very well be Serializable but have properties that we want
        // to render in JSON as a nested object. If we run into this issue, replace the test below with an test
        // for whether propValue is an instanceof Number, String, Boolean, Char, etc.
        propValue instanceof Serializable || propValue == null
    }

}
