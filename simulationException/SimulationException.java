/*
 * Package: SimulationException
 * Author: Michael Thomas
 * SID: 530049902
 * Description: This class extends RunTimeException.
 * 
 * Reference:
 * https://www.baeldung.com/java-new-custom-exception
 * 
 */

package simulationException;

public class SimulationException extends RuntimeException {
    public SimulationException(String errorMessage) {
        super(errorMessage);
    }
    
    public SimulationException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    @Override
    public String toString() {
        return getMessage();
    }

} 