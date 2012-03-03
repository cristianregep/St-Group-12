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
/*$Id: XMLElementAdapter.java,v 1.17 2005/05/29 13:14:25 veghead Exp $ */

package vmap.modes;

import vmap.main.XMLElement;
import vmap.main.VmapMain;
import vmap.main.Tools;
import vmap.modes.MindIcon;
import vmap.modes.MindMapLinkRegistry;
import vmap.modes.NodeAdapter;
import vmap.modes.mindmapmode.MindMapCloudModel;

import java.awt.Font;
import java.util.Vector;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Enumeration;
import java.io.File;

import ims.LIPTypeName;
import ims.LIPDate;
import ims.Identification;

public abstract class XMLElementAdapter extends XMLElement {

    private Object           userObject = null;
    private VmapMain     frame;
    private NodeAdapter      mapChild   = null;
    private Object      parent   = null;
    protected File currentFile;

    //   Font attributes

    private String fontName;
    private int    fontStyle = 0;
    private int    fontSize = 0;

    //   Icon attributes

    private String iconName;

    // arrow link attributes:
    protected Vector ArrowLinkAdapters;
    protected HashMap /* id -> target */  IDToTarget;

    //   Overhead methods

    public XMLElementAdapter(VmapMain frame) {
        this.frame = frame;
        this.ArrowLinkAdapters = new Vector();
        this.IDToTarget = new HashMap();
    }

    protected XMLElementAdapter(VmapMain frame, Vector ArrowLinkAdapters, HashMap IDToTarget) {
        this.frame = frame;
        this.ArrowLinkAdapters = ArrowLinkAdapters;
        this.IDToTarget = IDToTarget;
    }

    /** abstract method to create elements of my type (factory).*/
    abstract protected XMLElement  createAnotherElement();
    abstract protected NodeAdapter createNodeAdapter(VmapMain     frame);
    abstract protected EdgeAdapter createEdgeAdapter(NodeAdapter node, VmapMain frame);
    abstract protected CloudAdapter createCloudAdapter(NodeAdapter node, VmapMain frame);
    abstract protected ArrowLinkAdapter createArrowLinkAdapter(NodeAdapter source, NodeAdapter target, VmapMain frame);

    protected VmapMain getFrame() {
        return frame;
    }

    public Object getUserObject() {
        return userObject;
    }


    public NodeAdapter getMapChild() {
        return mapChild;
    }


    /**
     * This is effectively the VMAP parser for the XML!
     * Called by scanElement among other things.
     * @param name the name of the element
     */
    public void setName(String name)  {
        int i = 0;
        super.setName(name);

        // Create user object based on name
        for(i=0;i<Tools.nodeXML.length;i++) {
            if (name.equals(Tools.nodeXML[i])) {
                userObject = createNodeAdapter(frame);
                NodeAdapter node=(NodeAdapter)userObject;
                node.addIcon(new MindIcon(Tools.iconArray[i]));
            }
        }

        // Make sure we get a node created for portfolio and
        // portfolioPart
        if (name.equals("portfolio")) {
            userObject = createNodeAdapter(frame);
        } else if (name.equals("node")) {
            userObject = createNodeAdapter(frame);
        } else if (name.equals("edge")) {
            userObject = createEdgeAdapter(null, frame); 
        } else if (name.equals("arrowlink")) {
            userObject = createArrowLinkAdapter(null, null, frame);
        } else if (name.equals("portfolioPart")) {
            userObject = createNodeAdapter(frame);
            NodeAdapter nodep=(NodeAdapter)userObject;
            CloudAdapter newCloud = createCloudAdapter(null,frame);
            newCloud.setTarget(nodep);
            nodep.setCloud(newCloud);
        } else if (name.equals("date")) {
            userObject=new LIPDate();
        }
    }


