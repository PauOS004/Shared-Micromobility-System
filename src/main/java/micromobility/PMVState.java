package micromobility;

/**
 * Enumeration representing the possible states of a personal mobility vehicle (PMV).
 */
public enum PMVState {
    /** The vehicle is available for use. */
    Available,
    
    /** The vehicle is not available for use. */
    NotAvailable,
    
    /** The vehicle is currently being used. */
    UnderWay,
    
    /** The vehicle is temporarily parked. */
    TemporaryParking
}