import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;

public class move implements ItemListener{
	static boolean flag=false;
	static BufferedImage image_old,image_new;
	int old_x=0,old_y=0,new_x=0,new_y=0,sx=640,sy=480,m_y=450,m_x=800;
    float s_count=1,count=1;
    Robot r = null;
    JLabel label_1;
	move(Component C)throws InterruptedException, IOException
	{
		//Imgproc for cvtColor and threshold
		//Core for absdiff
		 
		try {
			r = new Robot();
		} catch (AWTException e1) {
			
		}
		r.mouseMove(m_x,m_y);
		
		JFrame frm=new JFrame();
    	label_1 = new JLabel("",null,JLabel.CENTER);
    	//label_1.setLocation(300, 300);
        frm.add(C,BorderLayout.EAST);
        
        //C.setPreferredSize(75, 30);
        JToggleButton jtb=new JToggleButton("Start Mot Det");
        frm.add(jtb,BorderLayout.SOUTH);
        frm.add(label_1,BorderLayout.CENTER);
       // frm.setLayout(new FlowLayout());
      
        
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
    frm.setSize(800, 620);
    frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frm.setVisible(true);
    
    int[] pix=new int[640*480];
    for(int j=0;j<640*480;j++)
    {
    	pix[j]=0;
    	
    }
    
    frm.setLocation(350, 100);
    jtb.addItemListener(this);
       
    	do
	    {
		    camera.grab();
		    camera.retrieve(frame);
		  
		    camera.read(frame);
		 
		    Highgui.imwrite("camera.png", frame);
		    
		    File file = new File("camera.png");  
		    
		    image_new = ImageIO.read(file);
		    
		    flipImg();
		   
		    
		    label_1.setIcon(new ImageIcon(image_new));	
		    
		    label_1.setPreferredSize(new Dimension(100,100));
		   /* 
		    
		  //  System.out.println(sx+" "+sy);
		    
		    if(image_new.getWidth()>sx || image_new.getHeight()>sy)
	        	label_1.setIcon(new ImageIcon(image_new.getScaledInstance(sx, sy,Image.SCALE_SMOOTH)));
	        
		    else
		    	 label_1.setIcon(new ImageIcon(image_new));*/
		    
		    ////////////////////////////////////////////////////////
		    
		   // File file_out=new File("C:/Users/Harshin/Desktop/depcryt_pics/rushi.png");
		    if(flag)
		    {
			int[] pixels = image_new.getRGB(0,0, image_new.getWidth(), image_new.getHeight(), null, 0, image_new.getWidth());
			int x=0,y=0;
			count=1;//,count2=0;
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
			
	       	    
	       	    if(red>green+25 && red>blue+25)
	       	    {
	       	    	//image_new.setRGB(x, y, -1);  
	       	    	new_x+=x;
	       	    	new_y+=y;
	       	    	k++;
	       	    	count++;
	       	    }
	       	 x++;
			}   
		
			new_x/=k;
			new_y/=k;
			//System.out.println(new_x+" "+new_y);
		//	System.out.println(count);

			if(Math.abs(new_x-old_x)>5 || Math.abs(new_y-old_y)>5)
			{			
				java.awt.Point p;
				p=C.getLocation();
				C.setLocation(p.x+(new_x-old_x)*2, p.y);//+(new_y-old_y)*2);
				
				try {
					r = new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					r.mouseMove(m_x+(new_x-old_x),m_y+(new_y-old_y));
					m_x=m_x+(new_x-old_x);
					m_y=m_y+(new_y-old_y);
					
					
					if(count>s_count*1.2)
					{
						r.mousePress(InputEvent.BUTTON1_MASK);
						System.out.println(count+" "+s_count);
						r.mouseRelease(InputEvent.BUTTON1_MASK);
						
					}
			}
			
			old_x=new_x;
			old_y=new_y;
		    }
		    
		   
	    }while(true);
	}
	
	public void itemStateChanged(ItemEvent ie) 
	{
		
		
		JToggleButton jtb2;
		jtb2=(JToggleButton) ie.getItem();
		if(jtb2.isSelected()==true)
		{
			flag=true;
			int[] pixels = image_new.getRGB(0,0, image_new.getWidth(), image_new.getHeight(), null, 0, image_new.getWidth());
			int x=0,y=0;
			
			int k=1;
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
	       	    	old_x+=x;
	       	    	old_y+=y;
	       	    	k++;
	       	    	s_count++;
	       	    }
	       	 x++;
			}   
		
			old_x/=k;
			old_y/=k;
			//m_x=(int) (old_x+label_1.getLocation().getX());
			//m_y=(int) (old_y+label_1.getLocation().getY());
			m_x=old_x+315;
			m_y=old_y+100;
			System.out.println(old_x+" "+old_y+" "+label_1.getLocation().getX()+" "+label_1.getLocation().getY());
			
			try {
				r = new Robot();
			} catch (AWTException e1) {
				
			}
			r.mouseMove(m_x,m_y);
		}
		
		else
		{
			flag=false;
		}
	}
	
	void flipImg() throws IOException
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
        	
        	if(y==image_new.getHeight())
        	{
        		break;
        	}
			
           	image_new.setRGB(image_new.getWidth()-x-1, y, pixels[i]);  
      	 x++;
		}   
		
	}
	
	
}
