package com.ind.service;

import com.ind.binding.EligDeterForm;
import com.ind.entity.EligibilityDeterminationTable;

public interface EligibilityDeterminationService {

	public EligibilityDeterminationTable checkEligibility(EligDeterForm data);

}
