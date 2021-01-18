import java.util.function.Function;

public class Pod {
    private String name;
    private Function<Double, Double> velocityFunction;

    //Constructor
    public Pod(String name, Function<Double, Double> velocityFunction) {
        this.name = name;
        this.velocityFunction = velocityFunction;
    }

    //Getter method
    public String getName() {
        return name;
    }

    //This method is necessary to compute Riemann sum method, distanceTraveled()
    public double v(double t) {
        return velocityFunction.apply(t);
    }

    //Estimates the distance of a pod who's velocity depends on a given function.
    //Distance is computed using Riemann sum method
    public double distanceTraveled(double startTime, double endTime, int slices) {
        if(slices < 1){
            throw new IllegalArgumentException("At least one slice required");
        }
        var distance = 0.0;
        var timeSlice = (endTime - startTime) / slices;
        for(var i = 0; i < slices; i++){
            var t = startTime + i * timeSlice;
            distance += v(t) * timeSlice;
        }
        return distance;
    }
}
