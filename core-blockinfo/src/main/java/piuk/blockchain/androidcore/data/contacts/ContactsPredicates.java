package piuk.blockchain.androidcore.data.contacts;

import info.blockchain.wallet.contacts.data.Contact;

import io.reactivex.functions.Predicate;

public final class ContactsPredicates {

    public static Predicate<Contact> filterById(String id) {
        return contact -> contact.getId() != null && contact.getId().equals(id);
    }

    public static Predicate<Contact> filterByMdid(String mdid) {
        return contact -> contact.getMdid() != null && contact.getMdid().equals(mdid);
    }

    public static Predicate<Contact> filterByConfirmed() {
        return contact -> contact.getMdid() != null && !contact.getMdid().isEmpty();
    }

}
