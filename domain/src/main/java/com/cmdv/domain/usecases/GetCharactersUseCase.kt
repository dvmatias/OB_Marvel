package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.uimodels.CharacterUiModel
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: To get a list of Marvel's characters.
 */
class GetCharactersUseCase(
    private val charactersRepository: CharactersRepository
) : BaseUseCase<ResponseWrapper<List<CharacterUiModel>>, GetCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<List<CharacterUiModel>> =
        charactersRepository.getCharacters(params.fetch, params.limit, params.offset)

    /**
     * @param fetch If true, indicates  the need to make a service call to get Marvel's characters. If false indicate
     * that the characters should be provided from DB (stored).
     * @param limit Limit the result set to the specified number of resources (applied only to service call).
     * @param offset Skip the specified number of resources in the result set (applied only to service call).
     */
    data class Params(val fetch: Boolean, val limit: Int, val offset: Int)
}