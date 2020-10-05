package com.example.waiterapp.ApiRequests;

/**
 * AsyncTaskResult - Encapsulation of API result and any Exception encountered, if any.
 * @param <T>
 */
public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    public T getResult(){
        return result;
    }

    public Exception getError() {
        return error;
    }

    /**
     * Constructor with API result
     * @param result
     */
    public AsyncTaskResult(T result){
        this.result = result;
    }

    /**
     * Constructor with Exception
     * @param error
     */
    public AsyncTaskResult(Exception error){
        this.error = error;
    }
}