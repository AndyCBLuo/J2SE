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
 * Class DeviceConfigs.
 * 
 * @version $Revision$ $Date$
 */
public class DeviceConfigs implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _deviceConfigList
     */
    private java.util.Vector _deviceConfigList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DeviceConfigs() 
     {
        super();
        _deviceConfigList = new Vector();
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfigs()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDeviceConfig
     * 
     * 
     * 
     * @param vDeviceConfig
     */
    public void addDeviceConfig(com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig vDeviceConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        _deviceConfigList.addElement(vDeviceConfig);
    } //-- void addDeviceConfig(com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig) 

    /**
     * Method addDeviceConfig
     * 
     * 
     * 
     * @param index
     * @param vDeviceConfig
     */
    public void addDeviceConfig(int index, com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig vDeviceConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        _deviceConfigList.insertElementAt(vDeviceConfig, index);
    } //-- void addDeviceConfig(int, com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig) 

    /**
     * Method enumerateDeviceConfig
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateDeviceConfig()
    {
        return _deviceConfigList.elements();
    } //-- java.util.Enumeration enumerateDeviceConfig() 

    /**
     * Method getDeviceConfig
     * 
     * 
     * 
     * @param index
     * @return DeviceConfig
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig getDeviceConfig(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deviceConfigList.size())) {
            throw new IndexOutOfBoundsException("getDeviceConfig: Index value '"+index+"' not in range [0.."+_deviceConfigList.size()+ "]");
        }
        
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig) _deviceConfigList.elementAt(index);
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig getDeviceConfig(int) 

    /**
     * Method getDeviceConfig
     * 
     * 
     * 
     * @return DeviceConfig
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig[] getDeviceConfig()
    {
        int size = _deviceConfigList.size();
        com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig[] mArray = new com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig) _deviceConfigList.elementAt(index);
        }
        return mArray;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig[] getDeviceConfig() 

    /**
     * Method getDeviceConfigCount
     * 
     * 
     * 
     * @return int
     */
    public int getDeviceConfigCount()
    {
        return _deviceConfigList.size();
    } //-- int getDeviceConfigCount() 

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
     * Method removeAllDeviceConfig
     * 
     */
    public void removeAllDeviceConfig()
    {
        _deviceConfigList.removeAllElements();
    } //-- void removeAllDeviceConfig() 

    /**
     * Method removeDeviceConfig
     * 
     * 
     * 
     * @param index
     * @return DeviceConfig
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig removeDeviceConfig(int index)
    {
        java.lang.Object obj = _deviceConfigList.elementAt(index);
        _deviceConfigList.removeElementAt(index);
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig) obj;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig removeDeviceConfig(int) 

    /**
     * Method setDeviceConfig
     * 
     * 
     * 
     * @param index
     * @param vDeviceConfig
     */
    public void setDeviceConfig(int index, com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig vDeviceConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deviceConfigList.size())) {
            throw new IndexOutOfBoundsException("setDeviceConfig: Index value '"+index+"' not in range [0.."+_deviceConfigList.size()+ "]");
        }
        _deviceConfigList.setElementAt(vDeviceConfig, index);
    } //-- void setDeviceConfig(int, com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig) 

    /**
     * Method setDeviceConfig
     * 
     * 
     * 
     * @param deviceConfigArray
     */
    public void setDeviceConfig(com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfig[] deviceConfigArray)
    {
        //-- copy array
        _deviceConfigList.removeAllElements();
        for (int i = 0; i < deviceConfigArray.length; i++) {
            _deviceConfigList.addElement(deviceConfigArray[i]);
        }
    } //-- void setDeviceConfig(com.hsbc.p2g.mobile.tools.checksum.bean.DeviceConfig) 

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
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfigs) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.DeviceConfigs.class, reader);
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
