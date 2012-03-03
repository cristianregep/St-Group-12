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
/*$Id: MindIcon.java,v 1.9 2005/04/12 19:35:38 veghead Exp $ */

package vmap.modes;

import java.util.*;
import java.io.*;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/* for resources:*/
import vmap.main.VmapMain;

/**
 * This class represents a MindIcon than can be applied
 * to a node or a whole branch.
 */
public class MindIcon {
    private String name;
    private String description;
    private Icon   associatedIcon;
    private static Vector mAllIconNames;
    private static Icon iconNotFound;

    public MindIcon(String name) {
       setName(name); 
       associatedIcon=null;
    }

    public String toString() {
        return "Icon_name: "+name; 
    }

    /**
       * Get the value of name.
       * @return Value of name.
       */
    public String getName() {
       // DanPolansky: it's essential that we do not return null
       // for saving of the map.
       return name == null ? "notfound" : name; 
    }
    
    /**
       * Set the value of name.
       * @param v  Value to assign to name.
       */
    public void setName(String name) {
        
        this.name = name; 
        return;
    } 
    
    /**
       * Get the value of description (in local language).
       * @return Value of description.
       */
    public String getDescription(VmapMain frame) {
        /* GRRR: doubled code from controller: */
        String resource = new String("icon_"+getName());
        try {
            return frame.getResources().getString(resource); }
        catch (Exception ex) {
            System.err.println("Warning - resource string not found:"+resource);
            return getName(); 
        }
    }
    
    public String getIconFileName() {
        return "images/icons/"+getName()+".png";
    }

    public Icon getIcon(VmapMain frame) {
        // We need the frame to be able to obtain the resource URL of the icon.
        if (iconNotFound == null) {
           iconNotFound = new ImageIcon(frame.getResource("images/IconNotFound.png")); }

        if(associatedIcon != null)
            return associatedIcon;
        if ( name != null ) {
           URL imageURL = frame.getResource(getIconFileName());
           Icon icon = imageURL == null ? iconNotFound : new ImageIcon(imageURL);
           setIcon(icon);
           return icon; }
        else {
           setIcon(iconNotFound);
           return iconNotFound; }}      
            
    /**
       * Set the value of icon.
       * @param v  Value to assign to icon.
       */
    protected void setIcon(Icon  _associatedIcon) {
       this.associatedIcon = _associatedIcon; }

    public static Vector getAllIconNames () {
        if(mAllIconNames != null)
            return mAllIconNames;
        Vector mAllIconNames = new Vector();
        mAllIconNames.add("cloud");
        mAllIconNames.add("recactivity");
        mAllIconNames.add("recgoal");
        mAllIconNames.add("recaffiliation");
        mAllIconNames.add("recproduct");
        mAllIconNames.add("reccompetency");
        mAllIconNames.add("recinterest");
        mAllIconNames.add("recreflect");
        mAllIconNames.add("recassertion");
        //mAllIconNames.add("recqcl");
        return mAllIconNames;
    }

}
