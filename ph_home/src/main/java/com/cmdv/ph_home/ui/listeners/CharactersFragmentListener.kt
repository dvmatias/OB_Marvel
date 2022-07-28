package com.cmdv.ph_home.ui.listeners

/**
 * Interface. Declares a set of action to be implemented by [com.cmdv.ph_home.HomeActivity]
 */
interface CharactersFragmentListener {
    /**
     * Triggered when a character item in the list is clicked. App should navigate to character details.
     *
     * @param characterId
     */
    fun onCharacterClick(characterId: Int)

    /**
     * Triggered when something went wrong in the characters loading.
     *
     * @param message Error message to display.
     */
    fun showErrorSnackBar(message: String)
}