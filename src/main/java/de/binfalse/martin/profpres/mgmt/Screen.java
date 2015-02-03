/**
 * 
 */
package de.binfalse.martin.profpres.mgmt;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


/**
 * @author Martin Scharm
 *
 */
public class Screen
{
	private GraphicsDevice dev;
	private String name;
	private Rectangle bounds;
	
	public Screen (String name, GraphicsDevice dev)
	{
		this.name = name;
		this.dev = dev;
		getInfos ();
	}
	
	public GraphicsDevice getDevice ()
	{
		return dev;
	}
	
	public void drawTo (Graphics2D g2)
	{
		g2.fillRect (bounds.x / 10, bounds.y / 10, bounds.width / 10, bounds.height/10);
		g2.setColor (Color.BLACK);
		g2.drawRect (bounds.x / 10, bounds.y / 10, bounds.width / 10, bounds.height/10);
		
		String res = bounds.width + "x" + bounds.height;
		
		FontMetrics fm   = g2.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(name, g2);
		Rectangle2D rectres = fm.getStringBounds(res, g2);
		
		g2.drawString (name, bounds.x/10 + bounds.width/20 - (int)rect.getWidth () / 2, bounds.y/10 + bounds.height/20 - (int)rect.getHeight () / 2);
		g2.drawString (res, bounds.x/10 + bounds.width/20 - (int)rectres.getWidth () / 2, bounds.y/10 + bounds.height/20 + (int)rect.getHeight () +  5 - (int)rectres.getHeight () / 2);
	}
	
	public Rectangle getBounds ()
	{
		return bounds;
	}
	
	public int getLeftX ()
	{
		return bounds.x;
	}
	
	public int getTopY()
	{
		return bounds.y;
	}
	
	public int getRightX ()
	{
		return bounds.x + bounds.width;
	}
	
	public int getBottomY()
	{
		return bounds.y + bounds.height;
	}
	
	public int getWidth ()
	{
		return bounds.width;
	}
	
	public int getHeight ()
	{
		return bounds.height;
	}
	
	private void getInfos ()
	{
		bounds = dev.getDefaultConfiguration ().getBounds ();
		
		//System.out.println (name + ": " + bounds + " -> " + width + "x" + height);
	}
	
	public String toString ()
	{
		return name;
	}

	public boolean isIn (Point location)
	{
		return bounds.contains (location);
	}
}
