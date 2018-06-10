package com.admin.common.shell;


/**
 * Created by zhouhao on 16-6-28.
 */
public interface Callback {
    Callback sout = ((line, helper) -> System.out.println(line));

    void accept(String line, ProcessHelper helper);
}
