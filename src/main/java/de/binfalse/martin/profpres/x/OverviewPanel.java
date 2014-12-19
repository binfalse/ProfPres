/**
 * 
 */
package de.binfalse.martin.profpres.x;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.pdfview.PDFPage;

import de.binfalse.martin.profpres.mgmt.PresentationListener;


/**
 * @author Martin Scharm
 *
 */
public class OverviewPanel
	extends JPanel
{
	Vector<BufferedImage> pages;
	PresentationListener pl;
	public OverviewPanel (int winWidth, int winHeight, Vector<BufferedImage> pages, PresentationListener pl)
	{
		super ();
		this.pl = pl;
		this.addMouseListener (pl);
		this.addKeyListener (pl);
		this.pages = pages;
		this.setBackground (Color.BLACK);
		init (winWidth, winHeight);
	}
	
	private void init (int winWidth, int winHeight)
	{
// calc height and size -> num rows/cols
		int cols = winWidth / (PublicFrame.PREVIEW_WIDTH + 10);
		int rows = pages.size () / cols + 1;
		System.out.println (pages.size () + "->" + rows + ":" + cols);
		
    JPanel p = new JPanel();
    p.addMouseListener (pl);
    p.addKeyListener (pl);
    p.setPreferredSize(new Dimension (cols * (PublicFrame.PREVIEW_WIDTH + 10), rows * (PublicFrame.PREVIEW_HEIGHT+ 10)));
    p.setSize(cols * (PublicFrame.PREVIEW_WIDTH), rows * (PublicFrame.PREVIEW_HEIGHT));
    p.setLayout(new GridLayout(rows, cols, 10, 10));
    p.setBackground (Color.BLACK);
    for (int i = 0; i < pages.size (); i++)
    		p.add (new PreviewPanel (pages.elementAt (i), i, pl));
    
    JScrollPane scrollpane = new JScrollPane (p);
    scrollpane.setSize (new Dimension (winWidth, winHeight));
    scrollpane.addKeyListener (pl);
    scrollpane.getVerticalScrollBar().setUnitIncrement(10);
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, winWidth, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, winHeight, Short.MAX_VALUE)
    );
    
	}
}
