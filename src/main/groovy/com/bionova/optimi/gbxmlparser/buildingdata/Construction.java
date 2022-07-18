package com.bionova.optimi.gbxmlparser.buildingdata;

import java.util.List;

public class Construction {
  public String id;
  public String name;
  public List<String> layerIdRefs;

  /**
   * Store construction data.
   */
  public Construction(String id, String name, List<String> layerIdRefs) {
    this.id = id;
    this.name = name;
    this.layerIdRefs = layerIdRefs;
  }
}
