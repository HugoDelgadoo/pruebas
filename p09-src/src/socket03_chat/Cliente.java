package socket03_chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente extends JFrame {
    private JTextField campoTexto;
    private JLabel etiquetaMensaje;
    private JButton botonEnviar;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Cliente() {
        setTitle("Cliente Chat");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        campoTexto = new JTextField(20);
        etiquetaMensaje = new JLabel("Mensajes recibidos aparecerán aquí");
        botonEnviar = new JButton("Enviar");

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(campoTexto);
        panelSuperior.add(botonEnviar);

        add(panelSuperior, BorderLayout.NORTH);
        add(etiquetaMensaje, BorderLayout.CENTER);

        botonEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new MensajeListener()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje() {
        String mensaje = campoTexto.getText();
        out.println(mensaje);
        campoTexto.setText("");
    }

    private class MensajeListener implements Runnable {
        @Override
        public void run() {
            try {
                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    final String mensajeFinal = mensaje;
                    SwingUtilities.invokeLater(() -> etiquetaMensaje.setText(mensajeFinal));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cliente().setVisible(true));
    }
}
