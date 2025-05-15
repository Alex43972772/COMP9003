package vehicle;
public class Bus extends Vehicle {
    private static final double DEFAULT_VELOCITY = 80.0; // meters per 5 seconds
    
    public Bus(String vehicleID, int moveTimeSeconds) {
        super(vehicleID, DEFAULT_VELOCITY, moveTimeSeconds);
    }
    
    @Override
    protected double velocity() {
        // Bus moves at 80 meters per 5 seconds = 16 m/s
        return DEFAULT_VELOCITY / getMoveTimeSeconds();
    }
    
    @Override
    public String toString() {
        return "Bus: " + super.toString();
    }
} 