package com.example.priceanalyzer.controller;

import com.example.priceanalyzer.services.SyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class Sync {
    private final SyncService syncService;

    public Sync(SyncService syncService) {
        this.syncService = syncService;
    }

    @GetMapping
    public void sync() throws Exception {
        syncService.sync();
    }

    @GetMapping("/clear")
    public void clear() {
        syncService.clear();
    }
}
