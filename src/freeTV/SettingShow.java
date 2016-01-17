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
public class SettingShow extends JFrame implements ActionListener{
	JLabel la1,la2;
	JButton bu1;
	SettingShow(){  
		 super("Setting");
		 try{
		    String src ="/img/setting.png";//¸ü¸Älogo
		    Image image = ImageIO.read(this.getClass().getResource(src));
		    this.setIconImage(image);
		    }catch(IOException e){
		     e.printStackTrace();
		   }
	   la1=new JLabel("Setting");
	   la1.setBounds(0, 0, 500, 25);
	   bu1=new JButton("OK");
	   bu1.setFocusPainted(false);
	   bu1.addActionListener(this);
	   bu1.setBounds(200, 120, 80, 25);
	//   Con1=new Container();
	   //Con1.setLayout(new FlowLayout());
	   bu1.addActionListener(this);
	   setLayout(null);
	   add(la1);
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
///////////////////////////////////////////////////////////////////////
