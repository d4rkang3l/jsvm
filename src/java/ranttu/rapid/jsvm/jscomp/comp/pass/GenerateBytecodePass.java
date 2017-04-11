/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2017 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.jscomp.comp.pass;

import jdk.internal.org.objectweb.asm.Opcodes;
import ranttu.rapid.jsvm.codegen.ClassNode;
import ranttu.rapid.jsvm.jscomp.ast.astnode.Program;
import ranttu.rapid.jsvm.jscomp.ast.astnode.VariableDeclaration;
import ranttu.rapid.jsvm.jscomp.ast.astnode.VariableDeclarator;
import ranttu.rapid.jsvm.jscomp.comp.CompilePass;
import ranttu.rapid.jsvm.runtime.JsModule;

import java.util.Stack;

/**
 * the pass that generate bytecode
 *
 * @author rapidhere@gmail.com
 * @version $id: GenerateBytecodePass.java, v0.1 2017/4/6 dongwei.dq Exp $
 */
public class GenerateBytecodePass extends CompilePass {
    /** the class stack*/
    private Stack<ClassNode> classStack = new Stack<>();

    @Override
    protected void on(Program program) {
        // whole module as a top class
        ClassNode cls = newClass()
            .acc(Opcodes.ACC_PUBLIC, Opcodes.ACC_SUPER)
            .name(context.className, JsModule.class)
            .source(context.sourceFileName);

        // set this class to the context
        context.moduleClass = cls

        // add MODULE field
        .field(JsModule.FIELD_MODULE_NAME)
            .acc(Opcodes.ACC_PUBLIC, Opcodes.ACC_FINAL, Opcodes.ACC_STATIC)
            .desc(cls)
        .end()

        // default init
        .method_init()
            .label("L0")
            .aload(0)
            .invoke_init(JsModule.class)
            .ret()
            .label("L1")

            .local_var("this", cls, "L0", "L1")
            .stack(1)
        .end()

        // init MODULE field
        .method_clinit()
            .new_class(cls)
            .dup()
            .invoke_init(cls)
            .store_static(cls.field(JsModule.FIELD_MODULE_NAME))
            .ret()
            .stack(2)
        .end();
    }

    @Override
    protected void on(VariableDeclaration variableDeclaration) {
        for (VariableDeclarator varDec: variableDeclaration.getDeclarations()) {
            String varName = varDec.getId().getName();

            clazz().field(varName)
                .acc(Opcodes.ACC_PRIVATE)
                .desc(Object.class)
            .end();
        }
    }

    /**
     * create a new class
     * @return the class node
     */
    private ClassNode newClass() {
        return classStack.push(new ClassNode());
    }

    /**
     * get current class
     * @return current class
     */
    private ClassNode clazz() {
        return classStack.peek();
    }
}