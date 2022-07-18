package com.bionova.optimi.util

import groovy.transform.CompileStatic
import javax.script.Invocable
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException

@CompileStatic
class JavaScriptEngineManager {
    private Reader reader;

    public JavaScriptEngineManager(InputStream inputStream){
        this.reader = new InputStreamReader(inputStream);
    }

    public JavaScriptEngineManager(File file) throws IOException{
        this.reader = new FileReader(file);
    }

    public JavaScriptEngineManager(URI uri) throws IOException{
        this.reader = new InputStreamReader(uri.toURL().openStream());
    }

    public JavaScriptEngineManager(URL url) throws IOException{
        this.reader = new InputStreamReader(url.openStream());
    }

    public Map<String, String> completeJSMethod(String methodName, String resourceId, Map params) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js")
        engine.put("polyglot.js.allowHostAccess", true)
        engine.put("polyglot.js.allowHostClassLookup", true)
        engine.eval(reader)
        Invocable invocable = (Invocable) engine

        return (Map<String, String>)invocable.invokeFunction(methodName, resourceId, params)
    }
}
