import java.io.*;
import java.net.*;
import java.util.Date;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PrimeServer extends Application{
	
		  public void start(Stage primaryStage) throws Exception{
		    // Text area for displaying contents
		    TextArea ta = new TextArea();

		    // Create a scene and place it in the stage
		    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		    primaryStage.setTitle("Prime Server"); // Set the stage title
		    primaryStage.setScene(scene); // Place the scene in the stage
		    primaryStage.show(); // Display the stage
		    
		    new Thread( () -> {
		      try {
		        try (// Create a server socket
				ServerSocket serverSocket = new ServerSocket(8000)) {
					Platform.runLater(() ->
					  ta.appendText("Server started at " + new Date() + '\n'));
  
					// Listen for a connection request
					Socket socket = serverSocket.accept();
  
					// Create data input and output streams
					DataInputStream inputFromClient = new DataInputStream(
					  socket.getInputStream());
					DataOutputStream outputToClient = new DataOutputStream(
					  socket.getOutputStream());
  
					while (true) {
					  // Receive radius from the client
					  int number = inputFromClient.readInt();
					  boolean flag = false;
					  
					  // Compute prime
					  for (int i = 2; i <= number / 2; ++i) {
					    // condition for non-prime number
					    if (number % i == 0) {
					      flag = true;
					      break;
					    }
					  }
  
					 
					// Send area back to the client
					  String response;
					  if(!flag) {
						  response = "a prime number.";
					  }else
						  response = "not a prime number.";
						  
						  
					  outputToClient.writeChars(response);
  
					  Platform.runLater(() -> {
					    ta.appendText("Integer received from client: " 
					      + number + '\n');
					    ta.appendText("The interger is: " + response + '\n'); 
					  });
					}
				}
		      }
		   
		      catch(IOException ex) {
		        ex.printStackTrace();
		      }
		    }).start();
		  }

		  /**
		   * The main method is only needed for the IDE with limited
		   * JavaFX support. Not needed for running from the command line.
		   */
		  public static void main(String[] args) throws IOException{
		    launch(args);
		  }

		}

