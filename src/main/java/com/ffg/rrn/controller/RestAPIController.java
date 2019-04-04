/**
 * 
 */
package com.ffg.rrn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessmentQuestionnaire;
import com.ffg.rrn.model.ServiceCoordinator;
import com.ffg.rrn.service.ResidentServiceImpl;
import com.ffg.rrn.service.ServiceCoordinatorServiceImpl;

/**
 * @author FFGRRNTEam
 *
 */
@RestController
public class RestAPIController {

	@Autowired
	private ResidentServiceImpl residentService;

	@Autowired
	private ServiceCoordinatorServiceImpl serviceCoordinatorService;

	@PostMapping("/getAllResidents")
	public ResponseEntity<?> getAllResidents() {

		List<Resident> allResident = residentService.getAllResident();

		return ResponseEntity.ok(allResident);
	}

	@PostMapping("/getAllServiceCoordinators")
	public ResponseEntity<?> getAllServiceCoordinators() {

		List<ServiceCoordinator> allServiceCoordinators = serviceCoordinatorService.getAllServiceCoordinators();

		return ResponseEntity.ok(allServiceCoordinators);
	}

	@PostMapping("/getHistoricalAssessmentByResidentIdAndLifeDomain")
	public ResponseEntity<?> getHistoricalAssessmentByResidentIdAndLifeDomain(
			@RequestParam("residentId") String residentId, @RequestParam("onThisDate") String onThisDate,
			@RequestParam String lifeDomain) {

		if (!StringUtils.isEmpty(residentId) && !StringUtils.isEmpty(onThisDate) && !StringUtils.isEmpty(lifeDomain) && !StringUtils.equals("NewAssessment", onThisDate)) {
			List<ResidentAssessmentQuestionnaire> historicalAssessmentByResidentIdAndLifeDomain = residentService
					.getHistoricalAssessmentByResidentIdAndLifeDomain(Long.valueOf(residentId), onThisDate, lifeDomain);
			return ResponseEntity.ok(historicalAssessmentByResidentIdAndLifeDomain);
		}
		return ResponseEntity.ok("");
	}
	
	@PostMapping("/getAllLatestScoreGoal")
	public ResponseEntity<?> getAllLatestScoreGoal(@RequestParam("residentId") String residentId){
		
		Map<String, String> scoreGoalPerLifeDomain = new HashMap<String, String>();		
		
		if (!StringUtils.isEmpty(residentId)){
			String latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"HOUSING");
			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("HOUSING", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"MONEY MANAGEMENT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("MM", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"EMPLOYMENT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("EMP", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"EDUCATION");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("EDU", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"NETWORK SUPPORT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("NETSUPP", latestScoreGoal);
			}
			
			latestScoreGoal = residentService.getLatestScoreGoal(Long.valueOf(residentId),"HOUSEHOLD MANAGEMENT");			
			if(!StringUtils.isEmpty(latestScoreGoal)) {
				scoreGoalPerLifeDomain.put("HH", latestScoreGoal);
			}
			
		}
		
		return ResponseEntity.ok(scoreGoalPerLifeDomain);
	}
			
			

}
