/**
 * 
 */
package de.binfalse.martin.profpres.x;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.JPanel;

import de.binfalse.martin.profpres.mgmt.Screen;


/**
 * @author Martin Scharm
 *
 */
public class ScreensPanel extends JPanel
{
	private Screen activeScreen;
	private Screen [] screens;
	public ScreensPanel (Screen [] screens)
	{
		super ();
		this.screens = screens;
		init ();
	}
	
	private void init ()
	{
		int minWidth = 0;
		int minHeight = 0;
		
		for (int i = 0; i < screens.length; i++)
		{
			if (minWidth < screens[i].getRightX ())
				minWidth = screens[i].getRightX ();
			if (minHeight < screens[i].getBottomY ())
				minHeight = screens[i].getBottomY ();
		}
		
		setPreferredSize (new Dimension (minWidth/10+5, minHeight/10+5));
	}
	
	public void setActiveScreen (Screen activeScreen)
	{
		if (this.activeScreen == null || this.activeScreen != activeScreen)
		{
			this.activeScreen = activeScreen;
			repaint ();
		}
	}
	
	@Override
	public void paint (Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g2.setColor (Color.BLACK);
		g2.setStroke( new BasicStroke( 2, 
      BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER ) ); 
		//g2.scale(.1, .1);
		for (int i = 0; i < screens.length; i++)
		{
			if (activeScreen != null && screens[i] == activeScreen)
				g2.setColor (Color.ORANGE);
			else
				g2.setColor (Color.WHITE);
			screens[i].drawTo (g2);
		}
		g2.scale(.1, .1);
	}
}
