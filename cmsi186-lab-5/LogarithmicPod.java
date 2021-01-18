public class LogarithmicPod extends Pod{

    //Constructor
    public LogarithmicPod(String name, double a, double b){
      //Lambda expression
      super(name, t -> evalulateLogarithm(a, b, t));
    }

    //Evaluates the function at a given time, 'x', with constants 'a' and 'b'
    private static double evalulateLogarithm(double a, double b, double x){
      var inside = b * (x + 1);
      var result = a * Math.log(inside);
      return result;
    }
}
