package io.blackbricks.todomanager.model;

/**
 * Created by yegorkryndach on 14/04/16.
 */

public class ErrorMessageDeterminer {
    public String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "An unknown error has occurred";
    }
}
