package org.example.entityEnum;

public enum CarMarkEnum {
    BMW("bmw"),
    KIA("kia"),
    MERCEDES("mercedes"),
    NOT_FOUND("Not found");


    private final String title;

    CarMarkEnum(String title) {
        this.title = title;
    }


    public static CarMarkEnum fromValue(String value) {
        for (CarMarkEnum carMarkEnum : values()) {
            if (carMarkEnum.title.equalsIgnoreCase(value)) {
                return carMarkEnum;
            }
        }
        return NOT_FOUND;
    }
}
