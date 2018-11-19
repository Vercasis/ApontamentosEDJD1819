package ipca.edjd.apontamentos.models;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Apontamento extends RealmObject {

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

    public static void add(final Apontamento apontamento, Realm realm){
        RealmResults<Apontamento> apontamentos = realm.where(Apontamento.class)
                .equalTo("id", apontamento.id)
                .findAll();
        if (apontamentos.size()==0){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    Apontamento item = realm.createObject(Apontamento.class);
                    item.id = apontamento.id;
                    item.titulo     =   apontamento.titulo    ;
                    item.descricao  =   apontamento.descricao ;
                    item.date       =   apontamento.date      ;
                    item.uriPhoto   =   apontamento.uriPhoto  ;
                    //item.uc         =   apontamento.uc        ;
                }
            });
        }
    }

    public static void update(final Apontamento apontamento, Realm realm){
        final RealmResults<Apontamento> apontamentos = realm.where(Apontamento.class)
                .equalTo("id", apontamento.id)
                .findAll();
        if (apontamentos.size()>0){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    apontamentos.first().titulo     =   apontamento.titulo    ;
                    apontamentos.first().descricao  =   apontamento.descricao ;
                    apontamentos.first().date       =   apontamento.date      ;
                    apontamentos.first().uriPhoto   =   apontamento.uriPhoto  ;
                    apontamentos.first().uc         =   apontamento.uc        ;
                }
            });
        }
    }

    public static void delete(final Apontamento apontamento, Realm realm){
        final RealmResults<Apontamento> apontamentos = realm.where(Apontamento.class)
                .equalTo("id", apontamento.id)
                .findAll();
        if (apontamentos.size()>0){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    apontamentos.first().deleteFromRealm();
                }
            });
        }
    }
}
