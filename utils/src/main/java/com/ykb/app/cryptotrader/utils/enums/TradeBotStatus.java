package com.ykb.app.cryptotrader.utils.enums;

public enum TradeBotStatus {
    INIT,
    RUNNING,
    ENTERING_LONG,
    IN_LONG,
    EXITING_LONG,
    ENTERING_SHORT,
    IN_SHORT,
    EXITING_SHORT,
    TERMINATED;

    public static boolean isRunning(TradeBotStatus status) {
        return TERMINATED!=status && INIT!=status;
    }

}
