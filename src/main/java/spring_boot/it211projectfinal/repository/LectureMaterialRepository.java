package spring_boot.it211projectfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot.it211projectfinal.model.entity.LectureMaterial;

@Repository
public interface LectureMaterialRepository extends JpaRepository<LectureMaterial,Long> {
}
