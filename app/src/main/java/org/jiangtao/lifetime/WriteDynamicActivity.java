package org.jiangtao.lifetime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.GridView;

public class WriteDynamicActivity extends AppCompatActivity {

    private EditText mEditText;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_dynamic);
        initControl();
    }

    private void initControl() {
        mEditText = (EditText) findViewById(R.id.et_write_dynamic);
        mGridView = (GridView) findViewById(R.id.gl_write_dynamic);
    }
}
