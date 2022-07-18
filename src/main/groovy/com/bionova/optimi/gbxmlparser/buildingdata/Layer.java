package com.bionova.optimi.gbxmlparser.buildingdata;

import java.util.List;

public class Layer {
  public String id;
  public List<String> materialIdRefs;

  public Layer(String id, List<String> materialIdRefs) {
    this.id = id;
    this.materialIdRefs = materialIdRefs;
  }
}
