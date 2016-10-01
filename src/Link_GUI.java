import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Link_GUI extends JFrame 
					  implements MouseListener,ChangeListener, ActionListener{
	//*********************************锟斤拷锟斤拷锟斤拷锟�********************************
	private final int WIDTH = 1176;
	private final int HEIGHT = 680;
	private final int X_LOCATION = 80;
	private final int Y_LOCATION = 50;
	public final int ROW = 8;
	public final int COL = 15;
	private final int UNIT = 80;
	private final int PICTURES = this.ROW * this.COL;
	
	//************************************全锟街憋拷锟斤拷******************************
	public static boolean is_over = false; 					
	public static int left_button;									//剩锟斤拷图片锟斤拷锟斤拷锟斤拷
	private String style = "animal/";					//锟斤拷锟窖★拷锟�
	private JButton buttons[][] = new JButton[this.ROW][this.COL];
	private Timer time;
	private JProgressBar progress; 
	private Picture pictures[][] = new Picture[this.ROW][this.COL];
	
	private int time_slice = 55;								//锟斤拷时锟斤拷锟斤拷时锟斤拷片
	
	
	//**********************锟剿碉拷锟斤拷**********************************************
	JMenuItem item11;
	JMenuItem item12;
	JMenuItem item13;
	JMenuItem item14;
	JMenuItem item15;
	JMenuItem item21;	
	
	
	/**
	 * 锟斤拷锟届方锟斤拷锟斤拷锟斤拷锟芥布锟斤拷
	 */
	public Link_GUI(){
		//**************************锟斤拷锟芥布锟斤拷****************************
		//**************************锟阶诧拷锟斤拷****************************
		this.setTitle("link&link");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(this.WIDTH,this.HEIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocation(this.X_LOCATION, this.Y_LOCATION);
		
		//**********************锟叫硷拷锟�***********************************
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		
		//************************锟叫放凤拷锟斤拷锟絧anel***************************
		JPanel panel = new JPanel();
		panel.setSize(this.WIDTH, this.HEIGHT - this.UNIT);
		panel.setLayout(new GridLayout(this.ROW,this.COL));
		//锟窖凤拷锟斤拷锟脚诧拷锟斤拷panel锟斤拷
		for(int i=0; i < this.ROW; i++){
			for(int j=0; j < this.COL; j++){
				this.buttons[i][j] = new JButton("<html><font size = 4> </font></html>");
				buttons[i][j].setSize(this.UNIT,this.UNIT);
				panel.add(this.buttons[i][j]);
			}
		}
		//**********************************锟斤拷锟节底诧拷锟侥诧拷锟斤拷***********************
		JPanel bottom = new JPanel();
		bottom.setSize(this.WIDTH, this.UNIT);
		bottom.setLayout(new FlowLayout());

		//**********************************锟斤拷锟斤拷锟�*****************************
		this.progress = new JProgressBar();
		this.progress.setOrientation(JProgressBar.HORIZONTAL);
		this.progress.setMinimum(0);
		this.progress.setMaximum(2000);
		this.progress.setValue(2000);
		this.progress.setStringPainted(true);
		this.progress.addChangeListener(this);
		this.progress.setPreferredSize(new Dimension(600,20));
		this.progress.setForeground(Color.green);
		bottom.add(progress);
			
		//******************************锟斤拷锟叫硷拷锟斤拷锟接碉拷锟斤拷锟斤拷锟斤拷*********************
		c.add(panel, "Center");
		c.add(bottom,"South");
		
		
		
		//*************************锟斤拷锟矫菜碉拷锟斤拷***********************************
		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);
		
		JMenu menu1 = new JMenu("game");
		JMenu menu2 = new JMenu("info");
		
		menu.add(menu1);
		menu.add(menu2);
		
		item11 = new JMenuItem("new game");
		item12 = new JMenuItem("animal");
		item13 = new JMenuItem("class 18");
		item14 = new JMenuItem("unsure");
		item15 = new JMenuItem("exit");
		item21 = new JMenuItem("info");
		
		menu1.add(item11);
		menu1.add(item12);
		menu1.add(item13);
		menu1.add(item14);
		menu1.add(item15);
		menu2.add(item21);
		
		item11.addActionListener(this);
		item12.addActionListener(this);
		item13.addActionListener(this);
		item14.addActionListener(this);
		item15.addActionListener(this);
		item21.addActionListener(this);
		
		//*******************************注锟斤拷锟铰硷拷锟斤拷锟斤拷***************************
		for(int i=0; i < this.ROW; i++){
			for(int j=0; j < this.COL; j++){
				this.buttons[i][j].addMouseListener(this);
			}
		}
		
		//****************************锟斤拷始锟斤拷锟斤拷锟斤拷********************************
		this.time = new Timer(this.time_slice,this);
		this.left_button = this.PICTURES;
		for(int i=0; i < this.ROW; i++){
			for(int j=0; j < this.COL; j++){
				this.pictures[i][j] = new Picture(this, i, j);
			}
		}
		this.time.start();
		this.init();
		
	}//end of constructor
	
	
	public void init(){
		this.is_over = false;
		this.time.restart();
		this.left_button = this.PICTURES;
		this.progress.setValue(2000);
		this.reset_picture();
		this.arrange_picture();
		 
		Thread check = new Thread(new Check(this));
		check.start();
	}
	
	
	
	/**
	 * 锟斤拷锟斤拷锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 */
	private void reset_picture(){
		for(int i=0; i < this.ROW; i++){
			for(int j=0; j < this.COL; j++){
				this.pictures[i][j].set_state(0);
				this.pictures[i][j].set_number(0);
				this.pictures[i][j].reset_have_picture();
				this.buttons[i][j].setVisible(true);
			}
		}
	}
	
	/**
	 * 锟斤拷锟街诧拷图片
	 */
	private void arrange_picture(){
		Random rand = new Random();
		int base = rand.nextInt(10) + 1;
		
		for(int i=0; i< this.PICTURES/2; i++){
			int number = rand.nextInt(20) + 1 + base;
			int picture_index = rand.nextInt(this.PICTURES);
			int row = picture_index / this.COL;
			int col  = picture_index % this.COL;
	
			for(int j=0; j<2; j++){
				while(this.pictures[row][col].get_have_picture()){
					picture_index = rand.nextInt(this.PICTURES);
					row = picture_index / this.COL;
					col  = picture_index % this.COL;
				}
				this.pictures[row][col].set_number(number);
			}
		}
		
	}
	
	
	
	// ************************* 锟斤拷应锟铰硷拷锟侥凤拷锟斤拷**********************************
	public void set_picture(int number ,int row , int col){
		ImageIcon img = new ImageIcon(this.style + number + ".1.jpg","picture");
		this.buttons[row][col].setIcon(img);
	}
	public void change_color(int number,int row, int col){
		ImageIcon img = new ImageIcon(this.style + number + ".2.jpg","picture");
		this.buttons[row][col].setIcon(img);
	}
	public void recover_color(int number ,int row, int col){
		ImageIcon img = new ImageIcon(this.style + number + ".1.jpg","picture");
		this.buttons[row][col].setIcon(img);
	}
	public int get_number(int row, int col){
		return this.pictures[row][col].get_number();
	}
	public void change_state(int row, int col, int state){
		this.pictures[row][col].set_state(state);
	}
	public int get_state(int row, int col){
		return this.pictures[row][col].get_state();
	}
	public void disappear(int row, int col){
		this.buttons[row][col].setVisible(false);
	}

	/**
	 * 剩锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 锟斤拷锟斤拷锟斤拷锟街碉拷锟斤拷锟�0锟斤拷为锟斤拷锟斤拷
	 * 锟斤拷锟缴癸拷锟斤拷去图片锟斤拷锟酵碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 锟斤拷锟矫伙拷锟绞ｏ拷锟酵计拷耍锟斤拷捅锟绞撅拷锟较肥わ拷锟�
	 * 	  1.时锟斤拷片锟斤拷锟劫ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷戏锟酵伙拷锟斤拷战锟斤拷锟�
	 *    2.锟斤拷时锟斤拷停止
	 *    3.锟斤拷锟斤拷锟斤拷示锟斤拷息锟侥对伙拷锟斤拷
	 */
	public void button_reduce(){
		this.left_button --;
		this.progress.setValue(this.progress.getValue() + 50);
		if(this.left_button == 0){
			this.time_reduce();				//锟斤拷锟斤拷锟较肥わ拷锟绞憋拷锟狡拷锟斤拷锟�
			this.time.stop();				//停止锟斤拷时
			this.is_over = true;
			Object[] options = {"yes","no"}; 
			int response=JOptionPane.showOptionDialog(this, "Play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(0 == response){
				this.init();
			}else if(1 == response){
				System.exit(0);
			}
			this.init();
		}
	}
	/**
	 * 时锟斤拷片锟斤拷锟斤拷5锟斤拷锟斤拷
	 */
	private void time_reduce(){
		if(this.time_slice >= 15){
			this.time_slice -= 5;
		}
	}
	
	//==========================================================================
	//***********************锟斤拷锟斤拷锟斤拷锟斤拷*******************************************
	public static void main(String args[]){
		new Link_GUI();
	}	
	//***********************锟斤拷锟斤拷锟斤拷锟斤拷*******************************************
	//==========================================================================
	
	
	
	//==========================================================================
	//***********************锟铰硷拷锟斤拷应*******************************************
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int row=0; row<this.ROW; row++){
			for(int col=0; col<this.COL; col++){
				if(e.getSource() == this.buttons[row][col]){		
					this.pictures[row][col].click();	
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.progress){
			int value = this.progress.getValue();
			if(value == 0 && this.left_button >0){
				this.time.stop();
				this.is_over = true;
				//锟斤拷示锟斤拷戏锟斤拷锟斤拷
				Object[] options = {"yes"," no "}; 
				int response=JOptionPane.showOptionDialog(this, "Play again?", "Game over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if(0 == response){
					this.init();				//锟斤拷锟斤拷锟斤拷戏
				}else if(1 == response){
					System.exit(0);
				}
				this.init();					//锟斤拷锟斤拷锟斤拷戏
			}
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.time){
			int value = this.progress.getValue();
			if(value > 0){
				value --;
				this.progress.setValue(value);
			}else{
				this.time.stop();
			}
		}
		////////////////////////////////////////////////////////////////////////
		//******************************88锟剿碉拷锟斤拷********************************	
		if(e.getSource() == this.item11){
			this.init();
		}
		
		if(e.getSource() == this.item12){
			this.style = "animal/";
			this.init();
		}
		
		if(e.getSource() == this.item13){
			this.style = "18/";
			this.init();
		}
		
		if(e.getSource() == this.item14){
			this.init();
		}
		
		if(e.getSource() == this.item15){
			System.exit(0);
		}
		
		if(e.getSource() == this.item21){
			StringBuilder msg = new StringBuilder();
			msg.append("Puzzle.\n\n");
			msg.append("Zhu Linfeng, from Huzzhong University,China\n");
			msg.append("contact with me : 1453065649@qq.com\n");
			msg.append("                                    2013-3-25");
			JOptionPane.showMessageDialog(null, msg);
		}
	}
	

}
