public class PolynomialPod extends Pod{

    //Constructor
    public PolynomialPod(String name, double... coefficients){
      //Lambda expression
      super(name, t -> evaluatePolynomial(coefficients, t));
    }

    //Evalutates the function at a given time, 'x', with the 'coefficients' parameter who's degrees relies
    //on the index of each coefficient within the array
    private static double evaluatePolynomial(double[] coefficients, double x){
      var result = 0.0;
      for(var i = 0; i < coefficients.length; i++){
        result += coefficients[i] * Math.pow(x, i);
      }
      return result;
    }
}
