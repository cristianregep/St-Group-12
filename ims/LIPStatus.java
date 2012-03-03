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
/*$Id: LIPStatus.java,v 1.2 2005/05/29 13:14:24 veghead Exp $ */

package ims;

import java.util.Vector;
import java.lang.String;
import vmap.main.XMLElement;

public class LIPStatus {

    private LIPDate date;
    private LIPDescription desc;
    private LIPTypeName typeName;


    /**
     * constructor
     */
    public LIPStatus() {
        typeName=new LIPTypeName();
    }


    /**
     * constructor
     */
    public LIPStatus(String tysourcet,String tysourcev, String tyval) {
        typeName=new LIPTypeName(tysourcet,tysourcev,tyval);
    }


    /**
     * Return the status as an XML structure
     */
    public XMLElement getXML() {
        XMLElement xstatus=new XMLElement();
        xstatus.setName("status");
        if (typeName!=null) {
            XMLElement xtn=typeName.getXML();
            xstatus.addChild(xtn);
        }
        if (desc!=null) {
            XMLElement xdesc=desc.getXML();
            xstatus.addChild(xdesc);
        }
        if (date!=null) {
            XMLElement xdate=date.getXML();
            xstatus.addChild(xdate);
        }
        return(xstatus);
    }

}
   
