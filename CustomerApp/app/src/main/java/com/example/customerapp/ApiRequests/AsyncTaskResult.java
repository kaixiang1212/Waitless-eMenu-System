package com.example.customerapp.ApiRequests;

/**
 * This class represents the results of the background processes for the api, it is extended by GetApiRequest and PostApiRequest Classes
 * @param <T>
 */
public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    /**
     * Gets the result
     * @return result : result
     */
    public T getResult(){
        return result;
    }

    /**
     * Gets the error
     * @return error : the error
     */
    public Exception getError() {
        return error;
    }

    /**
     * Public Constructor
     * Sets the result
     * @param result : result
     */
    public AsyncTaskResult(T result){
        this.result = result;
    }

    /**
     * Public Constructor
     * Sets the error
     * @param error : error
     */
    public AsyncTaskResult(Exception error){
        this.error = error;
    }
}
