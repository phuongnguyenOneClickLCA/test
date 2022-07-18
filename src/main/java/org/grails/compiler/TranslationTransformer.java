package org.grails.compiler;

import grails.compiler.ast.AstTransformer;
import grails.compiler.ast.GrailsArtefactClassInjector;
import grails.compiler.ast.GrailsDomainClassInjector;
import groovy.transform.Generated;
import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.tools.PropertyNodeUtils;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.grails.core.artefact.DomainClassArtefactHandler;
import org.grails.io.support.GrailsResourceUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.List;
import java.util.Objects;


/**
 * Adds translation methods to all domain artifacts.
 * A candidate field must be of type {@link java.util.Map} and
 * marked as {@link com.bionova.optimi.core.transformation.Translatable}.
 *
 * @author Valentin Ponochevniy
 */
@AstTransformer
public class TranslationTransformer implements GrailsDomainClassInjector, GrailsArtefactClassInjector {

    @Override
    public void performInjectionOnAnnotatedEntity(ClassNode classNode) {
        for (PropertyNode propertyNode : classNode.getProperties()) {
            AnnotationNode translatableAnnotation = propertyNode.getField().getAnnotations().stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getClassNode().getName().equals("com.bionova.optimi.core.transformation.Translatable"))
                    .findAny()
                    .orElse(null);
            boolean isTranslatable = translatableAnnotation != null;
            String alias = propertyNode.getName();
            String aliasPropertyName = "alias";
            if (isTranslatable && translatableAnnotation.getMember(aliasPropertyName) != null &&
                    StringUtils.hasText(translatableAnnotation.getMember(aliasPropertyName).getText())) {
                alias = translatableAnnotation.getMember(aliasPropertyName).getText();
            }
            boolean isMap = propertyNode.getType().isDerivedFrom(ClassHelper.MAP_TYPE);
            if (isTranslatable && isMap) {
                String getterName = "getLocalized" + MetaClassHelper.capitalize(alias);
                String transientName = "localized" + MetaClassHelper.capitalize(alias);
                if (classNode.getMethod(getterName, Parameter.EMPTY_ARRAY) == null) {
                    createTranslationGetterMethod(classNode, propertyNode, getterName);
                    addTransients(classNode, transientName);
                }
            }
        }
    }

    @Override
    public void performInjection(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        performInjectionOnAnnotatedClass(source, classNode);
    }

    @Override
    public void performInjection(SourceUnit source, ClassNode classNode) {
        performInjectionOnAnnotatedClass(source, classNode);
    }

    @Override
    public void performInjectionOnAnnotatedClass(SourceUnit source, ClassNode classNode) {
        performInjectionOnAnnotatedEntity(classNode);
    }

    public String[] getArtefactTypes() {
        return new String[]{DomainClassArtefactHandler.TYPE};
    }

    @Override
    public boolean shouldInject(URL url) {
        return GrailsResourceUtils.isDomainClass(url);
    }

    /**
     * Adds transients if they are not present.
     * <p>
     * Not essential.
     * As of Grails 2.0 if there is only a getter or only a setter method,
     * you don't need to declare the property name of the method in the transients list.
     * <p>
     * See https://docs.grails.org/3.1.1/ref/Domain%20Classes/transients.html
     *
     * @param classNode
     * @param transientName
     */
    protected void addTransients(final ClassNode classNode, final String transientName) {
        boolean found = false;
        for (FieldNode fNode : classNode.getFields()) {
            if (fNode.getName().equals("transients")) {
                found = true;
                if (((ListExpression) fNode.getInitialValueExpression()).getExpressions()
                        .stream()
                        .filter(Objects::nonNull)
                        .noneMatch(it -> it.getText().contains(transientName))) {
                    ((ListExpression) fNode.getInitialValueExpression()).addExpression(new ConstantExpression(transientName));
                }
            }
        }

        if (!found) {
            ListExpression listExpression = new ListExpression();
            listExpression.addExpression(new ConstantExpression(transientName));

            FieldNode fieldNode = new FieldNode("transients",
                    Opcodes.ACC_STATIC,
                    new ClassNode(List.class),
                    classNode,
                    listExpression);
            classNode.addField(fieldNode);
        }
    }

    protected void createTranslationGetterMethod(final ClassNode declaringClass, final PropertyNode propertyNode,
                                                 final String getterName) {

        BlockStatement code = new BlockStatement();
        VariableExpression registry = new VariableExpression(propertyNode.getField().getName());

        StaticMethodCallExpression loc1 = new StaticMethodCallExpression(new ClassNode(LocaleContextHolder.class),
                "getLocale", ArgumentListExpression.EMPTY_ARGUMENTS);
        MethodCallExpression loc2 = new MethodCallExpression(loc1,
                "getLanguage", ArgumentListExpression.EMPTY_ARGUMENTS);
        MethodCallExpression loc3 = new MethodCallExpression(loc2,
                "toUpperCase", ArgumentListExpression.EMPTY_ARGUMENTS);

        MethodCallExpression getValue = new MethodCallExpression(registry,
                "get", loc3);
        MethodCallExpression getDefaultValue = new MethodCallExpression(registry,
                "get", new ConstantExpression("EN"));

        BooleanExpression condition = new BooleanExpression(registry);
        ReturnStatement ifBlock = new ReturnStatement(new
                TernaryExpression(new BooleanExpression(getValue), getValue, getDefaultValue));
        ReturnStatement elseBlock = new ReturnStatement(ConstantExpression.EMPTY_STRING);

        code.addStatement(new IfStatement(condition, ifBlock, elseBlock));

        MethodNode getter = new MethodNode(
                getterName,
                PropertyNodeUtils.adjustPropertyModifiersForMethod(propertyNode),
                ClassHelper.STRING_TYPE,
                Parameter.EMPTY_ARRAY,
                ClassNode.EMPTY_ARRAY,
                code);
        getter.setSynthetic(true);
        getter.addAnnotation(new AnnotationNode(new ClassNode(Generated.class)));
        // add it to the class
        declaringClass.addMethod(getter);
    }
}
