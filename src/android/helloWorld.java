package cordova.plugin.helloWorld;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class helloWorld extends CordovaPlugin {
   
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
         Context context = cordova.getActivity().getApplicationContext();
        if(action.equals("showMessage"))    
        { 
            Log.d("DA","MERGE");
            this.openNewActivity(context);
            return true;
        }
        return false;
    }
     private void openNewActivity(Context context) {
        Intent intent = new Intent(context, helper.class);
        this.cordova.getActivity().startActivity(intent);
    }

    private void showMessage(CallbackContext callback){
    
         cordova.getThreadPool().execute(new Runnable(){
              public void run(){
                   
              }
         });            
    }
}
