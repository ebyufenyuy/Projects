//Ellison Yufenyuy
//New card deck with more implementations 


class Card { //Class name
    int cardNumber;
    String suit;

    Card(int v, String s) {
        cardNumber = v;
        suit = "" + s.toUpperCase().charAt(0);
        if (v < 2 || v > 14) {
            cardNumber = 0;
            suit = "ERROR";
        }
        if (suit.equals("S") || suit.equals("H") || suit.equals("C") || suit.equals("D")) {

        } else {
            cardNumber = 0;
            suit = "ERROR";
        }
    }

    String print() {
        String suitName = new String();
        if (cardNumber == 11) {
            suitName = "J";
        }
        if (cardNumber == 12) {
            suitName = "Q";
        }
        if (cardNumber == 13) {
            suitName = "K";
        }
        if (cardNumber == 14) {
            suitName = "A";
        }
        if (cardNumber == 2) {
            suitName = "2";
        }
        if (cardNumber == 3) {
            suitName = "3";
        }
        if (cardNumber == 4) {
            suitName = "4";
        }
        if (cardNumber == 5) {
            suitName = "5";
        }
        if (cardNumber == 6) {
            suitName = "6";
        }
        if (cardNumber == 7) {
            suitName = "7";
        }
        if (cardNumber == 8) {
            suitName = "8";
        }
        if (cardNumber == 9) {
            suitName = "9";
        }
        if (cardNumber == 10) {
            suitName = "10";
        }
        return suitName + suit;
    }
}

class Deck {
    Card[] allMyCards = new Card[52];
    int cardsDealt = 0;

    Deck() {
        int value = 2;
        int cardIndex = 0;
        while (value <= 14) {
            allMyCards[cardIndex] = new Card(value, "S");
            value++;
            cardIndex++;
        }
        
        value = 2;
        while (value <= 14) {
            allMyCards[cardIndex] = new Card(value, "H"); //Hearts
            value++;
            cardIndex++;
        }
        
        value = 2;
        while (value <= 14) {
            allMyCards[cardIndex] = new Card(value, "C"); //Clubs
            value++;
            cardIndex++;
        }
        
        value = 2;
        while (value <= 14) {
            allMyCards[cardIndex] = new Card(value, "D"); //Diamonds
            value++;
            cardIndex++;
        }
    }

    String print() {
        String myDeck = new String();
        int cardIndex = 0;
        while (cardIndex <= 51) {
            myDeck += allMyCards[cardIndex].print();
            myDeck = myDeck + " ";
            cardIndex++;
        }
        return myDeck;
    }

    Card deal() {
        if (cardsDealt < 52) {
            Card card = allMyCards[cardsDealt];
            cardsDealt++;
            return card;
        } else {
            return null; //All the cards have been dealt
        }
    }

    void shuffle() {
        for (int i = 0; i < 500; i++) {
            int cardIndex1 = (int) (Math.random() * 52);
            int cardIndex2 = (int) (Math.random() * 52);

            
            Card temp = allMyCards[cardIndex1]; //Swap the cards at index1 and index2
            allMyCards[cardIndex1] = allMyCards[cardIndex2];
            allMyCards[cardIndex2] = temp;
        }
    }
}

class NewCardDeck {
    public static void main(String[] args) {
        Deck myDeck = new Deck();
        myDeck.shuffle();
        Card card = myDeck.deal();
        if (card != null) {
            System.out.println(card.print()); //Print the card
        } else {
            System.out.println("Every card has been dealt.");
        }
    }
}
