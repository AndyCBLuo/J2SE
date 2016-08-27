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

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class DeployCfgList.
 * 
 * @version $Revision$ $Date$
 */
public class DeployCfgList implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _deployCfgList
     */
    private java.util.Vector _deployCfgList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DeployCfgList() 
     {
        super();
        _deployCfgList = new Vector();
    } //-- com.iwebcoding.mobile.tool.model.castor.bean.DeployCfgList()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDeployCfg
     * 
     * 
     * 
     * @param vDeployCfg
     */
    public void addDeployCfg(com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg vDeployCfg)
        throws java.lang.IndexOutOfBoundsException
    {
        _deployCfgList.addElement(vDeployCfg);
    } //-- void addDeployCfg(com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) 

    /**
     * Method addDeployCfg
     * 
     * 
     * 
     * @param index
     * @param vDeployCfg
     */
    public void addDeployCfg(int index, com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg vDeployCfg)
        throws java.lang.IndexOutOfBoundsException
    {
        _deployCfgList.insertElementAt(vDeployCfg, index);
    } //-- void addDeployCfg(int, com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) 

    /**
     * Method enumerateDeployCfg
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration enumerateDeployCfg()
    {
        return _deployCfgList.elements();
    } //-- java.util.Enumeration enumerateDeployCfg() 

    /**
     * Method getDeployCfg
     * 
     * 
     * 
     * @param index
     * @return DeployCfg
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg getDeployCfg(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deployCfgList.size())) {
            throw new IndexOutOfBoundsException("getDeployCfg: Index value '"+index+"' not in range [0.."+_deployCfgList.size()+ "]");
        }
        
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) _deployCfgList.elementAt(index);
    } //-- com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg getDeployCfg(int) 

    /**
     * Method getDeployCfg
     * 
     * 
     * 
     * @return DeployCfg
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg[] getDeployCfg()
    {
        int size = _deployCfgList.size();
        com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg[] mArray = new com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) _deployCfgList.elementAt(index);
        }
        return mArray;
    } //-- com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg[] getDeployCfg() 

    /**
     * Method getDeployCfgCount
     * 
     * 
     * 
     * @return int
     */
    public int getDeployCfgCount()
    {
        return _deployCfgList.size();
    } //-- int getDeployCfgCount() 

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
     * Method removeAllDeployCfg
     * 
     */
    public void removeAllDeployCfg()
    {
        _deployCfgList.removeAllElements();
    } //-- void removeAllDeployCfg() 

    /**
     * Method removeDeployCfg
     * 
     * 
     * 
     * @param index
     * @return DeployCfg
     */
    public com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg removeDeployCfg(int index)
    {
        java.lang.Object obj = _deployCfgList.elementAt(index);
        _deployCfgList.removeElementAt(index);
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) obj;
    } //-- com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg removeDeployCfg(int) 

    /**
     * Method setDeployCfg
     * 
     * 
     * 
     * @param index
     * @param vDeployCfg
     */
    public void setDeployCfg(int index, com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg vDeployCfg)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _deployCfgList.size())) {
            throw new IndexOutOfBoundsException("setDeployCfg: Index value '"+index+"' not in range [0.."+_deployCfgList.size()+ "]");
        }
        _deployCfgList.setElementAt(vDeployCfg, index);
    } //-- void setDeployCfg(int, com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) 

    /**
     * Method setDeployCfg
     * 
     * 
     * 
     * @param deployCfgArray
     */
    public void setDeployCfg(com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg[] deployCfgArray)
    {
        //-- copy array
        _deployCfgList.removeAllElements();
        for (int i = 0; i < deployCfgArray.length; i++) {
            _deployCfgList.addElement(deployCfgArray[i]);
        }
    } //-- void setDeployCfg(com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) 

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
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeployCfgList) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.DeployCfgList.class, reader);
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
