/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author kevin
 */
public class Servidor extends Observable implements Runnable {//Usa el patron de Diseño que notifica cuando se hace un cambio

    private int puerto;
    private ArrayList<Socket> clientesConectados;//Lista para los clientes conectados

    public Servidor(int puerto) {
        this.puerto = puerto;
        this.clientesConectados = new ArrayList<>();
    }

    public Servidor() {
    }

    @Override
    public void run() {
        ServerSocket servidor = null;//crea un servidor que escucha conexiones entrantes desde clientes a través de una red
        Socket socket = null;
        DataInputStream entrada;
        DataOutputStream salida;

        try {
            servidor = new ServerSocket(puerto);
            System.out.println("SERVIDOR INICIADO");

            while (true) {
                socket = servidor.accept();
                System.out.println("SE CONECTO UN CLIENTE");
                entrada = new DataInputStream(socket.getInputStream());

                String mensajeL = entrada.readUTF();
                System.out.println(mensajeL);

                // Agregar el socket del cliente a la lista de clientes conectados
                clientesConectados.add(socket);

                this.setChanged();
                this.notifyObservers(mensajeL);
                this.clearChanged();

                socket.close();//Se cierra para liberar recursos

                // Eliminar el socket del cliente de la lista de clientes conectados
                clientesConectados.remove(socket);
                
                this.setChanged();
                this.clearChanged();

            }
        } catch (Exception e) {
            System.out.println(e);
            // Manejar excepciones y desconexiones aquí
        }
    }
}
