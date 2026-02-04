package socket05_adivina_automativo_multithread_Client;

import java.io.Serializable;

public class EstadoJuego implements Serializable {

    private int numeroMin;
    private int numeroMax;
    private int numeroSecreto;
    private int numeroIntentado;
    private int intentos;    
    private boolean acertado;

    public EstadoJuego(int numeroMin, int numeroMax, int numeroSecreto, int intentos) {
        this.numeroMin = numeroMin;
        this.numeroMax = numeroMax;
        this.numeroSecreto = numeroSecreto;
        this.numeroIntentado = numeroMax+1;
        this.intentos = intentos;
        this.acertado=false;
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
        return "EstadoJuego{" + "numeroMin=" + numeroMin + ", numeroMax=" + numeroMax + ", numeroSecreto=" + numeroSecreto + ", numeroIntentado=" + numeroIntentado + ", intentos=" + intentos + ", acertado=" + acertado + '}';
    }


    
}
