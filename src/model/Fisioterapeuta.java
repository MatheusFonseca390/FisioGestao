
package model;

public class Fisioterapeuta {

    private int id;
    private String nome;
    private String crefito;
    private String especialidade;

    public Fisioterapeuta(String nome, String crefito,
                          String especialidade) {
        this.nome = nome;
        this.crefito = crefito;
        this.especialidade = especialidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrefito() {
        return crefito;
    }

    public void setCrefito(String crefito) {
        this.crefito = crefito;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
