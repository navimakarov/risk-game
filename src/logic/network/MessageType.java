package logic.network;

public enum MessageType {
    CLOSE_CONNECTION_BY_CLIENT,
    NAME_ERROR,
    OK,
    USERNAME,
    MAX_PLAYERS_ERROR,
    PLAYERS,
    COLOR_CHANGED,
    BOT_ADDED,
    CONNECTION_CLOSED_BY_ADMIN,
    PLAYER_DELETED,
    INVALID_NAME,
    START_GAME,
    REINFORCE,
    ATTACK,
    FORTIFY,
    END_ATTACK,
    END_REINFORCE,
    END_FORTIFY,
    SKIP_MOVE,
    GAME_OVER,
    USER_LEFT,
    CONNECTION_ERROR,
    SERVER_CLOSED_ERROR
}
