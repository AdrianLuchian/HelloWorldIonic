package cordova-plugin-helloWorld;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class helloWorld extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if(action.equals("showMessage"))    
        {
            this.showMessage(callbackContext);
            return true;
        }
        return false;
    }

    private void showMessage(CallbackContext callback){
         callback.succes("HELLO WORLD !!!!!");            
    }
}
