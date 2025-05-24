package intersectionNetwork;

import simulationException.SimulationException;
import java.util.ArrayList;
import java.util.List;

public class IntersectionNetwork {
    private String vehicleID;
    private int[][] intersections; // [lane][intersection]
    private int currentIntersection;
    
    public IntersectionNetwork(String vehicleID) {
        this.vehicleID = vehicleID;
        this.intersections = new int[3][10]; // 3 lanes, 10 intersections
        this.currentIntersection = 0;
        
        // Initialize intersections on lanes 1 and 3
        for (int i = 0; i < 10; i++) {
            // Lane 1 and 3 have intersections
            intersections[0][i] = 1; // Lane 1 (index 0)
            intersections[2][i] = 1; // Lane 3 (index 2)
        }
    }
    
    public void moveThrough() {
        try {
            // Move through every alternate intersection
            if (currentIntersection < intersections[0].length) {
                // Determine if vehicle turns at this intersection
                int turnStatus = (currentIntersection % 2 == 0) ? 1 : 0;
                
                // Update the intersection status for lanes 1 and 3
                if (turnStatus == 1) {
                    System.out.println("Vehicle " + vehicleID + " turned at intersection " + currentIntersection);
                } else {
                    System.out.println("Vehicle " + vehicleID + " went straight at intersection " + currentIntersection);
                }
                
                currentIntersection++;
            } else {
                System.out.println("Vehicle " + vehicleID + " has passed all intersections.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SimulationException("Attempted to move through a non-existent intersection", e);
        }
    }
    
    public void showIntersectionStatus() {
        System.out.println("Vehicle ID: " + vehicleID);
        System.out.println("Current intersection: " + currentIntersection);
        
        System.out.println("Intersection status:");
        for (int lane = 0; lane < intersections.length; lane++) {
            System.out.print("Lane " + (lane + 1) + ": ");
            for (int i = 0; i < intersections[lane].length; i++) {
                System.out.print(intersections[lane][i] + " ");
            }
            System.out.println();
        }
    }
    
    public String getVehicleID() {
        return vehicleID;
    }
    
    public int getCurrentIntersection() {
        return currentIntersection;
    }
    
    // Method to simulate moving through a non-existent intersection
    public void moveToInvalidIntersection(int lane, int intersection) {
        try {
            if (lane < 0 || lane >= intersections.length || 
                intersection < 0 || intersection >= intersections[0].length) {
                throw new ArrayIndexOutOfBoundsException("Invalid intersection coordinates");
            }
            
            // Try to move through middle lane (lane 2) which has no intersections
            if (lane == 1) { // Middle lane (index 1)
                throw new SimulationException("Attempted to move through a non-existent intersection on middle lane");
            }
            
            System.out.println("Vehicle " + vehicleID + " moved through intersection at lane " + 
                              (lane + 1) + ", intersection " + intersection);
        } catch (ArrayIndexOutOfBoundsException | SimulationException e) {
            throw new SimulationException("Error moving through intersection: " + e.getMessage(), e);
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        List<IntersectionNetwork> networks = new ArrayList<>();
        networks.add(new IntersectionNetwork("V001"));
        networks.add(new IntersectionNetwork("V002"));
        networks.add(new IntersectionNetwork("V003"));
        
        // Show initial status
        System.out.println("Initial intersection status:");
        for (IntersectionNetwork network : networks) {
            network.showIntersectionStatus();
            System.out.println();
        }
        
        // Move vehicles through intersections
        System.out.println("\nMoving vehicles through intersections:");
        for (IntersectionNetwork network : networks) {
            for (int i = 0; i < 3; i++) {
                network.moveThrough();
            }
            System.out.println();
        }
        
        // Show updated status
        System.out.println("\nUpdated intersection status:");
        for (IntersectionNetwork network : networks) {
            network.showIntersectionStatus();
            System.out.println();
        }
        
        // Test error handling
        System.out.println("\nTesting error handling:");
        try {
            networks.get(0).moveToInvalidIntersection(1, 5); // Try middle lane
        } catch (SimulationException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
} 