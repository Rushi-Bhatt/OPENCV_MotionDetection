import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.*;
import org.opencv.highgui.*;

public class DR3 {
	static boolean flag=true;
	static BufferedImage image_old,image_new;
	
    @SuppressWarnings("static-access")
	public static void main (String args[]) throws InterruptedException, IOException{
    	
    	
    	JFrame frm=new JFrame();
    	JLabel label_1 = new JLabel("",null,JLabel.CENTER);
        frm.add(label_1,BorderLayout.CENTER);
      
    	
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
    
    int[] pix=new int[640*480];
    for(int j=0;j<640*480;j++)
    {
    	pix[j]=0;
    	
    }
    
    //System.out.print(pix[0]);
    
   // Thread.sleep(5000);
    
   /* image_old=new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    image_old.setRGB(0, 0, 640, 480, pix, 0, 640);
   */
    
    int old_x=0,old_y=0,new_x=0,new_y=0;
    int[] mean_x=new int[640*480];
    int[] mean_y=new int[640*480];
	   
    	do
	    {
		    camera.grab();
		    camera.retrieve(frame);
		  
		    camera.read(frame);
		 
		    Highgui.imwrite("camera.png", frame);
		    
		    File file = new File("camera.png");  
		    
		    image_new = ImageIO.read(file);
		    
		    label_1.setIcon(new ImageIcon(image_new));	  
		    
		    ////////////////////////////////////////////////////////
		    
		   // File file_out=new File("C:/Users/Harshin/Desktop/depcryt_pics/rushi.png");
			int[] pixels = image_new.getRGB(0,0, image_new.getWidth(), image_new.getHeight(), null, 0, image_new.getWidth());
			int x=0,y=0;
			//int count=0,count2=0;
			int k=0;
			for(int i=0;i<pixels.length;i++)
			{			
				if(x==image_new.getWidth())
	        	{
	        		y++;
	        		x=0;
	        	}
	        	
	        	if(y==image_new.getHeight())
	        	{
	        		break;
	        	}
				int red=(pixels[i] & 0x00FF0000)>> 16;        		    		
	            int green=(pixels[i] & 0x0000FF00)>> 8;
	       	    int blue=(pixels[i] & 0x000000FF);
			
	       	    
	       	    if(red>green+25 && red>blue+25)
	       	    {
	       	    	image_new.setRGB(x, y, -1);  
	       	    	mean_x[k]=x;
	       	    	mean_y[k]=y;
	       	    	k++;
	       	    	//count++;
	       	    }
	       	    else
	       	    {
	       	    	image_new.setRGB(x, y, 0); 
	       	    	//count2++; 
	       	    }
	       	 x++;
			}   
			k=k/2;
			new_x=mean_x[k];
			new_y=mean_y[k];
			System.out.println(new_x+" "+new_y);
			
			java.awt.Point p;
			p=frm.getLocation();
			frm.setLocation(p.x+new_x-old_x, p.y+new_y-old_y);
			
			old_x=new_x;
			old_y=new_y;
			
			 
	    }while(true);
    }
}
