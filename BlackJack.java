import java.util.*;
import javax.swing.JOptionPane;


/*THIS  CODE  WAS MY OWN WORK , IT WAS  WRITTEN  WITHOUT  CONSULTING  ANYSOURCES  OUTSIDE  OF THOSE  APPROVED  BY THE  INSTRUCTOR. Kevin Tuyishime */

//possible suits
enum Suit{
    HEART, CLUB, DIAMOND, SPADE,;
}

//possible values and their numerical value
enum Value{
	 
	TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), 
	SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10),
	QUEEN(10), KING(10), ACE(11);

  private int numValue;
  
  //get numerical value of a card
	public int getNum(){
		return this.numValue;
  }
  //value constructor
  	private Value(int numValue){
    this.numValue =numValue;
  }

}

public class BlackJack {
	
	// Determining the value of the hand.
	public static int handValue(ArrayList<Card> sampleHand){
		int totalValue = 0;
		int aceCount = 0;
		for(Card oneCard : sampleHand){

			if (oneCard.getValue().getNum() ==11){
				aceCount+=1;
			}else{
				totalValue+=oneCard.getValue().getNum();
			}
		
		}
		//determing the value of aces to be 1 or 11
		for(int i=0; i<aceCount; i++){
			if (totalValue<=10){
				totalValue+=11;
			}else{
				totalValue+=1;
			}
		}
		return totalValue;
	}

	//define drawing a card in a deck
	public static void draw(ArrayList<Card> fromDeck, ArrayList<Card> toDeck ){
		// draws the first card in the deck and remove it from the deck
		toDeck.add(fromDeck.get(0));
		fromDeck.remove(0);
	}

    //build a Deck of 52 cards matching Suits with Values
    public static void buildDeck(ArrayList<Card> deck) {
			//iterate through the suits
			for(Suit cardSuit: Suit.values()){
				//iterate through the values
				for(Value cardValue: Value.values()){
					//add to the playing deck
					deck.add(new Card(cardSuit, cardValue));
				}
				
			}
			//shuffle the cards in the deck.
				Collections.shuffle(deck);
	}
	
	//start the game
    public static void initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand){	
		// reset both player and dealer hands after a game
		if ((handValue(dealerHand)!=0) || (handValue(playerHand)!=0)){
			playerHand.clear();
			dealerHand.clear();
		}
				// deal two cards to the player
				draw(deck, playerHand);
				draw(deck, playerHand);

				//two cards for the dealer
				draw(deck, dealerHand);
				draw(deck, dealerHand);

	}
	
	// draw one card
    public static void dealOne(ArrayList<Card> deck, ArrayList<Card> hand){
		//draw a signle card from the deck
		draw(deck, hand);
    }

	//determine when the deck is burst
    public static boolean checkBust(ArrayList<Card> hand){
		// initate isBurst to false.
		boolean isBurst = false;

		//check the value of the hand
		if(handValue(hand)>21){
			isBurst=true;
		}
		
		return isBurst;
    }
// dealer's turn
    public static boolean dealerTurn(ArrayList<Card> deck, ArrayList<Card> hand){
	
		boolean dealerBurst =false;
		//deal a card to the dealer if his deck is below 17, else will stand
		while(handValue(hand)<17){
			draw(deck, hand);
			}
			//check the deck value after drawing a card
			if(handValue(hand)>21){
				dealerBurst = true;
			}
		return dealerBurst;
	}
	
//check the winner
    public static int whoWins(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
		
			int win =0;
			// check if the hands are equal
			if(handValue(playerHand) == handValue(dealerHand)){

				//dealer wins
				win =2;
				//when dealer hand is greater than player and not burst
			}else if ((handValue(dealerHand)<=21) && (handValue(dealerHand)>handValue(playerHand))){
				//dealer wins
				win =2; 
			}
			//when player hand is greater than dealer and not burst
			else if((handValue(playerHand)<=21) && (handValue(dealerHand)<handValue(playerHand))){
					// player wins
				win =1;
			}
			return win;
	}
	
//display one card
    public static String displayCard(ArrayList<Card> hand){
		// arraylist to store one card to get value of card
		ArrayList<Card> oneCard = new ArrayList<Card>();

		//add card to the list
		oneCard.add(hand.get(1));

		//return card and value
		return "---------------\n " 
		+ hand.get(1)+"\n --------------- \nValued At: "
		+ handValue(oneCard)+"\n\n";
	}
	
