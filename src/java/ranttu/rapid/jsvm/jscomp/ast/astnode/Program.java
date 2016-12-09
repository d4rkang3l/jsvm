/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2016 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.jscomp.ast.astnode;

import org.json.JSONObject;
import ranttu.rapid.jsvm.jscomp.ast.asttype.Node;
import ranttu.rapid.jsvm.jscomp.ast.asttype.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Program node
 * @author rapidhere@gmail.com
 * @version $id: Program.java, v0.1 2016/12/8 dongwei.dq Exp $
 */
public class Program extends BaseAstNode {
    /** the body of the program */
    private List<Statement> body = new ArrayList<>();

    public Program(JSONObject jsonObject) {
        super(jsonObject);

        jsonObject.getJSONArray("body").forEach((child) ->
            body.add(Node.of((JSONObject) child)));
    }

    public void addStatement(Statement node) {
        body.add(node);
    }

    public List<Statement> getBody() {
        return body;
    }
}
