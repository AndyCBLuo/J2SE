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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class DeployCfg.
 * 
 * @version $Revision$ $Date$
 */
public class DeployCfg implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _envName
     */
    private java.lang.String _envName;

    /**
     * Field _envDomain
     */
    private java.lang.String _envDomain;


      //----------------/
     //- Constructors -/
    //----------------/

    public DeployCfg() 
     {
        super();
    } //-- com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'envDomain'.
     * 
     * @return String
     * @return the value of field 'envDomain'.
     */
    public java.lang.String getEnvDomain()
    {
        return this._envDomain;
    } //-- java.lang.String getEnvDomain() 

    /**
     * Returns the value of field 'envName'.
     * 
     * @return String
     * @return the value of field 'envName'.
     */
    public java.lang.String getEnvName()
    {
        return this._envName;
    } //-- java.lang.String getEnvName() 

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
     * Sets the value of field 'envDomain'.
     * 
     * @param envDomain the value of field 'envDomain'.
     */
    public void setEnvDomain(java.lang.String envDomain)
    {
        this._envDomain = envDomain;
    } //-- void setEnvDomain(java.lang.String) 

    /**
     * Sets the value of field 'envName'.
     * 
     * @param envName the value of field 'envName'.
     */
    public void setEnvName(java.lang.String envName)
    {
        this._envName = envName;
    } //-- void setEnvName(java.lang.String) 

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
        return (com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg) Unmarshaller.unmarshal(com.iwebcoding.mobile.tool.model.castor.bean.DeployCfg.class, reader);
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
