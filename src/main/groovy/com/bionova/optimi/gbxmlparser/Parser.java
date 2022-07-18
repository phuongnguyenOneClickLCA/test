package com.bionova.optimi.gbxmlparser;

import com.bionova.optimi.gbxmlparser.buildingdata.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.bionova.optimi.gbxmlparser.Math.area;

//TODO also I found that some of the samples from gbXML lack material definition but have
//     <CADObjectId>Basic Wall: ZMM_Interior - 1 1/4" metal stud gyp one side [155925]</CADObjectId>
//     <Name>S-133-133-I-W-504</Name>

//TODO also, can you make the error code returned differentiate between cases when
//     no materials are found because no structures are defined (ie really empty file)
//     no materials are found because materials are not defined (file has content but nothing we can read)

public class Parser {
  private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

  final double M3_IN_CU_FT = 0.0283168466;
  final double M_IN_FT = 0.3048;
  
  List<Surface> surfaces;
  Map<String, Space> spaces;
  Map<String, Construction> constructions;
  Map<String, Layer> layers;
  Map<String, Material> materials;
  Map<String, WindowType> windowTypes;

  /**
   * Returns an array of rows from the parsed building data. Should be run after
   * running the parse method.
   *
   * @param includeOpenings
   *          include opening materials
   * @param includeEmpty
   *          include empty surfaces and openings (air)
   * @param includeSpaceIds
   *          include surface adjacent space ids
   * @return array of material rows
   */
  List<String[]> outputRows(boolean includeOpenings,
      boolean includeEmpty,
      boolean includeSpaceIds) throws ParserException {

    if (surfaces == null) {
      throw new ParserException("Stream has not been parsed");
    }

    List<String[]> res = new ArrayList<String[]>();
    if (surfaces == null) {
      return res;
    }

    String[] row;
    int colNum = 7;
    if (includeSpaceIds) {
      colNum = 9;
    }

    for (Surface surface : surfaces) {
      double area = surface.area;
      String spaceNames = "";
      if (surface.spaceIdRef1 != null && !surface.spaceIdRef1.isEmpty()) {
        Space refSpace = spaces.get(surface.spaceIdRef1);

        if (refSpace == null) {
          throw new ParserException("Referenced space id not found: " + surface.spaceIdRef1);
        }
        spaceNames += refSpace.name;
        if (surface.spaceIdRef2 != null && !surface.spaceIdRef2.isEmpty()) {
          spaceNames += " - ";
        }
      }
      if (surface.spaceIdRef2 != null && !surface.spaceIdRef2.isEmpty()) {
        Space refSpace = spaces.get(surface.spaceIdRef2);

        if (refSpace == null) {
          throw new ParserException("Referenced space id not found: " + surface.spaceIdRef2);
        }
        spaceNames += refSpace.name;
      }

      for (Opening opening : surface.openings) {
        area -= opening.area;
        if (includeOpenings && !opening.openingType.equalsIgnoreCase("Air")) {
          if (opening.constructionIdRef != null) {
            Construction construction = constructions.get(opening.constructionIdRef);
            if (construction == null) {
              LOGGER.log(Level.WARNING, "Referenced construction id not found: "
                  + opening.constructionIdRef);
            } else {
              for (String layerIdRef : construction.layerIdRefs) {
                Layer layer = layers.get(layerIdRef);
                if (layer == null) {
                  LOGGER.log(Level.WARNING, "Referenced layer id not found: " + layerIdRef);
                } else {
                  for (String materialIdRef : layer.materialIdRefs) {
                    Material material = materials.get(materialIdRef);
                    if (material == null) {
                      LOGGER.log(Level.WARNING, "Referenced material id not found: " + materialIdRef);
                    } else if (!(!includeEmpty && materialIdRef.startsWith("Cavity-AIR"))) {
                      row = new String[colNum];
                      row[0] = opening.openingType;
                      row[1] = material.name;
                      if (material.thickness.units.equalsIgnoreCase("Feet")) {
                        row[2] = Double.toString(material.thickness.value * M_IN_FT * area /  M3_IN_CU_FT);
                        row[3] = "CU FT";
                      } else {
                        if (!material.thickness.units.equalsIgnoreCase("Meters")) {
                          LOGGER.log(Level.WARNING, "Unknown thickness measurement units, assuming meters");
                        }
                        row[2] = Double.toString(material.thickness.value * area);
                        row[3] = "M3";
                      }
                      row[4] = Double.toString(material.thickness.value * 1000);
                      row[5] = construction.name;
                      row[6] = spaceNames;
                      if (includeSpaceIds) {
                        row[7] = surface.spaceIdRef1;
                        row[8] = surface.spaceIdRef2;
                      }
                      res.add(row);
                    }
                  }
                }
              }
            }
          } else if (opening.windowTypeIdRef != null) {
            WindowType windowType = windowTypes.get(opening.windowTypeIdRef);
            if (windowType == null) {
              LOGGER.log(Level.WARNING, "Referenced window type id not found: "
                  + opening.windowTypeIdRef);
            } else {
              for (Glaze glaze : windowType.glazes) {
                row = new String[colNum];
                row[0] = opening.openingType;
                row[1] = glaze.name;
                if (glaze.thickness.units.equalsIgnoreCase("Feet")) {
                  row[2] = Double.toString(glaze.thickness.value * M_IN_FT * area /  M3_IN_CU_FT);
                  row[3] = "CU FT";
                } else {
                  if (!glaze.thickness.units.equalsIgnoreCase("Meters")) {
                    LOGGER.log(Level.WARNING, "Unknown thickness measurement units, assuming meters");
                  }
                  row[2] = Double.toString(glaze.thickness.value * area);
                  row[3] = "M3";
                }
                row[4] = Double.toString(glaze.thickness.value * 1000);
                row[5] = windowType.name;
                row[6] = spaceNames;
                if (includeSpaceIds) {
                  row[7] = surface.spaceIdRef1;
                  row[8] = surface.spaceIdRef2;
                }
                res.add(row);
              }
            }
          }
        }
      }

      Construction construction = constructions.get(surface.constructionIdRef);
      if (construction == null) {
        LOGGER.log(Level.WARNING, "Referenced construction id not found: "
            + surface.constructionIdRef);
      } else {
        for (String layerIdRef : construction.layerIdRefs) {
          Layer layer = layers.get(layerIdRef);
          if (layer == null) {
            LOGGER.log(Level.WARNING, "Referenced layer id not found: " + layerIdRef);
          } else {
            for (String materialIdRef : layer.materialIdRefs) {
              Material material = materials.get(materialIdRef);
              if (material == null) {
                LOGGER.log(Level.WARNING, "Referenced material id not found: " + materialIdRef);
              } else {
                if (!(!includeEmpty && materialIdRef.startsWith("Cavity-AIR"))) {
                  row = new String[colNum];
                  row[0] = surface.surfaceType;
                  row[1] = material.name;
                  if (material.thickness.units.equalsIgnoreCase("Feet")) {
                    row[2] = Double.toString(material.thickness.value * M_IN_FT * area /  M3_IN_CU_FT);
                    row[3] = "CU FT";
                  } else {
                    if (!material.thickness.units.equalsIgnoreCase("Meters")) {
                      LOGGER.log(Level.WARNING, "Unknown thickness measurement units, assuming meters");
                    }
                    row[2] = Double.toString(material.thickness.value * area);
                    row[3] = "M3";
                  }
                  row[4] = Double.toString(material.thickness.value * 1000);
                  row[5] = construction.name;
                  row[6] = spaceNames;
                  if (includeSpaceIds) {
                    row[7] = surface.spaceIdRef1;
                    row[8] = surface.spaceIdRef2;
                  }
                  res.add(row);
                }
              }
            }
          }
        }
      }
    }

    return res;
  }

