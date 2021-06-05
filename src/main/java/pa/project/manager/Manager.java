package pa.project.manager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Manager {
    private static EntityManagerFactory factory = null;

    private Manager() {
        factory = Persistence.createEntityManagerFactory("SpanzuratoareaPU");
    }

    public static EntityManagerFactory getFactory() {
        if (factory == null) {
            new Manager();
        }
        return factory;
    }
}
