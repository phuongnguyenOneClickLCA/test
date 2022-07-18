package com.bionova.optimi.gbxmlparser.buildingdata;

import java.util.List;

public class Surface {
  public String id;
  public String surfaceType;
  public String constructionIdRef;
  public String spaceIdRef1;
  public String spaceIdRef2;
  public List<Opening> openings;
  public double area;

  /**
   * Store surface data.
   */
  public Surface(String id, String surfaceType,
      String constructionIdRef, double area,
      String spaceIdRef1, String spaceIdRef2, List<Opening> openings) {
    this.id = id;
    this.surfaceType = surfaceType;
    this.constructionIdRef = constructionIdRef;
    this.area = area;
    this.spaceIdRef1 = spaceIdRef1;
    this.spaceIdRef2 = spaceIdRef2;
    this.openings = openings;
  }
}
