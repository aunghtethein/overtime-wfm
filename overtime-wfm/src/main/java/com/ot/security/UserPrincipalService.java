package com.ot.security;

import com.ot.model.Staff;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Service
public class UserPrincipalService implements UserDetailsService {
	
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserDetails loadUserByUsername(String staffId) throws UsernameNotFoundException {
        try {
            Staff staff = entityManager
                    .createQuery("SELECT s FROM Staff s LEFT JOIN FETCH s.roles r LEFT JOIN FETCH r.privileges p WHERE s.staffId = :staffId", Staff.class)
                    .setParameter("staffId", staffId)
                    .getResultList().get(0);
            return new UserPrincipal(staff);
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
