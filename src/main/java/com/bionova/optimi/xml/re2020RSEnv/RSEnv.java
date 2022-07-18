
package com.bionova.optimi.xml.re2020RSEnv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="entree_projet">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="batiment" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="nom" type="{}p_string500"/>
 *                             &lt;element name="sref">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="zone" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="usage">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="sref">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="scombles">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="contributeur">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="composant">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="lot" maxOccurs="13" minOccurs="9">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;choice>
 *                                                                     &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
 *                                                                     &lt;element name="sous_lot" maxOccurs="unbounded">
 *                                                                       &lt;complexType>
 *                                                                         &lt;complexContent>
 *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                             &lt;sequence>
 *                                                                               &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
 *                                                                             &lt;/sequence>
 *                                                                             &lt;attribute name="ref" use="required">
 *                                                                               &lt;simpleType>
 *                                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                                   &lt;enumeration value="1.1"/>
 *                                                                                   &lt;enumeration value="1.2"/>
 *                                                                                   &lt;enumeration value="1.3"/>
 *                                                                                   &lt;enumeration value="2.1"/>
 *                                                                                   &lt;enumeration value="2.2"/>
 *                                                                                   &lt;enumeration value="2.3"/>
 *                                                                                   &lt;enumeration value="3.1"/>
 *                                                                                   &lt;enumeration value="3.2"/>
 *                                                                                   &lt;enumeration value="3.3"/>
 *                                                                                   &lt;enumeration value="3.4"/>
 *                                                                                   &lt;enumeration value="3.5"/>
 *                                                                                   &lt;enumeration value="3.6"/>
 *                                                                                   &lt;enumeration value="3.7"/>
 *                                                                                   &lt;enumeration value="3.8"/>
 *                                                                                   &lt;enumeration value="4.1"/>
 *                                                                                   &lt;enumeration value="4.2"/>
 *                                                                                   &lt;enumeration value="4.3"/>
 *                                                                                   &lt;enumeration value="5.1"/>
 *                                                                                   &lt;enumeration value="5.2"/>
 *                                                                                   &lt;enumeration value="5.3"/>
 *                                                                                   &lt;enumeration value="5.4"/>
 *                                                                                   &lt;enumeration value="5.5"/>
 *                                                                                   &lt;enumeration value="6.1"/>
 *                                                                                   &lt;enumeration value="6.2"/>
 *                                                                                   &lt;enumeration value="6.3"/>
 *                                                                                   &lt;enumeration value="7.1"/>
 *                                                                                   &lt;enumeration value="7.2"/>
 *                                                                                   &lt;enumeration value="7.3"/>
 *                                                                                   &lt;enumeration value="8.1"/>
 *                                                                                   &lt;enumeration value="8.2"/>
 *                                                                                   &lt;enumeration value="8.3"/>
 *                                                                                   &lt;enumeration value="8.4"/>
 *                                                                                   &lt;enumeration value="8.5"/>
 *                                                                                   &lt;enumeration value="8.6"/>
 *                                                                                   &lt;enumeration value="8.7"/>
 *                                                                                   &lt;enumeration value="9.1"/>
 *                                                                                   &lt;enumeration value="9.2"/>
 *                                                                                   &lt;enumeration value="10.1"/>
 *                                                                                   &lt;enumeration value="10.2"/>
 *                                                                                   &lt;enumeration value="10.3"/>
 *                                                                                   &lt;enumeration value="10.4"/>
 *                                                                                   &lt;enumeration value="10.5"/>
 *                                                                                   &lt;enumeration value="10.6"/>
 *                                                                                   &lt;enumeration value="11.1"/>
 *                                                                                   &lt;enumeration value="11.2"/>
 *                                                                                   &lt;enumeration value="11.3"/>
 *                                                                                   &lt;enumeration value="12.1"/>
 *                                                                                   &lt;enumeration value="13.1"/>
 *                                                                                 &lt;/restriction>
 *                                                                               &lt;/simpleType>
 *                                                                             &lt;/attribute>
 *                                                                           &lt;/restriction>
 *                                                                         &lt;/complexContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/choice>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                         &lt;enumeration value="4"/>
 *                                                                         &lt;enumeration value="5"/>
 *                                                                         &lt;enumeration value="6"/>
 *                                                                         &lt;enumeration value="7"/>
 *                                                                         &lt;enumeration value="8"/>
 *                                                                         &lt;enumeration value="9"/>
 *                                                                         &lt;enumeration value="10"/>
 *                                                                         &lt;enumeration value="11"/>
 *                                                                         &lt;enumeration value="12"/>
 *                                                                         &lt;enumeration value="13"/>
 *                                                                         &lt;enumeration value="14"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="energie">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="sous_contributeur" maxOccurs="8">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                         &lt;enumeration value="4"/>
 *                                                                         &lt;enumeration value="5"/>
 *                                                                         &lt;enumeration value="6"/>
 *                                                                         &lt;enumeration value="7"/>
 *                                                                         &lt;enumeration value="8"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="eau">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="sous_contributeur" maxOccurs="3">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="chantier">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="sous_contributeur" maxOccurs="4">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;choice>
 *                                                                     &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
 *                                                                     &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
 *                                                                   &lt;/choice>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                         &lt;enumeration value="4"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="n_occ">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="nb_logement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
 *                                       &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="emprise_au_sol">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minExclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="periode_reference" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="duree_chantier">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minExclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="parcelle">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="contributeur" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="composant" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="eau" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="chantier" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="surface_parcelle" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="surface_arrosee" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="surface_veg" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="surface_imper" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="date_etude" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="reseau" maxOccurs="2" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="identifiant">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;whiteSpace value="collapse"/>
 *                                   &lt;pattern value="[0-9]{4}[CF]"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="1"/>
 *                                   &lt;enumeration value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nom" type="{}p_string500"/>
 *                             &lt;element name="localisation" type="{}p_string500"/>
 *                             &lt;element name="contenu_co2">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/all>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="sortie_projet">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="batiment" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="contributeur">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="composant">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                 &lt;element name="lot" maxOccurs="13" minOccurs="9">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                           &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;all>
 *                                                                     &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                                     &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                                     &lt;element name="stock_c">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="udd">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="ic">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="ic_ded">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/all>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                         &lt;enumeration value="1.1"/>
 *                                                                         &lt;enumeration value="1.2"/>
 *                                                                         &lt;enumeration value="1.3"/>
 *                                                                         &lt;enumeration value="2.1"/>
 *                                                                         &lt;enumeration value="2.2"/>
 *                                                                         &lt;enumeration value="2.3"/>
 *                                                                         &lt;enumeration value="3.1"/>
 *                                                                         &lt;enumeration value="3.2"/>
 *                                                                         &lt;enumeration value="3.3"/>
 *                                                                         &lt;enumeration value="3.4"/>
 *                                                                         &lt;enumeration value="3.5"/>
 *                                                                         &lt;enumeration value="3.6"/>
 *                                                                         &lt;enumeration value="3.7"/>
 *                                                                         &lt;enumeration value="3.8"/>
 *                                                                         &lt;enumeration value="4.1"/>
 *                                                                         &lt;enumeration value="4.2"/>
 *                                                                         &lt;enumeration value="4.3"/>
 *                                                                         &lt;enumeration value="5.1"/>
 *                                                                         &lt;enumeration value="5.2"/>
 *                                                                         &lt;enumeration value="5.3"/>
 *                                                                         &lt;enumeration value="5.4"/>
 *                                                                         &lt;enumeration value="5.5"/>
 *                                                                         &lt;enumeration value="6.1"/>
 *                                                                         &lt;enumeration value="6.2"/>
 *                                                                         &lt;enumeration value="6.3"/>
 *                                                                         &lt;enumeration value="7.1"/>
 *                                                                         &lt;enumeration value="7.2"/>
 *                                                                         &lt;enumeration value="7.3"/>
 *                                                                         &lt;enumeration value="8.1"/>
 *                                                                         &lt;enumeration value="8.2"/>
 *                                                                         &lt;enumeration value="8.3"/>
 *                                                                         &lt;enumeration value="8.4"/>
 *                                                                         &lt;enumeration value="8.5"/>
 *                                                                         &lt;enumeration value="8.6"/>
 *                                                                         &lt;enumeration value="8.7"/>
 *                                                                         &lt;enumeration value="9.1"/>
 *                                                                         &lt;enumeration value="9.2"/>
 *                                                                         &lt;enumeration value="10.1"/>
 *                                                                         &lt;enumeration value="10.2"/>
 *                                                                         &lt;enumeration value="10.3"/>
 *                                                                         &lt;enumeration value="10.4"/>
 *                                                                         &lt;enumeration value="10.5"/>
 *                                                                         &lt;enumeration value="10.6"/>
 *                                                                         &lt;enumeration value="11.1"/>
 *                                                                         &lt;enumeration value="11.2"/>
 *                                                                         &lt;enumeration value="11.3"/>
 *                                                                         &lt;enumeration value="12.1"/>
 *                                                                         &lt;enumeration value="13.1"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="stock_c">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="udd">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="ic">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="ic_ded">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="ref" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                               &lt;enumeration value="1"/>
 *                                                               &lt;enumeration value="2"/>
 *                                                               &lt;enumeration value="3"/>
 *                                                               &lt;enumeration value="4"/>
 *                                                               &lt;enumeration value="5"/>
 *                                                               &lt;enumeration value="6"/>
 *                                                               &lt;enumeration value="7"/>
 *                                                               &lt;enumeration value="8"/>
 *                                                               &lt;enumeration value="9"/>
 *                                                               &lt;enumeration value="10"/>
 *                                                               &lt;enumeration value="11"/>
 *                                                               &lt;enumeration value="12"/>
 *                                                               &lt;enumeration value="13"/>
 *                                                               &lt;enumeration value="14"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="stock_c">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="udd">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="energie">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                 &lt;element name="sous_contributeur" maxOccurs="8">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;all>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                         &lt;/all>
 *                                                         &lt;attribute name="ref" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                               &lt;enumeration value="1"/>
 *                                                               &lt;enumeration value="2"/>
 *                                                               &lt;enumeration value="3"/>
 *                                                               &lt;enumeration value="4"/>
 *                                                               &lt;enumeration value="5"/>
 *                                                               &lt;enumeration value="6"/>
 *                                                               &lt;enumeration value="7"/>
 *                                                               &lt;enumeration value="8"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="eau">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                 &lt;element name="sous_contributeur" maxOccurs="3">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;all>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                         &lt;/all>
 *                                                         &lt;attribute name="ref" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                               &lt;enumeration value="1"/>
 *                                                               &lt;enumeration value="2"/>
 *                                                               &lt;enumeration value="3"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="chantier">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                 &lt;element name="sous_contributeur" maxOccurs="4">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;all>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                         &lt;/all>
 *                                                         &lt;attribute name="ref" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                               &lt;enumeration value="1"/>
 *                                                               &lt;enumeration value="2"/>
 *                                                               &lt;enumeration value="3"/>
 *                                                               &lt;enumeration value="4"/>
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                             &lt;element name="indicateur_perf_env">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_batiment" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_batiment_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="stock_c_batiment">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="stock_c_parcelle">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="udd">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="zone" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="contributeur">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="composant">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                           &lt;element name="lot" maxOccurs="13" minOccurs="9">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                                     &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                                     &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;complexContent>
 *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                             &lt;all>
 *                                                                               &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                                               &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                                               &lt;element name="stock_c">
 *                                                                                 &lt;simpleType>
 *                                                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                                   &lt;/restriction>
 *                                                                                 &lt;/simpleType>
 *                                                                               &lt;/element>
 *                                                                               &lt;element name="udd">
 *                                                                                 &lt;simpleType>
 *                                                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                                   &lt;/restriction>
 *                                                                                 &lt;/simpleType>
 *                                                                               &lt;/element>
 *                                                                               &lt;element name="ic">
 *                                                                                 &lt;simpleType>
 *                                                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                                   &lt;/restriction>
 *                                                                                 &lt;/simpleType>
 *                                                                               &lt;/element>
 *                                                                               &lt;element name="ic_ded">
 *                                                                                 &lt;simpleType>
 *                                                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                                   &lt;/restriction>
 *                                                                                 &lt;/simpleType>
 *                                                                               &lt;/element>
 *                                                                             &lt;/all>
 *                                                                             &lt;attribute name="ref" use="required">
 *                                                                               &lt;simpleType>
 *                                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                                   &lt;enumeration value="1.1"/>
 *                                                                                   &lt;enumeration value="1.2"/>
 *                                                                                   &lt;enumeration value="1.3"/>
 *                                                                                   &lt;enumeration value="2.1"/>
 *                                                                                   &lt;enumeration value="2.2"/>
 *                                                                                   &lt;enumeration value="2.3"/>
 *                                                                                   &lt;enumeration value="3.1"/>
 *                                                                                   &lt;enumeration value="3.2"/>
 *                                                                                   &lt;enumeration value="3.3"/>
 *                                                                                   &lt;enumeration value="3.4"/>
 *                                                                                   &lt;enumeration value="3.5"/>
 *                                                                                   &lt;enumeration value="3.6"/>
 *                                                                                   &lt;enumeration value="3.7"/>
 *                                                                                   &lt;enumeration value="3.8"/>
 *                                                                                   &lt;enumeration value="4.1"/>
 *                                                                                   &lt;enumeration value="4.2"/>
 *                                                                                   &lt;enumeration value="4.3"/>
 *                                                                                   &lt;enumeration value="5.1"/>
 *                                                                                   &lt;enumeration value="5.2"/>
 *                                                                                   &lt;enumeration value="5.3"/>
 *                                                                                   &lt;enumeration value="5.4"/>
 *                                                                                   &lt;enumeration value="5.5"/>
 *                                                                                   &lt;enumeration value="6.1"/>
 *                                                                                   &lt;enumeration value="6.2"/>
 *                                                                                   &lt;enumeration value="6.3"/>
 *                                                                                   &lt;enumeration value="7.1"/>
 *                                                                                   &lt;enumeration value="7.2"/>
 *                                                                                   &lt;enumeration value="7.3"/>
 *                                                                                   &lt;enumeration value="8.1"/>
 *                                                                                   &lt;enumeration value="8.2"/>
 *                                                                                   &lt;enumeration value="8.3"/>
 *                                                                                   &lt;enumeration value="8.4"/>
 *                                                                                   &lt;enumeration value="8.5"/>
 *                                                                                   &lt;enumeration value="8.6"/>
 *                                                                                   &lt;enumeration value="8.7"/>
 *                                                                                   &lt;enumeration value="9.1"/>
 *                                                                                   &lt;enumeration value="9.2"/>
 *                                                                                   &lt;enumeration value="10.1"/>
 *                                                                                   &lt;enumeration value="10.2"/>
 *                                                                                   &lt;enumeration value="10.3"/>
 *                                                                                   &lt;enumeration value="10.4"/>
 *                                                                                   &lt;enumeration value="10.5"/>
 *                                                                                   &lt;enumeration value="10.6"/>
 *                                                                                   &lt;enumeration value="11.1"/>
 *                                                                                   &lt;enumeration value="11.2"/>
 *                                                                                   &lt;enumeration value="11.3"/>
 *                                                                                   &lt;enumeration value="12.1"/>
 *                                                                                   &lt;enumeration value="13.1"/>
 *                                                                                 &lt;/restriction>
 *                                                                               &lt;/simpleType>
 *                                                                             &lt;/attribute>
 *                                                                           &lt;/restriction>
 *                                                                         &lt;/complexContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="stock_c">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="udd">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="ic">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="ic_ded">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                         &lt;enumeration value="4"/>
 *                                                                         &lt;enumeration value="5"/>
 *                                                                         &lt;enumeration value="6"/>
 *                                                                         &lt;enumeration value="7"/>
 *                                                                         &lt;enumeration value="8"/>
 *                                                                         &lt;enumeration value="9"/>
 *                                                                         &lt;enumeration value="10"/>
 *                                                                         &lt;enumeration value="11"/>
 *                                                                         &lt;enumeration value="12"/>
 *                                                                         &lt;enumeration value="13"/>
 *                                                                         &lt;enumeration value="14"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="stock_c">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="udd">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="energie">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                           &lt;element name="sous_contributeur" maxOccurs="8">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;all>
 *                                                                     &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                                     &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                                   &lt;/all>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                         &lt;enumeration value="4"/>
 *                                                                         &lt;enumeration value="5"/>
 *                                                                         &lt;enumeration value="6"/>
 *                                                                         &lt;enumeration value="7"/>
 *                                                                         &lt;enumeration value="8"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="eau">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                           &lt;element name="sous_contributeur" maxOccurs="3">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;all>
 *                                                                     &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                                     &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                                   &lt;/all>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="chantier">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                           &lt;element name="sous_contributeur" maxOccurs="4">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;all>
 *                                                                     &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                                     &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                                   &lt;/all>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                         &lt;enumeration value="1"/>
 *                                                                         &lt;enumeration value="2"/>
 *                                                                         &lt;enumeration value="3"/>
 *                                                                         &lt;enumeration value="4"/>
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                       &lt;element name="indicateur_perf_env">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;all>
 *                                                 &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="coef_mod_icconstruction" type="{}t_coef_mod_icconstruction"/>
 *                                                 &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="coef_mod_icenergie" type="{}t_coef_mod_icenergie"/>
 *                                                 &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_zone" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_zone_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                 &lt;element name="stock_c">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="udd">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                               &lt;/all>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="parcelle" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                             &lt;element name="contributeur">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="composant" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                                 &lt;element name="stock_c">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="udd">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="ic">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="ic_ded">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="eau" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="chantier" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *       &lt;attribute name="version" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;whiteSpace value="collapse"/>
 *             &lt;pattern value="20[2-4][1-9]\.[C][1-9]\.0\.0"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="phase" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;enumeration value="1"/>
 *             &lt;enumeration value="2"/>
 *             &lt;enumeration value="3"/>
 *             &lt;enumeration value="4"/>
 *             &lt;enumeration value="5"/>
 *             &lt;enumeration value="6"/>
 *             &lt;enumeration value="7"/>
 *             &lt;enumeration value="8"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "RSEnv")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RSEnv {

    @XmlElement(name = "entree_projet", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RSEnv.EntreeProjet entreeProjet;
    @XmlElement(name = "sortie_projet", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RSEnv.SortieProjet sortieProjet;
    @XmlAttribute(name = "version", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String version;
    @XmlAttribute(name = "phase", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int phase;

    /**
     * Gets the value of the entreeProjet property.
     * 
     * @return
     *     possible object is
     *     {@link RSEnv.EntreeProjet }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RSEnv.EntreeProjet getEntreeProjet() {
        return entreeProjet;
    }

    /**
     * Sets the value of the entreeProjet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSEnv.EntreeProjet }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEntreeProjet(RSEnv.EntreeProjet value) {
        this.entreeProjet = value;
    }

    /**
     * Gets the value of the sortieProjet property.
     * 
     * @return
     *     possible object is
     *     {@link RSEnv.SortieProjet }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RSEnv.SortieProjet getSortieProjet() {
        return sortieProjet;
    }

    /**
     * Sets the value of the sortieProjet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSEnv.SortieProjet }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieProjet(RSEnv.SortieProjet value) {
        this.sortieProjet = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the phase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getPhase() {
        return phase;
    }

    /**
     * Sets the value of the phase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPhase(int value) {
        this.phase = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="batiment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="nom" type="{}p_string500"/>
     *                   &lt;element name="sref">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="zone" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="usage">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="sref">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="scombles">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="contributeur">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="composant">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="lot" maxOccurs="13" minOccurs="9">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;choice>
     *                                                           &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
     *                                                           &lt;element name="sous_lot" maxOccurs="unbounded">
     *                                                             &lt;complexType>
     *                                                               &lt;complexContent>
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                                   &lt;sequence>
     *                                                                     &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
     *                                                                   &lt;/sequence>
     *                                                                   &lt;attribute name="ref" use="required">
     *                                                                     &lt;simpleType>
     *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                                         &lt;enumeration value="1.1"/>
     *                                                                         &lt;enumeration value="1.2"/>
     *                                                                         &lt;enumeration value="1.3"/>
     *                                                                         &lt;enumeration value="2.1"/>
     *                                                                         &lt;enumeration value="2.2"/>
     *                                                                         &lt;enumeration value="2.3"/>
     *                                                                         &lt;enumeration value="3.1"/>
     *                                                                         &lt;enumeration value="3.2"/>
     *                                                                         &lt;enumeration value="3.3"/>
     *                                                                         &lt;enumeration value="3.4"/>
     *                                                                         &lt;enumeration value="3.5"/>
     *                                                                         &lt;enumeration value="3.6"/>
     *                                                                         &lt;enumeration value="3.7"/>
     *                                                                         &lt;enumeration value="3.8"/>
     *                                                                         &lt;enumeration value="4.1"/>
     *                                                                         &lt;enumeration value="4.2"/>
     *                                                                         &lt;enumeration value="4.3"/>
     *                                                                         &lt;enumeration value="5.1"/>
     *                                                                         &lt;enumeration value="5.2"/>
     *                                                                         &lt;enumeration value="5.3"/>
     *                                                                         &lt;enumeration value="5.4"/>
     *                                                                         &lt;enumeration value="5.5"/>
     *                                                                         &lt;enumeration value="6.1"/>
     *                                                                         &lt;enumeration value="6.2"/>
     *                                                                         &lt;enumeration value="6.3"/>
     *                                                                         &lt;enumeration value="7.1"/>
     *                                                                         &lt;enumeration value="7.2"/>
     *                                                                         &lt;enumeration value="7.3"/>
     *                                                                         &lt;enumeration value="8.1"/>
     *                                                                         &lt;enumeration value="8.2"/>
     *                                                                         &lt;enumeration value="8.3"/>
     *                                                                         &lt;enumeration value="8.4"/>
     *                                                                         &lt;enumeration value="8.5"/>
     *                                                                         &lt;enumeration value="8.6"/>
     *                                                                         &lt;enumeration value="8.7"/>
     *                                                                         &lt;enumeration value="9.1"/>
     *                                                                         &lt;enumeration value="9.2"/>
     *                                                                         &lt;enumeration value="10.1"/>
     *                                                                         &lt;enumeration value="10.2"/>
     *                                                                         &lt;enumeration value="10.3"/>
     *                                                                         &lt;enumeration value="10.4"/>
     *                                                                         &lt;enumeration value="10.5"/>
     *                                                                         &lt;enumeration value="10.6"/>
     *                                                                         &lt;enumeration value="11.1"/>
     *                                                                         &lt;enumeration value="11.2"/>
     *                                                                         &lt;enumeration value="11.3"/>
     *                                                                         &lt;enumeration value="12.1"/>
     *                                                                         &lt;enumeration value="13.1"/>
     *                                                                       &lt;/restriction>
     *                                                                     &lt;/simpleType>
     *                                                                   &lt;/attribute>
     *                                                                 &lt;/restriction>
     *                                                               &lt;/complexContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/choice>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                               &lt;enumeration value="4"/>
     *                                                               &lt;enumeration value="5"/>
     *                                                               &lt;enumeration value="6"/>
     *                                                               &lt;enumeration value="7"/>
     *                                                               &lt;enumeration value="8"/>
     *                                                               &lt;enumeration value="9"/>
     *                                                               &lt;enumeration value="10"/>
     *                                                               &lt;enumeration value="11"/>
     *                                                               &lt;enumeration value="12"/>
     *                                                               &lt;enumeration value="13"/>
     *                                                               &lt;enumeration value="14"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="energie">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="sous_contributeur" maxOccurs="8">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                               &lt;enumeration value="4"/>
     *                                                               &lt;enumeration value="5"/>
     *                                                               &lt;enumeration value="6"/>
     *                                                               &lt;enumeration value="7"/>
     *                                                               &lt;enumeration value="8"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="eau">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="sous_contributeur" maxOccurs="3">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="chantier">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="sous_contributeur" maxOccurs="4">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;choice>
     *                                                           &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
     *                                                           &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
     *                                                         &lt;/choice>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                               &lt;enumeration value="4"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="n_occ">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="nb_logement" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
     *                             &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="emprise_au_sol">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minExclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="periode_reference" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="duree_chantier">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minExclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="parcelle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="contributeur" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="composant" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="eau" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="chantier" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="surface_parcelle" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="surface_arrosee" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="surface_veg" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="surface_imper" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="date_etude" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="reseau" maxOccurs="2" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="identifiant">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;whiteSpace value="collapse"/>
     *                         &lt;pattern value="[0-9]{4}[CF]"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="1"/>
     *                         &lt;enumeration value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nom" type="{}p_string500"/>
     *                   &lt;element name="localisation" type="{}p_string500"/>
     *                   &lt;element name="contenu_co2">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/all>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "batiment",
        "parcelle",
        "dateEtude",
        "reseau"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class EntreeProjet {

        @XmlElement(required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected List<RSEnv.EntreeProjet.Batiment> batiment;
        @XmlElement(required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected RSEnv.EntreeProjet.Parcelle parcelle;
        @XmlElement(name = "date_etude", required = true)
        @XmlSchemaType(name = "date")
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected XMLGregorianCalendar dateEtude;
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected List<RSEnv.EntreeProjet.Reseau> reseau;

        /**
         * Gets the value of the batiment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the batiment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBatiment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RSEnv.EntreeProjet.Batiment }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public List<RSEnv.EntreeProjet.Batiment> getBatiment() {
            if (batiment == null) {
                batiment = new ArrayList<RSEnv.EntreeProjet.Batiment>();
            }
            return this.batiment;
        }

        /**
         * Gets the value of the parcelle property.
         * 
         * @return
         *     possible object is
         *     {@link RSEnv.EntreeProjet.Parcelle }
         *     
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public RSEnv.EntreeProjet.Parcelle getParcelle() {
            return parcelle;
        }

        /**
         * Sets the value of the parcelle property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSEnv.EntreeProjet.Parcelle }
         *     
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setParcelle(RSEnv.EntreeProjet.Parcelle value) {
            this.parcelle = value;
        }

        /**
         * Gets the value of the dateEtude property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public XMLGregorianCalendar getDateEtude() {
            return dateEtude;
        }

        /**
         * Sets the value of the dateEtude property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setDateEtude(XMLGregorianCalendar value) {
            this.dateEtude = value;
        }

        /**
         * Gets the value of the reseau property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reseau property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReseau().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RSEnv.EntreeProjet.Reseau }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public List<RSEnv.EntreeProjet.Reseau> getReseau() {
            if (reseau == null) {
                reseau = new ArrayList<RSEnv.EntreeProjet.Reseau>();
            }
            return this.reseau;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="nom" type="{}p_string500"/>
         *         &lt;element name="sref">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="zone" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="usage">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="sref">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="scombles">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="contributeur">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="composant">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="lot" maxOccurs="13" minOccurs="9">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;choice>
         *                                                 &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
         *                                                 &lt;element name="sous_lot" maxOccurs="unbounded">
         *                                                   &lt;complexType>
         *                                                     &lt;complexContent>
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                                         &lt;sequence>
         *                                                           &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
         *                                                         &lt;/sequence>
         *                                                         &lt;attribute name="ref" use="required">
         *                                                           &lt;simpleType>
         *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                                               &lt;enumeration value="1.1"/>
         *                                                               &lt;enumeration value="1.2"/>
         *                                                               &lt;enumeration value="1.3"/>
         *                                                               &lt;enumeration value="2.1"/>
         *                                                               &lt;enumeration value="2.2"/>
         *                                                               &lt;enumeration value="2.3"/>
         *                                                               &lt;enumeration value="3.1"/>
         *                                                               &lt;enumeration value="3.2"/>
         *                                                               &lt;enumeration value="3.3"/>
         *                                                               &lt;enumeration value="3.4"/>
         *                                                               &lt;enumeration value="3.5"/>
         *                                                               &lt;enumeration value="3.6"/>
         *                                                               &lt;enumeration value="3.7"/>
         *                                                               &lt;enumeration value="3.8"/>
         *                                                               &lt;enumeration value="4.1"/>
         *                                                               &lt;enumeration value="4.2"/>
         *                                                               &lt;enumeration value="4.3"/>
         *                                                               &lt;enumeration value="5.1"/>
         *                                                               &lt;enumeration value="5.2"/>
         *                                                               &lt;enumeration value="5.3"/>
         *                                                               &lt;enumeration value="5.4"/>
         *                                                               &lt;enumeration value="5.5"/>
         *                                                               &lt;enumeration value="6.1"/>
         *                                                               &lt;enumeration value="6.2"/>
         *                                                               &lt;enumeration value="6.3"/>
         *                                                               &lt;enumeration value="7.1"/>
         *                                                               &lt;enumeration value="7.2"/>
         *                                                               &lt;enumeration value="7.3"/>
         *                                                               &lt;enumeration value="8.1"/>
         *                                                               &lt;enumeration value="8.2"/>
         *                                                               &lt;enumeration value="8.3"/>
         *                                                               &lt;enumeration value="8.4"/>
         *                                                               &lt;enumeration value="8.5"/>
         *                                                               &lt;enumeration value="8.6"/>
         *                                                               &lt;enumeration value="8.7"/>
         *                                                               &lt;enumeration value="9.1"/>
         *                                                               &lt;enumeration value="9.2"/>
         *                                                               &lt;enumeration value="10.1"/>
         *                                                               &lt;enumeration value="10.2"/>
         *                                                               &lt;enumeration value="10.3"/>
         *                                                               &lt;enumeration value="10.4"/>
         *                                                               &lt;enumeration value="10.5"/>
         *                                                               &lt;enumeration value="10.6"/>
         *                                                               &lt;enumeration value="11.1"/>
         *                                                               &lt;enumeration value="11.2"/>
         *                                                               &lt;enumeration value="11.3"/>
         *                                                               &lt;enumeration value="12.1"/>
         *                                                               &lt;enumeration value="13.1"/>
         *                                                             &lt;/restriction>
         *                                                           &lt;/simpleType>
         *                                                         &lt;/attribute>
         *                                                       &lt;/restriction>
         *                                                     &lt;/complexContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/choice>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                     &lt;enumeration value="4"/>
         *                                                     &lt;enumeration value="5"/>
         *                                                     &lt;enumeration value="6"/>
         *                                                     &lt;enumeration value="7"/>
         *                                                     &lt;enumeration value="8"/>
         *                                                     &lt;enumeration value="9"/>
         *                                                     &lt;enumeration value="10"/>
         *                                                     &lt;enumeration value="11"/>
         *                                                     &lt;enumeration value="12"/>
         *                                                     &lt;enumeration value="13"/>
         *                                                     &lt;enumeration value="14"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="energie">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="sous_contributeur" maxOccurs="8">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                     &lt;enumeration value="4"/>
         *                                                     &lt;enumeration value="5"/>
         *                                                     &lt;enumeration value="6"/>
         *                                                     &lt;enumeration value="7"/>
         *                                                     &lt;enumeration value="8"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="eau">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="sous_contributeur" maxOccurs="3">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="chantier">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="sous_contributeur" maxOccurs="4">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;choice>
         *                                                 &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
         *                                                 &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
         *                                               &lt;/choice>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                     &lt;enumeration value="4"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="n_occ">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="nb_logement" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
         *                   &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="emprise_au_sol">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minExclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="periode_reference" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="duree_chantier">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minExclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "index",
            "nom",
            "sref",
            "zone",
            "empriseAuSol",
            "periodeReference",
            "dureeChantier"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class Batiment {

            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected int index;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String nom;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected BigDecimal sref;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected List<RSEnv.EntreeProjet.Batiment.Zone> zone;
            @XmlElement(name = "emprise_au_sol", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected BigDecimal empriseAuSol;
            @XmlElement(name = "periode_reference", defaultValue = "50")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected int periodeReference;
            @XmlElement(name = "duree_chantier", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected BigDecimal dureeChantier;

            /**
             * Gets the value of the index property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public int getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndex(int value) {
                this.index = value;
            }

            /**
             * Gets the value of the nom property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getNom() {
                return nom;
            }

            /**
             * Sets the value of the nom property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setNom(String value) {
                this.nom = value;
            }

            /**
             * Gets the value of the sref property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public BigDecimal getSref() {
                return sref;
            }

            /**
             * Sets the value of the sref property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setSref(BigDecimal value) {
                this.sref = value;
            }

            /**
             * Gets the value of the zone property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the zone property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getZone().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RSEnv.EntreeProjet.Batiment.Zone }
             * 
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public List<RSEnv.EntreeProjet.Batiment.Zone> getZone() {
                if (zone == null) {
                    zone = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone>();
                }
                return this.zone;
            }

            /**
             * Gets the value of the empriseAuSol property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public BigDecimal getEmpriseAuSol() {
                return empriseAuSol;
            }

            /**
             * Sets the value of the empriseAuSol property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setEmpriseAuSol(BigDecimal value) {
                this.empriseAuSol = value;
            }

            /**
             * Gets the value of the periodeReference property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public int getPeriodeReference() {
                return periodeReference;
            }

            /**
             * Sets the value of the periodeReference property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setPeriodeReference(int value) {
                this.periodeReference = value;
            }

            /**
             * Gets the value of the dureeChantier property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public BigDecimal getDureeChantier() {
                return dureeChantier;
            }

            /**
             * Sets the value of the dureeChantier property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setDureeChantier(BigDecimal value) {
                this.dureeChantier = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="usage">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="sref">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="scombles">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="contributeur">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="composant">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="lot" maxOccurs="13" minOccurs="9">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;choice>
             *                                       &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
             *                                       &lt;element name="sous_lot" maxOccurs="unbounded">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;sequence>
             *                                                 &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
             *                                               &lt;/sequence>
             *                                               &lt;attribute name="ref" use="required">
             *                                                 &lt;simpleType>
             *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                                     &lt;enumeration value="1.1"/>
             *                                                     &lt;enumeration value="1.2"/>
             *                                                     &lt;enumeration value="1.3"/>
             *                                                     &lt;enumeration value="2.1"/>
             *                                                     &lt;enumeration value="2.2"/>
             *                                                     &lt;enumeration value="2.3"/>
             *                                                     &lt;enumeration value="3.1"/>
             *                                                     &lt;enumeration value="3.2"/>
             *                                                     &lt;enumeration value="3.3"/>
             *                                                     &lt;enumeration value="3.4"/>
             *                                                     &lt;enumeration value="3.5"/>
             *                                                     &lt;enumeration value="3.6"/>
             *                                                     &lt;enumeration value="3.7"/>
             *                                                     &lt;enumeration value="3.8"/>
             *                                                     &lt;enumeration value="4.1"/>
             *                                                     &lt;enumeration value="4.2"/>
             *                                                     &lt;enumeration value="4.3"/>
             *                                                     &lt;enumeration value="5.1"/>
             *                                                     &lt;enumeration value="5.2"/>
             *                                                     &lt;enumeration value="5.3"/>
             *                                                     &lt;enumeration value="5.4"/>
             *                                                     &lt;enumeration value="5.5"/>
             *                                                     &lt;enumeration value="6.1"/>
             *                                                     &lt;enumeration value="6.2"/>
             *                                                     &lt;enumeration value="6.3"/>
             *                                                     &lt;enumeration value="7.1"/>
             *                                                     &lt;enumeration value="7.2"/>
             *                                                     &lt;enumeration value="7.3"/>
             *                                                     &lt;enumeration value="8.1"/>
             *                                                     &lt;enumeration value="8.2"/>
             *                                                     &lt;enumeration value="8.3"/>
             *                                                     &lt;enumeration value="8.4"/>
             *                                                     &lt;enumeration value="8.5"/>
             *                                                     &lt;enumeration value="8.6"/>
             *                                                     &lt;enumeration value="8.7"/>
             *                                                     &lt;enumeration value="9.1"/>
             *                                                     &lt;enumeration value="9.2"/>
             *                                                     &lt;enumeration value="10.1"/>
             *                                                     &lt;enumeration value="10.2"/>
             *                                                     &lt;enumeration value="10.3"/>
             *                                                     &lt;enumeration value="10.4"/>
             *                                                     &lt;enumeration value="10.5"/>
             *                                                     &lt;enumeration value="10.6"/>
             *                                                     &lt;enumeration value="11.1"/>
             *                                                     &lt;enumeration value="11.2"/>
             *                                                     &lt;enumeration value="11.3"/>
             *                                                     &lt;enumeration value="12.1"/>
             *                                                     &lt;enumeration value="13.1"/>
             *                                                   &lt;/restriction>
             *                                                 &lt;/simpleType>
             *                                               &lt;/attribute>
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/choice>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                           &lt;enumeration value="4"/>
             *                                           &lt;enumeration value="5"/>
             *                                           &lt;enumeration value="6"/>
             *                                           &lt;enumeration value="7"/>
             *                                           &lt;enumeration value="8"/>
             *                                           &lt;enumeration value="9"/>
             *                                           &lt;enumeration value="10"/>
             *                                           &lt;enumeration value="11"/>
             *                                           &lt;enumeration value="12"/>
             *                                           &lt;enumeration value="13"/>
             *                                           &lt;enumeration value="14"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="energie">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="sous_contributeur" maxOccurs="8">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                           &lt;enumeration value="4"/>
             *                                           &lt;enumeration value="5"/>
             *                                           &lt;enumeration value="6"/>
             *                                           &lt;enumeration value="7"/>
             *                                           &lt;enumeration value="8"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="eau">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="sous_contributeur" maxOccurs="3">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="chantier">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="sous_contributeur" maxOccurs="4">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;choice>
             *                                       &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
             *                                       &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
             *                                     &lt;/choice>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                           &lt;enumeration value="4"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="n_occ">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="nb_logement" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
             *         &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "index",
                "usage",
                "sref",
                "scombles",
                "contributeur",
                "nOcc",
                "nbLogement",
                "calculetteEau",
                "calculetteChantier"
            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class Zone {

                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected int index;
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected int usage;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal sref;
                @XmlElement(required = true, defaultValue = "0")
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal scombles;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.EntreeProjet.Batiment.Zone.Contributeur contributeur;
                @XmlElement(name = "n_occ", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal nOcc;
                @XmlElement(name = "nb_logement")
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected int nbLogement;
                @XmlElement(name = "calculette_eau")
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected TCalculetteEau calculetteEau;
                @XmlElement(name = "calculette_chantier")
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected TCalculetteChantier calculetteChantier;

                /**
                 * Gets the value of the index property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public int getIndex() {
                    return index;
                }

                /**
                 * Sets the value of the index property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIndex(int value) {
                    this.index = value;
                }

                /**
                 * Gets the value of the usage property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public int getUsage() {
                    return usage;
                }

                /**
                 * Sets the value of the usage property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setUsage(int value) {
                    this.usage = value;
                }

                /**
                 * Gets the value of the sref property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getSref() {
                    return sref;
                }

                /**
                 * Sets the value of the sref property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setSref(BigDecimal value) {
                    this.sref = value;
                }

                /**
                 * Gets the value of the scombles property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getScombles() {
                    return scombles;
                }

                /**
                 * Sets the value of the scombles property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setScombles(BigDecimal value) {
                    this.scombles = value;
                }

                /**
                 * Gets the value of the contributeur property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.EntreeProjet.Batiment.Zone.Contributeur getContributeur() {
                    return contributeur;
                }

                /**
                 * Sets the value of the contributeur property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setContributeur(RSEnv.EntreeProjet.Batiment.Zone.Contributeur value) {
                    this.contributeur = value;
                }

                /**
                 * Gets the value of the nOcc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getNOcc() {
                    return nOcc;
                }

                /**
                 * Sets the value of the nOcc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setNOcc(BigDecimal value) {
                    this.nOcc = value;
                }

                /**
                 * Gets the value of the nbLogement property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public int getNbLogement() {
                    return nbLogement;
                }

                /**
                 * Sets the value of the nbLogement property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setNbLogement(int value) {
                    this.nbLogement = value;
                }

                /**
                 * Gets the value of the calculetteEau property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TCalculetteEau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public TCalculetteEau getCalculetteEau() {
                    return calculetteEau;
                }

                /**
                 * Sets the value of the calculetteEau property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TCalculetteEau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setCalculetteEau(TCalculetteEau value) {
                    this.calculetteEau = value;
                }

                /**
                 * Gets the value of the calculetteChantier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TCalculetteChantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public TCalculetteChantier getCalculetteChantier() {
                    return calculetteChantier;
                }

                /**
                 * Sets the value of the calculetteChantier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TCalculetteChantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setCalculetteChantier(TCalculetteChantier value) {
                    this.calculetteChantier = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="composant">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="lot" maxOccurs="13" minOccurs="9">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;choice>
                 *                             &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                 *                             &lt;element name="sous_lot" maxOccurs="unbounded">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;sequence>
                 *                                       &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                 *                                     &lt;/sequence>
                 *                                     &lt;attribute name="ref" use="required">
                 *                                       &lt;simpleType>
                 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                                           &lt;enumeration value="1.1"/>
                 *                                           &lt;enumeration value="1.2"/>
                 *                                           &lt;enumeration value="1.3"/>
                 *                                           &lt;enumeration value="2.1"/>
                 *                                           &lt;enumeration value="2.2"/>
                 *                                           &lt;enumeration value="2.3"/>
                 *                                           &lt;enumeration value="3.1"/>
                 *                                           &lt;enumeration value="3.2"/>
                 *                                           &lt;enumeration value="3.3"/>
                 *                                           &lt;enumeration value="3.4"/>
                 *                                           &lt;enumeration value="3.5"/>
                 *                                           &lt;enumeration value="3.6"/>
                 *                                           &lt;enumeration value="3.7"/>
                 *                                           &lt;enumeration value="3.8"/>
                 *                                           &lt;enumeration value="4.1"/>
                 *                                           &lt;enumeration value="4.2"/>
                 *                                           &lt;enumeration value="4.3"/>
                 *                                           &lt;enumeration value="5.1"/>
                 *                                           &lt;enumeration value="5.2"/>
                 *                                           &lt;enumeration value="5.3"/>
                 *                                           &lt;enumeration value="5.4"/>
                 *                                           &lt;enumeration value="5.5"/>
                 *                                           &lt;enumeration value="6.1"/>
                 *                                           &lt;enumeration value="6.2"/>
                 *                                           &lt;enumeration value="6.3"/>
                 *                                           &lt;enumeration value="7.1"/>
                 *                                           &lt;enumeration value="7.2"/>
                 *                                           &lt;enumeration value="7.3"/>
                 *                                           &lt;enumeration value="8.1"/>
                 *                                           &lt;enumeration value="8.2"/>
                 *                                           &lt;enumeration value="8.3"/>
                 *                                           &lt;enumeration value="8.4"/>
                 *                                           &lt;enumeration value="8.5"/>
                 *                                           &lt;enumeration value="8.6"/>
                 *                                           &lt;enumeration value="8.7"/>
                 *                                           &lt;enumeration value="9.1"/>
                 *                                           &lt;enumeration value="9.2"/>
                 *                                           &lt;enumeration value="10.1"/>
                 *                                           &lt;enumeration value="10.2"/>
                 *                                           &lt;enumeration value="10.3"/>
                 *                                           &lt;enumeration value="10.4"/>
                 *                                           &lt;enumeration value="10.5"/>
                 *                                           &lt;enumeration value="10.6"/>
                 *                                           &lt;enumeration value="11.1"/>
                 *                                           &lt;enumeration value="11.2"/>
                 *                                           &lt;enumeration value="11.3"/>
                 *                                           &lt;enumeration value="12.1"/>
                 *                                           &lt;enumeration value="13.1"/>
                 *                                         &lt;/restriction>
                 *                                       &lt;/simpleType>
                 *                                     &lt;/attribute>
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/choice>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                                 &lt;enumeration value="4"/>
                 *                                 &lt;enumeration value="5"/>
                 *                                 &lt;enumeration value="6"/>
                 *                                 &lt;enumeration value="7"/>
                 *                                 &lt;enumeration value="8"/>
                 *                                 &lt;enumeration value="9"/>
                 *                                 &lt;enumeration value="10"/>
                 *                                 &lt;enumeration value="11"/>
                 *                                 &lt;enumeration value="12"/>
                 *                                 &lt;enumeration value="13"/>
                 *                                 &lt;enumeration value="14"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="energie">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="sous_contributeur" maxOccurs="8">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                                 &lt;enumeration value="4"/>
                 *                                 &lt;enumeration value="5"/>
                 *                                 &lt;enumeration value="6"/>
                 *                                 &lt;enumeration value="7"/>
                 *                                 &lt;enumeration value="8"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="eau">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="sous_contributeur" maxOccurs="3">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="chantier">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="sous_contributeur" maxOccurs="4">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;choice>
                 *                             &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                 *                             &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                 *                           &lt;/choice>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                                 &lt;enumeration value="4"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "composant",
                    "energie",
                    "eau",
                    "chantier"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Contributeur {

                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant composant;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie energie;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau eau;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier chantier;

                    /**
                     * Gets the value of the composant property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant getComposant() {
                        return composant;
                    }

                    /**
                     * Sets the value of the composant property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setComposant(RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant value) {
                        this.composant = value;
                    }

                    /**
                     * Gets the value of the energie property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie getEnergie() {
                        return energie;
                    }

                    /**
                     * Sets the value of the energie property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setEnergie(RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie value) {
                        this.energie = value;
                    }

                    /**
                     * Gets the value of the eau property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau getEau() {
                        return eau;
                    }

                    /**
                     * Sets the value of the eau property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setEau(RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau value) {
                        this.eau = value;
                    }

                    /**
                     * Gets the value of the chantier property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier getChantier() {
                        return chantier;
                    }

                    /**
                     * Sets the value of the chantier property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setChantier(RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier value) {
                        this.chantier = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="sous_contributeur" maxOccurs="4">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;choice>
                     *                   &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                     *                   &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                     *                 &lt;/choice>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                       &lt;enumeration value="4"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "sousContributeur"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Chantier {

                        @XmlElement(name = "sous_contributeur", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur> sousContributeur;

                        /**
                         * Gets the value of the sousContributeur property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousContributeur().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur> getSousContributeur() {
                            if (sousContributeur == null) {
                                sousContributeur = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur>();
                            }
                            return this.sousContributeur;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;choice>
                         *         &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                         *         &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                         *       &lt;/choice>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *             &lt;enumeration value="4"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "donneesComposant",
                            "donneesService"
                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousContributeur {

                            @XmlElement(name = "donnees_composant")
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<TDonneeComposant> donneesComposant;
                            @XmlElement(name = "donnees_service")
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<TDonneesService> donneesService;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected int ref;

                            /**
                             * Gets the value of the donneesComposant property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the donneesComposant property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDonneesComposant().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link TDonneeComposant }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<TDonneeComposant> getDonneesComposant() {
                                if (donneesComposant == null) {
                                    donneesComposant = new ArrayList<TDonneeComposant>();
                                }
                                return this.donneesComposant;
                            }

                            /**
                             * Gets the value of the donneesService property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the donneesService property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDonneesService().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link TDonneesService }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<TDonneesService> getDonneesService() {
                                if (donneesService == null) {
                                    donneesService = new ArrayList<TDonneesService>();
                                }
                                return this.donneesService;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(int value) {
                                this.ref = value;
                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="lot" maxOccurs="13" minOccurs="9">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;choice>
                     *                   &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                     *                   &lt;element name="sous_lot" maxOccurs="unbounded">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;sequence>
                     *                             &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                     *                           &lt;/sequence>
                     *                           &lt;attribute name="ref" use="required">
                     *                             &lt;simpleType>
                     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *                                 &lt;enumeration value="1.1"/>
                     *                                 &lt;enumeration value="1.2"/>
                     *                                 &lt;enumeration value="1.3"/>
                     *                                 &lt;enumeration value="2.1"/>
                     *                                 &lt;enumeration value="2.2"/>
                     *                                 &lt;enumeration value="2.3"/>
                     *                                 &lt;enumeration value="3.1"/>
                     *                                 &lt;enumeration value="3.2"/>
                     *                                 &lt;enumeration value="3.3"/>
                     *                                 &lt;enumeration value="3.4"/>
                     *                                 &lt;enumeration value="3.5"/>
                     *                                 &lt;enumeration value="3.6"/>
                     *                                 &lt;enumeration value="3.7"/>
                     *                                 &lt;enumeration value="3.8"/>
                     *                                 &lt;enumeration value="4.1"/>
                     *                                 &lt;enumeration value="4.2"/>
                     *                                 &lt;enumeration value="4.3"/>
                     *                                 &lt;enumeration value="5.1"/>
                     *                                 &lt;enumeration value="5.2"/>
                     *                                 &lt;enumeration value="5.3"/>
                     *                                 &lt;enumeration value="5.4"/>
                     *                                 &lt;enumeration value="5.5"/>
                     *                                 &lt;enumeration value="6.1"/>
                     *                                 &lt;enumeration value="6.2"/>
                     *                                 &lt;enumeration value="6.3"/>
                     *                                 &lt;enumeration value="7.1"/>
                     *                                 &lt;enumeration value="7.2"/>
                     *                                 &lt;enumeration value="7.3"/>
                     *                                 &lt;enumeration value="8.1"/>
                     *                                 &lt;enumeration value="8.2"/>
                     *                                 &lt;enumeration value="8.3"/>
                     *                                 &lt;enumeration value="8.4"/>
                     *                                 &lt;enumeration value="8.5"/>
                     *                                 &lt;enumeration value="8.6"/>
                     *                                 &lt;enumeration value="8.7"/>
                     *                                 &lt;enumeration value="9.1"/>
                     *                                 &lt;enumeration value="9.2"/>
                     *                                 &lt;enumeration value="10.1"/>
                     *                                 &lt;enumeration value="10.2"/>
                     *                                 &lt;enumeration value="10.3"/>
                     *                                 &lt;enumeration value="10.4"/>
                     *                                 &lt;enumeration value="10.5"/>
                     *                                 &lt;enumeration value="10.6"/>
                     *                                 &lt;enumeration value="11.1"/>
                     *                                 &lt;enumeration value="11.2"/>
                     *                                 &lt;enumeration value="11.3"/>
                     *                                 &lt;enumeration value="12.1"/>
                     *                                 &lt;enumeration value="13.1"/>
                     *                               &lt;/restriction>
                     *                             &lt;/simpleType>
                     *                           &lt;/attribute>
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/choice>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                       &lt;enumeration value="4"/>
                     *                       &lt;enumeration value="5"/>
                     *                       &lt;enumeration value="6"/>
                     *                       &lt;enumeration value="7"/>
                     *                       &lt;enumeration value="8"/>
                     *                       &lt;enumeration value="9"/>
                     *                       &lt;enumeration value="10"/>
                     *                       &lt;enumeration value="11"/>
                     *                       &lt;enumeration value="12"/>
                     *                       &lt;enumeration value="13"/>
                     *                       &lt;enumeration value="14"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "lot"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Composant {

                        @XmlElement(required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot> lot;

                        /**
                         * Gets the value of the lot property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the lot property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getLot().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot> getLot() {
                            if (lot == null) {
                                lot = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot>();
                            }
                            return this.lot;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;choice>
                         *         &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                         *         &lt;element name="sous_lot" maxOccurs="unbounded">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;sequence>
                         *                   &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                         *                 &lt;/sequence>
                         *                 &lt;attribute name="ref" use="required">
                         *                   &lt;simpleType>
                         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                         *                       &lt;enumeration value="1.1"/>
                         *                       &lt;enumeration value="1.2"/>
                         *                       &lt;enumeration value="1.3"/>
                         *                       &lt;enumeration value="2.1"/>
                         *                       &lt;enumeration value="2.2"/>
                         *                       &lt;enumeration value="2.3"/>
                         *                       &lt;enumeration value="3.1"/>
                         *                       &lt;enumeration value="3.2"/>
                         *                       &lt;enumeration value="3.3"/>
                         *                       &lt;enumeration value="3.4"/>
                         *                       &lt;enumeration value="3.5"/>
                         *                       &lt;enumeration value="3.6"/>
                         *                       &lt;enumeration value="3.7"/>
                         *                       &lt;enumeration value="3.8"/>
                         *                       &lt;enumeration value="4.1"/>
                         *                       &lt;enumeration value="4.2"/>
                         *                       &lt;enumeration value="4.3"/>
                         *                       &lt;enumeration value="5.1"/>
                         *                       &lt;enumeration value="5.2"/>
                         *                       &lt;enumeration value="5.3"/>
                         *                       &lt;enumeration value="5.4"/>
                         *                       &lt;enumeration value="5.5"/>
                         *                       &lt;enumeration value="6.1"/>
                         *                       &lt;enumeration value="6.2"/>
                         *                       &lt;enumeration value="6.3"/>
                         *                       &lt;enumeration value="7.1"/>
                         *                       &lt;enumeration value="7.2"/>
                         *                       &lt;enumeration value="7.3"/>
                         *                       &lt;enumeration value="8.1"/>
                         *                       &lt;enumeration value="8.2"/>
                         *                       &lt;enumeration value="8.3"/>
                         *                       &lt;enumeration value="8.4"/>
                         *                       &lt;enumeration value="8.5"/>
                         *                       &lt;enumeration value="8.6"/>
                         *                       &lt;enumeration value="8.7"/>
                         *                       &lt;enumeration value="9.1"/>
                         *                       &lt;enumeration value="9.2"/>
                         *                       &lt;enumeration value="10.1"/>
                         *                       &lt;enumeration value="10.2"/>
                         *                       &lt;enumeration value="10.3"/>
                         *                       &lt;enumeration value="10.4"/>
                         *                       &lt;enumeration value="10.5"/>
                         *                       &lt;enumeration value="10.6"/>
                         *                       &lt;enumeration value="11.1"/>
                         *                       &lt;enumeration value="11.2"/>
                         *                       &lt;enumeration value="11.3"/>
                         *                       &lt;enumeration value="12.1"/>
                         *                       &lt;enumeration value="13.1"/>
                         *                     &lt;/restriction>
                         *                   &lt;/simpleType>
                         *                 &lt;/attribute>
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/choice>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *             &lt;enumeration value="4"/>
                         *             &lt;enumeration value="5"/>
                         *             &lt;enumeration value="6"/>
                         *             &lt;enumeration value="7"/>
                         *             &lt;enumeration value="8"/>
                         *             &lt;enumeration value="9"/>
                         *             &lt;enumeration value="10"/>
                         *             &lt;enumeration value="11"/>
                         *             &lt;enumeration value="12"/>
                         *             &lt;enumeration value="13"/>
                         *             &lt;enumeration value="14"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "donneesComposant",
                            "sousLot"
                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class Lot {

                            @XmlElement(name = "donnees_composant")
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<TDonneeComposant> donneesComposant;
                            @XmlElement(name = "sous_lot")
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot> sousLot;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected int ref;

                            /**
                             * Gets the value of the donneesComposant property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the donneesComposant property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDonneesComposant().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link TDonneeComposant }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<TDonneeComposant> getDonneesComposant() {
                                if (donneesComposant == null) {
                                    donneesComposant = new ArrayList<TDonneeComposant>();
                                }
                                return this.donneesComposant;
                            }

                            /**
                             * Gets the value of the sousLot property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the sousLot property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getSousLot().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot> getSousLot() {
                                if (sousLot == null) {
                                    sousLot = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot>();
                                }
                                return this.sousLot;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(int value) {
                                this.ref = value;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             * 
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;sequence>
                             *         &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                             *       &lt;/sequence>
                             *       &lt;attribute name="ref" use="required">
                             *         &lt;simpleType>
                             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                             *             &lt;enumeration value="1.1"/>
                             *             &lt;enumeration value="1.2"/>
                             *             &lt;enumeration value="1.3"/>
                             *             &lt;enumeration value="2.1"/>
                             *             &lt;enumeration value="2.2"/>
                             *             &lt;enumeration value="2.3"/>
                             *             &lt;enumeration value="3.1"/>
                             *             &lt;enumeration value="3.2"/>
                             *             &lt;enumeration value="3.3"/>
                             *             &lt;enumeration value="3.4"/>
                             *             &lt;enumeration value="3.5"/>
                             *             &lt;enumeration value="3.6"/>
                             *             &lt;enumeration value="3.7"/>
                             *             &lt;enumeration value="3.8"/>
                             *             &lt;enumeration value="4.1"/>
                             *             &lt;enumeration value="4.2"/>
                             *             &lt;enumeration value="4.3"/>
                             *             &lt;enumeration value="5.1"/>
                             *             &lt;enumeration value="5.2"/>
                             *             &lt;enumeration value="5.3"/>
                             *             &lt;enumeration value="5.4"/>
                             *             &lt;enumeration value="5.5"/>
                             *             &lt;enumeration value="6.1"/>
                             *             &lt;enumeration value="6.2"/>
                             *             &lt;enumeration value="6.3"/>
                             *             &lt;enumeration value="7.1"/>
                             *             &lt;enumeration value="7.2"/>
                             *             &lt;enumeration value="7.3"/>
                             *             &lt;enumeration value="8.1"/>
                             *             &lt;enumeration value="8.2"/>
                             *             &lt;enumeration value="8.3"/>
                             *             &lt;enumeration value="8.4"/>
                             *             &lt;enumeration value="8.5"/>
                             *             &lt;enumeration value="8.6"/>
                             *             &lt;enumeration value="8.7"/>
                             *             &lt;enumeration value="9.1"/>
                             *             &lt;enumeration value="9.2"/>
                             *             &lt;enumeration value="10.1"/>
                             *             &lt;enumeration value="10.2"/>
                             *             &lt;enumeration value="10.3"/>
                             *             &lt;enumeration value="10.4"/>
                             *             &lt;enumeration value="10.5"/>
                             *             &lt;enumeration value="10.6"/>
                             *             &lt;enumeration value="11.1"/>
                             *             &lt;enumeration value="11.2"/>
                             *             &lt;enumeration value="11.3"/>
                             *             &lt;enumeration value="12.1"/>
                             *             &lt;enumeration value="13.1"/>
                             *           &lt;/restriction>
                             *         &lt;/simpleType>
                             *       &lt;/attribute>
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "donneesComposant"
                            })
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public static class SousLot {

                                @XmlElement(name = "donnees_composant", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected List<TDonneeComposant> donneesComposant;
                                @XmlAttribute(name = "ref", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected String ref;

                                /**
                                 * Gets the value of the donneesComposant property.
                                 * 
                                 * <p>
                                 * This accessor method returns a reference to the live list,
                                 * not a snapshot. Therefore any modification you make to the
                                 * returned list will be present inside the JAXB object.
                                 * This is why there is not a <CODE>set</CODE> method for the donneesComposant property.
                                 * 
                                 * <p>
                                 * For example, to add a new item, do as follows:
                                 * <pre>
                                 *    getDonneesComposant().add(newItem);
                                 * </pre>
                                 * 
                                 * 
                                 * <p>
                                 * Objects of the following type(s) are allowed in the list
                                 * {@link TDonneeComposant }
                                 * 
                                 * 
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public List<TDonneeComposant> getDonneesComposant() {
                                    if (donneesComposant == null) {
                                        donneesComposant = new ArrayList<TDonneeComposant>();
                                    }
                                    return this.donneesComposant;
                                }

                                /**
                                 * Gets the value of the ref property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public String getRef() {
                                    return ref;
                                }

                                /**
                                 * Sets the value of the ref property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setRef(String value) {
                                    this.ref = value;
                                }

                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="sous_contributeur" maxOccurs="3">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "sousContributeur"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Eau {

                        @XmlElement(name = "sous_contributeur", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau.SousContributeur> sousContributeur;

                        /**
                         * Gets the value of the sousContributeur property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousContributeur().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau.SousContributeur }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau.SousContributeur> getSousContributeur() {
                            if (sousContributeur == null) {
                                sousContributeur = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Eau.SousContributeur>();
                            }
                            return this.sousContributeur;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                         *       &lt;/sequence>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "donneesService"
                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousContributeur {

                            @XmlElement(name = "donnees_service", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<TDonneesService> donneesService;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected int ref;

                            /**
                             * Gets the value of the donneesService property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the donneesService property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDonneesService().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link TDonneesService }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<TDonneesService> getDonneesService() {
                                if (donneesService == null) {
                                    donneesService = new ArrayList<TDonneesService>();
                                }
                                return this.donneesService;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(int value) {
                                this.ref = value;
                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="sous_contributeur" maxOccurs="8">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                       &lt;enumeration value="4"/>
                     *                       &lt;enumeration value="5"/>
                     *                       &lt;enumeration value="6"/>
                     *                       &lt;enumeration value="7"/>
                     *                       &lt;enumeration value="8"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "sousContributeur"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Energie {

                        @XmlElement(name = "sous_contributeur", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie.SousContributeur> sousContributeur;

                        /**
                         * Gets the value of the sousContributeur property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousContributeur().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie.SousContributeur }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie.SousContributeur> getSousContributeur() {
                            if (sousContributeur == null) {
                                sousContributeur = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Energie.SousContributeur>();
                            }
                            return this.sousContributeur;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                         *       &lt;/sequence>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *             &lt;enumeration value="4"/>
                         *             &lt;enumeration value="5"/>
                         *             &lt;enumeration value="6"/>
                         *             &lt;enumeration value="7"/>
                         *             &lt;enumeration value="8"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "donneesService"
                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousContributeur {

                            @XmlElement(name = "donnees_service", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<TDonneesService> donneesService;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected int ref;

                            /**
                             * Gets the value of the donneesService property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the donneesService property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDonneesService().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link TDonneesService }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<TDonneesService> getDonneesService() {
                                if (donneesService == null) {
                                    donneesService = new ArrayList<TDonneesService>();
                                }
                                return this.donneesService;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(int value) {
                                this.ref = value;
                            }

                        }

                    }

                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="contributeur" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="composant" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="eau" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="chantier" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="surface_parcelle" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="surface_arrosee" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="surface_veg" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="surface_imper" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "contributeur",
            "surfaceParcelle",
            "surfaceArrosee",
            "surfaceVeg",
            "surfaceImper"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class Parcelle {

            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected RSEnv.EntreeProjet.Parcelle.Contributeur contributeur;
            @XmlElement(name = "surface_parcelle")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected int surfaceParcelle;
            @XmlElement(name = "surface_arrosee")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected int surfaceArrosee;
            @XmlElement(name = "surface_veg")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected Integer surfaceVeg;
            @XmlElement(name = "surface_imper")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected Integer surfaceImper;

            /**
             * Gets the value of the contributeur property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public RSEnv.EntreeProjet.Parcelle.Contributeur getContributeur() {
                return contributeur;
            }

            /**
             * Sets the value of the contributeur property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setContributeur(RSEnv.EntreeProjet.Parcelle.Contributeur value) {
                this.contributeur = value;
            }

            /**
             * Gets the value of the surfaceParcelle property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public int getSurfaceParcelle() {
                return surfaceParcelle;
            }

            /**
             * Sets the value of the surfaceParcelle property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setSurfaceParcelle(int value) {
                this.surfaceParcelle = value;
            }

            /**
             * Gets the value of the surfaceArrosee property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public int getSurfaceArrosee() {
                return surfaceArrosee;
            }

            /**
             * Sets the value of the surfaceArrosee property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setSurfaceArrosee(int value) {
                this.surfaceArrosee = value;
            }

            /**
             * Gets the value of the surfaceVeg property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public Integer getSurfaceVeg() {
                return surfaceVeg;
            }

            /**
             * Sets the value of the surfaceVeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setSurfaceVeg(Integer value) {
                this.surfaceVeg = value;
            }

            /**
             * Gets the value of the surfaceImper property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public Integer getSurfaceImper() {
                return surfaceImper;
            }

            /**
             * Sets the value of the surfaceImper property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setSurfaceImper(Integer value) {
                this.surfaceImper = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;all>
             *         &lt;element name="composant" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="eau" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="chantier" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/all>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {

            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class Contributeur {

                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.EntreeProjet.Parcelle.Contributeur.Composant composant;
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.EntreeProjet.Parcelle.Contributeur.Eau eau;
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier chantier;

                /**
                 * Gets the value of the composant property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur.Composant }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.EntreeProjet.Parcelle.Contributeur.Composant getComposant() {
                    return composant;
                }

                /**
                 * Sets the value of the composant property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur.Composant }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setComposant(RSEnv.EntreeProjet.Parcelle.Contributeur.Composant value) {
                    this.composant = value;
                }

                /**
                 * Gets the value of the eau property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur.Eau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.EntreeProjet.Parcelle.Contributeur.Eau getEau() {
                    return eau;
                }

                /**
                 * Sets the value of the eau property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur.Eau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setEau(RSEnv.EntreeProjet.Parcelle.Contributeur.Eau value) {
                    this.eau = value;
                }

                /**
                 * Gets the value of the chantier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier getChantier() {
                    return chantier;
                }

                /**
                 * Sets the value of the chantier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setChantier(RSEnv.EntreeProjet.Parcelle.Contributeur.Chantier value) {
                    this.chantier = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "donneesService"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Chantier {

                    @XmlElement(name = "donnees_service", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<TDonneesService> donneesService;

                    /**
                     * Gets the value of the donneesService property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the donneesService property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDonneesService().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link TDonneesService }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<TDonneesService> getDonneesService() {
                        if (donneesService == null) {
                            donneesService = new ArrayList<TDonneesService>();
                        }
                        return this.donneesService;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "donneesComposant"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Composant {

                    @XmlElement(name = "donnees_composant", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<TDonneeComposant> donneesComposant;

                    /**
                     * Gets the value of the donneesComposant property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the donneesComposant property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDonneesComposant().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link TDonneeComposant }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<TDonneeComposant> getDonneesComposant() {
                        if (donneesComposant == null) {
                            donneesComposant = new ArrayList<TDonneeComposant>();
                        }
                        return this.donneesComposant;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="donnees_service" type="{}t_donnees_service" maxOccurs="unbounded"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "donneesService"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Eau {

                    @XmlElement(name = "donnees_service", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<TDonneesService> donneesService;

                    /**
                     * Gets the value of the donneesService property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the donneesService property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDonneesService().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link TDonneesService }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<TDonneesService> getDonneesService() {
                        if (donneesService == null) {
                            donneesService = new ArrayList<TDonneesService>();
                        }
                        return this.donneesService;
                    }

                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;all>
         *         &lt;element name="identifiant">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;whiteSpace value="collapse"/>
         *               &lt;pattern value="[0-9]{4}[CF]"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="1"/>
         *               &lt;enumeration value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nom" type="{}p_string500"/>
         *         &lt;element name="localisation" type="{}p_string500"/>
         *         &lt;element name="contenu_co2">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *       &lt;/all>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {

        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class Reseau {

            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String identifiant;
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected int type;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String nom;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String localisation;
            @XmlElement(name = "contenu_co2")
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected float contenuCo2;

            /**
             * Gets the value of the identifiant property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getIdentifiant() {
                return identifiant;
            }

            /**
             * Sets the value of the identifiant property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIdentifiant(String value) {
                this.identifiant = value;
            }

            /**
             * Gets the value of the type property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public int getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setType(int value) {
                this.type = value;
            }

            /**
             * Gets the value of the nom property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getNom() {
                return nom;
            }

            /**
             * Sets the value of the nom property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setNom(String value) {
                this.nom = value;
            }

            /**
             * Gets the value of the localisation property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getLocalisation() {
                return localisation;
            }

            /**
             * Sets the value of the localisation property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setLocalisation(String value) {
                this.localisation = value;
            }

            /**
             * Gets the value of the contenuCo2 property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public float getContenuCo2() {
                return contenuCo2;
            }

            /**
             * Sets the value of the contenuCo2 property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setContenuCo2(float value) {
                this.contenuCo2 = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="batiment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="contributeur">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="composant">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                       &lt;element name="lot" maxOccurs="13" minOccurs="9">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                 &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;all>
     *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                           &lt;element name="stock_c">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="udd">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="ic">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="ic_ded">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                         &lt;/all>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                               &lt;enumeration value="1.1"/>
     *                                                               &lt;enumeration value="1.2"/>
     *                                                               &lt;enumeration value="1.3"/>
     *                                                               &lt;enumeration value="2.1"/>
     *                                                               &lt;enumeration value="2.2"/>
     *                                                               &lt;enumeration value="2.3"/>
     *                                                               &lt;enumeration value="3.1"/>
     *                                                               &lt;enumeration value="3.2"/>
     *                                                               &lt;enumeration value="3.3"/>
     *                                                               &lt;enumeration value="3.4"/>
     *                                                               &lt;enumeration value="3.5"/>
     *                                                               &lt;enumeration value="3.6"/>
     *                                                               &lt;enumeration value="3.7"/>
     *                                                               &lt;enumeration value="3.8"/>
     *                                                               &lt;enumeration value="4.1"/>
     *                                                               &lt;enumeration value="4.2"/>
     *                                                               &lt;enumeration value="4.3"/>
     *                                                               &lt;enumeration value="5.1"/>
     *                                                               &lt;enumeration value="5.2"/>
     *                                                               &lt;enumeration value="5.3"/>
     *                                                               &lt;enumeration value="5.4"/>
     *                                                               &lt;enumeration value="5.5"/>
     *                                                               &lt;enumeration value="6.1"/>
     *                                                               &lt;enumeration value="6.2"/>
     *                                                               &lt;enumeration value="6.3"/>
     *                                                               &lt;enumeration value="7.1"/>
     *                                                               &lt;enumeration value="7.2"/>
     *                                                               &lt;enumeration value="7.3"/>
     *                                                               &lt;enumeration value="8.1"/>
     *                                                               &lt;enumeration value="8.2"/>
     *                                                               &lt;enumeration value="8.3"/>
     *                                                               &lt;enumeration value="8.4"/>
     *                                                               &lt;enumeration value="8.5"/>
     *                                                               &lt;enumeration value="8.6"/>
     *                                                               &lt;enumeration value="8.7"/>
     *                                                               &lt;enumeration value="9.1"/>
     *                                                               &lt;enumeration value="9.2"/>
     *                                                               &lt;enumeration value="10.1"/>
     *                                                               &lt;enumeration value="10.2"/>
     *                                                               &lt;enumeration value="10.3"/>
     *                                                               &lt;enumeration value="10.4"/>
     *                                                               &lt;enumeration value="10.5"/>
     *                                                               &lt;enumeration value="10.6"/>
     *                                                               &lt;enumeration value="11.1"/>
     *                                                               &lt;enumeration value="11.2"/>
     *                                                               &lt;enumeration value="11.3"/>
     *                                                               &lt;enumeration value="12.1"/>
     *                                                               &lt;enumeration value="13.1"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="stock_c">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="udd">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="ic">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="ic_ded">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="ref" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                     &lt;enumeration value="1"/>
     *                                                     &lt;enumeration value="2"/>
     *                                                     &lt;enumeration value="3"/>
     *                                                     &lt;enumeration value="4"/>
     *                                                     &lt;enumeration value="5"/>
     *                                                     &lt;enumeration value="6"/>
     *                                                     &lt;enumeration value="7"/>
     *                                                     &lt;enumeration value="8"/>
     *                                                     &lt;enumeration value="9"/>
     *                                                     &lt;enumeration value="10"/>
     *                                                     &lt;enumeration value="11"/>
     *                                                     &lt;enumeration value="12"/>
     *                                                     &lt;enumeration value="13"/>
     *                                                     &lt;enumeration value="14"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="stock_c">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="udd">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="energie">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                       &lt;element name="sous_contributeur" maxOccurs="8">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;all>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                               &lt;/all>
     *                                               &lt;attribute name="ref" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                     &lt;enumeration value="1"/>
     *                                                     &lt;enumeration value="2"/>
     *                                                     &lt;enumeration value="3"/>
     *                                                     &lt;enumeration value="4"/>
     *                                                     &lt;enumeration value="5"/>
     *                                                     &lt;enumeration value="6"/>
     *                                                     &lt;enumeration value="7"/>
     *                                                     &lt;enumeration value="8"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="eau">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                       &lt;element name="sous_contributeur" maxOccurs="3">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;all>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                               &lt;/all>
     *                                               &lt;attribute name="ref" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                     &lt;enumeration value="1"/>
     *                                                     &lt;enumeration value="2"/>
     *                                                     &lt;enumeration value="3"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="chantier">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                       &lt;element name="sous_contributeur" maxOccurs="4">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;all>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                               &lt;/all>
     *                                               &lt;attribute name="ref" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                     &lt;enumeration value="1"/>
     *                                                     &lt;enumeration value="2"/>
     *                                                     &lt;enumeration value="3"/>
     *                                                     &lt;enumeration value="4"/>
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                   &lt;element name="indicateur_perf_env">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_batiment" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_batiment_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="stock_c_batiment">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="stock_c_parcelle">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="udd">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="zone" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="contributeur">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="composant">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                 &lt;element name="lot" maxOccurs="13" minOccurs="9">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                           &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
     *                                                             &lt;complexType>
     *                                                               &lt;complexContent>
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                                   &lt;all>
     *                                                                     &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                                     &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                                     &lt;element name="stock_c">
     *                                                                       &lt;simpleType>
     *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                                         &lt;/restriction>
     *                                                                       &lt;/simpleType>
     *                                                                     &lt;/element>
     *                                                                     &lt;element name="udd">
     *                                                                       &lt;simpleType>
     *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                                         &lt;/restriction>
     *                                                                       &lt;/simpleType>
     *                                                                     &lt;/element>
     *                                                                     &lt;element name="ic">
     *                                                                       &lt;simpleType>
     *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                                         &lt;/restriction>
     *                                                                       &lt;/simpleType>
     *                                                                     &lt;/element>
     *                                                                     &lt;element name="ic_ded">
     *                                                                       &lt;simpleType>
     *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                                         &lt;/restriction>
     *                                                                       &lt;/simpleType>
     *                                                                     &lt;/element>
     *                                                                   &lt;/all>
     *                                                                   &lt;attribute name="ref" use="required">
     *                                                                     &lt;simpleType>
     *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                                         &lt;enumeration value="1.1"/>
     *                                                                         &lt;enumeration value="1.2"/>
     *                                                                         &lt;enumeration value="1.3"/>
     *                                                                         &lt;enumeration value="2.1"/>
     *                                                                         &lt;enumeration value="2.2"/>
     *                                                                         &lt;enumeration value="2.3"/>
     *                                                                         &lt;enumeration value="3.1"/>
     *                                                                         &lt;enumeration value="3.2"/>
     *                                                                         &lt;enumeration value="3.3"/>
     *                                                                         &lt;enumeration value="3.4"/>
     *                                                                         &lt;enumeration value="3.5"/>
     *                                                                         &lt;enumeration value="3.6"/>
     *                                                                         &lt;enumeration value="3.7"/>
     *                                                                         &lt;enumeration value="3.8"/>
     *                                                                         &lt;enumeration value="4.1"/>
     *                                                                         &lt;enumeration value="4.2"/>
     *                                                                         &lt;enumeration value="4.3"/>
     *                                                                         &lt;enumeration value="5.1"/>
     *                                                                         &lt;enumeration value="5.2"/>
     *                                                                         &lt;enumeration value="5.3"/>
     *                                                                         &lt;enumeration value="5.4"/>
     *                                                                         &lt;enumeration value="5.5"/>
     *                                                                         &lt;enumeration value="6.1"/>
     *                                                                         &lt;enumeration value="6.2"/>
     *                                                                         &lt;enumeration value="6.3"/>
     *                                                                         &lt;enumeration value="7.1"/>
     *                                                                         &lt;enumeration value="7.2"/>
     *                                                                         &lt;enumeration value="7.3"/>
     *                                                                         &lt;enumeration value="8.1"/>
     *                                                                         &lt;enumeration value="8.2"/>
     *                                                                         &lt;enumeration value="8.3"/>
     *                                                                         &lt;enumeration value="8.4"/>
     *                                                                         &lt;enumeration value="8.5"/>
     *                                                                         &lt;enumeration value="8.6"/>
     *                                                                         &lt;enumeration value="8.7"/>
     *                                                                         &lt;enumeration value="9.1"/>
     *                                                                         &lt;enumeration value="9.2"/>
     *                                                                         &lt;enumeration value="10.1"/>
     *                                                                         &lt;enumeration value="10.2"/>
     *                                                                         &lt;enumeration value="10.3"/>
     *                                                                         &lt;enumeration value="10.4"/>
     *                                                                         &lt;enumeration value="10.5"/>
     *                                                                         &lt;enumeration value="10.6"/>
     *                                                                         &lt;enumeration value="11.1"/>
     *                                                                         &lt;enumeration value="11.2"/>
     *                                                                         &lt;enumeration value="11.3"/>
     *                                                                         &lt;enumeration value="12.1"/>
     *                                                                         &lt;enumeration value="13.1"/>
     *                                                                       &lt;/restriction>
     *                                                                     &lt;/simpleType>
     *                                                                   &lt;/attribute>
     *                                                                 &lt;/restriction>
     *                                                               &lt;/complexContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="stock_c">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="udd">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="ic">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="ic_ded">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                               &lt;enumeration value="4"/>
     *                                                               &lt;enumeration value="5"/>
     *                                                               &lt;enumeration value="6"/>
     *                                                               &lt;enumeration value="7"/>
     *                                                               &lt;enumeration value="8"/>
     *                                                               &lt;enumeration value="9"/>
     *                                                               &lt;enumeration value="10"/>
     *                                                               &lt;enumeration value="11"/>
     *                                                               &lt;enumeration value="12"/>
     *                                                               &lt;enumeration value="13"/>
     *                                                               &lt;enumeration value="14"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="stock_c">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="udd">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="energie">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                 &lt;element name="sous_contributeur" maxOccurs="8">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;all>
     *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                         &lt;/all>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                               &lt;enumeration value="4"/>
     *                                                               &lt;enumeration value="5"/>
     *                                                               &lt;enumeration value="6"/>
     *                                                               &lt;enumeration value="7"/>
     *                                                               &lt;enumeration value="8"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="eau">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                 &lt;element name="sous_contributeur" maxOccurs="3">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;all>
     *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                         &lt;/all>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="chantier">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                 &lt;element name="sous_contributeur" maxOccurs="4">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;all>
     *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                                         &lt;/all>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                               &lt;enumeration value="1"/>
     *                                                               &lt;enumeration value="2"/>
     *                                                               &lt;enumeration value="3"/>
     *                                                               &lt;enumeration value="4"/>
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                             &lt;element name="indicateur_perf_env">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;all>
     *                                       &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="coef_mod_icconstruction" type="{}t_coef_mod_icconstruction"/>
     *                                       &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="coef_mod_icenergie" type="{}t_coef_mod_icenergie"/>
     *                                       &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_zone" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_zone_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                       &lt;element name="stock_c">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="udd">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                     &lt;/all>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="parcelle" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                   &lt;element name="contributeur">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="composant" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                       &lt;element name="stock_c">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="udd">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="ic">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="ic_ded">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="eau" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="chantier" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "batiment",
        "parcelle"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class SortieProjet {

        @XmlElement(required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected List<RSEnv.SortieProjet.Batiment> batiment;
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected RSEnv.SortieProjet.Parcelle parcelle;

        /**
         * Gets the value of the batiment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the batiment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBatiment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RSEnv.SortieProjet.Batiment }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public List<RSEnv.SortieProjet.Batiment> getBatiment() {
            if (batiment == null) {
                batiment = new ArrayList<RSEnv.SortieProjet.Batiment>();
            }
            return this.batiment;
        }

        /**
         * Gets the value of the parcelle property.
         * 
         * @return
         *     possible object is
         *     {@link RSEnv.SortieProjet.Parcelle }
         *     
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public RSEnv.SortieProjet.Parcelle getParcelle() {
            return parcelle;
        }

        /**
         * Sets the value of the parcelle property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSEnv.SortieProjet.Parcelle }
         *     
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public void setParcelle(RSEnv.SortieProjet.Parcelle value) {
            this.parcelle = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="contributeur">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="composant">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                             &lt;element name="lot" maxOccurs="13" minOccurs="9">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                       &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;all>
         *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                                 &lt;element name="stock_c">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="udd">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="ic">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="ic_ded">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                               &lt;/all>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                                     &lt;enumeration value="1.1"/>
         *                                                     &lt;enumeration value="1.2"/>
         *                                                     &lt;enumeration value="1.3"/>
         *                                                     &lt;enumeration value="2.1"/>
         *                                                     &lt;enumeration value="2.2"/>
         *                                                     &lt;enumeration value="2.3"/>
         *                                                     &lt;enumeration value="3.1"/>
         *                                                     &lt;enumeration value="3.2"/>
         *                                                     &lt;enumeration value="3.3"/>
         *                                                     &lt;enumeration value="3.4"/>
         *                                                     &lt;enumeration value="3.5"/>
         *                                                     &lt;enumeration value="3.6"/>
         *                                                     &lt;enumeration value="3.7"/>
         *                                                     &lt;enumeration value="3.8"/>
         *                                                     &lt;enumeration value="4.1"/>
         *                                                     &lt;enumeration value="4.2"/>
         *                                                     &lt;enumeration value="4.3"/>
         *                                                     &lt;enumeration value="5.1"/>
         *                                                     &lt;enumeration value="5.2"/>
         *                                                     &lt;enumeration value="5.3"/>
         *                                                     &lt;enumeration value="5.4"/>
         *                                                     &lt;enumeration value="5.5"/>
         *                                                     &lt;enumeration value="6.1"/>
         *                                                     &lt;enumeration value="6.2"/>
         *                                                     &lt;enumeration value="6.3"/>
         *                                                     &lt;enumeration value="7.1"/>
         *                                                     &lt;enumeration value="7.2"/>
         *                                                     &lt;enumeration value="7.3"/>
         *                                                     &lt;enumeration value="8.1"/>
         *                                                     &lt;enumeration value="8.2"/>
         *                                                     &lt;enumeration value="8.3"/>
         *                                                     &lt;enumeration value="8.4"/>
         *                                                     &lt;enumeration value="8.5"/>
         *                                                     &lt;enumeration value="8.6"/>
         *                                                     &lt;enumeration value="8.7"/>
         *                                                     &lt;enumeration value="9.1"/>
         *                                                     &lt;enumeration value="9.2"/>
         *                                                     &lt;enumeration value="10.1"/>
         *                                                     &lt;enumeration value="10.2"/>
         *                                                     &lt;enumeration value="10.3"/>
         *                                                     &lt;enumeration value="10.4"/>
         *                                                     &lt;enumeration value="10.5"/>
         *                                                     &lt;enumeration value="10.6"/>
         *                                                     &lt;enumeration value="11.1"/>
         *                                                     &lt;enumeration value="11.2"/>
         *                                                     &lt;enumeration value="11.3"/>
         *                                                     &lt;enumeration value="12.1"/>
         *                                                     &lt;enumeration value="13.1"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                       &lt;element name="stock_c">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="udd">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="ic">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="ic_ded">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="ref" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                           &lt;enumeration value="1"/>
         *                                           &lt;enumeration value="2"/>
         *                                           &lt;enumeration value="3"/>
         *                                           &lt;enumeration value="4"/>
         *                                           &lt;enumeration value="5"/>
         *                                           &lt;enumeration value="6"/>
         *                                           &lt;enumeration value="7"/>
         *                                           &lt;enumeration value="8"/>
         *                                           &lt;enumeration value="9"/>
         *                                           &lt;enumeration value="10"/>
         *                                           &lt;enumeration value="11"/>
         *                                           &lt;enumeration value="12"/>
         *                                           &lt;enumeration value="13"/>
         *                                           &lt;enumeration value="14"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="stock_c">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="udd">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="energie">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                             &lt;element name="sous_contributeur" maxOccurs="8">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;all>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                     &lt;/all>
         *                                     &lt;attribute name="ref" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                           &lt;enumeration value="1"/>
         *                                           &lt;enumeration value="2"/>
         *                                           &lt;enumeration value="3"/>
         *                                           &lt;enumeration value="4"/>
         *                                           &lt;enumeration value="5"/>
         *                                           &lt;enumeration value="6"/>
         *                                           &lt;enumeration value="7"/>
         *                                           &lt;enumeration value="8"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="eau">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                             &lt;element name="sous_contributeur" maxOccurs="3">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;all>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                     &lt;/all>
         *                                     &lt;attribute name="ref" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                           &lt;enumeration value="1"/>
         *                                           &lt;enumeration value="2"/>
         *                                           &lt;enumeration value="3"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="chantier">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                             &lt;element name="sous_contributeur" maxOccurs="4">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;all>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                     &lt;/all>
         *                                     &lt;attribute name="ref" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                           &lt;enumeration value="1"/>
         *                                           &lt;enumeration value="2"/>
         *                                           &lt;enumeration value="3"/>
         *                                           &lt;enumeration value="4"/>
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *         &lt;element name="indicateur_perf_env">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_batiment" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_batiment_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="stock_c_batiment">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="stock_c_parcelle">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="udd">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="zone" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="contributeur">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="composant">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                       &lt;element name="lot" maxOccurs="13" minOccurs="9">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                                 &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
         *                                                   &lt;complexType>
         *                                                     &lt;complexContent>
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                                         &lt;all>
         *                                                           &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                                           &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                                           &lt;element name="stock_c">
         *                                                             &lt;simpleType>
         *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                               &lt;/restriction>
         *                                                             &lt;/simpleType>
         *                                                           &lt;/element>
         *                                                           &lt;element name="udd">
         *                                                             &lt;simpleType>
         *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                               &lt;/restriction>
         *                                                             &lt;/simpleType>
         *                                                           &lt;/element>
         *                                                           &lt;element name="ic">
         *                                                             &lt;simpleType>
         *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                               &lt;/restriction>
         *                                                             &lt;/simpleType>
         *                                                           &lt;/element>
         *                                                           &lt;element name="ic_ded">
         *                                                             &lt;simpleType>
         *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                               &lt;/restriction>
         *                                                             &lt;/simpleType>
         *                                                           &lt;/element>
         *                                                         &lt;/all>
         *                                                         &lt;attribute name="ref" use="required">
         *                                                           &lt;simpleType>
         *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                                               &lt;enumeration value="1.1"/>
         *                                                               &lt;enumeration value="1.2"/>
         *                                                               &lt;enumeration value="1.3"/>
         *                                                               &lt;enumeration value="2.1"/>
         *                                                               &lt;enumeration value="2.2"/>
         *                                                               &lt;enumeration value="2.3"/>
         *                                                               &lt;enumeration value="3.1"/>
         *                                                               &lt;enumeration value="3.2"/>
         *                                                               &lt;enumeration value="3.3"/>
         *                                                               &lt;enumeration value="3.4"/>
         *                                                               &lt;enumeration value="3.5"/>
         *                                                               &lt;enumeration value="3.6"/>
         *                                                               &lt;enumeration value="3.7"/>
         *                                                               &lt;enumeration value="3.8"/>
         *                                                               &lt;enumeration value="4.1"/>
         *                                                               &lt;enumeration value="4.2"/>
         *                                                               &lt;enumeration value="4.3"/>
         *                                                               &lt;enumeration value="5.1"/>
         *                                                               &lt;enumeration value="5.2"/>
         *                                                               &lt;enumeration value="5.3"/>
         *                                                               &lt;enumeration value="5.4"/>
         *                                                               &lt;enumeration value="5.5"/>
         *                                                               &lt;enumeration value="6.1"/>
         *                                                               &lt;enumeration value="6.2"/>
         *                                                               &lt;enumeration value="6.3"/>
         *                                                               &lt;enumeration value="7.1"/>
         *                                                               &lt;enumeration value="7.2"/>
         *                                                               &lt;enumeration value="7.3"/>
         *                                                               &lt;enumeration value="8.1"/>
         *                                                               &lt;enumeration value="8.2"/>
         *                                                               &lt;enumeration value="8.3"/>
         *                                                               &lt;enumeration value="8.4"/>
         *                                                               &lt;enumeration value="8.5"/>
         *                                                               &lt;enumeration value="8.6"/>
         *                                                               &lt;enumeration value="8.7"/>
         *                                                               &lt;enumeration value="9.1"/>
         *                                                               &lt;enumeration value="9.2"/>
         *                                                               &lt;enumeration value="10.1"/>
         *                                                               &lt;enumeration value="10.2"/>
         *                                                               &lt;enumeration value="10.3"/>
         *                                                               &lt;enumeration value="10.4"/>
         *                                                               &lt;enumeration value="10.5"/>
         *                                                               &lt;enumeration value="10.6"/>
         *                                                               &lt;enumeration value="11.1"/>
         *                                                               &lt;enumeration value="11.2"/>
         *                                                               &lt;enumeration value="11.3"/>
         *                                                               &lt;enumeration value="12.1"/>
         *                                                               &lt;enumeration value="13.1"/>
         *                                                             &lt;/restriction>
         *                                                           &lt;/simpleType>
         *                                                         &lt;/attribute>
         *                                                       &lt;/restriction>
         *                                                     &lt;/complexContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="stock_c">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="udd">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="ic">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="ic_ded">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                     &lt;enumeration value="4"/>
         *                                                     &lt;enumeration value="5"/>
         *                                                     &lt;enumeration value="6"/>
         *                                                     &lt;enumeration value="7"/>
         *                                                     &lt;enumeration value="8"/>
         *                                                     &lt;enumeration value="9"/>
         *                                                     &lt;enumeration value="10"/>
         *                                                     &lt;enumeration value="11"/>
         *                                                     &lt;enumeration value="12"/>
         *                                                     &lt;enumeration value="13"/>
         *                                                     &lt;enumeration value="14"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                       &lt;element name="stock_c">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="udd">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="energie">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                       &lt;element name="sous_contributeur" maxOccurs="8">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;all>
         *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                               &lt;/all>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                     &lt;enumeration value="4"/>
         *                                                     &lt;enumeration value="5"/>
         *                                                     &lt;enumeration value="6"/>
         *                                                     &lt;enumeration value="7"/>
         *                                                     &lt;enumeration value="8"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="eau">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                       &lt;element name="sous_contributeur" maxOccurs="3">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;all>
         *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                               &lt;/all>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="chantier">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                       &lt;element name="sous_contributeur" maxOccurs="4">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;all>
         *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                                               &lt;/all>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                                     &lt;enumeration value="1"/>
         *                                                     &lt;enumeration value="2"/>
         *                                                     &lt;enumeration value="3"/>
         *                                                     &lt;enumeration value="4"/>
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                   &lt;element name="indicateur_perf_env">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;all>
         *                             &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="coef_mod_icconstruction" type="{}t_coef_mod_icconstruction"/>
         *                             &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="coef_mod_icenergie" type="{}t_coef_mod_icenergie"/>
         *                             &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_zone" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_zone_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                             &lt;element name="stock_c">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="udd">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                           &lt;/all>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "index",
            "contributeur",
            "indicateursAcvCollection",
            "indicateurCo2Dynamique",
            "indicateurPerfEnv",
            "zone"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class Batiment {

            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected int index;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected RSEnv.SortieProjet.Batiment.Contributeur contributeur;
            @XmlElement(name = "indicateurs_acv_collection", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected TIndicateur indicateursAcvCollection;
            @XmlElement(name = "indicateur_co2_dynamique", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
            @XmlElement(name = "indicateur_perf_env", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected RSEnv.SortieProjet.Batiment.IndicateurPerfEnv indicateurPerfEnv;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected List<RSEnv.SortieProjet.Batiment.Zone> zone;

            /**
             * Gets the value of the index property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public int getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndex(int value) {
                this.index = value;
            }

            /**
             * Gets the value of the contributeur property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.SortieProjet.Batiment.Contributeur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public RSEnv.SortieProjet.Batiment.Contributeur getContributeur() {
                return contributeur;
            }

            /**
             * Sets the value of the contributeur property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.SortieProjet.Batiment.Contributeur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setContributeur(RSEnv.SortieProjet.Batiment.Contributeur value) {
                this.contributeur = value;
            }

            /**
             * Gets the value of the indicateursAcvCollection property.
             * 
             * @return
             *     possible object is
             *     {@link TIndicateur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public TIndicateur getIndicateursAcvCollection() {
                return indicateursAcvCollection;
            }

            /**
             * Sets the value of the indicateursAcvCollection property.
             * 
             * @param value
             *     allowed object is
             *     {@link TIndicateur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndicateursAcvCollection(TIndicateur value) {
                this.indicateursAcvCollection = value;
            }

            /**
             * Gets the value of the indicateurCo2Dynamique property.
             * 
             * @return
             *     possible object is
             *     {@link TIndicateurCo2Dynamique }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                return indicateurCo2Dynamique;
            }

            /**
             * Sets the value of the indicateurCo2Dynamique property.
             * 
             * @param value
             *     allowed object is
             *     {@link TIndicateurCo2Dynamique }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                this.indicateurCo2Dynamique = value;
            }

            /**
             * Gets the value of the indicateurPerfEnv property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.SortieProjet.Batiment.IndicateurPerfEnv }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public RSEnv.SortieProjet.Batiment.IndicateurPerfEnv getIndicateurPerfEnv() {
                return indicateurPerfEnv;
            }

            /**
             * Sets the value of the indicateurPerfEnv property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.SortieProjet.Batiment.IndicateurPerfEnv }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndicateurPerfEnv(RSEnv.SortieProjet.Batiment.IndicateurPerfEnv value) {
                this.indicateurPerfEnv = value;
            }

            /**
             * Gets the value of the zone property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the zone property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getZone().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RSEnv.SortieProjet.Batiment.Zone }
             * 
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public List<RSEnv.SortieProjet.Batiment.Zone> getZone() {
                if (zone == null) {
                    zone = new ArrayList<RSEnv.SortieProjet.Batiment.Zone>();
                }
                return this.zone;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="composant">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                   &lt;element name="lot" maxOccurs="13" minOccurs="9">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                             &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;all>
             *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                                       &lt;element name="stock_c">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="udd">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="ic">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="ic_ded">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                     &lt;/all>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                           &lt;enumeration value="1.1"/>
             *                                           &lt;enumeration value="1.2"/>
             *                                           &lt;enumeration value="1.3"/>
             *                                           &lt;enumeration value="2.1"/>
             *                                           &lt;enumeration value="2.2"/>
             *                                           &lt;enumeration value="2.3"/>
             *                                           &lt;enumeration value="3.1"/>
             *                                           &lt;enumeration value="3.2"/>
             *                                           &lt;enumeration value="3.3"/>
             *                                           &lt;enumeration value="3.4"/>
             *                                           &lt;enumeration value="3.5"/>
             *                                           &lt;enumeration value="3.6"/>
             *                                           &lt;enumeration value="3.7"/>
             *                                           &lt;enumeration value="3.8"/>
             *                                           &lt;enumeration value="4.1"/>
             *                                           &lt;enumeration value="4.2"/>
             *                                           &lt;enumeration value="4.3"/>
             *                                           &lt;enumeration value="5.1"/>
             *                                           &lt;enumeration value="5.2"/>
             *                                           &lt;enumeration value="5.3"/>
             *                                           &lt;enumeration value="5.4"/>
             *                                           &lt;enumeration value="5.5"/>
             *                                           &lt;enumeration value="6.1"/>
             *                                           &lt;enumeration value="6.2"/>
             *                                           &lt;enumeration value="6.3"/>
             *                                           &lt;enumeration value="7.1"/>
             *                                           &lt;enumeration value="7.2"/>
             *                                           &lt;enumeration value="7.3"/>
             *                                           &lt;enumeration value="8.1"/>
             *                                           &lt;enumeration value="8.2"/>
             *                                           &lt;enumeration value="8.3"/>
             *                                           &lt;enumeration value="8.4"/>
             *                                           &lt;enumeration value="8.5"/>
             *                                           &lt;enumeration value="8.6"/>
             *                                           &lt;enumeration value="8.7"/>
             *                                           &lt;enumeration value="9.1"/>
             *                                           &lt;enumeration value="9.2"/>
             *                                           &lt;enumeration value="10.1"/>
             *                                           &lt;enumeration value="10.2"/>
             *                                           &lt;enumeration value="10.3"/>
             *                                           &lt;enumeration value="10.4"/>
             *                                           &lt;enumeration value="10.5"/>
             *                                           &lt;enumeration value="10.6"/>
             *                                           &lt;enumeration value="11.1"/>
             *                                           &lt;enumeration value="11.2"/>
             *                                           &lt;enumeration value="11.3"/>
             *                                           &lt;enumeration value="12.1"/>
             *                                           &lt;enumeration value="13.1"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                             &lt;element name="stock_c">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="udd">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="ic">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="ic_ded">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="ref" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                 &lt;enumeration value="1"/>
             *                                 &lt;enumeration value="2"/>
             *                                 &lt;enumeration value="3"/>
             *                                 &lt;enumeration value="4"/>
             *                                 &lt;enumeration value="5"/>
             *                                 &lt;enumeration value="6"/>
             *                                 &lt;enumeration value="7"/>
             *                                 &lt;enumeration value="8"/>
             *                                 &lt;enumeration value="9"/>
             *                                 &lt;enumeration value="10"/>
             *                                 &lt;enumeration value="11"/>
             *                                 &lt;enumeration value="12"/>
             *                                 &lt;enumeration value="13"/>
             *                                 &lt;enumeration value="14"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="stock_c">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="udd">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="energie">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                   &lt;element name="sous_contributeur" maxOccurs="8">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;all>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                           &lt;/all>
             *                           &lt;attribute name="ref" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                 &lt;enumeration value="1"/>
             *                                 &lt;enumeration value="2"/>
             *                                 &lt;enumeration value="3"/>
             *                                 &lt;enumeration value="4"/>
             *                                 &lt;enumeration value="5"/>
             *                                 &lt;enumeration value="6"/>
             *                                 &lt;enumeration value="7"/>
             *                                 &lt;enumeration value="8"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="eau">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                   &lt;element name="sous_contributeur" maxOccurs="3">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;all>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                           &lt;/all>
             *                           &lt;attribute name="ref" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                 &lt;enumeration value="1"/>
             *                                 &lt;enumeration value="2"/>
             *                                 &lt;enumeration value="3"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="chantier">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                   &lt;element name="sous_contributeur" maxOccurs="4">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;all>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                           &lt;/all>
             *                           &lt;attribute name="ref" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                 &lt;enumeration value="1"/>
             *                                 &lt;enumeration value="2"/>
             *                                 &lt;enumeration value="3"/>
             *                                 &lt;enumeration value="4"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "composant",
                "energie",
                "eau",
                "chantier"
            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class Contributeur {

                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Batiment.Contributeur.Composant composant;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Batiment.Contributeur.Energie energie;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Batiment.Contributeur.Eau eau;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Batiment.Contributeur.Chantier chantier;

                /**
                 * Gets the value of the composant property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Composant }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Batiment.Contributeur.Composant getComposant() {
                    return composant;
                }

                /**
                 * Sets the value of the composant property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Composant }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setComposant(RSEnv.SortieProjet.Batiment.Contributeur.Composant value) {
                    this.composant = value;
                }

                /**
                 * Gets the value of the energie property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Energie }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Batiment.Contributeur.Energie getEnergie() {
                    return energie;
                }

                /**
                 * Sets the value of the energie property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Energie }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setEnergie(RSEnv.SortieProjet.Batiment.Contributeur.Energie value) {
                    this.energie = value;
                }

                /**
                 * Gets the value of the eau property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Eau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Batiment.Contributeur.Eau getEau() {
                    return eau;
                }

                /**
                 * Sets the value of the eau property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Eau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setEau(RSEnv.SortieProjet.Batiment.Contributeur.Eau value) {
                    this.eau = value;
                }

                /**
                 * Gets the value of the chantier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Chantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Batiment.Contributeur.Chantier getChantier() {
                    return chantier;
                }

                /**
                 * Sets the value of the chantier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Contributeur.Chantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setChantier(RSEnv.SortieProjet.Batiment.Contributeur.Chantier value) {
                    this.chantier = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *         &lt;element name="sous_contributeur" maxOccurs="4">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;all>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                 &lt;/all>
                 *                 &lt;attribute name="ref" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                       &lt;enumeration value="1"/>
                 *                       &lt;enumeration value="2"/>
                 *                       &lt;enumeration value="3"/>
                 *                       &lt;enumeration value="4"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique",
                    "sousContributeur"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Chantier {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                    @XmlElement(name = "sous_contributeur", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<RSEnv.SortieProjet.Batiment.Contributeur.Chantier.SousContributeur> sousContributeur;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                    /**
                     * Gets the value of the sousContributeur property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getSousContributeur().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RSEnv.SortieProjet.Batiment.Contributeur.Chantier.SousContributeur }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<RSEnv.SortieProjet.Batiment.Contributeur.Chantier.SousContributeur> getSousContributeur() {
                        if (sousContributeur == null) {
                            sousContributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Chantier.SousContributeur>();
                        }
                        return this.sousContributeur;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;all>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *       &lt;/all>
                     *       &lt;attribute name="ref" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *             &lt;enumeration value="1"/>
                     *             &lt;enumeration value="2"/>
                     *             &lt;enumeration value="3"/>
                     *             &lt;enumeration value="4"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {

                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class SousContributeur {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlAttribute(name = "ref", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected String ref;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public String getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setRef(String value) {
                            this.ref = value;
                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *         &lt;element name="lot" maxOccurs="13" minOccurs="9">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                   &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;all>
                 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                             &lt;element name="stock_c">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="udd">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="ic">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="ic_ded">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                           &lt;/all>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                                 &lt;enumeration value="1.1"/>
                 *                                 &lt;enumeration value="1.2"/>
                 *                                 &lt;enumeration value="1.3"/>
                 *                                 &lt;enumeration value="2.1"/>
                 *                                 &lt;enumeration value="2.2"/>
                 *                                 &lt;enumeration value="2.3"/>
                 *                                 &lt;enumeration value="3.1"/>
                 *                                 &lt;enumeration value="3.2"/>
                 *                                 &lt;enumeration value="3.3"/>
                 *                                 &lt;enumeration value="3.4"/>
                 *                                 &lt;enumeration value="3.5"/>
                 *                                 &lt;enumeration value="3.6"/>
                 *                                 &lt;enumeration value="3.7"/>
                 *                                 &lt;enumeration value="3.8"/>
                 *                                 &lt;enumeration value="4.1"/>
                 *                                 &lt;enumeration value="4.2"/>
                 *                                 &lt;enumeration value="4.3"/>
                 *                                 &lt;enumeration value="5.1"/>
                 *                                 &lt;enumeration value="5.2"/>
                 *                                 &lt;enumeration value="5.3"/>
                 *                                 &lt;enumeration value="5.4"/>
                 *                                 &lt;enumeration value="5.5"/>
                 *                                 &lt;enumeration value="6.1"/>
                 *                                 &lt;enumeration value="6.2"/>
                 *                                 &lt;enumeration value="6.3"/>
                 *                                 &lt;enumeration value="7.1"/>
                 *                                 &lt;enumeration value="7.2"/>
                 *                                 &lt;enumeration value="7.3"/>
                 *                                 &lt;enumeration value="8.1"/>
                 *                                 &lt;enumeration value="8.2"/>
                 *                                 &lt;enumeration value="8.3"/>
                 *                                 &lt;enumeration value="8.4"/>
                 *                                 &lt;enumeration value="8.5"/>
                 *                                 &lt;enumeration value="8.6"/>
                 *                                 &lt;enumeration value="8.7"/>
                 *                                 &lt;enumeration value="9.1"/>
                 *                                 &lt;enumeration value="9.2"/>
                 *                                 &lt;enumeration value="10.1"/>
                 *                                 &lt;enumeration value="10.2"/>
                 *                                 &lt;enumeration value="10.3"/>
                 *                                 &lt;enumeration value="10.4"/>
                 *                                 &lt;enumeration value="10.5"/>
                 *                                 &lt;enumeration value="10.6"/>
                 *                                 &lt;enumeration value="11.1"/>
                 *                                 &lt;enumeration value="11.2"/>
                 *                                 &lt;enumeration value="11.3"/>
                 *                                 &lt;enumeration value="12.1"/>
                 *                                 &lt;enumeration value="13.1"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                   &lt;element name="stock_c">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="udd">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="ic">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="ic_ded">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="ref" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                       &lt;enumeration value="1"/>
                 *                       &lt;enumeration value="2"/>
                 *                       &lt;enumeration value="3"/>
                 *                       &lt;enumeration value="4"/>
                 *                       &lt;enumeration value="5"/>
                 *                       &lt;enumeration value="6"/>
                 *                       &lt;enumeration value="7"/>
                 *                       &lt;enumeration value="8"/>
                 *                       &lt;enumeration value="9"/>
                 *                       &lt;enumeration value="10"/>
                 *                       &lt;enumeration value="11"/>
                 *                       &lt;enumeration value="12"/>
                 *                       &lt;enumeration value="13"/>
                 *                       &lt;enumeration value="14"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="stock_c">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="udd">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique",
                    "lot",
                    "stockC",
                    "udd"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Composant {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot> lot;
                    @XmlElement(name = "stock_c", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal stockC;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal udd;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                    /**
                     * Gets the value of the lot property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the lot property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getLot().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot> getLot() {
                        if (lot == null) {
                            lot = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot>();
                        }
                        return this.lot;
                    }

                    /**
                     * Gets the value of the stockC property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getStockC() {
                        return stockC;
                    }

                    /**
                     * Sets the value of the stockC property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setStockC(BigDecimal value) {
                        this.stockC = value;
                    }

                    /**
                     * Gets the value of the udd property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getUdd() {
                        return udd;
                    }

                    /**
                     * Sets the value of the udd property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setUdd(BigDecimal value) {
                        this.udd = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *         &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;all>
                     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *                   &lt;element name="stock_c">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="udd">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="ic">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="ic_ded">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                 &lt;/all>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *                       &lt;enumeration value="1.1"/>
                     *                       &lt;enumeration value="1.2"/>
                     *                       &lt;enumeration value="1.3"/>
                     *                       &lt;enumeration value="2.1"/>
                     *                       &lt;enumeration value="2.2"/>
                     *                       &lt;enumeration value="2.3"/>
                     *                       &lt;enumeration value="3.1"/>
                     *                       &lt;enumeration value="3.2"/>
                     *                       &lt;enumeration value="3.3"/>
                     *                       &lt;enumeration value="3.4"/>
                     *                       &lt;enumeration value="3.5"/>
                     *                       &lt;enumeration value="3.6"/>
                     *                       &lt;enumeration value="3.7"/>
                     *                       &lt;enumeration value="3.8"/>
                     *                       &lt;enumeration value="4.1"/>
                     *                       &lt;enumeration value="4.2"/>
                     *                       &lt;enumeration value="4.3"/>
                     *                       &lt;enumeration value="5.1"/>
                     *                       &lt;enumeration value="5.2"/>
                     *                       &lt;enumeration value="5.3"/>
                     *                       &lt;enumeration value="5.4"/>
                     *                       &lt;enumeration value="5.5"/>
                     *                       &lt;enumeration value="6.1"/>
                     *                       &lt;enumeration value="6.2"/>
                     *                       &lt;enumeration value="6.3"/>
                     *                       &lt;enumeration value="7.1"/>
                     *                       &lt;enumeration value="7.2"/>
                     *                       &lt;enumeration value="7.3"/>
                     *                       &lt;enumeration value="8.1"/>
                     *                       &lt;enumeration value="8.2"/>
                     *                       &lt;enumeration value="8.3"/>
                     *                       &lt;enumeration value="8.4"/>
                     *                       &lt;enumeration value="8.5"/>
                     *                       &lt;enumeration value="8.6"/>
                     *                       &lt;enumeration value="8.7"/>
                     *                       &lt;enumeration value="9.1"/>
                     *                       &lt;enumeration value="9.2"/>
                     *                       &lt;enumeration value="10.1"/>
                     *                       &lt;enumeration value="10.2"/>
                     *                       &lt;enumeration value="10.3"/>
                     *                       &lt;enumeration value="10.4"/>
                     *                       &lt;enumeration value="10.5"/>
                     *                       &lt;enumeration value="10.6"/>
                     *                       &lt;enumeration value="11.1"/>
                     *                       &lt;enumeration value="11.2"/>
                     *                       &lt;enumeration value="11.3"/>
                     *                       &lt;enumeration value="12.1"/>
                     *                       &lt;enumeration value="13.1"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *         &lt;element name="stock_c">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="udd">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="ic">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="ic_ded">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="ref" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *             &lt;enumeration value="1"/>
                     *             &lt;enumeration value="2"/>
                     *             &lt;enumeration value="3"/>
                     *             &lt;enumeration value="4"/>
                     *             &lt;enumeration value="5"/>
                     *             &lt;enumeration value="6"/>
                     *             &lt;enumeration value="7"/>
                     *             &lt;enumeration value="8"/>
                     *             &lt;enumeration value="9"/>
                     *             &lt;enumeration value="10"/>
                     *             &lt;enumeration value="11"/>
                     *             &lt;enumeration value="12"/>
                     *             &lt;enumeration value="13"/>
                     *             &lt;enumeration value="14"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "indicateursAcvCollection",
                        "indicateurCo2Dynamique",
                        "sousLot",
                        "stockC",
                        "udd",
                        "ic",
                        "icDed"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Lot {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlElement(name = "sous_lot")
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot.SousLot> sousLot;
                        @XmlElement(name = "stock_c", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected BigDecimal stockC;
                        @XmlElement(required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected BigDecimal udd;
                        @XmlElement(required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected BigDecimal ic;
                        @XmlElement(name = "ic_ded", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected BigDecimal icDed;
                        @XmlAttribute(name = "ref", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected int ref;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the sousLot property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousLot property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousLot().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot.SousLot }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot.SousLot> getSousLot() {
                            if (sousLot == null) {
                                sousLot = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Composant.Lot.SousLot>();
                            }
                            return this.sousLot;
                        }

                        /**
                         * Gets the value of the stockC property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public BigDecimal getStockC() {
                            return stockC;
                        }

                        /**
                         * Sets the value of the stockC property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setStockC(BigDecimal value) {
                            this.stockC = value;
                        }

                        /**
                         * Gets the value of the udd property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public BigDecimal getUdd() {
                            return udd;
                        }

                        /**
                         * Sets the value of the udd property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setUdd(BigDecimal value) {
                            this.udd = value;
                        }

                        /**
                         * Gets the value of the ic property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public BigDecimal getIc() {
                            return ic;
                        }

                        /**
                         * Sets the value of the ic property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIc(BigDecimal value) {
                            this.ic = value;
                        }

                        /**
                         * Gets the value of the icDed property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public BigDecimal getIcDed() {
                            return icDed;
                        }

                        /**
                         * Sets the value of the icDed property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIcDed(BigDecimal value) {
                            this.icDed = value;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public int getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setRef(int value) {
                            this.ref = value;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;all>
                         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                         *         &lt;element name="stock_c">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="udd">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="ic">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="ic_ded">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *       &lt;/all>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                         *             &lt;enumeration value="1.1"/>
                         *             &lt;enumeration value="1.2"/>
                         *             &lt;enumeration value="1.3"/>
                         *             &lt;enumeration value="2.1"/>
                         *             &lt;enumeration value="2.2"/>
                         *             &lt;enumeration value="2.3"/>
                         *             &lt;enumeration value="3.1"/>
                         *             &lt;enumeration value="3.2"/>
                         *             &lt;enumeration value="3.3"/>
                         *             &lt;enumeration value="3.4"/>
                         *             &lt;enumeration value="3.5"/>
                         *             &lt;enumeration value="3.6"/>
                         *             &lt;enumeration value="3.7"/>
                         *             &lt;enumeration value="3.8"/>
                         *             &lt;enumeration value="4.1"/>
                         *             &lt;enumeration value="4.2"/>
                         *             &lt;enumeration value="4.3"/>
                         *             &lt;enumeration value="5.1"/>
                         *             &lt;enumeration value="5.2"/>
                         *             &lt;enumeration value="5.3"/>
                         *             &lt;enumeration value="5.4"/>
                         *             &lt;enumeration value="5.5"/>
                         *             &lt;enumeration value="6.1"/>
                         *             &lt;enumeration value="6.2"/>
                         *             &lt;enumeration value="6.3"/>
                         *             &lt;enumeration value="7.1"/>
                         *             &lt;enumeration value="7.2"/>
                         *             &lt;enumeration value="7.3"/>
                         *             &lt;enumeration value="8.1"/>
                         *             &lt;enumeration value="8.2"/>
                         *             &lt;enumeration value="8.3"/>
                         *             &lt;enumeration value="8.4"/>
                         *             &lt;enumeration value="8.5"/>
                         *             &lt;enumeration value="8.6"/>
                         *             &lt;enumeration value="8.7"/>
                         *             &lt;enumeration value="9.1"/>
                         *             &lt;enumeration value="9.2"/>
                         *             &lt;enumeration value="10.1"/>
                         *             &lt;enumeration value="10.2"/>
                         *             &lt;enumeration value="10.3"/>
                         *             &lt;enumeration value="10.4"/>
                         *             &lt;enumeration value="10.5"/>
                         *             &lt;enumeration value="10.6"/>
                         *             &lt;enumeration value="11.1"/>
                         *             &lt;enumeration value="11.2"/>
                         *             &lt;enumeration value="11.3"/>
                         *             &lt;enumeration value="12.1"/>
                         *             &lt;enumeration value="13.1"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {

                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousLot {

                            @XmlElement(name = "indicateurs_acv_collection", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateur indicateursAcvCollection;
                            @XmlElement(name = "indicateur_co2_dynamique", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                            @XmlElement(name = "stock_c", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal stockC;
                            @XmlElement(required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal udd;
                            @XmlElement(required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal ic;
                            @XmlElement(name = "ic_ded", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal icDed;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected String ref;

                            /**
                             * Gets the value of the indicateursAcvCollection property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateur getIndicateursAcvCollection() {
                                return indicateursAcvCollection;
                            }

                            /**
                             * Sets the value of the indicateursAcvCollection property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateursAcvCollection(TIndicateur value) {
                                this.indicateursAcvCollection = value;
                            }

                            /**
                             * Gets the value of the indicateurCo2Dynamique property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                                return indicateurCo2Dynamique;
                            }

                            /**
                             * Sets the value of the indicateurCo2Dynamique property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                                this.indicateurCo2Dynamique = value;
                            }

                            /**
                             * Gets the value of the stockC property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getStockC() {
                                return stockC;
                            }

                            /**
                             * Sets the value of the stockC property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setStockC(BigDecimal value) {
                                this.stockC = value;
                            }

                            /**
                             * Gets the value of the udd property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getUdd() {
                                return udd;
                            }

                            /**
                             * Sets the value of the udd property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setUdd(BigDecimal value) {
                                this.udd = value;
                            }

                            /**
                             * Gets the value of the ic property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getIc() {
                                return ic;
                            }

                            /**
                             * Sets the value of the ic property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIc(BigDecimal value) {
                                this.ic = value;
                            }

                            /**
                             * Gets the value of the icDed property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getIcDed() {
                                return icDed;
                            }

                            /**
                             * Sets the value of the icDed property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIcDed(BigDecimal value) {
                                this.icDed = value;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public String getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(String value) {
                                this.ref = value;
                            }

                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *         &lt;element name="sous_contributeur" maxOccurs="3">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;all>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                 &lt;/all>
                 *                 &lt;attribute name="ref" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                       &lt;enumeration value="1"/>
                 *                       &lt;enumeration value="2"/>
                 *                       &lt;enumeration value="3"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique",
                    "sousContributeur"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Eau {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                    @XmlElement(name = "sous_contributeur", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<RSEnv.SortieProjet.Batiment.Contributeur.Eau.SousContributeur> sousContributeur;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                    /**
                     * Gets the value of the sousContributeur property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getSousContributeur().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RSEnv.SortieProjet.Batiment.Contributeur.Eau.SousContributeur }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<RSEnv.SortieProjet.Batiment.Contributeur.Eau.SousContributeur> getSousContributeur() {
                        if (sousContributeur == null) {
                            sousContributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Eau.SousContributeur>();
                        }
                        return this.sousContributeur;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;all>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *       &lt;/all>
                     *       &lt;attribute name="ref" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *             &lt;enumeration value="1"/>
                     *             &lt;enumeration value="2"/>
                     *             &lt;enumeration value="3"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {

                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class SousContributeur {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlAttribute(name = "ref", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected String ref;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public String getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setRef(String value) {
                            this.ref = value;
                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *         &lt;element name="sous_contributeur" maxOccurs="8">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;all>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                 &lt;/all>
                 *                 &lt;attribute name="ref" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                       &lt;enumeration value="1"/>
                 *                       &lt;enumeration value="2"/>
                 *                       &lt;enumeration value="3"/>
                 *                       &lt;enumeration value="4"/>
                 *                       &lt;enumeration value="5"/>
                 *                       &lt;enumeration value="6"/>
                 *                       &lt;enumeration value="7"/>
                 *                       &lt;enumeration value="8"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique",
                    "sousContributeur"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Energie {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                    @XmlElement(name = "sous_contributeur", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected List<RSEnv.SortieProjet.Batiment.Contributeur.Energie.SousContributeur> sousContributeur;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                    /**
                     * Gets the value of the sousContributeur property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getSousContributeur().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RSEnv.SortieProjet.Batiment.Contributeur.Energie.SousContributeur }
                     * 
                     * 
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public List<RSEnv.SortieProjet.Batiment.Contributeur.Energie.SousContributeur> getSousContributeur() {
                        if (sousContributeur == null) {
                            sousContributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Energie.SousContributeur>();
                        }
                        return this.sousContributeur;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;all>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *       &lt;/all>
                     *       &lt;attribute name="ref" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *             &lt;enumeration value="1"/>
                     *             &lt;enumeration value="2"/>
                     *             &lt;enumeration value="3"/>
                     *             &lt;enumeration value="4"/>
                     *             &lt;enumeration value="5"/>
                     *             &lt;enumeration value="6"/>
                     *             &lt;enumeration value="7"/>
                     *             &lt;enumeration value="8"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {

                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class SousContributeur {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlAttribute(name = "ref", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected String ref;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public String getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setRef(String value) {
                            this.ref = value;
                        }

                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;all>
             *         &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_batiment" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_batiment_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="stock_c_batiment">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="stock_c_parcelle">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="udd">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *       &lt;/all>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {

            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class IndicateurPerfEnv {

                @XmlElement(name = "ic_construction", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icConstruction;
                @XmlElement(name = "ic_construction_max", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icConstructionMax;
                @XmlElement(name = "ic_construction_occ", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icConstructionOcc;
                @XmlElement(name = "ic_energie", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icEnergie;
                @XmlElement(name = "ic_energie_max", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icEnergieMax;
                @XmlElement(name = "ic_energie_occ", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icEnergieOcc;
                @XmlElement(name = "ic_composant", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icComposant;
                @XmlElement(name = "ic_eau", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icEau;
                @XmlElement(name = "ic_chantier", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icChantier;
                @XmlElement(name = "ic_batiment", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icBatiment;
                @XmlElement(name = "ic_batiment_occ", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icBatimentOcc;
                @XmlElement(name = "ic_parcelle", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icParcelle;
                @XmlElement(name = "ic_projet", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icProjet;
                @XmlElement(name = "ic_projet_occ", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icProjetOcc;
                @XmlElement(name = "stock_c_batiment", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal stockCBatiment;
                @XmlElement(name = "stock_c_parcelle", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal stockCParcelle;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal udd;
                @XmlElement(name = "ic_ded", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected BigDecimal icDed;

                /**
                 * Gets the value of the icConstruction property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcConstruction() {
                    return icConstruction;
                }

                /**
                 * Sets the value of the icConstruction property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcConstruction(BigDecimal value) {
                    this.icConstruction = value;
                }

                /**
                 * Gets the value of the icConstructionMax property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcConstructionMax() {
                    return icConstructionMax;
                }

                /**
                 * Sets the value of the icConstructionMax property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcConstructionMax(BigDecimal value) {
                    this.icConstructionMax = value;
                }

                /**
                 * Gets the value of the icConstructionOcc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcConstructionOcc() {
                    return icConstructionOcc;
                }

                /**
                 * Sets the value of the icConstructionOcc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcConstructionOcc(BigDecimal value) {
                    this.icConstructionOcc = value;
                }

                /**
                 * Gets the value of the icEnergie property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcEnergie() {
                    return icEnergie;
                }

                /**
                 * Sets the value of the icEnergie property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcEnergie(BigDecimal value) {
                    this.icEnergie = value;
                }

                /**
                 * Gets the value of the icEnergieMax property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcEnergieMax() {
                    return icEnergieMax;
                }

                /**
                 * Sets the value of the icEnergieMax property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcEnergieMax(BigDecimal value) {
                    this.icEnergieMax = value;
                }

                /**
                 * Gets the value of the icEnergieOcc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcEnergieOcc() {
                    return icEnergieOcc;
                }

                /**
                 * Sets the value of the icEnergieOcc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcEnergieOcc(BigDecimal value) {
                    this.icEnergieOcc = value;
                }

                /**
                 * Gets the value of the icComposant property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcComposant() {
                    return icComposant;
                }

                /**
                 * Sets the value of the icComposant property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcComposant(BigDecimal value) {
                    this.icComposant = value;
                }

                /**
                 * Gets the value of the icEau property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcEau() {
                    return icEau;
                }

                /**
                 * Sets the value of the icEau property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcEau(BigDecimal value) {
                    this.icEau = value;
                }

                /**
                 * Gets the value of the icChantier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcChantier() {
                    return icChantier;
                }

                /**
                 * Sets the value of the icChantier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcChantier(BigDecimal value) {
                    this.icChantier = value;
                }

                /**
                 * Gets the value of the icBatiment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcBatiment() {
                    return icBatiment;
                }

                /**
                 * Sets the value of the icBatiment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcBatiment(BigDecimal value) {
                    this.icBatiment = value;
                }

                /**
                 * Gets the value of the icBatimentOcc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcBatimentOcc() {
                    return icBatimentOcc;
                }

                /**
                 * Sets the value of the icBatimentOcc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcBatimentOcc(BigDecimal value) {
                    this.icBatimentOcc = value;
                }

                /**
                 * Gets the value of the icParcelle property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcParcelle() {
                    return icParcelle;
                }

                /**
                 * Sets the value of the icParcelle property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcParcelle(BigDecimal value) {
                    this.icParcelle = value;
                }

                /**
                 * Gets the value of the icProjet property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcProjet() {
                    return icProjet;
                }

                /**
                 * Sets the value of the icProjet property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcProjet(BigDecimal value) {
                    this.icProjet = value;
                }

                /**
                 * Gets the value of the icProjetOcc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcProjetOcc() {
                    return icProjetOcc;
                }

                /**
                 * Sets the value of the icProjetOcc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcProjetOcc(BigDecimal value) {
                    this.icProjetOcc = value;
                }

                /**
                 * Gets the value of the stockCBatiment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getStockCBatiment() {
                    return stockCBatiment;
                }

                /**
                 * Sets the value of the stockCBatiment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setStockCBatiment(BigDecimal value) {
                    this.stockCBatiment = value;
                }

                /**
                 * Gets the value of the stockCParcelle property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getStockCParcelle() {
                    return stockCParcelle;
                }

                /**
                 * Sets the value of the stockCParcelle property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setStockCParcelle(BigDecimal value) {
                    this.stockCParcelle = value;
                }

                /**
                 * Gets the value of the udd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getUdd() {
                    return udd;
                }

                /**
                 * Sets the value of the udd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setUdd(BigDecimal value) {
                    this.udd = value;
                }

                /**
                 * Gets the value of the icDed property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public BigDecimal getIcDed() {
                    return icDed;
                }

                /**
                 * Sets the value of the icDed property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIcDed(BigDecimal value) {
                    this.icDed = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;all>
             *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="contributeur">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="composant">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                             &lt;element name="lot" maxOccurs="13" minOccurs="9">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                                       &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;all>
             *                                                 &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                                                 &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                                                 &lt;element name="stock_c">
             *                                                   &lt;simpleType>
             *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                                     &lt;/restriction>
             *                                                   &lt;/simpleType>
             *                                                 &lt;/element>
             *                                                 &lt;element name="udd">
             *                                                   &lt;simpleType>
             *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                                     &lt;/restriction>
             *                                                   &lt;/simpleType>
             *                                                 &lt;/element>
             *                                                 &lt;element name="ic">
             *                                                   &lt;simpleType>
             *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                                     &lt;/restriction>
             *                                                   &lt;/simpleType>
             *                                                 &lt;/element>
             *                                                 &lt;element name="ic_ded">
             *                                                   &lt;simpleType>
             *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                                     &lt;/restriction>
             *                                                   &lt;/simpleType>
             *                                                 &lt;/element>
             *                                               &lt;/all>
             *                                               &lt;attribute name="ref" use="required">
             *                                                 &lt;simpleType>
             *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                                     &lt;enumeration value="1.1"/>
             *                                                     &lt;enumeration value="1.2"/>
             *                                                     &lt;enumeration value="1.3"/>
             *                                                     &lt;enumeration value="2.1"/>
             *                                                     &lt;enumeration value="2.2"/>
             *                                                     &lt;enumeration value="2.3"/>
             *                                                     &lt;enumeration value="3.1"/>
             *                                                     &lt;enumeration value="3.2"/>
             *                                                     &lt;enumeration value="3.3"/>
             *                                                     &lt;enumeration value="3.4"/>
             *                                                     &lt;enumeration value="3.5"/>
             *                                                     &lt;enumeration value="3.6"/>
             *                                                     &lt;enumeration value="3.7"/>
             *                                                     &lt;enumeration value="3.8"/>
             *                                                     &lt;enumeration value="4.1"/>
             *                                                     &lt;enumeration value="4.2"/>
             *                                                     &lt;enumeration value="4.3"/>
             *                                                     &lt;enumeration value="5.1"/>
             *                                                     &lt;enumeration value="5.2"/>
             *                                                     &lt;enumeration value="5.3"/>
             *                                                     &lt;enumeration value="5.4"/>
             *                                                     &lt;enumeration value="5.5"/>
             *                                                     &lt;enumeration value="6.1"/>
             *                                                     &lt;enumeration value="6.2"/>
             *                                                     &lt;enumeration value="6.3"/>
             *                                                     &lt;enumeration value="7.1"/>
             *                                                     &lt;enumeration value="7.2"/>
             *                                                     &lt;enumeration value="7.3"/>
             *                                                     &lt;enumeration value="8.1"/>
             *                                                     &lt;enumeration value="8.2"/>
             *                                                     &lt;enumeration value="8.3"/>
             *                                                     &lt;enumeration value="8.4"/>
             *                                                     &lt;enumeration value="8.5"/>
             *                                                     &lt;enumeration value="8.6"/>
             *                                                     &lt;enumeration value="8.7"/>
             *                                                     &lt;enumeration value="9.1"/>
             *                                                     &lt;enumeration value="9.2"/>
             *                                                     &lt;enumeration value="10.1"/>
             *                                                     &lt;enumeration value="10.2"/>
             *                                                     &lt;enumeration value="10.3"/>
             *                                                     &lt;enumeration value="10.4"/>
             *                                                     &lt;enumeration value="10.5"/>
             *                                                     &lt;enumeration value="10.6"/>
             *                                                     &lt;enumeration value="11.1"/>
             *                                                     &lt;enumeration value="11.2"/>
             *                                                     &lt;enumeration value="11.3"/>
             *                                                     &lt;enumeration value="12.1"/>
             *                                                     &lt;enumeration value="13.1"/>
             *                                                   &lt;/restriction>
             *                                                 &lt;/simpleType>
             *                                               &lt;/attribute>
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                       &lt;element name="stock_c">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="udd">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="ic">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="ic_ded">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                           &lt;enumeration value="4"/>
             *                                           &lt;enumeration value="5"/>
             *                                           &lt;enumeration value="6"/>
             *                                           &lt;enumeration value="7"/>
             *                                           &lt;enumeration value="8"/>
             *                                           &lt;enumeration value="9"/>
             *                                           &lt;enumeration value="10"/>
             *                                           &lt;enumeration value="11"/>
             *                                           &lt;enumeration value="12"/>
             *                                           &lt;enumeration value="13"/>
             *                                           &lt;enumeration value="14"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                             &lt;element name="stock_c">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="udd">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="energie">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                             &lt;element name="sous_contributeur" maxOccurs="8">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;all>
             *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                                     &lt;/all>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                           &lt;enumeration value="4"/>
             *                                           &lt;enumeration value="5"/>
             *                                           &lt;enumeration value="6"/>
             *                                           &lt;enumeration value="7"/>
             *                                           &lt;enumeration value="8"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="eau">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                             &lt;element name="sous_contributeur" maxOccurs="3">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;all>
             *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                                     &lt;/all>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="chantier">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                             &lt;element name="sous_contributeur" maxOccurs="4">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;all>
             *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                                     &lt;/all>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                           &lt;enumeration value="1"/>
             *                                           &lt;enumeration value="2"/>
             *                                           &lt;enumeration value="3"/>
             *                                           &lt;enumeration value="4"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *         &lt;element name="indicateur_perf_env">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;all>
             *                   &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="coef_mod_icconstruction" type="{}t_coef_mod_icconstruction"/>
             *                   &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="coef_mod_icenergie" type="{}t_coef_mod_icenergie"/>
             *                   &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_zone" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_zone_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                   &lt;element name="stock_c">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="udd">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                 &lt;/all>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *       &lt;/all>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {

            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class Zone {

                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected int index;
                @XmlElement(required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Batiment.Zone.Contributeur contributeur;
                @XmlElement(name = "indicateurs_acv_collection", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected TIndicateur indicateursAcvCollection;
                @XmlElement(name = "indicateur_perf_env", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Batiment.Zone.IndicateurPerfEnv indicateurPerfEnv;
                @XmlElement(name = "indicateur_co2_dynamique", required = true)
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected TIndicateurCo2Dynamique indicateurCo2Dynamique;

                /**
                 * Gets the value of the index property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public int getIndex() {
                    return index;
                }

                /**
                 * Sets the value of the index property.
                 * 
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIndex(int value) {
                    this.index = value;
                }

                /**
                 * Gets the value of the contributeur property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Batiment.Zone.Contributeur getContributeur() {
                    return contributeur;
                }

                /**
                 * Sets the value of the contributeur property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setContributeur(RSEnv.SortieProjet.Batiment.Zone.Contributeur value) {
                    this.contributeur = value;
                }

                /**
                 * Gets the value of the indicateursAcvCollection property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TIndicateur }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public TIndicateur getIndicateursAcvCollection() {
                    return indicateursAcvCollection;
                }

                /**
                 * Sets the value of the indicateursAcvCollection property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TIndicateur }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIndicateursAcvCollection(TIndicateur value) {
                    this.indicateursAcvCollection = value;
                }

                /**
                 * Gets the value of the indicateurPerfEnv property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Zone.IndicateurPerfEnv }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Batiment.Zone.IndicateurPerfEnv getIndicateurPerfEnv() {
                    return indicateurPerfEnv;
                }

                /**
                 * Sets the value of the indicateurPerfEnv property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Zone.IndicateurPerfEnv }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIndicateurPerfEnv(RSEnv.SortieProjet.Batiment.Zone.IndicateurPerfEnv value) {
                    this.indicateurPerfEnv = value;
                }

                /**
                 * Gets the value of the indicateurCo2Dynamique property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TIndicateurCo2Dynamique }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                    return indicateurCo2Dynamique;
                }

                /**
                 * Sets the value of the indicateurCo2Dynamique property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TIndicateurCo2Dynamique }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                    this.indicateurCo2Dynamique = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="composant">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                   &lt;element name="lot" maxOccurs="13" minOccurs="9">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                             &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;all>
                 *                                       &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                                       &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                                       &lt;element name="stock_c">
                 *                                         &lt;simpleType>
                 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                           &lt;/restriction>
                 *                                         &lt;/simpleType>
                 *                                       &lt;/element>
                 *                                       &lt;element name="udd">
                 *                                         &lt;simpleType>
                 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                           &lt;/restriction>
                 *                                         &lt;/simpleType>
                 *                                       &lt;/element>
                 *                                       &lt;element name="ic">
                 *                                         &lt;simpleType>
                 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                           &lt;/restriction>
                 *                                         &lt;/simpleType>
                 *                                       &lt;/element>
                 *                                       &lt;element name="ic_ded">
                 *                                         &lt;simpleType>
                 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                           &lt;/restriction>
                 *                                         &lt;/simpleType>
                 *                                       &lt;/element>
                 *                                     &lt;/all>
                 *                                     &lt;attribute name="ref" use="required">
                 *                                       &lt;simpleType>
                 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                                           &lt;enumeration value="1.1"/>
                 *                                           &lt;enumeration value="1.2"/>
                 *                                           &lt;enumeration value="1.3"/>
                 *                                           &lt;enumeration value="2.1"/>
                 *                                           &lt;enumeration value="2.2"/>
                 *                                           &lt;enumeration value="2.3"/>
                 *                                           &lt;enumeration value="3.1"/>
                 *                                           &lt;enumeration value="3.2"/>
                 *                                           &lt;enumeration value="3.3"/>
                 *                                           &lt;enumeration value="3.4"/>
                 *                                           &lt;enumeration value="3.5"/>
                 *                                           &lt;enumeration value="3.6"/>
                 *                                           &lt;enumeration value="3.7"/>
                 *                                           &lt;enumeration value="3.8"/>
                 *                                           &lt;enumeration value="4.1"/>
                 *                                           &lt;enumeration value="4.2"/>
                 *                                           &lt;enumeration value="4.3"/>
                 *                                           &lt;enumeration value="5.1"/>
                 *                                           &lt;enumeration value="5.2"/>
                 *                                           &lt;enumeration value="5.3"/>
                 *                                           &lt;enumeration value="5.4"/>
                 *                                           &lt;enumeration value="5.5"/>
                 *                                           &lt;enumeration value="6.1"/>
                 *                                           &lt;enumeration value="6.2"/>
                 *                                           &lt;enumeration value="6.3"/>
                 *                                           &lt;enumeration value="7.1"/>
                 *                                           &lt;enumeration value="7.2"/>
                 *                                           &lt;enumeration value="7.3"/>
                 *                                           &lt;enumeration value="8.1"/>
                 *                                           &lt;enumeration value="8.2"/>
                 *                                           &lt;enumeration value="8.3"/>
                 *                                           &lt;enumeration value="8.4"/>
                 *                                           &lt;enumeration value="8.5"/>
                 *                                           &lt;enumeration value="8.6"/>
                 *                                           &lt;enumeration value="8.7"/>
                 *                                           &lt;enumeration value="9.1"/>
                 *                                           &lt;enumeration value="9.2"/>
                 *                                           &lt;enumeration value="10.1"/>
                 *                                           &lt;enumeration value="10.2"/>
                 *                                           &lt;enumeration value="10.3"/>
                 *                                           &lt;enumeration value="10.4"/>
                 *                                           &lt;enumeration value="10.5"/>
                 *                                           &lt;enumeration value="10.6"/>
                 *                                           &lt;enumeration value="11.1"/>
                 *                                           &lt;enumeration value="11.2"/>
                 *                                           &lt;enumeration value="11.3"/>
                 *                                           &lt;enumeration value="12.1"/>
                 *                                           &lt;enumeration value="13.1"/>
                 *                                         &lt;/restriction>
                 *                                       &lt;/simpleType>
                 *                                     &lt;/attribute>
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                             &lt;element name="stock_c">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="udd">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="ic">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="ic_ded">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                                 &lt;enumeration value="4"/>
                 *                                 &lt;enumeration value="5"/>
                 *                                 &lt;enumeration value="6"/>
                 *                                 &lt;enumeration value="7"/>
                 *                                 &lt;enumeration value="8"/>
                 *                                 &lt;enumeration value="9"/>
                 *                                 &lt;enumeration value="10"/>
                 *                                 &lt;enumeration value="11"/>
                 *                                 &lt;enumeration value="12"/>
                 *                                 &lt;enumeration value="13"/>
                 *                                 &lt;enumeration value="14"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                   &lt;element name="stock_c">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="udd">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="energie">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                   &lt;element name="sous_contributeur" maxOccurs="8">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;all>
                 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                           &lt;/all>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                                 &lt;enumeration value="4"/>
                 *                                 &lt;enumeration value="5"/>
                 *                                 &lt;enumeration value="6"/>
                 *                                 &lt;enumeration value="7"/>
                 *                                 &lt;enumeration value="8"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="eau">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                   &lt;element name="sous_contributeur" maxOccurs="3">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;all>
                 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                           &lt;/all>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="chantier">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                   &lt;element name="sous_contributeur" maxOccurs="4">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;all>
                 *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *                           &lt;/all>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                                 &lt;enumeration value="1"/>
                 *                                 &lt;enumeration value="2"/>
                 *                                 &lt;enumeration value="3"/>
                 *                                 &lt;enumeration value="4"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "composant",
                    "energie",
                    "eau",
                    "chantier"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Contributeur {

                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant composant;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie energie;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau eau;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier chantier;

                    /**
                     * Gets the value of the composant property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant getComposant() {
                        return composant;
                    }

                    /**
                     * Sets the value of the composant property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setComposant(RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant value) {
                        this.composant = value;
                    }

                    /**
                     * Gets the value of the energie property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie getEnergie() {
                        return energie;
                    }

                    /**
                     * Sets the value of the energie property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setEnergie(RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie value) {
                        this.energie = value;
                    }

                    /**
                     * Gets the value of the eau property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau getEau() {
                        return eau;
                    }

                    /**
                     * Sets the value of the eau property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setEau(RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau value) {
                        this.eau = value;
                    }

                    /**
                     * Gets the value of the chantier property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier getChantier() {
                        return chantier;
                    }

                    /**
                     * Sets the value of the chantier property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setChantier(RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier value) {
                        this.chantier = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *         &lt;element name="sous_contributeur" maxOccurs="4">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;all>
                     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *                 &lt;/all>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                       &lt;enumeration value="4"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "indicateursAcvCollection",
                        "indicateurCo2Dynamique",
                        "sousContributeur"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Chantier {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlElement(name = "sous_contributeur", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur> sousContributeur;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the sousContributeur property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousContributeur().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur> getSousContributeur() {
                            if (sousContributeur == null) {
                                sousContributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Chantier.SousContributeur>();
                            }
                            return this.sousContributeur;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;all>
                         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                         *       &lt;/all>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *             &lt;enumeration value="4"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {

                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousContributeur {

                            @XmlElement(name = "indicateurs_acv_collection", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateur indicateursAcvCollection;
                            @XmlElement(name = "indicateur_co2_dynamique", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected String ref;

                            /**
                             * Gets the value of the indicateursAcvCollection property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateur getIndicateursAcvCollection() {
                                return indicateursAcvCollection;
                            }

                            /**
                             * Sets the value of the indicateursAcvCollection property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateursAcvCollection(TIndicateur value) {
                                this.indicateursAcvCollection = value;
                            }

                            /**
                             * Gets the value of the indicateurCo2Dynamique property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                                return indicateurCo2Dynamique;
                            }

                            /**
                             * Sets the value of the indicateurCo2Dynamique property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                                this.indicateurCo2Dynamique = value;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public String getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(String value) {
                                this.ref = value;
                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *         &lt;element name="lot" maxOccurs="13" minOccurs="9">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *                   &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;all>
                     *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *                             &lt;element name="stock_c">
                     *                               &lt;simpleType>
                     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                                 &lt;/restriction>
                     *                               &lt;/simpleType>
                     *                             &lt;/element>
                     *                             &lt;element name="udd">
                     *                               &lt;simpleType>
                     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                                 &lt;/restriction>
                     *                               &lt;/simpleType>
                     *                             &lt;/element>
                     *                             &lt;element name="ic">
                     *                               &lt;simpleType>
                     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                                 &lt;/restriction>
                     *                               &lt;/simpleType>
                     *                             &lt;/element>
                     *                             &lt;element name="ic_ded">
                     *                               &lt;simpleType>
                     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                                 &lt;/restriction>
                     *                               &lt;/simpleType>
                     *                             &lt;/element>
                     *                           &lt;/all>
                     *                           &lt;attribute name="ref" use="required">
                     *                             &lt;simpleType>
                     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *                                 &lt;enumeration value="1.1"/>
                     *                                 &lt;enumeration value="1.2"/>
                     *                                 &lt;enumeration value="1.3"/>
                     *                                 &lt;enumeration value="2.1"/>
                     *                                 &lt;enumeration value="2.2"/>
                     *                                 &lt;enumeration value="2.3"/>
                     *                                 &lt;enumeration value="3.1"/>
                     *                                 &lt;enumeration value="3.2"/>
                     *                                 &lt;enumeration value="3.3"/>
                     *                                 &lt;enumeration value="3.4"/>
                     *                                 &lt;enumeration value="3.5"/>
                     *                                 &lt;enumeration value="3.6"/>
                     *                                 &lt;enumeration value="3.7"/>
                     *                                 &lt;enumeration value="3.8"/>
                     *                                 &lt;enumeration value="4.1"/>
                     *                                 &lt;enumeration value="4.2"/>
                     *                                 &lt;enumeration value="4.3"/>
                     *                                 &lt;enumeration value="5.1"/>
                     *                                 &lt;enumeration value="5.2"/>
                     *                                 &lt;enumeration value="5.3"/>
                     *                                 &lt;enumeration value="5.4"/>
                     *                                 &lt;enumeration value="5.5"/>
                     *                                 &lt;enumeration value="6.1"/>
                     *                                 &lt;enumeration value="6.2"/>
                     *                                 &lt;enumeration value="6.3"/>
                     *                                 &lt;enumeration value="7.1"/>
                     *                                 &lt;enumeration value="7.2"/>
                     *                                 &lt;enumeration value="7.3"/>
                     *                                 &lt;enumeration value="8.1"/>
                     *                                 &lt;enumeration value="8.2"/>
                     *                                 &lt;enumeration value="8.3"/>
                     *                                 &lt;enumeration value="8.4"/>
                     *                                 &lt;enumeration value="8.5"/>
                     *                                 &lt;enumeration value="8.6"/>
                     *                                 &lt;enumeration value="8.7"/>
                     *                                 &lt;enumeration value="9.1"/>
                     *                                 &lt;enumeration value="9.2"/>
                     *                                 &lt;enumeration value="10.1"/>
                     *                                 &lt;enumeration value="10.2"/>
                     *                                 &lt;enumeration value="10.3"/>
                     *                                 &lt;enumeration value="10.4"/>
                     *                                 &lt;enumeration value="10.5"/>
                     *                                 &lt;enumeration value="10.6"/>
                     *                                 &lt;enumeration value="11.1"/>
                     *                                 &lt;enumeration value="11.2"/>
                     *                                 &lt;enumeration value="11.3"/>
                     *                                 &lt;enumeration value="12.1"/>
                     *                                 &lt;enumeration value="13.1"/>
                     *                               &lt;/restriction>
                     *                             &lt;/simpleType>
                     *                           &lt;/attribute>
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                   &lt;element name="stock_c">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="udd">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="ic">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="ic_ded">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                       &lt;enumeration value="4"/>
                     *                       &lt;enumeration value="5"/>
                     *                       &lt;enumeration value="6"/>
                     *                       &lt;enumeration value="7"/>
                     *                       &lt;enumeration value="8"/>
                     *                       &lt;enumeration value="9"/>
                     *                       &lt;enumeration value="10"/>
                     *                       &lt;enumeration value="11"/>
                     *                       &lt;enumeration value="12"/>
                     *                       &lt;enumeration value="13"/>
                     *                       &lt;enumeration value="14"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *         &lt;element name="stock_c">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="udd">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "indicateursAcvCollection",
                        "indicateurCo2Dynamique",
                        "lot",
                        "stockC",
                        "udd"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Composant {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlElement(required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot> lot;
                        @XmlElement(name = "stock_c", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected BigDecimal stockC;
                        @XmlElement(required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected BigDecimal udd;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the lot property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the lot property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getLot().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot> getLot() {
                            if (lot == null) {
                                lot = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot>();
                            }
                            return this.lot;
                        }

                        /**
                         * Gets the value of the stockC property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public BigDecimal getStockC() {
                            return stockC;
                        }

                        /**
                         * Sets the value of the stockC property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setStockC(BigDecimal value) {
                            this.stockC = value;
                        }

                        /**
                         * Gets the value of the udd property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public BigDecimal getUdd() {
                            return udd;
                        }

                        /**
                         * Sets the value of the udd property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setUdd(BigDecimal value) {
                            this.udd = value;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                         *         &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;all>
                         *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                         *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                         *                   &lt;element name="stock_c">
                         *                     &lt;simpleType>
                         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *                       &lt;/restriction>
                         *                     &lt;/simpleType>
                         *                   &lt;/element>
                         *                   &lt;element name="udd">
                         *                     &lt;simpleType>
                         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *                       &lt;/restriction>
                         *                     &lt;/simpleType>
                         *                   &lt;/element>
                         *                   &lt;element name="ic">
                         *                     &lt;simpleType>
                         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *                       &lt;/restriction>
                         *                     &lt;/simpleType>
                         *                   &lt;/element>
                         *                   &lt;element name="ic_ded">
                         *                     &lt;simpleType>
                         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *                       &lt;/restriction>
                         *                     &lt;/simpleType>
                         *                   &lt;/element>
                         *                 &lt;/all>
                         *                 &lt;attribute name="ref" use="required">
                         *                   &lt;simpleType>
                         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                         *                       &lt;enumeration value="1.1"/>
                         *                       &lt;enumeration value="1.2"/>
                         *                       &lt;enumeration value="1.3"/>
                         *                       &lt;enumeration value="2.1"/>
                         *                       &lt;enumeration value="2.2"/>
                         *                       &lt;enumeration value="2.3"/>
                         *                       &lt;enumeration value="3.1"/>
                         *                       &lt;enumeration value="3.2"/>
                         *                       &lt;enumeration value="3.3"/>
                         *                       &lt;enumeration value="3.4"/>
                         *                       &lt;enumeration value="3.5"/>
                         *                       &lt;enumeration value="3.6"/>
                         *                       &lt;enumeration value="3.7"/>
                         *                       &lt;enumeration value="3.8"/>
                         *                       &lt;enumeration value="4.1"/>
                         *                       &lt;enumeration value="4.2"/>
                         *                       &lt;enumeration value="4.3"/>
                         *                       &lt;enumeration value="5.1"/>
                         *                       &lt;enumeration value="5.2"/>
                         *                       &lt;enumeration value="5.3"/>
                         *                       &lt;enumeration value="5.4"/>
                         *                       &lt;enumeration value="5.5"/>
                         *                       &lt;enumeration value="6.1"/>
                         *                       &lt;enumeration value="6.2"/>
                         *                       &lt;enumeration value="6.3"/>
                         *                       &lt;enumeration value="7.1"/>
                         *                       &lt;enumeration value="7.2"/>
                         *                       &lt;enumeration value="7.3"/>
                         *                       &lt;enumeration value="8.1"/>
                         *                       &lt;enumeration value="8.2"/>
                         *                       &lt;enumeration value="8.3"/>
                         *                       &lt;enumeration value="8.4"/>
                         *                       &lt;enumeration value="8.5"/>
                         *                       &lt;enumeration value="8.6"/>
                         *                       &lt;enumeration value="8.7"/>
                         *                       &lt;enumeration value="9.1"/>
                         *                       &lt;enumeration value="9.2"/>
                         *                       &lt;enumeration value="10.1"/>
                         *                       &lt;enumeration value="10.2"/>
                         *                       &lt;enumeration value="10.3"/>
                         *                       &lt;enumeration value="10.4"/>
                         *                       &lt;enumeration value="10.5"/>
                         *                       &lt;enumeration value="10.6"/>
                         *                       &lt;enumeration value="11.1"/>
                         *                       &lt;enumeration value="11.2"/>
                         *                       &lt;enumeration value="11.3"/>
                         *                       &lt;enumeration value="12.1"/>
                         *                       &lt;enumeration value="13.1"/>
                         *                     &lt;/restriction>
                         *                   &lt;/simpleType>
                         *                 &lt;/attribute>
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *         &lt;element name="stock_c">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="udd">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="ic">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="ic_ded">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *             &lt;enumeration value="4"/>
                         *             &lt;enumeration value="5"/>
                         *             &lt;enumeration value="6"/>
                         *             &lt;enumeration value="7"/>
                         *             &lt;enumeration value="8"/>
                         *             &lt;enumeration value="9"/>
                         *             &lt;enumeration value="10"/>
                         *             &lt;enumeration value="11"/>
                         *             &lt;enumeration value="12"/>
                         *             &lt;enumeration value="13"/>
                         *             &lt;enumeration value="14"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "indicateursAcvCollection",
                            "indicateurCo2Dynamique",
                            "sousLot",
                            "stockC",
                            "udd",
                            "ic",
                            "icDed"
                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class Lot {

                            @XmlElement(name = "indicateurs_acv_collection", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateur indicateursAcvCollection;
                            @XmlElement(name = "indicateur_co2_dynamique", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                            @XmlElement(name = "sous_lot")
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot> sousLot;
                            @XmlElement(name = "stock_c", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal stockC;
                            @XmlElement(required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal udd;
                            @XmlElement(required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal ic;
                            @XmlElement(name = "ic_ded", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected BigDecimal icDed;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected int ref;

                            /**
                             * Gets the value of the indicateursAcvCollection property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateur getIndicateursAcvCollection() {
                                return indicateursAcvCollection;
                            }

                            /**
                             * Sets the value of the indicateursAcvCollection property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateursAcvCollection(TIndicateur value) {
                                this.indicateursAcvCollection = value;
                            }

                            /**
                             * Gets the value of the indicateurCo2Dynamique property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                                return indicateurCo2Dynamique;
                            }

                            /**
                             * Sets the value of the indicateurCo2Dynamique property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                                this.indicateurCo2Dynamique = value;
                            }

                            /**
                             * Gets the value of the sousLot property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the sousLot property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getSousLot().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot }
                             * 
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot> getSousLot() {
                                if (sousLot == null) {
                                    sousLot = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Composant.Lot.SousLot>();
                                }
                                return this.sousLot;
                            }

                            /**
                             * Gets the value of the stockC property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getStockC() {
                                return stockC;
                            }

                            /**
                             * Sets the value of the stockC property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setStockC(BigDecimal value) {
                                this.stockC = value;
                            }

                            /**
                             * Gets the value of the udd property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getUdd() {
                                return udd;
                            }

                            /**
                             * Sets the value of the udd property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setUdd(BigDecimal value) {
                                this.udd = value;
                            }

                            /**
                             * Gets the value of the ic property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getIc() {
                                return ic;
                            }

                            /**
                             * Sets the value of the ic property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIc(BigDecimal value) {
                                this.ic = value;
                            }

                            /**
                             * Gets the value of the icDed property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public BigDecimal getIcDed() {
                                return icDed;
                            }

                            /**
                             * Sets the value of the icDed property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIcDed(BigDecimal value) {
                                this.icDed = value;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(int value) {
                                this.ref = value;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             * 
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;all>
                             *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                             *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                             *         &lt;element name="stock_c">
                             *           &lt;simpleType>
                             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                             *             &lt;/restriction>
                             *           &lt;/simpleType>
                             *         &lt;/element>
                             *         &lt;element name="udd">
                             *           &lt;simpleType>
                             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                             *             &lt;/restriction>
                             *           &lt;/simpleType>
                             *         &lt;/element>
                             *         &lt;element name="ic">
                             *           &lt;simpleType>
                             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                             *             &lt;/restriction>
                             *           &lt;/simpleType>
                             *         &lt;/element>
                             *         &lt;element name="ic_ded">
                             *           &lt;simpleType>
                             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                             *             &lt;/restriction>
                             *           &lt;/simpleType>
                             *         &lt;/element>
                             *       &lt;/all>
                             *       &lt;attribute name="ref" use="required">
                             *         &lt;simpleType>
                             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                             *             &lt;enumeration value="1.1"/>
                             *             &lt;enumeration value="1.2"/>
                             *             &lt;enumeration value="1.3"/>
                             *             &lt;enumeration value="2.1"/>
                             *             &lt;enumeration value="2.2"/>
                             *             &lt;enumeration value="2.3"/>
                             *             &lt;enumeration value="3.1"/>
                             *             &lt;enumeration value="3.2"/>
                             *             &lt;enumeration value="3.3"/>
                             *             &lt;enumeration value="3.4"/>
                             *             &lt;enumeration value="3.5"/>
                             *             &lt;enumeration value="3.6"/>
                             *             &lt;enumeration value="3.7"/>
                             *             &lt;enumeration value="3.8"/>
                             *             &lt;enumeration value="4.1"/>
                             *             &lt;enumeration value="4.2"/>
                             *             &lt;enumeration value="4.3"/>
                             *             &lt;enumeration value="5.1"/>
                             *             &lt;enumeration value="5.2"/>
                             *             &lt;enumeration value="5.3"/>
                             *             &lt;enumeration value="5.4"/>
                             *             &lt;enumeration value="5.5"/>
                             *             &lt;enumeration value="6.1"/>
                             *             &lt;enumeration value="6.2"/>
                             *             &lt;enumeration value="6.3"/>
                             *             &lt;enumeration value="7.1"/>
                             *             &lt;enumeration value="7.2"/>
                             *             &lt;enumeration value="7.3"/>
                             *             &lt;enumeration value="8.1"/>
                             *             &lt;enumeration value="8.2"/>
                             *             &lt;enumeration value="8.3"/>
                             *             &lt;enumeration value="8.4"/>
                             *             &lt;enumeration value="8.5"/>
                             *             &lt;enumeration value="8.6"/>
                             *             &lt;enumeration value="8.7"/>
                             *             &lt;enumeration value="9.1"/>
                             *             &lt;enumeration value="9.2"/>
                             *             &lt;enumeration value="10.1"/>
                             *             &lt;enumeration value="10.2"/>
                             *             &lt;enumeration value="10.3"/>
                             *             &lt;enumeration value="10.4"/>
                             *             &lt;enumeration value="10.5"/>
                             *             &lt;enumeration value="10.6"/>
                             *             &lt;enumeration value="11.1"/>
                             *             &lt;enumeration value="11.2"/>
                             *             &lt;enumeration value="11.3"/>
                             *             &lt;enumeration value="12.1"/>
                             *             &lt;enumeration value="13.1"/>
                             *           &lt;/restriction>
                             *         &lt;/simpleType>
                             *       &lt;/attribute>
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {

                            })
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public static class SousLot {

                                @XmlElement(name = "indicateurs_acv_collection", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected TIndicateur indicateursAcvCollection;
                                @XmlElement(name = "indicateur_co2_dynamique", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                                @XmlElement(name = "stock_c", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected BigDecimal stockC;
                                @XmlElement(required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected BigDecimal udd;
                                @XmlElement(required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected BigDecimal ic;
                                @XmlElement(name = "ic_ded", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected BigDecimal icDed;
                                @XmlAttribute(name = "ref", required = true)
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                protected String ref;

                                /**
                                 * Gets the value of the indicateursAcvCollection property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link TIndicateur }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public TIndicateur getIndicateursAcvCollection() {
                                    return indicateursAcvCollection;
                                }

                                /**
                                 * Sets the value of the indicateursAcvCollection property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link TIndicateur }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setIndicateursAcvCollection(TIndicateur value) {
                                    this.indicateursAcvCollection = value;
                                }

                                /**
                                 * Gets the value of the indicateurCo2Dynamique property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link TIndicateurCo2Dynamique }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                                    return indicateurCo2Dynamique;
                                }

                                /**
                                 * Sets the value of the indicateurCo2Dynamique property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link TIndicateurCo2Dynamique }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                                    this.indicateurCo2Dynamique = value;
                                }

                                /**
                                 * Gets the value of the stockC property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public BigDecimal getStockC() {
                                    return stockC;
                                }

                                /**
                                 * Sets the value of the stockC property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setStockC(BigDecimal value) {
                                    this.stockC = value;
                                }

                                /**
                                 * Gets the value of the udd property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public BigDecimal getUdd() {
                                    return udd;
                                }

                                /**
                                 * Sets the value of the udd property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setUdd(BigDecimal value) {
                                    this.udd = value;
                                }

                                /**
                                 * Gets the value of the ic property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public BigDecimal getIc() {
                                    return ic;
                                }

                                /**
                                 * Sets the value of the ic property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setIc(BigDecimal value) {
                                    this.ic = value;
                                }

                                /**
                                 * Gets the value of the icDed property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public BigDecimal getIcDed() {
                                    return icDed;
                                }

                                /**
                                 * Sets the value of the icDed property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setIcDed(BigDecimal value) {
                                    this.icDed = value;
                                }

                                /**
                                 * Gets the value of the ref property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public String getRef() {
                                    return ref;
                                }

                                /**
                                 * Sets the value of the ref property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                                public void setRef(String value) {
                                    this.ref = value;
                                }

                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *         &lt;element name="sous_contributeur" maxOccurs="3">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;all>
                     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *                 &lt;/all>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "indicateursAcvCollection",
                        "indicateurCo2Dynamique",
                        "sousContributeur"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Eau {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlElement(name = "sous_contributeur", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau.SousContributeur> sousContributeur;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the sousContributeur property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousContributeur().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau.SousContributeur }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau.SousContributeur> getSousContributeur() {
                            if (sousContributeur == null) {
                                sousContributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Eau.SousContributeur>();
                            }
                            return this.sousContributeur;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;all>
                         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                         *       &lt;/all>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {

                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousContributeur {

                            @XmlElement(name = "indicateurs_acv_collection", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateur indicateursAcvCollection;
                            @XmlElement(name = "indicateur_co2_dynamique", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected String ref;

                            /**
                             * Gets the value of the indicateursAcvCollection property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateur getIndicateursAcvCollection() {
                                return indicateursAcvCollection;
                            }

                            /**
                             * Sets the value of the indicateursAcvCollection property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateursAcvCollection(TIndicateur value) {
                                this.indicateursAcvCollection = value;
                            }

                            /**
                             * Gets the value of the indicateurCo2Dynamique property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                                return indicateurCo2Dynamique;
                            }

                            /**
                             * Sets the value of the indicateurCo2Dynamique property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                                this.indicateurCo2Dynamique = value;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public String getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(String value) {
                                this.ref = value;
                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *         &lt;element name="sous_contributeur" maxOccurs="8">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;all>
                     *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                     *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                     *                 &lt;/all>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *                       &lt;enumeration value="1"/>
                     *                       &lt;enumeration value="2"/>
                     *                       &lt;enumeration value="3"/>
                     *                       &lt;enumeration value="4"/>
                     *                       &lt;enumeration value="5"/>
                     *                       &lt;enumeration value="6"/>
                     *                       &lt;enumeration value="7"/>
                     *                       &lt;enumeration value="8"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "indicateursAcvCollection",
                        "indicateurCo2Dynamique",
                        "sousContributeur"
                    })
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public static class Energie {

                        @XmlElement(name = "indicateurs_acv_collection", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateur indicateursAcvCollection;
                        @XmlElement(name = "indicateur_co2_dynamique", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                        @XmlElement(name = "sous_contributeur", required = true)
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie.SousContributeur> sousContributeur;

                        /**
                         * Gets the value of the indicateursAcvCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateur getIndicateursAcvCollection() {
                            return indicateursAcvCollection;
                        }

                        /**
                         * Sets the value of the indicateursAcvCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateursAcvCollection(TIndicateur value) {
                            this.indicateursAcvCollection = value;
                        }

                        /**
                         * Gets the value of the indicateurCo2Dynamique property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                            return indicateurCo2Dynamique;
                        }

                        /**
                         * Sets the value of the indicateurCo2Dynamique property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateurCo2Dynamique }
                         *     
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                            this.indicateurCo2Dynamique = value;
                        }

                        /**
                         * Gets the value of the sousContributeur property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the sousContributeur property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getSousContributeur().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie.SousContributeur }
                         * 
                         * 
                         */
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie.SousContributeur> getSousContributeur() {
                            if (sousContributeur == null) {
                                sousContributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Energie.SousContributeur>();
                            }
                            return this.sousContributeur;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;all>
                         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                         *       &lt;/all>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                         *             &lt;enumeration value="1"/>
                         *             &lt;enumeration value="2"/>
                         *             &lt;enumeration value="3"/>
                         *             &lt;enumeration value="4"/>
                         *             &lt;enumeration value="5"/>
                         *             &lt;enumeration value="6"/>
                         *             &lt;enumeration value="7"/>
                         *             &lt;enumeration value="8"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {

                        })
                        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                        public static class SousContributeur {

                            @XmlElement(name = "indicateurs_acv_collection", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateur indicateursAcvCollection;
                            @XmlElement(name = "indicateur_co2_dynamique", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                            @XmlAttribute(name = "ref", required = true)
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            protected String ref;

                            /**
                             * Gets the value of the indicateursAcvCollection property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateur getIndicateursAcvCollection() {
                                return indicateursAcvCollection;
                            }

                            /**
                             * Sets the value of the indicateursAcvCollection property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateur }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateursAcvCollection(TIndicateur value) {
                                this.indicateursAcvCollection = value;
                            }

                            /**
                             * Gets the value of the indicateurCo2Dynamique property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                                return indicateurCo2Dynamique;
                            }

                            /**
                             * Sets the value of the indicateurCo2Dynamique property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateurCo2Dynamique }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                                this.indicateurCo2Dynamique = value;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public String getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                            public void setRef(String value) {
                                this.ref = value;
                            }

                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;all>
                 *         &lt;element name="ic_construction" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_construction_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="coef_mod_icconstruction" type="{}t_coef_mod_icconstruction"/>
                 *         &lt;element name="ic_construction_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_energie" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_energie_max" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="coef_mod_icenergie" type="{}t_coef_mod_icenergie"/>
                 *         &lt;element name="ic_energie_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_composant" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_eau" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_chantier" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_zone" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_zone_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_parcelle" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_projet" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="ic_projet_occ" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *         &lt;element name="stock_c">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="udd">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="ic_ded" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *       &lt;/all>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {

                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class IndicateurPerfEnv {

                    @XmlElement(name = "ic_construction", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icConstruction;
                    @XmlElement(name = "ic_construction_max", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icConstructionMax;
                    @XmlElement(name = "coef_mod_icconstruction", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TCoefModIcconstruction coefModIcconstruction;
                    @XmlElement(name = "ic_construction_occ", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icConstructionOcc;
                    @XmlElement(name = "ic_energie", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icEnergie;
                    @XmlElement(name = "ic_energie_max", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icEnergieMax;
                    @XmlElement(name = "coef_mod_icenergie", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TCoefModIcenergie coefModIcenergie;
                    @XmlElement(name = "ic_energie_occ", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icEnergieOcc;
                    @XmlElement(name = "ic_composant", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icComposant;
                    @XmlElement(name = "ic_eau", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icEau;
                    @XmlElement(name = "ic_chantier", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icChantier;
                    @XmlElement(name = "ic_zone", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icZone;
                    @XmlElement(name = "ic_zone_occ", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icZoneOcc;
                    @XmlElement(name = "ic_parcelle", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icParcelle;
                    @XmlElement(name = "ic_projet", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icProjet;
                    @XmlElement(name = "ic_projet_occ", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icProjetOcc;
                    @XmlElement(name = "stock_c", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal stockC;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal udd;
                    @XmlElement(name = "ic_ded", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icDed;

                    /**
                     * Gets the value of the icConstruction property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcConstruction() {
                        return icConstruction;
                    }

                    /**
                     * Sets the value of the icConstruction property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcConstruction(BigDecimal value) {
                        this.icConstruction = value;
                    }

                    /**
                     * Gets the value of the icConstructionMax property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcConstructionMax() {
                        return icConstructionMax;
                    }

                    /**
                     * Sets the value of the icConstructionMax property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcConstructionMax(BigDecimal value) {
                        this.icConstructionMax = value;
                    }

                    /**
                     * Gets the value of the coefModIcconstruction property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TCoefModIcconstruction }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TCoefModIcconstruction getCoefModIcconstruction() {
                        return coefModIcconstruction;
                    }

                    /**
                     * Sets the value of the coefModIcconstruction property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TCoefModIcconstruction }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setCoefModIcconstruction(TCoefModIcconstruction value) {
                        this.coefModIcconstruction = value;
                    }

                    /**
                     * Gets the value of the icConstructionOcc property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcConstructionOcc() {
                        return icConstructionOcc;
                    }

                    /**
                     * Sets the value of the icConstructionOcc property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcConstructionOcc(BigDecimal value) {
                        this.icConstructionOcc = value;
                    }

                    /**
                     * Gets the value of the icEnergie property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcEnergie() {
                        return icEnergie;
                    }

                    /**
                     * Sets the value of the icEnergie property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcEnergie(BigDecimal value) {
                        this.icEnergie = value;
                    }

                    /**
                     * Gets the value of the icEnergieMax property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcEnergieMax() {
                        return icEnergieMax;
                    }

                    /**
                     * Sets the value of the icEnergieMax property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcEnergieMax(BigDecimal value) {
                        this.icEnergieMax = value;
                    }

                    /**
                     * Gets the value of the coefModIcenergie property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TCoefModIcenergie }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TCoefModIcenergie getCoefModIcenergie() {
                        return coefModIcenergie;
                    }

                    /**
                     * Sets the value of the coefModIcenergie property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TCoefModIcenergie }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setCoefModIcenergie(TCoefModIcenergie value) {
                        this.coefModIcenergie = value;
                    }

                    /**
                     * Gets the value of the icEnergieOcc property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcEnergieOcc() {
                        return icEnergieOcc;
                    }

                    /**
                     * Sets the value of the icEnergieOcc property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcEnergieOcc(BigDecimal value) {
                        this.icEnergieOcc = value;
                    }

                    /**
                     * Gets the value of the icComposant property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcComposant() {
                        return icComposant;
                    }

                    /**
                     * Sets the value of the icComposant property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcComposant(BigDecimal value) {
                        this.icComposant = value;
                    }

                    /**
                     * Gets the value of the icEau property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcEau() {
                        return icEau;
                    }

                    /**
                     * Sets the value of the icEau property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcEau(BigDecimal value) {
                        this.icEau = value;
                    }

                    /**
                     * Gets the value of the icChantier property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcChantier() {
                        return icChantier;
                    }

                    /**
                     * Sets the value of the icChantier property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcChantier(BigDecimal value) {
                        this.icChantier = value;
                    }

                    /**
                     * Gets the value of the icZone property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcZone() {
                        return icZone;
                    }

                    /**
                     * Sets the value of the icZone property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcZone(BigDecimal value) {
                        this.icZone = value;
                    }

                    /**
                     * Gets the value of the icZoneOcc property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcZoneOcc() {
                        return icZoneOcc;
                    }

                    /**
                     * Sets the value of the icZoneOcc property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcZoneOcc(BigDecimal value) {
                        this.icZoneOcc = value;
                    }

                    /**
                     * Gets the value of the icParcelle property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcParcelle() {
                        return icParcelle;
                    }

                    /**
                     * Sets the value of the icParcelle property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcParcelle(BigDecimal value) {
                        this.icParcelle = value;
                    }

                    /**
                     * Gets the value of the icProjet property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcProjet() {
                        return icProjet;
                    }

                    /**
                     * Sets the value of the icProjet property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcProjet(BigDecimal value) {
                        this.icProjet = value;
                    }

                    /**
                     * Gets the value of the icProjetOcc property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcProjetOcc() {
                        return icProjetOcc;
                    }

                    /**
                     * Sets the value of the icProjetOcc property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcProjetOcc(BigDecimal value) {
                        this.icProjetOcc = value;
                    }

                    /**
                     * Gets the value of the stockC property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getStockC() {
                        return stockC;
                    }

                    /**
                     * Sets the value of the stockC property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setStockC(BigDecimal value) {
                        this.stockC = value;
                    }

                    /**
                     * Gets the value of the udd property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getUdd() {
                        return udd;
                    }

                    /**
                     * Sets the value of the udd property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setUdd(BigDecimal value) {
                        this.udd = value;
                    }

                    /**
                     * Gets the value of the icDed property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcDed() {
                        return icDed;
                    }

                    /**
                     * Sets the value of the icDed property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcDed(BigDecimal value) {
                        this.icDed = value;
                    }

                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *         &lt;element name="contributeur">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="composant" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                             &lt;element name="stock_c">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="udd">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="ic">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="ic_ded">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="eau" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="chantier" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
         *                             &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "indicateursAcvCollection",
            "indicateurCo2Dynamique",
            "contributeur"
        })
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class Parcelle {

            @XmlElement(name = "indicateurs_acv_collection", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected TIndicateur indicateursAcvCollection;
            @XmlElement(name = "indicateur_co2_dynamique", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
            @XmlElement(required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected RSEnv.SortieProjet.Parcelle.Contributeur contributeur;

            /**
             * Gets the value of the indicateursAcvCollection property.
             * 
             * @return
             *     possible object is
             *     {@link TIndicateur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public TIndicateur getIndicateursAcvCollection() {
                return indicateursAcvCollection;
            }

            /**
             * Sets the value of the indicateursAcvCollection property.
             * 
             * @param value
             *     allowed object is
             *     {@link TIndicateur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndicateursAcvCollection(TIndicateur value) {
                this.indicateursAcvCollection = value;
            }

            /**
             * Gets the value of the indicateurCo2Dynamique property.
             * 
             * @return
             *     possible object is
             *     {@link TIndicateurCo2Dynamique }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                return indicateurCo2Dynamique;
            }

            /**
             * Sets the value of the indicateurCo2Dynamique property.
             * 
             * @param value
             *     allowed object is
             *     {@link TIndicateurCo2Dynamique }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                this.indicateurCo2Dynamique = value;
            }

            /**
             * Gets the value of the contributeur property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.SortieProjet.Parcelle.Contributeur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public RSEnv.SortieProjet.Parcelle.Contributeur getContributeur() {
                return contributeur;
            }

            /**
             * Sets the value of the contributeur property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.SortieProjet.Parcelle.Contributeur }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setContributeur(RSEnv.SortieProjet.Parcelle.Contributeur value) {
                this.contributeur = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;all>
             *         &lt;element name="composant" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                   &lt;element name="stock_c">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="udd">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="ic">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="ic_ded">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="eau" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="chantier" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
             *                   &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/all>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {

            })
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public static class Contributeur {

                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Parcelle.Contributeur.Composant composant;
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Parcelle.Contributeur.Eau eau;
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                protected RSEnv.SortieProjet.Parcelle.Contributeur.Chantier chantier;

                /**
                 * Gets the value of the composant property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Parcelle.Contributeur.Composant }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Parcelle.Contributeur.Composant getComposant() {
                    return composant;
                }

                /**
                 * Sets the value of the composant property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Parcelle.Contributeur.Composant }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setComposant(RSEnv.SortieProjet.Parcelle.Contributeur.Composant value) {
                    this.composant = value;
                }

                /**
                 * Gets the value of the eau property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Parcelle.Contributeur.Eau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Parcelle.Contributeur.Eau getEau() {
                    return eau;
                }

                /**
                 * Sets the value of the eau property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Parcelle.Contributeur.Eau }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setEau(RSEnv.SortieProjet.Parcelle.Contributeur.Eau value) {
                    this.eau = value;
                }

                /**
                 * Gets the value of the chantier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Parcelle.Contributeur.Chantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public RSEnv.SortieProjet.Parcelle.Contributeur.Chantier getChantier() {
                    return chantier;
                }

                /**
                 * Sets the value of the chantier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Parcelle.Contributeur.Chantier }
                 *     
                 */
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public void setChantier(RSEnv.SortieProjet.Parcelle.Contributeur.Chantier value) {
                    this.chantier = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Chantier {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *         &lt;element name="stock_c">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="udd">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="ic">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="ic_ded">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique",
                    "stockC",
                    "udd",
                    "ic",
                    "icDed"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Composant {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;
                    @XmlElement(name = "stock_c", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal stockC;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal udd;
                    @XmlElement(required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal ic;
                    @XmlElement(name = "ic_ded", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected BigDecimal icDed;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                    /**
                     * Gets the value of the stockC property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getStockC() {
                        return stockC;
                    }

                    /**
                     * Sets the value of the stockC property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setStockC(BigDecimal value) {
                        this.stockC = value;
                    }

                    /**
                     * Gets the value of the udd property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getUdd() {
                        return udd;
                    }

                    /**
                     * Sets the value of the udd property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setUdd(BigDecimal value) {
                        this.udd = value;
                    }

                    /**
                     * Gets the value of the ic property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIc() {
                        return ic;
                    }

                    /**
                     * Sets the value of the ic property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIc(BigDecimal value) {
                        this.ic = value;
                    }

                    /**
                     * Gets the value of the icDed property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public BigDecimal getIcDed() {
                        return icDed;
                    }

                    /**
                     * Sets the value of the icDed property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIcDed(BigDecimal value) {
                        this.icDed = value;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="indicateurs_acv_collection" type="{}t_indicateur"/>
                 *         &lt;element name="indicateur_co2_dynamique" type="{}t_indicateur_co2_dynamique"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "indicateursAcvCollection",
                    "indicateurCo2Dynamique"
                })
                @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                public static class Eau {

                    @XmlElement(name = "indicateurs_acv_collection", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateur indicateursAcvCollection;
                    @XmlElement(name = "indicateur_co2_dynamique", required = true)
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    protected TIndicateurCo2Dynamique indicateurCo2Dynamique;

                    /**
                     * Gets the value of the indicateursAcvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateur getIndicateursAcvCollection() {
                        return indicateursAcvCollection;
                    }

                    /**
                     * Sets the value of the indicateursAcvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateursAcvCollection(TIndicateur value) {
                        this.indicateursAcvCollection = value;
                    }

                    /**
                     * Gets the value of the indicateurCo2Dynamique property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public TIndicateurCo2Dynamique getIndicateurCo2Dynamique() {
                        return indicateurCo2Dynamique;
                    }

                    /**
                     * Sets the value of the indicateurCo2Dynamique property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateurCo2Dynamique }
                     *     
                     */
                    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
                    public void setIndicateurCo2Dynamique(TIndicateurCo2Dynamique value) {
                        this.indicateurCo2Dynamique = value;
                    }

                }

            }

        }

    }

}
