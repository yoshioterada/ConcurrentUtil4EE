/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jp.co.oracle.tasks.MyRunnableTask;

public class MyDynamicProxy {
    public static void main(String argv[]){
        MyRunnableTask
                task = new MyRunnableTask();
        InvocationHandler handler = new MyInvocationHandler(task);
        Runnable proxy = (Runnable)Proxy.newProxyInstance(MyRunnableTask.class.getClassLoader(), new Class[]{Runnable.class}, handler);
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(proxy);
    }
}
