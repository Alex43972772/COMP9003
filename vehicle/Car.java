package vehicle;
public class Car extends Vehicle {
    private static final double DEFAULT_VELOCITY = 100.0; // meters per 5 seconds
    
    public Car(int vehicleID) {
        super(vehicleID, DEFAULT_VELOCITY);
    }
    
    @Override
    public String toString() {
        return "Car " + super.toString();
    }
} 