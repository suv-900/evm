package com.project.evm.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.evm.models.UserEntity;

//count,exists,findAllById,deleteAllById,findById
@Repository
public class UserDAO {
    
    private final static Logger log = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public UserEntity addUser(UserEntity user)throws Exception{
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

    public boolean exists(UserEntity user)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE name = :username OR email = :email) AS user_exists";

        Boolean exists = session.createQuery(query,Boolean.class)
            .setParameter("username",user.getName())
            .setParameter("email",user.getEmail()) 
            .getSingleResult();
        
        return exists;
    }

    public UserEntity findById(int userID)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();

        String query = "SELECT name,email,description FROM users WHERE id = :id";

        UserEntity user = session.createQuery(query,UserEntity.class)
                .setParameter("id",userID)
                .uniqueResult();
        
        return user; 
    }
    
    public List<UserEntity> findAllById(Iterable<Integer> idList)throws Exception{
        List<UserEntity> userList = new LinkedList<>();

        Session session = this.sessionFactory.getCurrentSession();
        
        String query = "SELECT name,email,description FROM users WHERE id = :id";

        idList.forEach((id) -> {
            UserEntity user = session.createQuery(query,UserEntity.class)
                .setParameter("id",id)    
                .getSingleResultOrNull();

            if(user != null){
                userList.add(user);
            }
        });

        return userList;
    }

    public String getPasswordByUsername(String name)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = null;
        String dbPassword = null;

        try{
            String query = "select password from users where name = :name";
            
            transaction = session.beginTransaction();

            dbPassword = session.createQuery(query,String.class)
                .setParameter("name",name)
                .uniqueResult();

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
    public void deleteUser(UserEntity user)throws Exception{
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

    public void updateUser(UserEntity user)throws Exception{
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = null;
        UserEntity updatedUser = null;

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
        log.info("User updated: "+updatedUser.toString());
    }

}