  /**
   * Run outputXLS with default parameters. Include openings, exclude empty
   * surfaces and openings, exclude space ids.
   */
  void outputXls(OutputStream outs) throws ParserException, IOException {
    outputXls(outs, true, false, false);
  }

  /**
   * Generate XLS formatted output into OutputStream.
   */
  void outputXls(OutputStream outs,
      boolean includeOpenings, boolean includeEmpty, boolean includeSpaceIds)
      throws ParserException, IOException {

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Materials");

    HSSFRow rowhead = sheet.createRow(0);
    rowhead.createCell(0).setCellValue("CLASS");
    rowhead.createCell(1).setCellValue("IFCMATERIAL");
    rowhead.createCell(2).setCellValue("QUANTITY");
    rowhead.createCell(3).setCellValue("QTY_TYPE");
    rowhead.createCell(4).setCellValue("THICKNESS_MM");
    rowhead.createCell(5).setCellValue("Comment");
    rowhead.createCell(6).setCellValue("Spaces");

    if (includeSpaceIds) {
      rowhead.createCell(7).setCellValue("AdjacentSpaceId_1");
      rowhead.createCell(8).setCellValue("AdjacentSpaceId_2");
    }

    int rownum = 1;
    int cellnum;
    rownum = 1;
    for (String[] row : outputRows(includeOpenings, includeEmpty, includeSpaceIds)) {
      HSSFRow xlsRow = sheet.createRow(rownum++);
      cellnum = 0;
      for (String column : row) {
        xlsRow.createCell(cellnum++).setCellValue(column);
      }
    }

    workbook.write(outs);
  }