    /**
      * Adds a child element to an XML element
      */
    public void addChild(XMLElement child) {
        String childName=child.getName();
        super.addChild(child);
        if (getName().equals("portfolio")) {
            mapChild = (NodeAdapter)this.getUserObject();
            NodeAdapter node = (NodeAdapter)userObject;
            node.setTitle("ePortfolio");
        }
        if (childName.indexOf("ext_")==0) {
            applyVmapMeta(child);
            return;
        } else if (childName.indexOf("description")==0) {
            applyDescription(child);
            return;
        } else if (childName.indexOf("typename")==0) {
            addTypeName(child);
        } else if (childName.indexOf("portfolio")==0) {
            NodeAdapter node = (NodeAdapter)userObject;
            Identification id=new Identification();
            id.setXML(child);
            node.setId(id);
        }

        // Look for tags that are valid for nodes 
        if (userObject instanceof NodeAdapter) {
            NodeAdapter node = (NodeAdapter)userObject;
            if (child.getUserObject() instanceof NodeAdapter) {
                node.insert((NodeAdapter)child.getUserObject(),-1); 
                // to the end without preferable... (PN)
            } else if (child.getUserObject() instanceof EdgeAdapter) {
                EdgeAdapter edge = (EdgeAdapter)child.getUserObject();
                edge.setTarget(node);
                node.setEdge(edge); 
            } else if (child.getUserObject() instanceof ArrowLinkAdapter) {
                ArrowLinkAdapter arrowLink = (ArrowLinkAdapter)child.getUserObject();
                arrowLink.setSource(node);
                // annotate this link: (later processed by caller.).
                //System.out.println("arrowLink="+arrowLink);
                ArrowLinkAdapters.add(arrowLink);
            } else if (childName.equals("icon")) {
                node.addIcon((MindIcon)child.getUserObject()); 
            } else if (childName.equals("date")) {
                if (((LIPDate)child.getUserObject()).getType().equalsIgnoreCase("create")) {
                    node.newCreateDate((LIPDate)child.getUserObject()); 
                    // If this isn't a create date, we assume it's an
                    // update date. Yes, this is only true until we add 
                    // other date types.
                } else {
                    node.newUpdateDate((LIPDate)child.getUserObject()); 
                }
            }

        // Look for elements that are valid for dates
        } else if (userObject instanceof LIPDate) {
            if (childName.equals("datetime")) {
                ((LIPDate)userObject).setDate(child.getContent());
            }
        }
    }


    /**
     * Walks through the description and applies
     * data to the associated node.
     * @param child the description node
     */
    public void applyDescription(XMLElement child) {
        if (!(userObject instanceof NodeAdapter)) {
            return;
        }
        XMLElement x;
        NodeAdapter node = (NodeAdapter)userObject;

        Vector c = child.getChildren();
        // walk through the children
        for (int i=0;i<c.size();i++) {
            x=(XMLElement)c.get(i);
            if (x.getName().indexOf("short")==0) {
                node.setTitle(x.getContent());
            } else if (x.getName().indexOf("full")==0) {
                applyFullDescription(x,node);
            } else if (x.getName().indexOf("long")==0) {
                node.setUserObject(x.getContent());
            }
        }
    }


    /**
     * Applies the description->full data to the node
     * @param x the "full" element
     * @param node the node to apply the data to
     */
    public void applyFullDescription(XMLElement x,NodeAdapter node) {
        Vector c = x.getChildren();
        for (int i=0;i<c.size();i++) {
            x=(XMLElement)c.get(i);
            if (x.getName().indexOf("media")==0) {
                node.setAttach((String)(x.getContent()).trim());
            }
        }
    }


    /**
     * Walks through this elements children and applies the Vmap meta
     * data to the associated node.
     * @param child the child node
     */
    public void applyVmapMeta(XMLElement child) {
        XMLElement x;
        Vector c = child.getChildren();
        // walk through the children
        for (int i=0;i<c.size();i++) {
            x=(XMLElement)c.get(i);
            //enumerate attribute names

            for(Enumeration e = x.enumerateAttributeNames() ; e.hasMoreElements() ;) {
                String elementName=(String)e.nextElement();
                Object value=x.getAttribute(elementName,null);
                setAttributeLocal(elementName,value);
            }
            if (!(x.getName().equals("vmapmeta"))) {
                continue;
            }

            // as this is a vmapmeta element, we traverse the children
            Vector cc = x.getChildren();
            for (int j=0;j<cc.size();j++) {
                x=(XMLElement)cc.get(j);
                if (x.getName().equals("font")) {
                    NodeAdapter node = (NodeAdapter)userObject;
                    node.setFont((Font)x.getUserObject()); 
                }
            }
        }
    }


    public void setAttribute(String name, Object value) {
        super.setAttribute(name,value);
        setAttributeLocal(name,value);
    }

