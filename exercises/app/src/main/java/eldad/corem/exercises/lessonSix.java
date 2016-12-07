package eldad.corem.exercises;

import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class lessonSix extends AppCompatActivity {
    ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_six);
        rootView = (ViewGroup) findViewById(android.R.id.content);
    }

    public void onGoToScene6A(View view) {
        Scene mScene1 = Scene.getSceneForLayout(rootView, R.layout.activity_lesson_six, getBaseContext());
        TransitionManager.go(mScene1);
    }

    public void onGoToScene6B(View view) {
        Scene mScene2 = Scene.getSceneForLayout(rootView, R.layout.activity_lesson_six_b, getBaseContext());
        TransitionManager.go(mScene2);
    }
}
