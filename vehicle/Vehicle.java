package vehicle;

import java.util.ArrayList;
import simulationException.SimulationException;
/*
 * My understanding of the requirement of Vehicle class, and the implementation reasoning:
 * 
 * 1. The vehicle has a initial velocity when it is initialized.
 * 2. The vehicle can change its velocity by the velocityChange parameter in the move method.
 * 3. Every move() call means the vehicle moves at the changed velocity for 5 seconds.
 * 4. Hence after every call to move(), the currentposition changes by the new velocity * 5.
 * 5. the vehicle changs its lane (1 -> 2, 2 -> 3, 3 -> 1) by calling lane();
 * 6. The vehicle must change its lane for once everytime a move() is called.
 * 7. The vehicle can't have a global variable to store all vehicles that were initialized because the attributes are given.
 * 8. That is why I used a list of vehicles in the main class to store all vehicles.
 * 9. I could have used a static variable to store the list of vehicles in the Vehicle class, but I didn't because the attributes are given.
 * 10. The error message to catch lane change exception will never be thrown because the lane will never reach 4. But I added it anyway.
 */

public abstract class Vehicle {
    private int vehicleID;
    private double velocity;
    private int[] lane;
    private double currentPosition;
    private int moveCount;

    public Vehicle(int vehicleID, double initialVelocity) {
        this.vehicleID = vehicleID;
        this.velocity = initialVelocity;
        this.lane = new int[]{1, 1, 0, 0};
        this.currentPosition = 0;
        this.moveCount = 0;
    }

    // Abstract method to calculate velocity
    protected double velocity(int time) {
         return velocity + time; 
    }

    // Method to change lanes
    protected void lane() {
        try {
            int currentLane = lane[0];
            lane[currentLane] = 0;
            if (currentLane == 3) {
                // Return to lane 1 if currently in lane 3
                lane[0] = 1; 
            } else {
                // Move to next lane
                lane[0] = currentLane + 1; 
            }
            lane[currentLane] = 1;
        } catch (Exception e) {
            throw new SimulationException("Invalid lane transition", e);
        }
    }

    // Method to move the vehicle
    public void move(int timeElapsed) {
        int moveTimeSeconds = 5;

        // Update velocity based on vehicle type
        this.velocity = velocity(timeElapsed);
        
        // Change lane
        lane();
        
        // Update position
        this.currentPosition += velocity * moveTimeSeconds;

        // increment moveCount
        this.moveCount++;
    }
    
    // Method to show the current traffic state
    public static void showTrafficState(ArrayList<Vehicle> vehicles) {       
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

    public double currentPosition() {
        return currentPosition;
    }

    public int getMoveCount() {
        return moveCount;
    }
    
    @Override
    public String toString() {
        return vehicleID + ": " +
                "Lane: " + lane[0] +
               ", Velocity: " + String.format("%.2f", velocity) + " m/s" +
               ", Position: " + String.format("%.0f", currentPosition);
    }
}