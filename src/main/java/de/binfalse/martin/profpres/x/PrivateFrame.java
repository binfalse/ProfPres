/**
 * 
 */
package de.binfalse.martin.profpres.x;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

import de.binfalse.martin.profpres.mgmt.PresentationListener;
import de.binfalse.martin.profpres.mgmt.Screen;


/**
 * @author Martin Scharm
 *
 */
public class PrivateFrame
	extends JFrame implements Runnable
{
  private long start;
  private SimpleDateFormat df;
	private Screen screen;
	private PDFFile pdf;
	int winWidth, winHeight;
  private PresentationListener pl;
  private javax.swing.JLabel cmd1;
  private javax.swing.JLabel cmd2;
  private javax.swing.JLabel timeLabel;
  private javax.swing.JPanel timePanel;
  private PagePanel notePanel;
  private javax.swing.JPanel publicPreview;
  private javax.swing.JPanel commandsPanel;
  private Vector<BufferedImage> notes;
  private Vector<BufferedImage> publicPreviews;
  private CardLayout previewCards;
	private int currentPage;

  public PrivateFrame (Screen screen, PresentationListener pl, PDFFile pdf, Vector<BufferedImage> publicPreviews)
  {
		this.pl = pl;
		this.screen = screen;
		this.pdf = pdf;
		this.publicPreviews = publicPreviews;
      initComponents();
      start = System.currentTimeMillis();

      df = new SimpleDateFormat ("HH:mm:ss");
      df.setTimeZone(TimeZone.getTimeZone("UTC"));
      
      timeLabel.setText("elapsed time");
      Timer t = new javax.swing.Timer(1000, new ClockListener());
      t.start();

  		winWidth = this.getWidth ();
  		winHeight = this.getHeight ();
  		notes = new Vector<BufferedImage> ();
  		renderNotes (winWidth-PublicFrame.PREVIEW_WIDTH, winHeight-PublicFrame.PREVIEW_HEIGHT, pdf.getPage (1));
  		
  		notePanel.setPage (notes.elementAt (0));
  		currentPage = 0;
  		new Thread (this).start ();
  }
  
  
  private void initComponents() {

		this.addMouseListener (pl);
		this.addKeyListener (pl);
		this.addMouseWheelListener (pl);
		
		//this.setAlwaysOnTop (true);
		setUndecorated(true);
		screen.getDevice ().setFullScreenWindow(this);
		setResizable(false);
		this.setBackground (Color.WHITE);
		
      publicPreview = new javax.swing.JPanel();
      timePanel = new javax.swing.JPanel();
      timeLabel = new javax.swing.JLabel();
      commandsPanel = new javax.swing.JPanel();
      cmd1 = new javax.swing.JLabel();
      cmd2 = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

  		notePanel = new PagePanel (pl);
  		previewCards = new CardLayout ();
  		publicPreview.setLayout (previewCards);
  		
  		for (int i = 0; i < publicPreviews.size (); i++)
  			publicPreview.add (new PreviewPanel (publicPreviews.elementAt (i), i, null), "" + i);
  		
      publicPreview.setBackground(new java.awt.Color(51, 204, 255));
      publicPreview.setPreferredSize(new java.awt.Dimension(PublicFrame.PREVIEW_WIDTH, PublicFrame.PREVIEW_HEIGHT));

      //timePanel.setLayout(new java.awt.BorderLayout());

      timeLabel.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
      timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      timeLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
      timeLabel.setText("elapsed time");
      timeLabel.setBackground (Color.BLACK);
      timeLabel.setForeground (Color.GRAY);
      timeLabel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            timeClick(e);
        }
    });
      timePanel.setLayout(new java.awt.BorderLayout());
      timePanel.add(timeLabel, BorderLayout.CENTER);
      timePanel.setBackground (Color.BLACK);
      timePanel.addKeyListener (pl);
      commandsPanel.addKeyListener (pl);
      /*javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(timePanel);
      timePanel.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
          jPanel1Layout.createSequentialGroup().addContainerGap (10, Short.MAX_VALUE)
          .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap (10, Short.MAX_VALUE)
      );
      jPanel1Layout.setVerticalGroup(
          jPanel1Layout.createSequentialGroup().addContainerGap (10, Short.MAX_VALUE)
          .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap (10, Short.MAX_VALUE)
      );*/

      commandsPanel.setLayout(new java.awt.GridLayout(10, 1));
      commandsPanel.setBackground (Color.BLACK);
      cmd1.setText("o = overview");
      cmd1.setForeground (Color.GRAY);
      commandsPanel.add(cmd1);

      cmd2.setText("q = quit");
      cmd2.setForeground (Color.GRAY);
      commandsPanel.add(cmd2);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(timePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(notePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(publicPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(commandsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      );
      layout.setVerticalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(publicPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(timePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(commandsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(notePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      );

      //pack();
  }

	public void show (int i)
	{
		currentPage = i;
		if (currentPage > pdf.getNumPages ())
			currentPage = pdf.getNumPages ();
		if (currentPage < 0)
			currentPage = 0;
		System.out.println ("show i: " + currentPage);
		
		/*for (int p = 0; p < pages.size (); p++)
			pages.elementAt (p).showBig ();*/
		
		notePanel.setPage (notes.elementAt (currentPage));
		
		previewCards.show (publicPreview, currentPage + "");
		System.out.println (this.getFocusOwner ());
		notePanel.requestFocus ();
		System.out.println (this.getFocusOwner ());
	}
	
	public void showOverview ()
	{
		// currently nothing to do
	}
	
	public void showNext ()
	{
		if (++currentPage >= notes.size ())
			currentPage = notes.size ()-1;
		System.out.println ("show next: " + currentPage);
		
		/*for (int p = 0; p < pages.size (); p++)
			pages.elementAt (p).showBig ();*/
		
		notePanel.setPage (notes.elementAt (currentPage));
		
		previewCards.show (publicPreview, currentPage + "");
		System.out.println (this.getFocusOwner ());
		notePanel.requestFocus ();
		System.out.println (this.getFocusOwner ());
	}
	
	public void showPrev ()
	{
		if (--currentPage < 0)
			currentPage = 0;
		System.out.println ("show i: " + currentPage);
		
		/*for (int p = 0; p < pages.size (); p++)
			pages.elementAt (p).showBig ();*/
		
		notePanel.setPage (notes.elementAt (currentPage));
		
		previewCards.show (publicPreview, currentPage + "");
		System.out.println (this.getFocusOwner ());
		notePanel.requestFocus ();
		System.out.println (this.getFocusOwner ());
	}
  private void timeClick(MouseEvent evt)
  {
    start = System.currentTimeMillis();
    timeLabel.setText(df.format(new Date (System.currentTimeMillis() - start)));
  }
  
  class ClockListener implements ActionListener {
  	public void actionPerformed(ActionEvent e) {
          timeLabel.setText(df.format(new Date (System.currentTimeMillis() - start)));
  	}
  }
	private void renderNotes (int winWidth, int winHeight, PDFPage page)
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
			notes.add (largeImage);
	}
	
	@Override
	public void run ()
	{
		for (int i = 1; i < pdf.getNumPages (); i++)
		{
  		renderNotes (winWidth-PublicFrame.PREVIEW_WIDTH, winHeight-PublicFrame.PREVIEW_HEIGHT, pdf.getPage (i + 1));
		}
	}
}
