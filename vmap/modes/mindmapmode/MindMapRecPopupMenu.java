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
/*$Id: MindMapRecPopupMenu.java,v 1.7 2005/04/09 15:25:19 veghead Exp $ */

package vmap.modes.mindmapmode;

import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.lang.String;


public class MindMapRecPopupMenu extends JPopupMenu {

    private MindMapController c;
    JMenu recMenu;

    public MindMapRecPopupMenu(MindMapController c, String s) {
        this.c = c;
        recMenu=new JMenu(s);
	recMenu.add(c.newCloud);
	recMenu.add(c.newRecActivity);
	recMenu.add(c.newRecGoal);
	recMenu.add(c.newRecAffiliation);
	recMenu.add(c.newRecCompetency);
	recMenu.add(c.newRecInterest);
	recMenu.add(c.newRecProduct);
	recMenu.add(c.newRecReflect);
	recMenu.add(c.newRecAssert);
	//recMenu.add(c.newRecQCL);
        add(recMenu);
    }

}
