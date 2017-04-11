/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2016 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.jscomp.comp;

import ranttu.rapid.jsvm.exp.CompileError;
import ranttu.rapid.jsvm.exp.DuplicateName;
import ranttu.rapid.jsvm.jscomp.ast.asttype.Declaration;
import ranttu.rapid.jsvm.jscomp.ast.asttype.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * the naming environment
 *
 * @author rapidhere@gmail.com
 * @version $id: NamingEnvironment.java, v0.1 2016/12/11 dongwei.dq Exp $
 */
public class NamingEnvironment {
    private Map<Node, Map<String, Name>> scopes = new HashMap<>();

    /**
     * get the scope of the node
     * @param node the node to find scope
     * @return the scope of the node
     */
    public Map<String, Name> getScope(Node node) {
        Map<String, Name> scope;

        while(true) {
            if ((scope = scopes.get(node)) != null) {
                return scope;
            }

            if(node.hasParent()) {
                node = node.getParent();
            } else {
                break;
            }
        }

        // no scope exist, this should never happen
        throw new CompileError(node, "cannot find a scope");
    }

    /**
     * add a binding to the last scope
     * @param name the binding name
     */
    public void addBinding(Node node, Name name) {
        // find last scope
        Map<String, Name> scope = getScope(node);

        if (! scope.containsKey(name.getId())) {
            scope.put(name.getId(), name);
        } else {
            // TODO
            throw new DuplicateName(node, name.getId());
        }
    }

    /**
     * add a binding to the last scope
     */
    public void addBinding(Node node, String id, Declaration declaration) {
        addBinding(node, new Name(id, declaration));
    }

    /**
     * create a new scope
     * @param node the node where scope activate
     * @return the created scope
     */
    public Map<String, Name> newScope(Node node) {
        Map<String, Name> scope = new HashMap<>();
        scopes.put(node, scope);

        return scope;
    }
}
