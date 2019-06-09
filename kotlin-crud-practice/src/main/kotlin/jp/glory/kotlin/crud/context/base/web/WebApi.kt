package jp.glory.kotlin.crud.context.base.web

import org.springframework.web.bind.annotation.RestController

/**
 * WebAPIを表すアノテーション.
 */
@RestController
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebApi