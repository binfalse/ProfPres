/**
 * 
 */
package de.binfalse.martin.profpres;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import de.binfalse.martin.profpres.x.MainFrame;


/**
 * @author Martin Scharm
 *
 */
public class Main
{
	
	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		/*char [] a = new char [] {'A', 'B', 'C'};
		//int n = 5;
		int j = 1;
		//if (j < 0)
		//	j = n + j;
		//int r = j;
		for (int i = 0; i < a.length; i++)
			System.out.println ((a.length + j + i) + ": " + a[i] + " -> " + a[(a.length + j + i) % a.length]);
		
		

		if (1==1) return;*/
		/*try {
      UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
  } catch (Exception e) {
  	e.printStackTrace ();
		try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	      if ("Nimbus".equals(info.getName())) {
	          UIManager.setLookAndFeel(info.getClassName());
	          break;
	      }
	  }
	} catch (Exception e4) {
	  // If Nimbus is not available, you can set the GUI to another look and feel.
			try
			{
				UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}}/*UIDefaults defaults = UIManager.getDefaults();
  //defaults.putDefaults(newSettings);
  defaults.put("Button.background", Color.BLACK);*/
  
  
  

    /*Hashtable   properties = UIManager.getDefaults();
    properties.put ("Panel.background" , Color.BLACK);
    
    Enumeration keys       = properties.keys();

    while (keys.hasMoreElements()) {
      String key   = keys.nextElement().toString ();
      Object value = properties.get (key);
      System.out.println(key + "\t" + value);
    }*/
  
		try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	      if ("Nimbus".equals(info.getName())) {
	          UIManager.setLookAndFeel(info.getClassName());
	          break;
	      }
	  }}
		catch (Exception e4) {
		  // If Nimbus is not available, you can set the GUI to another look and feel.
				try
				{
					UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName ());
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
  
		
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
          try
					{
						new MainFrame().setVisible(true);
					}
					catch (ClassNotFoundException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (InstantiationException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (UnsupportedLookAndFeelException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
      }
  });
	}
	
}
