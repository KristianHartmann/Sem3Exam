package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Cityinfo;
import entities.House;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CityInfoFacade {
    private static EntityManagerFactory emf;
    private static CityInfoFacade instance;
    EntityManager em = emf.createEntityManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private CityInfoFacade() {
    }

    public static CityInfoFacade getCityinfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }
    public Cityinfo getCityInfo(Integer cityInfoID) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Cityinfo Cityinfo;
        try {
            Cityinfo = em.find(Cityinfo.class, cityInfoID);
            if (Cityinfo == null) {
                throw new AuthenticationException("CityInfo Doesn't Exist");
            }
        } finally {
            em.close();
        }
        return Cityinfo;
    }


}
