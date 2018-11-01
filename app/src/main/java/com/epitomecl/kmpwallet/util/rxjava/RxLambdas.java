package com.epitomecl.kmpwallet.util.rxjava;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public final class RxLambdas {

    // For collapsing into Lambdas
    public interface ObservableRequest<T> {
        Observable<T> apply();
    }

    // For collapsing into Lambdas
    public interface SingleRequest<T> {
        Single<T> apply();
    }

    // For collapsing into Lambdas
    public interface CompletableRequest {
        Completable apply();
    }

    public abstract static class ObservableFunction<T> implements Function<Void, Observable<T>> {
        public abstract Observable<T> apply(Void empty);
    }

    public abstract static class SingleFunction<T> implements Function<Void, Single<T>> {
        public abstract Single<T> apply(Void empty);
    }

    public abstract static class CompletableFunction implements Function<Void, Completable> {
        public abstract Completable apply(Void empty);
    }

}
