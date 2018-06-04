package cordova.plugin.helloWorld.helpers;

import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;

public class Setting {
	private String settingName;
	private int samplingRateFieldID;
	private int enabledCheckboxID;
	private int displayedCheckboxID;
	
	public Setting(String settingName, int samplingRateFieldID, int enabledCheckboxID, int displayedCheckboxID) {
		this.settingName = settingName;
		this.samplingRateFieldID = samplingRateFieldID;
		this.enabledCheckboxID = enabledCheckboxID;
		this.displayedCheckboxID = displayedCheckboxID;
	}
	
	public String getSensorName() {
		return settingName;
	}
	
	public String getSamplingRate(View v) {
		EditText editText = (EditText) v.findViewById(samplingRateFieldID);
		CharSequence s = editText.getText();
		String value = s.toString();
		return value;
	}
	
	public String isEnabled(View v) {
		CheckBox checkbox = (CheckBox) v.findViewById(enabledCheckboxID);
		if( checkbox.isChecked() )
			return "true";
		return "false";
	}
	
	public String isDisplayed(View v) {
		CheckBox checkbox = (CheckBox) v.findViewById(displayedCheckboxID);
		if( checkbox.isChecked() )
			return "true";
		return "false";
	}
}
