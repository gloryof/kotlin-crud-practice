package jp.glory.kotlin.crud

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.core.importer.ImportOptions
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class LayerTest {

    private val rootPackage = "jp.glory.kotlin.crud"

    private val domain = "Domain"
    private val usecase = "Usecase"
    private val infra = "Infrastructure"
    private val infraRepository = "Infrastructure Repository"
    private val web = "Web"

    @Test
    @DisplayName("レイヤのテスト")
    fun testLayers() {

        val options = ImportOptions()
            .with(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        val importedClasses = ClassFileImporter(options).importPackages(rootPackage)

        val rules = Architectures.layeredArchitecture()
            .layer(domain).definedBy("$rootPackage.context..domain..")
            .layer(usecase).definedBy("$rootPackage.context..usecase..")
            .layer(infra).definedBy("$rootPackage.context..infra..")
            .layer(infraRepository).definedBy("$rootPackage.context..infra.repository..")
            .layer(web).definedBy("$rootPackage.context..web..")
            .whereLayer(domain).mayOnlyBeAccessedByLayers(usecase, infraRepository, web)
            .whereLayer(usecase).mayOnlyBeAccessedByLayers(web)
            .whereLayer(web).mayNotBeAccessedByAnyLayer()
            .whereLayer(infra).mayNotBeAccessedByAnyLayer()

        rules.check(importedClasses)
    }

}