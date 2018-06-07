/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velasco.karen.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import velasco.karen.test1.Burbuja;

/**
 *
 * @author karen
 */
public class MainFrame extends JFrame{
    private JLabel lblHilos;
    private JTextField txtHilos;
    private JButton btnLeerArchivo;
    private JLabel lblInstrucciones;
    private Burbuja burbuja;
    private Integer hilos;
    
    public MainFrame(){
        super("Super burbujas");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setSize(300, 300);
        Font fuente = new Font("Dialog", Font.BOLD, 18);
        
        lblInstrucciones = new JLabel("Super burbuja");
        
        lblHilos = new JLabel("Cantidad de hilos: ");
        txtHilos = new JTextField("");
        txtHilos.setPreferredSize(new Dimension(60,40));
        
        JPanel pnlHilos = new JPanel();
        pnlHilos.setLayout(new FlowLayout());
        pnlHilos.add(lblHilos);
        pnlHilos.add(txtHilos);
        
        btnLeerArchivo = new JButton("Ordenar");
        btnLeerArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Lee el archivo 
                hilos = Integer.valueOf(txtHilos.getText());
                burbuja = new Burbuja(hilos);
            }
        });
        
        super.add(lblInstrucciones, BorderLayout.NORTH);
        super.add(pnlHilos, BorderLayout.CENTER);
        super.add(btnLeerArchivo, BorderLayout.SOUTH);
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
    
}
