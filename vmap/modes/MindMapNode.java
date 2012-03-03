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
/*$Id: MindMapNode.java,v 1.13 2005/05/29 13:14:25 veghead Exp $ */

package vmap.modes;

import vmap.modes.MindIcon;
import vmap.view.mindmapview.NodeView;
// clouds, fc, 08.11.2003:
import vmap.modes.MindMapCloud;
// end clouds.
// links, fc, 08.11.2003:
import vmap.modes.MindMapLink;
// end links.
import java.util.ListIterator;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.Vector;
import java.io.File;
import ims.LIPTypeName;
import ims.Identification;

public interface MindMapNode extends MutableTreeNode {
   
    public static final String STYLE_BUBBLE = "bubble";
    public static final String STYLE_FORK = "fork";
	
    public void setTypeName(LIPTypeName liptype);
    public LIPTypeName getTypeName();

    ListIterator childrenFolded();

    ListIterator childrenUnfolded();

    boolean hasChildren();

    int getChildPosition(MindMapNode childNode);

    MindMapNode getPreferredChild();
    void setPreferredChild(MindMapNode node);
    
    int getNodeLevel();

    String getLink();

    String getTitle();

    void setAttach(String attachPath);

    String getAttach();

    File getAttachFile();

    void setTitle(String newtitle);

    MindMapEdge getEdge();

    Color getColor();

    String getStyle();

    MindMapNode getParentNode();

    boolean isBold();

    boolean isItalic();

    boolean isUnderlined();

    Font getFont();
    
	String getFontSize();
    
	String getFontFamilyName();
    
    NodeView getViewer();

    void setViewer( NodeView viewer );

    String toString();
	 
    TreePath getPath();
    
    boolean isDescendantOf(MindMapNode node);
    
    boolean isRoot();

    boolean isFolded();

    vmap.main.Tools.BooleanHolder isLeft();
    
    void setLeft(boolean isLeft);

    void setFolded(boolean folded);

    void setFont(Font font);

    void setLink(String link);

    void setFontSize(int fontSize);

    void setColor(Color color);

    // fc, 06.10.2003:
    Vector/*of MindIcon s*/ getIcons();

    void   addIcon(MindIcon icon);

    /* @return returns the new amount of icons.*/
    int   removeLastIcon();
    // end, fc, 24.9.2003

//     //fc, 01.11.2003:
//     /** \@return returns the label of the node, if applicable. otherwise null.*/
//     String getLabel();

//     void setLabel(String newLabel); 

//     Vector/* of MindMapLink s*/ getReferences();
    
//     void removeReferenceAt(int i);

//     void addReference(MindMapLink referenceStruct);
//     // end links, fc, 01.11.2003.

    // clouds, fc, 08.11.2003:
    MindMapCloud getCloud();
    // end clouds.
        
    MindMapNode shallowCopy();

    boolean canHaveAsChild(MindMapNode node);

    String getPrimaryIcon();

    public Identification getId();

    public void setId(Identification id);

    public void setVisible(boolean set);

    public boolean getVisible();

    public void newUpdateDate(String tyst,String tysv, String tyv);

    public void newCreateDate(String tyst, String tysv, String tyv);

    public void newStatus(String tyst, String tysv, String tyv);

    public void updateDate();

    public void setCreateDate(String datetime);

    public void setUpdateDate(String datetime);
}
