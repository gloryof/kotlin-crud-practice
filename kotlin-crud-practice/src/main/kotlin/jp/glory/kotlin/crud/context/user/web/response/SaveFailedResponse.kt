package jp.glory.kotlin.crud.context.user.web.response

/**
 * 保存失敗レスポンス.
 *
 * 本来は共通エラーハンドリングを作るべきだが、Spring Bootの勉強が目的ではないので割愛。
 *
 * @param messages メッセージリスト
 */
class SaveFailedResponse(val messages: List<String>) {

}