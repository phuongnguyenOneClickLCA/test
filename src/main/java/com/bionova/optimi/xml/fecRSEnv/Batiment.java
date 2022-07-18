
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="type_travaux">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="exigences_perf">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="art71">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art72">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art73">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art74">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="titre5" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="art1" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art1_statut" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art2" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art2_statut" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art3" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art3_statut" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="exigences_moyens">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="art16_a" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art16_b" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art16_c" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art16_d" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art16_e" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art17_a" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art17_b" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art18">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art19_a">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art19_b">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art19_c">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art20" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art21" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art22">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art23" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art24" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art25" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art26" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art27" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art28" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art29" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art30" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art31" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art32" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art33" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art34" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art35" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art36" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art37" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art38" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art39" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art40" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art41" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art42" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art43" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art44" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art45" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art30v" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="art32v" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;enumeration value="0"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="enveloppe" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parois_opaques" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="nature" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Is_Vegetalise" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="systeme_constructif" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="8"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="systeme_constructif_autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="epaisseur_isolant" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="resistance_thermique_isolant" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="origine_donnee" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="U_paroi" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="donnant" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
 *                                     &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="type_paroi" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                 &lt;minInclusive value="1"/>
 *                                 &lt;maxInclusive value="4"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="parois_vitrees" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="type_paroi">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="12"/>
 *                                   &lt;minInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type_protection">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="9"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type_menuiserie">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type_vitrage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Ug_vitrage" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="origine_donnee_Ug" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Uw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="origine_donnee_Uw" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="Tlw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="donnant" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="orientation" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="S"/>
 *                                 &lt;enumeration value="O"/>
 *                                 &lt;enumeration value="N"/>
 *                                 &lt;enumeration value="E"/>
 *                                 &lt;enumeration value="H"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="liaisons" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="psi_liaison" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="origine" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="3"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="lineaire" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="donnant" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
 *                                     &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="type_liaison" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="PB"/>
 *                                 &lt;enumeration value="PSI9"/>
 *                                 &lt;enumeration value="PH"/>
 *                                 &lt;enumeration value="REFV"/>
 *                                 &lt;enumeration value="ANG"/>
 *                                 &lt;enumeration value="REFPB"/>
 *                                 &lt;enumeration value="REFPH"/>
 *                                 &lt;enumeration value="LIMENU"/>
 *                                 &lt;enumeration value="AUTRES"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="baies" maxOccurs="5" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="dt_prot_mobile" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="dt_masques_proches" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="dt_masques_lointain" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="orientation" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="S"/>
 *                                 &lt;enumeration value="O"/>
 *                                 &lt;enumeration value="N"/>
 *                                 &lt;enumeration value="E"/>
 *                                 &lt;enumeration value="H"/>
 *                                 &lt;enumeration value="s"/>
 *                                 &lt;enumeration value="o"/>
 *                                 &lt;enumeration value="n"/>
 *                                 &lt;enumeration value="e"/>
 *                                 &lt;enumeration value="h"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="synth_caract_therm_ete" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="surf_totale" maxOccurs="5" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{}t_synth_therm_ete">
 *                                     &lt;attribute name="orientation" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;enumeration value="S"/>
 *                                           &lt;enumeration value="O"/>
 *                                           &lt;enumeration value="N"/>
 *                                           &lt;enumeration value="E"/>
 *                                           &lt;enumeration value="H"/>
 *                                           &lt;enumeration value="s"/>
 *                                           &lt;enumeration value="o"/>
 *                                           &lt;enumeration value="n"/>
 *                                           &lt;enumeration value="e"/>
 *                                           &lt;enumeration value="h"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ete" maxOccurs="5" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="exposes_br1" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="type_protection">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                       &lt;minInclusive value="0"/>
 *                                                       &lt;maxInclusive value="9"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                                 &lt;element name="store_libelle" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="exposes_br2_br3" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="type_protection">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                       &lt;minInclusive value="0"/>
 *                                                       &lt;maxInclusive value="9"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                                 &lt;element name="store_libelle" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="passage" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="type_protection">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                       &lt;minInclusive value="0"/>
 *                                                       &lt;maxInclusive value="9"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                                 &lt;element name="store_libelle" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="autres_br1" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="type_protection">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                       &lt;minInclusive value="0"/>
 *                                                       &lt;maxInclusive value="9"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                                 &lt;element name="store_libelle" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="autres_br2_br3" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="type_protection">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                       &lt;minInclusive value="0"/>
 *                                                       &lt;maxInclusive value="9"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                                                 &lt;element name="store_libelle" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="orientation" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;enumeration value="S"/>
 *                                           &lt;enumeration value="O"/>
 *                                           &lt;enumeration value="N"/>
 *                                           &lt;enumeration value="E"/>
 *                                           &lt;enumeration value="H"/>
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
 *                   &lt;element name="psi_lineaire_moyen" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="surf_totale_pv_portes" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="surf_fac_disp" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pv_install_collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="pv_install" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="onduleur_pv_collection">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="onduleur_pv" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="certification" minOccurs="0">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                       &lt;maxInclusive value="4"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="capteur_pv_collection" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence minOccurs="0">
 *                                                           &lt;element name="capteur_pv" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;all>
 *                                                                     &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                                     &lt;element name="certification" minOccurs="0">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                                           &lt;maxInclusive value="4"/>
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="masques_azi" minOccurs="0">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                                           &lt;maxInclusive value="1"/>
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="masques_vert" minOccurs="0">
 *                                                                       &lt;simpleType>
 *                                                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                                                           &lt;maxInclusive value="1"/>
 *                                                                         &lt;/restriction>
 *                                                                       &lt;/simpleType>
 *                                                                     &lt;/element>
 *                                                                     &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                                     &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                                   &lt;/all>
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
 *         &lt;element name="zone_collection">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}zone" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Ref_Titres" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ref_titres">
 *               &lt;/extension>
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
@XmlRootElement(name = "batiment")
public class Batiment {

    @XmlElement(name = "Index", required = true)
    protected BigInteger index;
    @XmlElement(name = "O_Shon_RT", required = true)
    protected BigDecimal oShonRT;
    @XmlElement(name = "type_travaux", required = true)
    protected BigInteger typeTravaux;
    @XmlElement(name = "exigences_perf", required = true)
    protected Batiment.ExigencesPerf exigencesPerf;
    protected Batiment.Titre5 titre5;
    @XmlElement(name = "exigences_moyens", required = true)
    protected Batiment.ExigencesMoyens exigencesMoyens;
    protected Batiment.Enveloppe enveloppe;
    @XmlElement(name = "pv_install_collection")
    protected Batiment.PvInstallCollection pvInstallCollection;
    @XmlElement(name = "zone_collection", required = true)
    protected Batiment.ZoneCollection zoneCollection;
    @XmlElement(name = "Ref_Titres")
    protected Batiment.RefTitres refTitres;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIndex(BigInteger value) {
        this.index = value;
    }

    /**
     * Gets the value of the oShonRT property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOShonRT() {
        return oShonRT;
    }

    /**
     * Sets the value of the oShonRT property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOShonRT(BigDecimal value) {
        this.oShonRT = value;
    }

    /**
     * Gets the value of the typeTravaux property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTypeTravaux() {
        return typeTravaux;
    }

    /**
     * Sets the value of the typeTravaux property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTypeTravaux(BigInteger value) {
        this.typeTravaux = value;
    }

    /**
     * Gets the value of the exigencesPerf property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.ExigencesPerf }
     *     
     */
    public Batiment.ExigencesPerf getExigencesPerf() {
        return exigencesPerf;
    }

    /**
     * Sets the value of the exigencesPerf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.ExigencesPerf }
     *     
     */
    public void setExigencesPerf(Batiment.ExigencesPerf value) {
        this.exigencesPerf = value;
    }

    /**
     * Gets the value of the titre5 property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.Titre5 }
     *     
     */
    public Batiment.Titre5 getTitre5() {
        return titre5;
    }

    /**
     * Sets the value of the titre5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.Titre5 }
     *     
     */
    public void setTitre5(Batiment.Titre5 value) {
        this.titre5 = value;
    }

    /**
     * Gets the value of the exigencesMoyens property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.ExigencesMoyens }
     *     
     */
    public Batiment.ExigencesMoyens getExigencesMoyens() {
        return exigencesMoyens;
    }

    /**
     * Sets the value of the exigencesMoyens property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.ExigencesMoyens }
     *     
     */
    public void setExigencesMoyens(Batiment.ExigencesMoyens value) {
        this.exigencesMoyens = value;
    }

    /**
     * Gets the value of the enveloppe property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.Enveloppe }
     *     
     */
    public Batiment.Enveloppe getEnveloppe() {
        return enveloppe;
    }

    /**
     * Sets the value of the enveloppe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.Enveloppe }
     *     
     */
    public void setEnveloppe(Batiment.Enveloppe value) {
        this.enveloppe = value;
    }

    /**
     * Gets the value of the pvInstallCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.PvInstallCollection }
     *     
     */
    public Batiment.PvInstallCollection getPvInstallCollection() {
        return pvInstallCollection;
    }

    /**
     * Sets the value of the pvInstallCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.PvInstallCollection }
     *     
     */
    public void setPvInstallCollection(Batiment.PvInstallCollection value) {
        this.pvInstallCollection = value;
    }

    /**
     * Gets the value of the zoneCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.ZoneCollection }
     *     
     */
    public Batiment.ZoneCollection getZoneCollection() {
        return zoneCollection;
    }

