package jp.glory.kotlin.crud.context.user.web

import jp.glory.kotlin.crud.context.base.usecase.error.FailedResult
import jp.glory.kotlin.crud.context.base.usecase.error.SuccessResult
import jp.glory.kotlin.crud.context.base.usecase.error.UsecaseResult
import jp.glory.kotlin.crud.context.base.web.WebApi
import jp.glory.kotlin.crud.context.user.usecase.SaveUser
import jp.glory.kotlin.crud.context.user.usecase.SaveUser.RegisterParam
import jp.glory.kotlin.crud.context.user.usecase.SaveUser.UpdateParam
import jp.glory.kotlin.crud.context.user.usecase.SearchUser
import jp.glory.kotlin.crud.context.user.usecase.SearchUser.UserResult
import jp.glory.kotlin.crud.context.user.web.request.SaveUserRequest
import jp.glory.kotlin.crud.context.user.web.response.UserResponse
import jp.glory.kotlin.crud.context.user.web.response.UsersResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

/**
 * ユーザAPI.
 */
@WebApi
@RequestMapping("/user")
class UserApi(private val search: SearchUser, private val save: SaveUser) {

    /**
     * ユーザのリストを取得する.
     *
     * @return ユーザリストレスポンス
     */
    @GetMapping
    fun findAll(): UsersResponse = UsersResponse(search.findAll())

    /***
     * ユーザの登録を行う.
     *
     * @param userRequest ユーザリクエスト
     * @return 登録されたユーザのID
     */
    @PostMapping
    fun register(@RequestBody userRequest: SaveUserRequest): ResponseEntity<Any> {

        val param: RegisterParam = createRegisterParam(userRequest)

        return createResponse(save.register(param))
    }

    /**
     * IDをキーにユーザを取得する.
     *
     * @param id ユーザID
     * @return ユーザレスポンス
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<UserResponse> {

        return when (val user: UserResult? = search.findByUserId(id)) {
            null -> ResponseEntity(HttpStatus.NOT_FOUND)
            else -> ResponseEntity(UserResponse(user), HttpStatus.OK)
        }
    }

    /***
     * ユーザの更新を行う.
     *
     * @param userRequest ユーザリクエスト
     * @return 更新されたユーザのID
     */
    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody userRequest: SaveUserRequest
    ): ResponseEntity<Any> {

        val param: UpdateParam = createUpdateParam(id, userRequest)

        return createResponse(save.update(param))
    }

    /**
     * 登録パラメータを作成する.
     *
     * @param request 保存リクエスト
     * @return 登録パラメータ
     */
    private fun createRegisterParam(request: SaveUserRequest): RegisterParam {

        return RegisterParam(
            lastName = request.lastName,
            firstName = request.firstName,
            birthDay = request.birthDay
        )
    }

    /**
     * 登録パラメータを作成する.
     *
     * @param id ユーザID
     * @param request 保存リクエスト
     * @return 登録パラメータ
     */
    private fun createUpdateParam(id: Long, request: SaveUserRequest): UpdateParam {

        return UpdateParam(
            userId = id,
            lastName = request.lastName,
            firstName = request.firstName,
            birthDay = request.birthDay
        )
    }

    /**
     * レスポンスを作成する.
     *
     * @param result ユースケースの実行結果
     * @return レスポンスエンティティ
     */
    private fun createResponse(result: UsecaseResult<Long>): ResponseEntity<Any> {

        return when (result) {
            is SuccessResult<Long> -> ResponseEntity(result.value, HttpStatus.CREATED)
            is FailedResult -> ResponseEntity(result.messages, HttpStatus.BAD_REQUEST)
        }
    }
}
