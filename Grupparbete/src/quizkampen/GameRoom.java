package quizkampen;

import java.io.*;
import java.net.Socket;

public class GameRoom implements Runnable {
    private Socket client1;
    private Socket client2;

    GameRoom(Socket client1, Socket client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    @Override
    public void run() {
        System.out.println("Server room has been created");
        Protocol protocol1 = new Protocol();
        Protocol protocol2 = new Protocol();
        Player player1 = new Player(client1, protocol1);
        Player player2 = new Player(client2, protocol2);
        Thread t1 = new Thread(player1);
        Thread t2 = new Thread(player2);
        t1.start();
        t2.start();

        /*try (
                ObjectInputStream client1Input = new ObjectInputStream(client1.getInputStream());
                ObjectInputStream client2Input = new ObjectInputStream(client2.getInputStream())
        ) {
            Session c1, c2;
            while ((c1 = (Session)client1Input.readObject()) != null && (c2 = (Session)client2Input.readObject()) != null) {
                if (c1.getState() == ProtocolState.SERVERENDROUND && c2.getState() == ProtocolState.SERVERENDROUND) {
                    System.out.println("Game room handles next round here");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        /*try (
                BufferedReader client1Input = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                BufferedReader client2Input = new BufferedReader(new InputStreamReader(client2.getInputStream()))
        ) {
            String c1, c2;
            while (!((c1 = client1Input.readLine()).isEmpty()) && (!((c2 = client2Input.readLine()).isEmpty()))) {
                if (c1.equalsIgnoreCase("END") && c2.equalsIgnoreCase("END")) {
                    System.out.println("Game room handles next round here");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
