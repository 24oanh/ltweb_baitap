package baitap4.Dao.Impl;

import baitap4.Dao.UserDao;
import baitap4.Entity.AppUser;
import jakarta.persistence.EntityManager;

public class UserDaoImpl implements UserDao {
    private EntityManager em;

    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void saveUser(AppUser user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public AppUser getUserById(int id) {
        return em.find(AppUser.class, id);
    }

    @Override
    public void updateUser(AppUser user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    @Override
    public void deleteUser(int id) {
        AppUser user = getUserById(id);
        if (user != null) {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }
    }
}