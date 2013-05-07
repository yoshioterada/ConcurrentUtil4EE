/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    private Object underlying;
    public MyInvocationHandler(Object underlying) {
        this.underlying = underlying;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("オリジナル・メソッド呼び出しの前処理");
        Object ret = method.invoke(underlying, args);
        System.out.println("オリジナル・メソッド呼び出しの前処理");
        return ret;
    }
}
