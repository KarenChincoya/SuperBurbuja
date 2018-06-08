package velasco.karen.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarPnl extends JPanel{
	private JProgressBar progressBar;
	public ProgressBarPnl() {
		super.setLayout(new FlowLayout());
		//SuperBurbuja superBurbuja
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(300,60));
		progressBar.setValue(0);
		progressBar.setStringPainted(false);//text whitin the progressbar
		progressBar.setIndeterminate(true);
		
		super.add(progressBar);
	}
	
	/*public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(300,300);
		
		ProgressBarPnl pnlProgress = new ProgressBarPnl();
		
		frame.add(pnlProgress);
		frame.setVisible(true);
	}*/
	
}
