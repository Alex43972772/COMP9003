import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int DEFAULT_MOVE_TIME = 5; // seconds
    private static final int SIMULATION_STEPS_PER_MINUTE = 12; // 5 seconds * 12 = 60 seconds
    
    private List<Vehicle> vehicles;
    private List<TrafficSignal> trafficSignals;
    private List<IntersectionNetwork> intersectionNetworks;
    private Scanner scanner;
    
    public Main() {
        vehicles = new ArrayList<>();
        trafficSignals = new ArrayList<>();
        intersectionNetworks = new ArrayList<>();
        scanner = new Scanner(System.in);
        
        // Initialize with some default vehicles
        vehicles.add(new Car("Car001", DEFAULT_MOVE_TIME));
        vehicles.add(new Bus("Bus001", DEFAULT_MOVE_TIME));
        vehicles.add(new Truck("Truck001", DEFAULT_MOVE_TIME));
        
        // Initialize traffic signals
        trafficSignals.add(new TrafficSignal("TS001"));
        trafficSignals.add(new TrafficSignal("TS002"));
        trafficSignals.add(new TrafficSignal("TS003"));
        
        // Initialize intersection networks
        intersectionNetworks.add(new IntersectionNetwork("V001"));
        intersectionNetworks.add(new IntersectionNetwork("V002"));
        intersectionNetworks.add(new IntersectionNetwork("V003"));
    }
    
    public void displayMenu() {
        System.out.println("\n===== Urban Traffic Simulation System =====");
        System.out.println("1. View current traffic status");
        System.out.println("2. Add a new vehicle");
        System.out.println("3. Remove a vehicle");
        System.out.println("4. Adjust traffic signal timers");
        System.out.println("5. Run simulation");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    
    public void showTrafficState() {
        System.out.println("\n----- Current Vehicle Status -----");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the simulation.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
        
        System.out.println("\n----- Current Traffic Signal Status -----");
        if (trafficSignals.isEmpty()) {
            System.out.println("No traffic signals in the simulation.");
        } else {
            for (TrafficSignal signal : trafficSignals) {
                signal.showTrafficSignal();
            }
        }
        
        System.out.println("\n----- Current Intersection Status -----");
        if (intersectionNetworks.isEmpty()) {
            System.out.println("No intersection networks in the simulation.");
        } else {
            for (IntersectionNetwork network : intersectionNetworks) {
                network.showIntersectionStatus();
                System.out.println();
            }
        }
    }
    
    public void addVehicle() {
        System.out.println("\n----- Add New Vehicle -----");
        System.out.println("Select vehicle type:");
        System.out.println("1. Car");
        System.out.println("2. Bus");
        System.out.println("3. Truck");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter vehicle ID: ");
            String vehicleID = scanner.nextLine();
            
            Vehicle newVehicle = null;
            switch (choice) {
                case 1:
                    newVehicle = new Car(vehicleID, DEFAULT_MOVE_TIME);
                    break;
                case 2:
                    newVehicle = new Bus(vehicleID, DEFAULT_MOVE_TIME);
                    break;
                case 3:
                    newVehicle = new Truck(vehicleID, DEFAULT_MOVE_TIME);
                    break;
                default:
                    throw new SimulationException("Invalid vehicle type selected");
            }
            
            vehicles.add(newVehicle);
            System.out.println("Vehicle added successfully: " + newVehicle);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        } catch (SimulationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void removeVehicle() {
        System.out.println("\n----- Remove Vehicle -----");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles to remove.");
            return;
        }
        
        System.out.println("Current vehicles:");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println((i + 1) + ". " + vehicles.get(i));
        }
        
        System.out.print("Enter the number of the vehicle to remove: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > vehicles.size()) {
                throw new SimulationException("Invalid vehicle number");
            }
            
            Vehicle removed = vehicles.remove(choice - 1);
            System.out.println("Vehicle removed: " + removed);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        } catch (SimulationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void adjustTrafficSignalTimers() {
        System.out.println("\n----- Adjust Traffic Signal Timers -----");
        if (trafficSignals.isEmpty()) {
            System.out.println("No traffic signals to adjust.");
            return;
        }
        
        System.out.println("Select traffic signal:");
        for (int i = 0; i < trafficSignals.size(); i++) {
            System.out.println((i + 1) + ". " + trafficSignals.get(i).getSignalID());
        }
        
        System.out.print("Enter your choice: ");
        try {
            int signalChoice = Integer.parseInt(scanner.nextLine());
            if (signalChoice < 1 || signalChoice > trafficSignals.size()) {
                throw new SimulationException("Invalid traffic signal number");
            }
            
            // Create a new signal with adjusted timers
            System.out.print("Enter red timer (seconds): ");
            int redTimer = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter yellow timer (seconds): ");
            int yellowTimer = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter green timer (seconds): ");
            int greenTimer = Integer.parseInt(scanner.nextLine());
            
            // Replace the old signal with a new one with updated timers
            TrafficSignal oldSignal = trafficSignals.get(signalChoice - 1);
            TrafficSignal newSignal = new TrafficSignal(oldSignal.getSignalID(), redTimer, yellowTimer, greenTimer);
            trafficSignals.set(signalChoice - 1, newSignal);
            
            System.out.println("Traffic signal timers updated successfully.");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid numbers for timers.");
        } catch (SimulationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void runSimulation() {
        System.out.println("\n----- Running Simulation -----");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles to simulate. Please add vehicles first.");
            return;
        }
        
        System.out.print("Enter simulation duration (minutes): ");
        try {
            int minutes = Integer.parseInt(scanner.nextLine());
            if (minutes <= 0) {
                throw new SimulationException("Simulation duration must be positive");
            }
            
            int totalSteps = minutes * SIMULATION_STEPS_PER_MINUTE;
            System.out.println("Starting simulation for " + minutes + " minute(s)...");
            
            for (int step = 1; step <= totalSteps; step++) {
                System.out.println("\n----- Simulation Step " + step + " -----");
                
                // Move vehicles
                for (Vehicle vehicle : vehicles) {
                    vehicle.move();
                }
                
                // Change traffic signals (every step)
                for (TrafficSignal signal : trafficSignals) {
                    signal.signal();
                }
                
                // Move through intersections
                for (IntersectionNetwork network : intersectionNetworks) {
                    network.moveThrough();
                }
                
                // Show traffic state after the second move and at the end
                if (step == 2) {
                    System.out.println("\n----- Traffic State after Second Move -----");
                    Vehicle.showTrafficState(vehicles);
                    
                    System.out.println("\n----- Traffic Signal Status -----");
                    for (TrafficSignal signal : trafficSignals) {
                        signal.showTrafficSignal();
                    }
                    
                    System.out.println("\n----- Intersection Status -----");
                    for (IntersectionNetwork network : intersectionNetworks) {
                        network.showIntersectionStatus();
                        System.out.println();
                    }
                } else if (step == totalSteps) {
                    System.out.println("\n----- Final Traffic State -----");
                    Vehicle.showTrafficState(vehicles);
                    
                    System.out.println("\n----- Final Traffic Signal Status -----");
                    for (TrafficSignal signal : trafficSignals) {
                        signal.showTrafficSignal();
                    }
                    
                    System.out.println("\n----- Final Intersection Status -----");
                    for (IntersectionNetwork network : intersectionNetworks) {
                        network.showIntersectionStatus();
                        System.out.println();
                    }
                }
                
                // Display minute completion message
                if (step % SIMULATION_STEPS_PER_MINUTE == 0) {
                    int minuteCompleted = step / SIMULATION_STEPS_PER_MINUTE;
                    System.out.println("\n***** " + minuteCompleted + " Minute(s) Completed! *****");
                }
                
                // Small pause between steps for better readability
                try {
                    Thread.sleep(500); // 500ms pause
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            System.out.println("\nSimulation completed successfully.");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
        } catch (SimulationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void start() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        showTrafficState();
                        break;
                    case 2:
                        addVehicle();
                        break;
                    case 3:
                        removeVehicle();
                        break;
                    case 4:
                        adjustTrafficSignalTimers();
                        break;
                    case 5:
                        runSimulation();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Exiting Urban Traffic Simulation System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        
        scanner.close();
    }
    
    public static void main(String[] args) {
        Main simulator = new Main();
        simulator.start();
    }
} 