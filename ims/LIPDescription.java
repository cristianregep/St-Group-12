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
/*$Id: LIPDescription.java,v 1.2 2005/05/29 12:21:14 veghead Exp $ */

package ims;

import java.util.Vector;
import java.lang.String;
import vmap.main.XMLElement;
import ims.LIPMedia;

public class LIPDescription {

    private String dshort=null;
    private String dlong=null;
    private String comment=null;
    private Vector mediaList=new Vector();


    /**
     * overloaded constructor
     */
    public LIPDescription() {
        dshort=new String("");
    }


    /**
     * overloaded constructor
     * @param dshort short description
     * @param dlong long description
     */
    public LIPDescription(String dshort, String dlong) {
        this.dshort=dshort;
        this.dlong=dlong;
    }


    /**
     * overloaded constructor
     * @param dshort short description
     */
    public LIPDescription(String dshort) {
        this.dshort=dshort;
    }


    /**
     * Get the list of available medias
     * @returns Vector of media object
     */
    public Vector getMediaList() {
        return mediaList;
    }


    /**
     * Return the typename as an XML structure
     */
    public XMLElement getXML() {
        boolean hasFull=false;
        XMLElement xdescription=new XMLElement();
        xdescription.setName("description");
        XMLElement xdshort=new XMLElement();
        xdshort.setName("short");
        XMLElement xdlong=new XMLElement();
        xdlong.setName("long");
        XMLElement xfull=new XMLElement();
        xfull.setName("full");
        XMLElement xcomment=new XMLElement();
        xcomment.setName("comment");
        XMLElement xmedia=null;
        
        if (comment!=null) {
            xcomment.setContent(comment);
            xfull.addChild(xcomment);
            hasFull=true;
        }
        if (mediaList!=null) {
            for(int i=0;i<mediaList.size();i++) {
                xmedia=((LIPMedia)mediaList.elementAt(i)).getXML();
                xfull.addChild(xmedia);
            }
            hasFull=true;
        }
        if (dshort!=null) {
            xdshort.setContent(dshort);
            xdescription.addChild(xdshort);
        }
        if (dlong!=null) {
            xdlong.setContent(dlong);
            xdescription.addChild(xdlong);
        }
        if (hasFull) {
            xdescription.addChild(xfull);
        }
        return(xdescription);
    }


    public void addMediaURI(String uri) {
        LIPMedia media=new LIPMedia(uri);
        this.mediaList.add(media);
    }
}
   
