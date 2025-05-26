package edutech.gestionDeClaseMS.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private Long idUsuario; 

    @Column(nullable = false)
    private Integer puntuacion; 

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase; 
}
