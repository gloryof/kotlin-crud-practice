package jp.glory.kotlin.crud.context.user.usecase

import jp.glory.kotlin.crud.context.user.domain.repository.UserSaveEventRepository

/**
 * ユーザを保存する.
 *
 * @param repository ユーザ保存イベントリポジトリ
 */
class SaveUser(private val repository: UserSaveEventRepository)