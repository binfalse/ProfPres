/**
 * 
 */
package de.binfalse.martin.profpres.x;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import com.sun.pdfview.PDFFile;

import de.binfalse.martin.profpres.mgmt.PresentationListener;
import de.binfalse.martin.profpres.mgmt.Screen;
/*import net.sf.ghost4j.document.DocumentException;
import net.sf.ghost4j.document.PDFDocument;
import net.sf.ghost4j.renderer.RendererException;
import net.sf.ghost4j.renderer.SimpleRenderer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;*/
import de.binfalse.martin.profpres.mgmt.Alphabet;



/**
 * @author Martin Scharm
 * 
 */
public class MainFrame
	extends JFrame
{
	public final String TOOLNAME = MainFrame.class.getName ();
	
	private Screen [] screens;
	private PDFFile pubPdfFile, privPdfFile;
	private File pubFile, privFile;
	
  private javax.swing.JButton startBtn;
  private javax.swing.JButton pubChooseBtn;
  private javax.swing.JButton privChooseBtn;
  private javax.swing.JComboBox<Screen> pubScreenList;
  private javax.swing.JComboBox<Screen> privScreenList;
  private javax.swing.JLabel mouseLabel;
  private javax.swing.JLabel mousePositionLabel;
  private javax.swing.JLabel pubPresLabel;
  private javax.swing.JLabel privPresLabel;
  private javax.swing.JLabel pubFileLabel;
  private javax.swing.JLabel privFileLabel;
  private javax.swing.JLabel pubScreenLabel;
  private javax.swing.JLabel privScreenLabel;
  private ScreensPanel screensPanel;
  private javax.swing.JPanel configPanel;
	
	public MainFrame ()
		throws ClassNotFoundException,
			InstantiationException,
			IllegalAccessException,
			UnsupportedLookAndFeelException
	{

		initScreens ();
		
		init ();
		
	}
	
	private void tellWindPos ()
	{
		for (int i = 0; i < screens.length; i++)
		{
			if (screens[i].isIn (getLocation ()))
			{
				mousePositionLabel.setText (screens[i].toString ());
				screensPanel.setActiveScreen (screens[i]);
			}
		}
	}
	
	
	private void init () throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		
		
		
		
		
		
		
		
		
		
		
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationByPlatform (true);
    this.addComponentListener (new ComponentListener () {

			public void componentResized (ComponentEvent e)
			{
			}

			public void componentMoved (ComponentEvent e)
			{
				tellWindPos ();
			}

			public void componentShown (ComponentEvent e)
			{
			}

			public void componentHidden (ComponentEvent e)
			{
			}});

    screensPanel = new ScreensPanel (screens);
    configPanel = new javax.swing.JPanel();
    mouseLabel = new javax.swing.JLabel();
    mousePositionLabel = new javax.swing.JLabel();
    pubPresLabel = new javax.swing.JLabel();
    privPresLabel = new javax.swing.JLabel();
    pubFileLabel = new javax.swing.JLabel();
    privFileLabel = new javax.swing.JLabel();
    pubChooseBtn = new javax.swing.JButton();
    privChooseBtn = new javax.swing.JButton();
    pubScreenLabel = new javax.swing.JLabel();
    privScreenLabel = new javax.swing.JLabel();
    pubScreenList = new javax.swing.JComboBox<Screen>();
    privScreenList = new javax.swing.JComboBox<Screen>();
    startBtn = new javax.swing.JButton();

    mouseLabel.setText("Upper left corner of this window is on:");

    mousePositionLabel.setText("");

    pubPresLabel.setText("Public Presentation:");

    privPresLabel.setText("Private Notes:");

    pubFileLabel.setText("");

    privFileLabel.setText("");

    pubScreenLabel.setText("on screen");

    privScreenLabel.setText("on screen");

    pubChooseBtn.setText("choose");

    privChooseBtn.setText("choose");
    
    pubChooseBtn.addActionListener (new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				final JFileChooser fc = new JFileChooser(Preferences.userRoot ().get (TOOLNAME, "."));
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter (new PDFFilter ());
				if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(MainFrame.this))
				{
					File f = fc.getSelectedFile ();
					if (!f.getName ().endsWith (".pdf"))
					{
						displayError ("only PDF files supported..");
						return;
					}
					//System.out.println (f.getAbsolutePath ());
					try
					{
						RandomAccessFile raf = new RandomAccessFile(f, "r");
				    FileChannel channel = raf.getChannel();
				    ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
				    pubPdfFile = new PDFFile(buf);
				    raf.close ();
				    pubFile = f;
				    pubFileLabel.setText (pubFile.getName () + " (" + pubPdfFile.getNumPages () + " Pages)");
				    Preferences.userRoot ().put (TOOLNAME, f.getParent ());
					}
					catch (IOException e)
					{
						e.printStackTrace ();
						displayError (e.getMessage ());
					}
				}
			}
		});

    privChooseBtn.addActionListener (new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				final JFileChooser fc = new JFileChooser(Preferences.userRoot ().get (TOOLNAME, "."));
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter (new PDFFilter ());
				if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(MainFrame.this))
				{
					File f = fc.getSelectedFile ();
					if (!f.getName ().endsWith (".pdf"))
					{
						displayError ("only PDF files supported..");
						return;
					}
					//System.out.println (f.getAbsolutePath ());
					try
					{
						RandomAccessFile raf = new RandomAccessFile(f, "r");
				    FileChannel channel = raf.getChannel();
				    ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
				    privPdfFile = new PDFFile(buf);
				    raf.close ();
				    privFile = f;
				    privFileLabel.setText (privFile.getName () + " (" + privPdfFile.getNumPages () + " Pages)");
				    Preferences.userRoot ().put (TOOLNAME, f.getParent ());
					}
					catch (IOException e)
					{
						e.printStackTrace ();
						displayError (e.getMessage ());
					}
				}
			}
		});
    

    pubScreenList.setModel(new javax.swing.DefaultComboBoxModel<Screen>(screens));

    privScreenList.setModel(new javax.swing.DefaultComboBoxModel<Screen>(screens));
    
    


    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(configPanel);
    configPanel.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pubPresLabel)
                .addComponent(privPresLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(privFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pubFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addComponent(privChooseBtn)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pubScreenLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pubScreenList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addComponent(pubChooseBtn)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(privScreenLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(privScreenList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(pubPresLabel)
                .addComponent(pubFileLabel)
                .addComponent(pubChooseBtn)
                .addComponent(pubScreenLabel)
                .addComponent(pubScreenList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(privPresLabel)
                .addComponent(privFileLabel)
                .addComponent(privChooseBtn)
                .addComponent(privScreenLabel)
                .addComponent(privScreenList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );
    
    startBtn.setText("start presentation");
    startBtn.addActionListener (new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent e)
			{
				startPresentation ();
			}});
    
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(configPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(screensPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(mouseLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(mousePositionLabel)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(startBtn)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(screensPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(mouseLabel)
                .addComponent(mousePositionLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(configPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(startBtn)
            .addContainerGap())
    );
    

    configPanel.setBackground (new Color(244,245,247));
		getContentPane().setBackground (new Color(244,245,247));

    pack();
    tellWindPos ();
	}

	private void startPresentation ()
	{
		// same number of pages?
		if (pubPdfFile == null)
		{
			displayError ("need a public file");
			return;
		}
		if (privPdfFile == null)
		{
			displayError ("need a private file");
			return;
		}
		if (privPdfFile.getNumPages () != pubPdfFile.getNumPages ())
		{
			displayError ("pdf files don't have the same numer of pages");
			return;
		}
		if (pubScreenList.getSelectedItem () == privScreenList.getSelectedItem ())
		{
			displayError ("public and private document can't be shown on the same screen!");
			return;
		}
		if (pubFrame != null)
		{
			displayError ("there is already a presentation in action!");
			return;
		}
		
		pl = new PresentationListener (this);
		
		// let's go
		pubFrame = new PublicFrame ((Screen) pubScreenList.getSelectedItem (), pl, pubPdfFile, this);
	}
	PublicFrame pubFrame;
	PrivateFrame privFrame;
	PresentationListener pl;
	public void startPrivateFrame (Vector<BufferedImage> pubPreviews)
	{
		privFrame = new PrivateFrame ((Screen) privScreenList.getSelectedItem (), pl, privPdfFile, pubPreviews);
		
	}
	
	public void stopPresentation ()
	{
		if (pubFrame != null)
		{
			pubFrame.dispose ();
			pubFrame = null;
			privFrame.dispose ();
			privFrame = null;
		}
	}
	
	public void nextPage ()
	{
		if (pubFrame != null)
		{
			pubFrame.showNext ();
			privFrame.showNext ();
		}
		
	}
	
	public void prevPage ()
	{
		if (pubFrame != null)
		{
			pubFrame.showPrev ();
			privFrame.showPrev ();
		}
		
	}
	
	public void showOverview ()
	{
		if (pubFrame != null)
		{
		pubFrame.showOverview ();
		privFrame.showOverview ();
		}
	}
	
	public void showPage (int i)
	{
		if (pubFrame != null)
		{
		pubFrame.show (i);
		privFrame.show (i);
		}
	}
	
	private void initScreens ()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
		GraphicsDevice[] devs = ge.getScreenDevices ();
		Alphabet abc = new Alphabet ();
		screens = new Screen [devs.length];
		for (int i = 0; i < devs.length; i++)
		{
			screens[i] = new Screen (abc.next (), devs[i]);
		}
		
	}
	
	public void test ()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
		GraphicsDevice[] devs = ge.getScreenDevices ();
		for (int i = 0; i < devs.length; i++)
		{
			System.out.println (devs[i].toString () + ": " + devs[i].getIDstring ()
				+ " - " + devs[i].getType ());
			
			GraphicsConfiguration gc = devs[i].getDefaultConfiguration ();
			System.out.println (gc.getBounds ());
			
		}
		
		int sequence = 1;
		for (GraphicsDevice device : devs)
		{
			System.out.println ("Screen Number [" + (sequence++) + "]");
			System.out.println ("Width       : "
				+ device.getDisplayMode ().getWidth ());
			System.out.println ("Height      : "
				+ device.getDisplayMode ().getHeight ());
			System.out.println ("Refresh Rate: "
				+ device.getDisplayMode ().getRefreshRate ());
			System.out.println ("Bit Depth   : "
				+ device.getDisplayMode ().getBitDepth ());
			System.out.println ("");
		}
		
	}
	
	/*File pdf;
	
	private void start () throws IOException
	{
    pdf = new File ("/home/martin/education/business-docs/presentations/2012-11-29-heidelberg-bives-und-budhat/VortragHeidelberg-Scharm.pdf");
    RandomAccessFile raf = new RandomAccessFile(pdf, "r");
    FileChannel channel = raf.getChannel();
    ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
    PDFFile pdffile = new PDFFile(buf);
    
    
    
    raf.close ();
	}*/

	private final void displayError (String e)
	{
		JOptionPane.showMessageDialog(this,
	    "Following error occured:\n" + e,
	    "Error",
	    JOptionPane.ERROR_MESSAGE);
	}
	
	private class PDFFilter extends FileFilter
	{
		@Override
		public boolean accept (File f)
		{
			return f.isDirectory () || f.getName ().endsWith (".pdf");
		}

		@Override
		public String getDescription ()
		{
			return "PDF Files";
		}
	}
	
}
