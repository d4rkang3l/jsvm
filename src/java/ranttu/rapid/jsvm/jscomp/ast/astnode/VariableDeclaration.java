/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2016 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.jscomp.ast.astnode;

import org.json.JSONObject;
import ranttu.rapid.jsvm.exp.NotSupportedYet;
import ranttu.rapid.jsvm.jscomp.ast.asttype.Declaration;
import ranttu.rapid.jsvm.jscomp.ast.asttype.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rapidhere@gmail.com
 * @version $id: VariableDeclaration.java, v0.1 2016/12/8 dongwei.dq Exp $
 */
public class VariableDeclaration extends BaseAstNode implements Declaration {
    private DeclarationType          kind;

    private List<VariableDeclarator> declarations = new ArrayList<>();

    public VariableDeclaration(JSONObject jsonObject) {
        super(jsonObject);
        kind = DeclarationType.of(jsonObject.getString("kind"));

        jsonObject.getJSONArray("declarations").forEach(
            (child) -> declarations.add(Node.of(this, (JSONObject) child)));

        if (kind != DeclarationType.LET) {
            throw new NotSupportedYet(this, "variable declaration only support `let`");
        }
    }

    public List<VariableDeclarator> getDeclarations() {
        return declarations;
    }

    public DeclarationType getKind() {
        return kind;
    }

    /**
     * declaration type enum
     */
    public enum DeclarationType {
        VAR, LET, CONST

        ;

        public static DeclarationType of(String s) {
            return DeclarationType.valueOf(s.toUpperCase());
        }
    }
}
