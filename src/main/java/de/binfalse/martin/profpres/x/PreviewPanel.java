/**
 * 
 */
package de.binfalse.martin.profpres.x;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.binfalse.martin.profpres.mgmt.PresentationListener;


/**
 * @author Martin Scharm
 *
 */
public class PreviewPanel
	extends JButton
{
	private BufferedImage bi;
	private int pageNumber;
	
	public PreviewPanel (BufferedImage bi, int pageNumber, final PresentationListener pl)
	{
		super ();
		//super ("" + pageNumber);

		System.out.println ("init overview " + pageNumber);
		this.bi = bi;
		this.pageNumber = pageNumber;
		System.out.println (bi.getWidth () + " x " + bi.getHeight ());
		this.setPreferredSize (new Dimension (bi.getWidth (), bi.getHeight ()));
		this.setSize (bi.getWidth (), bi.getHeight ());
		this.setBackground (Color.WHITE);
		/*this.addActionListener (new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent e)
			{
				pl.actionPerformed (e);
				System.out.println ("test");
			}});*/
		this.addActionListener (pl);
		this.addKeyListener (pl);
	}
	
	public int getPageNumber ()
	{
		return pageNumber;
	}
	
	
	public void paint (Graphics g)
	{
		//super.paint (g);
		System.out.println ("paint overview " + pageNumber);
		//render (g);
		if (bi != null)
			g.drawImage (bi, 0, 0, bi.getWidth (), bi.getHeight (), null);
		//g.setColor (Color.BLUE);
		//g.drawRect (0, 0, pageNumber*10, pageNumber*10);
		
	}
}
