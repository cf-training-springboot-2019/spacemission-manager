package com.springboot.training.spaceover.spacemission.manager.enums;

public enum SpaceMissionStatus {

    IN_PROGRESS, FAILED, ABORTED, ACCOMPLISHED, NOT_IN_PROGRESS;

    public static SpaceMissionStatus fromName(String name) {
        for (SpaceMissionStatus e : values()) {
            if (e.name().equals(name))
                return e;
        }
        return null;
    }
}
