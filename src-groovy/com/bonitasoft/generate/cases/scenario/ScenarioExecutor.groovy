package com.bonitasoft.generate.cases.scenario


import java.util.concurrent.ThreadLocalRandom

import org.bonitasoft.engine.bpm.flownode.FlowNodeInstanceSearchDescriptor
import org.bonitasoft.engine.search.SearchOptionsBuilder

import com.bonitasoft.engine.api.APIAccessor
import com.bonitasoft.generate.cases.scenario.process.PublishDailyMealProcess
import com.bonitasoft.generate.cases.scenario.process.VacationRequestProcess
import com.bonitasoft.generate.cases.scenario.process.VegasProcess

class ScenarioExecutor {
    
    
    def ScenarioExecutor() {
    }
    
            
    def generateDataToTestUserCaseList(APIAccessor apiAccessor) {
        
		VacationRequestProcess vacationRequest = new VacationRequestProcess(apiAccessor)
		VegasProcess vegasRequest = new VegasProcess(apiAccessor)
		PublishDailyMealProcess publishDailyMeal = new PublishDailyMealProcess(apiAccessor)
		
		for(int i = 0; i < 10; i++) {
			long processInstanceId
			
			//Generate open cases
			vacationRequest.startCase(getUserId(apiAccessor, "walter.bates"), "1.0")
			processInstanceId = vacationRequest.startCase(getUserId(apiAccessor, "helen.kelly.veryveryveryverylongusername"), "2.0")
			vacationRequest.invloveUser(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			vacationRequest.startCase(getUserId(apiAccessor, "walter.bates"), "3.0")
			vacationRequest.startCase(getUserId(apiAccessor, "walter.bates"), "4.0")
			vacationRequest.startCase(getUserId(apiAccessor, "walter.bates"), "5.0")
			vegasRequest.startCase(getUserId(apiAccessor, "walter.bates"), "1.0") 
			processInstanceId = vegasRequest.startCase(getUserId(apiAccessor, "helen.kelly.veryveryveryverylongusername"), "2.0")
			vegasRequest.invloveUser(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			publishDailyMeal.startCase(getUserId(apiAccessor, "walter.bates"), "1.0")
			
			//Generate archived cases
			processInstanceId = vacationRequest.startCase(getUserId(apiAccessor, "walter.bates"), "1.0")
			vacationRequest.resolveCase(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			
			processInstanceId = vacationRequest.startCase(getUserId(apiAccessor, "helen.kelly.veryveryveryverylongusername"), "2.0")
			vacationRequest.invloveUser(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			vacationRequest.resolveCase(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			
			processInstanceId = vacationRequest.startCase(getUserId(apiAccessor, "walter.bates"), "3.0")
			vacationRequest.resolveCase(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			
			processInstanceId = vegasRequest.startCase(getUserId(apiAccessor, "walter.bates"), "1.0")
			vegasRequest.resolveCase(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			
			processInstanceId = vegasRequest.startCase(getUserId(apiAccessor, "helen.kelly.veryveryveryverylongusername"), "2.0")
			vegasRequest.invloveUser(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			vegasRequest.resolveCase(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			
			processInstanceId = publishDailyMeal.startCase(getUserId(apiAccessor, "walter.bates"), "1.0")
			publishDailyMeal.invloveUser(getUserId(apiAccessor, "walter.bates"), processInstanceId)
			publishDailyMeal.resolveCase(getUserId(apiAccessor, "walter.bates"), processInstanceId)
	
		}

		        
    }
    
    def long getUserId(APIAccessor apiAccessor, String username) {
        apiAccessor.getIdentityAPI().getUserByUserName(username).id
    }
	

}
