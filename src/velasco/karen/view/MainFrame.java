/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velasco.karen.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import velasco.karen.controller.SuperBurbuja;

/**
 *
 * @author karen
 */
public class MainFrame extends JFrame{
    private JLabel lblHilos;
    private JTextField txtHilos;
    private JLabel lblLongitud;
    private JTextField txtLongitud;
    private JLabel lblInstrucciones;
    private SuperBurbuja burbuja;
    private JLabel lblLista;
    private JTextField txtLista;
    private JButton btnEnviar;
    private String longitud;
    private String tamanio;
    private String hilos;
    private DataErrorDlg dlgData;
    private EmptyDataErrorDlg dlgEmpty;
    private ProgressBarPnl progressBar;
    private JLabel lblTareaTerminada;
    private long tiempoEjecucion;
    private JButton btnReset;
    private JButton btnAceptar;
    
    public MainFrame(){
        super("Super burbujas");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new FlowLayout());
        super.setSize(350, 400);
        Font fuente = new Font("Dialog", Font.BOLD, 16);
        
        lblTareaTerminada = new JLabel();
        
        dlgData = new DataErrorDlg(this);
        dlgEmpty = new EmptyDataErrorDlg(this);
        
        lblInstrucciones = new JLabel("Super burbuja");
        
        lblLongitud = new JLabel("Rango de los numeros: [0,");
        txtLongitud = new JTextField("");
        lblLongitud.setFont(fuente);
        txtLongitud.setFont(fuente);
        txtLongitud.setPreferredSize(new Dimension(60,40));
        JPanel pnlLongitud = new JPanel();
        pnlLongitud.setLayout(new FlowLayout());
        pnlLongitud.add(lblLongitud);
        pnlLongitud.add(txtLongitud);
        
        //tamanio de la lista
        lblLista = new JLabel("Ingrese el tamaño de la lista: ");
        txtLista = new JTextField("");
        txtLista.setPreferredSize(new Dimension(80,40));
        JPanel pnlLista = new JPanel();
        pnlLista.setLayout(new FlowLayout());
        pnlLista.add(lblLista);
        pnlLista.add(txtLista);
        
        lblHilos = new JLabel("Cantidad de hilos: ");
        lblHilos.setFont(fuente);
        txtHilos = new JTextField("");
        txtHilos.setPreferredSize(new Dimension(120,40));
        txtHilos.setFont(fuente);
        JPanel pnlHilos = new JPanel();
        pnlHilos.setLayout(new FlowLayout());
        pnlHilos.add(lblHilos);
        pnlHilos.add(txtHilos);
        
        progressBar = new ProgressBarPnl();
        
        btnAceptar = new JButton("Aceptar");
        
        btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ejecutar todo o mostrar un DLGerror
				 longitud = txtLongitud.getText().trim();
				 tamanio = txtLista.getText().trim();
				 hilos = txtHilos.getText().trim();
				 
				 if(longitud==null || tamanio==null || hilos==null) {
					 dlgEmpty.setVisible(true);
				 }else if(Integer.valueOf(longitud)<Integer.valueOf(hilos)){//lim sup
						 dlgData.setVisible(true);
				 }else {
					 Integer limSuperior = Integer.valueOf(longitud);
					 Integer longitudLista = Integer.valueOf(tamanio);
					 Integer hilosFinal = Integer.valueOf(hilos);
					 
					 SuperBurbuja burbuja = new SuperBurbuja(-1, hilosFinal, longitudLista, limSuperior, MainFrame.this);
					 
					 try {
						 MainFrame.this.add(progressBar);
						 MainFrame.this.progressBar.setVisible(true);
					} catch (Exception e2) {
						// TODO: handle exception
					}
					 
					 btnEnviar.setEnabled(false);
					 btnReset.setEnabled(true);
					 
					 MainFrame.this.revalidate();
					 MainFrame.this.repaint();
					 
					 burbuja.start();
				 }
				 
			}
		});
        
        btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtLongitud.setText("");
				txtHilos.setText("");
				txtLista.setText("");
				btnEnviar.setEnabled(true);
				progressBar.setVisible(false);
				lblTareaTerminada.setVisible(false);
				btnReset.setVisible(false);
				MainFrame.this.revalidate();
				MainFrame.this.repaint();
			}
		});
        
        super.add(lblInstrucciones);
        super.add(pnlLongitud);
        super.add(pnlLista);
        super.add(pnlHilos);
        super.add(btnEnviar);
        super.add(lblTareaTerminada);
        super.add(btnReset);
        super.setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainFrame();
    }

    public JTextField getTxtHilos() {
        return txtHilos;
    }

    public void setTxtHilos(JTextField txtHilos) {
        this.txtHilos = txtHilos;
    }

	public JLabel getLblTareaTerminada() {
		return lblTareaTerminada;
	}

	public void setLblTareaTerminada() {
		this.lblTareaTerminada.setText("Tarea finalizada. Duracion total: "+this.getTiempoEjecucion()/(60*1000)+" minutos.");
		
		lblTareaTerminada.setVisible(true);
		
		progressBar.setVisible(false);
	    
		MainFrame.this.add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				txtLongitud.setText("");
				txtHilos.setText("");
				txtLista.setText("");
				lblTareaTerminada.setVisible(false);
				btnEnviar.setEnabled(true);
				btnAceptar.setVisible(false);
			}
		});
		
		btnAceptar.setVisible(true);
		
		super.revalidate();
		super.repaint();
	}

	public long getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(long tiempoInicio, long tiempoFinal) {
		this.tiempoEjecucion = tiempoFinal-tiempoInicio;//miliseconds 
	}
    
}
