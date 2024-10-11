package energycontrol.back.api.impl;

public enum EApiMessagesKeys 
{
	StatusCode200("StatusCode200"),
	StatusCode500("StatusCode500"),
	CreateMSourceAlreayExists("CreateMSourceAlreayExists");
	
	public final String stringValue;
	
	private EApiMessagesKeys(String stringValue)
	{
		this.stringValue = stringValue;
	}
}
