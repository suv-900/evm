package com.project.host.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.host.models.HostEntity;

@Repository
public class HostDao {
    public final static Logger log = LoggerFactory.getLogger(HostDao.class);
    
    @Autowired
    private SessionFactory sessionFactory;

    public void addHost(HostEntity host)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            session.persist("host",host);

            tx.commit();

        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            if(session.isOpen()){
                session.close();
            }
            throw e;
        }
    }

    public boolean exists(HostEntity host)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        String query = "SELECT EXISTS(SELECT 1 FROM hosts WHERE name = :username OR email = :email) AS host_exists";

        Boolean exists = session.createQuery(query,Boolean.class)
            .setParameter("username",host.getUsername())
            .setParameter("email",host.getEmail())
            .getSingleResult();

        return exists;
    }

    public HostEntity findById(int hostID)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        String query = "SELECT name,email,phone_number,description,events_created FROM hosts WHERE id = :id";

        HostEntity host = session.createQuery(query,HostEntity.class)
            .setParameter("id",hostID)
            .uniqueResult();
        
        return host;
    }

    public String getPasswordByUsername(String username)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        String query = "SELECT password FROM hosts WHERE username = :username";

        String password = session.createQuery(query,String.class)
            .setParameter("username",username)
            .uniqueResult();
        
        return password;
    }

    public void deleteHost(HostEntity host)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            session.remove(host);
            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            if(session.isOpen()){
                session.close();
            }
            throw e;
        }
    }

    //add
    public HostEntity updateHost(HostEntity host)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;
        HostEntity updatedHost = null;
        
        try{
            tx = session.beginTransaction();
            updatedHost = session.merge("hosts",host);
            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            if(session.isOpen()){
                session.close();
            }
            throw e;
        }
        
        return updatedHost;
    }

    public List<HostEntity> findAllById(Iterable<Integer> idList)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();

        String query = "SELECT name,email FROM hosts WHERE id = :id";
        List<HostEntity> list = new LinkedList<>();

        idList.forEach((id)->{
            HostEntity host = session.createQuery(query,HostEntity.class).setParameter("id",id).uniqueResult();
            list.add(host);
        });

        return list;
    }
    
}
