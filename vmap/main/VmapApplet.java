/*FreeMind - A Program for creating and viewing Mindmaps
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
/*$Id: VmapApplet.java,v 1.3 2005/02/02 20:53:38 veghead Exp $ */

package vmap.main;

import vmap.view.mindmapview.MapView;
import vmap.controller.MenuBar;
import vmap.controller.Controller;
import java.io.InputStream;
import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.Enumeration;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import javax.swing.*;

public class VmapApplet extends JApplet implements VmapMain {

    public static final String version = "0.1g";
    //    public static final String defaultPropsURL;
    public URL defaultPropsURL;
    public static Properties defaultProps;
    public static Properties userProps;
    private JScrollPane scrollPane = new JScrollPane();
    private MenuBar menuBar;
    private PropertyResourceBundle resources;
    private JLabel status = new JLabel();
    Controller c;//the one and only controller


    public VmapApplet() {
    }//Constructor

    public boolean isApplet() {
       return true; }

    public File getPatternsFile() {
       return null; }

    public Controller getController() {
	return c;
    }

    public MapView getView() {
	return c.getView();
    }

    public void setView(MapView view) {
 	scrollPane.setViewportView(view);
    }

    public MenuBar getVmapMenuBar() {
	return menuBar;
    }

    public Container getViewport() {
		return scrollPane.getViewport();
    }

    public String getVmapVersion() {
        return version;
    }

    // "dummy" implementation of the interface (PN)
    public int getWinHeight() {
      return getRootPane().getHeight();
    }
    public int getWinWidth() {
      return getRootPane().getWidth();
    }
    public int getWinState() {
      return 6;  
    }

    /**
     * Returns the ResourceBundle with the current language
     */
    public ResourceBundle getResources() {
	if (resources==null) {
	    String lang = userProps.getProperty("language");
            // Veg - force English for now
	    lang = "en";
	    try {
		URL myurl = getResource("Resources_"+lang.trim()+".properties");
		InputStream in = myurl.openStream();
		resources = new PropertyResourceBundle(in);
		in.close();
	    } catch (Exception ex) {
		System.err.println("Error loading Resources");
		return null;
	    }
	}
	return resources;
    }

    public String getProperty(String key) {
	return userProps.getProperty(key);
    }
    
    public void setProperty(String key, String value) {
    }

    public String getVmapDirectory() {return null;};

    static int iMaxNodeWidth = 0;
	
    static public int getMaxNodeWidth(){
       if (iMaxNodeWidth == 0){
          try{
             iMaxNodeWidth = Integer.parseInt(userProps.getProperty("max_node_width"));	
          }
          catch (NumberFormatException nfe) {
             iMaxNodeWidth = Integer.parseInt(userProps.getProperty("el__max_default_window_width"));
          }
       }
       return iMaxNodeWidth;
    }

    public void saveProperties() {
    }

    public void setTitle(String title) {
    }

    public void out (String msg) {
	status.setText(msg);
    }

    public void err (String msg) {
	status.setText("ERROR: "+msg);
    }

    public void openDocument(URL doc) throws Exception {
	getAppletContext().showDocument(doc,"_blank");
    }

    public void start() {
       // Make sure the map is centered at the very beginning.
       try {
          if (getView() != null) {
             getView().moveToRoot(); }
          else {
             System.err.println("View is null."); }}
       catch (Exception e) { e.printStackTrace(); }
    }

   /*
    public void stop() {
    }

    public void destroy() {
    }
    */

    public void setWaitingCursor(boolean waiting) {
       if (waiting) {
          getRootPane().getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          getRootPane().getGlassPane().setVisible(true); }
       else {
          getRootPane().getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          getRootPane().getGlassPane().setVisible(false); }}

    public URL getResource(String name) {
	return this.getClass().getResource("/"+name);
    }

    public java.util.logging.Logger getLogger(String forClass) {
        /* Applet logging is anonymous due to security reasons. (Calling a named logger is answered with a security exception).*/
        return java.util.logging.Logger.getAnonymousLogger();
    }

    public void init() {
	JRootPane rootPane = createRootPane();
 	//load properties
	defaultPropsURL = getResource("vmap.properties");
	try {
	    //load properties
	    defaultProps = new Properties();
	    InputStream in = defaultPropsURL.openStream();
	    defaultProps.load(in);
 	    in.close();
	    userProps = defaultProps;
 	} catch (Exception ex) {
           System.err.println("Could not load properties.");
 	}

        //try to overload some properties with given command-line (html tag) Arguments
        Enumeration allKeys = userProps.propertyNames();
        while (allKeys.hasMoreElements()) {
           String key = (String)allKeys.nextElement();
           String val = getParameter(key);
           //	    System.out.println("Got prop:"+key+":"+val);
           if (val != null  &&  val != "") {
              userProps.setProperty(key,val);
           }
        }
            
        //set Look&Feel
        String lookAndFeel = "";
        try {
           lookAndFeel = userProps.getProperty("lookandfeel");
           if (lookAndFeel.equals("windows")) {
              UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
           } else if (lookAndFeel.equals("motif")) {
              UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
           } else if (lookAndFeel.equals("mac")) {
              //Only available on macOS
              UIManager.setLookAndFeel("javax.swing.plaf.mac.MacLookAndFeel");
           } else if (lookAndFeel.equals("metal")) {
              UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
           } else if (lookAndFeel.equals("nothing")) {
           } else {
               // default.
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
           }
        } catch (Exception ex) {
           System.err.println("Error while setting Look&Feel"+lookAndFeel);
        }

 	//Layout everything
 	getContentPane().setLayout( new BorderLayout() );

	c = new Controller(this);

	if (Tools.safeEquals(getProperty("antialiasEdges"), "true")) {
           c.setAntialiasEdges(true); }
	if (Tools.safeEquals(getProperty("antialiasAll"), "true")) {
           c.setAntialiasAll(true); }

 	//Create the MenuBar
	menuBar = new MenuBar(c); //new MenuBar(c);
 	setJMenuBar(menuBar);
        c.setToolbarVisible(false);
        c.setMenubarVisible(false);

	//Create the scroll pane.
		
	getContentPane().add( scrollPane, BorderLayout.CENTER );
	getContentPane().add( status, BorderLayout.SOUTH );

        SwingUtilities.updateComponentTreeUI(this); // Propagate LookAndFeel to JComponents

	c.changeToMode(getProperty("initial_mode"));
    }
}
