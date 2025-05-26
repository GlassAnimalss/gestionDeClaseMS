package edutech.gestionDeClaseMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edutech.gestionDeClaseMS.model.Clase;
import edutech.gestionDeClaseMS.model.Material;
import edutech.gestionDeClaseMS.model.TipoMaterial;
import edutech.gestionDeClaseMS.repository.ClaseRepository;
import edutech.gestionDeClaseMS.repository.MaterialRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ClaseRepository claseRepository; 

    @Autowired
    public MaterialService(MaterialRepository materialRepository, ClaseRepository claseRepository) {
        this.materialRepository = materialRepository;
        this.claseRepository = claseRepository;
    }

    @Transactional
    public Material crearMaterial(Material material, Long claseId) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + claseId));

        material.setClase(clase);
        material.setFechaCreacion(LocalDateTime.now());
       
        if (material.getTitulo() == null || material.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del material no puede estar vacío.");
        }
        return materialRepository.save(material);
    }

    @Transactional
    public Material actualizarMaterial(Long id, Material materialActualizado) {
        return materialRepository.findById(id)
                .map(materialExistente -> {
                    materialExistente.setTitulo(materialActualizado.getTitulo());
                    materialExistente.setDescripcion(materialActualizado.getDescripcion());
                    materialExistente.setTipoMaterial(materialActualizado.getTipoMaterial());
                   
                    return materialRepository.save(materialExistente);
                }).orElseThrow(() -> new RuntimeException("Material no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new RuntimeException("Material no encontrado con ID: " + id);
        }
        materialRepository.deleteById(id);
    }

    public Optional<Material> obtenerMaterialPorId(Long id) {
        return materialRepository.findById(id);
    }

    public List<Material> listarTodosLosMateriales() {
        return materialRepository.findAll();
    }

    public List<Material> listarMaterialesPorClase(Long claseId) {
        return materialRepository.findByClaseId(claseId);
    }

    public List<Material> listarMaterialesPorTipo(TipoMaterial tipoMaterial) {
        return materialRepository.findByTipoMaterial(tipoMaterial);
    }
}
