/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2017 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.runtime.indy;

import ranttu.rapid.jsvm.common.$$;
import ranttu.rapid.jsvm.common.ReflectionUtil;
import ranttu.rapid.jsvm.runtime.JsFunctionObject;
import ranttu.rapid.jsvm.runtime.JsObjectObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

/**
 * a invoke-dynamic call site that is mutable
 *
 * @author rapidhere@gmail.com
 * @version $id: JsIndyBindingPoint.java, v0.1 2017/4/17 dongwei.dq Exp $
 */
public class JsIndyCallSite extends MutableCallSite {
    private JsIndyType          indyType;

    private static MethodHandle SET_PROP;
    private static MethodHandle GET_PROP;
    private static MethodHandle INVOKE;
    private static MethodHandle BOUNDED_INVOKE;
    private static MethodHandle CONSTRUCT;

    static {
        try {
            SET_PROP = MethodHandles.lookup().findStatic(JsIndyCallSite.class, "setProperty",
                MethodType.methodType(void.class, Object.class, String.class, Object.class));

            GET_PROP = MethodHandles.lookup().findStatic(JsIndyCallSite.class, "getProperty",
                MethodType.methodType(Object.class, Object.class, String.class));

            INVOKE = MethodHandles
                .lookup()
                .findStatic(JsIndyCallSite.class, "invoke",
                    MethodType.methodType(Object.class, Object.class, Object.class, Object[].class))
                .asVarargsCollector(Object[].class);

            BOUNDED_INVOKE = MethodHandles
                .lookup()
                .findStatic(JsIndyCallSite.class, "boundedInvoke",
                    MethodType.methodType(Object.class, Object.class, Object.class, Object[].class))
                .asVarargsCollector(Object[].class);

            CONSTRUCT = MethodHandles
                .lookup()
                .findStatic(JsIndyCallSite.class, "construct",
                    MethodType.methodType(Object.class, Object.class, Object[].class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public JsIndyCallSite(JsIndyType indyType, MethodType type) {
        super(type);
        this.indyType = indyType;
    }

    public void init() {
        switch (indyType) {
            case SET_PROP:
                setTarget(SET_PROP);
                break;
            case GET_PROP:
                setTarget(GET_PROP);
                break;
            case UNBOUNDED_INVOKE:
                setTarget(INVOKE.asType(type()));
                break;
            case BOUNDED_INVOKE:
                setTarget(BOUNDED_INVOKE.asType(type()));
                break;
            case CONSTRUCT:
                setTarget(CONSTRUCT.asType(type()));
                break;
        }
    }

    @SuppressWarnings("unused")
    public static void setProperty(Object obj, String name, Object val) {
        // TODO: refine
        if (obj instanceof JsObjectObject) {
            $$.cast(obj, JsObjectObject.class).setProperty(name, val);
        } else {
            ReflectionUtil.setFieldValue(obj, name, val);
        }
    }

    @SuppressWarnings("unused")
    public static Object getProperty(Object obj, String name) {
        // TODO: refine
        if (obj instanceof JsObjectObject) {
            return $$.cast(obj, JsObjectObject.class).getProperty(name);
        } else {
            return ReflectionUtil.getFieldValue(obj, name);
        }
    }

    @SuppressWarnings("unused")
    public static Object invoke(Object invoker, Object context, Object... args) {
        // TODO
        return $$.cast(invoker, JsFunctionObject.class).invoke(context, args);
    }

    @SuppressWarnings("unused")
    public static Object boundedInvoke(Object context, Object name, Object... args) {
        // TODO
        Object invoker = getProperty(context, name.toString());
        return invoke(invoker, context, args);
    }

    @SuppressWarnings("unused")
    public static Object construct(Object invoker, Object...args) {
        JsFunctionObject func = $$.cast(invoker);
        return func.construct(args);
    }
}