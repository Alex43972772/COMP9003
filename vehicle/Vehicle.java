package Vehicle;
import java.util.List;

public abstract class vehicle {
    private String vehicleID;
    private double velocity;
    private int[] lane;
    private double currentPosition;
    private int moveTimeSeconds;

    public vehicle(String vehicleID, double initialVelocity, int moveTimeSeconds) {
        this.vehicleID = vehicleID;
        this.velocity = initialVelocity;
        this.lane = new int[]{1}; // Initially in lane 1
        this.currentPosition = 0;
        this.moveTimeSeconds = moveTimeSeconds;
    }

    // Abstract method to calculate velocity
    protected abstract double velocity();

    // Method to change lanes
    protected void lane() {
        try {
            int currentLane = lane[0];
            if (currentLane >= 3) {
                lane[0] = 1; // Return to lane 1 if currently in lane 3
            } else {
                lane[0] = currentLane + 1; // Move to next lane
            }
        } catch (Exception e) {
            throw new SimulationException("Invalid lane transition", e);
        }
    }

    // Method to move the vehicle
    public void move() {
        // Update velocity based on vehicle type
        this.velocity = velocity();
        
        // Change lane
        lane();
        
        // Update position
        this.currentPosition += velocity * moveTimeSeconds;
    }
    
    // Method to show the current traffic state
    public static void showTrafficState(List<vehicle> vehicles) {
        System.out.println("\n----- Current Traffic State -----");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the simulation.");
            return;
        }
        
        for (vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    // Getters and Setters
    public String getVehicleID() {
        return vehicleID;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public int getLane() {
        return lane[0];
    }

    public double getCurrentPosition() {
        return currentPosition;
    }

    public int getMoveTimeSeconds() {
        return moveTimeSeconds;
    }

    public void setMoveTimeSeconds(int moveTimeSeconds) {
        this.moveTimeSeconds = moveTimeSeconds;
    }
    
    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleID + 
               ", Velocity: " + velocity + " m/s" +
               ", Lane: " + lane[0] + 
               ", Position: " + currentPosition + " meters";
    }
} 