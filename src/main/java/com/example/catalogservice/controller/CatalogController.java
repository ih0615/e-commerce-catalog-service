package com.example.catalogservice.controller;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

  private final Environment env;
  private final CatalogService catalogService;
  private final ModelMapper modelMapper = new ModelMapper();

  @GetMapping("/heath_check")
  public String status() {
    return String.format("It's Working in Catalog Service on PORT %s",
        env.getProperty("local.server.port"));
  }

  @GetMapping("/catalogs")
  public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
    List<CatalogEntity> catalogs = catalogService.getAllCatalogs();
    return ResponseEntity.ok()
        .body(catalogs.stream()
            .map(catalogEntity -> modelMapper.map(catalogEntity, ResponseCatalog.class))
            .collect(Collectors.toList()));
  }
}
