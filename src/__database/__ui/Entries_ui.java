package __database.__ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import __backend.__entries.Patinet;
import __database.__backend.File_IO;
import __database.__backend.Message_node;
import __tool.Pair;
import __tool.RegexMatch;
import __ui.Main_ui;

public class Entries_ui {
	protected JPanel panel;
	public static int MAIN_WEIGHT = 370;
	public static int MAIN_HEIGHT = 250;
	public static Color BACK_GROUND = Color.darkGray;
	public static Color FONT_COLOR = Color.WHITE;
	protected JDialog father;
	protected Entries_display_ui grand;
	protected Message_node node;
	protected int rank;
	public Entries_ui(int rank,Message_node m,
			JDialog f,Entries_display_ui g){
		father = f; grand = g; node = m; this.rank = rank;
		panel = new JPanel(new GridLayout(m.size()+1,1,0,-5));
		Border titleBorder = BorderFactory.createTitledBorder
				(null,Main_ui.smallTitles.elementAt(rank)
						,TitledBorder.DEFAULT_JUSTIFICATION
						,TitledBorder.DEFAULT_POSITION,null,FONT_COLOR);
        panel.setBorder(titleBorder);
        panel.add(getButtonPanel(75,20));
        for(int i=0;i<m.size();i++){
        	panel.add(getBlock(Main_ui.titleFonts.elementAt(rank).elementAt(i).first
        			,m.elementAt(i)),0,i+1);
        }
        panel.setBackground(BACK_GROUND);
	}
	public JPanel getBlock(String p,Object obj){
		JPanel res = new JPanel();
		res.setBackground(BACK_GROUND);
		res.setLayout(new FlowLayout());
		JLabel lable = new JLabel(p);
		lable.setPreferredSize(new Dimension(130,20));
		JTextField textField = new JTextField();
		textField.setBackground(BACK_GROUND);
		textField.setPreferredSize(new Dimension(200,20));
		textField.setText(obj!=null?obj.toString():"");
		if(obj == null)
			textField.setText("");
		else if(p.equals("Sex"))
			textField.setText(obj.equals(Patinet.MALE)?
					Entries_display_ui.MALE_SIGN:Entries_display_ui.FEMALE_SIGN);
		textField.setEditable(false);
		setColor(lable); setColor(textField);
		res.add(lable,0,0);
		res.add(textField,0,1);
		return res;
	}
	public JPanel getButtonPanel(int w,int h){
		JLabel save,edit,delete;
		save = new JLabel("Save");
		save.addMouseListener(getButtonListener(1,save,node));
		setColor(save);
		save.setPreferredSize(new Dimension(w,h));
		edit = new JLabel("Edit");
		edit.addMouseListener(getButtonListener(0,edit,node));
		setColor(edit);
		edit.setPreferredSize(new Dimension(w,h));
		delete = new JLabel("Delete");
		delete.addMouseListener(getButtonListener(2,delete,node));
		setColor(delete);
		delete.setPreferredSize(new Dimension(w,h));
		JPanel res = new JPanel(new FlowLayout(FlowLayout.RIGHT,3,0));
		res.setBackground(BACK_GROUND);
		save.setPreferredSize(new Dimension(40,15));
		edit.setPreferredSize(new Dimension(40,15));
		delete.setPreferredSize(new Dimension(40,15));
		res.add(edit);
		res.add(save);
		res.add(delete);
		return res;
	}
	public MouseListener getButtonListener(final int sign,
			final JLabel button,final Message_node m){
		return new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(sign == 0){
					JPanel subP; JTextField textField;
					int count = panel.getComponentCount();
					for(int i=2;i<count;i++){
						subP = (JPanel)panel.getComponent(i);
						textField = (JTextField)subP.getComponent(1);
						textField.setEditable(true);
					}
				}else if(sign == 1){
					if(checkFull(true))saveGeted();
				}else if(sign == 2){
					grand.setRes(new Pair<Message_node,Message_node>(null,m));
					father.dispose();
				}
			}
			public void mouseEntered(MouseEvent e){
				button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}  
			public void mouseExited(MouseEvent e){
				button.setCursor(Cursor.getDefaultCursor());
			}  
		};
	}
	public void saveGeted(){
		StringBuffer message = new StringBuffer();
		String item;
		JPanel subP; JTextField textField;
		int count = panel.getComponentCount();
		for(int i=1;i<count;i++){
			subP = (JPanel)panel.getComponent(i);
			textField = (JTextField)subP.getComponent(1);
			item = textField.getText();
			if(item.equals(""))item = File_IO.NULL;
			else if(Main_ui.titleFonts.elementAt(rank).elementAt(i-1).first.equals("Sex"))
				item = Integer.toString
					(item.equals(Entries_display_ui.MALE_SIGN)?Patinet.MALE:Patinet.FEMALE);
			message.append(item+File_IO.split);
		}
		//System.out.println(message.toString());
		Message_node s = File_IO.getNode(message.toString()
				,Main_ui.dataBases.elementAt(rank).getSign().getOrd());
		grand.setRes(new Pair<Message_node,Message_node>(s,null));
		father.dispose();
	}
	public boolean checkFull(boolean isAdd){
		boolean res = true,isMatch,isNull,canNull;
		JPanel subP; JTextField textField;
		int count = panel.getComponentCount();
		for(int i=1;i<count;i++){
			subP = (JPanel)panel.getComponent(i);
			textField = (JTextField)subP.getComponent(1);
			isMatch = RegexMatch.absoluteMatch(textField.getText()
					,Main_ui.regexs.elementAt(rank).elementAt(i-1).second);
			isNull = textField.getText().equals("");
			canNull = Main_ui.regexs.elementAt(rank).elementAt(i-1).first;
			if(!(isMatch||(isNull&&!canNull)||(isNull&&!isAdd))){
				res = false;
				textField.setBorder(BorderFactory
						.createLineBorder(Color.RED,1));
			}else textField.setBorder(BorderFactory
					.createLineBorder(Color.WHITE,1));
		}
		return res;
	}
	public void setColor(Container p){
		p.setForeground(FONT_COLOR);
		p.setBackground(BACK_GROUND);
	}
	public JPanel getMainPanel(){
		return panel;
	}
}
