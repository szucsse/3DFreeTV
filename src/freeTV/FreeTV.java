
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

public class FreeTV extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;	
	//组件
	JFrame frame ;
    JMenuBar menubar;//菜单
    JMenu file,setting,help;
    JMenuItem open,exit;
    //这里添加“Setting”
    JMenuItem Setting;
    JMenuItem Help,aboutfreetv,feedback;
    JPopupMenu popupMenu;//右键菜单
    JMenuItem mopen,mprior,mnext;
    JButton prior,next,camera;

    static JTextField jtf;//底部状态栏信息
    
	JPanel background = new JPanel();
	JPanel window = new JPanel();
 	JLabel label  = new JLabel();
	JPanel active = new JPanel();
	JPanel monitor = new JPanel();
	JLabel stalabel = new JLabel();
	static JToolBar toolbar = new JToolBar();
	
	//x1,y1为初始坐标，x2,y2为当前坐标
	boolean isCameraOpen = false;
	JLabel x1,x2,y1,y2; //用于显示人脸位置
	JLabel curentPos,firstPos;//分别包含于下面的两个JPanel.
	JPanel firstPosPanel,curentPosPanel;
	
	
	ArrayList<String> imagepath=new ArrayList<String>();//文件路径
	
	int pos = 0;//记录位置
	int num = 0;//记录图片数目
	int current = 0;
	int flag = 0 ;	
	SettingShow settingshow;
	HelpShow helpshow;
	AboutShow aboutshow;
	FeedbackShow feedbackshow;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//获取主屏幕大小
	int Width = screenSize.width;
	int Height = screenSize.height;
  public FreeTV(){
   {
   	//主窗体
   	frame = new JFrame("3DFreeTV");
	 try{
       	 String src ="/img/logo.png";//更改logo
       	 Image image = ImageIO.read(this.getClass().getResource(src));
       	 frame.setIconImage(image);
        }catch(IOException e){
       	 e.printStackTrace();
        }
	frame.setSize((int)(Width*0.9), (int)(Height*0.84));
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	//设置关闭时提示信息
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.addWindowListener(new WindowAdapter() {
//        public void windowClosing(WindowEvent e) {
//        	Object[] o = {"取消","退出"};
//            int a = JOptionPane.showOptionDialog(frame, "确认关闭将退出程序", "温馨提示",
//                    JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,o,o[0]);
//            if (a == 1) {
//            	 frame.dispose();
//            	 System.exit(0);
//            }
//        }
//    });
	
	//菜单
	menubar = new JMenuBar();
	file = new JMenu("File");
	setting = new JMenu("Setting");
	help = new JMenu("Help");
	menubar.add(file);
	menubar.add(setting);
	menubar.add(help);
	
	open = new JMenuItem("Open",KeyEvent.VK_O);
	open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
	String stropen = "/img/open.png";
	ImageIcon iconopen = new ImageIcon(this.getClass().getResource(stropen));
	open.setIcon(iconopen);
	
	exit = new JMenuItem("Exit");
	String strexit = "/img/exit.png";
	ImageIcon iconexit = new ImageIcon(this.getClass().getResource(strexit));
	exit.setIcon(iconexit);
  	
	Setting = new JMenuItem("Setting");
	String strset = "/img/setting.png";
	ImageIcon iconset = new ImageIcon(this.getClass().getResource(strset));
	Setting.setIcon(iconset);
	settingshow = new SettingShow();
	
	Help = new JMenuItem("Help",KeyEvent.VK_H);
	Help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
	String strhelp = "/img/help.png";
	ImageIcon iconhelp = new ImageIcon(this.getClass().getResource(strhelp));
	Help.setIcon(iconhelp);
	helpshow = new HelpShow();
	
	aboutfreetv = new JMenuItem("About FreeTV");
	String strtv = "/img/freetv.png";
	ImageIcon icontv = new ImageIcon(this.getClass().getResource(strtv));
	aboutfreetv.setIcon(icontv);
	aboutshow = new AboutShow();
	
	feedback = new JMenuItem("Feedback");
	String strback = "/img/feedback.png";
	ImageIcon iconback = new ImageIcon(this.getClass().getResource(strback));
	feedback.setIcon(iconback);
	feedbackshow = new FeedbackShow();
	
	file.add(open);
	file.add(exit);
	setting.add(Setting);
	help.add(Help);
	help.add(aboutfreetv);
	help.add(feedback);
	frame.setJMenuBar(menubar);

	//右键菜单
	popupMenu = new JPopupMenu();
	
	mopen = new JMenuItem("Open         ");
	mopen.setIcon(iconopen);
	
	mprior = new JMenuItem("Prior       ");
	String strleft = "/img/left.png";
	ImageIcon iconleft = new ImageIcon(this.getClass().getResource(strleft));
	mprior.setIcon(iconleft);
	
	mnext = new JMenuItem("Next         ");
	String strright = "/img/right.png";
	ImageIcon iconright = new ImageIcon(this.getClass().getResource(strright));
	mnext.setIcon(iconright);
	
	popupMenu.add(mopen);
	popupMenu.add(mprior);
	popupMenu.add(mnext);
	add(popupMenu);
	//显示右键菜单，此处修改成加在label中
	label.addMouseListener(new MouseAdapter(){
		public void mouseReleased(MouseEvent e){
			if(e.isPopupTrigger()){
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}});
//	frame.addMouseListener(new MouseAdapter(){
//		public void mouseReleased(MouseEvent e){
//			if(e.isPopupTrigger()){
//				popupMenu.show(e.getComponent(), e.getX(), e.getY());
//			}
//		}
//	});

	//界面布局
	background.setBackground(new Color(45,45,48));
	BorderLayout backlayout = new BorderLayout(12,5);
	background.setLayout(backlayout);
	frame.add(background,BorderLayout.CENTER);//frame采用边界布局
	
	//主窗口
	window.setBorder(new EtchedBorder(EtchedBorder.RAISED));
	window.setLayout(new BorderLayout());
	window.setBackground(new Color(45,45,48));
	window.setPreferredSize(new Dimension((int)(Width*0.70), (int)(Height*0.71)));
	label.setSize(new Dimension((int)(Width*0.62), (int)(Height*0.71)));
	label.setHorizontalAlignment(0);//使图标居中显示
	prior = new JButton("");
	prior.setToolTipText("prior view");
	prior.setPreferredSize(new Dimension(100,545));//合适大小
	prior.setVerticalTextPosition(JButton.TOP);//设置button的文字位置来改变图标的位置，文字在top
	//icon就在center
    prior.setContentAreaFilled(false);//去除按钮内容绘制
	prior.setBorder(null);//去除按钮边框
	prior.setFocusPainted(false);//去除按钮焦点边框
	prior.addMouseListener(new MouseAdapter()
	{
		public void mouseEntered(MouseEvent e){
			String str = "/img/prior.png";
			ImageIcon iconn = new ImageIcon(this.getClass().getResource(str));
			prior.setIcon(iconn);
		}
		public void mouseExited(MouseEvent e){
			String str = "/img/blank.png";
			ImageIcon iconn = new ImageIcon(this.getClass().getResource(str));
			prior.setIcon(iconn);
		}
		public void mousePressed(MouseEvent e ){
			String str = "src/img/prior_small.png";
			ImageIcon iconn = new ImageIcon(str);
			prior.setIcon(iconn);	
		}
		public void mouseReleased(MouseEvent e){
			String str = "src/img/prior.png";
			ImageIcon iconn = new ImageIcon(str);
			prior.setIcon(iconn);	
		}
	});
	
	next = new JButton("");
	next.setToolTipText("next view");
	next.setPreferredSize(new Dimension(100,545));
	next.setVerticalTextPosition(JButton.TOP);
	next.setContentAreaFilled(false);
	next.setBorder(null);
	next.setFocusPainted(false);
	next.addMouseListener(new MouseAdapter()
	{
		public void mouseEntered(MouseEvent e){
			String str = "/img/next.png";
			ImageIcon iconn = new ImageIcon(this.getClass().getResource(str));
			next.setIcon(iconn);
		}
		public void mouseExited(MouseEvent e){
		    String str = "/img/blank.png";
			ImageIcon iconn = new ImageIcon(this.getClass().getResource(str));
			next.setIcon(iconn);
		}
		public void mousePressed(MouseEvent e ){
			String str = "src/img/next_small.png";
			ImageIcon iconn = new ImageIcon(str);
			next.setIcon(iconn);	
		}
		public void mouseReleased(MouseEvent e)
		{
			String str = "src/img/next.png";
			ImageIcon iconn = new ImageIcon(str);
			next.setIcon(iconn);	
		}
		
	});
    window.add(prior,BorderLayout.LINE_START);//采用边界布局，将prior放在行开始处
	window.add(next,BorderLayout.LINE_END);//采用边界布局，将next放在行结束处
    window.add(label,BorderLayout.CENTER);
    window.requestFocus(true);//获取焦点
	
	//事件窗口
    active.setBorder(new EtchedBorder(EtchedBorder.RAISED));
	active.setLayout(new FlowLayout(FlowLayout.LEADING));
	active.setBackground(new Color(30,30,30));//set historyFrame background color
	active.setPreferredSize(new Dimension(340,545));
	

	monitor.setBackground(new Color(236,240,245));//广告栏
	monitor.setPreferredSize(new Dimension(325,200));
	monitor.add(new JLabel("<html><b><font color=red><font size = 20>广告位招租"));
	monitor.add(new JLabel("<html><b><font color=red><font size = 20>联系人:陈树昱 "));
	monitor.add(new JLabel("<html><b><font color=red><font size = 20>电话:1346989746 "));
	

	camera = new JButton();	//摄像头按钮
	camera.setToolTipText("Open Camera");
	camera.setPreferredSize(new Dimension(325,100));
	camera.setIcon(new ImageIcon("src/img/白摄像头.png"));
	camera.setBorder(null);
	camera.setFocusPainted(false);
	camera.setContentAreaFilled(false);

	//TODO ,在这里加上显示当前位置和初始位置的JPanel	

	firstPos = new JLabel(new ImageIcon("src/img/white_position.png"));
	firstPos.setText("<html><font color=\"eaeced\">初始位置:  ");
	x1 = new JLabel("<html><font size =6><Font color=white> X:"); x1.setPreferredSize(new Dimension(140,50));
	y1 = new JLabel("<html><font size =6><Font color=white> Y:"); y1.setPreferredSize(new Dimension(140,50));
	firstPosPanel = new JPanel();
	firstPosPanel.setPreferredSize(new Dimension(160,230));
	firstPosPanel.add(firstPos);
	firstPosPanel.add(x1);	firstPosPanel.add(y1);
	firstPosPanel.setBackground(active.getBackground()); //与active相同的背景色。

	
	curentPos = new JLabel(new ImageIcon("src/img/white_position.png"));
	curentPos.setText("<html><font color=\"eaeced\">当前位置:  "); 
	x2 = new JLabel("<html><font size =6><Font color=white> X:"); x2.setPreferredSize(new Dimension(140,50));
	y2 = new JLabel("<html><font size =6><Font color=white> Y:"); y2.setPreferredSize(new Dimension(140,50));
	curentPosPanel = new JPanel();
	curentPosPanel.setPreferredSize(new Dimension(160,230));
	curentPosPanel.add(curentPos);
	curentPosPanel.add(x2);curentPosPanel.add(y2);
	curentPosPanel.setBackground(active.getBackground()); 

	active.add(monitor);
	active.add(camera);
	active.add(firstPosPanel);
	active.add(curentPosPanel);

	
 
    //状态栏
	toolbar.setUI(null);
	toolbar.setBackground(new Color(30,30,30));
	toolbar.add(stalabel);
	toolbar.setPreferredSize(new Dimension(1202,(int)(Height*0.03)));
	jtf = new JTextField(" Ready");
	jtf.setForeground(Color.WHITE);
	jtf.setBackground(new Color(25,95,225));
	toolbar.add(jtf);
	background.add(window,BorderLayout.CENTER);
	background.add(active,BorderLayout.EAST);
	background.add(toolbar,BorderLayout.SOUTH);
   
	//事件监听
	open.addActionListener(this);
	exit.addActionListener(this);
	Setting.addActionListener(this);
	Help.addActionListener(this);
	aboutfreetv.addActionListener(this);
	feedback.addActionListener(this);
	mopen.addActionListener(this);
	prior.addActionListener(this);
	next.addActionListener(this);
	setting.addActionListener(this);
	camera.addActionListener(this);
	//添加左右键盘快捷键
	prior.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent k) {
					if(k.getKeyCode()==KeyEvent.VK_LEFT ){
						priorPicture();
					}

					else if(k.getKeyCode()==KeyEvent.VK_RIGHT ){
						nextPicture();
					}
				}
			}
		);
	next.addKeyListener(new KeyAdapter(){
		public void keyPressed(KeyEvent k) {
			if(k.getKeyCode()==KeyEvent.VK_LEFT ){
				priorPicture();
			}

			else if(k.getKeyCode()==KeyEvent.VK_RIGHT ){
				nextPicture();
			}
		}
	}
);

	mprior.addActionListener(this);
	mnext.addActionListener(this);
	label.addMouseMotionListener(new MouseAdapter()
			{
				String font = "<html><font size=6><font color = \"eaeced\">X: ";//字体。
				public void mouseMoved(MouseEvent e) {

					String xx = Integer.toString(e.getX());
					String yy = Integer.toString(e.getY());
					x2.setText(font+xx);
					y2.setText(font+yy);
					active.repaint();
				}
			});
	    }
    } 
  
  //事件处理
 	public void actionPerformed(ActionEvent e){
     	//打开文件

    	if(e.getSource()== open||e.getSource()==mopen){
    		flag=0;
    		//imagepath.clear();
    		//保存上一次打开路径
    		Preferences pref = Preferences.userRoot().node(this.getClass().getName());
    		String lastPath = pref.get("lastPath", "");
    		JFileChooser fileChooser =null;
    		if(!lastPath.equals("")){
    			fileChooser = new JFileChooser(lastPath);
    		}
    		else
    			fileChooser = new JFileChooser();
    		//创建文件扩展名过滤器，只显示图片格式的文件
    		FileNameExtensionFilter filter=new FileNameExtensionFilter("JPEG,GIF,BMP,PNG,SVG,PSD,EPS,AI Images","jpg","jpeg","gif","bmp","png","svg","psd","eps","ai");
    		fileChooser.setFileFilter(filter);									//为文件对话框设置扩展名过滤器
    		fileChooser.setMultiSelectionEnabled(true);
    		fileChooser.setDialogTitle("Open");
    		int option = fileChooser.showOpenDialog(this);
    		if(option==JFileChooser.APPROVE_OPTION){
    			File fileArray[] = fileChooser.getSelectedFiles();   
    			String filelist = "nothing";
    			num = fileArray.length;
    			filelist = num + " pictures";  
    			if(num==1){
    				String firstSelect=fileArray[0].getPath();
    				File ff=new File(fileArray[0].getPath());
    				File file=ff.getParentFile();
    				FileFilter fileFilter = new FileFilter() {		//设置过滤器
    			        public boolean accept(File file) {
    			        	String s=file.getName().toLowerCase();
    			            if (s.endsWith(".jpg") || s.endsWith(".jpeg") || s.endsWith(".png") || s.endsWith(".gif")) {
    			                return true;
    			            }else if(s.endsWith(".bmp") || s.endsWith(".svg") || s.endsWith(".psd") || s.endsWith(".eps") || s.endsWith(".ai")) {
    			                return true;
    			            }
    			            return false;
    			        }
    			    };		
    				fileArray=file.listFiles(fileFilter);		//过滤出图片格式的文件
    				num = fileArray.length;
    				for(int i=0;i<fileArray.length;i++){
    					if(fileArray[i].getPath().equals(firstSelect)){
    						flag=i;
    						pos=flag;
    						break;
    					}
    				}
    			}
    			pref.put("lastPath", fileArray[0].getPath()); //保存路径
    			for (int i = 0; i < fileArray.length; i++) {  
    				imagepath.add(fileArray[i].getPath());  
  	          	}
    			jtf.setForeground(Color.WHITE);
    			jtf.setBackground(new Color(30,30,30));
    			jtf.setText(" you choosed: "+filelist);//显示状态
    			String path = fileArray[flag].getPath();
    			ImageIcon windowlabel = new ImageIcon(path);
    			if(windowlabel.getIconWidth()>window.getSize().width*0.99||windowlabel.getIconHeight()>window.getSize().height*0.99){
    				for(double i=0.9;i>0;i-=0.02){
						double imgLen = i*windowlabel.getIconWidth();
						double imgHei = i*windowlabel.getIconHeight();
						if(imgLen<=window.getSize().width*0.99 && imgHei<=window.getSize().height*0.99){
							windowlabel.setImage(windowlabel.getImage().getScaledInstance((int)imgLen,(int)imgHei, Image.SCALE_FAST));
							break;
						}
					}
				}
	    	    //如果图片大小超过，则等比例压缩  每次压缩0.02
	    	    label.setIcon(windowlabel); 
	    	}//if
	    	else{
	    		jtf.setForeground(Color.WHITE);
	 			jtf.setBackground(new Color(157,85,184));
	    		jtf.setText(" you canceled");	
	    	}
    	}

    	//退出程序
    	else if(e.getSource()==exit){
    		Object[] o = {"取消","退出"};
            int a = JOptionPane.showOptionDialog(frame, "确认关闭将退出程序", "温馨提示",
                    JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,o,o[0]);
            if (a == 1) {
            	 frame.dispose();
            	 System.exit(0);
            }
    	}
    	//设置
    	else if(e.getSource()==Setting){
    		settingshow.setVisible(true);
    	}
    	//帮助
    	else if(e.getSource()==Help){
    		helpshow.setVisible(true);
    	}
    	//关于程序
    	else if(e.getSource()==aboutfreetv){
    	aboutshow.setVisible(true);
    	}
    	//反馈
    	else if(e.getSource()==feedback){
    	feedbackshow.setVisible(true);	
    	}
    	//上一个
    	else if(e.getSource()== prior||e.getSource()==mprior){
    		priorPicture();
    	}
    	//下一个
    	else if(e.getSource()== next||e.getSource()==mnext){
    		nextPicture();
    	}
    	else if(e.getSource()==camera)
    	{
    		if(!isCameraOpen)
    		{
    			camera.setIcon(new ImageIcon("src/img/红摄像头.png"));
    			isCameraOpen = true;
    			jtf.setText("turn 【ON】 the camera");
    			jtf.setBackground(new Color(25,95,225));
    		}
    		else 
    		{
    			camera.setIcon(new ImageIcon("src/img/白摄像头.png"));
    			isCameraOpen = false;
    			jtf.setText("turn 【OFF】 the camera");
    			jtf.setBackground(new Color(157,85,184));
    		} 
    	}
    } 
 	public void nextPicture()
 	{
 		try{
  		if(pos == num-1){
    	    pos = 0;	
    		}
    		else{
    			pos++;
    		}
    		ImageIcon windowlabel = new ImageIcon(imagepath.get(pos));
			if(windowlabel.getIconWidth()>window.getSize().width*0.99||windowlabel.getIconHeight()>window.getSize().height*0.99)
				for(double i=0.9;i>0;i-=0.02){
					{
						double imgLen = i*windowlabel.getIconWidth();
						double imgHei = i*windowlabel.getIconHeight();
						if(imgLen<=window.getSize().width*0.99 && imgHei<=window.getSize().height*0.998)
						{
							windowlabel.setImage(windowlabel.getImage().getScaledInstance((int)imgLen,(int)imgHei, Image.SCALE_SMOOTH));
							break;
						}
					}			
				}
      	    label.setIcon(windowlabel);
      	    jtf.setForeground(Color.WHITE);
      		jtf.setBackground(new Color(30,30,30));
  		    jtf.setText(" "+imagepath.get(pos));
 		}
 		catch(Exception ee){
 			jtf.setForeground(Color.WHITE);
 			jtf.setBackground(Color.RED);
  		    jtf.setText(" NOTHING");
 		}
 	}
 	public void priorPicture()
 	{
 		try{
  		if(pos == 0){
			pos = num-1;
		}
		else{
			pos--;
		}
		ImageIcon windowlabel = new ImageIcon(imagepath.get(pos));
		if(windowlabel.getIconWidth()>window.getSize().width*0.99||windowlabel.getIconHeight()>window.getSize().height*0.99)
			for(double i=0.9;i>0;i-=0.02){
				{
					double imgLen = i*windowlabel.getIconWidth();
					double imgHei = i*windowlabel.getIconHeight();
					if(imgLen<=window.getSize().width*0.99 && imgHei<=window.getSize().height*0.99)
					{
						windowlabel.setImage(windowlabel.getImage().getScaledInstance((int)imgLen,(int)imgHei, Image.SCALE_FAST));
						break;
					}
				}			
			}
  	    label.setIcon(windowlabel);
		jtf.setForeground(Color.WHITE);
		jtf.setBackground(new Color(30,30,30));
		jtf.setText(" "+imagepath.get(pos));
 		}
 		catch(Exception ee){
 			jtf.setForeground(Color.WHITE);
 			jtf.setBackground(Color.RED);
  		    jtf.setText(" NOTHING");
 		}
 	}
    public static void main(String args[]){
    	 java.awt.EventQueue.invokeLater(new Runnable(){
    	   public  void run(){
    		new FreeTV();   
    	   }
       });
    }
}