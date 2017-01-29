import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;

public class seq {
	boolean flag=true;
    static BufferedImage image;
    JFrame frm;
    JLabel label_1;
    JButton jb1;
    int X=0,Y=0,oldx=0,oldy=0,newx=0,newy=0;
    
    seq()throws InterruptedException, IOException{
    	
    	frm=new JFrame();
    	label_1 = new JLabel("",null,JLabel.CENTER);
    	
        frm.add(label_1,BorderLayout.CENTER);
        jb1=new JButton("Capture");
    	frm.add(jb1,BorderLayout.SOUTH);
    	/*jb1.addActionListener(new ActionListener() {
			
		
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
		});*/
    	
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    VideoCapture camera = new VideoCapture(0);
    Thread.sleep(500);
    
    if(!camera.isOpened()){
        System.out.println("Camera Error");
    }
    else{
        System.out.println("Camera OK?");
    }
    
    Mat frame1=new Mat();
    Mat frame2=new Mat();
	Mat grayimg1=new Mat();
	Mat grayimg2=new Mat();
	Mat diffimg=new Mat();
	Mat threshimg=new Mat();
    
   //frm.setSize(1370, 730);
    frm.setSize(700, 500);
    frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frm.setVisible(true);
    
	    do
	    {
		    camera.grab();
		    camera.retrieve(frame1);
		    camera.read(frame1);
		    Imgproc.cvtColor(frame1, grayimg1,Imgproc.COLOR_BGR2GRAY);
		 
		    camera.grab();
		    camera.retrieve(frame2);
		    camera.read(frame2);
		    Imgproc.cvtColor(frame2, grayimg2,Imgproc.COLOR_BGR2GRAY);
		    
		    Core.absdiff(grayimg1, grayimg2, diffimg);
		    Imgproc.threshold(diffimg, threshimg, 20, 255, Imgproc.THRESH_BINARY);
		    

		    Imgproc.blur(threshimg, threshimg,new Size(5.0,5.0));
		    Highgui.imwrite("camera.png", threshimg);
		    
		    File file = new File("camera.png");  
		   
		    image = ImageIO.read(file);
		       
		    flipImg();
		
		    getXY();
		    
		   if(X!=0 && Y!=0)
		    	motion();
		    
		    else
		    {
		    	oldx=0;
		    	oldy=0;
		    }
		    
		    label_1.setIcon(new ImageIcon(image));	    
	    }while(true);
	    
    }
    
    static void flipImg() throws IOException
	{
		File file_temp = new File("camera.png");  
	    
		BufferedImage image_temp;
		image_temp = ImageIO.read(file_temp);
		
		int[] pixels = image_temp.getRGB(0,0, image_temp.getWidth(), image_temp.getHeight(), null, 0, image_temp.getWidth());
		int x=0,y=0;
		
		for(int i=0;i<pixels.length;i++)
		{			
			if(x==image_temp.getWidth())
        	{
        		y++;
        		x=0;
        	}
        	
        	if(y==image.getHeight())
        	{
        		break;
        	}
			
           	image.setRGB(image.getWidth()-x-1, y, pixels[i]);  
      	 x++;
		}   	
	}
    
    void getXY()
    {
    	int[] pixels = image.getRGB(0,0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int x=0,y=0;
		int count=1;//,count2=0;
		int k=1;//so no divide by 0 error
		X=0;
		Y=0;
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
       	    
       	
       	   // 
        	//System.out.println(pixels[i]);
       	    if(red>=235 && blue>=235 && green>=235)
       	    {	
       	    // System.out.println(red+" "+green+" "+blue);
       	    
       	    	//image_new.setRGB(x, y, -1);  
       	    	X+=x;
       	    	Y+=y;
       	    	k++;
       	    	count++;
       	    }
       	 x++;
		}   
	
		X/=k;
		Y/=k;
		//System.out.println(X+" "+Y);
		
	/*	if(Math.abs(new_x-old_x)>5 || Math.abs(new_y-old_y)>5)
		{			
			java.awt.Point p;
			p=label_1.getLocation();
			label_1.setLocation(p.x+(new_x-old_x)*2, p.y+(new_y-old_y)*2);
		}
		
		/*old_x=new_x;
		old_y=new_y;
		*/
	    
	    
    }
    
   void motion(){
    	  
	   if(oldx==0 && oldy==0)
	   {
		   oldx=X;
		   oldy=Y;
	   }
	   
	   else
	   {
		   			
			/*	java.awt.Point p;
				p=label_1.getLocation();
			*/	
		   //if(Math.abs(X-oldx)<25 && Math.abs(Y-oldy)<25)
		   {
			   System.out.println((X-oldx)+" "+(Y-oldy));	   
		   	//	label_1.setLocation(p.x+(X-oldx), p.y+(Y-oldy));
		   }	
		   
		  
			
		   oldx=X;
		   oldy=Y;
	   }
	   
    }
    
    public static void main (String args[]) throws InterruptedException, IOException{
    	new seq();
    }
}
