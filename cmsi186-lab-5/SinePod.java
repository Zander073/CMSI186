public class SinePod extends Pod{

    //Constructor
    public SinePod(String name, double a, double b, double c){
      //Lambda expression
      super(name, t -> evaluateSinePod(a, b, c, t));
    }

    //Evaluates the function at time, 'x', with the constants 'a', 'b', and 'c'
    public static double evaluateSinePod(double a, double b, double c, double x){
      var result = a * Math.sin(b * x) + c;
      return result;
    }
}
