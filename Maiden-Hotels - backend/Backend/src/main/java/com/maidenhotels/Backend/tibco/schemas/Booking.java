//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.11 at 09:29:45 AM BST 
//


package com.maidenhotels.Backend.tibco.schemas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://www.tibco.com/schemas/TIBCO/Maiden_Hotels_Project/Schemas/Bookings.xsd}BkGuests" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.tibco.com/schemas/TIBCO/Maiden_Hotels_Project/Schemas/Bookings.xsd}BkServices" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.tibco.com/schemas/TIBCO/Maiden_Hotels_Project/Schemas/Bookings.xsd}Bkrooms_hotel" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="check_in_date" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="check_out_date" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bkGuests",
    "bkServices",
    "bkroomsHotel"
})
@XmlRootElement(name = "Booking")
public class Booking {

    @XmlElement(name = "BkGuests")
    protected List<BkGuests> bkGuests;
    @XmlElement(name = "BkServices")
    protected List<BkServices> bkServices;
    @XmlElement(name = "Bkrooms_hotel")
    protected List<BkroomsHotel> bkroomsHotel;
    @XmlAttribute(name = "check_in_date")
    protected String checkInDate;
    @XmlAttribute(name = "check_out_date")
    protected String checkOutDate;
    @XmlAttribute(name = "date")
    protected String date;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "status")
    protected String status;

    public Booking() {
    }

    public Booking(List<BkGuests> bkGuests, List<BkServices> bkServices, List<BkroomsHotel> bkroomsHotel, String checkInDate, String checkOutDate, String date, String id, String status) {
        this.bkGuests = bkGuests;
        this.bkServices = bkServices;
        this.bkroomsHotel = bkroomsHotel;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.date = date;
        this.id = id;
        this.status = status;
    }

    /**
     * Gets the value of the bkGuests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bkGuests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBkGuests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BkGuests }
     * 
     * 
     */
    public List<BkGuests> getBkGuests() {
        if (bkGuests == null) {
            bkGuests = new ArrayList<BkGuests>();
        }
        return this.bkGuests;
    }

    /**
     * Gets the value of the bkServices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bkServices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBkServices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BkServices }
     * 
     * 
     */
    public List<BkServices> getBkServices() {
        if (bkServices == null) {
            bkServices = new ArrayList<BkServices>();
        }
        return this.bkServices;
    }

    /**
     * Gets the value of the bkroomsHotel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bkroomsHotel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBkroomsHotel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BkroomsHotel }
     * 
     * 
     */
    public List<BkroomsHotel> getBkroomsHotel() {
        if (bkroomsHotel == null) {
            bkroomsHotel = new ArrayList<BkroomsHotel>();
        }
        return this.bkroomsHotel;
    }

    /**
     * Gets the value of the checkInDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckInDate() {
        return checkInDate;
    }

    /**
     * Sets the value of the checkInDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckInDate(String value) {
        this.checkInDate = value;
    }

    /**
     * Gets the value of the checkOutDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Sets the value of the checkOutDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckOutDate(String value) {
        this.checkOutDate = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
