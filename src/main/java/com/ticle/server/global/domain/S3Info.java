package com.ticle.server.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class S3Info {

    @Column(name = "image_file_name", length = 100)
    String imageFileName;

    @Column(name = "image_folder_name", length = 50)
    String imageFolderName;

    @Column(name = "image_url")
    String imageUrl;
}