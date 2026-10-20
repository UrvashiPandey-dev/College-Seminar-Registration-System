
package com.college.seminar.service;

import com.college.seminar.model.Registration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RegistrationBackgroundTask {

    private final RegistrationService registrationService =
            new RegistrationService();

    public CompletableFuture<List<Registration>> loadRegistrationsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return registrationService.getAllRegistrations();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Could not load registrations", e);
            }
        });
    }
}
