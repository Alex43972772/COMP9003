package vehicle;
import java.util.List;
import simulationexception.SimulationException;
/*
 * Vehicle Class Hierarchy:
 * 
 * Create an abstract Vehicle class with attributes such as vehicleID, velocity, lane and currentPosition.
 * 
 * Include methods to calculate velocity (i.e., velocity()) and lane changes (i.e., lane(); assume the vehicles are driving on a three-lane road; initially choose the first
 * lane for each vehicle and change it to lanes other than the current lane for each call to
 * move() method). The variable lane is an int array. Note that once a vehicle reaches
lane 3, it returns to lane 1 on the next call to move().
o Call the move() method five times; the move() method will change the current status
of a vehicle, such as velocity (except the vehicleID) and lane. Each call to move()
method is considered 5 seconds; the move() method will call the
velocity()method to calculate the vehicle’s velocity. Assuming the current speed
is 100 at the start, its velocity would be v = 100 meters / 5 seconds = 20 meters per
second. The currentPosition is calculated at the end of the call 5 to move()
method, which is velocity*5 (since the method is called five times).
▪ The default value is 5 seconds; adjust it to any value from the Main class
(optional, no weightage); this will help you test your method once you see the
change in velocity.
o Derive at least three subclasses (e.g., Car, Bus, Truck) from the Vehicle class that
implement specific behaviours (overriding methods such as move()). The Car, Bus,
and Truck move at 100, 80, and 90 speeds.
o Implement another method (showTrafficState()) to show the current status of
all vehicles, including velocity, lane, and position. Call this method two times: once
after the second call to move() method and once at the end of the simulation. Show
the currentPosition of each vehicle on the second call.
 * o Provide error handling for invalid lane transitions, i.e.; a vehicle moves to lane 4.
 * o Optional: Implement another method to calculate acceleration or deceleration. (no
 * weightage)
 */
public abstract class Vehicle {
    private int vehicleID;
    private double velocity;
    private int[] lane;
    private double currentPosition;

    public Vehicle(int vehicleID, double initialVelocity) {
        this.vehicleID = vehicleID;
        this.velocity = initialVelocity;
        this.lane = new int[]{1, 1, 0, 0}; // Initially in lane 1
        this.currentPosition = 0;
    }

    // Abstract method to calculate velocity
    protected double velocity(int velocityChange) {
        return velocity + velocityChange;
    }
    // Method to change lanes
    protected void lane() {
        try {
            int currentLane = lane[0];
            lane[currentLane] = 0;
            if (currentLane == 3) {
                lane[0] = 1; // Return to lane 1 if currently in lane 3
            } else {
                lane[0] = currentLane + 1; // Move to next lane
            }
            lane[currentLane] = 1;
        } catch (Exception e) {
            throw new SimulationException("Invalid lane transition", e);
        }
    }

    // Method to move the vehicle
    public void move(int velocityChange) {
        int moveTimeSeconds = 5;
        // Update velocity based on vehicle type
        this.velocity = velocity(velocityChange);
        
        // Change lane
        lane();
        
        // Update position
        this.currentPosition += velocity * moveTimeSeconds;
    }
    
    // Method to show the current traffic state
    public static void showTrafficState(List<Vehicle> vehicles) {
        System.out.println("\n----- Current Traffic State -----");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the simulation.");
            return;
        }
        
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    // Getters and Setters
    public int getVehicleID() {
        return vehicleID;
    }

    public double getVelocity() {
        return velocity;
    }


    public int getLane() {
        return lane[0];
    }

    public double getCurrentPosition() {
        return currentPosition;
    }
    
    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleID + 
               ", Velocity: " + velocity + " m/s" +
               ", Lane: " + lane[0] + 
               ", Position: " + currentPosition + " meters";
    }
} 