  /**
   * Run outputCSV with default parameters. Include openings, exclude empty
   * surfaces and openings, exclude space ids.
   */
  void outputCsv(OutputStream outs) throws ParserException, IOException {
    outputCsv(outs, true, false, false);
  }

  /**
   * Generate CSV formatted output into OutputStream.
   */
  void outputCsv(OutputStream outs,
      boolean includeOpenings, boolean includeEmpty, boolean includeSpaceIds)
      throws ParserException, IOException {

    StringBuilder sb = new StringBuilder();
    sb.append("CLASS,IFCMATERIAL,QUANTITY,QTY_TYPE,THICKNESS_MM,Comment,Spaces");
    if (includeSpaceIds) {
      sb.append(",AdjacentSpaceId_1,AdjacentSpaceId_2");
    }
    sb.append("\n");

    for (String[] row : outputRows(includeOpenings, includeEmpty, includeSpaceIds)) {
      for (String column : row) {
        sb.append(column);
        sb.append(",");
      }
      sb.append("\n");
    }

    outs.write(sb.toString().getBytes());
  }

  /**
   * Try to extract an attribute from an xml opening tag.
   */
  String getAttribute(StartElement elem, String name) throws ParserException {
    Attribute attr = elem.getAttributeByName(new QName(name));

    if (attr == null) {
      Location loc = elem.getLocation();
      throw new ParserException("Line " + loc.getLineNumber()
          + ": Expected attribute " + name + " not found");
    }
    return attr.getValue();
  }

