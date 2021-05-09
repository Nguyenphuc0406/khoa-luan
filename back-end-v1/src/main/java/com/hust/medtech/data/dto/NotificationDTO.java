package com.hust.medtech.data.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class NotificationDTO {
    private String title;
    private String content;
    private String link;
    private String srcUrl;

}
