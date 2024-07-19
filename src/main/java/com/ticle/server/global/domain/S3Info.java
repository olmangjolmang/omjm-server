package com.ticle.server.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class S3Info {

    @Column(name = "image_file_name", length = 100)
    String imageFileName;

    @Column(name = "image_folder_name", length = 50)
    String imageFolderName;

    @Column(name = "image_url")
    String imageUrl;

    @Builder
    public S3Info(String imageFileName, String imageFolderName, String imageUrl) {
        this.imageFileName = imageFileName;
        this.imageFolderName = imageFolderName;
        this.imageUrl = imageUrl;
    }
}