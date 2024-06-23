package com.user.user.exception;

public class ResourceNotFoundException extends RuntimeException{
    //when we make it inheritance of runtime exception it will become normal class to custom exception class
    //whenever we create one obj to this class it will through one exception

    public ResourceNotFoundException(String message) {
      super(message); // super is calling its parent class constructor runtime, runtime again calls exception, exception to throwable
    }
}
