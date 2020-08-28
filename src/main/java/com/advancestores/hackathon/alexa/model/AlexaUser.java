package com.advancestores.hackathon.alexa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("AlexaUser")
public class AlexaUser {
    @Id
    private String id;

    @Indexed(name = "alexaUserId")
    private String alexaUserId;
    private String speedPerksMemberId;

    @Indexed(name = "speedPerksPhone")
    private String speedPerksPhone;
}