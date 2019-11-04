package com.bonitasoft.generate.cases.scenario.process

import static com.bonitasoft.generate.cases.scenario.process.Condition.loop

import java.util.concurrent.TimeoutException

import org.bonitasoft.engine.bpm.flownode.ActivityStates
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstanceSearchDescriptor
import org.bonitasoft.engine.search.SearchOptionsBuilder

import com.bonitasoft.engine.api.APIAccessor


class VacationRequestProcess {

    APIAccessor apiAccessor

    VacationRequestProcess(APIAccessor apiAccessor){
        this.apiAccessor = apiAccessor
    }

    def long startCase(long userId, String version) {
        apiAccessor.getProcessAPI().startProcess(userId, apiAccessor.getProcessAPI().getProcessDefinitionId('VacationRequest', version)).getId()
	}
 

    def resolveCase(long userId, long processInstanceId) {
        apiAccessor.getProcessAPI().assignAndExecuteUserTask(userId, taskId(apiAccessor,processInstanceId, 'Close'), null)
    }
	
	def invloveUser(long userId, long processInstanceId) {
		apiAccessor.getProcessAPI().assignAndExecuteUserTask(userId, taskId(apiAccessor,processInstanceId, 'InvolveUser'), null)
	}
  
    def long taskId(APIAccessor apiAccessor, long processInstanceId, String taskName) {
        long taskId = -1
        loop {
            def searchResult = apiAccessor.getProcessAPI().searchHumanTaskInstances(new SearchOptionsBuilder(0, 1)
                    .filter(HumanTaskInstanceSearchDescriptor.NAME, taskName)
                    .filter(HumanTaskInstanceSearchDescriptor.PARENT_PROCESS_INSTANCE_ID, processInstanceId)
                    .filter(HumanTaskInstanceSearchDescriptor.STATE_NAME, ActivityStates.READY_STATE)
                    .done())
            taskId = searchResult.count == 1 ? searchResult.getResult()[0].id : -1
        } until { taskId >= 0 }
        return taskId
    }
}
