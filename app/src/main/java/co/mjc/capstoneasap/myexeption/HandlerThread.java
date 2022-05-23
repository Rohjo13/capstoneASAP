package co.mjc.capstoneasap.myexeption;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

@Deprecated
public class HandlerThread implements Thread.UncaughtExceptionHandler {

    Context context;
    public HandlerThread(Context context) {
        Log.e("HandlerThread", "Constructor");
        this.context =context;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        System.out.println("이게 뭔지 알려줘 : " + context + "야");
        Toast.makeText(context.getApplicationContext(), "컴포넌트에 접근하지 못합니다.", Toast.LENGTH_LONG).show();
    }
}
