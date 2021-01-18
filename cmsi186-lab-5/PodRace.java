import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

public class PodRace {

    //Races the pods against each other and returns the winner(s)
    public static Set<Pod> race(double distance, Set<Pod> racers, double timeSlice, double timeLimit) {
        if(distance <= 0){
          throw new IllegalArgumentException("Distance must be greater than 0");
        }

        var winners = new HashSet<Pod>();
        var time = 0;
        boolean winnersFound = false;

        //Checks at each timeSlice if a pod(s) has crossed the finish line
        //The while loop alone constrains the race within the time limit 
        while(time <= timeLimit){
          for(var p: racers){
            var temp = p.distanceTraveled(0, time, 10);
            if(temp >= distance){
              winners.add(p);
              winnersFound = true;
            }
          }
          time += timeSlice;
          if(winnersFound){
            return winners;
          }
        }
        return winners;
    }
}
