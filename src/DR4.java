import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;

public class DR4{
	JSlider js1=new JSlider();
	JSlider js2=new JSlider();
	JSlider js3=new JSlider();
	JSlider js4=new JSlider();
	JSlider js5=new JSlider();
	JSlider js6=new JSlider();
	static boolean flag=true;
	int red_min,green_min,blue_min,red_max,green_max,blue_max;
	static BufferedImage image_old,image_new;
	
    @SuppressWarnings("static-access")
	DR4() throws InterruptedException, IOException{
    	
    	
    	
    	
    	js1.setMinimum(0);
    	js1.setMaximum(255);
    	js2.setMinimum(0);
    	js2.setMaximum(255);
    	js3.setMinimum(0);
    	js3.setMaximum(255);

    	js4.setMinimum(0);
    	js4.setMaximum(255);
    	js5.setMinimum(0);
    	js5.setMaximum(255);
    	js6.setMinimum(0);
    	js6.setMaximum(255);
    	
    	js1.setValue(0);
    	js2.setValue(0);
    	js3.setValue(0);

    	js4.setValue(255);
    	js5.setValue(255);
    	js6.setValue(255);
    	
    	JFrame frms=new JFrame();
    	frms.setLayout(new FlowLayout());
    	
    	frms.add(js1);
    	frms.add(js2);
    	frms.add(js3);
    	frms.add(js4);
    	frms.add(js5);
    	frms.add(js6);
    	
    	
    	js1.addChangeListener(new ChangeListener() {
    		
    		@Override
    		public void stateChanged(ChangeEvent arg0) {
    			
    			red_min= js1.getValue();
    			//System.out.println("reds:"+reds+"  greens:"+greens+"  blues:"+blues);
    			   
    			//System.out.println();
    		}
    	});
        js2.addChangeListener(new ChangeListener() {
    		
    		@Override
    		public void stateChanged(ChangeEvent arg0) {
    			
    			green_min= js2.getValue();
    			//System.out.println("reds:"+reds+"  greens:"+greens+"  blues:"+blues);
    			   //System.out.println();
    		}
    	});
         js3.addChangeListener(new ChangeListener() {
    		
    		@Override
    		public void stateChanged(ChangeEvent arg0) {
    			
    			blue_min= js3.getValue();
    			//System.out.println("reds:"+reds+"  greens:"+greens+"  blues:"+blues);
    			   //System.out.println();
    		}
    	});
    		
         js4.addChangeListener(new ChangeListener() {
     		
     		@Override
     		public void stateChanged(ChangeEvent arg0) {
     			
     			red_max= js4.getValue();
     			//System.out.println("reds:"+reds+"  greens:"+greens+"  blues:"+blues);
     			   //System.out.println();
     		}
     	});
         
         js5.addChangeListener(new ChangeListener() {
     	
     		@Override
     		public void stateChanged(ChangeEvent arg0) {
     			
     			green_max= js5.getValue();
     			//System.out.println("reds:"+reds+"  greens:"+greens+"  blues:"+blues);
     			   //System.out.println();
     		}
     	});
         
         js6.addChangeListener(new ChangeListener() {
     		
     		@Override
     		public void stateChanged(ChangeEvent arg0) {
     			
     			blue_max= js6.getValue();
     		//	System.out.println("reds:"+reds+"  greens:"+greens+"  blues:"+blues);
     			   //System.out.println();
     		}
     	});
         
         
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
    Mat grayimg = new Mat();
    frm.setSize(640, 480);
    frm.setDefaultCloseOperation(frm.EXIT_ON_CLOSE);
    frms.setSize(640, 480);
    frms.setDefaultCloseOperation(frm.EXIT_ON_CLOSE);
    frms.setVisible(true);
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
    
    frm.setLocation(320, 200);
    int old_x=320,old_y=200,new_x=0,new_y=0;
   
    
	/*Graphics g = null;
	g.drawRect(10, 20,50,100);
	g.setColor(Color.RED);*/
	
    
    	do
	    {
		    camera.grab();
		    camera.retrieve(frame);
		  
		    camera.read(frame);
		 
		    Highgui.imwrite("camera.png", frame);
		    
		    File file = new File("camera.png");  
		    
		    Imgproc.cvtColor(frame, grayimg,Imgproc.COLOR_RGB2GRAY);
		    Imgproc.blur(grayimg, grayimg,new Size(10.0,10.0));
		    
		    
		    image_new = ImageIO.read(file);
		    
		    
		   
		   
		    
		    ////////////////////////////////////////////////////////
		    
		   // File file_out=new File("C:/Users/Harshin/Desktop/depcryt_pics/rushi.png");
			int[] pixels = image_new.getRGB(0,0, image_new.getWidth(), image_new.getHeight(), null, 0, image_new.getWidth());
			int x=0,y=0;
			int count=0;//,count2=0;
			int k=1;//so no divide by 0 error
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
			
	       	    
	       	  //  if(red>green+55 && red>blue+55)
	    
	       	    if((red>=red_min && red<=red_max) && (green>=green_min && green<=green_max) && (blue>=blue_min && blue<=blue_max))
	       	    {
	       	    	image_new.setRGB(x, y, -1);  
	       	    	new_x+=x;
	       	    	new_y+=y;
	       	    	k++;
	       	    	count++;
	       	    }
	       	    else
	       	    {
	       	    	image_new.setRGB(x, y, 0); 
	       	    	//count2++; 
	       	    }
	       	 x++;
			}   
		
			if(count>500)///////newly added
			{
				new_x/=k;
				new_y/=k;
			}
			else
			{
				new_x=old_x;
				new_y=old_y;
			}
	//		System.out.println(new_x+" "+new_y);
			//System.out.println(count);

		//	if(Math.abs(new_x-old_x)>10 || Math.abs(new_y-old_y)>10)
			{			
				/*java.awt.Point p;
				p=frm.getLocation();*/
			//	frm.setLocation(p.x-(new_x-old_x)*3, p.y+(new_y-old_y)*2);
			}
			old_x=new_x;
			old_y=new_y;
		
			if(!flag)
			{
				/*Dimension d;
				d=frm.getSize();*/
				//frm.setSize(d.width+(count-count2)/20, d.height+(count-count2)/20);
				//label_1.setSize(d.width+(count-count2)/20, d.height+(count-count2)/20);
			}
			flag=false;
			//count2=count;
			 label_1.setIcon(new ImageIcon(image_new));	  
	    }while(true);
    }

	public static void main(String args[]) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		new DR4();
		
	}
}
