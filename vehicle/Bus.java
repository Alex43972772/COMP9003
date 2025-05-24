package vehicle;
public class Bus extends Vehicle {
    private static final double DEFAULT_VELOCITY = 80.0; // meters per 5 seconds
    
    public Bus(int vehicleID, double initialVelocity) {
        super(vehicleID, DEFAULT_VELOCITY);
    }
    
    @Override
    public String toString() {
        return "Bus: " + super.toString();
    }
} 