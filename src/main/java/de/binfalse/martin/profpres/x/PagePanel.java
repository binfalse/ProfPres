/**
 * 
 */
package de.binfalse.martin.profpres.x;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import com.sun.pdfview.Watchable;

import de.binfalse.martin.profpres.mgmt.PresentationListener;



/**
 * @author Martin Scharm
 * 
 */
public class PagePanel
	extends JButton
{
	PDFPage page;
	BufferedImage bi;
	boolean showLargeImage;
	
	public PagePanel (PresentationListener pl)//int winWidth, int winHeight, PDFPage page)
	{
		super ();
		//this.page = page;
		this.setBackground (Color.WHITE);
		//render (winWidth, winHeight);
		showLargeImage = true;
		this.addKeyListener (pl);
		this.addMouseListener (pl);
		this.addMouseWheelListener (pl);
	}
	
	/*public void showPage (PDFPage page)
	{
		this.page = page;
		valid = false;
		repaint ();
	}*/
	
	public void setPage (BufferedImage bi)
	{
		this.bi = bi;
		repaint ();
	}

	/*private void render (int winWidth, int winHeight)
	{
		renderBig (winWidth, winHeight);
		renderSmall (300, 300);
	}*/
	
	public void paint (Graphics g)
	{
		//super.paint (g);
		System.out.println ("paint");
		//render (g);
		if (bi != null)
			g.drawImage (bi, 0, 0, bi.getWidth (), bi.getHeight (), null);
	}
	
}
