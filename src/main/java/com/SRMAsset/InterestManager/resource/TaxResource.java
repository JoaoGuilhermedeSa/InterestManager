package com.SRMAsset.InterestManager.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SRMAsset.InterestManager.helper.InterestTax;

@CrossOrigin(origins = "http://127.0.0.1:8080")
@RestController
@RequestMapping("api/tax")
public class TaxResource {

	@GetMapping
	public ResponseEntity<?> findValues() {
		List<String> options = new ArrayList<String>();
		for (InterestTax tax : InterestTax.values()) {
			options.add(tax.name());
		}
		return ResponseEntity.ok(options);
	}

}
