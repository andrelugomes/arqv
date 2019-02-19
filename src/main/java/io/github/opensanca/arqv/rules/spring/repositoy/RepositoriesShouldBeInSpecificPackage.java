package io.github.opensanca.arqv.rules.spring.repositoy;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import io.github.opensanca.arqv.ArqvRule;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "")
@ArqvRule(
    name = "REPOSITORIES_SHOULD_STAY_ON_REPOSITORY_PACKAGE",
    group = "SPRING_REPOSITORY_GROUP_RULES"
)
public class RepositoriesShouldBeInSpecificPackage {

    @ArchTest
    static final ArchRule REPOSITORIES_SHOULD_STAY_ON_REPOSITORY_PACKAGE =
        classes()
            .that()
                .areInterfaces()
                .and()
                .haveNameMatching(".*Repository")
            .should()
                .beInterfaces()
            .andShould()
                .resideInAPackage("..repository..")
            .as("Repositories should reside in a package '..repository..'");
}
