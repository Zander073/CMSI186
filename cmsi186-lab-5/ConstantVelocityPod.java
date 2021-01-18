public class ConstantVelocityPod extends Pod {

    //Constructor
    public ConstantVelocityPod(String name, double velocity) {
        //Lambda expression
        super(name, t -> velocity);
    }
}
