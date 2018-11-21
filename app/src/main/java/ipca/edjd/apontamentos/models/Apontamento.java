package ipca.edjd.apontamentos.models;

import java.util.Date;
import java.util.UUID;


public class Apontamento  {

    String  id;
    String  titulo;
    String  descricao;
    Date    date;
    String  uriPhoto;
    Uc      uc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public Uc getUc() {
        return uc;
    }

    public void setUc(Uc uc) {
        this.uc = uc;
    }

    public Apontamento() {
        this.id          = UUID.randomUUID().toString();
        this.titulo      = "";
        this.descricao   = "";
        this.date        = new Date();
        this.uriPhoto    = "";
        this.uc          = new Uc();
    }

    public Apontamento(String id, String titulo, String descricao, Date date, String uriPhoto, Uc uc) {
        this.id         = id;
        this.titulo     = titulo;
        this.descricao  = descricao;
        this.date       = date;
        this.uriPhoto   = uriPhoto;
        this.uc         = uc;
    }


}
