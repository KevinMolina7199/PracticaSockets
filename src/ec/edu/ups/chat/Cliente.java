/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.chat;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author kevin
 */
//Runable para poder ejecutar hilos 
public class Cliente implements Runnable {

    private int puerto;
    private String mensaje;

    public Cliente(int puerto, String mensaje) {
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    public Cliente() {
    }

    @Override
    public void run() {
        //HOST PARA EL SERVIDOR
        final String HOST = "localhost";

        //PUERTO DEL SERVIDOR 
        DataOutputStream salida;//Almacena datos de manera binaria
        while (true) {//Para que siempre este ejecutandose
            try {
                Socket socket = new Socket(HOST, puerto);//Permite la comunicación entre dos dispositivos a través de una red
                salida = new DataOutputStream(socket.getOutputStream());//obtener un flujo de salida asociado con un objeto Socket
                salida.writeUTF(mensaje);
                break;

            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }
                
                JOptionPane.showMessageDialog(null, "Error: Volviendo a intentar...");
            }
        }
    }

}
