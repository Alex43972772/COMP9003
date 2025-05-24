/*
 * UrbanTrafficSimulation
 * 
 * COMP9003 - Assignment 2
 * Group 23
 * 
 * Description: Main entry point for Urban Traffic Simulation.
 * 
 * The program can be passed integer input arguments to load a specified number
 * of Vehicles, TrafficSignals and IntersectionNetworks (each). If no input is 
 * specified, 3 instances of each will be created.
 * 
 * TrafficSignal timer values have been randomised to provide more meaningful
 * output.
 * 
 * Usage:
 *                      java UrbanTrafficSimulation
 * Or, alternatively:
 *                      java UrbanTrafficSimulation 5
 * 
 * Required packages:
 * 
 *          vehicle.*
 *          trafficSignal.TrafficSignal
 *          intersectionNetwork.IntersectionNetwork
 *          simulationException.SimulationException
 * 
 */

import java.util.ArrayList;
import java.util.Scanner;

import vehicle.*;
import trafficSignal.TrafficSignal;
import intersectionNetwork.IntersectionNetwork;
import simulationException.SimulationException;

public class UrbanTrafficSimulation {

    // ArrayLists
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static ArrayList<TrafficSignal> trafficSignals = new ArrayList<>();
    public static ArrayList<IntersectionNetwork> intersectionNetworks = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);
    public static final String TITLE_LINE = new String(new char[80]).replace("\0", "=");
    public static final String SECTION_LINE = new String(new char[80]).replace("\0", "-");

    public static void main(String[] args) {

        // Authorship statement
        System.out.println(SECTION_LINE);
        System.out.println("\nThis submission belongs to:\n\n" +
                "\033[4mGroup 23\033[0m\n\n" +
                "530049902, Michael Thomas\n" +
                "<SID>, Malavika Nandagopan\n" +
                "<SID>, Alex\n");

        // Program title
        System.out.println("\n" + TITLE_LINE);
        System.out.println("\n\t\t\t\033[1;4mUrban Traffic Simulation System\033[0m");
        System.out.println("\n" + TITLE_LINE + "\n");

        // instantiate objects from input arguments
        int num;
        try {
            num = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            num = 3;
        }

        loadObjects(num);

        ///////////// MENU LOOP
        boolean programComplete = false;
        while (!programComplete) {

            // Show the menu
            showMenu();

            // prompt for user input
            String menuSelection = scanner.nextLine();
            System.out.println();

            // labelled switch statement to allow for exiting to main menu
            menuSelection: switch (menuSelection) {

                // MENU 1: Add Vehicle
                case "1":
                    System.out.println(
                            "\n---------------------------------- \033[1mAdd Vehicle\033[0m ---------------------------------\n");

                    boolean validVehicle = false;

                    // loop until valid input or 'cancel' are entered
                    while (!validVehicle) {
                        System.out.print("What kind of vehicle would you like to add? ('Car', 'Bus' or 'Truck'): ");

                        // prompt for user input (type of vehicle)
                        String vehicleType = scanner.nextLine().toLowerCase();

                        // return to main menu if user enters "cancel"
                        if (vehicleType.equals("cancel")) {
                            returnToMain();
                            break menuSelection;
                        } else {
                            try {
                                validVehicle(vehicleType);
                                // String vehicleID = String.valueOf(vehicles.size() + 1);
                                int vehicleID = vehicles.get(vehicles.size() - 1).getVehicleID() + 1;

                                switch (vehicleType) {

                                    // add a car
                                    case "car":
                                        Car car = new Car(vehicleID);
                                        vehicles.add(car);
                                        System.out.println("\n\033[1;32mNew car created!\033[0m");
                                        System.out.println("vehicleID: " + car.getVehicleID());
                                        System.out.println("Velocity: " + car.getVelocity() + "\n");
                                        validVehicle = true;
                                        break;

                                    // add a bus
                                    case "bus":
                                        Bus bus = new Bus(vehicleID);
                                        vehicles.add(bus);
                                        System.out.println("\n\033[1;32mNew bus created!\033[0m");
                                        System.out.println("vehicleID: " + bus.getVehicleID());
                                        System.out.println("Velocity: " + bus.getVelocity() + "\n");
                                        validVehicle = true;
                                        break;

                                    // add a truck
                                    case "truck":
                                        Truck truck = new Truck(vehicleID);
                                        vehicles.add(truck);
                                        System.out.println("\n\033[1;32mNew truck created!\033[0m");
                                        System.out.println("vehicleID: " + truck.getVehicleID());
                                        System.out.println("Velocity: " + truck.getVelocity() + "\n");
                                        validVehicle = true;
                                        break;

                                    // should never happen
                                    default:
                                        System.out.println(
                                                "\033[31mInvalid input. Please enter 'car', 'bus', 'truck', or 'cancel'\033[0m\n");
                                }
                                returnToMain();
                            } catch (SimulationException e) {
                                System.out.println("\n" + e +
                                        "\033[31mInvalid input. Please enter 'car', 'bus', 'truck', or 'cancel'\033[0m\n");
                            }
                        }

                    }

                    break;

                // MENU 2: Remove Vehicle
                case "2":
                    System.out.println(
                            "\n-------------------------------- \033[1mRemove Vehicle\033[0m --------------------------------\n");

                    if (vehicles.isEmpty()) {
                        System.out.println("No vehicles found!");
                    } else {
                        System.out.println("Displaying " + vehicles.size() + " Vehicles...\n");
                        // display vehicles
                        for (Vehicle vehicle : vehicles) {
                            System.out.print(vehicle.getVehicleID() + ". ");
                            System.out.println(vehicle);
                        }

                        boolean validInput = false;

                        while (!validInput) {
                            System.out.print("\nEnter the ID of the vehicle to remove or 'cancel': ");
                            String removeID = scanner.nextLine();

                            try {
                                if (validateInput(removeID) > 0) {
                                    for (int i = 0; i < vehicles.size(); i++) {
                                        if (removeID.equals(String.valueOf(vehicles.get(i).getVehicleID()))) {
                                            System.out.println("\n\033[31mVehicle " + vehicles.get(i).getVehicleID()
                                                    + " removed!\033[0m\n");
                                            vehicles.remove(i);
                                            validInput = true;
                                            break;
                                        }
                                    }
                                    if (!validInput) {
                                        System.out.println(
                                                "\n\033[31mID not found. Please enter a valid Vehicle ID.\033[0m\n");
                                    }
                                } else {
                                    returnToMain();
                                    break menuSelection;
                                }
                            } catch (SimulationException e) {
                                System.out.println("\n" + e + "\033[31mInvalid Vehicle ID.\033[0m\n");
                            }

                        }
                    }
                    returnToMain();
                    break;

                // MENU 3: Adjust Signal Timers
                case "3":
                    System.out.println(
                            "\n----------------------------- \033[1mAdjust Signal Timers\033[0m -----------------------------\n");

                    System.out.println("Which signal would you like to adjust?\n");

                    // display submenu
                    for (TrafficSignal signal : trafficSignals) {
                        System.out.println(signal.getId() + ". Traffic Signal " + signal.getId());
                    }
                    System.out.println(trafficSignals.size() + 1 + ". All signals\n");

                    // input validation
                    boolean validInput = false;
                    String signalSelection;
                    int selection = 0;

                    while (!validInput) {
                        System.out.print("Make a selection: ");
                        signalSelection = scanner.nextLine();
                        System.out.println();

                        try {
                            if (validateInput(signalSelection) > 0) {
                                selection = Integer.parseInt(signalSelection);
                                validInput = true;
                            } else {
                                returnToMain();
                                break menuSelection;
                            }
                        } catch (SimulationException e) {
                            System.out.println(e +
                                    "\033[31mInvalid input. Please enter a numbered selection or 'cancel'\033[0m\n");

                        }

                    }

                    // set timer values
                    String state;
                    int[] newTimer = new int[3];

                    for (int i = 0; i < newTimer.length; i++) {
                        if (i == 0) {
                            state = "\033[31mRed\033[0m";
                        } else if (i == 1) {
                            state = "\033[33mYellow\033[0m";
                        } else {
                            state = "\033[32mGreen\033[0m";
                        }

                        validInput = false;

                        // loop until valid input or 'cancel' are entered
                        while (!validInput) {
                            System.out.print("Set a new timer for the " + state + " signal: ");
                            String newTime = scanner.nextLine();

                            try {
                                if (validateInput(newTime) > 0) {
                                    newTimer[i] = Integer.parseInt(newTime);
                                    validInput = true;
                                } else {
                                    returnToMain();
                                    break menuSelection;
                                }
                            } catch (SimulationException e) {
                                System.out.println(
                                        "\n" + e + "\033[31mInput should be an integer greater than zero.\033[0m\n");
                            }
                        }
                    }

                    // adjust signals
                    if (selection == trafficSignals.size() + 1) {
                        for (TrafficSignal signal : trafficSignals) {
                            signal.setTimer(newTimer);
                        }
                        System.out.println("\nTimers adjusted for all Traffic Signals!\n");

                    } else {
                        for (TrafficSignal signal : trafficSignals) {
                            if (signal.getId().equals(String.valueOf(selection))) {
                                signal.setTimer(newTimer);
                            }
                        }
                        System.out.println("\nTimers adjusted for Traffic Signal " + selection + "\n");
                    }

                    returnToMain();
                    break;

                // MENU 4: View Traffic Status
                case "4":
                    System.out.println(
                            "\n----------------------------- \033[1mShow Traffic Status\033[0m ------------------------------\n");

                    if (!vehicles.isEmpty()) {
                        System.out.println("\n\033[1;4mVehicle states:\033[0m\n");
                        Vehicle.showTrafficState(vehicles);
                    } else {
                        System.out.println("No Vehicles to display.");
                    }

                    if (!trafficSignals.isEmpty()) {
                        System.out.println("\n\033[1;4mSignal states:\033[0m\n");
                        for (TrafficSignal signal : trafficSignals) {
                            signal.showTrafficSignal();
                        }
                    } else {
                        System.out.println("No Traffic Signals to display.");
                    }

                    if (!intersectionNetworks.isEmpty()) {
                        System.out.println("\n\033[1;4mIntersection states:\033[0m\n");
                        for (IntersectionNetwork network : intersectionNetworks) {
                            network.showIntersectionStatus();
                        }
                    } else {
                        System.out.println("No Intersection Networks to display.");
                    }

                    returnToMain();
                    break;

                // MENU 5: Run Simulation
                case "5":
                    System.out.println(
                            "\n-------------------------------- \033[1mRun Simulation\033[0m --------------------------------\n");

                    validInput = false;
                    int maxTime = 0;

                    // get user input & validate
                    while (!validInput) {
                        System.out.print("How many minutes would you like to run the simulation for: ");
                        String userTime = scanner.nextLine();

                        try {
                            if (validateInput(userTime) > 0) {
                                maxTime = Integer.parseInt(userTime);
                                validInput = true;
                            } else {
                                returnToMain();
                                break menuSelection;
                            }
                        } catch (SimulationException e) {
                            System.out.println(
                                    "\n" + e + "\033[31mMinutes should be an integer greater than zero.\033[0m\n");
                        }
                    }

                    // run a simulation
                    runSimulation(maxTime);
                    returnToMain();
                    break;

                // MENU 6: Exit
                case "6": // exit
                case "exit":
                    // close the scanner
                    if (scanner != null) {
                        scanner.close();
                    }

                    // display message
                    System.out.println("\nGoodbye!\n");

                    // exit the program
                    programComplete = true;
                    return;

                default:
                    // notify for invalid input
                    System.out.println("\033[31mInvalid input. Please enter a numbered selection, or 'exit'.\033[0m\n");
            }
        }

    }

    /**
     * Displays the Menu
     */
    public static void showMenu() {
        System.out.println("\033[1mSelect one of the following:\033[0m\n");
        System.out.println("\033[34m1. Add vehicle");
        System.out.println("2. Remove vehicle");
        System.out.println("3. Adjust traffic signal timers");
        System.out.println("4. View current traffic status");
        System.out.println("5. Run Simulation");
        System.out.println("6. Exit program\033[0m\n");
        System.out.print("Enter your numbered selection or 'exit': ");
    }

    /**
     * Displays relevant output before returning to main menu
     */
    public static void returnToMain() {
        System.out.println("\nReturning to main menu...");
        System.out.println("\n" + SECTION_LINE);
        System.out.println();
    }

    /**
     * Instaniates Vehicles, Traffic Signals, and IntersectionNetworks
     * 
     * @param num - the number of each to load
     */
    public static void loadObjects(int num) {
        if (num == 3) {
            Car car = new Car(vehicles.size() + 1);
            vehicles.add(car);

            Bus bus = new Bus(vehicles.size() + 1);
            vehicles.add(bus);

            Truck truck = new Truck(vehicles.size() + 1);
            vehicles.add(truck);
        } else {
            for (int i = 0; i < num; i++) {
                int random = (int) (Math.random() * 3) + 1;
                switch (random) {
                    case 1:
                        Car car = new Car(vehicles.size() + 1);
                        vehicles.add(car);
                        break;
                    case 2:
                        Bus bus = new Bus(vehicles.size() + 1);
                        vehicles.add(bus);
                        break;
                    case 3:
                        Truck truck = new Truck(vehicles.size() + 1);
                        vehicles.add(truck);
                        break;
                    default:
                        Car defaultCar = new Car(vehicles.size() + 1);
                        vehicles.add(defaultCar);
                }
            }
        }

        for (int i = 0; i < num; i++) {
            int[] timer = new int[] { (int) (Math.random() * 60) + 1, (int) (Math.random() * 60) + 1,
                (int) (Math.random() * 60) + 1 };
            
            TrafficSignal signal = new TrafficSignal(trafficSignals.size() + 1, timer);

            trafficSignals.add(signal);
        }

        for (Vehicle vehicle : vehicles) {
            intersectionNetworks.add(new IntersectionNetwork(vehicle.getVehicleID()));
        }

    }

    /**
     * Validates an input String
     * 
     * @param input - the input String to validate
     * @return - 1 if valid input, -1 if invalid, and 0 if 'cancel'
     */
    public static int validateInput(String input) {
        // validates inputs
        if (input.toLowerCase().equals("cancel")) {
            return 0;
        } else {
            try {
                int num = Integer.parseInt(input);
                if (num < 1) {
                    throw new SimulationException("");
                } else {
                    return 1;
                }
            } catch (NumberFormatException e) {
                throw new SimulationException("SimulationException: ");
            }
        }
    }

    /**
     * Validates a vehicle input string
     * 
     * @param input - input to validate
     * @return - boolean, whether or not the input is correct
     */
    public static boolean validVehicle(String input) {
        String[] validVehicles = new String[] { "car", "bus", "truck" };

        boolean validVehicle = false;

        for (int i = 0; i < validVehicles.length; i++) {
            if (input.toLowerCase().equals(validVehicles[i])) {
                validVehicle = true;
            }
        }

        if (!validVehicle) {
            throw new SimulationException("SimulationException: ");
        }

        return validVehicle;

    }

    /**
     * Runs the simulation
     * 
     * @param maxTime - the time in minutes to run the simulation for
     */
    public static void runSimulation(int maxTime) {

        // ensure that vehicles have been added
        if (vehicles.isEmpty()) {
            System.out.println("\n\033[31mError! You need to add at least 1 vehicle to the simulation.\033[0m\n");
        } else {
            System.out.println("\n\033[1;4mRunning simulation...\033[0m\n");

            // input provided in minutes, loop runs in seconds
            int maxTimeSeconds = maxTime * 60;
            int stepDuration = 5;

            // one step is considered 5 seconds
            for (int i = 0; i <= maxTimeSeconds; i += stepDuration) {
                // call the vehicle move method
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getMoveCount() <= 5) {
                        try {
                            vehicle.move(stepDuration);
                        } catch (SimulationException e) {
                            System.out.println("Error! Invalid lane change");
                        }

                        if (vehicle.getMoveCount() == 5) {
                            vehicle.currentPosition();
                        }
                    }
                }

                // update the signal
                for (TrafficSignal signal : trafficSignals) {
                    try {
                        signal.signal(i);
                    } catch (SimulationException e) {
                        System.out.println(e);
                    }
                }

                // call the intersectionNetwork moveThrough method
                for (IntersectionNetwork intersectionNetwork : intersectionNetworks) {
                    for (Vehicle vehicle : vehicles) {
                        if (vehicle.getVehicleID() == intersectionNetwork.getVehicleID()) {
                            intersectionNetwork.moveThrough(vehicle.getLane());
                        }
                    }
                    
                }

                // show the traffic states on the second call
                if (i == stepDuration) {
                    System.out.println();

                    System.out.println("\n\033[1;4mVehicle states:\033[0m\n");
                    Vehicle.showTrafficState(vehicles);

                    System.out.println();

                    System.out.println("\033[1;4mSignal states:\033[0m\n");
                    for (TrafficSignal signal : trafficSignals) {
                        signal.showTrafficSignal();
                    }
                    System.out.println();

                    System.out.println("\033[1;4mIntersection states (V = current position):\033[0m\n");
                    for (IntersectionNetwork network : intersectionNetworks) {
                    network.showIntersectionStatus();
                    }
                    System.out.println();
                }

                if (i % 60 == 0 && i > 0) {
                    System.out.println("\n\033[1;4m" + (i / 60) + " Minutes Completed!\033[0m\n");
                }

                try {
                    Thread.sleep(100); // 500ms pause
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println(TITLE_LINE);
            System.out.println("\n\t\t\t\t\033[1;32mSimulation complete!\033[0m\n");
            System.out.println(TITLE_LINE);

            // // show the traffic states after the final call
            System.out.println("\n\033[1;4mVehicle positions at completion:\033[0m\n");
            Vehicle.showTrafficState(vehicles);

            System.out.println("\n\033[1;4mSignal states at completion:\033[0m\n");
            for (TrafficSignal signal : trafficSignals) {
                signal.showTrafficSignal();
            }

            System.out.println("\n\033[1;4mIntersection status at completion (V = current position):\033[0m\n");
            for (IntersectionNetwork network : intersectionNetworks) {
            network.showIntersectionStatus();
            }

        }
    }

}