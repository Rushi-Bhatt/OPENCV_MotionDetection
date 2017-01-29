import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.*;
import org.opencv.highgui.*;

public class DR2 {
	static boolean flag=true;
	static BufferedImage image;
	
    @SuppressWarnings("static-access")
	public static void main (String args[]) throws InterruptedException, IOException{
    	
    	
    	JFrame frm=new JFrame();
    	JLabel label_1 = new JLabel("",null,JLabel.CENTER);
        frm.add(label_1,BorderLayout.CENTER);
        JButton jb1=new JButton("Capture");
    	frm.add(jb1,BorderLayout.SOUTH);
    	jb1.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent arg0) {
				File file_out=new File("C:/Users/Harshin/Desktop/depcryt_pics/rushi.png");
				int[] pixels = image.getRGB(0,0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
				int x=0,y=0;
				//int count=0,count2=0;

				for(int i=0;i<pixels.length;i++)
				{			
					if(x==image.getWidth())
		        	{
		        		y++;
		        		x=0;
		        	}
		        	
		        	if(y==image.getHeight())
		        	{
		        		break;
		        	}
					int red=(pixels[i] & 0x00FF0000)>> 16;        		    		
		            int green=(pixels[i] & 0x0000FF00)>> 8;
		       	    int blue=(pixels[i] & 0x000000FF);
		       	    
		       	    
		       	    if(red>green+25 && red>blue+25)
		       	    {
		       	    	image.setRGB(x, y, -1);  	
		       	    	//count++;
		       	    }
		       	    else
		       	    {
		       	    	image.setRGB(x, y, 0); 
		       	    	//count2++; 
		       	    }
		       	 x++;
				}   
				
				try {
					ImageIO.write(image, "png", file_out);
				} 
				catch (IOException e) {}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				
			}
		});
    	
    System.out.println("Hello, OpenCV");
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    VideoCapture camera = new VideoCapture(0);
    Thread.sleep(500);
    
    if(!camera.isOpened()){
        System.out.println("Camera Error");
    }
    else{
        System.out.println("Camera OK?");
    }

   
    Mat frame = new Mat();
    frm.setSize(640, 480);
    frm.setDefaultCloseOperation(frm.EXIT_ON_CLOSE);
    frm.setVisible(true);
    
	    do
	    {
		    camera.grab();
		    camera.retrieve(frame);
		  
		    camera.read(frame);
		 
		    Highgui.imwrite("camera.png", frame);
		    
		    File file = new File("camera.png");  
		    
		    image = ImageIO.read(file);
		    
		    label_1.setIcon(new ImageIcon(image));	    
	    }while(true);
	    
    }
}
