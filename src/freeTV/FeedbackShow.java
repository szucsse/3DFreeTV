package freeTV;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import java.net.URI;
import java.net.URI.*;
public class FeedbackShow extends JFrame implements ActionListener{
	JLabel logo;
	JLabel la1,la2,la3;
	JButton bu1;
	FeedbackShow(){  
		 super("Feedback");
		 try{
		    String src ="/img/feedback.png";//¸ü¸Älogo
		    Image image = ImageIO.read(this.getClass().getResource(src));
		    this.setIconImage(image);
		    }catch(IOException e){
		     e.printStackTrace();
		   }
	   logo = new JLabel();
	   String strlogo = "/img/logo2.png";
	   ImageIcon icologo = new ImageIcon(this.getClass().getResource(strlogo));
	   logo.setIcon(icologo);
	   logo.setBounds(0, 0, 500, 150);
	   la1=new JLabel(" Thank you for using our software,if you have any suggestions");
	   la1.setBounds(0, 50, 500, 25);
	   la2=new JLabel(" Please click the button below,and fill in the feedback form");
	   la2.setBounds(0, 70, 500, 25);
	   la3=new JLabel(" Help us improve our product");
	   la3.setBounds(0, 90, 500, 25);
	   bu1=new JButton("GO");
	   bu1.setFocusPainted(false);
	   bu1.setBounds(200, 120, 60, 25);
	   bu1.addActionListener(this);
	   setLayout(null);
	   add(logo);
	   add(la1);
	   add(la2);
	   add(la3);
	   add(bu1);
	   setBounds(250,300,500,200);
	   setVisible(false);
	   validate();
	   addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent k){
			setVisible(false);
		}});   
	}
	public void actionPerformed(ActionEvent e){
      try{
    	   URI uri = new java.net.URI("http://www.baidu.com");
    	   java.awt.Desktop.getDesktop().browse(uri);
       }catch(Exception ee){
    	   FreeTV.jtf.setForeground(Color.WHITE);
    	   FreeTV.jtf.setBackground(Color.RED);
    	   FreeTV.jtf.setText(" Open Web Failed");
       }
          setVisible(false);		
	}
}