
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
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
 *                             &lt;element name="zone" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="usage">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                             &lt;enumeration value="19"/>
 *                                             &lt;enumeration value="20"/>
 *                                             &lt;enumeration value="22"/>
 *                                             &lt;enumeration value="24"/>
 *                                             &lt;enumeration value="26"/>
 *                                             &lt;enumeration value="27"/>
 *                                             &lt;enumeration value="28"/>
 *                                             &lt;enumeration value="29"/>
 *                                             &lt;enumeration value="30"/>
 *                                             &lt;enumeration value="32"/>
 *                                             &lt;enumeration value="33"/>
 *                                             &lt;enumeration value="34"/>
 *                                             &lt;enumeration value="36"/>
 *                                             &lt;enumeration value="37"/>
 *                                             &lt;enumeration value="38"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="sdp">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;choice minOccurs="0">
 *                                                 &lt;element name="donnees_service" maxOccurs="unbounded">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;all>
 *                                                           &lt;element name="id_base">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="id_fiche" type="{}p_string500"/>
 *                                                           &lt;element name="id_produit" type="{}p_string500"/>
 *                                                           &lt;element name="quantite">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                                                 &lt;minExclusive value="0"/>
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element ref="{}unite_uf"/>
 *                                                           &lt;element name="chantier_consommations" minOccurs="0">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                 &lt;enumeration value="1"/>
 *                                                                 &lt;enumeration value="2"/>
 *                                                                 &lt;enumeration value="3"/>
 *                                                                 &lt;enumeration value="4"/>
 *                                                                 &lt;enumeration value="5"/>
 *                                                                 &lt;enumeration value="6"/>
 *                                                                 &lt;enumeration value="7"/>
 *                                                                 &lt;enumeration value="8"/>
 *                                                                 &lt;enumeration value="9"/>
 *                                                                 &lt;enumeration value="10"/>
 *                                                                 &lt;enumeration value="11"/>
 *                                                                 &lt;enumeration value="12"/>
 *                                                                 &lt;enumeration value="13"/>
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="energie_vecteur" minOccurs="0">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                 &lt;enumeration value="1"/>
 *                                                                 &lt;enumeration value="2"/>
 *                                                                 &lt;enumeration value="3"/>
 *                                                                 &lt;enumeration value="4"/>
 *                                                                 &lt;enumeration value="5"/>
 *                                                                 &lt;enumeration value="6"/>
 *                                                                 &lt;enumeration value="7"/>
 *                                                                 &lt;enumeration value="8"/>
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="energie_usage" minOccurs="0">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                 &lt;enumeration value="1"/>
 *                                                                 &lt;enumeration value="2"/>
 *                                                                 &lt;enumeration value="3"/>
 *                                                                 &lt;enumeration value="4"/>
 *                                                                 &lt;enumeration value="5"/>
 *                                                                 &lt;enumeration value="6"/>
 *                                                                 &lt;enumeration value="7"/>
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="eau_type" minOccurs="0">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                 &lt;enumeration value="1"/>
 *                                                                 &lt;enumeration value="2"/>
 *                                                                 &lt;enumeration value="3"/>
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="commentaire" type="{}p_string500" minOccurs="0"/>
 *                                                         &lt;/all>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="lot" maxOccurs="14" minOccurs="2">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;choice minOccurs="0">
 *                                                           &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
 *                                                           &lt;element name="sous_lot" maxOccurs="unbounded">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
 *                                       &lt;element name="partie_commune">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;minInclusive value="0"/>
 *                                             &lt;maxInclusive value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
 *                                       &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="periode_reference" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="production_pv" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="autoconsommation_pv" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="cogeneration" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="autoconsommation_cogeneration" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="date_etude" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
 *                             &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
 *                                       &lt;element name="lot" maxOccurs="14" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;all>
 *                                                           &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
 *                                                         &lt;/all>
 *                                                         &lt;attribute name="ref" use="required">
 *                                                           &lt;simpleType>
 *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                             &lt;/restriction>
 *                                                           &lt;/simpleType>
 *                                                         &lt;/attribute>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
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
 *                                     &lt;/sequence>
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
 *                             &lt;element name="zone" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
 *                                                 &lt;element name="lot" maxOccurs="14" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
 *                                                           &lt;element name="valeur_forfaitaire">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                 &lt;enumeration value="0"/>
 *                                                                 &lt;enumeration value="1"/>
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;all>
 *                                                                     &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
 *                                                                   &lt;/all>
 *                                                                   &lt;attribute name="ref" use="required">
 *                                                                     &lt;simpleType>
 *                                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                                                       &lt;/restriction>
 *                                                                     &lt;/simpleType>
 *                                                                   &lt;/attribute>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
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
 *                                               &lt;/sequence>
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
 *                                       &lt;element ref="{}indicateurs_performance_collection"/>
 *                                       &lt;element name="coefficients_modulateurs">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="mgctype" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                                 &lt;element name="mgcgeo" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                                 &lt;element name="mgcalt" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                                 &lt;element name="mgcsurf" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                                                 &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *                                                 &lt;element name="valeurs_seuils" maxOccurs="8" minOccurs="8">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                                           &lt;element name="nom" minOccurs="0">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                               &lt;/restriction>
 *                                                             &lt;/simpleType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="unite" minOccurs="0">
 *                                                             &lt;simpleType>
 *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                                 &lt;minLength value="1"/>
 *                                                                 &lt;whiteSpace value="collapse"/>
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
 *                             &lt;element name="part_donnees_generiques">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="part_impact_donnees_generiques" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="indicateur_completude" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element ref="{}indicateurs_performance_collection"/>
 *                             &lt;element name="coefficients_modulateurs">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="data_comp">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="donnees_generales">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="maitre_ouvrage">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="type">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="adresse" type="{}t_adresse"/>
 *                                       &lt;element name="SIRET" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;length value="14"/>
 *                                             &lt;whiteSpace value="collapse"/>
 *                                             &lt;pattern value="[0-9]{14}"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="bureau_etude_acv" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="adresse" type="{}t_adresse"/>
 *                                       &lt;element name="SIRET">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;length value="14"/>
 *                                             &lt;whiteSpace value="collapse"/>
 *                                             &lt;pattern value="[0-9]{14}"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="maitre_oeuvre" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="entreprise" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="logiciel">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="editeur" type="{}p_string500"/>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="version">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;whiteSpace value="collapse"/>
 *                                             &lt;minLength value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="operation">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="description" type="{}p_string2000" minOccurs="0"/>
 *                                       &lt;element name="adresse" type="{}t_adresse"/>
 *                                       &lt;element name="date_depot_PC">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="date_obtention_PC" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                                       &lt;element name="date_livraison" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                                       &lt;element name="num_permis" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;whiteSpace value="collapse"/>
 *                                             &lt;pattern value="PC[A-B0-9]{3}[0-9]{3}[0-2][0-9][A-Z0-9]{5}"/>
 *                                             &lt;pattern value="en cours"/>
 *                                             &lt;pattern value="EN COURS"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="surface_parcelle">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="nb_batiment">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;minInclusive value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="surface_arrosee">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="surface_veg" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="surface_imper" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="geotech" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                             &lt;enumeration value="19"/>
 *                                             &lt;enumeration value="20"/>
 *                                             &lt;enumeration value="21"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="sol_pollution" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="zone_climatique">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;whiteSpace value="collapse"/>
 *                                             &lt;minLength value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="altitude">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="zone_sismique">
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
 *                                       &lt;element name="commentaire_acv" type="{}p_string2000" minOccurs="0"/>
 *                                       &lt;element name="cadastre" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="reference_cadastrale" maxOccurs="unbounded">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                       &lt;whiteSpace value="collapse"/>
 *                                                       &lt;pattern value="[0-9]{3}[a-zA-Z0-9]{2}[0-9]{4}"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
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
 *                             &lt;element name="reseau" maxOccurs="2" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="type">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="localisation" type="{}p_string500"/>
 *                                       &lt;element name="taux_enr">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                             &lt;maxInclusive value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="part_cogeneration">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                             &lt;minInclusive value="0"/>
 *                                             &lt;maxInclusive value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="contenu_co2">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
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
 *                   &lt;element name="batiment" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="usage_principal">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="1"/>
 *                                   &lt;enumeration value="2"/>
 *                                   &lt;enumeration value="3"/>
 *                                   &lt;enumeration value="4"/>
 *                                   &lt;enumeration value="5"/>
 *                                   &lt;enumeration value="6"/>
 *                                   &lt;enumeration value="7"/>
 *                                   &lt;enumeration value="8"/>
 *                                   &lt;enumeration value="10"/>
 *                                   &lt;enumeration value="11"/>
 *                                   &lt;enumeration value="12"/>
 *                                   &lt;enumeration value="13"/>
 *                                   &lt;enumeration value="14"/>
 *                                   &lt;enumeration value="15"/>
 *                                   &lt;enumeration value="16"/>
 *                                   &lt;enumeration value="17"/>
 *                                   &lt;enumeration value="18"/>
 *                                   &lt;enumeration value="19"/>
 *                                   &lt;enumeration value="20"/>
 *                                   &lt;enumeration value="22"/>
 *                                   &lt;enumeration value="24"/>
 *                                   &lt;enumeration value="26"/>
 *                                   &lt;enumeration value="27"/>
 *                                   &lt;enumeration value="28"/>
 *                                   &lt;enumeration value="29"/>
 *                                   &lt;enumeration value="30"/>
 *                                   &lt;enumeration value="32"/>
 *                                   &lt;enumeration value="33"/>
 *                                   &lt;enumeration value="34"/>
 *                                   &lt;enumeration value="36"/>
 *                                   &lt;enumeration value="37"/>
 *                                   &lt;enumeration value="38"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="precisions_usage" type="{}p_string500" minOccurs="0"/>
 *                             &lt;element name="cadre_rt">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="0"/>
 *                                   &lt;enumeration value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="commentaires" type="{}p_string2000" minOccurs="0"/>
 *                             &lt;element name="nb_unite_fonctionnalite" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="nb_occupant" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="sdp">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minExclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="srt" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="shab" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="surt" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="emprise_au_sol">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minExclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nb_niv_ssol">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nb_niv_surface">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="duree_chantier">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minExclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="ccmi" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="0"/>
 *                                   &lt;enumeration value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="signe_qualite" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="label" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="label_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="certification" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;pattern value="[^-|_|0|\s]{1}|[\w\W]{2,}"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="certification_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
 *                                       &lt;element name="demarche_environnementale" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="gps" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="longitude">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;minLength value="1"/>
 *                                             &lt;whiteSpace value="collapse"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="latitude">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                             &lt;whiteSpace value="collapse"/>
 *                                             &lt;minLength value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="verificateur" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="adresse" type="{}t_adresse"/>
 *                                       &lt;element name="date_verification" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="certificateur" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="nom" type="{}p_string500"/>
 *                                       &lt;element name="adresse" type="{}t_adresse"/>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="donnees_techniques">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="commentaires_donnees_techniques" type="{}p_string500" minOccurs="0"/>
 *                                       &lt;element name="type_structure_principale">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="commentaires_structure" type="{}p_string500" minOccurs="0"/>
 *                                       &lt;element name="elements_prefabriques">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;pattern value="0"/>
 *                                             &lt;pattern value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="materiau_principal">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="materiau_remplissage_facade">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="materiau_remplissage_facade_autre" type="{}p_string500" minOccurs="0"/>
 *                                       &lt;element name="mur_mode_isolation">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="mur_nature_isolant">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="mur_revetement_ext">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_fondation">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_plancher">
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
 *                                       &lt;element name="plancher_mode_isolation">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="plancher_nature_isolant">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="plancher_nature_espace">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_toiture">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="toiture_mode_isolation">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="toiture_nature_isolant">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="toiture_vegetalisee">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="toiture_pente" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
 *                                             &lt;minInclusive value="0"/>
 *                                             &lt;maxInclusive value="90"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="toiture_couverture">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_menuiserie">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_pm">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="vecteur_energie_principal_ch">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="vecteur_energie_principal_ecs">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="vecteur_energie_principal_fr">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="generateur_principal_ch">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                             &lt;enumeration value="19"/>
 *                                             &lt;enumeration value="20"/>
 *                                             &lt;enumeration value="21"/>
 *                                             &lt;enumeration value="22"/>
 *                                             &lt;enumeration value="23"/>
 *                                             &lt;enumeration value="24"/>
 *                                             &lt;enumeration value="25"/>
 *                                             &lt;enumeration value="27"/>
 *                                             &lt;enumeration value="28"/>
 *                                             &lt;enumeration value="29"/>
 *                                             &lt;enumeration value="30"/>
 *                                             &lt;enumeration value="31"/>
 *                                             &lt;enumeration value="32"/>
 *                                             &lt;enumeration value="33"/>
 *                                             &lt;enumeration value="41"/>
 *                                             &lt;enumeration value="42"/>
 *                                             &lt;enumeration value="43"/>
 *                                             &lt;enumeration value="44"/>
 *                                             &lt;enumeration value="45"/>
 *                                             &lt;enumeration value="46"/>
 *                                             &lt;enumeration value="47"/>
 *                                             &lt;enumeration value="48"/>
 *                                             &lt;enumeration value="49"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="generateur_ch_liaison">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="emetteur_principal_ch">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="generateur_appoint_ch" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="generateur_principal_ecs" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                             &lt;enumeration value="19"/>
 *                                             &lt;enumeration value="20"/>
 *                                             &lt;enumeration value="21"/>
 *                                             &lt;enumeration value="22"/>
 *                                             &lt;enumeration value="23"/>
 *                                             &lt;enumeration value="24"/>
 *                                             &lt;enumeration value="25"/>
 *                                             &lt;enumeration value="26"/>
 *                                             &lt;enumeration value="27"/>
 *                                             &lt;enumeration value="28"/>
 *                                             &lt;enumeration value="29"/>
 *                                             &lt;enumeration value="30"/>
 *                                             &lt;enumeration value="31"/>
 *                                             &lt;enumeration value="32"/>
 *                                             &lt;enumeration value="33"/>
 *                                             &lt;enumeration value="34"/>
 *                                             &lt;enumeration value="35"/>
 *                                             &lt;enumeration value="36"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="generateur_principal_fr" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="14"/>
 *                                             &lt;enumeration value="15"/>
 *                                             &lt;enumeration value="16"/>
 *                                             &lt;enumeration value="17"/>
 *                                             &lt;enumeration value="18"/>
 *                                             &lt;enumeration value="19"/>
 *                                             &lt;enumeration value="20"/>
 *                                             &lt;enumeration value="21"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_ventilation_principale">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                             &lt;enumeration value="7"/>
 *                                             &lt;enumeration value="8"/>
 *                                             &lt;enumeration value="9"/>
 *                                             &lt;enumeration value="10"/>
 *                                             &lt;enumeration value="11"/>
 *                                             &lt;enumeration value="12"/>
 *                                             &lt;enumeration value="13"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="commentaires_prod_electricite" type="{}p_string500" minOccurs="0"/>
 *                                       &lt;element name="stockage_electricite" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="gestion_active">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="type_eclairage">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                             &lt;enumeration value="0"/>
 *                                             &lt;enumeration value="1"/>
 *                                             &lt;enumeration value="2"/>
 *                                             &lt;enumeration value="3"/>
 *                                             &lt;enumeration value="4"/>
 *                                             &lt;enumeration value="5"/>
 *                                             &lt;enumeration value="6"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/all>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="maquette_numerique">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="0"/>
 *                                   &lt;enumeration value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="zone_br" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                   &lt;enumeration value="1"/>
 *                                   &lt;enumeration value="2"/>
 *                                   &lt;enumeration value="3"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nb_place_parking_surface_plu">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nb_place_parking_ssol_plu">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nb_place_parking_surface">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="nb_place_parking_ssol">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
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
 *       &lt;/all>
 *       &lt;attribute name="version" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;whiteSpace value="collapse"/>
 *             &lt;enumeration value="1.1.0.0"/>
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
public class RSEnv {

