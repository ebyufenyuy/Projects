//Ellison Yufenyuy
//Program that plays game Acey Ducey with a dealer

import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Acey {

    private static final double RISK_TAKE = 0.02; //Defining acceptable risk

    public static void main(String[] args) throws IOException {
        String IpAddress = args[0];
        int IpPort = Integer.parseInt(args[1]);
        Socket socket = new Socket(IpAddress, IpPort);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        boolean inGame = true;

        //Initialize an array to track the number of each card rank left in the deck
        int[] numberOfCards = new int[13]; //Represents the cards from 2 to Ace (2-10, J, Q, K, A)

        while (inGame) {
            String dealerCommand = read(dis); //Read the command from the dealer
            String[] splitUp = dealerCommand.split(":");
            String reader = splitUp[0];

            if (reader.equals("login")) {
                write("ebyufenyuy:ejjj", dos); //Respond with GitHub ID and avatar name
            } else if (reader.equals("play")) {
                //Parse play command
                int potSize = Integer.parseInt(splitUp[1]);
                int availableChips = Integer.parseInt(splitUp[2]);
                String card1 = splitUp[3];
                String card2 = splitUp[4];

                updateCardCount(numberOfCards, card1); //Update the card count based on the cards received
                updateCardCount(numberOfCards, card2);

                int bet = calcBet(numberOfCards, potSize, availableChips, card1, card2);

                //Choose "low," "mid," or "high" depending
                String bettingDecision="";
                if (card1.substring(0,1).equals(card2.substring(0,1))) {
                    if(card1.charAt(0)<5) {
                        bettingDecision = "low:1"; //Bet low if cards are equal
                    }
                    else {
                        bettingDecision = "high:1"; //Bet high if cards have different values
                    }
                } 
                else {
                    bettingDecision = "mid:" + bet;
                } 

                System.out.println(dealerCommand);
                System.out.println(bettingDecision);
                write(bettingDecision, dos);
            } else if (reader.equals("status") || reader.equals("done")) {
                System.out.println(dealerCommand); //Print the status or done message

                if (reader.equals("done")) {
                    inGame = false; //Exit the game when done is received
                }
            }
        }
       
        socket.close(); //Close the socket
    }

    private static void write(String s, DataOutputStream dos) throws IOException {
        dos.writeUTF(s);
        dos.flush();
    }

    private static String read(DataInputStream dis) throws IOException {
        return dis.readUTF();
    }

    private static void updateCardCount(int[] numberOfCards, String card) {
        String cardRank = card.substring(0, 1); // Extract the rank of the card (2-9, T, J, Q, K, A)
        String[] cardRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};

        for (int i = 0; i < cardRanks.length; i++) {
            if (cardRank.equals(cardRanks[i])) {
                numberOfCards[i]--; //Decrement the count for that card rank
                break;
            }
        }
    }

    private static int calcBet(int[] numberOfCards, int availableChips, int potSize, String card1, String card2) {
        double winningProbability = calcWinningProb(numberOfCards);
        //Ensure not to bet more than the available chips
        int maxBet = Math.min(availableChips, (int) (availableChips * RISK_TAKE));

        if (winningProbability >= 0.7) {
            //High winning probability, bet a percentage of the pot
            int bet = (int) (potSize * 0.7); //Bet 70% of the pot
            
            return Math.min(bet, maxBet);
        } else if (winningProbability >= 0.5) {
            int bet = (int) (potSize * 0.5); //Moderate winning probability, bet percentage of the pot
          
            return Math.min(bet, maxBet);
        } else {
            int bet = (int) (availableChips * 0.1); //Low winning probability, make a small bet
            return Math.min(bet, maxBet);
        }
    }

    private static double calcWinningProb(int[] numberOfCards) {
        int totalHighValueCards = numberOfCards[10] + numberOfCards[11] + numberOfCards[12]; //T, J, Q, K, A
        int totalLowValueCards = numberOfCards[0] + numberOfCards[1] + numberOfCards[2] + numberOfCards[3] + numberOfCards[4] + numberOfCards[5] + numberOfCards[6] + numberOfCards[7]; // 2-9

        double highCardPercentage = (double) totalHighValueCards / (totalHighValueCards + totalLowValueCards);

        if (highCardPercentage >= 0.6) {
            return 0.7; //High probability when many high-value cards are left
        } else if (highCardPercentage >= 0.4) {
            return 0.5; //Moderate probability
        } else {
            return 0.3; //Low probability when low-value cards are of most
        }
    }
}

