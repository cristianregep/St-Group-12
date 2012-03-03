/*Vmap - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2001  Joerg Mueller <joergmueller@bigfoot.com>
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
/*$Id: MindMapArrowLinkModel.java,v 1.1.1.1 2004/09/11 17:31:34 veghead Exp $ */

package vmap.modes.mindmapmode;

import vmap.main.VmapMain;
import vmap.modes.MindMapNode;
import vmap.modes.ArrowLinkAdapter;
import vmap.main.Tools;
import java.awt.Color;

import vmap.main.XMLElement;

public class MindMapArrowLinkModel extends ArrowLinkAdapter {

    public MindMapArrowLinkModel(MindMapNode source,MindMapNode target,VmapMain frame) {
        super(source,target,frame);
    }

    /* maybe this method is wrong here, but ...*/
    public Object clone() {
        return super.clone();
    }

    public XMLElement save() {
	    XMLElement arrowLink = new XMLElement();
	    arrowLink.setName("arrowlink");

	    if (style != null) {
            arrowLink.setAttribute("style",style);
	    }
	    if (color != null) {
            arrowLink.setAttribute("color",Tools.colorToXml(color));
	    }
        if(getDestinationLabel() != null) {
            arrowLink.setAttribute("destination",getDestinationLabel());
        }
        if(getReferenceText() != null) {
            arrowLink.setAttribute("referenceText",getReferenceText());
        }
        if(getStartInclination() != null) {
            arrowLink.setAttribute("startInclination",getStartInclination().toString());
        }
        if(getEndInclination() != null) {
            arrowLink.setAttribute("endInclination",getEndInclination().toString());
        }
        if(getStartArrow() != null)
            arrowLink.setAttribute("startArrow",(getStartArrow()));
        if(getEndArrow() != null)
            arrowLink.setAttribute("endArrow",(getEndArrow()));
	    return arrowLink;
    }

    public String toString() { return "Source="+getSource()+", target="+getTarget()+", "+save().toString(); }

}
