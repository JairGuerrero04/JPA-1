
package Utilidades;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author jairg
 */
public class Utilidad {
    
    public final static EntityManager em = Persistence.createEntityManagerFactory("JPA_1PU1").createEntityManager();
}
