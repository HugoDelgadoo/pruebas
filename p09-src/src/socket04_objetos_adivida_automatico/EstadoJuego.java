/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket04_objetos_adivida_automatico;

import java.io.Serializable;

public class EstadoJuego implements Serializable {

    private String nombre;
    private int numeroMin;
    private int numeroMax;
    private int numeroSecreto;
    private int numeroIntentado;
    private int intentos;    
    private boolean acertado;

    public EstadoJuego( String nombre, int numeroMin, int numeroMax, int numeroSecreto, int intentos) {
        this.nombre = nombre;
        this.numeroMin = numeroMin;
        this.numeroMax = numeroMax;
        this.numeroSecreto = numeroSecreto;
        this.numeroIntentado = numeroMax+1;
        this.intentos = intentos;
        this.acertado=false;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroMin() {
        return numeroMin;
    }

    public void setNumeroMin(int numeroMin) {
        this.numeroMin = numeroMin;
    }

    public int getNumeroMax() {
        return numeroMax;
    }

    public void setNumeroMax(int numeroMax) {
        this.numeroMax = numeroMax;
    }

    public int getNumeroSecreto() {
        return numeroSecreto;
    }

    public void setNumeroSecreto(int numeroSecreto) {
        this.numeroSecreto = numeroSecreto;
    }
    
    public int getNumeroIntentado() {
        return numeroIntentado;
    }
    
    public void setNumeroIntentado(int numeroIntentado) {
        this.numeroIntentado = numeroIntentado;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }
    
    public boolean esAcertado() {
        return acertado;
    }
    
    public void setAcertado(boolean acertado) {
        this.acertado = acertado;
    }

    @Override
    public String toString() {
        return "EstadoJuego{" + "nombre=" + nombre + ", numeroMin=" + numeroMin + ", numeroMax=" + numeroMax + ", numeroSecreto=" + numeroSecreto + ", numeroIntentado=" + numeroIntentado + ", intentos=" + intentos + ", acertado=" + acertado + '}';
    }
    
}
