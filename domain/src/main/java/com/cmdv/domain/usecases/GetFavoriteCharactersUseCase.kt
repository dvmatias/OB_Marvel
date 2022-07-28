package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.ResponseWrapper

class GetFavoriteCharactersUseCase :
    BaseUseCase<ResponseWrapper<List<CharacterModel>>, GetFavoriteCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<List<CharacterModel>> = TODO()

    class Params
}