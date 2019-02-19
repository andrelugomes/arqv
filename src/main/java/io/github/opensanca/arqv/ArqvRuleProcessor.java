package io.github.opensanca.arqv;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("io.github.opensanca.arqv.ArqvRule")
public class ArqvRuleProcessor extends AbstractProcessor {

    private Messager messager;
    private Types typesUtil;
    private Elements elementsUtil;
    private Filer filer;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typesUtil = processingEnv.getTypeUtils();
        elementsUtil = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }


    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(ArqvRule.class)) {
              System.out.println(element);
        }

        for (final Element element : roundEnv.getElementsAnnotatedWith(ArqvRule.class)) {
            if (element instanceof TypeElement) {
                final TypeElement typeElement = (TypeElement) element;

                for (final Element eclosedElement : typeElement.getEnclosedElements()) {
                    if (eclosedElement instanceof VariableElement) {
                        final VariableElement variableElement = (VariableElement) eclosedElement;

                        if (!variableElement.getModifiers().contains(Modifier.FINAL)) {
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                    String.format("Class '%s' is annotated as @Immutable, but field '%s' is not declared as final",
                                            typeElement.getSimpleName(), variableElement.getSimpleName()));
                        }
                    }
                }
            }

            // Claiming that annotations have been processed by this processor

        }
        return true;
    }
}
