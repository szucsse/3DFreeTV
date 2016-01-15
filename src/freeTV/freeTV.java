
package freeTV;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
//所有图片放在工程src文件夹下img包中
public class freeTV extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	//组件
	JFrame frame ;
    JMenuBar menubar;//菜单
    JMenu file,setting,help;
    JMenuItem open,exit;
   
    //add setting menu item here
    
    JMenuItem Help,aboutfreetv,feedback;
    JPopupMenu popupMenu;//右键菜单
    JMenuItem mopen,mprior,mnext;
    JButton prior,next;
    JTextField jtf;//底部状态栏信息
    
	JPanel background = new JPanel();
	JPanel window = new JPanel();
 	JLabel label  = new JLabel();
	JPanel active = new JPanel();
	JPanel monitor = new JPanel();
	JLabel stalabel = new JLabel();
	JToolBar toolbar = new JToolBar();
  public freeTV(){
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
	frame.setSize(1230, 648);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//获取主屏幕大小
        //各个组件位置以及大小会在之后根据显示器进行调整
	String scrSize = new String("ScreenSize Width:"+screenSize.width+" Hight:"+screenSize.height);
	//设置关闭时提示信息
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        	Object[] o = {"取消","退出"};
            int a = JOptionPane.showOptionDialog(frame, "确认关闭将退出程序", "温馨提示",
                    JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,o,o[0]);
            if (a == 1) {
            	 System.exit(0);
            }
        }
    });
	
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
  	
	//add setting menu item here
	
	Help = new JMenuItem("Help",KeyEvent.VK_H);
	Help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
	String strhelp = "/img/help.png";
	ImageIcon iconhelp = new ImageIcon(this.getClass().getResource(strhelp));
	Help.setIcon(iconhelp);
	
	aboutfreetv = new JMenuItem("About FreeTV");
	String strtv = "/img/freetv.png";
	ImageIcon icontv = new ImageIcon(this.getClass().getResource(strtv));
	aboutfreetv.setIcon(icontv);
	
	feedback = new JMenuItem("Feedback");
	String strback = "/img/feedback.png";
	ImageIcon iconback = new ImageIcon(this.getClass().getResource(strback));
	feedback.setIcon(iconback);
	
	file.add(open);
	file.add(exit);
	//add setting menu item here
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
	//显示右键菜单
	frame.addMouseListener(new MouseAdapter(){
		public void mouseReleased(MouseEvent e){
			if(e.isPopupTrigger()){
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	});

	//界面布局
	background.setBackground(new Color(45,45,48));
	FlowLayout backlayout = new FlowLayout(FlowLayout.LEADING);
	backlayout.setHgap(10);//设置组件间距
	background.setLayout(backlayout);
	frame.add(background,BorderLayout.CENTER);
	
	//主窗口
	window.setBorder(new EtchedBorder(EtchedBorder.RAISED));
	window.setLayout(null);
	window.setBackground(new Color(45,45,48));//set mainFrame background color
	window.setPreferredSize(new Dimension(851,545));
	
	label.setSize(new Dimension(851,545));
	label.setHorizontalAlignment(0);//使居中
	
	prior = new JButton("");
	prior.setToolTipText("prior view");
	prior.setBounds(-10, -120, 200, 800);
	prior.setContentAreaFilled(false);//去除按钮内容绘制
	prior.setBorder(null);//去除按钮边框
	prior.setFocusPainted(false);//去除按钮焦点边框
	prior.addMouseListener(new MouseAdapter()
	{
		public void mouseEntered(MouseEvent e){//鼠标移入
			String str = "/img/prior.png";//展示图标
			ImageIcon iconn = new ImageIcon(this.getClass().getResource(str));
			prior.setIcon(iconn);
		}
		public void mouseExited(MouseEvent e){
			String str = "/img/blank.png";//透明空白图片blank，即设置prior的图标为透明
			ImageIcon iconn = new ImageIcon(this.getClass().getResource(str));
			prior.setIcon(iconn);
		}
		public void mousePressed(MouseEvent e ){
			String str = "src/img/prior_small.png";
			ImageIcon iconn = new ImageIcon(str);
			prior.setIcon(iconn);	
		}
		public void mouseReleased(MouseEvent e)
		{
			String str = "src/img/prior.png";
			ImageIcon iconn = new ImageIcon(str);
			prior.setIcon(iconn);	
		}
	});
	
	next = new JButton();
	next.setToolTipText("next view");
	next.setBounds(760, -120, 200, 800);
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
    window.add(prior);
	window.add(next);
    window.add(label);
	
	//事件窗口
	active.setBorder(new EtchedBorder(EtchedBorder.RAISED));
	active.setLayout(new FlowLayout(FlowLayout.LEADING));
	active.setBackground(new Color(30,30,30));//set historyFrame background color
	active.setPreferredSize(new Dimension(340,545));
	monitor.setBackground(new Color(236,240,241));
	monitor.setPreferredSize(new Dimension(325,200));
	active.add(monitor);
	
   //状态栏
	toolbar.setUI(null);
	toolbar.setBackground(new Color(30,30,30));
	toolbar.add(stalabel);
	toolbar.setPreferredSize(new Dimension(1202,22));
	jtf = new JTextField(""+scrSize);
	jtf.setForeground(Color.WHITE);
	jtf.setBackground(new Color(30,30,30));
	toolbar.add(jtf);
	background.add(window);
	background.add(active);
	background.add(toolbar);
   
	//事件监听
	open.addActionListener(this);
	exit.addActionListener(this);
	Help.addActionListener(this);
	aboutfreetv.addActionListener(this);
	feedback.addActionListener(this);
	mopen.addActionListener(this);
	prior.addActionListener(this);
	next.addActionListener(this);
	mprior.addActionListener(this);
	mnext.addActionListener(this);
    }
    } 
   //事件处理
	public void actionPerformed(ActionEvent e){
    	//打开文件
    	if(e.getSource()== open||e.getSource()==mopen){
    		JFileChooser fileChooser=new JFileChooser();
    		fileChooser.setCurrentDirectory(new File("."));
    		fileChooser.setDialogTitle("Open");
    		fileChooser.showOpenDialog(this);
    		fileChooser.setVisible(true);
    	    String path = fileChooser.getSelectedFile().getPath();
    	    System.out.print(path);
    	    ImageIcon windowlabel = new ImageIcon(path);
    	    label.setIcon(windowlabel);//设置window中的label的图片   	    
            	    
    	}
    	//退出程序
    	if(e.getSource()==exit){
    		System.exit(0);
    	}
    	//add setting menu item here
    	//帮助
    	if(e.getSource()==Help){
    		
    	}
    	//关于程序
    	if(e.getSource()==aboutfreetv){
    		
    	}
    	//反馈
    	if(e.getSource()==feedback){
    		
    	}
    	//上一个
    	if(e.getSource()== prior||e.getSource()==mprior){
    		
    	}
    	//下一个
    	if(e.getSource()== next||e.getSource()==mnext){
    		
    	}
    	
    }
    
    public static void main(String args[]){
    	 java.awt.EventQueue.invokeLater(new Runnable(){
    	   public  void run(){
    		new freeTV();   
    	   }
       });
    }
}
