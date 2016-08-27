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

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class DeviceConfig.
 * 
 * @version $Revision$ $Date$
 */
public class DeviceConfig implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _devName
     */
    private java.lang.String _devName;

    /**
     * Field _configUrl
     */
    private java.lang.String _configUrl;


      //----------------/
     //- Constructors -/
    //----------------/

    public DeviceConfig() 
     {
        super();
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'configUrl'.
     * 
     * @return String
     * @return the value of field 'configUrl'.
     */
    public java.lang.String getConfigUrl()
    {
        return this._configUrl;
    } //-- java.lang.String getConfigUrl() 

    /**
     * Returns the value of field 'devName'.
     * 
     * @return String
     * @return the value of field 'devName'.
     */
    public java.lang.String getDevName()
    {
        return this._devName;
    } //-- java.lang.String getDevName() 

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
     * Sets the value of field 'configUrl'.
     * 
     * @param configUrl the value of field 'configUrl'.
     */
    public void setConfigUrl(java.lang.String configUrl)
    {
        this._configUrl = configUrl;
    } //-- void setConfigUrl(java.lang.String) 

    /**
     * Sets the value of field 'devName'.
     * 
     * @param devName the value of field 'devName'.
     */
    public void setDevName(java.lang.String devName)
    {
        this._devName = devName;
    } //-- void setDevName(java.lang.String) 

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
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig.class, reader);
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
