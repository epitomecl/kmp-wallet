package piuk.blockchain.androidcore.data.datastores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import piuk.blockchain.androidcore.utils.ListUtil;

public abstract class ListStore<T> {

    private List<T> data;

    public ListStore() {
        data = new ArrayList<>();
    }

    public List<T> getList() {
        return data;
    }

    public void storeList(List<T> data) {
        this.data = data;
    }

    public void clearList() {
        data.clear();
    }

    public void insertObjectIntoList(T object) {
        data.add(object);
    }

    public void removeObjectFromList(T object) {
        data.remove(object);
    }

    public void insertBulk(List<T> objects) {
        ListUtil.addAllIfNotNull(data, objects);
    }

    public void sort(Comparator<T> objectComparator) {
        Collections.sort(data, objectComparator);
    }
}
