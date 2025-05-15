package trafficsignal;
public class TrafficSignal {
    private String signalID;
    private String[] currentState;
    private int[] timer;
    private int currentStateIndex;
    
    public TrafficSignal(String signalID, int redTimer, int yellowTimer, int greenTimer) {
        this.signalID = signalID;
        this.currentState = new String[]{"red", "yellow", "green"};
        this.timer = new int[]{redTimer, yellowTimer, greenTimer};
        this.currentStateIndex = 0; // Start with red
    }
    
    public TrafficSignal(String signalID) {
        this(signalID, 10, 2, 15); // Default timers
    }
    
    public void signal() {
        try {
            currentStateIndex = (currentStateIndex + 1) % currentState.length;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SimulationException("Invalid traffic signal state transition", e);
        }
    }
    
    public String getCurrentState() {
        return currentState[currentStateIndex];
    }
    
    public int getCurrentTimer() {
        return timer[currentStateIndex];
    }
    
    public String getSignalID() {
        return signalID;
    }
    
    public void showTrafficSignal() {
        System.out.println("Signal ID: " + signalID + 
                          ", Current State: " + getCurrentState() + 
                          ", Timer: " + getCurrentTimer() + " seconds");
    }
    
    // Main method for testing
    public static void main(String[] args) {
        TrafficSignal signal = new TrafficSignal("TS001");
        
        System.out.println("Initial state:");
        signal.showTrafficSignal();
        
        for (int i = 0; i < 3; i++) {
            signal.signal();
            System.out.println("\nAfter signal change " + (i+1) + ":");
            signal.showTrafficSignal();
        }
        
        // Demonstrating error handling
        try {
            // This will cause an error since we've already cycled through all states
            // and trying to access a fourth non-existent state
            signal.currentStateIndex = 3; // Force invalid index
            signal.showTrafficSignal();
        } catch (SimulationException e) {
            System.out.println("\nCaught exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error: " + e.getMessage());
        }
    }
} 