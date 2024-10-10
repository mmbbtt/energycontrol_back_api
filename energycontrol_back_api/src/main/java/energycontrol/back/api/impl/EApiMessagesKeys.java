package energycontrol.back.api.impl;

public enum EApiMessagesKeys 
{
	CreateMSourceAlreayExists("CreateMSourceAlreayExists"),
	//<-
	GetSourcesError("GetSourcesError"),
	GetSourcesNoneRegistred("GetSourcesNoneRegistred"),
	RowsAffected("RowsAffected"),
	CommandExecutedOk("CommandExecutedOk"),
	CommandExecutedKo("CommandExecutedKo"),
	BillLoaded("BillLoaded"),
	SourceConsumptionsLoaded("SourceConsumptionsLoaded"),
	NullParameter("NullParameter"),
	IncorrectNumberOfParameters("IncorrectNumberOfParameters"),
	IncorrectParameter("IncorrectParameter"),
	ValueNotValidFor("ValueNotValidFor"),
	NoData("NoData"),
	FileNotExist("FileNotExist");
	
	public final String stringValue;
	
	private EApiMessagesKeys(String stringValue)
	{
		this.stringValue = stringValue;
	}
}
