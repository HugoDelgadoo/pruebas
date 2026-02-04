package comun;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
// Puedes usar java.awt.Point para guardar X e Y, o crear tu propia clase simple.
import java.awt.Point; 

public class EstadoJuego implements Serializable {
    // ID único para saber qué versión del objeto es (buena práctica en Serializable)
    private static final long serialVersionUID = 1L;

    // Guardamos la posición de cada jugador por su ID (o nombre)
    // Clave: Nombre del jugador ("Jugador 1"), Valor: Coordenada (x, y)
    private Map<String, Point> posiciones;
    
    // Un mensaje extra por si quieres notificar ganadores, etc.
    private String mensajeSistema;

    // Constructor
    public EstadoJuego() {
        this.posiciones = new HashMap<>();
    }

    // Métodos para actualizar la posición de un jugador concreto
    public void actualizarPosicion(String nombreJugador, int x, int y) {
        this.posiciones.put(nombreJugador, new Point(x, y));
    }

    // Método para obtener todas las posiciones (para pintarlas)
    public Map<String, Point> getPosiciones() {
        return posiciones;
    }
    
    // Getters y Setters para mensajeSistema...
}