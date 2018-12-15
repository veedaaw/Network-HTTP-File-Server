package Server;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer {
    private static ServerSocket server = null;

    private static String response;
    private static int port;
    private static String directory;
    private static boolean verbose;
    private static File _file;
    private static HttpStatus status;
    private static boolean isAccessible;
    private static String finalResponse;
    private static String body;


    public static void main(String[] args) throws Exception

    {

            init();
    }


    public static void init() throws Exception
    {
        port = 5050;
        server  = new ServerSocket(port);
        directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";
        isAccessible = true;
        status= HttpStatus.OK;
        response =" ";
        finalResponse =" ";
        body =" ";

        while(true) {
            System.out.println("Waiting for client request");
            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();

            //********receiving the request values*************//

            String[] receivedMsg = new String[]{};
            receivedMsg = message.split(" ");
            ArrayList<String> msg = new ArrayList<>(Arrays.asList(receivedMsg));

            if(msg.contains("get")){

            //*******decoding received msg from client************//
            if ((msg.get(msg.indexOf("-p") + 1) != null))

            {
                port = Integer.valueOf(msg.get(msg.indexOf("-p") + 1));
            } else {
                port = 5050;
            }

            System.out.println(port);

            if (msg.contains("-v")) {
                verbose = Boolean.valueOf(msg.get(msg.indexOf("-p") + 1));

            }

            //System.out.println(verbose);


            //if user wants to change the default directory
            if (msg.contains("-d")) {
                if ((msg.get(msg.indexOf("-d") + 1)) != null)

                {
                    directory = msg.get(msg.indexOf("-d") + 1);

                    System.out.println(directory);

                    //if accessing the directory is forbidden
                    if (directory.startsWith("/Users/veedaa/Desktop/java/HttpProtocol/src/main"))

                    {
                        directory = msg.get(msg.indexOf("-d") + 1);
                        status = HttpStatus.OK;


                        isAccessible = true;

                    } else

                    //otherwise we will let the user to set it as he wants.
                    {
                        status = HttpStatus.FORBIDDEN;


                        isAccessible = false;
                        response = "Access is forbidden";
                        directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";
                    }
                }

                //if user doesn't want to change the default directory

                else {
                    directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";
                    status = HttpStatus.OK;

                    isAccessible = true;

                }

                System.out.println(directory);
            }

            String file = msg.get(msg.indexOf("/") + 1);

            // if user want to see the list of all available files in a directory

            if (file.equals("/") && isAccessible) {

                try

                {
                    //files in our directory

                    _file = new File(directory).getAbsoluteFile();

                    String result = " ";

                    for (File f : _file.listFiles()) {
                        result += f.getAbsoluteFile().getName() + "\n";
                    }
                    response = result;
                    status = HttpStatus.OK;


                } catch (Exception e)

                {

                }
            }
            //if user specify that he wants to read inside a specific file

            else if (!file.equals("/") && isAccessible)

            {

                System.out.println("+++++ " + directory);
                _file = new File(directory).getAbsoluteFile();
                File _desiredFile = new File(msg.get(msg.indexOf("/") + 1));


                for (File f : _file.listFiles())

                {
                    if (f.getAbsoluteFile().getName().equals(_desiredFile.getAbsoluteFile().getName()))

                    {

                        try {

                            FileReader respFile = new FileReader(directory + "/" + _desiredFile.getAbsoluteFile().getName());
                            BufferedReader buff = new BufferedReader(respFile);
                            String fileLine;
                            String _result = " ";
                            while ((fileLine = buff.readLine()) != null) {
                                if (fileLine.length() != 0)
                                    _result += (fileLine);
                            }
                            response = _result;
                            status = HttpStatus.OK;
                            buff.close();
                            respFile.close();
                        } catch (IOException e) {
                            throw new Exception("This file is unable to be opened");
                        }


                        status = HttpStatus.OK;
                        break;

                    } else {

                        response = "File not found";
                        status = HttpStatus.NFOUND;


                    }


                }


            } else

            {
                status = HttpStatus.FORBIDDEN;

            }



                finalResponse = HttpResponseBuilder(response, status);

                System.out.println("Message Received: " + finalResponse);
                //create ObjectOutputStream object
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //write object to Socket
                oos.writeObject(finalResponse);
                //close resources
                ois.close();
                oos.close();
                socket.close();
                //terminate the server if client sends exit request
                if (message.equalsIgnoreCase("exit")) break;

        }



       ///*****************************POST**************************

       else if(msg.contains("post")){

                //*******decoding received msg from client************//
                if (msg.contains("-p"))

                {
                    port = Integer.valueOf(msg.get(msg.indexOf("-p") + 1));
                } else {
                    port = 5050;
                }

                System.out.println(port);

                if (msg.contains("-v")) {
                    verbose = Boolean.valueOf(msg.get(msg.indexOf("-p") + 1));

                }

                System.out.println(verbose);


                //if user wants to change the default directory
                if (msg.contains("-d")) {
                    if ((msg.get(msg.indexOf("-d") + 1)) != null)

                    {
                        directory = msg.get(msg.indexOf("-d") + 1);

                        System.out.println(directory);

                        //if accessing the directory is forbidden
                        if (directory.startsWith("/Users/veedaa/Desktop/java/HttpProtocol/src/main"))

                        {
                            directory = msg.get(msg.indexOf("-d") + 1);
                            status = HttpStatus.OK;
                            isAccessible = true;

                        } else

                        //otherwise we will let the user to set it as he wants.
                        {
                            status = HttpStatus.FORBIDDEN;

                            isAccessible = false;
                            response = "****Access is forbidden";
                            directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";
                        }
                    }

                    //if user doesn't want to change the default directory

                    else {

                        directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";
                        status = HttpStatus.OK;
                        isAccessible = true;

                    }

                    System.out.println("------"+directory);
                }

                String file = msg.get(msg.indexOf("/") + 1);

                // if user want to see the list of all available files in a directory

                if (file.equals("/")) {

                  status = HttpStatus.BADREQUEST;
                  response = "File could not be found";
                }
                //if user specify that he wants to read inside a specific file

                else if (!file.equals("/") && isAccessible)

                {

                    _file = new File(directory).getAbsoluteFile();
                    File _desiredFile = new File(msg.get(msg.indexOf("/") + 1));

                    File[] our_files = _file.listFiles();
                    ArrayList<String> filenames = new ArrayList<>();
                    String fileLine;
                    String _result = " ";
                    for(File fil: our_files )
                    {
                        filenames.add(fil.getAbsoluteFile().getName());
                    }

                        if (filenames.contains(_desiredFile.getAbsoluteFile().getName()) && !isRestricted(_desiredFile))

                        {

                           /* try {

                                FileReader respFile = new FileReader(directory + "/" + _desiredFile.getAbsoluteFile().getName());
                                BufferedReader buff = new BufferedReader(respFile);

                                while ((fileLine = buff.readLine()) != null) {
                                    if (fileLine.length() != 0)
                                        _result += (fileLine);
                                }
                                response = _result;
                                status = HttpStatus.OK;
                                buff.close();
                                respFile.close();
                            } catch (IOException e) {
                                throw new Exception("This file is unable to be opened");
                            }*/

                            PrintWriter writer = new PrintWriter(directory+"/"+_desiredFile.getAbsoluteFile().getName(), "UTF-8");

                            body = msg.get(msg.indexOf("-b") + 1);
                            writer.println(body);
                            writer.close();

                            response = "File is modified to: " + body;
                            status = HttpStatus.OK;


                          //  status = HttpStatus.OK;
                           // response = _result;

                        }

                        else if (!filenames.contains(_desiredFile.getAbsoluteFile().getName()) && !isRestricted(_desiredFile))

                        {

                            File newFile = new File(directory+"/"+_desiredFile.getAbsoluteFile().getName());
                           // File parentDir = newFile.getParentFile();

                            newFile.getParentFile().mkdirs();
                            newFile.createNewFile();

                            PrintWriter writer = new PrintWriter(directory+"/"+_desiredFile.getAbsoluteFile().getName(), "UTF-8");

                            body = msg.get(msg.indexOf("-b") + 1);
                            writer.println(body);
                            writer.close();

                            response = "File created: " + body;
                            status = HttpStatus.OK;


                        }

                        else if (!filenames.contains(_desiredFile.getAbsoluteFile().getName()) && isRestricted(_desiredFile))

                        {

                            response = "You cannot create a file with this format";
                            status = HttpStatus.FORBIDDEN;


                        }

                        else if (filenames.contains(_desiredFile.getAbsoluteFile().getName()) && isRestricted(_desiredFile))
                        {
                            response = " Modifying this file is restricted";
                            status = HttpStatus.FORBIDDEN;


                        }






                }

                else if (!file.equals("/") && !isAccessible)

                {
                    status = HttpStatus.FORBIDDEN;

                }



                finalResponse = HttpResponseBuilder(response, status);

                System.out.println("Message Received: " + finalResponse);
                //create ObjectOutputStream object
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //write object to Socket
                oos.writeObject(finalResponse);
                //close resources
                ois.close();
                oos.close();
                socket.close();
                //terminate the server if client sends exit request
                if (message.equalsIgnoreCase("exit")) break;

            }


        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }


    public static String HttpResponseBuilder(String respBody, HttpStatus hs){
        String response = "";
        if (hs.equals(HttpStatus.OK)) {

            response += hs.toString() + "\r\n";
            response += "Date:" + getTimeStamp() + "\r\n";
            response += "Server: localhost\r\n";
            response += "Content-Type: text/html\r\n";
            response += "Connection: Closed\r\n\r\n";

        } else if (hs.equals(HttpStatus.NFOUND)) {

            response += hs.toString() + "\r\n";
            response += "Date:" + getTimeStamp() + "\r\n";
            response += "Server: localhost\r\n";
            response += "\r\n";
        } else if (hs.equals(HttpStatus.NMODIFIED)) {

            response += hs.toString() + "\r\n";
            response += "Date:" + getTimeStamp() + "\r\n";
            response += "Server: localhost\r\n";
            response += "\r\n";
        }
        else if(hs.equals(HttpStatus.FORBIDDEN)){
            response += hs.toString() + "\r\n";
            response += "Date:" + getTimeStamp() + "\r\n";
            response += "Server: localhost\r\n";
            response += "\r\n";
        }

        else if(hs.equals(HttpStatus.BADREQUEST)){
            response += hs.toString() + "\r\n";
            response += "Date:" + getTimeStamp() + "\r\n";
            response += "Server: localhost\r\n";
            response += "\r\n";
        }

        if(respBody!=null)
            response += respBody + "\r\n";

        return response;
    }
    private static String getTimeStamp()

    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private static Boolean isRestricted(File file)
    {

       Boolean result;

            Pattern p1 = Pattern.compile(".class");
            Pattern p2 = Pattern.compile(".java");
            Pattern p3 = Pattern.compile(".jar");
            Pattern p5 = Pattern.compile(".project");
            Pattern p4 = Pattern.compile(".git");
            Pattern p6 = Pattern.compile(".xml");


                Matcher m1 = p1.matcher(file.getPath());
                Matcher m2 = p2.matcher(file.getPath());
                Matcher m3 = p3.matcher(file.getPath());
                Matcher m4 = p4.matcher(file.getPath());
                Matcher m5 = p5.matcher(file.getPath());
                Matcher m6 = p6.matcher(file.getPath());

                if(m1.find()||m2.find()||m3.find()||m4.find()||m5.find()||m6.find())
                    result = true;
                else
                   result = false;

        return result;

    }


}
