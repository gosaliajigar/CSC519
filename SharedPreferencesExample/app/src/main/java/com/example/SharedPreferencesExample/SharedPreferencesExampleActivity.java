package com.example.SharedPreferencesExample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferencesExampleActivity extends Activity {
    /** Called when the activity is first created. */
	SharedPreferences settings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
        	//our edit text/text box where the name will be entered
        	final EditText name_edit_text = (EditText) this.findViewById(R.id.NameTxt);
        	
        	//getSharedPreferences() - Use this if you need multiple preferences files identified by name, 
        	//which you specify with the first parameter.

        	settings = getSharedPreferences("CIS53_shared_pref", 0);
        	
            View.OnClickListener handler = new View.OnClickListener(){
                public void onClick(View v) {
                    switch (v.getId()) {

                        case R.id.SaveBtn:
                        	//get entered value and set to a variable
                        	String name_input = name_edit_text.getText().toString();
                            
                            //empty edit text field
                        	name_edit_text.setText("");
                        	
                        	//SAVE shared pref value
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("name", name_input);
                            editor.commit();
                            
                            //show button after saving
                            Toast.makeText(SharedPreferencesExampleActivity.this, 
                            				"You entered: " + name_input,
                            				Toast.LENGTH_SHORT)
                            				.show();
                            
                            break;
                            
                        case R.id.ShowSavedBtn: 

                        	//RETRIEVE/load the saved shared pref value
                        	String name = settings.getString("name", null);
                        	Toast.makeText(SharedPreferencesExampleActivity.this, 
                        					"Saved Name is: " + name, 
                        					Toast.LENGTH_LONG)
                        					.show();
                            break;
                    }
                }
            };
                
            //we will set the listeners
            findViewById(R.id.SaveBtn).setOnClickListener(handler);
            findViewById(R.id.ShowSavedBtn).setOnClickListener(handler);
                
        }catch(Exception e){
             Log.e("CIS53", e.toString());
        }  
            
    }
}
