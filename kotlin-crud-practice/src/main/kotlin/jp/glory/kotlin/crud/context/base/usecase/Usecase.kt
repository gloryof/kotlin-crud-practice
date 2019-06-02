package jp.glory.kotlin.crud.context.base.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * ユースケースを表すアノテーション.
 */
@Service
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional
annotation class Usecase