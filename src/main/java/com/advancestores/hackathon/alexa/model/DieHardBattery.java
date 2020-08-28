package com.advancestores.hackathon.alexa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("DieHardBattery")
public class DieHardBattery {

    @Id
    private String id;

    @Indexed(name = "batteryId")
    private String make;
    private String model;
    private String year;

    private String batteryOffer;
}
