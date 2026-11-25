package cl.rinconsur.registros.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "registros")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String acceso;

    @Column(nullable = false, length = 30)
    private String clasificacion;

    @Column(name = "rango_etario", nullable = false, length = 30)
    private String rangoEtario;

    @Column(name = "tipo_compania", nullable = false, length = 30)
    private String tipoCompania;

    @Column(name = "compras_otra_tienda", nullable = false)
    private boolean comprasOtraTienda;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false, length = 150)
    private String observador;

    public Registro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getRangoEtario() {
        return rangoEtario;
    }

    public void setRangoEtario(String rangoEtario) {
        this.rangoEtario = rangoEtario;
    }

    public String getTipoCompania() {
        return tipoCompania;
    }

    public void setTipoCompania(String tipoCompania) {
        this.tipoCompania = tipoCompania;
    }

    public boolean isComprasOtraTienda() {
        return comprasOtraTienda;
    }

    public void setComprasOtraTienda(boolean comprasOtraTienda) {
        this.comprasOtraTienda = comprasOtraTienda;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getObservador() {
        return observador;
    }

    public void setObservador(String observador) {
        this.observador = observador;
    }
}