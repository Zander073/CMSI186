import java.util.Random;

public class Die {
    //Global variables
    private static Random random = new Random();
    public static final String SIX_SIDED_DIE_EMOJI = "🎲";
    public final int sides;
    public int value;

    //Constructor for global variables
    public Die(int sides, int value) {
      if(sides < 4){
        throw new IllegalArgumentException("At least four sides required");
      }
      if(value < 1 || value > sides){
        throw new IllegalArgumentException("Die value not legal for die shape");
      }
      this.sides = sides;
      this.value = value;
    }

    //Returns random value of 1 to however many sides
    public int roll() {
      value = random.nextInt(sides) + 1;
      return value;
    }

    //Returns number of sides
    public int getSides() {
      return sides;
    }

    //Returns value of die
    public int getValue() {
      return value;
    }

    /**
     * Returns a description of this die, which is its value enclosed in square
     * brackets, without spaces, for example "[5]".
     */
    @Override public String toString() {
      return "["+value+"]";
    }
}
