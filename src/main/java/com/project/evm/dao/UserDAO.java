package com.project.evm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.evm.models.User;

@Repository
public class UserDAO {
    
    private final static Logger log = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public User addUser(User user){
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;
        
        try{
            tx = session.beginTransaction();

            session.persist("user",user);

            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            session.close();
            throw e;
        }finally{
            session.close();
        }

        log.info("User added: "+user.getName());
        return user;
    }

    public User getUserById(int userID){
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = null;
        User user = null;

        try{
            transaction = session.beginTransaction();

            user = session.get(User.class,userID);

            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            session.close();
            throw e;
        }finally{
            session.close();
        }
        
        return user; 
    }
    
    public String getPasswordByUsername(String name)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = null;
        String dbPassword = null;

        try{
            String query = "select password from users where name = :name";
            
            transaction = session.beginTransaction();

            dbPassword = session.createQuery(query,String.class).uniqueResult();

            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            session.close();
            throw e;
        }finally{
            session.close();
        }
        
        return dbPassword;
    }
    //notify other services [cascade] 
    public void deleteUser(User user)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            session.remove(user);

            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            session.close();
            throw e;
        }finally{
            session.close();
        }
        log.info("User removed: "+user.getName());
    }

    public User updateUser(User user)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;
        User updatedUser = null;

        try{
            tx = session.beginTransaction();

            updatedUser = session.merge("users",user);

            tx.commit();
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            session.close();
            throw e;
        }finally{
            session.close();     
        }
        log.info("User updated: "+user.getName());
        return updatedUser;
    }

}
