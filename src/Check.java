import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Check implements Runnable{
	private Link_GUI link;
	
	public Check(Link_GUI link){
		this.link = link;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!link.is_over){	
			boolean has_link = false;
			for(int i=0; i<link.ROW; i++){
				for(int j=0; j<link.COL; j++){
					if(link.get_state(i, j) != -1){
						for(int row=0; row<link.ROW; row++){
							for(int col=0; col<link.COL; col ++){
								if(link.get_state(row, col) != -1 && link.get_number(i, j) == link.get_number(row, col)){
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									has_link = this.has_link(true, i, j, row, col);
									if(!has_link){
										has_link = this.has_link(false, i, j, row, col);
									}
									if(has_link)	break;
								}
							}
							if(has_link)	break;
						}
						if(has_link)	break;
					}
				}
				if(has_link)	break;
			}
			
			if(!has_link && this.link.left_button > 2){
				JOptionPane.showMessageDialog(null, "找不到可以连的了，重新开始吧!");
				this.link.init();
				return;
			}
		}
	}
	
	private boolean has_link(boolean is_row, int ii, int jj, int x_row, int y_col){
		boolean has_link = false;
		ArrayList<Integer> this_list = new ArrayList<Integer>();
		ArrayList<Integer> last_list = new ArrayList<Integer>();
		int this_p;
		int last_p;
		if(is_row){
			this_p = ii;
			last_p = x_row;
		}else{
			this_p = jj;
			last_p = y_col;
		}
		
		this_list.add(this_p);
		for(int i=this_p - 1; i >= 0; i--){
			if(is_row){
				if(this.link.get_state(i, jj) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}else{
				if(this.link.get_state(ii, i) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}
		}	
		
		if(is_row){
			for(int i= this_p + 1; i < this.link.ROW; i++){
				if(this.link.get_state(i, jj) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}
		}else{
			for(int i= this_p + 1; i < this.link.COL; i++){
				if(this.link.get_state(ii, i) == -1){
					this_list.add(i);
				}else{
					break;
				}
			}
		}
		
		
		last_list.add(last_p);
		for(int i=last_p - 1; i >= 0; i--){
			if(is_row){
				if(this.link.get_state(i, y_col) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}else{
				if(this.link.get_state(x_row, i) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}
		}		
		if(is_row){
			for(int i= last_p + 1; i < this.link.ROW; i++){
				if(this.link.get_state(i, y_col) == -1){
					last_list.add(i);
				}else{
					break;
				}
			}
		}else{
			for(int i= last_p + 1; i < this.link.COL; i++){
				if(this.link.get_state(x_row, i) == -1){
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
				if(jj < y_col){
					int col;
					for(col = jj +1; col < y_col; col++){
						if(this.link.get_state(this_list.get(i), col) != -1)	break;
					}
					if(col == y_col){
						has_link = true;
						return true;
					}
				}else if(jj > y_col){
					int col;
					for(col = y_col +1; col < jj; col++){
						if(this.link.get_state(this_list.get(i), col) != -1)	break;
					}
					if(col == jj){
						has_link = true;
						return true;
					}
				}
			}else{
				if(ii < x_row){
					int row;
					for(row = ii +1; row < x_row; row++){
						if(this.link.get_state(row, this_list.get(i)) != -1)	break;
					}
					if(row == x_row){
						has_link = true;
						return true;
					}
				}else if(ii > x_row){
					int row;
					for(row = x_row +1; row < ii; row++){
						if(this.link.get_state(row, this_list.get(i)) != -1)	break;
					}
					if(row == ii){
						has_link = true;
						return true;
					}
				}
			}
		}
		
		return has_link;
		
	}
	
	
	/**
	 * 供private boolean link(boolean is_row, int this_p, int last_p)方法调用
	 * 对list内的数据进行升序排序
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
