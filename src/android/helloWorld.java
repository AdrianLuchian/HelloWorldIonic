package cordova.plugin.helloWorld;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class helloWorld extends CordovaPlugin {
    helper h = new helper("hello");
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
    
         cordova.getThreadPool().execute(new Runnable(){
              public void run(){
                     for(int i=0; i < 10;i++)
                     {
                         try{
                            Thread.sleep(100);
                         }catch(InterruptedException ie){}
                     }       
                callback.success(h.getMesaj());
              }
         });            
    }
}
