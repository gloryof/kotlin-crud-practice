package jp.glory.kotlin.crud.base.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy


/**
 * ユースケースを表すアノテーション.
 */
@Service
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional
annotation class Usecase