// display the ahnd of the player and value of the deck
    public static String displayHand(ArrayList<Card> hand){

			String myHand = "--------------- \n";
		//prind individual card
		for(int i=0; i<hand.size(); i++){
			myHand+= hand.get(i) + "\n";
		}
		return myHand + 
		"--------------- \nValued At: "
		+handValue(hand) +"\n";
    }


    public static void main(String[] args) {

		int playerChoice, winner;
		ArrayList<Card> deck = new ArrayList<Card>();
		
		
		playerChoice = JOptionPane.showConfirmDialog(
			null, 
			"Ready to Play Blackjack?", 
			"Blackjack", 
			JOptionPane.OK_CANCEL_OPTION
		);

		if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
		    System.exit(0);
		
		Object[] options = {"Hit","Stand"};
		boolean isBusted;	// Player busts? 
		boolean dealerBusted;
		boolean isPlayerTurn;
		ArrayList<Card> playerHand = new ArrayList<>();
		ArrayList<Card> dealerHand = new ArrayList<>();
	
		do{ // Game loop
			buildDeck(deck);  // Initializes the deck for a new game
		    initialDeal(deck, playerHand, dealerHand);
		    isPlayerTurn=true;
		    isBusted=false;
		    dealerBusted=false;
		    
		    while(isPlayerTurn){

				// Shows the hand and prompts player to hit or stand
				playerChoice = JOptionPane.showOptionDialog(
					null,
					"Dealer shows: \n" + displayCard(dealerHand) + " Your hand is: \n" 
						+ displayHand(playerHand) + "\n What do you want to do?",
					"Hit or Stand",
				   JOptionPane.YES_NO_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,
				   options,
				   options[0]
				);

				if(playerChoice == JOptionPane.CLOSED_OPTION)
				    System.exit(0);
				
				else if(playerChoice == JOptionPane.YES_OPTION){
				    dealOne(deck, playerHand);
				    isBusted = checkBust(playerHand);
				    if(isBusted){
						// Case: Player Busts
						playerChoice = JOptionPane.showConfirmDialog(
							null,
							"Player has busted!", 
							"You lose", 
							JOptionPane.OK_CANCEL_OPTION
						);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
						
						isPlayerTurn=false;
				    }
				}
			    
				else{
				    isPlayerTurn=false;
				}
		    }

		    if(!isBusted){ // Continues if player hasn't busted
				dealerBusted = dealerTurn(deck, dealerHand);
				if(dealerBusted){ // Case: Dealer Busts
				    playerChoice = JOptionPane.showConfirmDialog(
				    	null, 
				    	"The dealer's hand: \n" +displayHand(dealerHand) + "\n Your hand: \n" 
				    		+ displayHand(playerHand) + "\nThe dealer busted.\n *** Congrautions! ***", 
				    	"You Win!!!", 
				    	JOptionPane.OK_CANCEL_OPTION
				    );		    

					if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						System.exit(0);
				}
			
			
				else{ //The Dealer did not bust.  The winner must be determined
				    winner = whoWins(playerHand, dealerHand);

				    if(winner == 1){ //Player Wins
						playerChoice = JOptionPane.showConfirmDialog(
							null, 
							"The dealer's hand: \n" +displayHand(dealerHand) + "\n Your hand: \n" 
								+ displayHand(playerHand) + "\n *** Congrautions! ***", 
							"You Win!!!", 
							JOptionPane.OK_CANCEL_OPTION
						);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }

				    else{ //Player Loses
						playerChoice = JOptionPane.showConfirmDialog(
							null, 
							"The dealer's hand: \n" +displayHand(dealerHand) + "\n Your hand: \n" 
								+ displayHand(playerHand) + "\n Better luck next time!", 
							"You lose!!!", 
							JOptionPane.OK_CANCEL_OPTION
						); 
					
						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }
				}
		    }
		}while(true);
    }
}


class Card {
	
	//each card has a suit and value
	private Suit suit;
	private Value value;

	//builds one card
	Card(Suit suit, Value value){
		this.suit = suit;
		this.value = value;
	}

	//get the card info
	public String toString(){
        return this.suit.toString() + "-" + this.value.toString();
	}

	//return the value of the card
	public Value getValue(){
        return this.value;
    }
}