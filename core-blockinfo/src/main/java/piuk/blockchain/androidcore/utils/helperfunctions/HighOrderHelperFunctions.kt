package piuk.blockchain.androidcore.utils.helperfunctions

/**
 * Contains general high-order convenience functions for various uses
 */

/**
 * Allows you to call a passed function whilst returning true. Inlined for performance reasons.
 * Useful for example in onOptionsItemSelected where you may want to invoke a function but also
 * have to return a value:
 *
 *      override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
 *          R.id.home -> consume { navigateToHome() }
 *          R.id.search -> consume { MenuItemCompat.expandActionView(item) }
 *          R.id.settings -> consume { navigateToSettings() }
 *          else -> super.onOptionsItemSelected(item)
 *
 * @see [https://kotlinlang.org/docs/reference/inline-functions.html]
 *
 */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}
