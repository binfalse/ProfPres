package de.binfalse.martin.profpres.x;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

import de.binfalse.martin.profpres.mgmt.PresentationListener;
import de.binfalse.martin.profpres.mgmt.Screen;


/**
 * 
 */

/**
 * @author Martin Scharm
 *
 */
public class PublicFrame
	extends JFrame implements Runnable
{
	public final static int PREVIEW_HEIGHT = 200;
	public final static int PREVIEW_WIDTH = 300;
	private final static String OVERVIEW = "overview";
	private final static String PAGE = "page";
	private Screen screen;
	private PDFFile pdf;
	private Vector<BufferedImage> largeImages;
	private Vector<BufferedImage> smallImages;
	private PagePanel pagePanel;
	private int currentPage;
  private CardLayout cardlayout;
  private PresentationListener pl;
	private int winWidth, winHeight;
	private MainFrame mf;
  
	public PublicFrame (Screen screen, PresentationListener pl, PDFFile pdf, MainFrame mf)
	{
		super (screen.getDevice ().getDefaultConfiguration ());
		this.mf = mf;
		this.pl = pl;
		this.screen = screen;
		this.pdf = pdf;
		currentPage = 0;
		init ();
		new Thread (this).start ();
	}
	
	private void init ()
	{
		largeImages = new Vector<BufferedImage> ();
		smallImages = new Vector<BufferedImage> ();
		for (int i = 0; i < pdf.getNumPages (); i++)
		{
			//renderBig (winWidth, winHeight, pdf.getPage (i + 1));
			renderSmall (PREVIEW_WIDTH, PREVIEW_HEIGHT, pdf.getPage (i + 1));
			/*PagePanel pp = new PagePanel (winWidth, winHeight, pdf.getPage (i + 1));
			getContentPane().add(pp, i + "");
			pages.add (pp);*/
		}
		mf.startPrivateFrame (smallImages);
		
		
		this.addMouseListener (pl);
		this.addMouseWheelListener (pl);
		this.addKeyListener (pl);
		
		setUndecorated(true);
		setResizable(false);
		//this.setAlwaysOnTop (true);
		screen.getDevice ().setFullScreenWindow(this);
		this.setBackground (Color.WHITE);
		
		winWidth = this.getWidth ();
		winHeight = this.getHeight ();
		cardlayout = new CardLayout();
		getContentPane().setLayout(cardlayout);
		/*PagePanel one = new PagePanel (winWidth, winHeight, pdf.getPage (1));
		getContentPane ().add(one, "0");*/
		
		pagePanel = new PagePanel (pl);
		getContentPane ().add(pagePanel, PAGE);
		getContentPane().add(new OverviewPanel (winWidth, winHeight, smallImages, pl), OVERVIEW);
		
		renderBig (winWidth, winHeight, pdf.getPage (1));
		renderSmall (PREVIEW_WIDTH, PREVIEW_HEIGHT, pdf.getPage (1));
		
		pagePanel.setPage (largeImages.elementAt (0));
		
		//pages.add (one);
		/*for (int i = 0; i < pdf.getNumPages (); i++)
			pages.add (new PagePanel (winWidth, winHeight, pdf.getPage (i + 1)));*/

	}
	
	
	
	public void show (int i)
	{
		currentPage = i;
		if (++currentPage > pdf.getNumPages ())
			currentPage = pdf.getNumPages ();
		if (--currentPage < 0)
			currentPage = 0;
		System.out.println ("show i: " + currentPage);
		
		/*for (int p = 0; p < pages.size (); p++)
			pages.elementAt (p).showBig ();*/
		
		pagePanel.setPage (largeImages.elementAt (currentPage));
		
		cardlayout.show (getContentPane (), PAGE);
		System.out.println (this.getFocusOwner ());
		pagePanel.requestFocus ();
		System.out.println (this.getFocusOwner ());
	}
	
	public void showOverview ()
	{
		System.out.println ("show overview");
		
		/*for (int p = 0; p < pages.size (); p++)
			pages.elementAt (p).showSmall ();*/
		
		cardlayout.show (getContentPane (), OVERVIEW);
	}
	
	public void showNext ()
	{
		System.out.println ("show: " + currentPage);
		//page.showPage (pdf.getPage (++nextPage));
		if (++currentPage > largeImages.size () - 1)
			currentPage = largeImages.size () - 1;
		//showPage ();
			System.out.println ("show next: " + currentPage);
			pagePanel.setPage (largeImages.elementAt (currentPage));
		cardlayout.show (getContentPane (), PAGE);
	}
	
	public void showPrev ()
	{
		//page.showPage (pdf.getPage (++nextPage));
		if (--currentPage < 0)
			currentPage = 0;
		System.out.println ("show prev: " + currentPage);
		pagePanel.setPage (largeImages.elementAt (currentPage));

			cardlayout.show (getContentPane (), PAGE);
	}

	@Override
	public void run ()
	{
		
			//pages.add (new PagePanel (winWidth, winHeight, pdf.getPage (i + 1)));
		
		// if all is done start the private frame
		for (int i = 1; i < pdf.getNumPages (); i++)
		{
			renderBig (winWidth, winHeight, pdf.getPage (i + 1));
		}
	}
	
	

	
	private void renderSmall (int winWidth, int winHeight, PDFPage page)
	{
		BufferedImage smallImage = new BufferedImage (winWidth, winHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = smallImage.createGraphics ();
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor (Color.BLACK);
		g2.fillRect (0, 0, winWidth, winHeight);
		
		int width = (int) page.getBBox ().getWidth ();
		int height = (int) page.getBBox ().getHeight ();
		
		//int panelW = this.getWidth ();
		//int panelH = this.getHeight ();
		
		int startx = 0, starty = 0;
		
		if ((double)winWidth/(double)winHeight < (double)width/(double)height)
		{
			height = height * winWidth / width;
			width = winWidth;
			starty = (winHeight - height) / 2;
		}
		else
		{
			width = width * winHeight / height;
			height = winHeight;
			startx = (winWidth - width) / 2;
		}
		
		PDFRenderer renderer = new PDFRenderer (page, g2, new Rectangle (startx, starty,
			width, height), null, Color.WHITE);
		try
		{
			page.waitForFinish ();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}
		renderer.run ();
		smallImages.add (smallImage);
	}
	
	private void renderBig (int winWidth, int winHeight, PDFPage page)
	{
		BufferedImage largeImage = new BufferedImage (winWidth, winHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = largeImage.createGraphics ();
			g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor (Color.BLACK);
			g2.fillRect (0, 0, winWidth, winHeight);
			
			int width = (int) page.getBBox ().getWidth ();
			int height = (int) page.getBBox ().getHeight ();
			
			//int panelW = this.getWidth ();
			//int panelH = this.getHeight ();
			
			int startx = 0, starty = 0;
			
			if ((double)winWidth/(double)winHeight < (double)width/(double)height)
			{
				height = height * winWidth / width;
				width = winWidth;
				starty = (winHeight - height) / 2;
			}
			else
			{
				width = width * winHeight / height;
				height = winHeight;
				startx = (winWidth - width) / 2;
			}
			
			PDFRenderer renderer = new PDFRenderer (page, g2, new Rectangle (startx, starty,
				width, height), null, Color.WHITE);
			try
			{
				page.waitForFinish ();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace ();
			}
			renderer.run ();
			largeImages.add (largeImage);
	}
}
