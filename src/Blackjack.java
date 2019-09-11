
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tvtuttle
 */
public class Blackjack {
    // fields
    int[] decisions;
    boolean insurance, chatter;
    CardList deck;
    CardList discard;
    CardList playerHand;
    CardList dealerHand;
    CardList splitHand1;
    CardList splitHand2;
    // for now, assume bet size $10
    public int play(int numGames, int[] decisionArray, boolean ins, boolean chat, int numDecks){
        decisions = decisionArray;
        insurance = ins;
        chatter = chat;
        int moneyBet = 10;
        int profit = 0;
        buildDeck(numDecks);
        for (int i = 0; i < numGames; i ++){
            profit += playGame(moneyBet);
        }
        return profit;
    }
    
    private int playGame(int bet){
        int out = 0;
        say("New hand.");
        playerHand = new CardList();
        dealerHand = new CardList();
        splitHand1 = new CardList();
        splitHand2 = new CardList();
        // player draws 2 cards, dealer draws 2, only one dealer card "shown"
        playerHand.add(drawFromDeck());
        playerHand.add(drawFromDeck());
        dealerHand.add(drawFromDeck());
        dealerHand.add(drawFromDeck());
        say("Player draws " + playerHand.get(0).getValue());
        say("Player draws " + playerHand.get(1).getValue());
        say("Dealer shows " + dealerHand.get(0).getValue());
        // check for player blackjack
        if (playerHand.sum() == 21){
            // check for dealer blackjack
            if (dealerHand.sum() == 21){
                say("Double blackjack.");
                clearHands();
                return out;
            }
            else {
                clearHands();
                say("Player blackjack.");
                out += (int) (1.5*bet);
                return out;
            }
        }
        // check if first dealer card is an ace to ask for insurance
        if (dealerHand.get(0).getValue() == 11){
            say("Insurance offered");
            if (insurance){
                say("Insurance accepted");
                if (dealerHand.sum() == 21){
                    say("Dealer blackjack. Insurance returned.");
                    clearHands();
                    return out;
                }
                else {
                    say("No blackjack. Insurance lost.");
                    out -= (int) (0.5*bet);
                }
            }
        }
        //check for dealer blackjack
        if (dealerHand.sum() == 21){
            say("Dealer blackjack.");
            clearHands();
            out -= bet;
            return out;
        }
        // all pre-play decisions have been exhausted
        // now the decision phase begins
        // first, identify if its a split hand (and whether to split)
        if (playerHand.get(0).getValue() == playerHand.get(1).getValue()){
            // lookup in decisions: 0 = no split, 1 = split
            if (checkSplit() == 1){
                say("Player splits.");
                splitHand1.add(playerHand.remove(0));
                splitHand2.add(playerHand.remove(0));
                // play splithand1
                // with splithands, the checkdecision method can fail, since initial hand sums can be below 4
                 if (splitHand1.sum() < 4){
                    // if 1 is, then both are
                    splitHand1.add(drawFromDeck());
                    say("Player 1 draws " + splitHand1.get(splitHand1.size()-1).getValue());
                }
                while ((checkDecision(splitHand1) == 0 || checkDecision(splitHand1) == 2) && splitHand1.sum() < 22){
                    splitHand1.add(drawFromDeck());
                    say("Player 1 draws " + splitHand1.get(splitHand1.size()-1).getValue());
                    if ((splitHand1.sum() > 21) && splitHand1.hasSoftAce()){
                        splitHand1.softToHard();
                    }
                }
                // play splithand2
                if (splitHand2.sum() < 4){
                    splitHand2.add(drawFromDeck());
                    say("Player 2 draws " + splitHand2.get(splitHand2.size()-1).getValue());
                }
                while ((checkDecision(splitHand2) == 0 || checkDecision(splitHand2) == 2) && splitHand2.sum() < 22){
                    splitHand2.add(drawFromDeck());
                    say("Player 2 draws " + splitHand2.get(splitHand2.size()-1).getValue());
                    if ((splitHand2.sum() > 21) && splitHand2.hasSoftAce()){
                        splitHand2.softToHard();
                    }
                }
                // check for busts in both; if so, dealer doesn't need to play
                if (splitHand1.sum() > 21 && splitHand2.sum() > 21){
                    say("Both player hands bust.");
                    clearHands();
                    out -= 2 * bet;
                    return out;
                }
                // play dealer hand
                say("Dealer shows " + dealerHand.get(1).getValue());
                while(dealerHand.sum() < 17){
                    dealerHand.add(drawFromDeck());
                    say("Dealer draws " + dealerHand.get(dealerHand.size()-1).getValue());
                    if ((dealerHand.sum() > 21) && dealerHand.hasSoftAce()){
                        dealerHand.softToHard();
                    }
                }
                // settlement tree; if dealer busts and 1 splithand busts, player makes a net profit of 0
                if (splitHand1.sum() > 21){
                    say("Player Hand 1 busts.");
                    if (dealerHand.sum() > 21){
                        say("Dealer busts. Player hand 2 wins.");
                        clearHands();
                        return out; // player loses 1 bet, wins 1 bet, net of 0
                    }
                    else if (dealerHand.sum() > splitHand2.sum()){
                        say("Dealer beats Player Hand 2.");
                        clearHands();
                        out -= 2*bet;
                        return out;
                    }
                    else if (dealerHand.sum() < splitHand2.sum()){
                        say("Player Hand 2 beats Dealer.");
                        clearHands();
                        return out;
                    }
                    else if (dealerHand.sum() == splitHand2.sum()){
                        say("Player Hand 2 pushes Dealer.");
                        clearHands();
                        out -= bet;
                        return out;
                    }
                }
                else if (splitHand2.sum() > 21){
                    say("Player Hand 2 busts.");
                    if (dealerHand.sum() > 21){
                        say("Dealer busts. Player Hand 1 wins.");
                        clearHands();
                        return out; // player loses 1 bet, wins 1 bet, net of 0
                    }
                    else if (dealerHand.sum() > splitHand1.sum()){
                        say("Dealer beats Player Hand 2.");
                        clearHands();
                        out -= 2*bet;
                        return out;
                    }
                    else if (dealerHand.sum() < splitHand1.sum()){
                        say("Player Hand 2 beats Dealer.");
                        clearHands();
                        return out;
                    }
                    else if (dealerHand.sum() == splitHand1.sum()){
                        say("Player Hand 2 pushes Dealer.");
                        clearHands();
                        out -= bet;
                        return out;
                    }
                }
                else {
                    // neither player hand busts
                    if (dealerHand.sum() > 21){
                        say("Dealer busts. Both player hands win.");
                        clearHands();
                        out += 2*bet;
                        return out;
                    }
                    else {
                        if (dealerHand.sum() > splitHand1.sum()){
                            say("Dealer beats Player hand 1.");
                            out -= bet;
                        }
                        else if (dealerHand.sum() < splitHand1.sum()){
                            say("Player Hand 1 beats Dealer.");
                            out += bet;
                        }
                        if (dealerHand.sum() > splitHand2.sum()){
                            say("Dealer beats Player Hand 2.");
                            out -= bet;
                        }
                        else if (dealerHand.sum() < splitHand2.sum()){
                            say("Player Hand 2 beats Dealer.");
                            out += bet;
                        }
                        say("Unlisted Hands pushed.");
                        clearHands();
                        return out;
                    }
                }
            }
        }
        // otherwise check for double
        if (checkDecision(playerHand) == 2){
            // if (legal) double down, hit once and stand
            say("Double down.");
            playerHand.add(drawFromDeck());
            say("Player draws " + playerHand.get(playerHand.size()-1).getValue());
            if (playerHand.sum() > 21 && playerHand.hasSoftAce()){
                playerHand.softToHard();
            }
            // check for bust
            if (playerHand.sum() > 21){
                say("Player busts.");
                clearHands();
                out -= 2 * bet;
                return out;
            }
            // then, dealer plays normally
            say("Dealer shows " + dealerHand.get(1).getValue());
            while (dealerHand.sum() < 17){
                dealerHand.add(drawFromDeck());
                say("Dealer draws " + dealerHand.get(dealerHand.size()-1).getValue());
                if (dealerHand.sum() > 21 && dealerHand.hasSoftAce()){
                    dealerHand.softToHard();
                }
            }
            //check for bust, otherwise compare
            if (dealerHand.sum() > 21){
                say("Dealer busts.");
                clearHands();
                out += 2*bet;
                return out;
            }
            else if (dealerHand.sum() > playerHand.sum()){
                say("Dealer beats player.");
                clearHands();
                out -= 2*bet;
                return out;
            }
            else if (dealerHand.sum() < playerHand.sum()){
                say("Player beats Dealer.");
                clearHands();
                out += 2*bet;
                return out;
            }
            else if (dealerHand.sum() == playerHand.sum()){
                say("Player pushes Dealer.");
                clearHands();
                return out;
            }
        }
        //otherwise, play normally
        while ((checkDecision(playerHand) == 0 || checkDecision(playerHand) == 2) && playerHand.sum()<22){
            playerHand.add(drawFromDeck());
            say("Player draws " + playerHand.get(playerHand.size()-1).getValue());
                if (playerHand.sum() > 21 && playerHand.hasSoftAce()){
                    playerHand.softToHard();
                }
        }
        if (playerHand.sum() > 21){
            say("Player busts.");
            clearHands();
            out -= bet;
            return out;
        }
        say("Dealer shows " + dealerHand.get(1).getValue());
        while(dealerHand.sum() < 17){
            dealerHand.add(drawFromDeck());
            say("Dealer draws " + dealerHand.get(dealerHand.size()-1).getValue());
            if (dealerHand.sum() > 21 && dealerHand.hasSoftAce()){
                dealerHand.softToHard();
            }
        }
        if (dealerHand.sum() > 21){
            say("Dealer busts.");
            clearHands();
            out += bet;
            return out;
        }
        else if (dealerHand.sum() > playerHand.sum()){
            say("Dealer beats Player.");
            clearHands();
            out -= bet;
            return out;
        }
        else if (dealerHand.sum() < playerHand.sum()){
            say("Player beats Dealer.");
            clearHands();
            out += bet;
            return out;
        }
        say("Player pushes Dealer.");
        clearHands();
        return out;
    }
    
