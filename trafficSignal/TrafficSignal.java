/*
 * Package: TrafficSignal
 * Author: Michael Thomas
 * SID: 530049902
 * Description: This class defines a 'Traffic Signal' for use in an Urban Traffic Simulation.
 *  
 * Each TrafficSignal has the following private attributes:
 *          
 *          signalId (String):          A unique identifier for the TrafficSignal
 *          timer (int array):          An array of integers representing the duration for each signal
 *          currentState (String):      The current state of the signal (red, yellow or green) 
 *          stateIndex (int):           An int corresponding to the signal's current state
 *          signalComplete (boolean):   Defines whether the signal has completed its cycle
 * 
 * And the following methods:
 * 
 *          showTrafficSignal()         Displays the current state of the traffic signals
 *          getID()                     Returns the signalId
 *          getCurrentState()           Returns the currentState
 *          setTimer()                  Sets the timer array
 *          signal()                    Changes the current signal to the next value in the array
 * 
 */

package trafficSignal;

import simulationException.SimulationException;

public class TrafficSignal {

    // allowed states
    private static final String[] STATES = new String[] { "Red", "Yellow", "Green" };

    // instance attributes
    private int signalId;
    private int[] timer = new int[3];
    private String currentState;
    private int stateIndex = 0;
    private boolean signalComplete = false;

    //////// CONSTRUCTORS
    // default constructor
    public TrafficSignal(int id) {
        // set timers
        this.timer[0] = 10;
        this.timer[1] = 2;
        this.timer[2] = 15;

        // set the signalId
        this.signalId = id;
    }

    // overloaded constructor

    public TrafficSignal(int id, int[] timer) {
        // set timers
        this.timer = timer.clone();

        // set the signalId
        this.signalId = id;
    }

    //////// STATIC METHODS
    /**
     * Displays the traffic status of the TrafficSignal
     */
    public void showTrafficSignal() {
        System.out.println(this.toString());
    }

    //////// GETTERS
    public String getId() {
        // return the signalId
        return Integer.toString(this.signalId);
    }

    public String getCurrentState() {
        // return the current state

        if (currentState != null) {
            String formatString = "";
            String escape = "\033[0m";

            if (currentState.equals("Red")) {
                formatString = "\033[31m";
            } else if (currentState.equals("Yellow")) {
                formatString = "\033[33m";
            } else {
                formatString = "\033[32m";
            }

            return formatString + currentState + escape;
        } else {
            return "Off";
        }
    }

    //////// SETTERS
    /**
     * Sets the timer for a TrafficSignal
     * 
     * @param newTimer - int array of new times
     */
    public void setTimer(int newTimer[]) {
        // change the timers

        this.timer = newTimer.clone();
    }

    //////// METHODS

    /**
     * Changes the currentState of a signal
     * 
     * @param timeElapsed - int value of time elapsed in the simulation
     */
    public void signal(int timeElapsed) {

        // ensures that continuous SimulationExceptions are not output
        if (!signalComplete) {

            // cumulative signal change times
            int[] signalTimes = new int[] { (timer[0]), (timer[0] + timer[1]), (timer[0] + timer[1] + timer[2]) };

            try {
                for (int i = stateIndex; i < signalTimes.length; i++) {
                    if (timeElapsed >= signalTimes[i]) {
                        String previousState = getCurrentState();
                        this.currentState = STATES[stateIndex];

                        System.out.println(
                                "Traffic signal #" + getId() + " changed from \033[1m" + previousState
                                        + "\033[0m to \033[1m" + getCurrentState() + "\033[0m after "
                                        + signalTimes[stateIndex] + " seconds.");
                        stateIndex++;
                        return;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                signalComplete = true;
                throw new SimulationException("SimulationException: \033[31mTraffic Signal #" + getId()
                        + " cannot be changed more than 3 times!\033[0m");
            }

        }

    }

    //////// STRING
    @Override
    public String toString() {
        return "Traffic Signal #" + getId() + ": " + getCurrentState();
    }

}