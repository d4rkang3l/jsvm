/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2017 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.codegen.ir;

import jdk.internal.org.objectweb.asm.Type;
import ranttu.rapid.jsvm.common.MethodConst;

/**
 * @author rapidhere@gmail.com
 * @version $id: IrInvoke.java, v0.1 2017/4/18 dongwei.dq Exp $
 */
public class IrInvoke extends IrNode {
    public InvokeType type;
    public IrNode     invoker;
    public IrNode     invokeName;

    public String     desc;
    public IrNode[]   args;

    public IrInvoke(InvokeType type, IrNode invoker, IrNode invokeName, String desc, IrNode... args) {
        this.type = type;
        this.invoker = invoker;
        this.invokeName = invokeName;
        this.desc = desc;
        this.args = args;
    }

    public static IrInvoke invokeInit(IrNode invoker, String desc, IrNode... args) {
        return new IrInvoke(InvokeType.SPECIAL, invoker, IrLiteral.of(MethodConst.INIT), desc, args);
    }

    public static IrInvoke invokeInit(Class clazz, String desc, IrNode... args) {
        return invokeInit(IrLiteral.of(Type.getInternalName(clazz)), desc, args);
    }

    public static IrInvoke invokeInit(Class clazz) {
        return invokeInit(clazz,
            Type.getMethodDescriptor(Type.VOID_TYPE));
    }
}
