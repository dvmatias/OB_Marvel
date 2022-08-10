package com.cmdv.ph_home.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.cmdv.domain.models.CharacterModel

class CharacterDiffCallback(
    private val oldList: List<CharacterModel>,
    private val mewList: List<CharacterModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = mewList.size

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return (oldList[oldPosition].id == mewList[newPosition].id)
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldCharacter = oldList[oldPosition]
        val newCharacter = mewList[newPosition]

        return oldCharacter.isFavorite == newCharacter.isFavorite
    }
}