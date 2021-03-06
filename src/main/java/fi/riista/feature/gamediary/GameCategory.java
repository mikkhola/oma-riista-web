package fi.riista.feature.gamediary;

import fi.riista.feature.common.entity.HasOfficialCode;

public enum GameCategory implements HasOfficialCode {

    FOWL(1),
    GAME_MAMMAL(2),
    UNPROTECTED(3);

    private final int code;

    private GameCategory(int code) {
        this.code = code;
    }

    @Override
    public int getOfficialCode() {
        return code;
    }

}
