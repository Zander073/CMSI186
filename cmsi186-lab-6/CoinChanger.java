import java.util.Set;
import java.util.*;

public abstract class CoinChanger {
    abstract public int minCoins(int amount, Set<Integer> denominations);

    //Checks if arguments are suitable
    private static void checkArguments(int amount, Set<Integer> denominations) {
        Integer valOne = 1;
        int length = 0;
        boolean exists = false;
        if(amount < 1){
          throw new IllegalArgumentException("Amount must be at least 1");
        }
        if(denominations.size() < 1){
          throw new IllegalArgumentException("At least one denomination is required");
        }
        for (Iterator<Integer> it = denominations.iterator(); it.hasNext(); ) {
          Integer f = it.next();
          if(f.intValue() > 0){
            length++;
          }
        }
        if(length != denominations.size()){
          throw new IllegalArgumentException("Denominations must all be positive");
        }
        for (Iterator<Integer> it = denominations.iterator(); it.hasNext(); ) {
          Integer g = it.next();
          if(g.equals(valOne)){
            exists = true;
          }
        }
        if(exists == false){
          throw new IllegalArgumentException("Denominations must have a 1-unit coin");
        }
    }

    //Recurisve method
    public static class TopDown extends CoinChanger {
        public int minCoins(int amount, Set<Integer> denominations) {
            checkArguments(amount, denominations);
            int[] denomination = new int[denominations.size()];
            int index = 0;
            for (Iterator<Integer> it = denominations.iterator(); it.hasNext(); ) {
              Integer f = it.next();
              denomination[index] = f.intValue();
              index++;
            }
            int[] memo = new int[amount + 1];
            return minCoins(amount, denomination, memo);
        }

        //Helper method
        public int minCoins(int amount, int[] coins, int[] memo){
          if (amount < 0) {
            return -1;
          }
          if (memo[amount] != 0) {
            return memo[amount];
          }
          if (amount == 0) {
            return 0;
          }

          //Arbitrary placeholder
          int val = Integer.MAX_VALUE;
          for (int coin: coins) {
            //Breaks loop and moves on to next iteration
            if (amount - coin < 0){
              continue;
            }
            memo[amount - coin] = minCoins(amount - coin, coins, memo);
            if (memo[amount - coin] >= 0 && 1 + memo[amount - coin] < val) {
              val = memo[amount - coin] + 1;
            }
          }
          memo[amount] = val != Integer.MAX_VALUE ? val : -1;
          return memo[amount];
        }
    }

    //Non-recursive method
    public static class BottomUp extends CoinChanger {
        public int minCoins(int amount, Set<Integer> denominations) {
            checkArguments(amount, denominations);
            int[] denomination = new int[denominations.size()];
            int index = 0;
            for (Iterator<Integer> it = denominations.iterator(); it.hasNext();) {
              Integer f = it.next();
              denomination[index] = f.intValue();
              index++;
            }
            int[] temp = new int[amount + 1];
            temp[0] = 0;
            for (int i = 1; i <= amount; i++) {
                //Arbitrary placeholder
                int val = Integer.MAX_VALUE;
                for (int coin : denomination) {
                    if (i - coin < 0)
                        continue;
                    if(temp[i - coin] >= 0 && temp[i - coin] + 1 < val)
                        val = temp[i - coin] + 1;
                }
                if (val == Integer.MAX_VALUE)
                    temp[i] = -1;
                else
                    temp[i] = val;
            }
            return temp[amount];
        }
    }
}
