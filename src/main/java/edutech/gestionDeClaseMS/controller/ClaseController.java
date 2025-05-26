package edutech.gestionDeClaseMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edutech.gestionDeClaseMS.model.Clase;
import edutech.gestionDeClaseMS.service.ClaseService;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    private final ClaseService claseService;

    @Autowired
    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @PostMapping
    public ResponseEntity<Clase> crearClase(@RequestBody Clase clase) {
        try {
            Clase nuevaClase = claseService.crearClase(clase);
            return new ResponseEntity<>(nuevaClase, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); 
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> obtenerClasePorId(@PathVariable Long id) {
        return claseService.obtenerClasePorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Clase>> listarTodasLasClases() {
        List<Clase> clases = claseService.listarTodasLasClases();
        return ResponseEntity.ok(clases);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clase> actualizarClase(@PathVariable Long id, @RequestBody Clase clase) {
        try {
            Clase claseActualizada = claseService.actualizarClase(id, clase);
            return ResponseEntity.ok(claseActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        try {
            claseService.eliminarClase(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/por-curso/{idCurso}")
    public ResponseEntity<List<Clase>> listarClasesPorCurso(@PathVariable Long idCurso) {
        List<Clase> clases = claseService.listarClasesPorCurso(idCurso);
        return ResponseEntity.ok(clases);
    }
}
