
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {

    private int id;
    private int pacienteId;
    private int fisioterapeutaId;
    private LocalDate dataSessao;
    private LocalTime horario;
    private String status;
    private String tipo;

    public Agendamento(int pacienteId, int fisioterapeutaId,
                       LocalDate dataSessao, LocalTime horario) {

        this.pacienteId = pacienteId;
        this.fisioterapeutaId = fisioterapeutaId;
        this.dataSessao = dataSessao;
        this.horario = horario;
        this.status = "AGENDADO";
        this.tipo = "SESSAO";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getFisioterapeutaId() {
        return fisioterapeutaId;
    }

    public void setFisioterapeutaId(int fisioterapeutaId) {
        this.fisioterapeutaId = fisioterapeutaId;
    }

    public LocalDate getDataSessao() {
        return dataSessao;
    }

    public void setDataSessao(LocalDate dataSessao) {
        this.dataSessao = dataSessao;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
