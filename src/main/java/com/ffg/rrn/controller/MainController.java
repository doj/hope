/**
 * 
 */
package com.ffg.rrn.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ffg.rrn.model.Resident;
import com.ffg.rrn.model.ResidentAssessment;
import com.ffg.rrn.service.ResidentServiceImpl;
import com.ffg.rrn.utils.WebUtils;

/**
 * @author FFGRRNTeam
 *
 */
@Controller
public class MainController {
	
	@Autowired
	private ResidentServiceImpl residentService;

	// Either you don't pass anything or pass welcome from the url, it hit below API
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Resident Resource Network");
		model.addAttribute("message", "Welcome to Resident Resource Hope portal");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		if (principal != null) {
			populateSCinModel(model, principal);
		}

		return "adminPage";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String loginPage(Model model) {

		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}
	
	@PostMapping(value = "/saveAssessment")
	public String ssmAssessment(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {

		int count = residentService.saveAssessment(resident);		
		
		return "redirect:/getResidentById?residentId="+resident.getResidentId();		
	}

	@RequestMapping(value = "/getResidentById",  method = { RequestMethod.GET, RequestMethod.POST })
	public String residents(@RequestParam("residentId") Long residentId, Model model, Principal principal) throws Exception{

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}		
		
		Resident resident  = residentService.getResidentById(residentId, serviceCoord);	
		
		ResidentAssessment ra = new ResidentAssessment();
		
		ra.setResidentId(resident.getResidentId());
		ra.setFirstName(resident.getFirstName());
		ra.setMiddle(resident.getMiddle());
		ra.setLastName(resident.getLastName());
		ra.setAId(resident.getAId());
		ra.setServiceCoord(serviceCoord);
		
		model.addAttribute("residentAssessment", ra);				
		model.addAttribute("resident", resident);		
		model.addAttribute("message", "Please select resident from All Resident Table first");
		
		return "residentPage";
		
	}
	
	@RequestMapping(value = "/newResident", method = RequestMethod.GET)
	public String residents(Model model, Principal principal) throws Exception{

		// (1) (en)
		// After user login successfully.
		String serviceCoord = null;
		if (principal != null) {
			serviceCoord = populateSCinModel(model, principal);
		}
					
		Resident resident  = residentService.getResidentById(0l, serviceCoord);
				
		model.addAttribute("resident", resident);
		model.addAttribute("message", "Please select resident from All Resident Table first");
		
		return "residentPage";
		
	}
	
	@RequestMapping(value = "/allResident", method = RequestMethod.GET)
	public String getAllResidents(Model model, Principal principal) throws Exception{

		// (1) (en)
		// After user login successfully.
		
		if (principal != null) {
			populateSCinModel(model, principal);
		}
		
		return "allResident";		
	}
	
	@PostMapping("/saveResident")
    public String signup(@Valid @ModelAttribute Resident resident, BindingResult bindingResult) {      
        
		if (bindingResult.hasErrors()) {
            return "residentPage";
        }
		//This will be new ResidentId always
        Long residentId = residentService.saveResident(resident);    
        
        return "allResident";
    }	

	private String populateSCinModel(Model model, Principal principal) {

		User loggedinUser = (User) ((Authentication) principal).getPrincipal();
		String serviceCordInfo = WebUtils.toString(loggedinUser);
		model.addAttribute("serviceCordInfo", serviceCordInfo);
		return loggedinUser.getUsername();
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			populateSCinModel(model, principal);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}
		return "403Page";
	}	

}
