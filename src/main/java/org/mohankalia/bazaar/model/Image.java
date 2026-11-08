package org.mohankalia.bazaar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity // This will set this class as a database table
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob // an annotation by JPA to notify that this is a large binary object
    private Blob image; // These are large binary objects in the database such as images, videos etc
    private String downloadUrl;

    @ManyToOne // because one product can have many images!
    @JoinColumn(name = "product_id")
    private Product product;

}
