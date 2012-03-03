/*Vmap - A Program for creating and viewing Mindmaps
 *Copyright (C) 2004-2005  <celt@gold.ac.uk>
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*$Id: LIPTypeName.java,v 1.8 2005/05/29 12:21:14 veghead Exp $ */

package ims;

import java.util.Vector;
import java.lang.String;
import vmap.main.XMLElement;

public class LIPTypeName {

    private Vector typeNameSourceList=null;
    private String typeNameSource=null;
    private String typeNameValue=null;


    /**
     * overloaded constructor
     */
    public LIPTypeName() {
        typeNameValue=new String("");
    }


    /**
     * overloaded constructor
     * @param sourcelist content for the source list
     * @param value default value
     */
    public LIPTypeName(String[] sourcelist,String value) {
        typeNameSourceList=new Vector();
        for(int i=0;i<sourcelist.length;i++) {
            typeNameSourceList.add(sourcelist[i]);
        }
        typeNameValue=value; 
    }


    /**
     * overloaded constructor
     * @param sourcelist content for the source list
     */
    public LIPTypeName(String[] sourcelist) {
        typeNameSourceList=new Vector();
        for(int i=0;i<sourcelist.length;i++) {
            typeNameSourceList.add(sourcelist[i]);
        }
        typeNameValue=sourcelist[0]; 
    }


    /**
     * overloaded constructor
     * @param tysource 
     * @param tyvalue
     */
    public LIPTypeName(String tysource, String tyvalue) {
        typeNameSource=tysource;
        typeNameValue=tyvalue;
    }

    /**
     * overloaded constructor
     * @param tysourcet 
     * @param tysourcev 
     * @param tyvalue
     */
    public LIPTypeName(String tysourcet, String tysourcev,String tyvalue) {
        setSource(tysourcet,tysourcev);
        typeNameValue=tyvalue;
    }



    /**
     * Get the list of available typename values
     * @returns Vector of typename strings
     */
    public Vector getSourceList() {
        return typeNameSourceList;
    }

    /**
     * Set the source
     * @param sourceType sourcetype attribute
     * @param sourceVal tysource content
     */
    public void setSource(String sourceType,String sourceVal) {
        setSourceList(sourceVal);
        typeNameSource=sourceType;
    }


    /**
     * Get the list of available typename values
     * @returns String for use in XML
     */
    public String getSourceListXML() {
        String ret=new String("");
        for(int i=0;i<typeNameSourceList.size();i++) {
            if (i>0) {
                ret=ret.concat(", ");
            }
            ret=ret.concat((String)typeNameSourceList.elementAt(i));
        }
        return ret;
    }


    /**
     * Set the list of available typename values
     */
    public void setSourceList(Vector list) {
        typeNameSourceList=list;
        typeNameSource="List";
    }


    /**
     * Set the list of available typename values
     * @param list a string consisting of comma seperated values
     */
    public void setSourceList(String list) {
        String[] sa=list.split("[,\\s\\n\\r]+");
        typeNameSourceList=new Vector();   
        typeNameSource="List";
        for(int i=0;i<sa.length;i++) {
            typeNameSourceList.add(sa[i]);
        }
    }

 
    /** 
     * Get the typename
     * @returns String of typeName Value
     */
    public String getValue() {
        return typeNameValue;
    }


    /** 
     * Get the typesource
     * @returns String of typeNameSource Value
     */
    public String getSource() {
        return typeNameSource;
    }


    /** 
     * Set the typename
     */
    public void setValue(String value) {
        typeNameValue=value;
    }


    /**
     * Return the typename as an XML structure
     */
    public XMLElement getXML() {
        XMLElement xtypename=new XMLElement();
        xtypename.setName("typename");
        XMLElement xtypesource=new XMLElement();
        xtypesource.setName("tysource");
        XMLElement xtypeval=new XMLElement();
        xtypeval.setName("tyvalue");
        xtypeval.setContent(typeNameValue);
        xtypesource.setAttribute("sourcetype",typeNameSource);
        if (typeNameSourceList!=null) {
            xtypesource.setContent(getSourceListXML());
        }
        xtypename.addChild(xtypesource);
        xtypename.addChild(xtypeval);
        return(xtypename);
    }

}
   
