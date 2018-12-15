package Server;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;

public class fsConsoleParser {


    final fsCLIParameters.GET get = new fsCLIParameters.GET();
    final fsCLIParameters.POST post = new fsCLIParameters.POST();
    final fsCLIParameters.Help help = new fsCLIParameters.Help();
    private static fsConsoleParser demo;


    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {

            demo = new fsConsoleParser();
            demo.handleInputArgs(args);


    }

    void handleInputArgs(String args[]) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {


        JCommander jCommander = new JCommander().newBuilder().addCommand("get", get).addCommand("post", post)
                .addCommand("help", help).build();
        jCommander.setProgramName("httpfs");

        try {
            jCommander.parse(args);

        } catch (ParameterException exception) {
            System.out.println(exception.getMessage());
            showUsage(jCommander);
        }

        demo.run(args, jCommander);
    }

    void showUsage(JCommander jCommander) {
        jCommander.usage();
        System.exit(0);
    }

    void run(String[] args, JCommander jCommander) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        System.out.println("Running ...");

        if (jCommander.getParsedCommand().equals("get")) {
           // HttpClient connection = new HttpClient("get", get.getURL(), 5050, get.getVerbose(), get.getHeaders(), null, get.getFilename());
            fsManager connection = new fsManager ("get", get.getFile(), get.getVerbose(), get.getPort(), get.getDirectory(), " ");
        }
        if (jCommander.getParsedCommand().equals("post")) {
           // HttpClient connection = new HttpClient("post", post.getURL(), 5050, post.getVerbose(), post.getHeaders(), post.getData(), post.getFilename());
           fsManager connection = new fsManager ("post", post.getFile(), post.getVerbose(), post.getPort(), post.getDirectory(), post.getBody());
        }

        if (jCommander.getParsedCommand().equals("help")) {
            System.out.println(jCommander.getCommandDescription("help"));
            System.out.println("Usage:\n" +
                    "    httpfs command [arguments]\n" +
                    "The commands are:\n" +
                    "    get     executes a HTTP GET file request and prints the response.\n" +
                    "    post    executes a HTTP POST file request and prints the response.\n" +
                    "    help    prints this screen. \n" +
                    "Use \"httpc help [command]\" for more information about a command.");


        }


    }
}