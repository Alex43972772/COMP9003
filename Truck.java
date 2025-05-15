public class Truck extends Vehicle {
    private static final double DEFAULT_VELOCITY = 90.0; // meters per 5 seconds
    
    public Truck(String vehicleID, int moveTimeSeconds) {
        super(vehicleID, DEFAULT_VELOCITY, moveTimeSeconds);
    }
    
    @Override
    protected double velocity() {
        // Truck moves at 90 meters per 5 seconds = 18 m/s
        return DEFAULT_VELOCITY / getMoveTimeSeconds();
    }
    
    @Override
    public String toString() {
        return "Truck: " + super.toString();
    }
} 