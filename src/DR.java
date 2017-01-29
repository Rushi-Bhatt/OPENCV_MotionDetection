
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class DR {
	static boolean flag=true;
	static BufferedImage image;
	
    public static void main (String args[]) throws InterruptedException, IOException{
    	
    	
    	JFrame frm=new JFrame();
    	JLabel label_1 = new JLabel("",null,JLabel.CENTER);
        frm.add(label_1,BorderLayout.CENTER);
        JButton jb1=new JButton("Capture");
    	frm.add(jb1,BorderLayout.SOUTH);
    	jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//flag=false;
				File file_out=new File("C:/Users/Harshin/Desktop/depcryt_pics/rushi.png");
				try {
					ImageIO.write(image, "png", file_out);
				} 
				catch (IOException e) {}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
			}
		});
    	//frm.setLayout(new FlowLayout());
        
    System.out.println("Hello, OpenCV");
    // Load the native library.
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    VideoCapture camera = new VideoCapture(0);
    Thread.sleep(1000);
    
   // camera.open(0); //Useless
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
   // System.out.println("Frame Grabbed");
    camera.retrieve(frame);
  //  System.out.println("Frame Decoded");

    camera.read(frame);
  //  System.out.println("Frame Obtained");

   

  //  System.out.println("Captured Frame Width " + frame.width());

    Highgui.imwrite("camera.png", frame);
   // System.out.println("OK");
    
    File file = new File("camera.png");  
    
    image = ImageIO.read(file);
    
    label_1.setIcon(new ImageIcon(image));
   
    
    }while(flag==true);
    
    // No difference
  //  camera.release();
    
    
    }
}
