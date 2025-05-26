package edutech.gestionDeClaseMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edutech.gestionDeClaseMS.model.Valoracion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    List<Valoracion> findByClaseId(Long claseId); 
    List<Valoracion> findByIdUsuario(Long idUsuario); 

    
    @Query("SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.clase.id = :claseId")
    Optional<Double> findAveragePuntuacionByClaseId(Long claseId);
}
