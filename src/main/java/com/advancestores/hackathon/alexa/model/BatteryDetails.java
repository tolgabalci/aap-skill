package com.advancestores.hackathon.alexa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("BatteryDetails")
public class BatteryDetails {
    @Id
    private String id;

    @Indexed(name = "makeId")
    private String makeId;
    private String modelId;
    private String yearId;

    @Indexed(name = "offer")
    private String offer;
}