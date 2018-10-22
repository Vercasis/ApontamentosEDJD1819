package ipca.edjd.apontamentos.models;

import io.realm.RealmObject;

class Uc extends RealmObject {

    long   id;
    String nome;
    String docente;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public Uc() {
        this.id      = 0;
        this.nome    = "";
        this.docente = "";
    }

    public Uc(long id, String nome, String docente) {
        this.id      = id;
        this.nome    = nome;
        this.docente = docente;
    }


}