  /**
   * Check if an xml tag is an opening tag.
   */
  StartElement getStartElement(XMLEvent event, String name) {
    if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
      StartElement elem = event.asStartElement();
      String ename = elem.getName().getLocalPart();

      if (ename.equalsIgnoreCase(name)) {
        return elem;
      }
    }
    return null;
  }

  /**
   * Extract textual content of an xml tag.
   */
  String getCharacters(XMLEvent event) {
    if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
      return event.asCharacters().getData();
    }
    return null;
  }

  /**
   * Check if an xml tag is a closing tag.
   */
  boolean isEndElement(XMLEvent event, String name) {
    if (event.getEventType() == XMLStreamConstants.END_ELEMENT) {
      String ename = event.asEndElement().getName().getLocalPart();
      if (ename.equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Try to parse an xml tag as a gbXML coordinate.
   */
  Double parseCoordinate(XMLEvent event, XMLEventReader reader)
      throws XMLStreamException, ParserException {
    StartElement elem = getStartElement(event, "Coordinate");
    if (elem == null) {
      return null;
    }

    event = reader.nextEvent();
    String coordinate = getCharacters(event);

    if (coordinate != null) {
      try {
        return Double.parseDouble(coordinate);
      } catch (NumberFormatException nume) {
        Location loc = event.getLocation();
        throw new ParserException("Couldn't parse number on line: ", loc.getLineNumber());
      }
    }

    return null;
  }

  /**
   * Try to parse an xml tag as a gbXML cartesian point.
   */
  double[] parseCartesianPoint(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "CartesianPoint");
    if (elem == null) {
      return null;
    }

    double[] point = { 0, 0, 0 };

    int axis = 0;
    while (reader.hasNext() && !isEndElement(event, "CartesianPoint")) {
      event = reader.nextEvent();

      Double coordinate = parseCoordinate(event, reader);

      if (coordinate != null) {
        ++axis;
        if (axis > 3) {
          Location loc = event.getLocation();
          throw new ParserException("Unexpected 4th Coordinate in CartesianPoint",
              loc.getLineNumber());
        }
        point[axis - 1] = coordinate.doubleValue();
      }
    }

    if (axis == 3) {
      return point;
    } else {
      Location loc = event.getLocation();
      throw new ParserException("Not enough Coordinates in CartesianPoint", loc.getLineNumber());
    }
  }

  /**
   * Try to parse an xml tag as a gbXML polygon, given by a coordinate list.
   */
  List<double[]> parsePolyLoop(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "PolyLoop");
    if (elem == null) {
      return null;
    }

    List<double[]> coordinates = new ArrayList<double[]>();

    while (reader.hasNext() && !isEndElement(event, "PolyLoop")) {
      event = reader.nextEvent();

      double[] point = parseCartesianPoint(event, reader);
      if (point != null) {
        coordinates.add(point);
      }
    }

    if (coordinates.size() < 3) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
          + ": Less than three points in PolyLoop.");
    }

    return coordinates;
  }

  /**
   * Try to parse an xml tag as a gbXML polygon, given by a coordinate list.
   */
  List<double[]> parsePlanarGeometry(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "PlanarGeometry");
    if (elem == null) {
      return null;
    }

    List<double[]> coordinates = null;

    while (reader.hasNext() && !isEndElement(event, "PlanarGeometry")) {
      event = reader.nextEvent();

      List<double[]> newCoordinates = parsePolyLoop(event, reader);
      if (newCoordinates != null) {
        if (coordinates != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
              + ": More than one PolyLoop in PlanarGeometry");
        }
        
        coordinates = newCoordinates;
      }
    }

    if (coordinates == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
          + ": No PolyLoop found in PlanarGeometry");
    }
 
    return coordinates;
  }

  /**
   * Try to parse an xml tag as a surface adjacent space.
   */
  String parseAdjacentSpaceId(XMLEvent event) throws ParserException {
    StartElement elem = getStartElement(event, "AdjacentSpaceId");
    if (elem == null) {
      return null;
    }

    return getAttribute(elem, "spaceIdRef");
  }

  /**
   * Try to parse an xml tag as a gbXML surface.
   */
  Surface parseSurface(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Surface");
    if (elem == null) {
      return null;
    }

    String id = getAttribute(elem, "id");
    String surfaceType = getAttribute(elem, "surfaceType");
    String constructionIdRef;
    try {
      constructionIdRef = getAttribute(elem, "constructionIdRef");
    } catch(ParserException parserException) {
      constructionIdRef = null;
    }
    String spaceIdRef1 = null;
    String spaceIdRef2 = null;
    List<Opening> openings = new ArrayList<Opening>();
    List<double[]> coordinates = null;

    while (reader.hasNext() && !isEndElement(event, "Surface")) {
      event = reader.nextEvent();

      String spaceId = parseAdjacentSpaceId(event);
      if (spaceId != null) {
        
        if (spaceIdRef1 == null) {
          spaceIdRef1 = spaceId;
        } else if (spaceIdRef2 == null) {
          spaceIdRef2 = spaceId;
        } else {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
              + ": More than two adjacent spaces found for a surface");
        }
      }

      Opening newOpening = parseOpening(event, reader);
      if (newOpening != null) {
        openings.add(newOpening);
      }

      List<double[]> newCoordinates = parsePlanarGeometry(event, reader);
      if (newCoordinates != null) {
        if (coordinates != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
                + ": More than one set of coordinates found for surface");
        }
        coordinates = newCoordinates;
      }
    }

    if (coordinates == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + "No coordinates found for surface");
      return null;
    }

    // Only return new surface if we have materials tied to it
    if (constructionIdRef != null) {
      return new Surface(id, surfaceType, constructionIdRef,
          area(coordinates), spaceIdRef1, spaceIdRef2, openings);
    } else {
      return null;
    }
  }

  /**
   * Try to parse an xml tag as a gbXML opening.
   */
  Opening parseOpening(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Opening");
    if (elem == null) {
      return null;
    }

    String constructionIdRef;
    String windowTypeIdRef;
    
    try {
      windowTypeIdRef = getAttribute(elem, "windowTypeIdRef");
    } catch (ParserException parserException) {
      windowTypeIdRef = null;
    }
    try {
      constructionIdRef = getAttribute(elem, "constructionIdRef");
    } catch (ParserException parserException) {
      constructionIdRef = null;
    }

    final String openingType = getAttribute(elem, "openingType");
    List<double[]> coordinates = null;

    while (reader.hasNext() && !isEndElement(event, "Opening")) {
      event = reader.nextEvent();

      List<double[]> newCoordinates = parsePlanarGeometry(event, reader);
      if (newCoordinates != null) {
        if (coordinates != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
                + ": More than one set of coordinates found for opening");
        }
        coordinates = newCoordinates;
      }
    }

    if (coordinates == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": No coordinates found for opening");
      return null;
    }
    
    if (!openingType.equalsIgnoreCase("Air")
          && constructionIdRef == null && windowTypeIdRef == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": No construction or window type reference found for opening");
    }

    return new Opening(openingType, constructionIdRef, windowTypeIdRef, area(coordinates));
  }

  /**
   * Try to parse an xml tag as a name.
   */
  String parseName(XMLEvent event, XMLEventReader reader) throws XMLStreamException {
    StartElement elem = getStartElement(event, "name");
    if (elem == null) {
      return null;
    }

    String name = null;

    while (reader.hasNext() && !isEndElement(event, "Name")) {
      event = reader.nextEvent();
      String newName = getCharacters(event);
      if (newName != null) {
        name = newName;
      }
    }

    return name;
  }

  /**
   * Try to parse an xml tag as a gbXML space.
   */
  Space parseSpace(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Space");
    if (elem == null) {
      return null;
    }

    String id = getAttribute(elem, "id");
    String name = null;

    while (reader.hasNext() && !isEndElement(event, "Space")) {
      event = reader.nextEvent();
      String newName = parseName(event, reader);
      if (newName != null) {
        name = newName;
      }
    }

    if (name == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": name not found for space");
    }

    return new Space(id, name);
  }

  /**
   * Try to parse an xml tag as a gbXML layer.
   */
  String parseLayerIdRef(XMLEvent event) throws ParserException {
    StartElement elem = getStartElement(event, "LayerId");
    if (elem == null) {
      return null;
    }

    return getAttribute(elem, "layerIdRef");
  }

  /**
   * Try to parse an xml tag as a gbXML construction.
   */
  Construction parseConstruction(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Construction");
    if (elem == null) {
      return null;
    }

    final String id = getAttribute(elem, "id");
    String name = null;
    List<String> layerIdRefs = new ArrayList<String>();

    while (reader.hasNext() && !isEndElement(event, "Construction")) {
      event = reader.nextEvent();

      String newLayerIdRef = parseLayerIdRef(event);
      if (newLayerIdRef != null) {
        layerIdRefs.add(newLayerIdRef);
      }

      String newName = parseName(event, reader);
      if (newName != null) {
        if (name != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
              + ": more than one name found for construction");
        }
        name = newName;
      }
    }

    if (name == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": name not found for construction");
    }

    if (layerIdRefs.isEmpty()) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no layer references found for construction");
    }

    return new Construction(id, name, layerIdRefs);
  }

  /**
   * Try to parse an xml tag as a gbXML material.
   */
  String parseMaterialIdRef(XMLEvent event) throws ParserException {
    StartElement elem = getStartElement(event, "MaterialId");
    if (elem == null) {
      return null;
    }

    return getAttribute(elem, "materialIdRef");
  }

  /**
   * Try to parse an xml tag as a gbXML layer.
   */
  Layer parseLayer(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Layer");
    if (elem == null) {
      return null;
    }

    String id = getAttribute(elem, "id");
    List<String> materialIdRefs = new ArrayList<String>();

    while (reader.hasNext() && !isEndElement(event, "Layer")) {
      event = reader.nextEvent();

      String newMaterialIdRef = parseMaterialIdRef(event);
      if (newMaterialIdRef != null) {
        materialIdRefs.add(newMaterialIdRef);
      }
    }

    if (materialIdRefs.isEmpty()) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no material references found for layer");
    }
    return new Layer(id, materialIdRefs);
  }

  /**
   * Try to parse an xml tag as a thickness annotation.
   */
  Thickness parseThickness(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Thickness");
    if (elem == null) {
      return null;
    }

    String unit = getAttribute(elem, "unit");
    Double thickness = null;

    while (reader.hasNext() && !isEndElement(event, "Thickness")) {
      event = reader.nextEvent();

      String thicknessStr = getCharacters(event);
      if (thicknessStr != null) {
        try {
          thickness = Double.parseDouble(thicknessStr);
        } catch (NumberFormatException nume) {
          Location loc = event.getLocation();
          throw new ParserException("Couldn't parse number on line: ", loc.getLineNumber());
        }
      }
    }

    if (thickness == null) {
      return null;
    }

    return new Thickness(thickness.doubleValue(), unit);
  }

  /**
   * Try to parse an xml tag as a gbXML material.
   */
  Material parseMaterial(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Material");
    if (elem == null) {
      return null;
    }

    final String id = getAttribute(elem, "id");
    String name = null;
    Thickness thickness = null;

    while (reader.hasNext() && !isEndElement(event, "Material")) {
      event = reader.nextEvent();
      String newName = parseName(event, reader);
      if (newName != null) {
        name = newName;
      }

      Thickness newThickness = parseThickness(event, reader);
      if (newThickness != null) {
        if (thickness != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
                + ": more than one thickness value found for material");
        }
        thickness = newThickness;
      }
    }

    if (name == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no name found for material");
    }
    
    // We dont return material if no thickness is known
    if (thickness == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no thickness value found for material");
      return null;
    }
    
    return new Material(id, name, thickness);
  }

  /**
   * Try to parse an xml tag as a window material.
   */
  Glaze parseGlaze(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "Glaze");
    if (elem == null) {
      return null;
    }

    String name = null;
    Thickness thickness = null;

    while (reader.hasNext() && !isEndElement(event, "Glaze")) {
      event = reader.nextEvent();

      String newName = parseName(event, reader);
      if (newName != null) {
        if (name != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
                + ": more than one name found for glaze material");
        }
        name = newName;
      }

      Thickness newThickness = parseThickness(event, reader);
      if (newThickness != null) {
        if (thickness != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
                + ": more than one thickness value found for glaze material");
        }
        thickness = newThickness;
      }
    }

    if (name == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no name found for glaze material");
    }
    
    if (thickness == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no thickness value found for glaze material");
    }

    return new Glaze(name, thickness);
  }

  /**
   * Try to parse an xml tag as a gbXML window type.
   */
  WindowType parseWindowType(XMLEvent event, XMLEventReader reader)
      throws ParserException, XMLStreamException {
    StartElement elem = getStartElement(event, "WindowType");
    if (elem == null) {
      return null;
    }

    final String id = getAttribute(elem, "id");
    String name = null;
    List<Glaze> glazes = new ArrayList<Glaze>();

    while (reader.hasNext() && !isEndElement(event, "WindowType")) {
      event = reader.nextEvent();

      String newName = parseName(event, reader);
      if (newName != null) {
        if (name != null) {
          Location loc = event.getLocation();
          LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
                + ": more than one name found for window type");
        }
        name = newName;
      }

      Glaze newGlaze = parseGlaze(event, reader);
      if (newGlaze != null) {
        glazes.add(newGlaze);
      }
    }

    if (name == null) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no name found for window type");
    }
    
    if (glazes.isEmpty()) {
      Location loc = event.getLocation();
      LOGGER.log(Level.WARNING, "Line " + loc.getLineNumber()
            + ": no glaze materials found for window type");
    }

    return new WindowType(id, name, glazes);
  }

  /**
   * Try to extract gbXML data from an input stream containing xml.
   */
  public void parse(InputStream ins) throws ParserException {
    surfaces = new ArrayList<Surface>();
    spaces = new HashMap<String, Space>();
    constructions = new HashMap<String, Construction>();
    layers = new HashMap<String, Layer>();
    materials = new HashMap<String, Material>();
    windowTypes = new HashMap<String, WindowType>();

    try {
      XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
      XMLEventReader reader = xmlInFact.createXMLEventReader(ins);
      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();

        Space newSpace = parseSpace(event, reader);
        if (newSpace != null) {
          spaces.put(newSpace.id, newSpace);
        }

        Surface newSurface = parseSurface(event, reader);
        if (newSurface != null) {
          surfaces.add(newSurface);
        }

        Construction newConstruction = parseConstruction(event, reader);
        if (newConstruction != null) {
          constructions.put(newConstruction.id, newConstruction);
        }

        Layer newLayer = parseLayer(event, reader);
        if (newLayer != null) {
          layers.put(newLayer.id, newLayer);
        }

        Material newMaterial = parseMaterial(event, reader);
        if (newMaterial != null) {
          materials.put(newMaterial.id, newMaterial);
        }

        WindowType newWindowType = parseWindowType(event, reader);
        if (newWindowType != null) {
          windowTypes.put(newWindowType.id, newWindowType);
        }
      }

      if (surfaces.isEmpty()) {
        LOGGER.log(Level.WARNING, "No surfaces found");
      }
    } catch (XMLStreamException exc) {
      throw new ParserException(exc.toString());
    }
  }
}
