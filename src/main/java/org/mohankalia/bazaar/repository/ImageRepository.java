package org.mohankalia.bazaar.repository;

import org.mohankalia.bazaar.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
