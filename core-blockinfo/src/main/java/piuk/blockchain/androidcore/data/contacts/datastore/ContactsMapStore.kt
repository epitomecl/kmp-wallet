package piuk.blockchain.androidcore.data.contacts.datastore

import piuk.blockchain.android.data.contacts.models.ContactTransactionDisplayModel
import piuk.blockchain.androidcore.data.datastores.SimpleDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsMapStore @Inject constructor() : SimpleDataStore {

    /**
     * A [MutableMap] containing a [ContactTransactionDisplayModel] keyed to a Tx hash for convenient
     * display.
     */
    val displayMap = mutableMapOf<String, ContactTransactionDisplayModel>()

    override fun clearData() {
        displayMap.clear()
    }

}
