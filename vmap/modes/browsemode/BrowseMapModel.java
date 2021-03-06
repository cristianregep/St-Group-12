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
/*$Id: BrowseMapModel.java,v 1.2 2005/04/16 14:02:26 veghead Exp $ */

package vmap.modes.browsemode;


import vmap.main.VmapMain;
import vmap.modes.MapAdapter;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessControlException;


// link registry.
import vmap.modes.LinkRegistryAdapter;
import vmap.modes.MindMapLinkRegistry;


public class BrowseMapModel extends MapAdapter {

    private URL url;
    private LinkRegistryAdapter linkRegistry;

    //
    // Constructors
    //
    public BrowseMapModel(VmapMain frame) {
        this(null, frame);
    }

    public BrowseMapModel( BrowseNodeModel root, VmapMain frame ) {
        super(frame);
        if(root != null)
            setRoot(root);
        else
           setRoot(new BrowseNodeModel(getFrame().getResources().getString("new_mindmap"), getFrame())); 
        // register new LinkRegistryAdapter
        linkRegistry = new LinkRegistryAdapter();
    }

    //
    // Other methods
    //
    public MindMapLinkRegistry getLinkRegistry() {
        return linkRegistry;
    }

    public String toString() {
	if (getURL() == null) {
	    return null;
	} else {
	    return getURL().toString();
	}
    }

    public File getFile() {
	return null;
    }

    protected void setFile() {
    }

    
    /**
       * Get the value of url.
       * @return Value of url.
       */
    public URL getURL() {
       return url;}
    
    /**
       * Set the value of url.
       * @param v  Value to assign to url.
       */
    public void setURL(URL  v) {this.url = v;}
    

    public boolean save(File file) {
    	return true;
    }

    public boolean save(File file,boolean pres) {
    	return true;
    }
    
    public boolean isSaved() {
	return true;
    }

    public void load(File file) throws FileNotFoundException {
	throw new FileNotFoundException();
    }

    public void load(URL url) throws Exception {
	setURL(url);
	BrowseNodeModel root = loadTree(url);
	if (root != null) {
	    setRoot(root);
	} else {
	    //	    System.err.println("Err:"+root.toString());
	    throw new Exception();
	}
    }

    BrowseNodeModel loadTree(URL url) {
	BrowseNodeModel root = null;

	//NanoXML Code
	//XMLElement parser = new XMLElement();
        BrowseXMLElement mapElement = new BrowseXMLElement(getFrame());

        InputStreamReader urlStreamReader = null;
        URLConnection uc = null;

        try {
           urlStreamReader = new InputStreamReader( url.openStream() ); }
        catch (AccessControlException ex) {
           getFrame().getController().errorMessage("Could not open URL "+url.toString()+". Access Denied.");
           System.err.println(ex);
           return null; }
        catch (Exception ex) {
           getFrame().getController().errorMessage("Could not open URL "+url.toString()+".");
           System.err.println(ex);
           // ex.printStackTrace();
           return null;
        }

	try {
	    mapElement.parseFromReader(urlStreamReader);
	} catch (Exception ex) {
	    System.err.println(ex);
	    return null;
	}
    // complete the arrow links:
    mapElement.processUnfinishedLinks(getLinkRegistry());
    root = (BrowseNodeModel) mapElement.getMapChild();
	return root;
    }
}
