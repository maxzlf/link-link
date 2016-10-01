import java.awt.Color;
import java.util.ArrayList;


public class Picture {
	public static int choosed = 0;			//��ѡ�е�ͼƬ��Ŀ��0��1
	public static int last_row = -1;		//��¼��һ����ѡ�е�ͼƬ��λ��
	public static int last_col = -1;		//��¼��һ����ѡ�е�ͼƬ��λ��

		
	private boolean have_picture;			//�Ƿ����ͼƬ����������ͼƬ����ʼ��Ϊfalse
	private int state;						//���ͼƬ��״̬����ʼ״̬��0��ѡ�к�״̬��Ϊ1����ȥ��Ϊ-1
	private int number;						//���ͼƬ���ı�ţ������ҵ���Ӧ��ͼƬ
	private Link_GUI link;					//ǰ̨�Ľ�����
	private int x;							//��ͼƬ��x����
	private int y;							//��ͼƬ��y����
	
	/**
	 * ���췽��
	 * �õ�����Ķ���
	 * �õ���ͼƬ�ڽ����б����������
	 * @param link
	 * @param x
	 * @param y
	 */
	public Picture(Link_GUI link, int x, int y){
		this.have_picture = false;
		this.link = link;
		this.state = 0;
		this.number = 0;
		this.x = x;
		this.y = y;
	}
	
	//**************************************************************************
	//**************************gets and sets***********************************
	public int get_state(){
		return this.state;
	}
	
	public boolean get_have_picture(){
		return this.have_picture;
	}

	public int get_number(){
		return this.number;
	}
	
	public void set_state(int state){
		this.state = state;
	}
	
	public void reset_have_picture(){
		this.have_picture = false;
	}
	
	public void set_number(int number){
		this.number = number;
		if(number > 0){
			this.link.set_picture(number, this.x, this.y);
		}
		this.have_picture = true;
	}
	
	/**
	 * ��Ӧ������¼�
	 * �����������û��һ��ͼƬ��ѡ�У��� choosed = 0
	 * 		��ı� choosed ��״̬Ϊ 1, ����һ��ͼƬ��ѡ��
	 * 		���ı��ͼƬ��״̬Ϊ1,����ͼƬ��ѡ��
	 * 		�ı����ͼƬΪ������ʾ
	 * 		������һ����ѡ�е�ͼƬ������Ϊ���ͼƬ������
	 * ������������Ѿ���һ��ͼƬ��ѡ�У����һ�������������е�ͼƬ����ô��Ҫ�����ж�
	 * 	1.������ߵ�ͼƬ��Ų�ͬ
	 *    ����һ����ѡ�е�ͼƬ��ѡ��״̬state�ָ�Ϊ0
	 * 	     ����һ����ѡ�е�ͼƬ�ĸ�����ʾȡ��
	 * 	     �����ͼƬ��ѡ��״̬state��Ϊ1
	 *    �����ͼƬ������ʾ
	 *    ������һ����ѡ�е�ͼƬ������Ϊ���ͼƬ������
	 *  2.������ߵ�ͼƬ�����ͬ����˵�����ѡ����������ͬ��ͼƬ
	 *    1.���������ͼƬ֮����·����������
	 *      ��������ͼƬ��ѡ��״̬ stateͬʱ��Ϊ -1����ʾ�Ѿ���ȥ
	 *      ��������ͼƬ��ʧ
	 *      ʣ��ͼƬ��������ȥ����
	 *      ���������ѡ��ͼƬ�� choosed ��Ϊ0
	 *      ������һ����ѡ�е�ͼƬ������Ϊ (-1, -1)
	 *    2.���������ͼƬ֮��û��·��������ȥ
	 *   	 ����һ����ѡ�е�ͼƬ��ѡ��״̬state�ָ�Ϊ0
	 * 	           ����һ����ѡ�е�ͼƬ�ĸ�����ʾȡ��
	 * 	   	 �����ͼƬ��ѡ��״̬state��Ϊ1
	 *   	 �����ͼƬ������ʾ  
	 */
	public void click(){
		if(Picture.choosed == 0 && state == 0){
			Picture.choosed = 1;
			this.state = 1;
			Picture.last_row= this.x;
			Picture.last_col = this.y;
			this.link.change_color(this.number,x, y);
		}else if(choosed == 1 && state == 0){
			this.state = 1;
			if(this.number == this.link.get_number(Picture.last_row, Picture.last_col)){
				boolean is_link = false;
				is_link = this.link(true, this.x, Picture.last_row);
				if(!is_link){
					is_link = this.link(false, this.y, Picture.last_col);
				}
				if(is_link){
					this.state = -1;
					this.link.change_state(last_row, last_col, -1);
					this.link.disappear(x, y);
					this.link.disappear(last_row, last_col);
					this.link.button_reduce();
					this.link.button_reduce();
					this.choosed = 0;
					this.last_row = -1;
					this.last_col = -1;
				}else{
					this.link.change_state(Picture.last_row, Picture.last_col, 0);
					this.link.recover_color(this.link.get_number(last_row, last_col),Picture.last_row, Picture.last_col);
					this.link.change_color(this.number,x, y);
					Picture.last_row = this.x;
					Picture.last_col = this.y;
				}
			}else{
				this.link.change_state(Picture.last_row, Picture.last_col, 0);
				this.link.recover_color(this.link.get_number(last_row, last_col),Picture.last_row, Picture.last_col);
				this.link.change_color(this.number,x, y);
				Picture.last_row = this.x;
				Picture.last_col = this.y;
			}
		}
	}
	
