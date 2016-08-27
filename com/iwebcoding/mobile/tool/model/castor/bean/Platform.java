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
 * Class Platfrom.
 * 
 * @version $Revision$ $Date$
 */
public class Platform implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _deviceConfigs
     */
    private com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfigs _deviceConfigs;


      //----------------/
     //- Constructors -/
    //----------------/

    public Platform() 
     {
        super();
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.Platfrom()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'deviceConfigs'.
     * 
     * @return DeviceConfigs
     * @return the value of field 'deviceConfigs'.
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfigs getDeviceConfigs()
    {
        return this._deviceConfigs;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfigs getDeviceConfigs() 

    /**
     * Returns the value of field 'name'.
     * 
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

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
     * Sets the value of field 'deviceConfigs'.
     * 
     * @param deviceConfigs the value of field 'deviceConfigs'.
     */
    public void setDeviceConfigs(com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfigs deviceConfigs)
    {
        this._deviceConfigs = deviceConfigs;
    } //-- void setDeviceConfigs(com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfigs) 

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String) 

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
        return (com.iwebcoding.mobile.tool.model.castor.bean.Platform) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.Platform.class, reader);
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
