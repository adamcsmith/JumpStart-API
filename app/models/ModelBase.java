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

    @Temporal(TemporalType.TIMESTAMP)
    public Date created;

    @Temporal(TemporalType.TIMESTAMP)
    public Date updated;
}