    @XmlElement(name = "entree_projet", required = true)
    protected RSEnv.EntreeProjet entreeProjet;
    @XmlElement(name = "sortie_projet", required = true)
    protected RSEnv.SortieProjet sortieProjet;
    @XmlElement(name = "data_comp", required = true)
    protected RSEnv.DataComp dataComp;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "phase", required = true)
    protected Integer phase;

    /**
     * Gets the value of the entreeProjet property.
     * 
     * @return
     *     possible object is
     *     {@link RSEnv.EntreeProjet }
     *     
     */
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
    public void setSortieProjet(RSEnv.SortieProjet value) {
        this.sortieProjet = value;
    }

    /**
     * Gets the value of the dataComp property.
     * 
     * @return
     *     possible object is
     *     {@link RSEnv.DataComp }
     *     
     */
    public RSEnv.DataComp getDataComp() {
        return dataComp;
    }

    /**
     * Sets the value of the dataComp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSEnv.DataComp }
     *     
     */
    public void setDataComp(RSEnv.DataComp value) {
        this.dataComp = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the phase property.
     * 
     */
    public Integer getPhase() {
        return phase;
    }

    /**
     * Sets the value of the phase property.
     * 
     */
    public void setPhase(Integer value) {
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
     *         &lt;element name="donnees_generales">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="maitre_ouvrage">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                             &lt;element name="type">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="adresse" type="{}t_adresse"/>
     *                             &lt;element name="SIRET" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;length value="14"/>
     *                                   &lt;whiteSpace value="collapse"/>
     *                                   &lt;pattern value="[0-9]{14}"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="bureau_etude_acv" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                             &lt;element name="adresse" type="{}t_adresse"/>
     *                             &lt;element name="SIRET">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;length value="14"/>
     *                                   &lt;whiteSpace value="collapse"/>
     *                                   &lt;pattern value="[0-9]{14}"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="maitre_oeuvre" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="entreprise" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="logiciel">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="editeur" type="{}p_string500"/>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                             &lt;element name="version">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;whiteSpace value="collapse"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="operation">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                             &lt;element name="description" type="{}p_string2000" minOccurs="0"/>
     *                             &lt;element name="adresse" type="{}t_adresse"/>
     *                             &lt;element name="date_depot_PC">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="date_obtention_PC" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *                             &lt;element name="date_livraison" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *                             &lt;element name="num_permis" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;whiteSpace value="collapse"/>
     *                                   &lt;pattern value="PC[A-B0-9]{3}[0-9]{3}[0-2][0-9][A-Z0-9]{5}"/>
     *                                   &lt;pattern value="en cours"/>
     *                                   &lt;pattern value="EN COURS"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="surface_parcelle">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="nb_batiment">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;minInclusive value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="surface_arrosee">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="surface_veg" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="surface_imper" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="geotech" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                   &lt;enumeration value="19"/>
     *                                   &lt;enumeration value="20"/>
     *                                   &lt;enumeration value="21"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="sol_pollution" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="zone_climatique">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;whiteSpace value="collapse"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="altitude">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="zone_sismique">
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
     *                             &lt;element name="commentaire_acv" type="{}p_string2000" minOccurs="0"/>
     *                             &lt;element name="cadastre" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="reference_cadastrale" maxOccurs="unbounded">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                             &lt;whiteSpace value="collapse"/>
     *                                             &lt;pattern value="[0-9]{3}[a-zA-Z0-9]{2}[0-9]{4}"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
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
     *                   &lt;element name="reseau" maxOccurs="2" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
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
     *                             &lt;element name="taux_enr">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                   &lt;maxInclusive value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="part_cogeneration">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                   &lt;maxInclusive value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="contenu_co2">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
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
     *         &lt;element name="batiment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="usage_principal">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="1"/>
     *                         &lt;enumeration value="2"/>
     *                         &lt;enumeration value="3"/>
     *                         &lt;enumeration value="4"/>
     *                         &lt;enumeration value="5"/>
     *                         &lt;enumeration value="6"/>
     *                         &lt;enumeration value="7"/>
     *                         &lt;enumeration value="8"/>
     *                         &lt;enumeration value="10"/>
     *                         &lt;enumeration value="11"/>
     *                         &lt;enumeration value="12"/>
     *                         &lt;enumeration value="13"/>
     *                         &lt;enumeration value="14"/>
     *                         &lt;enumeration value="15"/>
     *                         &lt;enumeration value="16"/>
     *                         &lt;enumeration value="17"/>
     *                         &lt;enumeration value="18"/>
     *                         &lt;enumeration value="19"/>
     *                         &lt;enumeration value="20"/>
     *                         &lt;enumeration value="22"/>
     *                         &lt;enumeration value="24"/>
     *                         &lt;enumeration value="26"/>
     *                         &lt;enumeration value="27"/>
     *                         &lt;enumeration value="28"/>
     *                         &lt;enumeration value="29"/>
     *                         &lt;enumeration value="30"/>
     *                         &lt;enumeration value="32"/>
     *                         &lt;enumeration value="33"/>
     *                         &lt;enumeration value="34"/>
     *                         &lt;enumeration value="36"/>
     *                         &lt;enumeration value="37"/>
     *                         &lt;enumeration value="38"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="precisions_usage" type="{}p_string500" minOccurs="0"/>
     *                   &lt;element name="cadre_rt">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="0"/>
     *                         &lt;enumeration value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="commentaires" type="{}p_string2000" minOccurs="0"/>
     *                   &lt;element name="nb_unite_fonctionnalite" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="nb_occupant" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="sdp">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minExclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="srt" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="shab" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="surt" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="emprise_au_sol">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minExclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nb_niv_ssol">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nb_niv_surface">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="duree_chantier">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minExclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="ccmi" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="0"/>
     *                         &lt;enumeration value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="signe_qualite" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="label" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="label_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
     *                             &lt;element name="certification" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;pattern value="[^-|_|0|\s]{1}|[\w\W]{2,}"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="certification_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
     *                             &lt;element name="demarche_environnementale" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="gps" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="longitude">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;whiteSpace value="collapse"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="latitude">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;whiteSpace value="collapse"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="verificateur" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                             &lt;element name="adresse" type="{}t_adresse"/>
     *                             &lt;element name="date_verification" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="certificateur" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="nom" type="{}p_string500"/>
     *                             &lt;element name="adresse" type="{}t_adresse"/>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="donnees_techniques">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="commentaires_donnees_techniques" type="{}p_string500" minOccurs="0"/>
     *                             &lt;element name="type_structure_principale">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="commentaires_structure" type="{}p_string500" minOccurs="0"/>
     *                             &lt;element name="elements_prefabriques">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;pattern value="0"/>
     *                                   &lt;pattern value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="materiau_principal">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="materiau_remplissage_facade">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="materiau_remplissage_facade_autre" type="{}p_string500" minOccurs="0"/>
     *                             &lt;element name="mur_mode_isolation">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="mur_nature_isolant">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="mur_revetement_ext">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_fondation">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_plancher">
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
     *                             &lt;element name="plancher_mode_isolation">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="plancher_nature_isolant">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="plancher_nature_espace">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_toiture">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="toiture_mode_isolation">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="toiture_nature_isolant">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="toiture_vegetalisee">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="toiture_pente" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
     *                                   &lt;minInclusive value="0"/>
     *                                   &lt;maxInclusive value="90"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="toiture_couverture">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_menuiserie">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_pm">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="vecteur_energie_principal_ch">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="vecteur_energie_principal_ecs">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="vecteur_energie_principal_fr">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="generateur_principal_ch">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                   &lt;enumeration value="19"/>
     *                                   &lt;enumeration value="20"/>
     *                                   &lt;enumeration value="21"/>
     *                                   &lt;enumeration value="22"/>
     *                                   &lt;enumeration value="23"/>
     *                                   &lt;enumeration value="24"/>
     *                                   &lt;enumeration value="25"/>
     *                                   &lt;enumeration value="27"/>
     *                                   &lt;enumeration value="28"/>
     *                                   &lt;enumeration value="29"/>
     *                                   &lt;enumeration value="30"/>
     *                                   &lt;enumeration value="31"/>
     *                                   &lt;enumeration value="32"/>
     *                                   &lt;enumeration value="33"/>
     *                                   &lt;enumeration value="41"/>
     *                                   &lt;enumeration value="42"/>
     *                                   &lt;enumeration value="43"/>
     *                                   &lt;enumeration value="44"/>
     *                                   &lt;enumeration value="45"/>
     *                                   &lt;enumeration value="46"/>
     *                                   &lt;enumeration value="47"/>
     *                                   &lt;enumeration value="48"/>
     *                                   &lt;enumeration value="49"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="generateur_ch_liaison">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="emetteur_principal_ch">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="generateur_appoint_ch" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="generateur_principal_ecs" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                   &lt;enumeration value="19"/>
     *                                   &lt;enumeration value="20"/>
     *                                   &lt;enumeration value="21"/>
     *                                   &lt;enumeration value="22"/>
     *                                   &lt;enumeration value="23"/>
     *                                   &lt;enumeration value="24"/>
     *                                   &lt;enumeration value="25"/>
     *                                   &lt;enumeration value="26"/>
     *                                   &lt;enumeration value="27"/>
     *                                   &lt;enumeration value="28"/>
     *                                   &lt;enumeration value="29"/>
     *                                   &lt;enumeration value="30"/>
     *                                   &lt;enumeration value="31"/>
     *                                   &lt;enumeration value="32"/>
     *                                   &lt;enumeration value="33"/>
     *                                   &lt;enumeration value="34"/>
     *                                   &lt;enumeration value="35"/>
     *                                   &lt;enumeration value="36"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="generateur_principal_fr" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                   &lt;enumeration value="19"/>
     *                                   &lt;enumeration value="20"/>
     *                                   &lt;enumeration value="21"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_ventilation_principale">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="9"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="commentaires_prod_electricite" type="{}p_string500" minOccurs="0"/>
     *                             &lt;element name="stockage_electricite" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="gestion_active">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="type_eclairage">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/all>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="maquette_numerique">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="0"/>
     *                         &lt;enumeration value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="zone_br" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                         &lt;enumeration value="1"/>
     *                         &lt;enumeration value="2"/>
     *                         &lt;enumeration value="3"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nb_place_parking_surface_plu">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nb_place_parking_ssol_plu">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nb_place_parking_surface">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="nb_place_parking_ssol">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
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
        "donneesGenerales",
        "batiment"
    })
    public static class DataComp {

        @XmlElement(name = "donnees_generales", required = true)
        protected RSEnv.DataComp.DonneesGenerales donneesGenerales;
        @XmlElement(required = true)
        protected List<RSEnv.DataComp.Batiment> batiment;

        /**
         * Gets the value of the donneesGenerales property.
         * 
         * @return
         *     possible object is
         *     {@link RSEnv.DataComp.DonneesGenerales }
         *     
         */
        public RSEnv.DataComp.DonneesGenerales getDonneesGenerales() {
            return donneesGenerales;
        }

        /**
         * Sets the value of the donneesGenerales property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSEnv.DataComp.DonneesGenerales }
         *     
         */
        public void setDonneesGenerales(RSEnv.DataComp.DonneesGenerales value) {
            this.donneesGenerales = value;
        }

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
         * {@link RSEnv.DataComp.Batiment }
         * 
         * 
         */
        public List<RSEnv.DataComp.Batiment> getBatiment() {
            if (batiment == null) {
                batiment = new ArrayList<RSEnv.DataComp.Batiment>();
            }
            return this.batiment;
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
         *         &lt;element name="usage_principal">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="1"/>
         *               &lt;enumeration value="2"/>
         *               &lt;enumeration value="3"/>
         *               &lt;enumeration value="4"/>
         *               &lt;enumeration value="5"/>
         *               &lt;enumeration value="6"/>
         *               &lt;enumeration value="7"/>
         *               &lt;enumeration value="8"/>
         *               &lt;enumeration value="10"/>
         *               &lt;enumeration value="11"/>
         *               &lt;enumeration value="12"/>
         *               &lt;enumeration value="13"/>
         *               &lt;enumeration value="14"/>
         *               &lt;enumeration value="15"/>
         *               &lt;enumeration value="16"/>
         *               &lt;enumeration value="17"/>
         *               &lt;enumeration value="18"/>
         *               &lt;enumeration value="19"/>
         *               &lt;enumeration value="20"/>
         *               &lt;enumeration value="22"/>
         *               &lt;enumeration value="24"/>
         *               &lt;enumeration value="26"/>
         *               &lt;enumeration value="27"/>
         *               &lt;enumeration value="28"/>
         *               &lt;enumeration value="29"/>
         *               &lt;enumeration value="30"/>
         *               &lt;enumeration value="32"/>
         *               &lt;enumeration value="33"/>
         *               &lt;enumeration value="34"/>
         *               &lt;enumeration value="36"/>
         *               &lt;enumeration value="37"/>
         *               &lt;enumeration value="38"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="precisions_usage" type="{}p_string500" minOccurs="0"/>
         *         &lt;element name="cadre_rt">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="0"/>
         *               &lt;enumeration value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="commentaires" type="{}p_string2000" minOccurs="0"/>
         *         &lt;element name="nb_unite_fonctionnalite" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="nb_occupant" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="sdp">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minExclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="srt" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="shab" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="surt" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="emprise_au_sol">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minExclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nb_niv_ssol">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nb_niv_surface">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="duree_chantier">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minExclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="ccmi" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="0"/>
         *               &lt;enumeration value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="signe_qualite" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="label" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="label_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
         *                   &lt;element name="certification" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;pattern value="[^-|_|0|\s]{1}|[\w\W]{2,}"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="certification_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
         *                   &lt;element name="demarche_environnementale" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="gps" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="longitude">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;whiteSpace value="collapse"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="latitude">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;whiteSpace value="collapse"/>
         *                         &lt;minLength value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="verificateur" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                   &lt;element name="adresse" type="{}t_adresse"/>
         *                   &lt;element name="date_verification" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="certificateur" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                   &lt;element name="adresse" type="{}t_adresse"/>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="donnees_techniques">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="commentaires_donnees_techniques" type="{}p_string500" minOccurs="0"/>
         *                   &lt;element name="type_structure_principale">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="commentaires_structure" type="{}p_string500" minOccurs="0"/>
         *                   &lt;element name="elements_prefabriques">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;pattern value="0"/>
         *                         &lt;pattern value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="materiau_principal">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="materiau_remplissage_facade">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="materiau_remplissage_facade_autre" type="{}p_string500" minOccurs="0"/>
         *                   &lt;element name="mur_mode_isolation">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="mur_nature_isolant">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="mur_revetement_ext">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_fondation">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_plancher">
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
         *                   &lt;element name="plancher_mode_isolation">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="plancher_nature_isolant">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="plancher_nature_espace">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_toiture">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="toiture_mode_isolation">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="toiture_nature_isolant">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="toiture_vegetalisee">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="toiture_pente" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
         *                         &lt;minInclusive value="0"/>
         *                         &lt;maxInclusive value="90"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="toiture_couverture">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_menuiserie">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_pm">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="vecteur_energie_principal_ch">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="vecteur_energie_principal_ecs">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="vecteur_energie_principal_fr">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="generateur_principal_ch">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                         &lt;enumeration value="19"/>
         *                         &lt;enumeration value="20"/>
         *                         &lt;enumeration value="21"/>
         *                         &lt;enumeration value="22"/>
         *                         &lt;enumeration value="23"/>
         *                         &lt;enumeration value="24"/>
         *                         &lt;enumeration value="25"/>
         *                         &lt;enumeration value="27"/>
         *                         &lt;enumeration value="28"/>
         *                         &lt;enumeration value="29"/>
         *                         &lt;enumeration value="30"/>
         *                         &lt;enumeration value="31"/>
         *                         &lt;enumeration value="32"/>
         *                         &lt;enumeration value="33"/>
         *                         &lt;enumeration value="41"/>
         *                         &lt;enumeration value="42"/>
         *                         &lt;enumeration value="43"/>
         *                         &lt;enumeration value="44"/>
         *                         &lt;enumeration value="45"/>
         *                         &lt;enumeration value="46"/>
         *                         &lt;enumeration value="47"/>
         *                         &lt;enumeration value="48"/>
         *                         &lt;enumeration value="49"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="generateur_ch_liaison">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="emetteur_principal_ch">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="generateur_appoint_ch" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="generateur_principal_ecs" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                         &lt;enumeration value="19"/>
         *                         &lt;enumeration value="20"/>
         *                         &lt;enumeration value="21"/>
         *                         &lt;enumeration value="22"/>
         *                         &lt;enumeration value="23"/>
         *                         &lt;enumeration value="24"/>
         *                         &lt;enumeration value="25"/>
         *                         &lt;enumeration value="26"/>
         *                         &lt;enumeration value="27"/>
         *                         &lt;enumeration value="28"/>
         *                         &lt;enumeration value="29"/>
         *                         &lt;enumeration value="30"/>
         *                         &lt;enumeration value="31"/>
         *                         &lt;enumeration value="32"/>
         *                         &lt;enumeration value="33"/>
         *                         &lt;enumeration value="34"/>
         *                         &lt;enumeration value="35"/>
         *                         &lt;enumeration value="36"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="generateur_principal_fr" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                         &lt;enumeration value="19"/>
         *                         &lt;enumeration value="20"/>
         *                         &lt;enumeration value="21"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_ventilation_principale">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="commentaires_prod_electricite" type="{}p_string500" minOccurs="0"/>
         *                   &lt;element name="stockage_electricite" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="gestion_active">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="type_eclairage">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="maquette_numerique">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="0"/>
         *               &lt;enumeration value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="zone_br" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *               &lt;enumeration value="1"/>
         *               &lt;enumeration value="2"/>
         *               &lt;enumeration value="3"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nb_place_parking_surface_plu">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nb_place_parking_ssol_plu">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nb_place_parking_surface">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="nb_place_parking_ssol">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
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
        public static class Batiment {

            protected int index;
            @XmlElement(name = "usage_principal")
            protected int usagePrincipal;
            @XmlElement(name = "precisions_usage")
            protected String precisionsUsage;
            @XmlElement(name = "cadre_rt", defaultValue = "1")
            protected int cadreRt;
            protected String commentaires;
            @XmlElement(name = "nb_unite_fonctionnalite")
            protected BigDecimal nbUniteFonctionnalite;
            @XmlElement(name = "nb_occupant")
            protected BigDecimal nbOccupant;
            @XmlElement(required = true)
            protected BigDecimal sdp;
            protected BigDecimal srt;
            protected BigDecimal shab;
            protected BigDecimal surt;
            @XmlElement(name = "emprise_au_sol", required = true)
            protected BigDecimal empriseAuSol;
            @XmlElement(name = "nb_niv_ssol")
            protected int nbNivSsol;
            @XmlElement(name = "nb_niv_surface")
            protected int nbNivSurface;
            @XmlElement(name = "duree_chantier", required = true)
            protected BigDecimal dureeChantier;
            protected Integer ccmi;
            @XmlElement(name = "signe_qualite")
            protected RSEnv.DataComp.Batiment.SigneQualite signeQualite;
            protected RSEnv.DataComp.Batiment.Gps gps;
            protected RSEnv.DataComp.Batiment.Verificateur verificateur;
            protected RSEnv.DataComp.Batiment.Certificateur certificateur;
            @XmlElement(name = "donnees_techniques", required = true)
            protected RSEnv.DataComp.Batiment.DonneesTechniques donneesTechniques;
            @XmlElement(name = "maquette_numerique")
            protected int maquetteNumerique;
            @XmlElement(name = "zone_br")
            protected Integer zoneBr;
            @XmlElement(name = "nb_place_parking_surface_plu", required = true)
            protected BigDecimal nbPlaceParkingSurfacePlu;
            @XmlElement(name = "nb_place_parking_ssol_plu", required = true)
            protected BigDecimal nbPlaceParkingSsolPlu;
            @XmlElement(name = "nb_place_parking_surface", required = true)
            protected BigDecimal nbPlaceParkingSurface;
            @XmlElement(name = "nb_place_parking_ssol", required = true)
            protected BigDecimal nbPlaceParkingSsol;

            /**
             * Gets the value of the index property.
             * 
             */
            public int getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             */
            public void setIndex(int value) {
                this.index = value;
            }

            /**
             * Gets the value of the usagePrincipal property.
             * 
             */
            public int getUsagePrincipal() {
                return usagePrincipal;
            }

            /**
             * Sets the value of the usagePrincipal property.
             * 
             */
            public void setUsagePrincipal(int value) {
                this.usagePrincipal = value;
            }

            /**
             * Gets the value of the precisionsUsage property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPrecisionsUsage() {
                return precisionsUsage;
            }

            /**
             * Sets the value of the precisionsUsage property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPrecisionsUsage(String value) {
                this.precisionsUsage = value;
            }

            /**
             * Gets the value of the cadreRt property.
             * 
             */
            public int getCadreRt() {
                return cadreRt;
            }

            /**
             * Sets the value of the cadreRt property.
             * 
             */
            public void setCadreRt(int value) {
                this.cadreRt = value;
            }

            /**
             * Gets the value of the commentaires property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCommentaires() {
                return commentaires;
            }

            /**
             * Sets the value of the commentaires property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCommentaires(String value) {
                this.commentaires = value;
            }

            /**
             * Gets the value of the nbUniteFonctionnalite property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNbUniteFonctionnalite() {
                return nbUniteFonctionnalite;
            }

            /**
             * Sets the value of the nbUniteFonctionnalite property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNbUniteFonctionnalite(BigDecimal value) {
                this.nbUniteFonctionnalite = value;
            }

            /**
             * Gets the value of the nbOccupant property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNbOccupant() {
                return nbOccupant;
            }

            /**
             * Sets the value of the nbOccupant property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNbOccupant(BigDecimal value) {
                this.nbOccupant = value;
            }

            /**
             * Gets the value of the sdp property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSdp() {
                return sdp;
            }

            /**
             * Sets the value of the sdp property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSdp(BigDecimal value) {
                this.sdp = value;
            }

            /**
             * Gets the value of the srt property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSrt() {
                return srt;
            }

            /**
             * Sets the value of the srt property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSrt(BigDecimal value) {
                this.srt = value;
            }

            /**
             * Gets the value of the shab property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getShab() {
                return shab;
            }

            /**
             * Sets the value of the shab property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setShab(BigDecimal value) {
                this.shab = value;
            }

            /**
             * Gets the value of the surt property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSurt() {
                return surt;
            }

            /**
             * Sets the value of the surt property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSurt(BigDecimal value) {
                this.surt = value;
            }

            /**
             * Gets the value of the empriseAuSol property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
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
            public void setEmpriseAuSol(BigDecimal value) {
                this.empriseAuSol = value;
            }

            /**
             * Gets the value of the nbNivSsol property.
             * 
             */
            public int getNbNivSsol() {
                return nbNivSsol;
            }

            /**
             * Sets the value of the nbNivSsol property.
             * 
             */
            public void setNbNivSsol(int value) {
                this.nbNivSsol = value;
            }

            /**
             * Gets the value of the nbNivSurface property.
             * 
             */
            public int getNbNivSurface() {
                return nbNivSurface;
            }

            /**
             * Sets the value of the nbNivSurface property.
             * 
             */
            public void setNbNivSurface(int value) {
                this.nbNivSurface = value;
            }

            /**
             * Gets the value of the dureeChantier property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
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
            public void setDureeChantier(BigDecimal value) {
                this.dureeChantier = value;
            }

            /**
             * Gets the value of the ccmi property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getCcmi() {
                return ccmi;
            }

            /**
             * Sets the value of the ccmi property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setCcmi(Integer value) {
                this.ccmi = value;
            }

            /**
             * Gets the value of the signeQualite property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.Batiment.SigneQualite }
             *     
             */
            public RSEnv.DataComp.Batiment.SigneQualite getSigneQualite() {
                return signeQualite;
            }

            /**
             * Sets the value of the signeQualite property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.Batiment.SigneQualite }
             *     
             */
            public void setSigneQualite(RSEnv.DataComp.Batiment.SigneQualite value) {
                this.signeQualite = value;
            }

            /**
             * Gets the value of the gps property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.Batiment.Gps }
             *     
             */
            public RSEnv.DataComp.Batiment.Gps getGps() {
                return gps;
            }

            /**
             * Sets the value of the gps property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.Batiment.Gps }
             *     
             */
            public void setGps(RSEnv.DataComp.Batiment.Gps value) {
                this.gps = value;
            }

            /**
             * Gets the value of the verificateur property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.Batiment.Verificateur }
             *     
             */
            public RSEnv.DataComp.Batiment.Verificateur getVerificateur() {
                return verificateur;
            }

            /**
             * Sets the value of the verificateur property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.Batiment.Verificateur }
             *     
             */
            public void setVerificateur(RSEnv.DataComp.Batiment.Verificateur value) {
                this.verificateur = value;
            }

            /**
             * Gets the value of the certificateur property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.Batiment.Certificateur }
             *     
             */
            public RSEnv.DataComp.Batiment.Certificateur getCertificateur() {
                return certificateur;
            }

            /**
             * Sets the value of the certificateur property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.Batiment.Certificateur }
             *     
             */
            public void setCertificateur(RSEnv.DataComp.Batiment.Certificateur value) {
                this.certificateur = value;
            }

            /**
             * Gets the value of the donneesTechniques property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.Batiment.DonneesTechniques }
             *     
             */
            public RSEnv.DataComp.Batiment.DonneesTechniques getDonneesTechniques() {
                return donneesTechniques;
            }

            /**
             * Sets the value of the donneesTechniques property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.Batiment.DonneesTechniques }
             *     
             */
            public void setDonneesTechniques(RSEnv.DataComp.Batiment.DonneesTechniques value) {
                this.donneesTechniques = value;
            }

            /**
             * Gets the value of the maquetteNumerique property.
             * 
             */
            public int getMaquetteNumerique() {
                return maquetteNumerique;
            }

            /**
             * Sets the value of the maquetteNumerique property.
             * 
             */
            public void setMaquetteNumerique(int value) {
                this.maquetteNumerique = value;
            }

            /**
             * Gets the value of the zoneBr property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getZoneBr() {
                return zoneBr;
            }

            /**
             * Sets the value of the zoneBr property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setZoneBr(Integer value) {
                this.zoneBr = value;
            }

            /**
             * Gets the value of the nbPlaceParkingSurfacePlu property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNbPlaceParkingSurfacePlu() {
                return nbPlaceParkingSurfacePlu;
            }

            /**
             * Sets the value of the nbPlaceParkingSurfacePlu property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNbPlaceParkingSurfacePlu(BigDecimal value) {
                this.nbPlaceParkingSurfacePlu = value;
            }

            /**
             * Gets the value of the nbPlaceParkingSsolPlu property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNbPlaceParkingSsolPlu() {
                return nbPlaceParkingSsolPlu;
            }

            /**
             * Sets the value of the nbPlaceParkingSsolPlu property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNbPlaceParkingSsolPlu(BigDecimal value) {
                this.nbPlaceParkingSsolPlu = value;
            }

            /**
             * Gets the value of the nbPlaceParkingSurface property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNbPlaceParkingSurface() {
                return nbPlaceParkingSurface;
            }

            /**
             * Sets the value of the nbPlaceParkingSurface property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNbPlaceParkingSurface(BigDecimal value) {
                this.nbPlaceParkingSurface = value;
            }

            /**
             * Gets the value of the nbPlaceParkingSsol property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNbPlaceParkingSsol() {
                return nbPlaceParkingSsol;
            }

            /**
             * Sets the value of the nbPlaceParkingSsol property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNbPlaceParkingSsol(BigDecimal value) {
                this.nbPlaceParkingSsol = value;
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
             *         &lt;element name="nom" type="{}p_string500"/>
             *         &lt;element name="adresse" type="{}t_adresse"/>
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
            public static class Certificateur {

                @XmlElement(required = true)
                protected String nom;
                @XmlElement(required = true)
                protected TAdresse adresse;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
                }

                /**
                 * Gets the value of the adresse property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TAdresse }
                 *     
                 */
                public TAdresse getAdresse() {
                    return adresse;
                }

                /**
                 * Sets the value of the adresse property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TAdresse }
                 *     
                 */
                public void setAdresse(TAdresse value) {
                    this.adresse = value;
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
             *         &lt;element name="commentaires_donnees_techniques" type="{}p_string500" minOccurs="0"/>
             *         &lt;element name="type_structure_principale">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="commentaires_structure" type="{}p_string500" minOccurs="0"/>
             *         &lt;element name="elements_prefabriques">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;pattern value="0"/>
             *               &lt;pattern value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="materiau_principal">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="materiau_remplissage_facade">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="materiau_remplissage_facade_autre" type="{}p_string500" minOccurs="0"/>
             *         &lt;element name="mur_mode_isolation">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="mur_nature_isolant">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="mur_revetement_ext">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_fondation">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_plancher">
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
             *         &lt;element name="plancher_mode_isolation">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="plancher_nature_isolant">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="plancher_nature_espace">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_toiture">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="toiture_mode_isolation">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="toiture_nature_isolant">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="toiture_vegetalisee">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="toiture_pente" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
             *               &lt;minInclusive value="0"/>
             *               &lt;maxInclusive value="90"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="toiture_couverture">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_menuiserie">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_pm">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="vecteur_energie_principal_ch">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="vecteur_energie_principal_ecs">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="vecteur_energie_principal_fr">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="generateur_principal_ch">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *               &lt;enumeration value="19"/>
             *               &lt;enumeration value="20"/>
             *               &lt;enumeration value="21"/>
             *               &lt;enumeration value="22"/>
             *               &lt;enumeration value="23"/>
             *               &lt;enumeration value="24"/>
             *               &lt;enumeration value="25"/>
             *               &lt;enumeration value="27"/>
             *               &lt;enumeration value="28"/>
             *               &lt;enumeration value="29"/>
             *               &lt;enumeration value="30"/>
             *               &lt;enumeration value="31"/>
             *               &lt;enumeration value="32"/>
             *               &lt;enumeration value="33"/>
             *               &lt;enumeration value="41"/>
             *               &lt;enumeration value="42"/>
             *               &lt;enumeration value="43"/>
             *               &lt;enumeration value="44"/>
             *               &lt;enumeration value="45"/>
             *               &lt;enumeration value="46"/>
             *               &lt;enumeration value="47"/>
             *               &lt;enumeration value="48"/>
             *               &lt;enumeration value="49"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="generateur_ch_liaison">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="emetteur_principal_ch">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="generateur_appoint_ch" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="generateur_principal_ecs" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *               &lt;enumeration value="19"/>
             *               &lt;enumeration value="20"/>
             *               &lt;enumeration value="21"/>
             *               &lt;enumeration value="22"/>
             *               &lt;enumeration value="23"/>
             *               &lt;enumeration value="24"/>
             *               &lt;enumeration value="25"/>
             *               &lt;enumeration value="26"/>
             *               &lt;enumeration value="27"/>
             *               &lt;enumeration value="28"/>
             *               &lt;enumeration value="29"/>
             *               &lt;enumeration value="30"/>
             *               &lt;enumeration value="31"/>
             *               &lt;enumeration value="32"/>
             *               &lt;enumeration value="33"/>
             *               &lt;enumeration value="34"/>
             *               &lt;enumeration value="35"/>
             *               &lt;enumeration value="36"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="generateur_principal_fr" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *               &lt;enumeration value="19"/>
             *               &lt;enumeration value="20"/>
             *               &lt;enumeration value="21"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_ventilation_principale">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="commentaires_prod_electricite" type="{}p_string500" minOccurs="0"/>
             *         &lt;element name="stockage_electricite" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="gestion_active">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="type_eclairage">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
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
            public static class DonneesTechniques {

                @XmlElement(name = "commentaires_donnees_techniques")
                protected String commentairesDonneesTechniques;
                @XmlElement(name = "type_structure_principale")
                protected int typeStructurePrincipale;
                @XmlElement(name = "commentaires_structure")
                protected String commentairesStructure;
                @XmlElement(name = "elements_prefabriques", defaultValue = "0")
                protected int elementsPrefabriques;
                @XmlElement(name = "materiau_principal")
                protected int materiauPrincipal;
                @XmlElement(name = "materiau_remplissage_facade")
                protected int materiauRemplissageFacade;
                @XmlElement(name = "materiau_remplissage_facade_autre")
                protected String materiauRemplissageFacadeAutre;
                @XmlElement(name = "mur_mode_isolation")
                protected int murModeIsolation;
                @XmlElement(name = "mur_nature_isolant")
                protected int murNatureIsolant;
                @XmlElement(name = "mur_revetement_ext")
                protected int murRevetementExt;
                @XmlElement(name = "type_fondation")
                protected int typeFondation;
                @XmlElement(name = "type_plancher")
                protected int typePlancher;
                @XmlElement(name = "plancher_mode_isolation")
                protected int plancherModeIsolation;
                @XmlElement(name = "plancher_nature_isolant")
                protected int plancherNatureIsolant;
                @XmlElement(name = "plancher_nature_espace")
                protected int plancherNatureEspace;
                @XmlElement(name = "type_toiture")
                protected int typeToiture;
                @XmlElement(name = "toiture_mode_isolation")
                protected int toitureModeIsolation;
                @XmlElement(name = "toiture_nature_isolant")
                protected int toitureNatureIsolant;
                @XmlElement(name = "toiture_vegetalisee", defaultValue = "0")
                protected int toitureVegetalisee;
                @XmlElement(name = "toiture_pente")
                protected Float toiturePente;
                @XmlElement(name = "toiture_couverture")
                protected int toitureCouverture;
                @XmlElement(name = "type_menuiserie")
                protected int typeMenuiserie;
                @XmlElement(name = "type_pm")
                protected int typePm;
                @XmlElement(name = "vecteur_energie_principal_ch")
                protected int vecteurEnergiePrincipalCh;
                @XmlElement(name = "vecteur_energie_principal_ecs")
                protected int vecteurEnergiePrincipalEcs;
                @XmlElement(name = "vecteur_energie_principal_fr")
                protected int vecteurEnergiePrincipalFr;
                @XmlElement(name = "generateur_principal_ch")
                protected int generateurPrincipalCh;
                @XmlElement(name = "generateur_ch_liaison")
                protected int generateurChLiaison;
                @XmlElement(name = "emetteur_principal_ch")
                protected int emetteurPrincipalCh;
                @XmlElement(name = "generateur_appoint_ch")
                protected Integer generateurAppointCh;
                @XmlElement(name = "generateur_principal_ecs")
                protected Integer generateurPrincipalEcs;
                @XmlElement(name = "generateur_principal_fr")
                protected Integer generateurPrincipalFr;
                @XmlElement(name = "type_ventilation_principale")
                protected int typeVentilationPrincipale;
                @XmlElement(name = "commentaires_prod_electricite")
                protected String commentairesProdElectricite;
                @XmlElement(name = "stockage_electricite")
                protected Integer stockageElectricite;
                @XmlElement(name = "gestion_active")
                protected int gestionActive;
                @XmlElement(name = "type_eclairage")
                protected int typeEclairage;

                /**
                 * Gets the value of the commentairesDonneesTechniques property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCommentairesDonneesTechniques() {
                    return commentairesDonneesTechniques;
                }

                /**
                 * Sets the value of the commentairesDonneesTechniques property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCommentairesDonneesTechniques(String value) {
                    this.commentairesDonneesTechniques = value;
                }

                /**
                 * Gets the value of the typeStructurePrincipale property.
                 * 
                 */
                public int getTypeStructurePrincipale() {
                    return typeStructurePrincipale;
                }

                /**
                 * Sets the value of the typeStructurePrincipale property.
                 * 
                 */
                public void setTypeStructurePrincipale(int value) {
                    this.typeStructurePrincipale = value;
                }

                /**
                 * Gets the value of the commentairesStructure property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCommentairesStructure() {
                    return commentairesStructure;
                }

                /**
                 * Sets the value of the commentairesStructure property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCommentairesStructure(String value) {
                    this.commentairesStructure = value;
                }

                /**
                 * Gets the value of the elementsPrefabriques property.
                 * 
                 */
                public int getElementsPrefabriques() {
                    return elementsPrefabriques;
                }

                /**
                 * Sets the value of the elementsPrefabriques property.
                 * 
                 */
                public void setElementsPrefabriques(int value) {
                    this.elementsPrefabriques = value;
                }

                /**
                 * Gets the value of the materiauPrincipal property.
                 * 
                 */
                public int getMateriauPrincipal() {
                    return materiauPrincipal;
                }

                /**
                 * Sets the value of the materiauPrincipal property.
                 * 
                 */
                public void setMateriauPrincipal(int value) {
                    this.materiauPrincipal = value;
                }

                /**
                 * Gets the value of the materiauRemplissageFacade property.
                 * 
                 */
                public int getMateriauRemplissageFacade() {
                    return materiauRemplissageFacade;
                }

                /**
                 * Sets the value of the materiauRemplissageFacade property.
                 * 
                 */
                public void setMateriauRemplissageFacade(int value) {
                    this.materiauRemplissageFacade = value;
                }

                /**
                 * Gets the value of the materiauRemplissageFacadeAutre property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMateriauRemplissageFacadeAutre() {
                    return materiauRemplissageFacadeAutre;
                }

                /**
                 * Sets the value of the materiauRemplissageFacadeAutre property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMateriauRemplissageFacadeAutre(String value) {
                    this.materiauRemplissageFacadeAutre = value;
                }

                /**
                 * Gets the value of the murModeIsolation property.
                 * 
                 */
                public int getMurModeIsolation() {
                    return murModeIsolation;
                }

                /**
                 * Sets the value of the murModeIsolation property.
                 * 
                 */
                public void setMurModeIsolation(int value) {
                    this.murModeIsolation = value;
                }

                /**
                 * Gets the value of the murNatureIsolant property.
                 * 
                 */
                public int getMurNatureIsolant() {
                    return murNatureIsolant;
                }

                /**
                 * Sets the value of the murNatureIsolant property.
                 * 
                 */
                public void setMurNatureIsolant(int value) {
                    this.murNatureIsolant = value;
                }

                /**
                 * Gets the value of the murRevetementExt property.
                 * 
                 */
                public int getMurRevetementExt() {
                    return murRevetementExt;
                }

                /**
                 * Sets the value of the murRevetementExt property.
                 * 
                 */
                public void setMurRevetementExt(int value) {
                    this.murRevetementExt = value;
                }

                /**
                 * Gets the value of the typeFondation property.
                 * 
                 */
                public int getTypeFondation() {
                    return typeFondation;
                }

                /**
                 * Sets the value of the typeFondation property.
                 * 
                 */
                public void setTypeFondation(int value) {
                    this.typeFondation = value;
                }

                /**
                 * Gets the value of the typePlancher property.
                 * 
                 */
                public int getTypePlancher() {
                    return typePlancher;
                }

                /**
                 * Sets the value of the typePlancher property.
                 * 
                 */
                public void setTypePlancher(int value) {
                    this.typePlancher = value;
                }

                /**
                 * Gets the value of the plancherModeIsolation property.
                 * 
                 */
                public int getPlancherModeIsolation() {
                    return plancherModeIsolation;
                }

                /**
                 * Sets the value of the plancherModeIsolation property.
                 * 
                 */
                public void setPlancherModeIsolation(int value) {
                    this.plancherModeIsolation = value;
                }

                /**
                 * Gets the value of the plancherNatureIsolant property.
                 * 
                 */
                public int getPlancherNatureIsolant() {
                    return plancherNatureIsolant;
                }

                /**
                 * Sets the value of the plancherNatureIsolant property.
                 * 
                 */
                public void setPlancherNatureIsolant(int value) {
                    this.plancherNatureIsolant = value;
                }

                /**
                 * Gets the value of the plancherNatureEspace property.
                 * 
                 */
                public int getPlancherNatureEspace() {
                    return plancherNatureEspace;
                }

                /**
                 * Sets the value of the plancherNatureEspace property.
                 * 
                 */
                public void setPlancherNatureEspace(int value) {
                    this.plancherNatureEspace = value;
                }

                /**
                 * Gets the value of the typeToiture property.
                 * 
                 */
                public int getTypeToiture() {
                    return typeToiture;
                }

                /**
                 * Sets the value of the typeToiture property.
                 * 
                 */
                public void setTypeToiture(int value) {
                    this.typeToiture = value;
                }

                /**
                 * Gets the value of the toitureModeIsolation property.
                 * 
                 */
                public int getToitureModeIsolation() {
                    return toitureModeIsolation;
                }

                /**
                 * Sets the value of the toitureModeIsolation property.
                 * 
                 */
                public void setToitureModeIsolation(int value) {
                    this.toitureModeIsolation = value;
                }

                /**
                 * Gets the value of the toitureNatureIsolant property.
                 * 
                 */
                public int getToitureNatureIsolant() {
                    return toitureNatureIsolant;
                }

                /**
                 * Sets the value of the toitureNatureIsolant property.
                 * 
                 */
                public void setToitureNatureIsolant(int value) {
                    this.toitureNatureIsolant = value;
                }

                /**
                 * Gets the value of the toitureVegetalisee property.
                 * 
                 */
                public int getToitureVegetalisee() {
                    return toitureVegetalisee;
                }

                /**
                 * Sets the value of the toitureVegetalisee property.
                 * 
                 */
                public void setToitureVegetalisee(int value) {
                    this.toitureVegetalisee = value;
                }

                /**
                 * Gets the value of the toiturePente property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Float }
                 *     
                 */
                public Float getToiturePente() {
                    return toiturePente;
                }

                /**
                 * Sets the value of the toiturePente property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Float }
                 *     
                 */
                public void setToiturePente(Float value) {
                    this.toiturePente = value;
                }

                /**
                 * Gets the value of the toitureCouverture property.
                 * 
                 */
                public int getToitureCouverture() {
                    return toitureCouverture;
                }

                /**
                 * Sets the value of the toitureCouverture property.
                 * 
                 */
                public void setToitureCouverture(int value) {
                    this.toitureCouverture = value;
                }

                /**
                 * Gets the value of the typeMenuiserie property.
                 * 
                 */
                public int getTypeMenuiserie() {
                    return typeMenuiserie;
                }

                /**
                 * Sets the value of the typeMenuiserie property.
                 * 
                 */
                public void setTypeMenuiserie(int value) {
                    this.typeMenuiserie = value;
                }

                /**
                 * Gets the value of the typePm property.
                 * 
                 */
                public int getTypePm() {
                    return typePm;
                }

                /**
                 * Sets the value of the typePm property.
                 * 
                 */
                public void setTypePm(int value) {
                    this.typePm = value;
                }

                /**
                 * Gets the value of the vecteurEnergiePrincipalCh property.
                 * 
                 */
                public int getVecteurEnergiePrincipalCh() {
                    return vecteurEnergiePrincipalCh;
                }

                /**
                 * Sets the value of the vecteurEnergiePrincipalCh property.
                 * 
                 */
                public void setVecteurEnergiePrincipalCh(int value) {
                    this.vecteurEnergiePrincipalCh = value;
                }

                /**
                 * Gets the value of the vecteurEnergiePrincipalEcs property.
                 * 
                 */
                public int getVecteurEnergiePrincipalEcs() {
                    return vecteurEnergiePrincipalEcs;
                }

                /**
                 * Sets the value of the vecteurEnergiePrincipalEcs property.
                 * 
                 */
                public void setVecteurEnergiePrincipalEcs(int value) {
                    this.vecteurEnergiePrincipalEcs = value;
                }

                /**
                 * Gets the value of the vecteurEnergiePrincipalFr property.
                 * 
                 */
                public int getVecteurEnergiePrincipalFr() {
                    return vecteurEnergiePrincipalFr;
                }

                /**
                 * Sets the value of the vecteurEnergiePrincipalFr property.
                 * 
                 */
                public void setVecteurEnergiePrincipalFr(int value) {
                    this.vecteurEnergiePrincipalFr = value;
                }

                /**
                 * Gets the value of the generateurPrincipalCh property.
                 * 
                 */
                public int getGenerateurPrincipalCh() {
                    return generateurPrincipalCh;
                }

                /**
                 * Sets the value of the generateurPrincipalCh property.
                 * 
                 */
                public void setGenerateurPrincipalCh(int value) {
                    this.generateurPrincipalCh = value;
                }

                /**
                 * Gets the value of the generateurChLiaison property.
                 * 
                 */
                public int getGenerateurChLiaison() {
                    return generateurChLiaison;
                }

                /**
                 * Sets the value of the generateurChLiaison property.
                 * 
                 */
                public void setGenerateurChLiaison(int value) {
                    this.generateurChLiaison = value;
                }

                /**
                 * Gets the value of the emetteurPrincipalCh property.
                 * 
                 */
                public int getEmetteurPrincipalCh() {
                    return emetteurPrincipalCh;
                }

                /**
                 * Sets the value of the emetteurPrincipalCh property.
                 * 
                 */
                public void setEmetteurPrincipalCh(int value) {
                    this.emetteurPrincipalCh = value;
                }

                /**
                 * Gets the value of the generateurAppointCh property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getGenerateurAppointCh() {
                    return generateurAppointCh;
                }

                /**
                 * Sets the value of the generateurAppointCh property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setGenerateurAppointCh(Integer value) {
                    this.generateurAppointCh = value;
                }

                /**
                 * Gets the value of the generateurPrincipalEcs property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getGenerateurPrincipalEcs() {
                    return generateurPrincipalEcs;
                }

                /**
                 * Sets the value of the generateurPrincipalEcs property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setGenerateurPrincipalEcs(Integer value) {
                    this.generateurPrincipalEcs = value;
                }

                /**
                 * Gets the value of the generateurPrincipalFr property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getGenerateurPrincipalFr() {
                    return generateurPrincipalFr;
                }

                /**
                 * Sets the value of the generateurPrincipalFr property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setGenerateurPrincipalFr(Integer value) {
                    this.generateurPrincipalFr = value;
                }

                /**
                 * Gets the value of the typeVentilationPrincipale property.
                 * 
                 */
                public int getTypeVentilationPrincipale() {
                    return typeVentilationPrincipale;
                }

                /**
                 * Sets the value of the typeVentilationPrincipale property.
                 * 
                 */
                public void setTypeVentilationPrincipale(int value) {
                    this.typeVentilationPrincipale = value;
                }

                /**
                 * Gets the value of the commentairesProdElectricite property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCommentairesProdElectricite() {
                    return commentairesProdElectricite;
                }

                /**
                 * Sets the value of the commentairesProdElectricite property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCommentairesProdElectricite(String value) {
                    this.commentairesProdElectricite = value;
                }

                /**
                 * Gets the value of the stockageElectricite property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getStockageElectricite() {
                    return stockageElectricite;
                }

                /**
                 * Sets the value of the stockageElectricite property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setStockageElectricite(Integer value) {
                    this.stockageElectricite = value;
                }

                /**
                 * Gets the value of the gestionActive property.
                 * 
                 */
                public int getGestionActive() {
                    return gestionActive;
                }

                /**
                 * Sets the value of the gestionActive property.
                 * 
                 */
                public void setGestionActive(int value) {
                    this.gestionActive = value;
                }

                /**
                 * Gets the value of the typeEclairage property.
                 * 
                 */
                public int getTypeEclairage() {
                    return typeEclairage;
                }

                /**
                 * Sets the value of the typeEclairage property.
                 * 
                 */
                public void setTypeEclairage(int value) {
                    this.typeEclairage = value;
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
             *         &lt;element name="longitude">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;whiteSpace value="collapse"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="latitude">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;minLength value="1"/>
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
            public static class Gps {

                @XmlElement(required = true)
                protected String longitude;
                @XmlElement(required = true)
                protected String latitude;

                /**
                 * Gets the value of the longitude property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLongitude() {
                    return longitude;
                }

                /**
                 * Sets the value of the longitude property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLongitude(String value) {
                    this.longitude = value;
                }

                /**
                 * Gets the value of the latitude property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLatitude() {
                    return latitude;
                }

                /**
                 * Sets the value of the latitude property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLatitude(String value) {
                    this.latitude = value;
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
             *         &lt;element name="label" maxOccurs="unbounded" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="label_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
             *         &lt;element name="certification" maxOccurs="unbounded" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;pattern value="[^-|_|0|\s]{1}|[\w\W]{2,}"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="certification_autre" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
             *         &lt;element name="demarche_environnementale" type="{}p_string500" maxOccurs="unbounded" minOccurs="0"/>
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
                "label",
                "labelAutre",
                "certification",
                "certificationAutre",
                "demarcheEnvironnementale"
            })
            public static class SigneQualite {

                @XmlElement(type = Integer.class)
                protected List<Integer> label;
                @XmlElement(name = "label_autre")
                protected List<String> labelAutre;
                @XmlElement(type = Integer.class)
                protected List<Integer> certification;
                @XmlElement(name = "certification_autre")
                protected List<String> certificationAutre;
                @XmlElement(name = "demarche_environnementale")
                protected List<String> demarcheEnvironnementale;

                /**
                 * Gets the value of the label property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the label property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getLabel().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Integer }
                 * 
                 * 
                 */
                public List<Integer> getLabel() {
                    if (label == null) {
                        label = new ArrayList<Integer>();
                    }
                    return this.label;
                }

                /**
                 * Gets the value of the labelAutre property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the labelAutre property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getLabelAutre().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getLabelAutre() {
                    if (labelAutre == null) {
                        labelAutre = new ArrayList<String>();
                    }
                    return this.labelAutre;
                }

                /**
                 * Gets the value of the certification property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the certification property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCertification().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Integer }
                 * 
                 * 
                 */
                public List<Integer> getCertification() {
                    if (certification == null) {
                        certification = new ArrayList<Integer>();
                    }
                    return this.certification;
                }

                /**
                 * Gets the value of the certificationAutre property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the certificationAutre property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCertificationAutre().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getCertificationAutre() {
                    if (certificationAutre == null) {
                        certificationAutre = new ArrayList<String>();
                    }
                    return this.certificationAutre;
                }

                /**
                 * Gets the value of the demarcheEnvironnementale property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the demarcheEnvironnementale property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDemarcheEnvironnementale().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getDemarcheEnvironnementale() {
                    if (demarcheEnvironnementale == null) {
                        demarcheEnvironnementale = new ArrayList<String>();
                    }
                    return this.demarcheEnvironnementale;
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
             *         &lt;element name="nom" type="{}p_string500"/>
             *         &lt;element name="adresse" type="{}t_adresse"/>
             *         &lt;element name="date_verification" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
            public static class Verificateur {

                @XmlElement(required = true)
                protected String nom;
                @XmlElement(required = true)
                protected TAdresse adresse;
                @XmlElement(name = "date_verification", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar dateVerification;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
                }

                /**
                 * Gets the value of the adresse property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TAdresse }
                 *     
                 */
                public TAdresse getAdresse() {
                    return adresse;
                }

                /**
                 * Sets the value of the adresse property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TAdresse }
                 *     
                 */
                public void setAdresse(TAdresse value) {
                    this.adresse = value;
                }

                /**
                 * Gets the value of the dateVerification property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getDateVerification() {
                    return dateVerification;
                }

                /**
                 * Sets the value of the dateVerification property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setDateVerification(XMLGregorianCalendar value) {
                    this.dateVerification = value;
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
         *         &lt;element name="maitre_ouvrage">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                   &lt;element name="type">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="adresse" type="{}t_adresse"/>
         *                   &lt;element name="SIRET" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;length value="14"/>
         *                         &lt;whiteSpace value="collapse"/>
         *                         &lt;pattern value="[0-9]{14}"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="bureau_etude_acv" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                   &lt;element name="adresse" type="{}t_adresse"/>
         *                   &lt;element name="SIRET">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;length value="14"/>
         *                         &lt;whiteSpace value="collapse"/>
         *                         &lt;pattern value="[0-9]{14}"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="maitre_oeuvre" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="entreprise" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="logiciel">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="editeur" type="{}p_string500"/>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                   &lt;element name="version">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;whiteSpace value="collapse"/>
         *                         &lt;minLength value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/all>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="operation">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="nom" type="{}p_string500"/>
         *                   &lt;element name="description" type="{}p_string2000" minOccurs="0"/>
         *                   &lt;element name="adresse" type="{}t_adresse"/>
         *                   &lt;element name="date_depot_PC">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="date_obtention_PC" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
         *                   &lt;element name="date_livraison" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
         *                   &lt;element name="num_permis" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;whiteSpace value="collapse"/>
         *                         &lt;pattern value="PC[A-B0-9]{3}[0-9]{3}[0-2][0-9][A-Z0-9]{5}"/>
         *                         &lt;pattern value="en cours"/>
         *                         &lt;pattern value="EN COURS"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="surface_parcelle">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="nb_batiment">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;minInclusive value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="surface_arrosee">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="surface_veg" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="surface_imper" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="geotech" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="9"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                         &lt;enumeration value="19"/>
         *                         &lt;enumeration value="20"/>
         *                         &lt;enumeration value="21"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="sol_pollution" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="zone_climatique">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;whiteSpace value="collapse"/>
         *                         &lt;minLength value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="altitude">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="zone_sismique">
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
         *                   &lt;element name="commentaire_acv" type="{}p_string2000" minOccurs="0"/>
         *                   &lt;element name="cadastre" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="reference_cadastrale" maxOccurs="unbounded">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                   &lt;whiteSpace value="collapse"/>
         *                                   &lt;pattern value="[0-9]{3}[a-zA-Z0-9]{2}[0-9]{4}"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
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
         *         &lt;element name="reseau" maxOccurs="2" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
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
         *                   &lt;element name="taux_enr">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                         &lt;maxInclusive value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="part_cogeneration">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                         &lt;maxInclusive value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="contenu_co2">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
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
            "maitreOuvrage",
            "bureauEtudeAcv",
            "maitreOeuvre",
            "entreprise",
            "logiciel",
            "operation",
            "reseau"
        })
        public static class DonneesGenerales {

            @XmlElement(name = "maitre_ouvrage", required = true)
            protected RSEnv.DataComp.DonneesGenerales.MaitreOuvrage maitreOuvrage;
            @XmlElement(name = "bureau_etude_acv")
            protected RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv bureauEtudeAcv;
            @XmlElement(name = "maitre_oeuvre")
            protected RSEnv.DataComp.DonneesGenerales.MaitreOeuvre maitreOeuvre;
            protected List<RSEnv.DataComp.DonneesGenerales.Entreprise> entreprise;
            @XmlElement(required = true)
            protected RSEnv.DataComp.DonneesGenerales.Logiciel logiciel;
            @XmlElement(required = true)
            protected RSEnv.DataComp.DonneesGenerales.Operation operation;
            protected List<RSEnv.DataComp.DonneesGenerales.Reseau> reseau;

            /**
             * Gets the value of the maitreOuvrage property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.DonneesGenerales.MaitreOuvrage }
             *     
             */
            public RSEnv.DataComp.DonneesGenerales.MaitreOuvrage getMaitreOuvrage() {
                return maitreOuvrage;
            }

            /**
             * Sets the value of the maitreOuvrage property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.DonneesGenerales.MaitreOuvrage }
             *     
             */
            public void setMaitreOuvrage(RSEnv.DataComp.DonneesGenerales.MaitreOuvrage value) {
                this.maitreOuvrage = value;
            }

            /**
             * Gets the value of the bureauEtudeAcv property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv }
             *     
             */
            public RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv getBureauEtudeAcv() {
                return bureauEtudeAcv;
            }

            /**
             * Sets the value of the bureauEtudeAcv property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv }
             *     
             */
            public void setBureauEtudeAcv(RSEnv.DataComp.DonneesGenerales.BureauEtudeAcv value) {
                this.bureauEtudeAcv = value;
            }

            /**
             * Gets the value of the maitreOeuvre property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.DonneesGenerales.MaitreOeuvre }
             *     
             */
            public RSEnv.DataComp.DonneesGenerales.MaitreOeuvre getMaitreOeuvre() {
                return maitreOeuvre;
            }

            /**
             * Sets the value of the maitreOeuvre property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.DonneesGenerales.MaitreOeuvre }
             *     
             */
            public void setMaitreOeuvre(RSEnv.DataComp.DonneesGenerales.MaitreOeuvre value) {
                this.maitreOeuvre = value;
            }

            /**
             * Gets the value of the entreprise property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the entreprise property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getEntreprise().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RSEnv.DataComp.DonneesGenerales.Entreprise }
             * 
             * 
             */
            public List<RSEnv.DataComp.DonneesGenerales.Entreprise> getEntreprise() {
                if (entreprise == null) {
                    entreprise = new ArrayList<RSEnv.DataComp.DonneesGenerales.Entreprise>();
                }
                return this.entreprise;
            }

            /**
             * Gets the value of the logiciel property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.DonneesGenerales.Logiciel }
             *     
             */
            public RSEnv.DataComp.DonneesGenerales.Logiciel getLogiciel() {
                return logiciel;
            }

            /**
             * Sets the value of the logiciel property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.DonneesGenerales.Logiciel }
             *     
             */
            public void setLogiciel(RSEnv.DataComp.DonneesGenerales.Logiciel value) {
                this.logiciel = value;
            }

            /**
             * Gets the value of the operation property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.DataComp.DonneesGenerales.Operation }
             *     
             */
            public RSEnv.DataComp.DonneesGenerales.Operation getOperation() {
                return operation;
            }

            /**
             * Sets the value of the operation property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.DataComp.DonneesGenerales.Operation }
             *     
             */
            public void setOperation(RSEnv.DataComp.DonneesGenerales.Operation value) {
                this.operation = value;
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
             * {@link RSEnv.DataComp.DonneesGenerales.Reseau }
             * 
             * 
             */
            public List<RSEnv.DataComp.DonneesGenerales.Reseau> getReseau() {
                if (reseau == null) {
                    reseau = new ArrayList<RSEnv.DataComp.DonneesGenerales.Reseau>();
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
             *       &lt;all>
             *         &lt;element name="nom" type="{}p_string500"/>
             *         &lt;element name="adresse" type="{}t_adresse"/>
             *         &lt;element name="SIRET">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;length value="14"/>
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;pattern value="[0-9]{14}"/>
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
            public static class BureauEtudeAcv {

                @XmlElement(required = true)
                protected String nom;
                @XmlElement(required = true)
                protected TAdresse adresse;
                @XmlElement(name = "SIRET", required = true)
                protected String siret;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
                }

                /**
                 * Gets the value of the adresse property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TAdresse }
                 *     
                 */
                public TAdresse getAdresse() {
                    return adresse;
                }

                /**
                 * Sets the value of the adresse property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TAdresse }
                 *     
                 */
                public void setAdresse(TAdresse value) {
                    this.adresse = value;
                }

                /**
                 * Gets the value of the siret property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSIRET() {
                    return siret;
                }

                /**
                 * Sets the value of the siret property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSIRET(String value) {
                    this.siret = value;
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
             *         &lt;element name="nom" type="{}p_string500"/>
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
            public static class Entreprise {

                @XmlElement(required = true)
                protected String nom;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
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
             *         &lt;element name="editeur" type="{}p_string500"/>
             *         &lt;element name="nom" type="{}p_string500"/>
             *         &lt;element name="version">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;minLength value="1"/>
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
            public static class Logiciel {

                @XmlElement(required = true)
                protected String editeur;
                @XmlElement(required = true)
                protected String nom;
                @XmlElement(required = true)
                protected String version;

                /**
                 * Gets the value of the editeur property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEditeur() {
                    return editeur;
                }

                /**
                 * Sets the value of the editeur property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEditeur(String value) {
                    this.editeur = value;
                }

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
                }

                /**
                 * Gets the value of the version property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setVersion(String value) {
                    this.version = value;
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
             *         &lt;element name="nom" type="{}p_string500"/>
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
            public static class MaitreOeuvre {

                @XmlElement(required = true)
                protected String nom;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
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
             *         &lt;element name="nom" type="{}p_string500"/>
             *         &lt;element name="type">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="adresse" type="{}t_adresse"/>
             *         &lt;element name="SIRET" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;length value="14"/>
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;pattern value="[0-9]{14}"/>
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
            public static class MaitreOuvrage {

                @XmlElement(required = true)
                protected String nom;
                protected int type;
                @XmlElement(required = true)
                protected TAdresse adresse;
                @XmlElement(name = "SIRET")
                protected String siret;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
                }

                /**
                 * Gets the value of the type property.
                 * 
                 */
                public int getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 * 
                 */
                public void setType(int value) {
                    this.type = value;
                }

                /**
                 * Gets the value of the adresse property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TAdresse }
                 *     
                 */
                public TAdresse getAdresse() {
                    return adresse;
                }

                /**
                 * Sets the value of the adresse property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TAdresse }
                 *     
                 */
                public void setAdresse(TAdresse value) {
                    this.adresse = value;
                }

                /**
                 * Gets the value of the siret property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSIRET() {
                    return siret;
                }

                /**
                 * Sets the value of the siret property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSIRET(String value) {
                    this.siret = value;
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
             *         &lt;element name="nom" type="{}p_string500"/>
             *         &lt;element name="description" type="{}p_string2000" minOccurs="0"/>
             *         &lt;element name="adresse" type="{}t_adresse"/>
             *         &lt;element name="date_depot_PC">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="date_obtention_PC" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
             *         &lt;element name="date_livraison" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
             *         &lt;element name="num_permis" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;pattern value="PC[A-B0-9]{3}[0-9]{3}[0-2][0-9][A-Z0-9]{5}"/>
             *               &lt;pattern value="en cours"/>
             *               &lt;pattern value="EN COURS"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="surface_parcelle">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="nb_batiment">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;minInclusive value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="surface_arrosee">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="surface_veg" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="surface_imper" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="geotech" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="9"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *               &lt;enumeration value="19"/>
             *               &lt;enumeration value="20"/>
             *               &lt;enumeration value="21"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="sol_pollution" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="zone_climatique">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;minLength value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="altitude">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="zone_sismique">
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
             *         &lt;element name="commentaire_acv" type="{}p_string2000" minOccurs="0"/>
             *         &lt;element name="cadastre" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="reference_cadastrale" maxOccurs="unbounded">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                         &lt;whiteSpace value="collapse"/>
             *                         &lt;pattern value="[0-9]{3}[a-zA-Z0-9]{2}[0-9]{4}"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
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
            public static class Operation {

                @XmlElement(required = true)
                protected String nom;
                @XmlElementRef(name = "description", type = JAXBElement.class, required = false)
                protected JAXBElement<String> description;
                @XmlElement(required = true)
                protected TAdresse adresse;
                @XmlElement(name = "date_depot_PC", required = true)
                protected XMLGregorianCalendar dateDepotPC;
                @XmlElement(name = "date_obtention_PC")
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar dateObtentionPC;
                @XmlElement(name = "date_livraison")
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar dateLivraison;
                @XmlElement(name = "num_permis")
                protected String numPermis;
                @XmlElement(name = "surface_parcelle", required = true)
                protected BigDecimal surfaceParcelle;
                @XmlElement(name = "nb_batiment")
                protected int nbBatiment;
                @XmlElement(name = "surface_arrosee", required = true)
                protected BigDecimal surfaceArrosee;
                @XmlElement(name = "surface_veg")
                protected BigDecimal surfaceVeg;
                @XmlElement(name = "surface_imper")
                protected BigDecimal surfaceImper;
                protected Integer geotech;
                @XmlElement(name = "sol_pollution")
                protected Integer solPollution;
                @XmlElement(name = "zone_climatique", required = true)
                protected String zoneClimatique;
                protected int altitude;
                @XmlElement(name = "zone_sismique")
                protected int zoneSismique;
                @XmlElement(name = "commentaire_acv")
                protected String commentaireAcv;
                protected RSEnv.DataComp.DonneesGenerales.Operation.Cadastre cadastre;

                /**
                 * Gets the value of the nom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
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
                public void setNom(String value) {
                    this.nom = value;
                }

                /**
                 * Gets the value of the description property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public JAXBElement<String> getDescription() {
                    return description;
                }

                /**
                 * Sets the value of the description property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link JAXBElement }{@code <}{@link String }{@code >}
                 *     
                 */
                public void setDescription(JAXBElement<String> value) {
                    this.description = value;
                }

                /**
                 * Gets the value of the adresse property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TAdresse }
                 *     
                 */
                public TAdresse getAdresse() {
                    return adresse;
                }

                /**
                 * Sets the value of the adresse property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TAdresse }
                 *     
                 */
                public void setAdresse(TAdresse value) {
                    this.adresse = value;
                }

                /**
                 * Gets the value of the dateDepotPC property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getDateDepotPC() {
                    return dateDepotPC;
                }

                /**
                 * Sets the value of the dateDepotPC property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setDateDepotPC(XMLGregorianCalendar value) {
                    this.dateDepotPC = value;
                }

                /**
                 * Gets the value of the dateObtentionPC property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getDateObtentionPC() {
                    return dateObtentionPC;
                }

                /**
                 * Sets the value of the dateObtentionPC property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setDateObtentionPC(XMLGregorianCalendar value) {
                    this.dateObtentionPC = value;
                }

                /**
                 * Gets the value of the dateLivraison property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getDateLivraison() {
                    return dateLivraison;
                }

                /**
                 * Sets the value of the dateLivraison property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setDateLivraison(XMLGregorianCalendar value) {
                    this.dateLivraison = value;
                }

                /**
                 * Gets the value of the numPermis property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNumPermis() {
                    return numPermis;
                }

                /**
                 * Sets the value of the numPermis property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNumPermis(String value) {
                    this.numPermis = value;
                }

                /**
                 * Gets the value of the surfaceParcelle property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getSurfaceParcelle() {
                    return surfaceParcelle;
                }

                /**
                 * Sets the value of the surfaceParcelle property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setSurfaceParcelle(BigDecimal value) {
                    this.surfaceParcelle = value;
                }

                /**
                 * Gets the value of the nbBatiment property.
                 * 
                 */
                public int getNbBatiment() {
                    return nbBatiment;
                }

                /**
                 * Sets the value of the nbBatiment property.
                 * 
                 */
                public void setNbBatiment(int value) {
                    this.nbBatiment = value;
                }

                /**
                 * Gets the value of the surfaceArrosee property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getSurfaceArrosee() {
                    return surfaceArrosee;
                }

                /**
                 * Sets the value of the surfaceArrosee property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setSurfaceArrosee(BigDecimal value) {
                    this.surfaceArrosee = value;
                }

                /**
                 * Gets the value of the surfaceVeg property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getSurfaceVeg() {
                    return surfaceVeg;
                }

                /**
                 * Sets the value of the surfaceVeg property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setSurfaceVeg(BigDecimal value) {
                    this.surfaceVeg = value;
                }

                /**
                 * Gets the value of the surfaceImper property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getSurfaceImper() {
                    return surfaceImper;
                }

                /**
                 * Sets the value of the surfaceImper property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setSurfaceImper(BigDecimal value) {
                    this.surfaceImper = value;
                }

                /**
                 * Gets the value of the geotech property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getGeotech() {
                    return geotech;
                }

                /**
                 * Sets the value of the geotech property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setGeotech(Integer value) {
                    this.geotech = value;
                }

                /**
                 * Gets the value of the solPollution property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getSolPollution() {
                    return solPollution;
                }

                /**
                 * Sets the value of the solPollution property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setSolPollution(Integer value) {
                    this.solPollution = value;
                }

                /**
                 * Gets the value of the zoneClimatique property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getZoneClimatique() {
                    return zoneClimatique;
                }

                /**
                 * Sets the value of the zoneClimatique property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setZoneClimatique(String value) {
                    this.zoneClimatique = value;
                }

                /**
                 * Gets the value of the altitude property.
                 * 
                 */
                public int getAltitude() {
                    return altitude;
                }

                /**
                 * Sets the value of the altitude property.
                 * 
                 */
                public void setAltitude(int value) {
                    this.altitude = value;
                }

                /**
                 * Gets the value of the zoneSismique property.
                 * 
                 */
                public int getZoneSismique() {
                    return zoneSismique;
                }

                /**
                 * Sets the value of the zoneSismique property.
                 * 
                 */
                public void setZoneSismique(int value) {
                    this.zoneSismique = value;
                }

                /**
                 * Gets the value of the commentaireAcv property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCommentaireAcv() {
                    return commentaireAcv;
                }

                /**
                 * Sets the value of the commentaireAcv property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCommentaireAcv(String value) {
                    this.commentaireAcv = value;
                }

                /**
                 * Gets the value of the cadastre property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.DataComp.DonneesGenerales.Operation.Cadastre }
                 *     
                 */
                public RSEnv.DataComp.DonneesGenerales.Operation.Cadastre getCadastre() {
                    return cadastre;
                }

                /**
                 * Sets the value of the cadastre property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.DataComp.DonneesGenerales.Operation.Cadastre }
                 *     
                 */
                public void setCadastre(RSEnv.DataComp.DonneesGenerales.Operation.Cadastre value) {
                    this.cadastre = value;
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
                 *         &lt;element name="reference_cadastrale" maxOccurs="unbounded">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *               &lt;whiteSpace value="collapse"/>
                 *               &lt;pattern value="[0-9]{3}[a-zA-Z0-9]{2}[0-9]{4}"/>
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
                    "referenceCadastrale"
                })
                public static class Cadastre {

                    @XmlElement(name = "reference_cadastrale", required = true)
                    protected List<String> referenceCadastrale;

                    /**
                     * Gets the value of the referenceCadastrale property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the referenceCadastrale property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getReferenceCadastrale().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link String }
                     * 
                     * 
                     */
                    public List<String> getReferenceCadastrale() {
                        if (referenceCadastrale == null) {
                            referenceCadastrale = new ArrayList<String>();
                        }
                        return this.referenceCadastrale;
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
             *         &lt;element name="taux_enr">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *               &lt;maxInclusive value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="part_cogeneration">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *               &lt;maxInclusive value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="contenu_co2">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float">
             *               &lt;minInclusive value="0"/>
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
                "type",
                "nom",
                "localisation",
                "tauxEnr",
                "partCogeneration",
                "contenuCo2"
            })
            public static class Reseau {

                protected int type;
                @XmlElement(required = true)
                protected String nom;
                @XmlElement(required = true)
                protected String localisation;
                @XmlElement(name = "taux_enr", required = true)
                protected BigDecimal tauxEnr;
                @XmlElement(name = "part_cogeneration", required = true)
                protected BigDecimal partCogeneration;
                @XmlElement(name = "contenu_co2")
                protected float contenuCo2;

                /**
                 * Gets the value of the type property.
                 * 
                 */
                public int getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 * 
                 */
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
                public void setLocalisation(String value) {
                    this.localisation = value;
                }

                /**
                 * Gets the value of the tauxEnr property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getTauxEnr() {
                    return tauxEnr;
                }

                /**
                 * Sets the value of the tauxEnr property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setTauxEnr(BigDecimal value) {
                    this.tauxEnr = value;
                }

                /**
                 * Gets the value of the partCogeneration property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getPartCogeneration() {
                    return partCogeneration;
                }

                /**
                 * Sets the value of the partCogeneration property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setPartCogeneration(BigDecimal value) {
                    this.partCogeneration = value;
                }

                /**
                 * Gets the value of the contenuCo2 property.
                 * 
                 */
                public float getContenuCo2() {
                    return contenuCo2;
                }

                /**
                 * Sets the value of the contenuCo2 property.
                 * 
                 */
                public void setContenuCo2(float value) {
                    this.contenuCo2 = value;
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
     *         &lt;element name="batiment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="nom" type="{}p_string500"/>
     *                   &lt;element name="zone" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="usage">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;enumeration value="0"/>
     *                                   &lt;enumeration value="1"/>
     *                                   &lt;enumeration value="2"/>
     *                                   &lt;enumeration value="3"/>
     *                                   &lt;enumeration value="4"/>
     *                                   &lt;enumeration value="5"/>
     *                                   &lt;enumeration value="6"/>
     *                                   &lt;enumeration value="7"/>
     *                                   &lt;enumeration value="8"/>
     *                                   &lt;enumeration value="10"/>
     *                                   &lt;enumeration value="11"/>
     *                                   &lt;enumeration value="12"/>
     *                                   &lt;enumeration value="13"/>
     *                                   &lt;enumeration value="14"/>
     *                                   &lt;enumeration value="15"/>
     *                                   &lt;enumeration value="16"/>
     *                                   &lt;enumeration value="17"/>
     *                                   &lt;enumeration value="18"/>
     *                                   &lt;enumeration value="19"/>
     *                                   &lt;enumeration value="20"/>
     *                                   &lt;enumeration value="22"/>
     *                                   &lt;enumeration value="24"/>
     *                                   &lt;enumeration value="26"/>
     *                                   &lt;enumeration value="27"/>
     *                                   &lt;enumeration value="28"/>
     *                                   &lt;enumeration value="29"/>
     *                                   &lt;enumeration value="30"/>
     *                                   &lt;enumeration value="32"/>
     *                                   &lt;enumeration value="33"/>
     *                                   &lt;enumeration value="34"/>
     *                                   &lt;enumeration value="36"/>
     *                                   &lt;enumeration value="37"/>
     *                                   &lt;enumeration value="38"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="sdp">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;choice minOccurs="0">
     *                                       &lt;element name="donnees_service" maxOccurs="unbounded">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;all>
     *                                                 &lt;element name="id_base">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="id_fiche" type="{}p_string500"/>
     *                                                 &lt;element name="id_produit" type="{}p_string500"/>
     *                                                 &lt;element name="quantite">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                                                       &lt;minExclusive value="0"/>
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element ref="{}unite_uf"/>
     *                                                 &lt;element name="chantier_consommations" minOccurs="0">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                       &lt;enumeration value="1"/>
     *                                                       &lt;enumeration value="2"/>
     *                                                       &lt;enumeration value="3"/>
     *                                                       &lt;enumeration value="4"/>
     *                                                       &lt;enumeration value="5"/>
     *                                                       &lt;enumeration value="6"/>
     *                                                       &lt;enumeration value="7"/>
     *                                                       &lt;enumeration value="8"/>
     *                                                       &lt;enumeration value="9"/>
     *                                                       &lt;enumeration value="10"/>
     *                                                       &lt;enumeration value="11"/>
     *                                                       &lt;enumeration value="12"/>
     *                                                       &lt;enumeration value="13"/>
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="energie_vecteur" minOccurs="0">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                       &lt;enumeration value="1"/>
     *                                                       &lt;enumeration value="2"/>
     *                                                       &lt;enumeration value="3"/>
     *                                                       &lt;enumeration value="4"/>
     *                                                       &lt;enumeration value="5"/>
     *                                                       &lt;enumeration value="6"/>
     *                                                       &lt;enumeration value="7"/>
     *                                                       &lt;enumeration value="8"/>
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="energie_usage" minOccurs="0">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                       &lt;enumeration value="1"/>
     *                                                       &lt;enumeration value="2"/>
     *                                                       &lt;enumeration value="3"/>
     *                                                       &lt;enumeration value="4"/>
     *                                                       &lt;enumeration value="5"/>
     *                                                       &lt;enumeration value="6"/>
     *                                                       &lt;enumeration value="7"/>
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="eau_type" minOccurs="0">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                       &lt;enumeration value="1"/>
     *                                                       &lt;enumeration value="2"/>
     *                                                       &lt;enumeration value="3"/>
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="commentaire" type="{}p_string500" minOccurs="0"/>
     *                                               &lt;/all>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="lot" maxOccurs="14" minOccurs="2">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;choice minOccurs="0">
     *                                                 &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
     *                                                 &lt;element name="sous_lot" maxOccurs="unbounded">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
     *                             &lt;element name="partie_commune">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                   &lt;minInclusive value="0"/>
     *                                   &lt;maxInclusive value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
     *                             &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="periode_reference" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="production_pv" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="autoconsommation_pv" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="cogeneration" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="autoconsommation_cogeneration" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="date_etude" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "dateEtude"
    })
    public static class EntreeProjet {

        @XmlElement(required = true)
        protected List<RSEnv.EntreeProjet.Batiment> batiment;
        @XmlElement(name = "date_etude", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dateEtude;

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
        public List<RSEnv.EntreeProjet.Batiment> getBatiment() {
            if (batiment == null) {
                batiment = new ArrayList<RSEnv.EntreeProjet.Batiment>();
            }
            return this.batiment;
        }

        /**
         * Gets the value of the dateEtude property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
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
        public void setDateEtude(XMLGregorianCalendar value) {
            this.dateEtude = value;
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
         *         &lt;element name="zone" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="usage">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;enumeration value="0"/>
         *                         &lt;enumeration value="1"/>
         *                         &lt;enumeration value="2"/>
         *                         &lt;enumeration value="3"/>
         *                         &lt;enumeration value="4"/>
         *                         &lt;enumeration value="5"/>
         *                         &lt;enumeration value="6"/>
         *                         &lt;enumeration value="7"/>
         *                         &lt;enumeration value="8"/>
         *                         &lt;enumeration value="10"/>
         *                         &lt;enumeration value="11"/>
         *                         &lt;enumeration value="12"/>
         *                         &lt;enumeration value="13"/>
         *                         &lt;enumeration value="14"/>
         *                         &lt;enumeration value="15"/>
         *                         &lt;enumeration value="16"/>
         *                         &lt;enumeration value="17"/>
         *                         &lt;enumeration value="18"/>
         *                         &lt;enumeration value="19"/>
         *                         &lt;enumeration value="20"/>
         *                         &lt;enumeration value="22"/>
         *                         &lt;enumeration value="24"/>
         *                         &lt;enumeration value="26"/>
         *                         &lt;enumeration value="27"/>
         *                         &lt;enumeration value="28"/>
         *                         &lt;enumeration value="29"/>
         *                         &lt;enumeration value="30"/>
         *                         &lt;enumeration value="32"/>
         *                         &lt;enumeration value="33"/>
         *                         &lt;enumeration value="34"/>
         *                         &lt;enumeration value="36"/>
         *                         &lt;enumeration value="37"/>
         *                         &lt;enumeration value="38"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="sdp">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;choice minOccurs="0">
         *                             &lt;element name="donnees_service" maxOccurs="unbounded">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;all>
         *                                       &lt;element name="id_base">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="id_fiche" type="{}p_string500"/>
         *                                       &lt;element name="id_produit" type="{}p_string500"/>
         *                                       &lt;element name="quantite">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *                                             &lt;minExclusive value="0"/>
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element ref="{}unite_uf"/>
         *                                       &lt;element name="chantier_consommations" minOccurs="0">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                             &lt;enumeration value="1"/>
         *                                             &lt;enumeration value="2"/>
         *                                             &lt;enumeration value="3"/>
         *                                             &lt;enumeration value="4"/>
         *                                             &lt;enumeration value="5"/>
         *                                             &lt;enumeration value="6"/>
         *                                             &lt;enumeration value="7"/>
         *                                             &lt;enumeration value="8"/>
         *                                             &lt;enumeration value="9"/>
         *                                             &lt;enumeration value="10"/>
         *                                             &lt;enumeration value="11"/>
         *                                             &lt;enumeration value="12"/>
         *                                             &lt;enumeration value="13"/>
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="energie_vecteur" minOccurs="0">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                             &lt;enumeration value="1"/>
         *                                             &lt;enumeration value="2"/>
         *                                             &lt;enumeration value="3"/>
         *                                             &lt;enumeration value="4"/>
         *                                             &lt;enumeration value="5"/>
         *                                             &lt;enumeration value="6"/>
         *                                             &lt;enumeration value="7"/>
         *                                             &lt;enumeration value="8"/>
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="energie_usage" minOccurs="0">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                             &lt;enumeration value="1"/>
         *                                             &lt;enumeration value="2"/>
         *                                             &lt;enumeration value="3"/>
         *                                             &lt;enumeration value="4"/>
         *                                             &lt;enumeration value="5"/>
         *                                             &lt;enumeration value="6"/>
         *                                             &lt;enumeration value="7"/>
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="eau_type" minOccurs="0">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                             &lt;enumeration value="1"/>
         *                                             &lt;enumeration value="2"/>
         *                                             &lt;enumeration value="3"/>
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="commentaire" type="{}p_string500" minOccurs="0"/>
         *                                     &lt;/all>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="lot" maxOccurs="14" minOccurs="2">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;choice minOccurs="0">
         *                                       &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
         *                                       &lt;element name="sous_lot" maxOccurs="unbounded">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
         *                   &lt;element name="partie_commune">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                         &lt;minInclusive value="0"/>
         *                         &lt;maxInclusive value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="calculette_eau" type="{}t_calculette_eau" minOccurs="0"/>
         *                   &lt;element name="calculette_chantier" type="{}t_calculette_chantier" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="periode_reference" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="production_pv" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="autoconsommation_pv" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="cogeneration" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="autoconsommation_cogeneration" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
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
            "zone",
            "periodeReference",
            "productionPv",
            "autoconsommationPv",
            "cogeneration",
            "autoconsommationCogeneration"
        })
        public static class Batiment {

            protected int index;
            @XmlElement(required = true)
            protected String nom;
            @XmlElement(required = true)
            protected List<RSEnv.EntreeProjet.Batiment.Zone> zone;
            @XmlElement(name = "periode_reference", defaultValue = "50")
            protected int periodeReference;
            @XmlElement(name = "production_pv")
            protected BigDecimal productionPv;
            @XmlElement(name = "autoconsommation_pv")
            protected BigDecimal autoconsommationPv;
            protected BigDecimal cogeneration;
            @XmlElement(name = "autoconsommation_cogeneration")
            protected BigDecimal autoconsommationCogeneration;

            /**
             * Gets the value of the index property.
             * 
             */
            public int getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             */
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
            public void setNom(String value) {
                this.nom = value;
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
            public List<RSEnv.EntreeProjet.Batiment.Zone> getZone() {
                if (zone == null) {
                    zone = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone>();
                }
                return this.zone;
            }

            /**
             * Gets the value of the periodeReference property.
             * 
             */
            public int getPeriodeReference() {
                return periodeReference;
            }

            /**
             * Sets the value of the periodeReference property.
             * 
             */
            public void setPeriodeReference(int value) {
                this.periodeReference = value;
            }

            /**
             * Gets the value of the productionPv property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getProductionPv() {
                return productionPv;
            }

            /**
             * Sets the value of the productionPv property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setProductionPv(BigDecimal value) {
                this.productionPv = value;
            }

            /**
             * Gets the value of the autoconsommationPv property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAutoconsommationPv() {
                return autoconsommationPv;
            }

            /**
             * Sets the value of the autoconsommationPv property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAutoconsommationPv(BigDecimal value) {
                this.autoconsommationPv = value;
            }

            /**
             * Gets the value of the cogeneration property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCogeneration() {
                return cogeneration;
            }

            /**
             * Sets the value of the cogeneration property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCogeneration(BigDecimal value) {
                this.cogeneration = value;
            }

            /**
             * Gets the value of the autoconsommationCogeneration property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAutoconsommationCogeneration() {
                return autoconsommationCogeneration;
            }

            /**
             * Sets the value of the autoconsommationCogeneration property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAutoconsommationCogeneration(BigDecimal value) {
                this.autoconsommationCogeneration = value;
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
             *               &lt;enumeration value="0"/>
             *               &lt;enumeration value="1"/>
             *               &lt;enumeration value="2"/>
             *               &lt;enumeration value="3"/>
             *               &lt;enumeration value="4"/>
             *               &lt;enumeration value="5"/>
             *               &lt;enumeration value="6"/>
             *               &lt;enumeration value="7"/>
             *               &lt;enumeration value="8"/>
             *               &lt;enumeration value="10"/>
             *               &lt;enumeration value="11"/>
             *               &lt;enumeration value="12"/>
             *               &lt;enumeration value="13"/>
             *               &lt;enumeration value="14"/>
             *               &lt;enumeration value="15"/>
             *               &lt;enumeration value="16"/>
             *               &lt;enumeration value="17"/>
             *               &lt;enumeration value="18"/>
             *               &lt;enumeration value="19"/>
             *               &lt;enumeration value="20"/>
             *               &lt;enumeration value="22"/>
             *               &lt;enumeration value="24"/>
             *               &lt;enumeration value="26"/>
             *               &lt;enumeration value="27"/>
             *               &lt;enumeration value="28"/>
             *               &lt;enumeration value="29"/>
             *               &lt;enumeration value="30"/>
             *               &lt;enumeration value="32"/>
             *               &lt;enumeration value="33"/>
             *               &lt;enumeration value="34"/>
             *               &lt;enumeration value="36"/>
             *               &lt;enumeration value="37"/>
             *               &lt;enumeration value="38"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="sdp">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;choice minOccurs="0">
             *                   &lt;element name="donnees_service" maxOccurs="unbounded">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;all>
             *                             &lt;element name="id_base">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="id_fiche" type="{}p_string500"/>
             *                             &lt;element name="id_produit" type="{}p_string500"/>
             *                             &lt;element name="quantite">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
             *                                   &lt;minExclusive value="0"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element ref="{}unite_uf"/>
             *                             &lt;element name="chantier_consommations" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                   &lt;enumeration value="1"/>
             *                                   &lt;enumeration value="2"/>
             *                                   &lt;enumeration value="3"/>
             *                                   &lt;enumeration value="4"/>
             *                                   &lt;enumeration value="5"/>
             *                                   &lt;enumeration value="6"/>
             *                                   &lt;enumeration value="7"/>
             *                                   &lt;enumeration value="8"/>
             *                                   &lt;enumeration value="9"/>
             *                                   &lt;enumeration value="10"/>
             *                                   &lt;enumeration value="11"/>
             *                                   &lt;enumeration value="12"/>
             *                                   &lt;enumeration value="13"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="energie_vecteur" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                   &lt;enumeration value="1"/>
             *                                   &lt;enumeration value="2"/>
             *                                   &lt;enumeration value="3"/>
             *                                   &lt;enumeration value="4"/>
             *                                   &lt;enumeration value="5"/>
             *                                   &lt;enumeration value="6"/>
             *                                   &lt;enumeration value="7"/>
             *                                   &lt;enumeration value="8"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="energie_usage" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                   &lt;enumeration value="1"/>
             *                                   &lt;enumeration value="2"/>
             *                                   &lt;enumeration value="3"/>
             *                                   &lt;enumeration value="4"/>
             *                                   &lt;enumeration value="5"/>
             *                                   &lt;enumeration value="6"/>
             *                                   &lt;enumeration value="7"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="eau_type" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                   &lt;enumeration value="1"/>
             *                                   &lt;enumeration value="2"/>
             *                                   &lt;enumeration value="3"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="commentaire" type="{}p_string500" minOccurs="0"/>
             *                           &lt;/all>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="lot" maxOccurs="14" minOccurs="2">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;choice minOccurs="0">
             *                             &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
             *                             &lt;element name="sous_lot" maxOccurs="unbounded">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
             *         &lt;element name="partie_commune">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *               &lt;minInclusive value="0"/>
             *               &lt;maxInclusive value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
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
                "sdp",
                "contributeur",
                "partieCommune",
                "calculetteEau",
                "calculetteChantier"
            })
            public static class Zone {

                protected int index;
                protected int usage;
                @XmlElement(required = true)
                protected BigDecimal sdp;
                @XmlElement(required = true)
                protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur> contributeur;
                @XmlElement(name = "partie_commune", defaultValue = "0")
                protected int partieCommune;
                @XmlElement(name = "calculette_eau")
                protected TCalculetteEau calculetteEau;
                @XmlElement(name = "calculette_chantier")
                protected TCalculetteChantier calculetteChantier;

                /**
                 * Gets the value of the index property.
                 * 
                 */
                public int getIndex() {
                    return index;
                }

                /**
                 * Sets the value of the index property.
                 * 
                 */
                public void setIndex(int value) {
                    this.index = value;
                }

                /**
                 * Gets the value of the usage property.
                 * 
                 */
                public int getUsage() {
                    return usage;
                }

                /**
                 * Sets the value of the usage property.
                 * 
                 */
                public void setUsage(int value) {
                    this.usage = value;
                }

                /**
                 * Gets the value of the sdp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getSdp() {
                    return sdp;
                }

                /**
                 * Sets the value of the sdp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setSdp(BigDecimal value) {
                    this.sdp = value;
                }

                /**
                 * Gets the value of the contributeur property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the contributeur property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getContributeur().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur }
                 * 
                 * 
                 */
                public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur> getContributeur() {
                    if (contributeur == null) {
                        contributeur = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur>();
                    }
                    return this.contributeur;
                }

                /**
                 * Gets the value of the partieCommune property.
                 * 
                 */
                public int getPartieCommune() {
                    return partieCommune;
                }

                /**
                 * Sets the value of the partieCommune property.
                 * 
                 */
                public void setPartieCommune(int value) {
                    this.partieCommune = value;
                }

                /**
                 * Gets the value of the calculetteEau property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TCalculetteEau }
                 *     
                 */
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
                 *       &lt;choice minOccurs="0">
                 *         &lt;element name="donnees_service" maxOccurs="unbounded">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;all>
                 *                   &lt;element name="id_base">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="id_fiche" type="{}p_string500"/>
                 *                   &lt;element name="id_produit" type="{}p_string500"/>
                 *                   &lt;element name="quantite">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                 *                         &lt;minExclusive value="0"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element ref="{}unite_uf"/>
                 *                   &lt;element name="chantier_consommations" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                         &lt;enumeration value="1"/>
                 *                         &lt;enumeration value="2"/>
                 *                         &lt;enumeration value="3"/>
                 *                         &lt;enumeration value="4"/>
                 *                         &lt;enumeration value="5"/>
                 *                         &lt;enumeration value="6"/>
                 *                         &lt;enumeration value="7"/>
                 *                         &lt;enumeration value="8"/>
                 *                         &lt;enumeration value="9"/>
                 *                         &lt;enumeration value="10"/>
                 *                         &lt;enumeration value="11"/>
                 *                         &lt;enumeration value="12"/>
                 *                         &lt;enumeration value="13"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="energie_vecteur" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                         &lt;enumeration value="1"/>
                 *                         &lt;enumeration value="2"/>
                 *                         &lt;enumeration value="3"/>
                 *                         &lt;enumeration value="4"/>
                 *                         &lt;enumeration value="5"/>
                 *                         &lt;enumeration value="6"/>
                 *                         &lt;enumeration value="7"/>
                 *                         &lt;enumeration value="8"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="energie_usage" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                         &lt;enumeration value="1"/>
                 *                         &lt;enumeration value="2"/>
                 *                         &lt;enumeration value="3"/>
                 *                         &lt;enumeration value="4"/>
                 *                         &lt;enumeration value="5"/>
                 *                         &lt;enumeration value="6"/>
                 *                         &lt;enumeration value="7"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="eau_type" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                         &lt;enumeration value="1"/>
                 *                         &lt;enumeration value="2"/>
                 *                         &lt;enumeration value="3"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="commentaire" type="{}p_string500" minOccurs="0"/>
                 *                 &lt;/all>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="lot" maxOccurs="14" minOccurs="2">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;choice minOccurs="0">
                 *                   &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                 *                   &lt;element name="sous_lot" maxOccurs="unbounded">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
                    "donneesService",
                    "lot"
                })
                public static class Contributeur {

                    @XmlElement(name = "donnees_service")
                    protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.DonneesService> donneesService;
                    protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot> lot;
                    @XmlAttribute(name = "ref", required = true)
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
                     * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.DonneesService }
                     * 
                     * 
                     */
                    public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.DonneesService> getDonneesService() {
                        if (donneesService == null) {
                            donneesService = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.DonneesService>();
                        }
                        return this.donneesService;
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
                     * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot }
                     * 
                     * 
                     */
                    public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot> getLot() {
                        if (lot == null) {
                            lot = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot>();
                        }
                        return this.lot;
                    }

                    /**
                     * Gets the value of the ref property.
                     * 
                     */
                    public int getRef() {
                        return ref;
                    }

                    /**
                     * Sets the value of the ref property.
                     * 
                     */
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
                     *         &lt;element name="id_base">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="id_fiche" type="{}p_string500"/>
                     *         &lt;element name="id_produit" type="{}p_string500"/>
                     *         &lt;element name="quantite">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
                     *               &lt;minExclusive value="0"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element ref="{}unite_uf"/>
                     *         &lt;element name="chantier_consommations" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *               &lt;enumeration value="1"/>
                     *               &lt;enumeration value="2"/>
                     *               &lt;enumeration value="3"/>
                     *               &lt;enumeration value="4"/>
                     *               &lt;enumeration value="5"/>
                     *               &lt;enumeration value="6"/>
                     *               &lt;enumeration value="7"/>
                     *               &lt;enumeration value="8"/>
                     *               &lt;enumeration value="9"/>
                     *               &lt;enumeration value="10"/>
                     *               &lt;enumeration value="11"/>
                     *               &lt;enumeration value="12"/>
                     *               &lt;enumeration value="13"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="energie_vecteur" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *               &lt;enumeration value="1"/>
                     *               &lt;enumeration value="2"/>
                     *               &lt;enumeration value="3"/>
                     *               &lt;enumeration value="4"/>
                     *               &lt;enumeration value="5"/>
                     *               &lt;enumeration value="6"/>
                     *               &lt;enumeration value="7"/>
                     *               &lt;enumeration value="8"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="energie_usage" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *               &lt;enumeration value="1"/>
                     *               &lt;enumeration value="2"/>
                     *               &lt;enumeration value="3"/>
                     *               &lt;enumeration value="4"/>
                     *               &lt;enumeration value="5"/>
                     *               &lt;enumeration value="6"/>
                     *               &lt;enumeration value="7"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="eau_type" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *               &lt;enumeration value="1"/>
                     *               &lt;enumeration value="2"/>
                     *               &lt;enumeration value="3"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="commentaire" type="{}p_string500" minOccurs="0"/>
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
                    public static class DonneesService {

                        @XmlElement(name = "id_base", defaultValue = "0")
                        protected int idBase;
                        @XmlElement(name = "id_fiche", required = true)
                        protected String idFiche;
                        @XmlElement(name = "id_produit", required = true)
                        protected String idProduit;
                        @XmlElement(required = true)
                        protected BigDecimal quantite;
                        @XmlElement(name = "unite_uf")
                        protected int uniteUf;
                        @XmlElement(name = "chantier_consommations")
                        protected Integer chantierConsommations;
                        @XmlElement(name = "energie_vecteur")
                        protected Integer energieVecteur;
                        @XmlElement(name = "energie_usage")
                        protected Integer energieUsage;
                        @XmlElement(name = "eau_type")
                        protected Integer eauType;
                        protected String commentaire;

                        /**
                         * Gets the value of the idBase property.
                         * 
                         */
                        public int getIdBase() {
                            return idBase;
                        }

                        /**
                         * Sets the value of the idBase property.
                         * 
                         */
                        public void setIdBase(int value) {
                            this.idBase = value;
                        }

                        /**
                         * Gets the value of the idFiche property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getIdFiche() {
                            return idFiche;
                        }

                        /**
                         * Sets the value of the idFiche property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setIdFiche(String value) {
                            this.idFiche = value;
                        }

                        /**
                         * Gets the value of the idProduit property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getIdProduit() {
                            return idProduit;
                        }

                        /**
                         * Sets the value of the idProduit property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setIdProduit(String value) {
                            this.idProduit = value;
                        }

                        /**
                         * Gets the value of the quantite property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public BigDecimal getQuantite() {
                            return quantite;
                        }

                        /**
                         * Sets the value of the quantite property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public void setQuantite(BigDecimal value) {
                            this.quantite = value;
                        }

                        /**
                         * Gets the value of the uniteUf property.
                         * 
                         */
                        public int getUniteUf() {
                            return uniteUf;
                        }

                        /**
                         * Sets the value of the uniteUf property.
                         * 
                         */
                        public void setUniteUf(int value) {
                            this.uniteUf = value;
                        }

                        /**
                         * Gets the value of the chantierConsommations property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Integer }
                         *     
                         */
                        public Integer getChantierConsommations() {
                            return chantierConsommations;
                        }

                        /**
                         * Sets the value of the chantierConsommations property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Integer }
                         *     
                         */
                        public void setChantierConsommations(Integer value) {
                            this.chantierConsommations = value;
                        }

                        /**
                         * Gets the value of the energieVecteur property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Integer }
                         *     
                         */
                        public Integer getEnergieVecteur() {
                            return energieVecteur;
                        }

                        /**
                         * Sets the value of the energieVecteur property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Integer }
                         *     
                         */
                        public void setEnergieVecteur(Integer value) {
                            this.energieVecteur = value;
                        }

                        /**
                         * Gets the value of the energieUsage property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Integer }
                         *     
                         */
                        public Integer getEnergieUsage() {
                            return energieUsage;
                        }

                        /**
                         * Sets the value of the energieUsage property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Integer }
                         *     
                         */
                        public void setEnergieUsage(Integer value) {
                            this.energieUsage = value;
                        }

                        /**
                         * Gets the value of the eauType property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Integer }
                         *     
                         */
                        public Integer getEauType() {
                            return eauType;
                        }

                        /**
                         * Sets the value of the eauType property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Integer }
                         *     
                         */
                        public void setEauType(Integer value) {
                            this.eauType = value;
                        }

                        /**
                         * Gets the value of the commentaire property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCommentaire() {
                            return commentaire;
                        }

                        /**
                         * Sets the value of the commentaire property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCommentaire(String value) {
                            this.commentaire = value;
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
                     *       &lt;choice minOccurs="0">
                     *         &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                     *         &lt;element name="sous_lot" maxOccurs="unbounded">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="donnees_composant" type="{}t_donnee_composant" maxOccurs="unbounded"/>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
                    public static class Lot {

                        @XmlElement(name = "donnees_composant")
                        protected List<TDonneeComposant> donneesComposant;
                        @XmlElement(name = "sous_lot")
                        protected List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot.SousLot> sousLot;
                        @XmlAttribute(name = "ref", required = true)
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
                         * {@link RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot.SousLot }
                         * 
                         * 
                         */
                        public List<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot.SousLot> getSousLot() {
                            if (sousLot == null) {
                                sousLot = new ArrayList<RSEnv.EntreeProjet.Batiment.Zone.Contributeur.Lot.SousLot>();
                            }
                            return this.sousLot;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         */
                        public int getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         */
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
                         *       &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
                        public static class SousLot {

                            @XmlElement(name = "donnees_composant", required = true)
                            protected List<TDonneeComposant> donneesComposant;
                            @XmlAttribute(name = "ref", required = true)
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
                            public List<TDonneeComposant> getDonneesComposant() {
                                if (donneesComposant == null) {
                                    donneesComposant = new ArrayList<TDonneeComposant>();
                                }
                                return this.donneesComposant;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            public void setRef(int value) {
                                this.ref = value;
                            }

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
     *         &lt;element name="batiment" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
     *                             &lt;element name="lot" maxOccurs="14" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;all>
     *                                                 &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
     *                                               &lt;/all>
     *                                               &lt;attribute name="ref" use="required">
     *                                                 &lt;simpleType>
     *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                   &lt;/restriction>
     *                                                 &lt;/simpleType>
     *                                               &lt;/attribute>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
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
     *                           &lt;/sequence>
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
     *                   &lt;element name="zone" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
     *                                       &lt;element name="lot" maxOccurs="14" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
     *                                                 &lt;element name="valeur_forfaitaire">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                       &lt;enumeration value="0"/>
     *                                                       &lt;enumeration value="1"/>
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;all>
     *                                                           &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
     *                                                         &lt;/all>
     *                                                         &lt;attribute name="ref" use="required">
     *                                                           &lt;simpleType>
     *                                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                                                             &lt;/restriction>
     *                                                           &lt;/simpleType>
     *                                                         &lt;/attribute>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
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
     *                                     &lt;/sequence>
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
     *                             &lt;element ref="{}indicateurs_performance_collection"/>
     *                             &lt;element name="coefficients_modulateurs">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="mgctype" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                                       &lt;element name="mgcgeo" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                                       &lt;element name="mgcalt" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                                       &lt;element name="mgcsurf" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                                       &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
     *                                       &lt;element name="valeurs_seuils" maxOccurs="8" minOccurs="8">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                                                 &lt;element name="nom" minOccurs="0">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                     &lt;/restriction>
     *                                                   &lt;/simpleType>
     *                                                 &lt;/element>
     *                                                 &lt;element name="unite" minOccurs="0">
     *                                                   &lt;simpleType>
     *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                                       &lt;minLength value="1"/>
     *                                                       &lt;whiteSpace value="collapse"/>
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
     *                   &lt;element name="part_donnees_generiques">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="part_impact_donnees_generiques" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="indicateur_completude" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element ref="{}indicateurs_performance_collection"/>
     *                   &lt;element name="coefficients_modulateurs">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
        "batiment"
    })
    public static class SortieProjet {

        @XmlElement(required = true)
        protected List<RSEnv.SortieProjet.Batiment> batiment;

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
        public List<RSEnv.SortieProjet.Batiment> getBatiment() {
            if (batiment == null) {
                batiment = new ArrayList<RSEnv.SortieProjet.Batiment>();
            }
            return this.batiment;
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
         *         &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
         *                   &lt;element name="lot" maxOccurs="14" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
         *                             &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;all>
         *                                       &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
         *                                     &lt;/all>
         *                                     &lt;attribute name="ref" use="required">
         *                                       &lt;simpleType>
         *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                         &lt;/restriction>
         *                                       &lt;/simpleType>
         *                                     &lt;/attribute>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
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
         *                 &lt;/sequence>
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
         *         &lt;element name="zone" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
         *                             &lt;element name="lot" maxOccurs="14" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
         *                                       &lt;element name="valeur_forfaitaire">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                             &lt;enumeration value="0"/>
         *                                             &lt;enumeration value="1"/>
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;all>
         *                                                 &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
         *                                               &lt;/all>
         *                                               &lt;attribute name="ref" use="required">
         *                                                 &lt;simpleType>
         *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *                                                   &lt;/restriction>
         *                                                 &lt;/simpleType>
         *                                               &lt;/attribute>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
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
         *                           &lt;/sequence>
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
         *                   &lt;element ref="{}indicateurs_performance_collection"/>
         *                   &lt;element name="coefficients_modulateurs">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="mgctype" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                             &lt;element name="mgcgeo" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                             &lt;element name="mgcalt" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                             &lt;element name="mgcsurf" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *                             &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
         *                             &lt;element name="valeurs_seuils" maxOccurs="8" minOccurs="8">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                                       &lt;element name="nom" minOccurs="0">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                           &lt;/restriction>
         *                                         &lt;/simpleType>
         *                                       &lt;/element>
         *                                       &lt;element name="unite" minOccurs="0">
         *                                         &lt;simpleType>
         *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                             &lt;minLength value="1"/>
         *                                             &lt;whiteSpace value="collapse"/>
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
         *         &lt;element name="part_donnees_generiques">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="part_impact_donnees_generiques" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="indicateur_completude" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element ref="{}indicateurs_performance_collection"/>
         *         &lt;element name="coefficients_modulateurs">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
            "index",
            "contributeur",
            "zone",
            "partDonneesGeneriques",
            "partImpactDonneesGeneriques",
            "indicateurCompletude",
            "indicateursPerformanceCollection",
            "coefficientsModulateurs"
        })
        public static class Batiment {

            protected int index;
            @XmlElement(required = true)
            protected List<RSEnv.SortieProjet.Batiment.Contributeur> contributeur;
            @XmlElement(required = true)
            protected List<RSEnv.SortieProjet.Batiment.Zone> zone;
            @XmlElement(name = "part_donnees_generiques", required = true)
            protected BigDecimal partDonneesGeneriques;
            @XmlElement(name = "part_impact_donnees_generiques")
            protected BigDecimal partImpactDonneesGeneriques;
            @XmlElement(name = "indicateur_completude")
            protected BigDecimal indicateurCompletude;
            @XmlElement(name = "indicateurs_performance_collection", required = true)
            protected IndicateursPerformanceCollection indicateursPerformanceCollection;
            @XmlElement(name = "coefficients_modulateurs", required = true)
            protected RSEnv.SortieProjet.Batiment.CoefficientsModulateurs coefficientsModulateurs;

            /**
             * Gets the value of the index property.
             * 
             */
            public int getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             */
            public void setIndex(int value) {
                this.index = value;
            }

            /**
             * Gets the value of the contributeur property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the contributeur property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getContributeur().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RSEnv.SortieProjet.Batiment.Contributeur }
             * 
             * 
             */
            public List<RSEnv.SortieProjet.Batiment.Contributeur> getContributeur() {
                if (contributeur == null) {
                    contributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur>();
                }
                return this.contributeur;
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
            public List<RSEnv.SortieProjet.Batiment.Zone> getZone() {
                if (zone == null) {
                    zone = new ArrayList<RSEnv.SortieProjet.Batiment.Zone>();
                }
                return this.zone;
            }

            /**
             * Gets the value of the partDonneesGeneriques property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPartDonneesGeneriques() {
                return partDonneesGeneriques;
            }

            /**
             * Sets the value of the partDonneesGeneriques property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPartDonneesGeneriques(BigDecimal value) {
                this.partDonneesGeneriques = value;
            }

            /**
             * Gets the value of the partImpactDonneesGeneriques property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPartImpactDonneesGeneriques() {
                return partImpactDonneesGeneriques;
            }

            /**
             * Sets the value of the partImpactDonneesGeneriques property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPartImpactDonneesGeneriques(BigDecimal value) {
                this.partImpactDonneesGeneriques = value;
            }

            /**
             * Gets the value of the indicateurCompletude property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getIndicateurCompletude() {
                return indicateurCompletude;
            }

            /**
             * Sets the value of the indicateurCompletude property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setIndicateurCompletude(BigDecimal value) {
                this.indicateurCompletude = value;
            }

            /**
             * Gets the value of the indicateursPerformanceCollection property.
             * 
             * @return
             *     possible object is
             *     {@link IndicateursPerformanceCollection }
             *     
             */
            public IndicateursPerformanceCollection getIndicateursPerformanceCollection() {
                return indicateursPerformanceCollection;
            }

            /**
             * Sets the value of the indicateursPerformanceCollection property.
             * 
             * @param value
             *     allowed object is
             *     {@link IndicateursPerformanceCollection }
             *     
             */
            public void setIndicateursPerformanceCollection(IndicateursPerformanceCollection value) {
                this.indicateursPerformanceCollection = value;
            }

            /**
             * Gets the value of the coefficientsModulateurs property.
             * 
             * @return
             *     possible object is
             *     {@link RSEnv.SortieProjet.Batiment.CoefficientsModulateurs }
             *     
             */
            public RSEnv.SortieProjet.Batiment.CoefficientsModulateurs getCoefficientsModulateurs() {
                return coefficientsModulateurs;
            }

            /**
             * Sets the value of the coefficientsModulateurs property.
             * 
             * @param value
             *     allowed object is
             *     {@link RSEnv.SortieProjet.Batiment.CoefficientsModulateurs }
             *     
             */
            public void setCoefficientsModulateurs(RSEnv.SortieProjet.Batiment.CoefficientsModulateurs value) {
                this.coefficientsModulateurs = value;
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
             *         &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
                "mpark"
            })
            public static class CoefficientsModulateurs {

                protected float mpark;

                /**
                 * Gets the value of the mpark property.
                 * 
                 */
                public float getMpark() {
                    return mpark;
                }

                /**
                 * Sets the value of the mpark property.
                 * 
                 */
                public void setMpark(float value) {
                    this.mpark = value;
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
             *         &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
             *         &lt;element name="lot" maxOccurs="14" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
             *                   &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;all>
             *                             &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
             *                           &lt;/all>
             *                           &lt;attribute name="ref" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
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
             *       &lt;/sequence>
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
                "indicateursCollection",
                "lot"
            })
            public static class Contributeur {

                @XmlElement(name = "indicateurs_collection", required = true)
                protected TIndicateur indicateursCollection;
                protected List<RSEnv.SortieProjet.Batiment.Contributeur.Lot> lot;
                @XmlAttribute(name = "ref", required = true)
                protected int ref;

                /**
                 * Gets the value of the indicateursCollection property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TIndicateur }
                 *     
                 */
                public TIndicateur getIndicateursCollection() {
                    return indicateursCollection;
                }

                /**
                 * Sets the value of the indicateursCollection property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TIndicateur }
                 *     
                 */
                public void setIndicateursCollection(TIndicateur value) {
                    this.indicateursCollection = value;
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
                 * {@link RSEnv.SortieProjet.Batiment.Contributeur.Lot }
                 * 
                 * 
                 */
                public List<RSEnv.SortieProjet.Batiment.Contributeur.Lot> getLot() {
                    if (lot == null) {
                        lot = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Lot>();
                    }
                    return this.lot;
                }

                /**
                 * Gets the value of the ref property.
                 * 
                 */
                public int getRef() {
                    return ref;
                }

                /**
                 * Sets the value of the ref property.
                 * 
                 */
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
                 *         &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                 *         &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;all>
                 *                   &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                 *                 &lt;/all>
                 *                 &lt;attribute name="ref" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
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
                    "indicateursCollection",
                    "sousLot"
                })
                public static class Lot {

                    @XmlElement(name = "indicateurs_collection", required = true)
                    protected TIndicateur indicateursCollection;
                    @XmlElement(name = "sous_lot")
                    protected List<RSEnv.SortieProjet.Batiment.Contributeur.Lot.SousLot> sousLot;
                    @XmlAttribute(name = "ref", required = true)
                    protected int ref;

                    /**
                     * Gets the value of the indicateursCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    public TIndicateur getIndicateursCollection() {
                        return indicateursCollection;
                    }

                    /**
                     * Sets the value of the indicateursCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    public void setIndicateursCollection(TIndicateur value) {
                        this.indicateursCollection = value;
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
                     * {@link RSEnv.SortieProjet.Batiment.Contributeur.Lot.SousLot }
                     * 
                     * 
                     */
                    public List<RSEnv.SortieProjet.Batiment.Contributeur.Lot.SousLot> getSousLot() {
                        if (sousLot == null) {
                            sousLot = new ArrayList<RSEnv.SortieProjet.Batiment.Contributeur.Lot.SousLot>();
                        }
                        return this.sousLot;
                    }

                    /**
                     * Gets the value of the ref property.
                     * 
                     */
                    public int getRef() {
                        return ref;
                    }

                    /**
                     * Sets the value of the ref property.
                     * 
                     */
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
                     *         &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                     *       &lt;/all>
                     *       &lt;attribute name="ref" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
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
                    public static class SousLot {

                        @XmlElement(name = "indicateurs_collection", required = true)
                        protected TIndicateur indicateursCollection;
                        @XmlAttribute(name = "ref", required = true)
                        protected int ref;

                        /**
                         * Gets the value of the indicateursCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        public TIndicateur getIndicateursCollection() {
                            return indicateursCollection;
                        }

                        /**
                         * Sets the value of the indicateursCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        public void setIndicateursCollection(TIndicateur value) {
                            this.indicateursCollection = value;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         */
                        public int getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         */
                        public void setRef(int value) {
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
             *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="contributeur" maxOccurs="4" minOccurs="4">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
             *                   &lt;element name="lot" maxOccurs="14" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
             *                             &lt;element name="valeur_forfaitaire">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                   &lt;enumeration value="0"/>
             *                                   &lt;enumeration value="1"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;all>
             *                                       &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
             *                                     &lt;/all>
             *                                     &lt;attribute name="ref" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
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
             *                 &lt;/sequence>
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
             *         &lt;element ref="{}indicateurs_performance_collection"/>
             *         &lt;element name="coefficients_modulateurs">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="mgctype" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *                   &lt;element name="mgcgeo" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *                   &lt;element name="mgcalt" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *                   &lt;element name="mgcsurf" type="{http://www.w3.org/2001/XMLSchema}float"/>
             *                   &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
             *                   &lt;element name="valeurs_seuils" maxOccurs="8" minOccurs="8">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *                             &lt;element name="nom" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="unite" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                   &lt;minLength value="1"/>
             *                                   &lt;whiteSpace value="collapse"/>
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
                "index",
                "contributeur",
                "indicateursPerformanceCollection",
                "coefficientsModulateurs"
            })
            public static class Zone {

                protected int index;
                @XmlElement(required = true)
                protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur> contributeur;
                @XmlElement(name = "indicateurs_performance_collection", required = true)
                protected IndicateursPerformanceCollection indicateursPerformanceCollection;
                @XmlElement(name = "coefficients_modulateurs", required = true)
                protected RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs coefficientsModulateurs;

                /**
                 * Gets the value of the index property.
                 * 
                 */
                public int getIndex() {
                    return index;
                }

                /**
                 * Sets the value of the index property.
                 * 
                 */
                public void setIndex(int value) {
                    this.index = value;
                }

                /**
                 * Gets the value of the contributeur property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the contributeur property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getContributeur().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur }
                 * 
                 * 
                 */
                public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur> getContributeur() {
                    if (contributeur == null) {
                        contributeur = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur>();
                    }
                    return this.contributeur;
                }

                /**
                 * Gets the value of the indicateursPerformanceCollection property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link IndicateursPerformanceCollection }
                 *     
                 */
                public IndicateursPerformanceCollection getIndicateursPerformanceCollection() {
                    return indicateursPerformanceCollection;
                }

                /**
                 * Sets the value of the indicateursPerformanceCollection property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link IndicateursPerformanceCollection }
                 *     
                 */
                public void setIndicateursPerformanceCollection(IndicateursPerformanceCollection value) {
                    this.indicateursPerformanceCollection = value;
                }

                /**
                 * Gets the value of the coefficientsModulateurs property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs }
                 *     
                 */
                public RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs getCoefficientsModulateurs() {
                    return coefficientsModulateurs;
                }

                /**
                 * Sets the value of the coefficientsModulateurs property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs }
                 *     
                 */
                public void setCoefficientsModulateurs(RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs value) {
                    this.coefficientsModulateurs = value;
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
                 *         &lt;element name="mgctype" type="{http://www.w3.org/2001/XMLSchema}float"/>
                 *         &lt;element name="mgcgeo" type="{http://www.w3.org/2001/XMLSchema}float"/>
                 *         &lt;element name="mgcalt" type="{http://www.w3.org/2001/XMLSchema}float"/>
                 *         &lt;element name="mgcsurf" type="{http://www.w3.org/2001/XMLSchema}float"/>
                 *         &lt;element name="mpark" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
                 *         &lt;element name="valeurs_seuils" maxOccurs="8" minOccurs="8">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                 *                   &lt;element name="nom" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="unite" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                         &lt;minLength value="1"/>
                 *                         &lt;whiteSpace value="collapse"/>
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
                    "mgctype",
                    "mgcgeo",
                    "mgcalt",
                    "mgcsurf",
                    "mpark",
                    "valeursSeuils"
                })
                public static class CoefficientsModulateurs {

                    protected float mgctype;
                    protected float mgcgeo;
                    protected float mgcalt;
                    protected float mgcsurf;
                    protected Float mpark;
                    @XmlElement(name = "valeurs_seuils", required = true)
                    protected List<RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs.ValeursSeuils> valeursSeuils;

                    /**
                     * Gets the value of the mgctype property.
                     * 
                     */
                    public float getMgctype() {
                        return mgctype;
                    }

                    /**
                     * Sets the value of the mgctype property.
                     * 
                     */
                    public void setMgctype(float value) {
                        this.mgctype = value;
                    }

                    /**
                     * Gets the value of the mgcgeo property.
                     * 
                     */
                    public float getMgcgeo() {
                        return mgcgeo;
                    }

                    /**
                     * Sets the value of the mgcgeo property.
                     * 
                     */
                    public void setMgcgeo(float value) {
                        this.mgcgeo = value;
                    }

                    /**
                     * Gets the value of the mgcalt property.
                     * 
                     */
                    public float getMgcalt() {
                        return mgcalt;
                    }

                    /**
                     * Sets the value of the mgcalt property.
                     * 
                     */
                    public void setMgcalt(float value) {
                        this.mgcalt = value;
                    }

                    /**
                     * Gets the value of the mgcsurf property.
                     * 
                     */
                    public float getMgcsurf() {
                        return mgcsurf;
                    }

                    /**
                     * Sets the value of the mgcsurf property.
                     * 
                     */
                    public void setMgcsurf(float value) {
                        this.mgcsurf = value;
                    }

                    /**
                     * Gets the value of the mpark property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Float }
                     *     
                     */
                    public Float getMpark() {
                        return mpark;
                    }

                    /**
                     * Sets the value of the mpark property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Float }
                     *     
                     */
                    public void setMpark(Float value) {
                        this.mpark = value;
                    }

                    /**
                     * Gets the value of the valeursSeuils property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the valeursSeuils property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getValeursSeuils().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs.ValeursSeuils }
                     * 
                     * 
                     */
                    public List<RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs.ValeursSeuils> getValeursSeuils() {
                        if (valeursSeuils == null) {
                            valeursSeuils = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs.ValeursSeuils>();
                        }
                        return this.valeursSeuils;
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
                     *         &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
                     *         &lt;element name="nom" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="unite" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *               &lt;minLength value="1"/>
                     *               &lt;whiteSpace value="collapse"/>
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
                        "valeur",
                        "nom",
                        "unite"
                    })
                    public static class ValeursSeuils {

                        @XmlElement(required = true)
                        protected BigDecimal valeur;
                        protected String nom;
                        protected String unite;
                        @XmlAttribute(name = "ref", required = true)
                        protected int ref;

                        /**
                         * Gets the value of the valeur property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public BigDecimal getValeur() {
                            return valeur;
                        }

                        /**
                         * Sets the value of the valeur property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public void setValeur(BigDecimal value) {
                            this.valeur = value;
                        }

                        /**
                         * Gets the value of the nom property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
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
                        public void setNom(String value) {
                            this.nom = value;
                        }

                        /**
                         * Gets the value of the unite property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getUnite() {
                            return unite;
                        }

                        /**
                         * Sets the value of the unite property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setUnite(String value) {
                            this.unite = value;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         */
                        public int getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         */
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
                 *         &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                 *         &lt;element name="lot" maxOccurs="14" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                 *                   &lt;element name="valeur_forfaitaire">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                         &lt;enumeration value="0"/>
                 *                         &lt;enumeration value="1"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;all>
                 *                             &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                 *                           &lt;/all>
                 *                           &lt;attribute name="ref" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
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
                 *       &lt;/sequence>
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
                    "indicateursCollection",
                    "lot"
                })
                public static class Contributeur {

                    @XmlElement(name = "indicateurs_collection", required = true)
                    protected TIndicateur indicateursCollection;
                    protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot> lot;
                    @XmlAttribute(name = "ref", required = true)
                    protected int ref;

                    /**
                     * Gets the value of the indicateursCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TIndicateur }
                     *     
                     */
                    public TIndicateur getIndicateursCollection() {
                        return indicateursCollection;
                    }

                    /**
                     * Sets the value of the indicateursCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TIndicateur }
                     *     
                     */
                    public void setIndicateursCollection(TIndicateur value) {
                        this.indicateursCollection = value;
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
                     * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot }
                     * 
                     * 
                     */
                    public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot> getLot() {
                        if (lot == null) {
                            lot = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot>();
                        }
                        return this.lot;
                    }

                    /**
                     * Gets the value of the ref property.
                     * 
                     */
                    public int getRef() {
                        return ref;
                    }

                    /**
                     * Sets the value of the ref property.
                     * 
                     */
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
                     *         &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                     *         &lt;element name="valeur_forfaitaire">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *               &lt;enumeration value="0"/>
                     *               &lt;enumeration value="1"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="sous_lot" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;all>
                     *                   &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                     *                 &lt;/all>
                     *                 &lt;attribute name="ref" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
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
                        "indicateursCollection",
                        "valeurForfaitaire",
                        "sousLot"
                    })
                    public static class Lot {

                        @XmlElement(name = "indicateurs_collection", required = true)
                        protected TIndicateur indicateursCollection;
                        @XmlElement(name = "valeur_forfaitaire", defaultValue = "1")
                        protected int valeurForfaitaire;
                        @XmlElement(name = "sous_lot")
                        protected List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot.SousLot> sousLot;
                        @XmlAttribute(name = "ref", required = true)
                        protected int ref;

                        /**
                         * Gets the value of the indicateursCollection property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link TIndicateur }
                         *     
                         */
                        public TIndicateur getIndicateursCollection() {
                            return indicateursCollection;
                        }

                        /**
                         * Sets the value of the indicateursCollection property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link TIndicateur }
                         *     
                         */
                        public void setIndicateursCollection(TIndicateur value) {
                            this.indicateursCollection = value;
                        }

                        /**
                         * Gets the value of the valeurForfaitaire property.
                         * 
                         */
                        public int getValeurForfaitaire() {
                            return valeurForfaitaire;
                        }

                        /**
                         * Sets the value of the valeurForfaitaire property.
                         * 
                         */
                        public void setValeurForfaitaire(int value) {
                            this.valeurForfaitaire = value;
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
                         * {@link RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot.SousLot }
                         * 
                         * 
                         */
                        public List<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot.SousLot> getSousLot() {
                            if (sousLot == null) {
                                sousLot = new ArrayList<RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot.SousLot>();
                            }
                            return this.sousLot;
                        }

                        /**
                         * Gets the value of the ref property.
                         * 
                         */
                        public int getRef() {
                            return ref;
                        }

                        /**
                         * Sets the value of the ref property.
                         * 
                         */
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
                         *         &lt;element name="indicateurs_collection" type="{}t_indicateur"/>
                         *       &lt;/all>
                         *       &lt;attribute name="ref" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
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
                        public static class SousLot {

                            @XmlElement(name = "indicateurs_collection", required = true)
                            protected TIndicateur indicateursCollection;
                            @XmlAttribute(name = "ref", required = true)
                            protected int ref;

                            /**
                             * Gets the value of the indicateursCollection property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TIndicateur }
                             *     
                             */
                            public TIndicateur getIndicateursCollection() {
                                return indicateursCollection;
                            }

                            /**
                             * Sets the value of the indicateursCollection property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TIndicateur }
                             *     
                             */
                            public void setIndicateursCollection(TIndicateur value) {
                                this.indicateursCollection = value;
                            }

                            /**
                             * Gets the value of the ref property.
                             * 
                             */
                            public int getRef() {
                                return ref;
                            }

                            /**
                             * Sets the value of the ref property.
                             * 
                             */
                            public void setRef(int value) {
                                this.ref = value;
                            }

                        }

                    }

                }

            }

        }

    }

}
