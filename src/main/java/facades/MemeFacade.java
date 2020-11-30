package facades;

import javax.persistence.EntityManagerFactory;

public class MemeFacade {
    
    private static EntityManagerFactory emf;
    private static MemeFacade instance;
    
    public static MemeFacade getMemeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MemeFacade();
        }
        return instance;
    }

    
}
