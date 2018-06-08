package velasco.karen.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DataErrorDlg extends JDialog{
	
	private JLabel lblError;
	private JButton btnOk;
	
	public DataErrorDlg(JFrame parent) {
		super(parent, "Error", true);
        super.setSize(700,200);
//        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setLayout(new FlowLayout());
        
        Font fuente = new Font("Dialog", Font.BOLD, 16);
        
        lblError = new JLabel();
        
        lblError.setText("El límite superior del rango debe ser mayor que la cantidad de hilos.");
        lblError.setFont(fuente);
        lblError.setPreferredSize(new Dimension(650,40));
        
        btnOk = new JButton("Aceptar");
        btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataErrorDlg.this.setVisible(false);
			}
		});
        
        btnOk.setPreferredSize(new Dimension(120,40));
        
        lblError.setFont(fuente);
        btnOk.setFont(fuente);
        
      
        
        super.add(lblError);
    	super.add(btnOk);
	}

	
}
