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
/*$Id: LIPDate.java,v 1.1 2005/05/29 12:21:14 veghead Exp $ */

package ims;

import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.String;
import vmap.main.XMLElement;

public class LIPDate {

    private LIPTypeName typeName=null;
    private String dateTime=null;
    private LIPDescription description=null;


    /**
     * overloaded constructor
     */
    public LIPDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd'T'HH:mm:ss");
        dateTime=sdf.format(new Date());
    }


    /**
     * overloaded constructor
     * @param tysourcet sourcetype for typename
     * @param tysourcev source content for typename
     * @param tyvalue value for typename
     */
    public LIPDate(String tysourcet,String tysourcev,String tyvalue) {
        this();
        typeName = new LIPTypeName(tysourcet, tysourcev, tyvalue);
    }


    /**
     * overloaded constructor
     * @param tysourcet sourcetype for typename
     * @param tysourcev source content for typename
     * @param tyvalue value for typename
     * @param desc description field
     */
    public LIPDate(String tysourcet,String tysourcev, String tyvalue, String desc) {
        this(tysourcet,tysourcev,tyvalue);
        description=new LIPDescription(desc);
    }

    /**
     * sets the typename
     * @param tm the LIPTypeName to set
     */
    public void setTypeName(LIPTypeName tn) {
        typeName=tn;
    }


    /**
     * returns the value of the typeName
     * @returns value of the TypeName as a String
     */
    public String getType() {
        String ret=new String("");
        if (typeName==null) {
            return ret;
        }
        return typeName.getValue();
    }


    /**
     * sets the current datetime
     */
    public void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd'T'HH:mm:ss");
        dateTime=sdf.format(new Date());
    }

    /**
     * sets the datetime with a set string
     */
    public void setDate(String datetime) {
        dateTime=datetime;
    }


    /**
     * Return date as an XML structure
     */
    public XMLElement getXML() {
        XMLElement xdate=new XMLElement();
        xdate.setName("date");
        if (typeName!=null) {
            XMLElement xtn=typeName.getXML();
            xdate.addChild(xtn);
        }
        XMLElement xdatetime=new XMLElement();
        xdatetime.setName("datetime");
        xdatetime.setContent(dateTime);
        xdate.addChild(xdatetime);
        return(xdate);
    }

}
   
