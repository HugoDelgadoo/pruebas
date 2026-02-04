/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socket02_hilos_JFrame_dando_hora_siempre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente extends JFrame {
    private JLabel timeLabel;
    private JButton getTimeButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Cliente() {
        setTitle("Cliente de Tiempo");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        timeLabel = new JLabel("Hora actual: ");
        getTimeButton = new JButton("Obtener Hora");

        add(timeLabel);
        add(getTimeButton);

        getTimeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getTimeFromServer();
            }
        });

        try {
            socket = new Socket("localhost", 5000);
            // Flujos de entrada y salida
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLocationRelativeTo(null);        
    }

    private void getTimeFromServer() {
        try {
            out.println("GET_TIME");  // envía el texto "GET_TIME" al servidor
            String response = in.readLine();
            timeLabel.setText("Hora actual: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }
}

