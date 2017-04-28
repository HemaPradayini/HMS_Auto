package RecoveryScenarios;

import genericFunctions.EnvironmentSetup;

public class RecoveryScenarioSelector {
	
	public static void recoverApplication(){
		if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ProjectPhoenix")){
			SiebelRecovery.saveErrors();
		}
		else if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ServiceCloud")){
			SalesForceRecovery.recoverServiceCloud();
		}
		
	}
}
