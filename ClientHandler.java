import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlersArray=new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientUserName;


    public ClientHandler(Socket sk) {
        try {
            this.socket=sk;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName=reader.readLine();
            clientHandlersArray.add(this);
            broadcastMessage("SERVER: "+ clientUserName+ " has entered tha chat!");

        }catch(IOException e) {
            closeEverything(socket, reader, writer);
        }
    }




    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()) {
            try {
                messageFromClient=reader.readLine();
                broadcastMessage(messageFromClient);

            }catch (IOException e) {
                closeEverything(socket, reader, writer);
                break;
            }
        }

    }

    public void broadcastMessage(String messageToSend) {
        for(ClientHandler clientHandler : clientHandlersArray) {
            try {
                if(!clientHandler.clientUserName.equals(clientUserName)) {
                    clientHandler.writer.write(messageToSend);
                    clientHandler.writer.newLine();
                    clientHandler.writer.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
                break;
            }
        }
    }

    public void removeClientHandler() {
        clientHandlersArray.remove(this);
        broadcastMessage("SERVER: "+clientUserName+ " has left the chat");
    }
    public void closeEverything(Socket socket,BufferedReader read, BufferedWriter write) {
        removeClientHandler();
        try {
            if(read!=null) {
                reader.close();
            }
            if(write!=null) {
                write.close();
            }
            if(socket!=null) {
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
