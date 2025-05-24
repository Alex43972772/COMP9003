package vehicle;
public class Truck extends Vehicle {
    private static final double DEFAULT_VELOCITY = 90.0; // meters per 5 seconds
    
    public Truck(int vehicleID) {
        super(vehicleID, DEFAULT_VELOCITY);
    }
    
    @Override
    public String toString() {
        return "Truck " + super.toString();
    }
} 