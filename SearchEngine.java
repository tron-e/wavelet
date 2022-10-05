import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.

    ArrayList<String> listString = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", listString.toString());
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    listString.add(parameters[1]);
                    return String.format("Added String by %s! List now contains %d strings", parameters[1], listString.size());
                }
            }
            else if(url.getPath().contains("/search")){
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")){
                    ArrayList<String> toReturn = new ArrayList<>();
                    for(String s: listString){
                        if (s.contains(parameters[1])) {
                            toReturn.add(parameters[1]);
                        }
                    }
                    return String.format("%d strings found matching query. Stings are by %s", toReturn.size(), toReturn.toString());
                }

            }

             

            return "404 Not Found!";
        }
    }

}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}




