import java.util.*;

public class HighRollerGame {

    public static void main(String[] args) {

        DiceSet diceSet = null;
        var highest = 0;
        System.out.println("Welcome " + Die.SIX_SIDED_DIE_EMOJI.repeat(5));

        while (true) {
            System.out.println();
            try {
                
                Scanner object = new Scanner(System.in);
                System.out.println("Enter a command (h for help): ");
                String command = object.nextLine();

                if (command.equals("h")) {
                    showHelp();
                } else if (command.matches("q(uit)?")) {
                    System.out.println("I'm glad you played today. You look great!");
                    break;
                } else if (command.matches("use\\s+\\d\\d?\\s+\\d\\d?")) {
                    var tokens = command.split("\\s+");
                    var sides = Integer.parseInt(tokens[2].trim());
                    var number = Integer.parseInt(tokens[1].trim());
                    diceSet = new DiceSet(sides, number);
                    highest = Math.max(highest, diceSet.sum());
                    System.out.println("You are now using a " + diceSet.descriptor());
                    System.out.println(diceSet);
                } else if (command.matches("roll\\s+all")) {
                    if (diceSet == null) {
                        throw new IllegalStateException("You don't have any dice yet");
                    }
                    diceSet.rollAll();
                    int highTemp = diceSet.sum();
                    if(highTemp > highest){
                      highest = highTemp;
                    }
                    System.out.println(diceSet);
                } else if (command.matches("roll\\s+\\d+")) {
                    if (diceSet == null) {
                        throw new IllegalStateException("You don't have any dice yet");
                    }
                    diceSet.rollIndividual(Integer.parseInt(command.substring(4).trim()));
                    highest = Math.max(highest, diceSet.sum());
                    System.out.println(diceSet);
                } else if (command.matches("high(est)?")) {
                    if (highest == 0) {
                        System.out.println("No high score registered yet");
                    } else {
                        System.out.println("Highest score so far is " + highest);
                    }
                } else {
                    System.out.println("I don't understand");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void showHelp() {
        System.out.println("h or help       : Prints this message");
        System.out.println("q or quit       : Quits the program");
        System.out.println("use <n> <s>     : Get a new dice set with n dice of s sides each");
        System.out.println("roll all        : Roll all the dice in your current dice set");
        System.out.println("roll <i>        : Roll the ith die of your current dice set");
        System.out.println("high or highest : Prints the highest roll so far");
    }
}
