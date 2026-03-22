package thelazycoder.blog_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.request.PreferenceRequestDto;
import thelazycoder.blog_app.dto.response.PreferenceResponseDto;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.service.UserPreferenceService;
import thelazycoder.blog_app.utils.InfoGetter;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService preferenceService;
    private final InfoGetter infoGetter;

    @GetMapping
    public ResponseEntity<PreferenceResponseDto> getPreferences(
    ) {
        String userId = extractUserId();
        return ResponseEntity.ok(preferenceService.getPreferences(userId));
    }

    @PutMapping
    public ResponseEntity<PreferenceResponseDto> savePreferences(
            @Valid @RequestBody PreferenceRequestDto dto
    ) {
        String userId = extractUserId();
        return ResponseEntity.ok(preferenceService.savePreferences(userId, dto));
    }

    private String extractUserId() {
        User loggedInUser = infoGetter.getLoggedInUser();
        return loggedInUser.getId();
    }
}