	/**
	 * �жϱ�ѡ�е�����ͼƬ����ͬ��ͼƬ�Ƿ���·��������ȥ
	 * ��·���ͷ���true
	 * û�з���false
	 * @param is_row
	 * @param this_p
	 * @param last_p
	 * @return
	 */
	private boolean link(boolean is_row, int this_p, int last_p){
		boolean has_link = false;
		ArrayList<Integer> this_list = new ArrayList<Integer>();
		ArrayList<Integer> last_list = new ArrayList<Integer>();
		
		this_list.add(this_p);
		for(int i=this_p - 1; i >= 0; i--){
			if(is_row){
				if(this.link.get_state(i, this.y) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}else{
				if(this.link.get_state(this.x, i) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}
		}	
		
		if(is_row){
			for(int i= this_p + 1; i < this.link.ROW; i++){
				if(this.link.get_state(i, this.y) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}
		}else{
			for(int i= this_p + 1; i < this.link.COL; i++){
				if(this.link.get_state(this.x, i) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}
		}
		
		
		last_list.add(last_p);
		for(int i=last_p - 1; i >= 0; i--){
			if(is_row){
				if(this.link.get_state(i, Picture.last_col) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}else{
				if(this.link.get_state(Picture.last_row, i) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}
		}		
		if(is_row){
			for(int i= last_p + 1; i < this.link.ROW; i++){
				if(this.link.get_state(i, Picture.last_col) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}
		}else{
			for(int i= last_p + 1; i < this.link.COL; i++){
				if(this.link.get_state(Picture.last_row, i) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}
		}
		this.sort(this_list);
		this.sort(last_list);

		
		if(this_list.get(this_list.size() - 1) < last_list.get(0) || this_list.get(0) > last_list.get(last_list.size()-1)){
			has_link = false;
			return has_link;
		}
		if(this_list.get(0) == 0 && last_list.get(0) == 0){
			has_link = true;
			return has_link;
		}
		if(is_row){
			if(this_list.get(this_list.size()-1) == link.ROW-1 && last_list.get(last_list.size()-1) == link.ROW-1){
				has_link = true;
				return has_link;
			}
		}else{
			if(this_list.get(this_list.size()-1) == link.COL-1 && last_list.get(last_list.size()-1) == link.COL-1){
				has_link = true;
				return has_link;
			}
		}
		
		int index_start = 0;
		int index_stop = this_list.size() - 1;
		
		while(index_start < this_list.size() && this_list.get(index_start) < last_list.get(0)){
			index_start++;
		}
		while(index_stop >=0 && this_list.get(index_stop) > last_list.get(last_list.size()-1)){
			index_stop --;
		}
			
		for(int i=index_start; i<=index_stop; i++){
			if(is_row){
				if(this.y < Picture.last_col){
					int col;
					for(col = this.y +1; col < Picture.last_col; col++){
						if(this.link.get_state(this_list.get(i), col) != -1)	break;
					}
					if(col == Picture.last_col){
						has_link = true;
						return true;
					}
				}else if(this.y > Picture.last_col){
					int col;
					for(col = Picture.last_col +1; col < this.y; col++){
						if(this.link.get_state(this_list.get(i), col) != -1)	break;
					}
					if(col == this.y){
						has_link = true;
						return true;
					}
				}
			}else{
				if(this.x < Picture.last_row){
					int row;
					for(row = this.x +1; row < Picture.last_row; row++){
						if(this.link.get_state(row, this_list.get(i)) != -1)	break;
					}
					if(row == Picture.last_row){
						has_link = true;
						return true;
					}
				}else if(this.x > Picture.last_row){
					int row;
					for(row = Picture.last_row +1; row < this.x; row++){
						if(this.link.get_state(row, this_list.get(i)) != -1)	break;
					}
					if(row == this.x){
						has_link = true;
						return true;
					}
				}
			}
		}
		
		return has_link;
		
	}
	
	/**
	 * ��private boolean link(boolean is_row, int this_p, int last_p)��������
	 * ��list�ڵ����ݽ�����������
	 * @param list
	 */
	private void sort(ArrayList<Integer> list){
		for(int i=0; i<list.size()-1; i++){
			for(int j = i+1; j<list.size(); j ++){
				if(list.get(i) > list.get(j)){
					int tmp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, tmp);
				}
			}
		}
	}
	
}
