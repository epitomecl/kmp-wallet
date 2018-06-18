package piuk.blockchain.androidcore.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the method being called hits a Shared Metadata endpoint and requires an
 * authenticated access token. As such, this method should be called via the helper methods
 * ContactsDataManager#callWithToken(ContactsDataManager.CompletableTokenRequest) or
 * ContactsDataManager#callWithToken(ContactsDataManager.ObservableTokenRequest) depending on the
 * subscription type.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface RequiresAccessToken {
}