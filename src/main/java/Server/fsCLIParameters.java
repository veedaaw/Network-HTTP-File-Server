package Server;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



    @Parameters(separators = ":") //space works too
    public class fsCLIParameters {

        @Parameters(commandDescription = "Get executes a HTTP GET request for a given URL.")
        public static class GET
        {
            @Parameter(names = "-v", description = "Prints debugging messages.")
            private Boolean verbose = false;

            @Parameter(names = "-p", description = "Specifies the port number that the server will listen and serve at. Default is 8080.")
            private Integer port = 5050;

            @Parameter(names = "-d", description = "Specifies the directory that the server will use to read/write requested files. Default is the current directory when launching the application.")
            private String directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";

            @Parameter(names = "-b",description = "file")
            private String body;

            @Parameter(description = "file", required = true)
            private String file;



            public Boolean getVerbose() {
                return verbose;
            }

            public Integer getPort() {
                return port;
            }

            public String getDirectory() {
                return directory;
            }

            public String getFile() {
                return file;
            }



        }


        @Parameters(commandDescription = "Get executes a HTTP GET request for a given URL.")
        public static class POST
        {
            @Parameter(names = "-v", description = "Prints debugging messages.")
            private Boolean verbose = false;

            @Parameter(names = "-p", description = "Specifies the port number that the server will listen and serve at. Default is 8080.")
            private Integer port = 8080;

            @Parameter(names = "-d", description = "Specifies the directory that the server will use to read/write requested files. Default is the current directory when launching the application.")
            private String directory = "/Users/veedaa/Desktop/java/HttpProtocol/src/main/java/Server";

            @Parameter(description = "file", required = true)
            private String file;


            @Parameter(names ="-b", description = "body of the request")
            private String body = " ";


            public Boolean getVerbose() {
                return verbose;
            }

            public Integer getPort() {
                return port;
            }

            public String getDirectory() {
                return directory;
            }

            public String getFile() {
                return file;
            }

            public String getBody() {
                return body;
            }
        }

        @Parameters(commandDescription = "file server manager.")
        public static class Help
        {


        }


    }
