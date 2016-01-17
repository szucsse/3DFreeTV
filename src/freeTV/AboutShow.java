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
import java.net.URI;
import java.util.ArrayList;
import java.util.prefs.Preferences;
public class AboutShow extends JFrame implements ActionListener{
	JLabel logo;
	JLabel la1,la2;
	JButton bu1;
	//Container Con1;
	/////
	AboutShow(){  
		 super("About");
		 try{
		    String src ="/img/logo.png";//¸ü¸Älogo
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
	   la1=new JLabel("  3DFreeTv JAVA Alpha");
	   la1.setBounds(0, 60, 500, 25);
	   la2=new JLabel("  January 2016");
	   la2.setBounds(0, 80, 500, 25);
	   bu1=new JButton("OK");
	   bu1.setFocusPainted(false);
	   bu1.addActionListener(this);
	   bu1.setBounds(200, 120, 80, 25);
	   bu1.addActionListener(this);
	   setLayout(null);
	   add(logo);
	   add(la1);
	   add(la2);
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
          setVisible(false);		
}
}
//////////////////////////////////////////////////////////////////////////////////
