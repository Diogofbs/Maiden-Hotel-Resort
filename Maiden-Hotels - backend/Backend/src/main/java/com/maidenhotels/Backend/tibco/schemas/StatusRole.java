//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.11 at 09:29:45 AM BST 
//


package com.maidenhotels.Backend.tibco.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.tibco.com/schemas/TIBCO/Maiden_Hotels_Project/Schemas/Schema.xsd}SuccessRole" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "successRole"
})
@XmlRootElement(name = "StatusRole", namespace = "http://www.tibco.com/schemas/TIBCO/Maiden_Hotels_Project/Schemas/Schema.xsd")
public class StatusRole {

    @XmlElement(name = "SuccessRole", namespace = "http://www.tibco.com/schemas/TIBCO/Maiden_Hotels_Project/Schemas/Schema.xsd")
    protected SuccessRole successRole;

    public StatusRole() {
    }

    public StatusRole(SuccessRole successRole) {
        this.successRole = successRole;
    }

    /**
     * Gets the value of the successRole property.
     * 
     * @return
     *     possible object is
     *     {@link SuccessRole }
     *     
     */
    public SuccessRole getSuccessRole() {
        return successRole;
    }

    /**
     * Sets the value of the successRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link SuccessRole }
     *     
     */
    public void setSuccessRole(SuccessRole value) {
        this.successRole = value;
    }

}