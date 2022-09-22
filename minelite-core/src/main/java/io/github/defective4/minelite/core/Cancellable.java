package io.github.defective4.minelite.core;

/**
 * Represents an object, which execution can be canceled
 * 
 * @author Defective4
 *
 */
public interface Cancellable {

    /**
     * Set canceled state of an object
     * 
     * @param canceled new canceled state
     */
    public void setCanceled(boolean canceled);

    /**
     * Get current canceled state of this object
     * 
     * @return current canceled state
     */
    public boolean isCanceled();
}
