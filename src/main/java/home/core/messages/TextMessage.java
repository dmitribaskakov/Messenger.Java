package home.core.messages;

import java.util.Objects;

/**
 * Текстовое сообщение
 */
public class TextMessage extends Message {
    private String text;

    public String getText() { return text; }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        TextMessage message = (TextMessage) other;
        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
