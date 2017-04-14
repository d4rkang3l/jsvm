/**
 * rapidhere@gmail.com
 * Copyright (c) 1995-2017 All Rights Reserved.
 * ===> GLORY TO THE FIRST BORN! <===
 */
package ranttu.rapid.jsvm.test.unittest;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import ranttu.rapid.jsvm.common.ReflectionUtil;
import ranttu.rapid.jsvm.runtime.JsModule;
import ranttu.rapid.jsvm.test.base.JsvmJunitTestBase;

/**
 * some common and base cases for modules
 *
 * @author rapidhere@gmail.com
 * @version $id: ExpressionTest.java, v0.1 2017/4/8 dongwei.dq Exp $
 */
@RunWith(DataProviderRunner.class)
public class ExpressionTest extends JsvmJunitTestBase {
    public static class ExpressionTestData extends BaseCaseData {
        public String jsSource;
        public Object expected;
    }

    @Test
    @UseDataProvider("yamlDataProvider")
    public void singleLiteral(ExpressionTestData testData) throws Exception {
        String clsName = "JsModuleTest";
        JsModule module = loadModule(clsName, testData.jsSource);

        assertEquals(clsName, module.getClass().getSimpleName());
        assertEquals(jsValueOf(testData.expected), ReflectionUtil.getFieldValue(module, "a"));
    }

    // ~~~ not working yet
    //    @Test
    //    @UseDataProvider("yamlDataProvider")
    //    public void binaryArithmeticOp(ExpressionTestData testData) throws Exception {
    //        String clsName = "JsModuleTest";
    //        JsModule module = loadModule(clsName, testData.jsSource);
    //
    //        assertEquals(clsName, module.getClass().getSimpleName());
    //        assertEquals(testData.expected, ReflectionUtil.getFieldValue(module, "res"));
    //    }
}
