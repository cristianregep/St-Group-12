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
/*$Id: MindMapCloudModel.java,v 1.2 2005/01/19 11:39:31 veghead Exp $ */

package vmap.modes.mindmapmode;

import vmap.main.VmapMain;
import vmap.modes.MindMapNode;
import vmap.modes.CloudAdapter;
import vmap.main.Tools;
import java.awt.Color;

import vmap.main.XMLElement;

public class MindMapCloudModel extends CloudAdapter {

    public MindMapCloudModel(MindMapNode node,VmapMain frame) {
        super(node,frame);
    }

    public XMLElement save() {
	    XMLElement cloud = new XMLElement();
	    cloud.setName("portfolioPart");

	    if (style != null) {
            cloud.setAttribute("style",style);
	    }
	    if (color != null) {
            cloud.setAttribute("color",Tools.colorToXml(color));
	    }
        if(width != DEFAULT_WIDTH) {
            cloud.setAttribute("width",Integer.toString(width));
        }
	    return cloud;
    }

}
