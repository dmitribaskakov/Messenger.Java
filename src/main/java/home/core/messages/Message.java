package home.core.messages;

import java.util.Objects;

/**
 * Базовый класс для всех сообщений
 */
public abstract class Message {
    private Long senderId;
    private TypeOfMessage type;

//    private Long id;
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public TypeOfMessage getType() {
        return type;
    }

    public void setType(TypeOfMessage type) { this.type = type; }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != getClass()) return false;
        Message message = (Message) other;
        return type == message.type &&
                Objects.equals(senderId, message.senderId);
//                Objects.equals(id, message.id) &&
    }

    @Override
    public int hashCode() {
//        return Objects.hash(id, type, senderId);
        return Objects.hash(type, senderId);
    }

    @Override
    public String toString() {
        return "Message{" +
//                "id=" + id +
                ", senderId=" + senderId +
                ", type=" + type +
                '}';
    }
}
