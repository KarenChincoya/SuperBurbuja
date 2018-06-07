/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velasco.karen.test1;

import java.util.ArrayList;

import archivosutilidades.ArchivoUtilidades;

/**
 *
 * @author karen
 */
public class BurbujaHilo extends Thread{
    private Integer[] lista;
    private Integer limInf;
    private Integer limSup;
    private Integer[] oLista;
    private Integer pInicial;
    private Integer pFinal;
    private boolean tareaFinalizada;
    private Integer id;
    private Integer tamanio;
    private Integer subIndice;
    
    public BurbujaHilo(Integer id,Integer[] oLista, Integer limInf, Integer limSup){
        this.limInf = limInf;
        this.limSup = limSup;
        this.id = id;
        this.oLista = oLista;
        this.tareaFinalizada = false;
        this.subIndice = 0;
    }
    
    public void run(){
        Integer aux;
        
        System.out.println("Hilo "+id+" -------------------------------------------> numero de elementos: "+tamanio);
        
        for(int i=0; i<tamanio;i++){
            for(int j=0; j<((tamanio)-1);j++){
                if(lista[j]>lista[j+1]){
                    aux = lista[j];
                    lista[j]=lista[j+1];
                    lista[j+1]= aux;
                }
            }
        }
        
        System.out.println("Hilo "+id+" is out of the bubble");
        /*
        String[] resultado = new String[this.getTamanio()];
        for(int i=0; i<this.getTamanio(); i++) {
        	resultado[i] = String.valueOf(lista[i]);
        }
        
        String dir = System.getProperty("user.dir");
        ArchivoUtilidades.escribirLineas(dir + "/src/resultado"+this.getId()+".txt", resultado);
        */
        Integer k=0;
        for(int i=this.pInicial; i<=this.pFinal;i++){
            oLista[i] = lista[k];
            k++;
        }
        this.tareaFinalizada = true;
        
        try {
        	this.tareaFinalizada=true;
            System.out.println("Hilo "+id+" is dead*************************************************************************");
            Thread.interrupted();
        } catch (Exception e) {
        }
    }

    public boolean isTareaFinalizada() {
        return tareaFinalizada;
    }

    public void setTareaFinalizada(boolean tareaFinalizada) {
        this.tareaFinalizada = tareaFinalizada;
    }

    public Integer[] getLista() {
        return lista;
    }

    public void setLista(Integer tamanio ) {
        this.lista = new Integer[tamanio];
    }
    

    public Integer getLimInf() {
        return limInf;
    }

    public void setLimInf(Integer limInf) {
        this.limInf = limInf;
    }

    public Integer getLimSup() {
        return limSup;
    }

    public void setLimSup(Integer limSup) {
        this.limSup = limSup;
    }
    
    public String getRango(){
        return "["+this.getLimInf()+","+this.getLimSup()+"]";
    }
    
    public boolean dentroDelRango(Integer n){
        if(n>=this.limInf && n<=this.limSup){
            return true;
        }else{
            return false;
        }
    }
    
    public void addNumber(Integer valor){
        this.lista[subIndice] = valor;
        this.subIndice++;
    }

    public Integer getpInicial() {
        return pInicial;
    }

    public void setpInicial(Integer pInicial) {
        this.pInicial = pInicial;
    }

    public Integer getpFinal() {
        return pFinal;
    }

    public void setpFinal(Integer pFinal) {
        this.pFinal = pFinal;
    }
    
    public String getPosiciones(){
        return "["+this.pInicial+" , "+this.pFinal+"]";
    }

    public Integer getTamanio() {
        return tamanio;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }
    
}
