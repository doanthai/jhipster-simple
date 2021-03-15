package com.topica.test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.topica.test");

        noClasses()
            .that()
                .resideInAnyPackage("com.topica.test.service..")
            .or()
                .resideInAnyPackage("com.topica.test.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.topica.test.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