    private void buildDeck(int n){
        deck = new CardList();
        discard = new CardList();
        for (int k = 0; k < n; k ++){
        for (int i = 0; i < 4; i ++){
            for (int j = 2; j <= 10; j ++){
                deck.add(new NumCard(j));
            }
            for (int j = 0; j < 3; j ++){
                deck.add(new FaceCard());
            }
            deck.add(new AceCard());
        }
        }
        System.out.println("deck.size() = " + deck.size());
        Collections.shuffle(deck);
    }
    private void rebuildDeck(){
        // add all cards in discard to deck and clear discard
        // make sure all aces in deck are in hard state
        discard.hardToSoft();
        deck.addAll(discard);
        discard.clear();
        Collections.shuffle(deck);
    }
    private Card drawFromDeck(){
        Card out = deck.remove(0);
        if (deck.isEmpty()){
            rebuildDeck();
        }
        return out;
    }
    private void clearHands(){
        if (!playerHand.isEmpty()){
            discard.addAll(playerHand);
            playerHand.clear();
        }
        if (!dealerHand.isEmpty()){
            discard.addAll(dealerHand);
            dealerHand.clear();
        }
        if (!splitHand1.isEmpty()){
            discard.addAll(splitHand1);
            splitHand1.clear();
        }
        if (!splitHand2.isEmpty()){
            discard.addAll(splitHand2);
            splitHand2.clear();
        }
    }
    
    private int checkSplit(){
        int playerVal = playerHand.get(0).getValue();
        int dealerVal = dealerHand.get(0).getValue();
        return decisions[270 + 10*(playerVal-2) + (dealerVal-2)];
    }
    // note: this considers doubles as hits (use when doubling down isn't allowed)
    private int checkDecision(CardList hand){
        for (Card card : hand) {
        }
        int playerVal = hand.sum();
        int dealerVal = dealerHand.get(0).getValue();
        if (hand.hasSoftAce()){
            return decisions[180 + 10 * (playerVal-13) + (dealerVal-2)];
        }
        else {
            return decisions[(10 * (playerVal-4)) + (dealerVal-2)];
        }
    }
    // chatter
    private void say(String s){
        if (chatter){
            System.out.println(s);
        }
    }
    // debug
    public static void main(String[] args) {
       
    }
}
