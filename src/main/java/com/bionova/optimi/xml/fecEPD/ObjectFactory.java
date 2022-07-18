
package com.bionova.optimi.xml.fecEPD;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bionova.optimi.xml.fecEPD package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bionova.optimi.xml.fecEPD
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EPDC }
     * 
     */
    public EPDC createEPDC() {
        return new EPDC();
    }

    /**
     * Create an instance of {@link EPDC.Indicators }
     * 
     */
    public EPDC.Indicators createEPDCIndicators() {
        return new EPDC.Indicators();
    }

    /**
     * Create an instance of {@link EPDC.Indicators.Indicator }
     * 
     */
    public EPDC.Indicators.Indicator createEPDCIndicatorsIndicator() {
        return new EPDC.Indicators.Indicator();
    }

    /**
     * Create an instance of {@link EPDC.Indicators.Indicator.Phases }
     * 
     */
    public EPDC.Indicators.Indicator.Phases createEPDCIndicatorsIndicatorPhases() {
        return new EPDC.Indicators.Indicator.Phases();
    }

    /**
     * Create an instance of {@link EPDC.Parameters }
     * 
     */
    public EPDC.Parameters createEPDCParameters() {
        return new EPDC.Parameters();
    }

    /**
     * Create an instance of {@link EPDC.Indicators.Indicator.Phases.Phase }
     * 
     */
    public EPDC.Indicators.Indicator.Phases.Phase createEPDCIndicatorsIndicatorPhasesPhase() {
        return new EPDC.Indicators.Indicator.Phases.Phase();
    }

    /**
     * Create an instance of {@link EPDC.Parameters.Parameter }
     * 
     */
    public EPDC.Parameters.Parameter createEPDCParametersParameter() {
        return new EPDC.Parameters.Parameter();
    }

}
