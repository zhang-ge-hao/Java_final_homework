package __ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import __database.__ui.Entries_display_ui;

public class Welcome_ui{
	static public int PATINET_PANEL_HEIGHT = 60;
	static private Welcome_ui single = null;
	private JPanel welcomePanel;
	private Welcome_ui(){
		ImageIcon icon = new ImageIcon("img\\wel.png");
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),
			    icon.getIconHeight(), Image.SCALE_DEFAULT));
		JLabel pic = new JLabel();
		pic.setHorizontalAlignment(0);
		pic.setIcon(icon);
		welcomePanel = new JPanel();
		welcomePanel.setPreferredSize(new Dimension
				(Main_ui.MAIN_WEIGHT,Entries_display_ui.MAIN_HEIGHT*2));
		welcomePanel.add(pic);
	}
	static public JPanel getMainPanel(){
		if(single == null)single = new Welcome_ui();
		return single.welcomePanel;
	}
}
