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
/*$Id: BrowseXMLElement.java,v 1.1.1.1 2004/09/11 17:31:33 veghead Exp $ */


package vmap.modes.browsemode;

import vmap.main.XMLElement;
import vmap.main.VmapMain;
import vmap.main.Tools;
import vmap.modes.NodeAdapter;
import vmap.modes.EdgeAdapter;
import vmap.modes.CloudAdapter;
import vmap.modes.ArrowLinkAdapter;
import vmap.modes.MindIcon;
import vmap.modes.XMLElementAdapter;
import vmap.modes.browsemode.BrowseEdgeModel;
import vmap.modes.browsemode.BrowseCloudModel;

import java.awt.Font;
import java.util.Vector;
import java.util.HashMap;

public class BrowseXMLElement extends XMLElementAdapter {

   public BrowseXMLElement(VmapMain frame) {
       super(frame);
   }

    protected BrowseXMLElement(VmapMain frame, Vector ArrowLinkAdapters, HashMap IDToTarget) {
        super(frame, ArrowLinkAdapters, IDToTarget);
    }

    /** abstract method to create elements of my type (factory).*/
    protected XMLElement  createAnotherElement(){
    // We do not need to initialize the things of XMLElement.
        return new BrowseXMLElement(getFrame(), ArrowLinkAdapters, IDToTarget);
    }
    protected NodeAdapter createNodeAdapter(VmapMain     frame){
        return new BrowseNodeModel(frame);
    }
    protected EdgeAdapter createEdgeAdapter(NodeAdapter node, VmapMain frame){
        return new BrowseEdgeModel(node, frame); 
    }
    protected CloudAdapter createCloudAdapter(NodeAdapter node, VmapMain frame){
        return new BrowseCloudModel(node, frame); 
    }
    protected ArrowLinkAdapter createArrowLinkAdapter(NodeAdapter source, NodeAdapter target, VmapMain frame) {
        return new BrowseArrowLinkModel(source,target,frame);
    }

}