    /**
     * Sets an attribute for the node associated with this element
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttributeLocal(String name, Object value) {
        // We take advantage of precondition that value != null.
        String sValue = value.toString();
        if (ignoreCase) {
            name = name.toUpperCase(); }

        if (userObject instanceof NodeAdapter) {
            //
            NodeAdapter node = (NodeAdapter)userObject;
            if (name.equals("text")) {
                node.setTitle(sValue);
            } else if (name.equals("folded")) {
                if (sValue.equals("true")) {
                    node.setFolded(true); }}
            else if (name.equals("position")) {
                // fc, 17.12.2003: Remove the left/right bug.
                node.setLeft(sValue.equals("left")); }
            else if (name.equals("cloudcolor")) {
                if (sValue.length() == 7) {
                    if (node.getCloud()!=null) {
                        ((MindMapCloudModel)node.getCloud()).setColor(Tools.xmlToColor(sValue));
                    }
                }
            } else if (name.equals("visible")) {
                if (node.getCloud()!=null) {
                    if (value.equals("false")) {
                        node.setVisible(false);
                    } else {
                        node.setVisible(true);
                    }
                }
            } else if (name.equals("color")) {
                if (sValue.length() == 7) {
                    node.setColor(Tools.xmlToColor(sValue)); 
                }
            } else if (name.equals("link")) {
                node.setLink(sValue); 
            } else if (name.equals("attach")) {
                    node.setAttach(sValue); 
            } 
            else if (name.equals("style")) {
                node.setStyle(sValue); 
            } else if (name.equals("id")) {
                // do not set label but annotate in list:
                //System.out.println("(sValue, node) = " + sValue + ", "+  node);
                IDToTarget.put(sValue, node);
            }
            return; 
        }

        if (userObject instanceof EdgeAdapter) {
            EdgeAdapter edge = (EdgeAdapter)userObject;
            if (name.equals("style")) {
                edge.setStyle(sValue); 
             } else if (name.equals("color")) {
                edge.setColor(Tools.xmlToColor(sValue)); 
            } else if (name.equals("width")) {
                if (sValue.equals("thin")) {
                    edge.setWidth(EdgeAdapter.WIDTH_THIN); 
                } else {
                    edge.setWidth(Integer.parseInt(sValue)); 
                }
            }
            return; 
        }

        if (userObject instanceof CloudAdapter) {
            CloudAdapter cloud = (CloudAdapter)userObject;
            if (name.equals("style")) {
                cloud.setStyle(sValue); }
            else if (name.equals("color")) {
                cloud.setColor(Tools.xmlToColor(sValue)); }
            else if (name.equals("width")) {
                cloud.setWidth(Integer.parseInt(sValue));
            }
            return; 
        }

        if (userObject instanceof ArrowLinkAdapter) {
            ArrowLinkAdapter arrowLink = (ArrowLinkAdapter)userObject;
            if (name.equals("style")) {
                arrowLink.setStyle(sValue); }
            else if (name.equals("color")) {
                arrowLink.setColor(Tools.xmlToColor(sValue)); }
            else if (name.equals("destination")) {
                arrowLink.setDestinationLabel(sValue); }
            else if (name.equals("referencetext")) {
                arrowLink.setReferenceText((sValue)); }
            else if (name.equals("startinclination")) {
                arrowLink.setStartInclination(Tools.xmlToPoint(sValue)); }
            else if (name.equals("endinclination")) {
                arrowLink.setEndInclination(Tools.xmlToPoint(sValue)); }
            else if (name.equals("startarrow")) {
                arrowLink.setStartArrow(sValue); }
            else if (name.equals("endarrow")) {
                arrowLink.setEndArrow(sValue); }
            else if (name.equals("width")) {
                arrowLink.setWidth(Integer.parseInt(sValue));
            }
            return; 
        }

        if (getName().equals("font")) {
            if (name.equals("size")) {
                fontSize = Integer.parseInt(sValue); 
            } else if (name.equals("name")) {
                fontName = sValue; 
            // Styling
            } else if (sValue.equals("true")) {
                if (name.equals("bold")) {
                    fontStyle+=Font.BOLD; 
                } else if (name.equals("italic")) {
                    fontStyle+=Font.ITALIC; 
                }
            }
        }

        /* icons */
        if (getName().equals("icon")) {
            if (name.equals("builtin")) {
                iconName = sValue; }
        }
    }


    protected void completeElement() {
        if (getName().equals("font")) {
            userObject =  frame.getController().getFontThroughMap
                          (new Font(fontName, fontStyle, fontSize)); 
            /* icons */
        } else if (getName().equals("icon")) {
            userObject =  new MindIcon(iconName); 
        }
    }



    /** Completes the links within the map. They are registered in the registry.*/
    public void processUnfinishedLinks(MindMapLinkRegistry registry) {
        // add labels to the nodes:
        setIDs(IDToTarget, registry);
        // complete arrow links with right labels:
        for(int i = 0; i < ArrowLinkAdapters.size(); ++i) {
            ArrowLinkAdapter arrowLink = (ArrowLinkAdapter) ArrowLinkAdapters.get(i);
            String oldID = arrowLink.getDestinationLabel();
            NodeAdapter target = null;
            String newID = null;
            // find oldID in target list:
            if(IDToTarget.containsKey(oldID)) {
                // link present in the xml text
                target = (NodeAdapter) IDToTarget.get(oldID);
                newID = registry.getLabel(target);
            } else if(registry.getTargetForID(oldID) != null) {
                // link is already present in the map (paste).
                target = (NodeAdapter) registry.getTargetForID(oldID);
                if(target == null) {
                    // link target is in nowhere-land
                    System.err.println("Cannot find the label " + oldID + " in the map. The link "+arrowLink+" is not restored.");
                    continue;
                }
                newID = registry.getLabel(target);
                if( ! newID.equals(oldID) ) {
                    System.err.println("Servere internal error. Looked for id " + oldID + " but found "+newID + " in the node " + target+".");
                    continue;
                }
            } else {
                // link target is in nowhere-land
                System.err.println("Cannot find the label " + oldID + " in the map. The link "+arrowLink+" is not restored.");
                continue;
            }
            // set the new ID:
            arrowLink.setDestinationLabel(newID);
            // set the target:
            arrowLink.setTarget(target);
            // add the arrowLink:
            //System.out.println("Node = " + target+ ", oldID="+oldID+", newID="+newID);
            registry.registerLink(arrowLink);

        }
    }


    /**Recursive method to set the ids of the nodes.*/
    private void setIDs(HashMap IDToTarget, MindMapLinkRegistry registry) {
        for(Iterator i = IDToTarget.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            NodeAdapter target = (NodeAdapter) IDToTarget.get(key);
            MindMapLinkRegistry.ID_Registered newState = registry.registerLinkTarget(target, key /* Proposed name for the target, is changed by the registry, if already present.*/);
            String newId = newState.getID();
            // and in the cutted case:
            // search for links to this ids that have been cutted earlier:
            Vector cuttedLinks = registry.getCuttedNode(key /* old target id*/);
            for(int j=0; j < cuttedLinks.size(); ++j) {
                ArrowLinkAdapter link = (ArrowLinkAdapter) cuttedLinks.get(j);
                // repair link
                link.setTarget(target);
                link.setDestinationLabel(newId);
                // and set it:
                registry.registerLink(link);
            }
        }
    }


    /**
     * Adds a typename to the node associated with the 
     * current element
     * @param child the child element which should be "typename"
     */
    private void addTypeName(XMLElement child)
    {
        LIPTypeName tn=new LIPTypeName();
        XMLElement x;
        Vector c = child.getChildren();
        // walk through the children
        for (int i=0;i<c.size();i++) {
            x=(XMLElement)c.get(i);
            //enumerate attribute names
            for(Enumeration e = x.enumerateAttributeNames() ; e.hasMoreElements() ;) {
                String attributeName=(String)e.nextElement();
                if ((x.getName().equals("tysource"))&&(attributeName.equals("sourcetype"))) {
                    Object st=x.getAttribute("sourcetype");
                    Object value=x.getContent();
                    tn.setSource((String)st,(String)value);
                }
            }
            if (x.getName().equals("tyvalue")) {
                    String value=x.getContent();
                    tn.setValue((String)value);
            }
        }
        if (userObject instanceof NodeAdapter) {
            NodeAdapter node = (NodeAdapter)userObject;
            node.setTypeName(tn);
        } else if (userObject instanceof LIPDate) {
            LIPDate date = (LIPDate)userObject;
            date.setTypeName(tn);
        }
    }
}
