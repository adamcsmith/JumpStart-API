package models;

import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created with IntelliJ IDEA for jumpstart
 * User: phutchinson
 * Date: 3/18/13
 * Time: 4:43 PM
 */
@MappedSuperclass
public abstract class ModelBase extends Model {

    @Id
    public Long id;

    @Temporal(TemporalType.DATE)
    public Date created;

    @Temporal(TemporalType.DATE)
    public Date updated;

//    public ModelBase() {
//        this.id = Long.parseLong(UUID.randomUUID().toString());
//    }

//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof ModelBase)) {
            return false;
        }

        ModelBase other = (ModelBase) obj;
        return getId().equals(other.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public static User findByID(Long id) {

        User user = User.find.byId(id);
        return user;
    }

}
