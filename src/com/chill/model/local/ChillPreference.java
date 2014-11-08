package com.chill.model.local;

import lombok.Data;

@Data
public class ChillPreference {
    private final Chill chill;
    private final String location;
    private final String date;
}
