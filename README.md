# Urban Traffic Simulation System

A Java-based OOP project that simulates urban traffic with vehicles, traffic signals, and intersections.

## Classes

- **Vehicle.java**: Abstract base class
- **Car.java, Bus.java, Truck.java**: Vehicle subclasses
- **TrafficSignal.java**: Manages traffic signal states
- **IntersectionNetwork.java**: Models intersections and roads
- **SimulationException.java**: Custom exception class
- **Main.java**: Menu-driven interface

## How to Run

1. Compile: `javac *.java`
2. Run: `java Main`

## Features

- Vehicle movement with lane changes
- Traffic signal state management
- Intersection modeling
- Error handling with custom exceptions
- User-friendly menu interface

## Project Structure

The project consists of the following classes:

1. **Vehicle Class Hierarchy**:
   - `Vehicle.java`: Abstract base class with attributes like vehicleID, velocity, lane, and currentPosition.
   - `Car.java`: Subclass of Vehicle with specific behavior (speed: 100).
   - `Bus.java`: Subclass of Vehicle with specific behavior (speed: 80).
   - `Truck.java`: Subclass of Vehicle with specific behavior (speed: 90).

2. **Traffic Signal Class**:
   - `TrafficSignal.java`: Manages traffic signal states (red, yellow, green) and timers.

3. **Intersection/Road Network**:
   - `IntersectionNetwork.java`: Models intersections and roads where vehicles interact.

4. **Exception Handling**:
   - `SimulationException.java`: Custom exception class for simulation-specific errors.

5. **Main Class**:
   - `Main.java`: Menu-driven interface to control the simulation.

## Simulation Details

- Each simulation step represents 5 seconds of real time
- 12 steps make up 1 minute of simulation time
- Vehicles change lanes and update their positions based on their velocity
- Traffic signals cycle through red, yellow, and green states
- Vehicles move through intersections based on predefined patterns

## Error Handling

The simulation includes robust error handling for:
- Invalid lane transitions
- Invalid traffic signal state transitions
- Attempts to move through non-existent intersections
- Invalid user inputs

## Requirements

- Java Development Kit (JDK) 8 or higher 