    /**
     * Sets the value of the zoneCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.ZoneCollection }
     *     
     */
    public void setZoneCollection(Batiment.ZoneCollection value) {
        this.zoneCollection = value;
    }

    /**
     * Gets the value of the refTitres property.
     * 
     * @return
     *     possible object is
     *     {@link Batiment.RefTitres }
     *     
     */
    public Batiment.RefTitres getRefTitres() {
        return refTitres;
    }

    /**
     * Sets the value of the refTitres property.
     * 
     * @param value
     *     allowed object is
     *     {@link Batiment.RefTitres }
     *     
     */
    public void setRefTitres(Batiment.RefTitres value) {
        this.refTitres = value;
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
     *         &lt;element name="parois_opaques" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="nature" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Is_Vegetalise" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="systeme_constructif" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="8"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="systeme_constructif_autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="epaisseur_isolant" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="resistance_thermique_isolant" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="origine_donnee" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="U_paroi" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="donnant" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
     *                           &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="type_paroi" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                       &lt;minInclusive value="1"/>
     *                       &lt;maxInclusive value="4"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="parois_vitrees" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="type_paroi">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="12"/>
     *                         &lt;minInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type_protection">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="9"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type_menuiserie">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type_vitrage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Ug_vitrage" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="origine_donnee_Ug" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Uw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="origine_donnee_Uw" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="Tlw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="donnant" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="orientation" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="S"/>
     *                       &lt;enumeration value="O"/>
     *                       &lt;enumeration value="N"/>
     *                       &lt;enumeration value="E"/>
     *                       &lt;enumeration value="H"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="liaisons" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="psi_liaison" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="origine" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="3"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="lineaire" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="donnant" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
     *                           &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="type_liaison" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="PB"/>
     *                       &lt;enumeration value="PSI9"/>
     *                       &lt;enumeration value="PH"/>
     *                       &lt;enumeration value="REFV"/>
     *                       &lt;enumeration value="ANG"/>
     *                       &lt;enumeration value="REFPB"/>
     *                       &lt;enumeration value="REFPH"/>
     *                       &lt;enumeration value="LIMENU"/>
     *                       &lt;enumeration value="AUTRES"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="baies" maxOccurs="5" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="dt_prot_mobile" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="dt_masques_proches" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="dt_masques_lointain" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="orientation" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="S"/>
     *                       &lt;enumeration value="O"/>
     *                       &lt;enumeration value="N"/>
     *                       &lt;enumeration value="E"/>
     *                       &lt;enumeration value="H"/>
     *                       &lt;enumeration value="s"/>
     *                       &lt;enumeration value="o"/>
     *                       &lt;enumeration value="n"/>
     *                       &lt;enumeration value="e"/>
     *                       &lt;enumeration value="h"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="synth_caract_therm_ete" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="surf_totale" maxOccurs="5" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{}t_synth_therm_ete">
     *                           &lt;attribute name="orientation" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;enumeration value="S"/>
     *                                 &lt;enumeration value="O"/>
     *                                 &lt;enumeration value="N"/>
     *                                 &lt;enumeration value="E"/>
     *                                 &lt;enumeration value="H"/>
     *                                 &lt;enumeration value="s"/>
     *                                 &lt;enumeration value="o"/>
     *                                 &lt;enumeration value="n"/>
     *                                 &lt;enumeration value="e"/>
     *                                 &lt;enumeration value="h"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="ete" maxOccurs="5" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="exposes_br1" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="type_protection">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                             &lt;minInclusive value="0"/>
     *                                             &lt;maxInclusive value="9"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                                       &lt;element name="store_libelle" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="exposes_br2_br3" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="type_protection">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                             &lt;minInclusive value="0"/>
     *                                             &lt;maxInclusive value="9"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                                       &lt;element name="store_libelle" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="passage" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="type_protection">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                             &lt;minInclusive value="0"/>
     *                                             &lt;maxInclusive value="9"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                                       &lt;element name="store_libelle" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="autres_br1" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="type_protection">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                             &lt;minInclusive value="0"/>
     *                                             &lt;maxInclusive value="9"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                                       &lt;element name="store_libelle" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="autres_br2_br3" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="type_protection">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                             &lt;minInclusive value="0"/>
     *                                             &lt;maxInclusive value="9"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                                       &lt;element name="store_libelle" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="orientation" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;enumeration value="S"/>
     *                                 &lt;enumeration value="O"/>
     *                                 &lt;enumeration value="N"/>
     *                                 &lt;enumeration value="E"/>
     *                                 &lt;enumeration value="H"/>
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
     *         &lt;element name="psi_lineaire_moyen" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="surf_totale_pv_portes" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="surf_fac_disp" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
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
        "paroisOpaques",
        "paroisVitrees",
        "liaisons",
        "baies",
        "synthCaractThermEte",
        "psiLineaireMoyen",
        "surfTotalePvPortes",
        "surfFacDisp"
    })
    public static class Enveloppe {

        @XmlElement(name = "parois_opaques")
        protected List<Batiment.Enveloppe.ParoisOpaques> paroisOpaques;
        @XmlElement(name = "parois_vitrees")
        protected List<Batiment.Enveloppe.ParoisVitrees> paroisVitrees;
        protected List<Batiment.Enveloppe.Liaisons> liaisons;
        protected List<Batiment.Enveloppe.Baies> baies;
        @XmlElement(name = "synth_caract_therm_ete")
        protected Batiment.Enveloppe.SynthCaractThermEte synthCaractThermEte;
        @XmlElement(name = "psi_lineaire_moyen", defaultValue = "0")
        protected BigDecimal psiLineaireMoyen;
        @XmlElement(name = "surf_totale_pv_portes", defaultValue = "0")
        protected BigDecimal surfTotalePvPortes;
        @XmlElement(name = "surf_fac_disp", defaultValue = "0")
        protected BigDecimal surfFacDisp;

        /**
         * Gets the value of the paroisOpaques property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the paroisOpaques property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParoisOpaques().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Batiment.Enveloppe.ParoisOpaques }
         * 
         * 
         */
        public List<Batiment.Enveloppe.ParoisOpaques> getParoisOpaques() {
            if (paroisOpaques == null) {
                paroisOpaques = new ArrayList<Batiment.Enveloppe.ParoisOpaques>();
            }
            return this.paroisOpaques;
        }

        /**
         * Gets the value of the paroisVitrees property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the paroisVitrees property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParoisVitrees().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Batiment.Enveloppe.ParoisVitrees }
         * 
         * 
         */
        public List<Batiment.Enveloppe.ParoisVitrees> getParoisVitrees() {
            if (paroisVitrees == null) {
                paroisVitrees = new ArrayList<Batiment.Enveloppe.ParoisVitrees>();
            }
            return this.paroisVitrees;
        }

        /**
         * Gets the value of the liaisons property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the liaisons property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLiaisons().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Batiment.Enveloppe.Liaisons }
         * 
         * 
         */
        public List<Batiment.Enveloppe.Liaisons> getLiaisons() {
            if (liaisons == null) {
                liaisons = new ArrayList<Batiment.Enveloppe.Liaisons>();
            }
            return this.liaisons;
        }

        /**
         * Gets the value of the baies property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the baies property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBaies().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Batiment.Enveloppe.Baies }
         * 
         * 
         */
        public List<Batiment.Enveloppe.Baies> getBaies() {
            if (baies == null) {
                baies = new ArrayList<Batiment.Enveloppe.Baies>();
            }
            return this.baies;
        }

        /**
         * Gets the value of the synthCaractThermEte property.
         * 
         * @return
         *     possible object is
         *     {@link Batiment.Enveloppe.SynthCaractThermEte }
         *     
         */
        public Batiment.Enveloppe.SynthCaractThermEte getSynthCaractThermEte() {
            return synthCaractThermEte;
        }

        /**
         * Sets the value of the synthCaractThermEte property.
         * 
         * @param value
         *     allowed object is
         *     {@link Batiment.Enveloppe.SynthCaractThermEte }
         *     
         */
        public void setSynthCaractThermEte(Batiment.Enveloppe.SynthCaractThermEte value) {
            this.synthCaractThermEte = value;
        }

        /**
         * Gets the value of the psiLineaireMoyen property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPsiLineaireMoyen() {
            return psiLineaireMoyen;
        }

        /**
         * Sets the value of the psiLineaireMoyen property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPsiLineaireMoyen(BigDecimal value) {
            this.psiLineaireMoyen = value;
        }

        /**
         * Gets the value of the surfTotalePvPortes property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSurfTotalePvPortes() {
            return surfTotalePvPortes;
        }

        /**
         * Sets the value of the surfTotalePvPortes property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSurfTotalePvPortes(BigDecimal value) {
            this.surfTotalePvPortes = value;
        }

        /**
         * Gets the value of the surfFacDisp property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSurfFacDisp() {
            return surfFacDisp;
        }

        /**
         * Sets the value of the surfFacDisp property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSurfFacDisp(BigDecimal value) {
            this.surfFacDisp = value;
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
         *         &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="dt_prot_mobile" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="dt_masques_proches" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="dt_masques_lointain" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="orientation" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="S"/>
         *             &lt;enumeration value="O"/>
         *             &lt;enumeration value="N"/>
         *             &lt;enumeration value="E"/>
         *             &lt;enumeration value="H"/>
         *             &lt;enumeration value="s"/>
         *             &lt;enumeration value="o"/>
         *             &lt;enumeration value="n"/>
         *             &lt;enumeration value="e"/>
         *             &lt;enumeration value="h"/>
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
            "surfaceTotale",
            "dtProtMobile",
            "dtMasquesProches",
            "dtMasquesLointain"
        })
        public static class Baies {

            @XmlElement(name = "surface_totale", defaultValue = "0")
            protected BigDecimal surfaceTotale;
            @XmlElement(name = "dt_prot_mobile", defaultValue = "0")
            protected BigDecimal dtProtMobile;
            @XmlElement(name = "dt_masques_proches", defaultValue = "0")
            protected BigDecimal dtMasquesProches;
            @XmlElement(name = "dt_masques_lointain", defaultValue = "0")
            protected BigDecimal dtMasquesLointain;
            @XmlAttribute(name = "orientation", required = true)
            protected String orientation;

            /**
             * Gets the value of the surfaceTotale property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSurfaceTotale() {
                return surfaceTotale;
            }

            /**
             * Sets the value of the surfaceTotale property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSurfaceTotale(BigDecimal value) {
                this.surfaceTotale = value;
            }

            /**
             * Gets the value of the dtProtMobile property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDtProtMobile() {
                return dtProtMobile;
            }

            /**
             * Sets the value of the dtProtMobile property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDtProtMobile(BigDecimal value) {
                this.dtProtMobile = value;
            }

            /**
             * Gets the value of the dtMasquesProches property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDtMasquesProches() {
                return dtMasquesProches;
            }

            /**
             * Sets the value of the dtMasquesProches property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDtMasquesProches(BigDecimal value) {
                this.dtMasquesProches = value;
            }

            /**
             * Gets the value of the dtMasquesLointain property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDtMasquesLointain() {
                return dtMasquesLointain;
            }

            /**
             * Sets the value of the dtMasquesLointain property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDtMasquesLointain(BigDecimal value) {
                this.dtMasquesLointain = value;
            }

            /**
             * Gets the value of the orientation property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOrientation() {
                return orientation;
            }

            /**
             * Sets the value of the orientation property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOrientation(String value) {
                this.orientation = value;
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
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="psi_liaison" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="origine" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="3"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="lineaire" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="donnant" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
         *                 &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="type_liaison" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="PB"/>
         *             &lt;enumeration value="PSI9"/>
         *             &lt;enumeration value="PH"/>
         *             &lt;enumeration value="REFV"/>
         *             &lt;enumeration value="ANG"/>
         *             &lt;enumeration value="REFPB"/>
         *             &lt;enumeration value="REFPH"/>
         *             &lt;enumeration value="LIMENU"/>
         *             &lt;enumeration value="AUTRES"/>
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
            "name",
            "psiLiaison",
            "origine",
            "lineaire",
            "donnant"
        })
        public static class Liaisons {

            protected String name;
            @XmlElement(name = "psi_liaison", defaultValue = "0")
            protected BigDecimal psiLiaison;
            @XmlElement(defaultValue = "0")
            protected BigInteger origine;
            @XmlElement(defaultValue = "0")
            protected BigDecimal lineaire;
            @XmlElement(defaultValue = "0")
            protected Batiment.Enveloppe.Liaisons.Donnant donnant;
            @XmlAttribute(name = "type_liaison", required = true)
            protected String typeLiaison;

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the psiLiaison property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPsiLiaison() {
                return psiLiaison;
            }

            /**
             * Sets the value of the psiLiaison property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPsiLiaison(BigDecimal value) {
                this.psiLiaison = value;
            }

            /**
             * Gets the value of the origine property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOrigine() {
                return origine;
            }

            /**
             * Sets the value of the origine property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOrigine(BigInteger value) {
                this.origine = value;
            }

            /**
             * Gets the value of the lineaire property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getLineaire() {
                return lineaire;
            }

            /**
             * Sets the value of the lineaire property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setLineaire(BigDecimal value) {
                this.lineaire = value;
            }

            /**
             * Gets the value of the donnant property.
             * 
             * @return
             *     possible object is
             *     {@link Batiment.Enveloppe.Liaisons.Donnant }
             *     
             */
            public Batiment.Enveloppe.Liaisons.Donnant getDonnant() {
                return donnant;
            }

            /**
             * Sets the value of the donnant property.
             * 
             * @param value
             *     allowed object is
             *     {@link Batiment.Enveloppe.Liaisons.Donnant }
             *     
             */
            public void setDonnant(Batiment.Enveloppe.Liaisons.Donnant value) {
                this.donnant = value;
            }

            /**
             * Gets the value of the typeLiaison property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTypeLiaison() {
                return typeLiaison;
            }

            /**
             * Sets the value of the typeLiaison property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTypeLiaison(String value) {
                this.typeLiaison = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
             *       &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Donnant {

                @XmlValue
                protected BigInteger value;
                @XmlAttribute(name = "b")
                protected BigDecimal b;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setValue(BigInteger value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the b property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getB() {
                    if (b == null) {
                        return new BigDecimal("0");
                    } else {
                        return b;
                    }
                }

                /**
                 * Sets the value of the b property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setB(BigDecimal value) {
                    this.b = value;
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
         *         &lt;element name="nature" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Is_Vegetalise" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="systeme_constructif" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="8"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="systeme_constructif_autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="epaisseur_isolant" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="resistance_thermique_isolant" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="origine_donnee" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="U_paroi" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="donnant" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
         *                 &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="type_paroi" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *             &lt;minInclusive value="1"/>
         *             &lt;maxInclusive value="4"/>
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
            "nature",
            "name",
            "isVegetalise",
            "systemeConstructif",
            "systemeConstructifAutre",
            "epaisseurIsolant",
            "resistanceThermiqueIsolant",
            "origineDonnee",
            "uParoi",
            "surfaceTotale",
            "donnant"
        })
        public static class ParoisOpaques {

            @XmlElement(required = true)
            protected BigInteger nature;
            protected String name;
            @XmlElement(name = "Is_Vegetalise", defaultValue = "0")
            protected Integer isVegetalise;
            @XmlElement(name = "systeme_constructif", defaultValue = "0")
            protected BigInteger systemeConstructif;
            @XmlElement(name = "systeme_constructif_autre", defaultValue = "0")
            protected String systemeConstructifAutre;
            @XmlElement(name = "epaisseur_isolant", defaultValue = "0")
            protected BigDecimal epaisseurIsolant;
            @XmlElement(name = "resistance_thermique_isolant", defaultValue = "0")
            protected BigDecimal resistanceThermiqueIsolant;
            @XmlElement(name = "origine_donnee", defaultValue = "0")
            protected BigInteger origineDonnee;
            @XmlElement(name = "U_paroi", defaultValue = "0")
            protected BigDecimal uParoi;
            @XmlElement(name = "surface_totale", defaultValue = "0")
            protected BigDecimal surfaceTotale;
            @XmlElement(defaultValue = "0")
            protected Batiment.Enveloppe.ParoisOpaques.Donnant donnant;
            @XmlAttribute(name = "type_paroi", required = true)
            protected int typeParoi;

            /**
             * Gets the value of the nature property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getNature() {
                return nature;
            }

            /**
             * Sets the value of the nature property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setNature(BigInteger value) {
                this.nature = value;
            }

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the isVegetalise property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getIsVegetalise() {
                return isVegetalise;
            }

            /**
             * Sets the value of the isVegetalise property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setIsVegetalise(Integer value) {
                this.isVegetalise = value;
            }

            /**
             * Gets the value of the systemeConstructif property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getSystemeConstructif() {
                return systemeConstructif;
            }

            /**
             * Sets the value of the systemeConstructif property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setSystemeConstructif(BigInteger value) {
                this.systemeConstructif = value;
            }

            /**
             * Gets the value of the systemeConstructifAutre property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSystemeConstructifAutre() {
                return systemeConstructifAutre;
            }

            /**
             * Sets the value of the systemeConstructifAutre property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSystemeConstructifAutre(String value) {
                this.systemeConstructifAutre = value;
            }

            /**
             * Gets the value of the epaisseurIsolant property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getEpaisseurIsolant() {
                return epaisseurIsolant;
            }

            /**
             * Sets the value of the epaisseurIsolant property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setEpaisseurIsolant(BigDecimal value) {
                this.epaisseurIsolant = value;
            }

            /**
             * Gets the value of the resistanceThermiqueIsolant property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getResistanceThermiqueIsolant() {
                return resistanceThermiqueIsolant;
            }

            /**
             * Sets the value of the resistanceThermiqueIsolant property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setResistanceThermiqueIsolant(BigDecimal value) {
                this.resistanceThermiqueIsolant = value;
            }

            /**
             * Gets the value of the origineDonnee property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOrigineDonnee() {
                return origineDonnee;
            }

            /**
             * Sets the value of the origineDonnee property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOrigineDonnee(BigInteger value) {
                this.origineDonnee = value;
            }

            /**
             * Gets the value of the uParoi property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getUParoi() {
                return uParoi;
            }

            /**
             * Sets the value of the uParoi property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setUParoi(BigDecimal value) {
                this.uParoi = value;
            }

            /**
             * Gets the value of the surfaceTotale property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSurfaceTotale() {
                return surfaceTotale;
            }

            /**
             * Sets the value of the surfaceTotale property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSurfaceTotale(BigDecimal value) {
                this.surfaceTotale = value;
            }

            /**
             * Gets the value of the donnant property.
             * 
             * @return
             *     possible object is
             *     {@link Batiment.Enveloppe.ParoisOpaques.Donnant }
             *     
             */
            public Batiment.Enveloppe.ParoisOpaques.Donnant getDonnant() {
                return donnant;
            }

            /**
             * Sets the value of the donnant property.
             * 
             * @param value
             *     allowed object is
             *     {@link Batiment.Enveloppe.ParoisOpaques.Donnant }
             *     
             */
            public void setDonnant(Batiment.Enveloppe.ParoisOpaques.Donnant value) {
                this.donnant = value;
            }

            /**
             * Gets the value of the typeParoi property.
             * 
             */
            public int getTypeParoi() {
                return typeParoi;
            }

            /**
             * Sets the value of the typeParoi property.
             * 
             */
            public void setTypeParoi(int value) {
                this.typeParoi = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
             *       &lt;attribute name="b" type="{http://www.w3.org/2001/XMLSchema}decimal" default="0" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Donnant {

                @XmlValue
                protected BigInteger value;
                @XmlAttribute(name = "b")
                protected BigDecimal b;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setValue(BigInteger value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the b property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getB() {
                    if (b == null) {
                        return new BigDecimal("0");
                    } else {
                        return b;
                    }
                }

                /**
                 * Sets the value of the b property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setB(BigDecimal value) {
                    this.b = value;
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
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="type_paroi">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="12"/>
         *               &lt;minInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type_protection">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="9"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type_menuiserie">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type_vitrage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Ug_vitrage" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="origine_donnee_Ug" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Uw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="origine_donnee_Uw" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="Tlw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="surface_totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="donnant" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="orientation" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="S"/>
         *             &lt;enumeration value="O"/>
         *             &lt;enumeration value="N"/>
         *             &lt;enumeration value="E"/>
         *             &lt;enumeration value="H"/>
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
            "name",
            "typeParoi",
            "typeProtection",
            "typeMenuiserie",
            "typeVitrage",
            "ugVitrage",
            "origineDonneeUg",
            "uw",
            "origineDonneeUw",
            "sw",
            "tlw",
            "surfaceTotale",
            "donnant"
        })
        public static class ParoisVitrees {

            protected String name;
            @XmlElement(name = "type_paroi")
            protected int typeParoi;
            @XmlElement(name = "type_protection")
            protected int typeProtection;
            @XmlElement(name = "type_menuiserie")
            protected int typeMenuiserie;
            @XmlElement(name = "type_vitrage")
            protected String typeVitrage;
            @XmlElement(name = "Ug_vitrage", defaultValue = "0")
            protected BigDecimal ugVitrage;
            @XmlElement(name = "origine_donnee_Ug", defaultValue = "0")
            protected BigInteger origineDonneeUg;
            @XmlElement(name = "Uw", defaultValue = "0")
            protected BigDecimal uw;
            @XmlElement(name = "origine_donnee_Uw", defaultValue = "0")
            protected BigInteger origineDonneeUw;
            @XmlElement(name = "Sw", defaultValue = "0")
            protected BigDecimal sw;
            @XmlElement(name = "Tlw", defaultValue = "0")
            protected BigDecimal tlw;
            @XmlElement(name = "surface_totale", defaultValue = "0")
            protected BigDecimal surfaceTotale;
            @XmlElement(defaultValue = "0")
            protected BigInteger donnant;
            @XmlAttribute(name = "orientation", required = true)
            protected String orientation;

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the typeParoi property.
             * 
             */
            public int getTypeParoi() {
                return typeParoi;
            }

            /**
             * Sets the value of the typeParoi property.
             * 
             */
            public void setTypeParoi(int value) {
                this.typeParoi = value;
            }

            /**
             * Gets the value of the typeProtection property.
             * 
             */
            public int getTypeProtection() {
                return typeProtection;
            }

            /**
             * Sets the value of the typeProtection property.
             * 
             */
            public void setTypeProtection(int value) {
                this.typeProtection = value;
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
             * Gets the value of the typeVitrage property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTypeVitrage() {
                return typeVitrage;
            }

            /**
             * Sets the value of the typeVitrage property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTypeVitrage(String value) {
                this.typeVitrage = value;
            }

            /**
             * Gets the value of the ugVitrage property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getUgVitrage() {
                return ugVitrage;
            }

            /**
             * Sets the value of the ugVitrage property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setUgVitrage(BigDecimal value) {
                this.ugVitrage = value;
            }

            /**
             * Gets the value of the origineDonneeUg property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOrigineDonneeUg() {
                return origineDonneeUg;
            }

            /**
             * Sets the value of the origineDonneeUg property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOrigineDonneeUg(BigInteger value) {
                this.origineDonneeUg = value;
            }

            /**
             * Gets the value of the uw property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getUw() {
                return uw;
            }

            /**
             * Sets the value of the uw property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setUw(BigDecimal value) {
                this.uw = value;
            }

            /**
             * Gets the value of the origineDonneeUw property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOrigineDonneeUw() {
                return origineDonneeUw;
            }

            /**
             * Sets the value of the origineDonneeUw property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOrigineDonneeUw(BigInteger value) {
                this.origineDonneeUw = value;
            }

            /**
             * Gets the value of the sw property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSw() {
                return sw;
            }

            /**
             * Sets the value of the sw property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSw(BigDecimal value) {
                this.sw = value;
            }

            /**
             * Gets the value of the tlw property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getTlw() {
                return tlw;
            }

            /**
             * Sets the value of the tlw property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setTlw(BigDecimal value) {
                this.tlw = value;
            }

            /**
             * Gets the value of the surfaceTotale property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSurfaceTotale() {
                return surfaceTotale;
            }

            /**
             * Sets the value of the surfaceTotale property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSurfaceTotale(BigDecimal value) {
                this.surfaceTotale = value;
            }

            /**
             * Gets the value of the donnant property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getDonnant() {
                return donnant;
            }

            /**
             * Sets the value of the donnant property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setDonnant(BigInteger value) {
                this.donnant = value;
            }

            /**
             * Gets the value of the orientation property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOrientation() {
                return orientation;
            }

            /**
             * Sets the value of the orientation property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOrientation(String value) {
                this.orientation = value;
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
         *         &lt;element name="surf_totale" maxOccurs="5" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{}t_synth_therm_ete">
         *                 &lt;attribute name="orientation" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;enumeration value="S"/>
         *                       &lt;enumeration value="O"/>
         *                       &lt;enumeration value="N"/>
         *                       &lt;enumeration value="E"/>
         *                       &lt;enumeration value="H"/>
         *                       &lt;enumeration value="s"/>
         *                       &lt;enumeration value="o"/>
         *                       &lt;enumeration value="n"/>
         *                       &lt;enumeration value="e"/>
         *                       &lt;enumeration value="h"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ete" maxOccurs="5" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="exposes_br1" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="type_protection">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                   &lt;minInclusive value="0"/>
         *                                   &lt;maxInclusive value="9"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                             &lt;element name="store_libelle" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="exposes_br2_br3" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="type_protection">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                   &lt;minInclusive value="0"/>
         *                                   &lt;maxInclusive value="9"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                             &lt;element name="store_libelle" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="passage" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="type_protection">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                   &lt;minInclusive value="0"/>
         *                                   &lt;maxInclusive value="9"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                             &lt;element name="store_libelle" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="autres_br1" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="type_protection">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                   &lt;minInclusive value="0"/>
         *                                   &lt;maxInclusive value="9"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                             &lt;element name="store_libelle" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="autres_br2_br3" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="type_protection">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                   &lt;minInclusive value="0"/>
         *                                   &lt;maxInclusive value="9"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *                             &lt;element name="store_libelle" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="orientation" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;enumeration value="S"/>
         *                       &lt;enumeration value="O"/>
         *                       &lt;enumeration value="N"/>
         *                       &lt;enumeration value="E"/>
         *                       &lt;enumeration value="H"/>
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
            "surfTotale",
            "ete"
        })
        public static class SynthCaractThermEte {

            @XmlElement(name = "surf_totale")
            protected List<Batiment.Enveloppe.SynthCaractThermEte.SurfTotale> surfTotale;
            protected List<Batiment.Enveloppe.SynthCaractThermEte.Ete> ete;

            /**
             * Gets the value of the surfTotale property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the surfTotale property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSurfTotale().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Batiment.Enveloppe.SynthCaractThermEte.SurfTotale }
             * 
             * 
             */
            public List<Batiment.Enveloppe.SynthCaractThermEte.SurfTotale> getSurfTotale() {
                if (surfTotale == null) {
                    surfTotale = new ArrayList<Batiment.Enveloppe.SynthCaractThermEte.SurfTotale>();
                }
                return this.surfTotale;
            }

            /**
             * Gets the value of the ete property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the ete property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getEte().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Batiment.Enveloppe.SynthCaractThermEte.Ete }
             * 
             * 
             */
            public List<Batiment.Enveloppe.SynthCaractThermEte.Ete> getEte() {
                if (ete == null) {
                    ete = new ArrayList<Batiment.Enveloppe.SynthCaractThermEte.Ete>();
                }
                return this.ete;
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
             *         &lt;element name="exposes_br1" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="type_protection">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                         &lt;minInclusive value="0"/>
             *                         &lt;maxInclusive value="9"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="store_libelle" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="exposes_br2_br3" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="type_protection">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                         &lt;minInclusive value="0"/>
             *                         &lt;maxInclusive value="9"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="store_libelle" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="passage" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="type_protection">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                         &lt;minInclusive value="0"/>
             *                         &lt;maxInclusive value="9"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="store_libelle" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="autres_br1" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="type_protection">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                         &lt;minInclusive value="0"/>
             *                         &lt;maxInclusive value="9"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="store_libelle" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="autres_br2_br3" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="type_protection">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                         &lt;minInclusive value="0"/>
             *                         &lt;maxInclusive value="9"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
             *                   &lt;element name="store_libelle" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="orientation" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;enumeration value="S"/>
             *             &lt;enumeration value="O"/>
             *             &lt;enumeration value="N"/>
             *             &lt;enumeration value="E"/>
             *             &lt;enumeration value="H"/>
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
                "exposesBr1",
                "exposesBr2Br3",
                "passage",
                "autresBr1",
                "autresBr2Br3"
            })
            public static class Ete {

                @XmlElement(name = "exposes_br1")
                protected Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 exposesBr1;
                @XmlElement(name = "exposes_br2_br3")
                protected Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 exposesBr2Br3;
                protected Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage passage;
                @XmlElement(name = "autres_br1")
                protected Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 autresBr1;
                @XmlElement(name = "autres_br2_br3")
                protected Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 autresBr2Br3;
                @XmlAttribute(name = "orientation", required = true)
                protected String orientation;

                /**
                 * Gets the value of the exposesBr1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 }
                 *     
                 */
                public Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 getExposesBr1() {
                    return exposesBr1;
                }

                /**
                 * Sets the value of the exposesBr1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 }
                 *     
                 */
                public void setExposesBr1(Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 value) {
                    this.exposesBr1 = value;
                }

                /**
                 * Gets the value of the exposesBr2Br3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 }
                 *     
                 */
                public Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 getExposesBr2Br3() {
                    return exposesBr2Br3;
                }

                /**
                 * Sets the value of the exposesBr2Br3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 }
                 *     
                 */
                public void setExposesBr2Br3(Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 value) {
                    this.exposesBr2Br3 = value;
                }

                /**
                 * Gets the value of the passage property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage }
                 *     
                 */
                public Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage getPassage() {
                    return passage;
                }

                /**
                 * Sets the value of the passage property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage }
                 *     
                 */
                public void setPassage(Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage value) {
                    this.passage = value;
                }

                /**
                 * Gets the value of the autresBr1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 }
                 *     
                 */
                public Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 getAutresBr1() {
                    return autresBr1;
                }

                /**
                 * Sets the value of the autresBr1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 }
                 *     
                 */
                public void setAutresBr1(Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 value) {
                    this.autresBr1 = value;
                }

                /**
                 * Gets the value of the autresBr2Br3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 }
                 *     
                 */
                public Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 getAutresBr2Br3() {
                    return autresBr2Br3;
                }

                /**
                 * Sets the value of the autresBr2Br3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 }
                 *     
                 */
                public void setAutresBr2Br3(Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 value) {
                    this.autresBr2Br3 = value;
                }

                /**
                 * Gets the value of the orientation property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOrientation() {
                    return orientation;
                }

                /**
                 * Sets the value of the orientation property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOrientation(String value) {
                    this.orientation = value;
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
                 *         &lt;element name="type_protection">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *               &lt;minInclusive value="0"/>
                 *               &lt;maxInclusive value="9"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="store_libelle" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
                    "typeProtection",
                    "sw",
                    "storeLibelle",
                    "idZone"
                })
                public static class AutresBr1 {

                    @XmlElement(name = "type_protection")
                    protected int typeProtection;
                    @XmlElement(name = "Sw", defaultValue = "0")
                    protected BigDecimal sw;
                    @XmlElement(name = "store_libelle")
                    protected Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 .StoreLibelle storeLibelle;
                    @XmlElement(name = "id_zone", required = true, defaultValue = "0")
                    protected BigInteger idZone;

                    /**
                     * Gets the value of the typeProtection property.
                     * 
                     */
                    public int getTypeProtection() {
                        return typeProtection;
                    }

                    /**
                     * Sets the value of the typeProtection property.
                     * 
                     */
                    public void setTypeProtection(int value) {
                        this.typeProtection = value;
                    }

                    /**
                     * Gets the value of the sw property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getSw() {
                        return sw;
                    }

                    /**
                     * Sets the value of the sw property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setSw(BigDecimal value) {
                        this.sw = value;
                    }

                    /**
                     * Gets the value of the storeLibelle property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 .StoreLibelle }
                     *     
                     */
                    public Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 .StoreLibelle getStoreLibelle() {
                        return storeLibelle;
                    }

                    /**
                     * Sets the value of the storeLibelle property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 .StoreLibelle }
                     *     
                     */
                    public void setStoreLibelle(Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr1 .StoreLibelle value) {
                        this.storeLibelle = value;
                    }

                    /**
                     * Gets the value of the idZone property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getIdZone() {
                        return idZone;
                    }

                    /**
                     * Sets the value of the idZone property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setIdZone(BigInteger value) {
                        this.idZone = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class StoreLibelle {

                        @XmlValue
                        protected String value;

                        /**
                         * Gets the value of the value property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Sets the value of the value property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setValue(String value) {
                            this.value = value;
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
                 *         &lt;element name="type_protection">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *               &lt;minInclusive value="0"/>
                 *               &lt;maxInclusive value="9"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="store_libelle" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
                    "typeProtection",
                    "sw",
                    "storeLibelle",
                    "idZone"
                })
                public static class AutresBr2Br3 {

                    @XmlElement(name = "type_protection")
                    protected int typeProtection;
                    @XmlElement(name = "Sw", defaultValue = "0")
                    protected BigDecimal sw;
                    @XmlElement(name = "store_libelle")
                    protected Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 .StoreLibelle storeLibelle;
                    @XmlElement(name = "id_zone", required = true, defaultValue = "0")
                    protected BigInteger idZone;

                    /**
                     * Gets the value of the typeProtection property.
                     * 
                     */
                    public int getTypeProtection() {
                        return typeProtection;
                    }

                    /**
                     * Sets the value of the typeProtection property.
                     * 
                     */
                    public void setTypeProtection(int value) {
                        this.typeProtection = value;
                    }

                    /**
                     * Gets the value of the sw property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getSw() {
                        return sw;
                    }

                    /**
                     * Sets the value of the sw property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setSw(BigDecimal value) {
                        this.sw = value;
                    }

                    /**
                     * Gets the value of the storeLibelle property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 .StoreLibelle }
                     *     
                     */
                    public Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 .StoreLibelle getStoreLibelle() {
                        return storeLibelle;
                    }

                    /**
                     * Sets the value of the storeLibelle property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 .StoreLibelle }
                     *     
                     */
                    public void setStoreLibelle(Batiment.Enveloppe.SynthCaractThermEte.Ete.AutresBr2Br3 .StoreLibelle value) {
                        this.storeLibelle = value;
                    }

                    /**
                     * Gets the value of the idZone property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getIdZone() {
                        return idZone;
                    }

                    /**
                     * Sets the value of the idZone property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setIdZone(BigInteger value) {
                        this.idZone = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class StoreLibelle {

                        @XmlValue
                        protected String value;

                        /**
                         * Gets the value of the value property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Sets the value of the value property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setValue(String value) {
                            this.value = value;
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
                 *         &lt;element name="type_protection">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *               &lt;minInclusive value="0"/>
                 *               &lt;maxInclusive value="9"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="store_libelle" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
                    "typeProtection",
                    "sw",
                    "storeLibelle",
                    "idZone"
                })
                public static class ExposesBr1 {

                    @XmlElement(name = "type_protection")
                    protected int typeProtection;
                    @XmlElement(name = "Sw", defaultValue = "0")
                    protected BigDecimal sw;
                    @XmlElement(name = "store_libelle")
                    protected Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 .StoreLibelle storeLibelle;
                    @XmlElement(name = "id_zone", required = true, defaultValue = "0")
                    protected BigInteger idZone;

                    /**
                     * Gets the value of the typeProtection property.
                     * 
                     */
                    public int getTypeProtection() {
                        return typeProtection;
                    }

                    /**
                     * Sets the value of the typeProtection property.
                     * 
                     */
                    public void setTypeProtection(int value) {
                        this.typeProtection = value;
                    }

                    /**
                     * Gets the value of the sw property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getSw() {
                        return sw;
                    }

                    /**
                     * Sets the value of the sw property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setSw(BigDecimal value) {
                        this.sw = value;
                    }

                    /**
                     * Gets the value of the storeLibelle property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 .StoreLibelle }
                     *     
                     */
                    public Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 .StoreLibelle getStoreLibelle() {
                        return storeLibelle;
                    }

                    /**
                     * Sets the value of the storeLibelle property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 .StoreLibelle }
                     *     
                     */
                    public void setStoreLibelle(Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr1 .StoreLibelle value) {
                        this.storeLibelle = value;
                    }

                    /**
                     * Gets the value of the idZone property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getIdZone() {
                        return idZone;
                    }

                    /**
                     * Sets the value of the idZone property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setIdZone(BigInteger value) {
                        this.idZone = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class StoreLibelle {

                        @XmlValue
                        protected String value;

                        /**
                         * Gets the value of the value property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Sets the value of the value property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setValue(String value) {
                            this.value = value;
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
                 *         &lt;element name="type_protection">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *               &lt;minInclusive value="0"/>
                 *               &lt;maxInclusive value="9"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="store_libelle" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
                    "typeProtection",
                    "sw",
                    "storeLibelle",
                    "idZone"
                })
                public static class ExposesBr2Br3 {

                    @XmlElement(name = "type_protection")
                    protected int typeProtection;
                    @XmlElement(name = "Sw", defaultValue = "0")
                    protected BigDecimal sw;
                    @XmlElement(name = "store_libelle")
                    protected Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 .StoreLibelle storeLibelle;
                    @XmlElement(name = "id_zone", required = true, defaultValue = "0")
                    protected BigInteger idZone;

                    /**
                     * Gets the value of the typeProtection property.
                     * 
                     */
                    public int getTypeProtection() {
                        return typeProtection;
                    }

                    /**
                     * Sets the value of the typeProtection property.
                     * 
                     */
                    public void setTypeProtection(int value) {
                        this.typeProtection = value;
                    }

                    /**
                     * Gets the value of the sw property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getSw() {
                        return sw;
                    }

                    /**
                     * Sets the value of the sw property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setSw(BigDecimal value) {
                        this.sw = value;
                    }

                    /**
                     * Gets the value of the storeLibelle property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 .StoreLibelle }
                     *     
                     */
                    public Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 .StoreLibelle getStoreLibelle() {
                        return storeLibelle;
                    }

                    /**
                     * Sets the value of the storeLibelle property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 .StoreLibelle }
                     *     
                     */
                    public void setStoreLibelle(Batiment.Enveloppe.SynthCaractThermEte.Ete.ExposesBr2Br3 .StoreLibelle value) {
                        this.storeLibelle = value;
                    }

                    /**
                     * Gets the value of the idZone property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getIdZone() {
                        return idZone;
                    }

                    /**
                     * Sets the value of the idZone property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setIdZone(BigInteger value) {
                        this.idZone = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class StoreLibelle {

                        @XmlValue
                        protected String value;

                        /**
                         * Gets the value of the value property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Sets the value of the value property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setValue(String value) {
                            this.value = value;
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
                 *         &lt;element name="type_protection">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *               &lt;minInclusive value="0"/>
                 *               &lt;maxInclusive value="9"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="Sw" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
                 *         &lt;element name="store_libelle" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="id_zone" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
                    "typeProtection",
                    "sw",
                    "storeLibelle",
                    "idZone"
                })
                public static class Passage {

                    @XmlElement(name = "type_protection")
                    protected int typeProtection;
                    @XmlElement(name = "Sw", defaultValue = "0")
                    protected BigDecimal sw;
                    @XmlElement(name = "store_libelle")
                    protected Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage.StoreLibelle storeLibelle;
                    @XmlElement(name = "id_zone", required = true, defaultValue = "0")
                    protected BigInteger idZone;

                    /**
                     * Gets the value of the typeProtection property.
                     * 
                     */
                    public int getTypeProtection() {
                        return typeProtection;
                    }

                    /**
                     * Sets the value of the typeProtection property.
                     * 
                     */
                    public void setTypeProtection(int value) {
                        this.typeProtection = value;
                    }

                    /**
                     * Gets the value of the sw property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getSw() {
                        return sw;
                    }

                    /**
                     * Sets the value of the sw property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setSw(BigDecimal value) {
                        this.sw = value;
                    }

                    /**
                     * Gets the value of the storeLibelle property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage.StoreLibelle }
                     *     
                     */
                    public Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage.StoreLibelle getStoreLibelle() {
                        return storeLibelle;
                    }

                    /**
                     * Sets the value of the storeLibelle property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage.StoreLibelle }
                     *     
                     */
                    public void setStoreLibelle(Batiment.Enveloppe.SynthCaractThermEte.Ete.Passage.StoreLibelle value) {
                        this.storeLibelle = value;
                    }

                    /**
                     * Gets the value of the idZone property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getIdZone() {
                        return idZone;
                    }

                    /**
                     * Sets the value of the idZone property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setIdZone(BigInteger value) {
                        this.idZone = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class StoreLibelle {

                        @XmlValue
                        protected String value;

                        /**
                         * Gets the value of the value property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Sets the value of the value property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setValue(String value) {
                            this.value = value;
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
             *     &lt;extension base="{}t_synth_therm_ete">
             *       &lt;attribute name="orientation" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;enumeration value="S"/>
             *             &lt;enumeration value="O"/>
             *             &lt;enumeration value="N"/>
             *             &lt;enumeration value="E"/>
             *             &lt;enumeration value="H"/>
             *             &lt;enumeration value="s"/>
             *             &lt;enumeration value="o"/>
             *             &lt;enumeration value="n"/>
             *             &lt;enumeration value="e"/>
             *             &lt;enumeration value="h"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class SurfTotale
                extends TSynthThermEte
            {

                @XmlAttribute(name = "orientation", required = true)
                protected String orientation;

                /**
                 * Gets the value of the orientation property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOrientation() {
                    return orientation;
                }

                /**
                 * Sets the value of the orientation property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOrientation(String value) {
                    this.orientation = value;
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
     *         &lt;element name="art16_a" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art16_b" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art16_c" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art16_d" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art16_e" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art17_a" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art17_b" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art18">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art19_a">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art19_b">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art19_c">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art20" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art21" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art22">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art23" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art24" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art25" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art26" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art27" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art28" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art29" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art30" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art31" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art32" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art33" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art34" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art35" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art36" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art37" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art38" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art39" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art40" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art41" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art42" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art43" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art44" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art45" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art30v" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art32v" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
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
    public static class ExigencesMoyens {

        @XmlElement(name = "art16_a")
        protected Integer art16A;
        @XmlElement(name = "art16_b")
        protected Integer art16B;
        @XmlElement(name = "art16_c")
        protected Integer art16C;
        @XmlElement(name = "art16_d")
        protected Integer art16D;
        @XmlElement(name = "art16_e")
        protected Integer art16E;
        @XmlElement(name = "art17_a")
        protected Integer art17A;
        @XmlElement(name = "art17_b")
        protected Integer art17B;
        protected int art18;
        @XmlElement(name = "art19_a")
        protected int art19A;
        @XmlElement(name = "art19_b")
        protected int art19B;
        @XmlElement(name = "art19_c")
        protected int art19C;
        protected Integer art20;
        protected Integer art21;
        protected int art22;
        protected Integer art23;
        protected Integer art24;
        protected Integer art25;
        protected Integer art26;
        protected Integer art27;
        protected Integer art28;
        protected Integer art29;
        protected Integer art30;
        protected Integer art31;
        protected Integer art32;
        protected Integer art33;
        protected Integer art34;
        protected Integer art35;
        protected Integer art36;
        protected Integer art37;
        protected Integer art38;
        protected Integer art39;
        protected Integer art40;
        protected Integer art41;
        protected Integer art42;
        protected Integer art43;
        protected Integer art44;
        protected Integer art45;
        @XmlElement(name = "art30v")
        protected Integer art30V;
        @XmlElement(name = "art32v")
        protected Integer art32V;

        /**
         * Gets the value of the art16A property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt16A() {
            return art16A;
        }

        /**
         * Sets the value of the art16A property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt16A(Integer value) {
            this.art16A = value;
        }

        /**
         * Gets the value of the art16B property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt16B() {
            return art16B;
        }

        /**
         * Sets the value of the art16B property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt16B(Integer value) {
            this.art16B = value;
        }

        /**
         * Gets the value of the art16C property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt16C() {
            return art16C;
        }

        /**
         * Sets the value of the art16C property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt16C(Integer value) {
            this.art16C = value;
        }

        /**
         * Gets the value of the art16D property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt16D() {
            return art16D;
        }

        /**
         * Sets the value of the art16D property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt16D(Integer value) {
            this.art16D = value;
        }

        /**
         * Gets the value of the art16E property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt16E() {
            return art16E;
        }

        /**
         * Sets the value of the art16E property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt16E(Integer value) {
            this.art16E = value;
        }

        /**
         * Gets the value of the art17A property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt17A() {
            return art17A;
        }

        /**
         * Sets the value of the art17A property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt17A(Integer value) {
            this.art17A = value;
        }

        /**
         * Gets the value of the art17B property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt17B() {
            return art17B;
        }

        /**
         * Sets the value of the art17B property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt17B(Integer value) {
            this.art17B = value;
        }

        /**
         * Gets the value of the art18 property.
         * 
         */
        public int getArt18() {
            return art18;
        }

        /**
         * Sets the value of the art18 property.
         * 
         */
        public void setArt18(int value) {
            this.art18 = value;
        }

        /**
         * Gets the value of the art19A property.
         * 
         */
        public int getArt19A() {
            return art19A;
        }

        /**
         * Sets the value of the art19A property.
         * 
         */
        public void setArt19A(int value) {
            this.art19A = value;
        }

        /**
         * Gets the value of the art19B property.
         * 
         */
        public int getArt19B() {
            return art19B;
        }

        /**
         * Sets the value of the art19B property.
         * 
         */
        public void setArt19B(int value) {
            this.art19B = value;
        }

        /**
         * Gets the value of the art19C property.
         * 
         */
        public int getArt19C() {
            return art19C;
        }

        /**
         * Sets the value of the art19C property.
         * 
         */
        public void setArt19C(int value) {
            this.art19C = value;
        }

        /**
         * Gets the value of the art20 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt20() {
            return art20;
        }

        /**
         * Sets the value of the art20 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt20(Integer value) {
            this.art20 = value;
        }

        /**
         * Gets the value of the art21 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt21() {
            return art21;
        }

        /**
         * Sets the value of the art21 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt21(Integer value) {
            this.art21 = value;
        }

        /**
         * Gets the value of the art22 property.
         * 
         */
        public int getArt22() {
            return art22;
        }

        /**
         * Sets the value of the art22 property.
         * 
         */
        public void setArt22(int value) {
            this.art22 = value;
        }

        /**
         * Gets the value of the art23 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt23() {
            return art23;
        }

        /**
         * Sets the value of the art23 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt23(Integer value) {
            this.art23 = value;
        }

        /**
         * Gets the value of the art24 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt24() {
            return art24;
        }

        /**
         * Sets the value of the art24 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt24(Integer value) {
            this.art24 = value;
        }

        /**
         * Gets the value of the art25 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt25() {
            return art25;
        }

        /**
         * Sets the value of the art25 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt25(Integer value) {
            this.art25 = value;
        }

        /**
         * Gets the value of the art26 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt26() {
            return art26;
        }

        /**
         * Sets the value of the art26 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt26(Integer value) {
            this.art26 = value;
        }

        /**
         * Gets the value of the art27 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt27() {
            return art27;
        }

        /**
         * Sets the value of the art27 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt27(Integer value) {
            this.art27 = value;
        }

        /**
         * Gets the value of the art28 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt28() {
            return art28;
        }

        /**
         * Sets the value of the art28 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt28(Integer value) {
            this.art28 = value;
        }

        /**
         * Gets the value of the art29 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt29() {
            return art29;
        }

        /**
         * Sets the value of the art29 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt29(Integer value) {
            this.art29 = value;
        }

        /**
         * Gets the value of the art30 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt30() {
            return art30;
        }

        /**
         * Sets the value of the art30 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt30(Integer value) {
            this.art30 = value;
        }

        /**
         * Gets the value of the art31 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt31() {
            return art31;
        }

        /**
         * Sets the value of the art31 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt31(Integer value) {
            this.art31 = value;
        }

        /**
         * Gets the value of the art32 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt32() {
            return art32;
        }

        /**
         * Sets the value of the art32 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt32(Integer value) {
            this.art32 = value;
        }

        /**
         * Gets the value of the art33 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt33() {
            return art33;
        }

        /**
         * Sets the value of the art33 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt33(Integer value) {
            this.art33 = value;
        }

        /**
         * Gets the value of the art34 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt34() {
            return art34;
        }

        /**
         * Sets the value of the art34 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt34(Integer value) {
            this.art34 = value;
        }

        /**
         * Gets the value of the art35 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt35() {
            return art35;
        }

        /**
         * Sets the value of the art35 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt35(Integer value) {
            this.art35 = value;
        }

        /**
         * Gets the value of the art36 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt36() {
            return art36;
        }

        /**
         * Sets the value of the art36 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt36(Integer value) {
            this.art36 = value;
        }

        /**
         * Gets the value of the art37 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt37() {
            return art37;
        }

        /**
         * Sets the value of the art37 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt37(Integer value) {
            this.art37 = value;
        }

        /**
         * Gets the value of the art38 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt38() {
            return art38;
        }

        /**
         * Sets the value of the art38 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt38(Integer value) {
            this.art38 = value;
        }

        /**
         * Gets the value of the art39 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt39() {
            return art39;
        }

        /**
         * Sets the value of the art39 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt39(Integer value) {
            this.art39 = value;
        }

        /**
         * Gets the value of the art40 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt40() {
            return art40;
        }

        /**
         * Sets the value of the art40 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt40(Integer value) {
            this.art40 = value;
        }

        /**
         * Gets the value of the art41 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt41() {
            return art41;
        }

        /**
         * Sets the value of the art41 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt41(Integer value) {
            this.art41 = value;
        }

        /**
         * Gets the value of the art42 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt42() {
            return art42;
        }

        /**
         * Sets the value of the art42 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt42(Integer value) {
            this.art42 = value;
        }

        /**
         * Gets the value of the art43 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt43() {
            return art43;
        }

        /**
         * Sets the value of the art43 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt43(Integer value) {
            this.art43 = value;
        }

        /**
         * Gets the value of the art44 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt44() {
            return art44;
        }

        /**
         * Sets the value of the art44 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt44(Integer value) {
            this.art44 = value;
        }

        /**
         * Gets the value of the art45 property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt45() {
            return art45;
        }

        /**
         * Sets the value of the art45 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt45(Integer value) {
            this.art45 = value;
        }

        /**
         * Gets the value of the art30V property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt30V() {
            return art30V;
        }

        /**
         * Sets the value of the art30V property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt30V(Integer value) {
            this.art30V = value;
        }

        /**
         * Gets the value of the art32V property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getArt32V() {
            return art32V;
        }

        /**
         * Sets the value of the art32V property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setArt32V(Integer value) {
            this.art32V = value;
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
     *         &lt;element name="art71">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art72">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art73">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art74">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
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
        "art71",
        "art72",
        "art73",
        "art74"
    })
    public static class ExigencesPerf {

        @XmlElement(defaultValue = "0")
        protected int art71;
        @XmlElement(defaultValue = "0")
        protected int art72;
        @XmlElement(defaultValue = "0")
        protected int art73;
        @XmlElement(defaultValue = "0")
        protected int art74;

        /**
         * Gets the value of the art71 property.
         * 
         */
        public int getArt71() {
            return art71;
        }

        /**
         * Sets the value of the art71 property.
         * 
         */
        public void setArt71(int value) {
            this.art71 = value;
        }

        /**
         * Gets the value of the art72 property.
         * 
         */
        public int getArt72() {
            return art72;
        }

        /**
         * Sets the value of the art72 property.
         * 
         */
        public void setArt72(int value) {
            this.art72 = value;
        }

        /**
         * Gets the value of the art73 property.
         * 
         */
        public int getArt73() {
            return art73;
        }

        /**
         * Sets the value of the art73 property.
         * 
         */
        public void setArt73(int value) {
            this.art73 = value;
        }

        /**
         * Gets the value of the art74 property.
         * 
         */
        public int getArt74() {
            return art74;
        }

        /**
         * Sets the value of the art74 property.
         * 
         */
        public void setArt74(int value) {
            this.art74 = value;
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
     *       &lt;sequence minOccurs="0">
     *         &lt;element name="pv_install" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="onduleur_pv_collection">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="onduleur_pv" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="certification" minOccurs="0">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                             &lt;maxInclusive value="4"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                       &lt;element name="capteur_pv_collection" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence minOccurs="0">
     *                                                 &lt;element name="capteur_pv" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;all>
     *                                                           &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                                           &lt;element name="certification" minOccurs="0">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                                                 &lt;maxInclusive value="4"/>
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="masques_azi" minOccurs="0">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                                                 &lt;maxInclusive value="1"/>
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="masques_vert" minOccurs="0">
     *                                                             &lt;simpleType>
     *                                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                                                 &lt;maxInclusive value="1"/>
     *                                                               &lt;/restriction>
     *                                                             &lt;/simpleType>
     *                                                           &lt;/element>
     *                                                           &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                           &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                         &lt;/all>
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
        "pvInstall"
    })
    public static class PvInstallCollection {

        @XmlElement(name = "pv_install")
        protected List<Batiment.PvInstallCollection.PvInstall> pvInstall;

        /**
         * Gets the value of the pvInstall property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pvInstall property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPvInstall().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Batiment.PvInstallCollection.PvInstall }
         * 
         * 
         */
        public List<Batiment.PvInstallCollection.PvInstall> getPvInstall() {
            if (pvInstall == null) {
                pvInstall = new ArrayList<Batiment.PvInstallCollection.PvInstall>();
            }
            return this.pvInstall;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="onduleur_pv_collection">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="onduleur_pv" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="certification" minOccurs="0">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                   &lt;maxInclusive value="4"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                             &lt;element name="capteur_pv_collection" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence minOccurs="0">
         *                                       &lt;element name="capteur_pv" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;all>
         *                                                 &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                                                 &lt;element name="certification" minOccurs="0">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                                       &lt;maxInclusive value="4"/>
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="masques_azi" minOccurs="0">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                                       &lt;maxInclusive value="1"/>
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="masques_vert" minOccurs="0">
         *                                                   &lt;simpleType>
         *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                                                       &lt;maxInclusive value="1"/>
         *                                                     &lt;/restriction>
         *                                                   &lt;/simpleType>
         *                                                 &lt;/element>
         *                                                 &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                                 &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                               &lt;/all>
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
        public static class PvInstall {

            @XmlElement(name = "Index", required = true)
            protected BigInteger index;
            @XmlElement(name = "onduleur_pv_collection", required = true)
            protected Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection onduleurPvCollection;

            /**
             * Gets the value of the index property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIndex() {
                return index;
            }

            /**
             * Sets the value of the index property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIndex(BigInteger value) {
                this.index = value;
            }

            /**
             * Gets the value of the onduleurPvCollection property.
             * 
             * @return
             *     possible object is
             *     {@link Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection }
             *     
             */
            public Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection getOnduleurPvCollection() {
                return onduleurPvCollection;
            }

            /**
             * Sets the value of the onduleurPvCollection property.
             * 
             * @param value
             *     allowed object is
             *     {@link Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection }
             *     
             */
            public void setOnduleurPvCollection(Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection value) {
                this.onduleurPvCollection = value;
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
             *         &lt;element name="onduleur_pv" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="certification" minOccurs="0">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                         &lt;maxInclusive value="4"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                   &lt;element name="capteur_pv_collection" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence minOccurs="0">
             *                             &lt;element name="capteur_pv" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;all>
             *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                                       &lt;element name="certification" minOccurs="0">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                                             &lt;maxInclusive value="4"/>
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="masques_azi" minOccurs="0">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                                             &lt;maxInclusive value="1"/>
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="masques_vert" minOccurs="0">
             *                                         &lt;simpleType>
             *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                                             &lt;maxInclusive value="1"/>
             *                                           &lt;/restriction>
             *                                         &lt;/simpleType>
             *                                       &lt;/element>
             *                                       &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                                       &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "onduleurPv"
            })
            public static class OnduleurPvCollection {

                @XmlElement(name = "onduleur_pv", required = true)
                protected List<Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv> onduleurPv;

                /**
                 * Gets the value of the onduleurPv property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the onduleurPv property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getOnduleurPv().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv }
                 * 
                 * 
                 */
                public List<Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv> getOnduleurPv() {
                    if (onduleurPv == null) {
                        onduleurPv = new ArrayList<Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv>();
                    }
                    return this.onduleurPv;
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
                 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *         &lt;element name="certification" minOccurs="0">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *               &lt;maxInclusive value="4"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
                 *         &lt;/element>
                 *         &lt;element name="capteur_pv_collection" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence minOccurs="0">
                 *                   &lt;element name="capteur_pv" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;all>
                 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *                             &lt;element name="certification" minOccurs="0">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *                                   &lt;maxInclusive value="4"/>
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="masques_azi" minOccurs="0">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *                                   &lt;maxInclusive value="1"/>
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="masques_vert" minOccurs="0">
                 *                               &lt;simpleType>
                 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *                                   &lt;maxInclusive value="1"/>
                 *                                 &lt;/restriction>
                 *                               &lt;/simpleType>
                 *                             &lt;/element>
                 *                             &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                             &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                    "index",
                    "certification",
                    "capteurPvCollection"
                })
                public static class OnduleurPv {

                    @XmlElement(name = "Index", required = true)
                    protected BigInteger index;
                    @XmlElement(defaultValue = "0")
                    protected BigInteger certification;
                    @XmlElement(name = "capteur_pv_collection")
                    protected Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection capteurPvCollection;

                    /**
                     * Gets the value of the index property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getIndex() {
                        return index;
                    }

                    /**
                     * Sets the value of the index property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setIndex(BigInteger value) {
                        this.index = value;
                    }

                    /**
                     * Gets the value of the certification property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getCertification() {
                        return certification;
                    }

                    /**
                     * Sets the value of the certification property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setCertification(BigInteger value) {
                        this.certification = value;
                    }

                    /**
                     * Gets the value of the capteurPvCollection property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection }
                     *     
                     */
                    public Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection getCapteurPvCollection() {
                        return capteurPvCollection;
                    }

                    /**
                     * Sets the value of the capteurPvCollection property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection }
                     *     
                     */
                    public void setCapteurPvCollection(Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection value) {
                        this.capteurPvCollection = value;
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
                     *       &lt;sequence minOccurs="0">
                     *         &lt;element name="capteur_pv" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;all>
                     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                   &lt;element name="certification" minOccurs="0">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                     *                         &lt;maxInclusive value="4"/>
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="masques_azi" minOccurs="0">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                     *                         &lt;maxInclusive value="1"/>
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="masques_vert" minOccurs="0">
                     *                     &lt;simpleType>
                     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                     *                         &lt;maxInclusive value="1"/>
                     *                       &lt;/restriction>
                     *                     &lt;/simpleType>
                     *                   &lt;/element>
                     *                   &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *                   &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                        "capteurPv"
                    })
                    public static class CapteurPvCollection {

                        @XmlElement(name = "capteur_pv")
                        protected List<Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection.CapteurPv> capteurPv;

                        /**
                         * Gets the value of the capteurPv property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the capteurPv property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getCapteurPv().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection.CapteurPv }
                         * 
                         * 
                         */
                        public List<Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection.CapteurPv> getCapteurPv() {
                            if (capteurPv == null) {
                                capteurPv = new ArrayList<Batiment.PvInstallCollection.PvInstall.OnduleurPvCollection.OnduleurPv.CapteurPvCollection.CapteurPv>();
                            }
                            return this.capteurPv;
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
                         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *         &lt;element name="certification" minOccurs="0">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                         *               &lt;maxInclusive value="4"/>
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="masques_azi" minOccurs="0">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                         *               &lt;maxInclusive value="1"/>
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="masques_vert" minOccurs="0">
                         *           &lt;simpleType>
                         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                         *               &lt;maxInclusive value="1"/>
                         *             &lt;/restriction>
                         *           &lt;/simpleType>
                         *         &lt;/element>
                         *         &lt;element name="marque_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                         *         &lt;element name="deno_com_capteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                        public static class CapteurPv {

                            @XmlElement(name = "Index", required = true)
                            protected BigInteger index;
                            @XmlElement(defaultValue = "0")
                            protected BigInteger certification;
                            @XmlElement(name = "masques_azi")
                            protected BigInteger masquesAzi;
                            @XmlElement(name = "masques_vert")
                            protected BigInteger masquesVert;
                            @XmlElement(name = "marque_capteur")
                            protected String marqueCapteur;
                            @XmlElement(name = "deno_com_capteur")
                            protected String denoComCapteur;

                            /**
                             * Gets the value of the index property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *     
                             */
                            public BigInteger getIndex() {
                                return index;
                            }

                            /**
                             * Sets the value of the index property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *     
                             */
                            public void setIndex(BigInteger value) {
                                this.index = value;
                            }

                            /**
                             * Gets the value of the certification property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *     
                             */
                            public BigInteger getCertification() {
                                return certification;
                            }

                            /**
                             * Sets the value of the certification property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *     
                             */
                            public void setCertification(BigInteger value) {
                                this.certification = value;
                            }

                            /**
                             * Gets the value of the masquesAzi property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *     
                             */
                            public BigInteger getMasquesAzi() {
                                return masquesAzi;
                            }

                            /**
                             * Sets the value of the masquesAzi property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *     
                             */
                            public void setMasquesAzi(BigInteger value) {
                                this.masquesAzi = value;
                            }

                            /**
                             * Gets the value of the masquesVert property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *     
                             */
                            public BigInteger getMasquesVert() {
                                return masquesVert;
                            }

                            /**
                             * Sets the value of the masquesVert property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *     
                             */
                            public void setMasquesVert(BigInteger value) {
                                this.masquesVert = value;
                            }

                            /**
                             * Gets the value of the marqueCapteur property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getMarqueCapteur() {
                                return marqueCapteur;
                            }

                            /**
                             * Sets the value of the marqueCapteur property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setMarqueCapteur(String value) {
                                this.marqueCapteur = value;
                            }

                            /**
                             * Gets the value of the denoComCapteur property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getDenoComCapteur() {
                                return denoComCapteur;
                            }

                            /**
                             * Sets the value of the denoComCapteur property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setDenoComCapteur(String value) {
                                this.denoComCapteur = value;
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
     *     &lt;extension base="{}ref_titres">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class RefTitres
        extends com.bionova.optimi.xml.fecRSEnv.RefTitres
    {


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
     *         &lt;element name="art1" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art1_statut" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art2" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art2_statut" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art3" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;enumeration value="0"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="art3_statut" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="0"/>
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
        "art1",
        "art1Statut",
        "art2",
        "art2Statut",
        "art3",
        "art3Statut"
    })
    public static class Titre5 {

        @XmlElement(defaultValue = "0")
        protected BigInteger art1;
        @XmlElement(name = "art1_statut", defaultValue = "0")
        protected BigInteger art1Statut;
        @XmlElement(defaultValue = "0")
        protected BigInteger art2;
        @XmlElement(name = "art2_statut", defaultValue = "0")
        protected BigInteger art2Statut;
        @XmlElement(defaultValue = "0")
        protected BigInteger art3;
        @XmlElement(name = "art3_statut", defaultValue = "0")
        protected BigInteger art3Statut;

        /**
         * Gets the value of the art1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getArt1() {
            return art1;
        }

        /**
         * Sets the value of the art1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setArt1(BigInteger value) {
            this.art1 = value;
        }

        /**
         * Gets the value of the art1Statut property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getArt1Statut() {
            return art1Statut;
        }

        /**
         * Sets the value of the art1Statut property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setArt1Statut(BigInteger value) {
            this.art1Statut = value;
        }

        /**
         * Gets the value of the art2 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getArt2() {
            return art2;
        }

        /**
         * Sets the value of the art2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setArt2(BigInteger value) {
            this.art2 = value;
        }

        /**
         * Gets the value of the art2Statut property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getArt2Statut() {
            return art2Statut;
        }

        /**
         * Sets the value of the art2Statut property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setArt2Statut(BigInteger value) {
            this.art2Statut = value;
        }

        /**
         * Gets the value of the art3 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getArt3() {
            return art3;
        }

        /**
         * Sets the value of the art3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setArt3(BigInteger value) {
            this.art3 = value;
        }

        /**
         * Gets the value of the art3Statut property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getArt3Statut() {
            return art3Statut;
        }

        /**
         * Sets the value of the art3Statut property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setArt3Statut(BigInteger value) {
            this.art3Statut = value;
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
     *         &lt;element ref="{}zone" maxOccurs="unbounded"/>
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
        "zone"
    })
    public static class ZoneCollection {

        @XmlElement(required = true)
        protected List<Zone> zone;

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
         * {@link Zone }
         * 
         * 
         */
        public List<Zone> getZone() {
            if (zone == null) {
                zone = new ArrayList<Zone>();
            }
            return this.zone;
        }

    }

}
