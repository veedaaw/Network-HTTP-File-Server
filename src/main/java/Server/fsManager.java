
package Server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class fsManager{
    String message = null;
    String verb = null;

        public fsManager(String method, String file, boolean verbose, int port, String directory, String body) throws IOException, ClassNotFoundException {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;

            if (verbose) {verb = "true";} else {verb ="false";}

             message = method + " " + "/"+ " "+ file + " " +"-v"+" "+ verb + " " +"-p"+" "+ String.valueOf(port)+ " "+ "-d"+ " "+ directory+
            " "+ "-b"+ " "+ body;


            //establish socket connection to server
            socket = new Socket(host.getHostName(), port);

            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");

            oos.writeObject(message);
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Message: " + message);
            //close resources
            ois.close();
            oos.close();
        }

       /* Runnable task1 = ()->
        {

            setClient();
        };

        Runnable task2 = ()->
        {
            try {
                send(port, method);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Thread tread1 = new Thread(task1);
        Thread tread2 = new Thread(task2);

        tread1.start();
        tread1.join();

        tread2.start();
        */


}

