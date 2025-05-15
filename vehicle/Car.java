package vehicle;
public class Car extends vehicle {
    private static final double DEFAULT_VELOCITY = 100.0; // meters per 5 seconds
    
    public Car(String vehicleID, int moveTimeSeconds) {
        super(vehicleID, DEFAULT_VELOCITY, moveTimeSeconds);
    }
    
    @Override
    protected double velocity() {
        // Car moves at 100 meters per 5 seconds = 20 m/s
        return DEFAULT_VELOCITY / getMoveTimeSeconds();
    }
    
    @Override
    public String toString() {
        return "Car: " + super.toString();
    }
} 