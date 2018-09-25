package com.SRMAsset.InterestManager.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("prod")
@RestController
public class DisableSwaggerUiController {

	@GetMapping("swagger-ui.html")
	public ResponseEntity<?> getSwagger(HttpServletResponse httpResponse) throws IOException {
		return ResponseEntity.notFound().build();
	}
}