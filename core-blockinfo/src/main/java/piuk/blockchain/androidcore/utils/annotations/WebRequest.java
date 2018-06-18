package piuk.blockchain.androidcore.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that this function makes a web request at somepoint in it's execution, either directly
 * or as a side effect, and as such should be called via {@link piuk.blockchain.android.data.rxjava.RxPinning}
 * for security. This annotation also denotes that the call should be threaded appropriately.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface WebRequest {
}
