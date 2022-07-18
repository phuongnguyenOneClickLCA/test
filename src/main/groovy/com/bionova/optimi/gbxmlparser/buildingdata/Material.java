package com.bionova.optimi.gbxmlparser.buildingdata;

public class Material {
  public String id;
  public String name;
  public Thickness thickness;

  /**
   * Store material data.
   */
  public Material(String id, String name, Thickness thickness) {
    this.id = id;
    this.name = name;
    this.thickness = thickness;
  }
}
