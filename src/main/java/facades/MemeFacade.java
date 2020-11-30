package facades;

import dto.MemeDTO;
import java.util.List;
import javax.persistence.EntityManager;
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
    
    public List<MemeDTO> getFavouriteMemes(String userName) {
        EntityManager em = emf.createEntityManager();
        List<MemeDTO> favourites;
       
        return favourites;
    }
    


    
}
