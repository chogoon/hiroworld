package kr.co.within.hiroworld.util;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;

import rx.Completable;
import rx.CompletableSubscriber;

public final class SetValueOnSubscribe implements Completable.OnSubscribe {

    private final DatabaseReference databaseRef;
    private final Object value;

    public SetValueOnSubscribe(DatabaseReference databaseRef, Object value) {
        this.databaseRef = databaseRef;
        this.value = value;
    }

    @Override
    public void call(CompletableSubscriber completableSubscriber) {
        final OnCompleteListener<Void> listener = task -> {
                MyLog.e(task.isSuccessful());
                if (!task.isSuccessful()) {
                    completableSubscriber.onError(task.getException());
                } else {
                    completableSubscriber.onCompleted();
                }
        };

        databaseRef.push().setValue(value).addOnCompleteListener(listener);
    }

}
