package __ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import __database.__backend.Message_node;
import __database.__ui.Entries_display_ui;
import __database.__ui.Entries_ui;

public class Entries_scan_ui extends Entries_ui{
	private boolean isAdd;
	public Entries_scan_ui(int rank,Message_node m,
			JDialog f,Entries_display_ui g,boolean isAdd){
		super(rank,m,f,g);
		this.isAdd = isAdd;
		changeShape();
	}
	public void changeShape(){
		JPanel subP; JTextField textField;
		int count = panel.getComponentCount();
		for(int i=1;i<count;i++){
			subP = (JPanel)panel.getComponent(i);
			textField = (JTextField)subP.getComponent(1);
			textField.setText("");
			textField.setEditable(true);
		}
		JPanel topPanel = (JPanel)panel.getComponent(0);
		topPanel.removeAll();
		topPanel.add(getSaveButton());
	}
	public JLabel getSaveButton(){
		final JLabel save = new JLabel(isAdd?"Save":"OK",JLabel.CENTER);
		setColor(save);
		save.setPreferredSize(new Dimension(75,20));
		save.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(checkFull(isAdd))saveGeted();
			}
			public void mouseEntered(MouseEvent e){
				save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e){
				save.setCursor(Cursor.getDefaultCursor());
			}
		});
		return save;
	}
}
