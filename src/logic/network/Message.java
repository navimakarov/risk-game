package logic.network;

import logic.Game;
import logic.Player;

import java.io.Serializable;
import java.util.Collection;

public class Message implements Serializable {
    public final MessageType type;

    public String msg;
    public Collection<Player> players;
    public Game game;



    Message(MessageType type, String username) throws Exception {
        if (type != MessageType.USERNAME) {
            throw new Exception("Message type exception"); // todo unique exception
        }
        this.type = type;
        this.msg = username;
    }

    Message(MessageType type) {
        this.type = type;
    }

    Message(MessageType type, Collection<Player> players) throws Exception {
        if (type != MessageType.PLAYERS) {
            throw new Exception("Message type exception"); // todo unique exception
        }
        this.type = type;
        this.players = players;
        this.game = game;
    }

    public String toString() {
        String str = "Message[type= " + type;
        if (msg != null) {
            str += ", msg = "  + msg;
        }
        if (players != null) {
            str += ", players= " + players;
        }
        if (game != null) {
            str += ", game= " + game;
        }
        str += "]";
        return str;
    }
}
