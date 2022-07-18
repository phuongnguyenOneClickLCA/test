package com.bionova.optimi.gbxmlparser.buildingdata;

import java.util.List;

public class WindowType {
  public String id;
  public String name;
  public List<Glaze> glazes;

  /**
   * Store window material data.
   */
  public WindowType(String id, String name, List<Glaze> glazes) {
    this.id = id;
    this.name = name;
    this.glazes = glazes;
  }
}
