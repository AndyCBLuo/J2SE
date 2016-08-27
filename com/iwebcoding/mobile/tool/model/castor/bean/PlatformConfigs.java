/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.7</a>, using an XML
 * Schema.
 * $Id$
 */

package com.iwebcoding.mobile.tool.model.castor.bean;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PlatformConfigs.
 * 
 * @version $Revision$ $Date$
 */
public class PlatformConfigs implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _platfromList
     */
    private java.util.Vector _platfromList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PlatformConfigs() 
     {
        super();
        _platfromList = new Vector();
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.PlatformConfigs()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addPlatfrom
     * 
     * 
     * 
     * @param vPlatfrom
     */
    public void addPlatfrom(com.iwebcoding.mobile.tool.model.castor.bean.Platform vPlatfrom)
        throws java.lang.IndexOutOfBoundsException
    {
        _platfromList.addElement(vPlatfrom);
    } //-- void addPlatfrom(com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom) 

    /**
     * Method addPlatfrom
     * 
     * 
     * 
     * @param index
     * @param vPlatfrom
     */
    public void addPlatfrom(int index, com.iwebcoding.mobile.tool.model.castor.bean.Platform vPlatfrom)
        throws java.lang.IndexOutOfBoundsException
    {
        _platfromList.insertElementAt(vPlatfrom, index);
    } //-- void addPlatfrom(int, com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom) 

    /**
     * Method enumeratePlatfrom
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumeratePlatfrom()
    {
        return _platfromList.elements();
    } //-- java.util.Enumeration enumeratePlatfrom() 

    /**
     * Method getPlatfrom
     * 
     * 
     * 
     * @param index
     * @return Platfrom
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.Platform getPlatfrom(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _platfromList.size())) {
            throw new IndexOutOfBoundsException("getPlatfrom: Index value '"+index+"' not in range [0.."+_platfromList.size()+ "]");
        }
        
        return (com.iwebcoding.mobile.tool.model.castor.bean.Platform) _platfromList.elementAt(index);
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom getPlatfrom(int) 

    /**
     * Method getPlatfrom
     * 
     * 
     * 
     * @return Platfrom
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.Platform[] getPlatfrom()
    {
        int size = _platfromList.size();
        com.iwebcoding.mobile.tool.model.castor.bean.Platform[] mArray = new com.iwebcoding.mobile.tool.model.castor.bean.Platform[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.iwebcoding.mobile.tool.model.castor.bean.Platform) _platfromList.elementAt(index);
        }
        return mArray;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom[] getPlatfrom() 

    /**
     * Method getPlatfromCount
     * 
     * 
     * 
     * @return int
     */
    public int getPlatfromCount()
    {
        return _platfromList.size();
    } //-- int getPlatfromCount() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return boolean
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeAllPlatfrom
     * 
     */
    public void removeAllPlatfrom()
    {
        _platfromList.removeAllElements();
    } //-- void removeAllPlatfrom() 

    /**
     * Method removePlatfrom
     * 
     * 
     * 
     * @param index
     * @return Platfrom
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.Platform removePlatfrom(int index)
    {
        java.lang.Object obj = _platfromList.elementAt(index);
        _platfromList.removeElementAt(index);
        return (com.iwebcoding.mobile.tool.model.castor.bean.Platform) obj;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom removePlatfrom(int) 

    /**
     * Method setPlatfrom
     * 
     * 
     * 
     * @param index
     * @param vPlatfrom
     */
    public void setPlatfrom(int index, com.iwebcoding.mobile.tool.model.castor.bean.Platform vPlatfrom)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _platfromList.size())) {
            throw new IndexOutOfBoundsException("setPlatfrom: Index value '"+index+"' not in range [0.."+_platfromList.size()+ "]");
        }
        _platfromList.setElementAt(vPlatfrom, index);
    } //-- void setPlatfrom(int, com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom) 

    /**
     * Method setPlatfrom
     * 
     * 
     * 
     * @param platfromArray
     */
    public void setPlatfrom(com.iwebcoding.mobile.tool.model.castor.bean.Platform[] platfromArray)
    {
        //-- copy array
        _platfromList.removeAllElements();
        for (int i = 0; i < platfromArray.length; i++) {
            _platfromList.addElement(platfromArray[i]);
        }
    } //-- void setPlatfrom(com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom) 

    /**
     * Method unmarshal
     * 
     * 
     * 
     * @param reader
     * @return Object
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (com.iwebcoding.mobile.tool.model.castor.bean.PlatformConfigs) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.PlatformConfigs.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     * 
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
