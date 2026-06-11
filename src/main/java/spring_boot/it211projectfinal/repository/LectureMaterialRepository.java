package spring_boot.it211projectfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot.it211projectfinal.model.entity.LectureMaterial;

public interface LectureMaterialRepository extends JpaRepository<LectureMaterial,Long> {
}
