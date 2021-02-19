import java.util.*;

public class DiceSet {

    Die[] dice;
    int sidesOnEachDie;
    int numberOfDice;

    //Constructor takes in arbitrary number of die sides and die
    public DiceSet(int sidesOnEachDie, int numberOfDice) {
        if(sidesOnEachDie < 4){
          throw new IllegalArgumentException("Dice must have at least four sides");
        }
        if(numberOfDice < 2){
          throw new IllegalArgumentException("At least two dice required");
        }
        this.sidesOnEachDie = sidesOnEachDie;
        this.numberOfDice = numberOfDice;
        this.dice = new Die[numberOfDice];
        for(int i = 0; i < dice.length; i++){
          this.dice[i] = new Die(sidesOnEachDie, 1);
        }
    }

    //Constructor takes in arbitrary number of die sides and die with given current die values
    public DiceSet(int sidesOnEachDie, int... values) {
        if(values.length < 2){
          throw new IllegalArgumentException("At least two dice required");
        }
        if(sidesOnEachDie < 4){
          throw new IllegalArgumentException("Dice must have at least four sides");
        }
        this.sidesOnEachDie = sidesOnEachDie;
        this.numberOfDice = values.length;
        this.dice = new Die[values.length];
        for(int i = 0; i < values.length; i++){
          if(values[i] > sidesOnEachDie){
            throw new IllegalArgumentException("Die value not legal for die shape");
          }
          dice[i] = new Die(sidesOnEachDie, values[i]);
        }
    }

    /**
     * Returns the descriptor of the dice set; for example "5d20" for a set with
     * five dice of 20 sides each; or "2d6" for a set of two six-sided dice.
     */
    public String descriptor() {
        String val = dice.length + "d" + dice[0].getSides();
        return val;
    }

    //Gets the sum of every die value in set.
    public int sum() {
        int sum = 0;
        for(int i = 0; i < dice.length; i++){
          sum += dice[i].getValue();
        }
        return sum;
    }

    //Rolls every die in set.
    public void rollAll() {
        for(int i = 0; i < dice.length; i++){
          dice[i].roll();
        }
    }

    //Rolls ith die in diceSet
    public void rollIndividual(int i) {
        dice[i].roll();
    }

    /**
     * Returns the value of the ith die.
     */
    public int getIndividual(int i) {
        // TODO
        return dice[i].getValue();
    }

    /**
     * Returns the values of each of the dice in a list.
     */
    public ArrayList<Integer> values() {
        // TODO
        if(numberOfDice < 2){
          throw new IllegalArgumentException("At least two dice required");
        }
        if(sidesOnEachDie < 4){
          throw new IllegalArgumentException("Dice must have at least four sides");
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < dice.length; i++){
          Integer temp = new Integer(1);
          list.add(temp);
        }
        return list;
    }

    public int getLength(){
      return dice.length;
    }

    public int getDieSides(){
      return dice[0].getSides();
    }

    /**
     * Returns whether this dice set has the same distribution of values as
     * an other dice set. The two dice sets must have the same number of dice
     * and the same number of sides per dice, and there must be the same
     * number of each value in each set.
     */
    public boolean isIdenticalTo(DiceSet diceSet) {
        // TODO
        int[] listOfIndex = new int[diceSet.getLength()];
        int sides = diceSet.getDieSides();
        int sum = 0;
        if(dice.length != diceSet.getLength()){
          return false;
        }
        if(sides != dice[0].getSides()){
          return false;
        }
        for(int i = 0; i < dice.length; i++){
          int value = dice[i].getValue();
          for(int j = 0; j < dice.length; j++){
            if(value == diceSet.getIndividual(j) && listOfIndex[j] == 0){
              listOfIndex[j] += 1;
            }
          }
        }
        for(int k = 0; k < dice.length; k++){
          sum += listOfIndex[k];
        }
        if(sum == dice.length){
          return true;
        }
        return false;
    }

    /**
     * Returns a string representation in which each of the die strings are
     * joined without a separator, for example: "[2][5][2][3]".
     */
    @Override public String toString() {
        // TODO
        String val = "";
        for(int i = 0; i < dice.length; i++){
          val += "[" + dice[i].getValue() + "]";
        }
        return val;
    }
}
