package com.ffg.rrn;

import com.ffg.rrn.model.Demographics;
import com.ffg.rrn.report.filter.AssessmentReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RRNHope {

	public static void main(String[] args) {
		SpringApplication.run(RRNHope.class, args);

		List<Demographics> testDemographicData = new ArrayList<>();
		AssessmentReport assessmentReport = new AssessmentReport();
		testDemographicData = assessmentReport.filterDemographicsDataByPropertyId((long) 1);
		System.out.println(testDemographicData);


	}



}

