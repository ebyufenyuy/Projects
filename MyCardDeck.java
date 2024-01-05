// Filename: FixCapitalization.java
// Purpose: This program simulates a deck of cards
// Name: EJ Yufenyuy (Ellison)
 
class Card { //Class name 
	int cardVal;
	String cardSuit;


	Card(int v, String s) { // Constructor to create a card 
		cardVal = v;
		cardSuit = "" + s.toUpperCase().charAt(0);
		if (v < 2 || v > 14) {
			cardVal = 0;
			cardSuit = "ERROR";
		}
		if (cardSuit.equals("S") || cardSuit.equals("H") || cardSuit.equals("C") || cardSuit.equals("D")) {

		} else {
			cardVal = 0;
			cardSuit = "ERROR";
		}
	}
	
	Card(String s) { //Constructor to create a card from a string
		s = s.toUpperCase();  
		if (s.charAt(0) == 'J') {  // Check first character to find out and see the card value
			cardVal = 11;
		}
		if (s.charAt(0) == 'A') {
			cardVal = 14;
		}
		if (s.charAt(0) == 'K') {
			cardVal = 13;
		}
		if (s.charAt(0) == 'Q') {
			cardVal = 12;
		}
		if (s.charAt(1) == '2') {
			cardVal = 2;
		}
		if (s.charAt(1) == '3') {
			cardVal = 3;
		}
		if (s.charAt(1) == '4') {
			cardVal = 4;
		}
		if (s.charAt(1) == '5') {
			cardVal = 5;
		}
		if (s.charAt(1) == '6') {
			cardVal = 6;
		}
		if (s.charAt(1) == '7') {
			cardVal = 7;
		}
		if (s.charAt(1) == '8') {
			cardVal = 8;
		}
		if (s.charAt(1) == '9') {
			cardVal = 9;
		}
		if (s.charAt(2) == '0') {
			cardVal = 10;
		}
		if (s.charAt(1) == '1') {   
			cardVal = 0;
			cardSuit = "ERROR";
		} else {
			cardSuit = "" + s.charAt(2);
		}
	}
	
	Card(String v, String s) {
		v = v.toUpperCase();
		if (v.equals("J")) {
			cardVal = 11;
		}
		if (v.equals("Q")) {
			cardVal = 12;
		}
		cardSuit = s;
	}
	
	Card() {
		cardVal = 0;
		cardSuit = "ERROR";
	}
	
	int valueNum() {
		return cardVal;
	}
	
	String cardSuit() {
		return cardSuit;
	}
		
	String print() {
		String cardSuitName = new String();
		if (cardVal == 11) {  
			cardSuitName = "J";
		}
		if (cardVal == 12) {
			cardSuitName = "Q";
		}
		if (cardVal == 13) {
			cardSuitName = "K";
		}
		if (cardVal == 14) {
			cardSuitName = "A";
		}
		if (cardVal == 2) {
			cardSuitName = "2";
		}
		if (cardVal == 3) {
			cardSuitName = "3";
		}a
		if (cardVal == 4) {
			cardSuitName = "4";
		}
		if (cardVal == 5) {
			cardSuitName = "5";
		}
		if (cardVal == 6) {
			cardSuitName = "6";
		}
		if (cardVal == 7) {
			cardSuitName = "7";
		}
		if (cardVal == 8) {
			cardSuitName = "8";
		}
		if (cardVal == 9) {
			cardSuitName = "9";
		}
		if (cardVal == 10) {
			cardSuitName = "10";
		}
 		return cardSuitName + cardSuit; //Return the concatenated card string
	}
}


class Deck {
	Card[] entireDeck = new Card[52];
	Deck() {
		int valueNumNum = 2;
		int indexNum = 0;
		while (valueNum <= 14) { //Cards created for each value and suit combination 
			entireDeck[indexNum] = new Card(valueNum, "S");
			valueNum++;
			indexNum++;
		}
		valueNum = 2;
		while (valueNum <= 14) {
			entireDeck[indexNum] = new Card(valueNum, "H");
			valueNum++;
			indexNumNum++;
		}
		valueNum = 2;
		while (valueNum <= 14) {
			entireDeck[indexNum] = new Card(valueNum, "C");
			valueNum++;
			indexNum++;
		}
		valueNum = 2;
		while (valueNum <= 14) {
			entireDeck[indexNum] = new Card(valueNum, "D");
			valueNum++;
			indexNum++;
		}
	}
	
	String print() {
		String myDeck = new String();  
		int indexNum = 0;
		while (indexNum <= 51) {   
			myDeck += entireDeck[indexNum].print();  
			myDeck = myDeck + " ";  
			indexNum++;
		}
		return myDeck;
	}
}


class MyCardDeck {
	public static void main(String[] args) {
		if (args.length >= 2) {
			Card newCards = new Card(args[0], args[1]);
			System.out.println(newCards.print());
			System.exit(0);
		}
		if (args.length == 1) {
			Card myCard = new Card(args[0]);
			System.out.println(myCard.print());
			System.exit(0);
		}
		if (args.length == 0) { // Create a deck and print it
			Deck myDeck = new Deck();
			System.out.println(myDeck.print());
			System.exit(0);
		}
	}
}


