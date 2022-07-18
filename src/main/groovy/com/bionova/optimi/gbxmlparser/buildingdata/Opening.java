package com.bionova.optimi.gbxmlparser.buildingdata;

public class Opening {
  public String openingType;
  public String constructionIdRef;
  public String windowTypeIdRef;
  public double area;

  /**
   * Store surface opening data data.
   */
  public Opening(String openingType, String constructionIdRef, String windowTypeIdRef,
      double area) {
    this.openingType = openingType;
    this.constructionIdRef = constructionIdRef;
    this.windowTypeIdRef = windowTypeIdRef;
    this.area = area;
  }
}
