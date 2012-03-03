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
/*$Id: ModeController.java,v 1.7 2005/04/16 14:02:26 veghead Exp $ */

package vmap.modes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPopupMenu;

import vmap.main.XMLParseException;
import vmap.view.mindmapview.NodeView;

public interface ModeController {

    public void load(File file) throws FileNotFoundException, IOException, XMLParseException;
    public boolean save(File file);
    public boolean save(File file,boolean present);
    public void addNew(NodeView target, int newNodeMode, KeyEvent e);
    public void addNew(NodeView target, int newNodeMode, KeyEvent e,String iconType);
    public void addNew(NodeView target, int newNodeMode, KeyEvent e,String iconType,String title);
    public void newMap();
    public boolean save();
    public boolean saveAs();
    public void open();
    //    public void edit(NodeView node, NodeView toBeSelected);
    public boolean close();
    public boolean close(boolean force);
    public void doubleClick(MouseEvent e);
    public void plainClick(MouseEvent e);
    public void openAttachment(MouseEvent e);
    public void toggleFolded();

    public boolean isBlocked();
    public void edit(KeyEvent e, boolean addNew, boolean editLong);
    public void mouseWheelMoved(MouseWheelEvent e);
    /** This extends the currently selected nodes. 
        @return true, if the method changed the selection.*/
    public boolean extendSelection(MouseEvent e);

    public JPopupMenu getPopupMenu();
    public JPopupMenu getRecPopupMenu();
    public void showPopupMenu(MouseEvent e);
    /** This returns a context menu for an object placed in the background pane.*/
    public JPopupMenu getPopupForModel(java.lang.Object obj);

    public void nodeChanged(MindMapNode n);
    public void anotherNodeSelected(MindMapNode n);
    public void setAvailableActions();
}
