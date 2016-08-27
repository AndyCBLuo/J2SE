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
 * Class EntityListConfig.
 * 
 * @version $Revision$ $Date$
 */
public class EntityListConfig implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _entityConfigList
     */
    private java.util.Vector _entityConfigList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EntityListConfig() 
     {
        super();
        _entityConfigList = new Vector();
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.EntityListConfig()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEntityConfig
     * 
     * 
     * 
     * @param vEntityConfig
     */
    public void addEntityConfig(com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig vEntityConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        _entityConfigList.addElement(vEntityConfig);
    } //-- void addEntityConfig(com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig) 

    /**
     * Method addEntityConfig
     * 
     * 
     * 
     * @param index
     * @param vEntityConfig
     */
    public void addEntityConfig(int index, com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig vEntityConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        _entityConfigList.insertElementAt(vEntityConfig, index);
    } //-- void addEntityConfig(int, com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig) 

    /**
     * Method enumerateEntityConfig
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateEntityConfig()
    {
        return _entityConfigList.elements();
    } //-- java.util.Enumeration enumerateEntityConfig() 

    /**
     * Method getEntityConfig
     * 
     * 
     * 
     * @param index
     * @return EntityConfig
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig getEntityConfig(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _entityConfigList.size())) {
            throw new IndexOutOfBoundsException("getEntityConfig: Index value '"+index+"' not in range [0.."+_entityConfigList.size()+ "]");
        }
        
        return (com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig) _entityConfigList.elementAt(index);
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig getEntityConfig(int) 

    /**
     * Method getEntityConfig
     * 
     * 
     * 
     * @return EntityConfig
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig[] getEntityConfig()
    {
        int size = _entityConfigList.size();
        com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig[] mArray = new com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig) _entityConfigList.elementAt(index);
        }
        return mArray;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig[] getEntityConfig() 

    /**
     * Method getEntityConfigCount
     * 
     * 
     * 
     * @return int
     */
    public int getEntityConfigCount()
    {
        return _entityConfigList.size();
    } //-- int getEntityConfigCount() 

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
     * Method removeAllEntityConfig
     * 
     */
    public void removeAllEntityConfig()
    {
        _entityConfigList.removeAllElements();
    } //-- void removeAllEntityConfig() 

    /**
     * Method removeEntityConfig
     * 
     * 
     * 
     * @param index
     * @return EntityConfig
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig removeEntityConfig(int index)
    {
        java.lang.Object obj = _entityConfigList.elementAt(index);
        _entityConfigList.removeElementAt(index);
        return (com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig) obj;
    } //-- com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig removeEntityConfig(int) 

    /**
     * Method setEntityConfig
     * 
     * 
     * 
     * @param index
     * @param vEntityConfig
     */
    public void setEntityConfig(int index, com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig vEntityConfig)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _entityConfigList.size())) {
            throw new IndexOutOfBoundsException("setEntityConfig: Index value '"+index+"' not in range [0.."+_entityConfigList.size()+ "]");
        }
        _entityConfigList.setElementAt(vEntityConfig, index);
    } //-- void setEntityConfig(int, com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig) 

    /**
     * Method setEntityConfig
     * 
     * 
     * 
     * @param entityConfigArray
     */
    public void setEntityConfig(com.iwebcoding.mobile.tool.model.castor.bean.EntityConfig[] entityConfigArray)
    {
        //-- copy array
        _entityConfigList.removeAllElements();
        for (int i = 0; i < entityConfigArray.length; i++) {
            _entityConfigList.addElement(entityConfigArray[i]);
        }
    } //-- void setEntityConfig(com.hsbc.p2g.mobile.tools.checksum.bean.EntityConfig) 

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
        return (com.iwebcoding.mobile.tool.model.castor.bean.EntityListConfig) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.EntityListConfig.class, reader);
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
