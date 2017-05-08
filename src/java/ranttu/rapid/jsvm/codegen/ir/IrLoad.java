/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2017 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.codegen.ir;

import jdk.internal.org.objectweb.asm.Type;

/**
 * load instruction
 *
 * @author rapidhere@gmail.com
 * @version $id: IrLoadLocal.java, v0.1 2017/4/18 dongwei.dq Exp $
 */
public class IrLoad extends IrNode {
    public FieldType type;

    // for get_field instruction only
    public String    className;
    public String    desc;
    public String    key;

    // for array instruction only
    public int       index;

    public static IrLoad property() {
        IrLoad irLoad = new IrLoad();
        irLoad.type = FieldType.PROP;

        return irLoad;
    }

    public static IrLoad field(String className, String fieldName, String desc) {
        IrLoad irLoad = new IrLoad();
        irLoad.type = FieldType.FIELD;
        irLoad.key = fieldName;
        irLoad.className = className;
        irLoad.desc = desc;
        return irLoad;
    }

    public static IrLoad staticField(Class clazz, String fieldName, String desc) {
        IrLoad irLoad = new IrLoad();
        irLoad.type = FieldType.STATIC_FIELD;
        irLoad.key = fieldName;
        irLoad.className = Type.getInternalName(clazz);
        irLoad.desc = desc;
        return irLoad;
    }

    public static IrLoad array(int idx) {
        IrLoad irLoad = new IrLoad();
        irLoad.type = FieldType.ARRAY;
        irLoad.index = idx;

        return irLoad;
    }

    public static IrLoad local(String name) {
        IrLoad irLoad = new IrLoad();
        irLoad.type = FieldType.LOCAL;
        irLoad.key = name;
        return irLoad;
    }
}
