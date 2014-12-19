/**
 * 
 */
package de.binfalse.martin.profpres.mgmt;

import java.util.Iterator;


/**
 * @author Martin Scharm
 *
 */
public class Alphabet implements Iterator<String>
{
  private int now;
  private char[] chars;
  
  public Alphabet ()
  {
  	now = 0;
  	chars = new char['Z' - 'A' + 1];
    for(char i='A'; i<='Z';i++)
    	chars[i - 'A'] = i;
  }
  
  public Alphabet (char[] chars)
  {
  	now = 0;
  	this.chars = chars;
  }

  private StringBuilder alpha(int i)
  {
      char r = chars[--i % chars.length];
      int n = i / chars.length;
      return n == 0 ? new StringBuilder().append(r) : alpha(n).append(r);
  }

  public String next()
  {
      return alpha(++now).toString();
  }

	public boolean hasNext ()
	{
		return true;
	}

	public void remove ()
	{
		// you are very funny... 
	}
	
	public static void main (String [] args)
	{
		char[] chars = new char['D' - 'A' + 1];
    for(char i='A'; i<='D';i++)
    	chars[i - 'A'] = i;
		Alphabet abc = new Alphabet (chars);
		for (int i = 0; i < 30; i++)
			System.out.println (abc.next ());
	}
	
}
