package ipca.edjd.apontamentos.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Uc extends RealmObject {

    @PrimaryKey
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

    public static void add(final Uc uc, Realm realm){
        RealmResults<Uc> ucs = realm.where(Uc.class)
                .equalTo("nome", uc.nome)
                .findAll();
        if (ucs.size()==0){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    Uc item = realm.createObject(Uc.class,System.currentTimeMillis());
                    item.docente = uc.docente;
                    item.nome = uc.nome;
                }
            });
        }
    }

    public static void update(final Uc uc, Realm realm){
        final RealmResults<Uc> ucs = realm.where(Uc.class)
                .equalTo("id", uc.id)
                .findAll();
        if (ucs.size()>0){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    ucs.first().docente = uc.docente;
                    ucs.first().nome = uc.nome;
                }
            });
        }
    }

    public static void delete(final Uc uc, Realm realm){
        final RealmResults<Uc> ucs = realm.where(Uc.class)
                .equalTo("id", uc.id)
                .findAll();
        if (ucs.size()>0){
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    ucs.first().deleteFromRealm();
                }
            });
        }
    }




}
