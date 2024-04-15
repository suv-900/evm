package com.project.evm.exceptions;

public class UnauthorizedUserException extends RuntimeException{
   public UnauthorizedUserException(){
    super();
   }
   
   public UnauthorizedUserException(String args){
        super(args);
   }
}
