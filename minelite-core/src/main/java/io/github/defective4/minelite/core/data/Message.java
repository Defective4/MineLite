package io.github.defective4.minelite.core.data;

/**
 * This object can contain a message in the JSON format. <br>
 * It also has a method used to parse it to user readable format.
 * 
 * @author Defective4
 *
 */
public class Message {
    private final String json;

    /**
     * Default constructor
     * 
     * @param json
     */
    public Message(final String json) {
        this.json = json;
    }

    /**
     * Get text contained in this message.
     * 
     * @return text in JSON format.
     */
    public String getJson() {
        return json;
    }

    /**
     * Get parsed, user readable text.
     * 
     * @return parsed text message
     */
    public String getText() {
        return Messages.parse(json);
    }

    @Override
    public String toString() {
        return getText();
    }
}
