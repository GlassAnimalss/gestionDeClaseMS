package edutech.gestionDeClaseMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edutech.gestionDeClaseMS.model.Valoracion;
import edutech.gestionDeClaseMS.service.ValoracionService;

import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    @Autowired
    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @PostMapping("/clase/{claseId}")
    public ResponseEntity<Valoracion> crearValoracion(@PathVariable Long claseId, @RequestBody Valoracion valoracion) {
        try {
            Valoracion nuevaValoracion = valoracionService.crearValoracion(valoracion, claseId);
            return new ResponseEntity<>(nuevaValoracion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Valoracion> obtenerValoracionPorId(@PathVariable Long id) {
        return valoracionService.obtenerValoracionPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Valoracion>> listarTodasLasValoraciones() {
        List<Valoracion> valoraciones = valoracionService.listarTodasLasValoraciones();
        return ResponseEntity.ok(valoraciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Valoracion> actualizarValoracion(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        try {
            Valoracion valoracionActualizada = valoracionService.actualizarValoracion(id, valoracion);
            return ResponseEntity.ok(valoracionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarValoracion(@PathVariable Long id) {
        try {
            valoracionService.eliminarValoracion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/por-clase/{claseId}")
    public ResponseEntity<List<Valoracion>> listarValoracionesPorClase(@PathVariable Long claseId) {
        List<Valoracion> valoraciones = valoracionService.listarValoracionesPorClase(claseId);
        return ResponseEntity.ok(valoraciones);
    }

    @GetMapping("/por-usuario/{idUsuario}")
    public ResponseEntity<List<Valoracion>> listarValoracionesPorUsuario(@PathVariable Long idUsuario) {
        List<Valoracion> valoraciones = valoracionService.listarValoracionesPorUsuario(idUsuario);
        return ResponseEntity.ok(valoraciones);
    }

    @GetMapping("/promedio-por-clase/{claseId}")
    public ResponseEntity<Double> obtenerPromedioPuntuacionPorClase(@PathVariable Long claseId) {
        Double promedio = valoracionService.obtenerPromedioPuntuacionPorClase(claseId);
        return ResponseEntity.ok(promedio);
    }
}
