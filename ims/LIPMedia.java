/*Vmap - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2001  <celt@gold.ac.uk>
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
/*$Id: LIPMedia.java,v 1.1 2005/05/22 14:20:48 veghead Exp $ */

package ims;

import java.util.Vector;
import java.lang.String;
import vmap.main.XMLElement;

public class LIPMedia {

    private String mediamode=null;
    private String mimetype=null;
    private String contentreftype=null;
    private String content=null;

    /**
     * overloaded constructor
     */
    public LIPMedia() {
        content=new String("");
        contentreftype=new String("uri");
        mimetype=new String("application/octet-stream");
        mediamode=new String("Application");
    }


    /**
     * overloaded constructor
     * @param content the actual media - a uri
     */
    public LIPMedia(String content) {
        this.content=new String(content);
        contentreftype=new String("uri");
        mimetype=new String("application/octet-stream");
        mediamode=new String("Application");
    }


    /**
     * Return the typename as an XML structure
     */
    public XMLElement getXML() {
        XMLElement xmedia=new XMLElement();
        xmedia.setName("media");
        xmedia.setContent(content);
        xmedia.setAttribute("mimetype",mimetype);
        xmedia.setAttribute("contentreftype",contentreftype);
        xmedia.setAttribute("mediamode",mediamode);
        return(xmedia);
    }
}
   
