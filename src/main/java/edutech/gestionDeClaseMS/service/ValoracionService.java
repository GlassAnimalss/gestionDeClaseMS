package edutech.gestionDeClaseMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edutech.gestionDeClaseMS.model.Clase;
import edutech.gestionDeClaseMS.model.Valoracion;
import edutech.gestionDeClaseMS.repository.ClaseRepository;
import edutech.gestionDeClaseMS.repository.ValoracionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final ClaseRepository claseRepository; 

    @Autowired
    public ValoracionService(ValoracionRepository valoracionRepository, ClaseRepository claseRepository) {
        this.valoracionRepository = valoracionRepository;
        this.claseRepository = claseRepository;
    }

    @Transactional
    public Valoracion crearValoracion(Valoracion valoracion, Long claseId) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + claseId));

        valoracion.setClase(clase);
        valoracion.setFechaCreacion(LocalDateTime.now());
        if (valoracion.getPuntuacion() < 1 || valoracion.getPuntuacion() > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }
        if (valoracion.getIdUsuario() == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo.");
        }
        return valoracionRepository.save(valoracion);
    }

    @Transactional
    public Valoracion actualizarValoracion(Long id, Valoracion valoracionActualizada) {
        return valoracionRepository.findById(id)
                .map(valoracionExistente -> {
                    valoracionExistente.setPuntuacion(valoracionActualizada.getPuntuacion());
                    valoracionExistente.setComentario(valoracionActualizada.getComentario());
                   
                    if (valoracionExistente.getPuntuacion() < 1 || valoracionExistente.getPuntuacion() > 5) {
                        throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
                    }
                    return valoracionRepository.save(valoracionExistente);
                }).orElseThrow(() -> new RuntimeException("Valoración no encontrada con ID: " + id));
    }

    @Transactional
    public void eliminarValoracion(Long id) {
        if (!valoracionRepository.existsById(id)) {
            throw new RuntimeException("Valoración no encontrada con ID: " + id);
        }
        valoracionRepository.deleteById(id);
    }

    public Optional<Valoracion> obtenerValoracionPorId(Long id) {
        return valoracionRepository.findById(id);
    }

    public List<Valoracion> listarTodasLasValoraciones() {
        return valoracionRepository.findAll();
    }

    public List<Valoracion> listarValoracionesPorClase(Long claseId) {
        return valoracionRepository.findByClaseId(claseId);
    }

    public List<Valoracion> listarValoracionesPorUsuario(Long idUsuario) {
        return valoracionRepository.findByIdUsuario(idUsuario);
    }

    public Double obtenerPromedioPuntuacionPorClase(Long claseId) {
        
        return valoracionRepository.findAveragePuntuacionByClaseId(claseId)
                .orElse(0.0); 
    }
}