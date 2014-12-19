/**
 * 
 */
package de.binfalse.martin.profpres.mgmt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import de.binfalse.martin.profpres.x.MainFrame;
import de.binfalse.martin.profpres.x.PreviewPanel;


/**
 * @author Martin Scharm
 *
 */
public class PresentationListener
	implements MouseListener, KeyListener, ActionListener, MouseWheelListener
{
	private MainFrame mf;
	public PresentationListener (MainFrame mf)
	{
		this.mf = mf;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed (KeyEvent e)
	{
		System.out.println ("keyPressed " + e);
		
		switch (e.getKeyCode ())
		{
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_Q:
				mf.stopPresentation ();
				break;
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_DOWN:
				//next slide
				mf.nextPage ();
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_BACK_SPACE:
				//next slide
				mf.prevPage ();
				break;
			case KeyEvent.VK_O:
				//next slide
				mf.showOverview ();
				break;
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased (KeyEvent e)
	{
		System.out.println ("keyReleased " + e);
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped (KeyEvent e)
	{
		System.out.println ("keyTyped " + e);
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked (MouseEvent e)
	{
		System.out.println ("clicked: " + e.getComponent ());
		if (!(e.getSource () instanceof PreviewPanel))
		{
			if (e.getButton () == MouseEvent.BUTTON1)
				mf.nextPage ();
			else if (e.getButton () == MouseEvent.BUTTON3)
				mf.prevPage ();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered (MouseEvent e)
	{
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited (MouseEvent e)
	{
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed (MouseEvent e)
	{
		System.out.println ("pressed: " + e.getComponent ());
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased (MouseEvent e)
	{
		
	}

	public void actionPerformed (ActionEvent e)
	{
		//System.out.println ("action: mmh");
		//System.out.println ("action: " + e.getSource ());
		if (e.getSource () instanceof PreviewPanel)
		{
			PreviewPanel pp = (PreviewPanel) e.getSource ();
			//System.out.println ("action: " + pp.getPageNumber ());
			mf.showPage (pp.getPageNumber ());
		}
		
	}

	public void mouseWheelMoved (MouseWheelEvent e)
	{
		int notches = e.getWheelRotation();
		if (notches < 0)
		{
			mf.prevPage ();
		}
		else
		{
			mf.nextPage ();
		}
	}
	
}
