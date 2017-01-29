import java.awt.Component;
import java.io.IOException;

import javax.swing.*;

public class test {

	public static void main(String args[]) throws InterruptedException, IOException
	{	JButton jb=new JButton("hello");
		jb.setSize(75, 30);
		Component c=jb;
		new move(c);
		
		
	}
}
