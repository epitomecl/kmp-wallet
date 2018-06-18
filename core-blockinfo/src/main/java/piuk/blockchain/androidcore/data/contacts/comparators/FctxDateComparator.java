package piuk.blockchain.androidcore.data.contacts.comparators;

import info.blockchain.wallet.contacts.data.FacilitatedTransaction;

import java.util.Comparator;

/**
 * Compares {@link FacilitatedTransaction} objects and sorts them in descending order of date last
 * updated.
 */
public class FctxDateComparator implements Comparator<FacilitatedTransaction> {

    @Override
    public int compare(FacilitatedTransaction transaction1, FacilitatedTransaction transaction2) {
        return Long.compare(transaction1.getLastUpdated(), transaction2.getLastUpdated());
    }

}