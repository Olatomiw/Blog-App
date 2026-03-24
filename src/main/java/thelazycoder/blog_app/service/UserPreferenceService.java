package thelazycoder.blog_app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.blog_app.dto.request.CategoryDto;
import thelazycoder.blog_app.dto.request.PreferenceRequestDto;
import thelazycoder.blog_app.dto.response.PreferenceResponseDto;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.model.Category;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.model.UserPreference;
import thelazycoder.blog_app.repository.CategoryRepository;
import thelazycoder.blog_app.repository.UserPreferenceRepository;
import thelazycoder.blog_app.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserPreferenceRepository preferenceRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @Transactional
    public PreferenceResponseDto savePreferences(String userId, PreferenceRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoEntityFoundException("User not found"));

        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(dto.categoryIds())
        );

        UserPreference preference = preferenceRepository.findByUserId(userId)
                .orElse(UserPreference.builder()
                        .id(UUID.randomUUID().toString())
                        .user(user).build());
        preference.setPreferredCategories(categories);
        UserPreference userPreference = preferenceRepository.save(preference);

        return mapToDto(userId, userPreference.getPreferredCategories());
    }

    @Transactional(readOnly = true)
    public PreferenceResponseDto getPreferences(String userId) {
        UserPreference preference = preferenceRepository.findByUserId(userId)
                .orElse(null);

        Set<Category> categories = preference != null
                ? preference.getPreferredCategories()
                : Set.of();

        return mapToDto(userId, categories);
    }


    @Transactional
    public void deletePreferences(String userId) {
        preferenceRepository.findByUserId(userId)
                .ifPresent(preferenceRepository::delete);
    }

    private PreferenceResponseDto mapToDto(String userId, Set<Category> categories) {
        Set<CategoryDto> categoryDtos = categories.stream()
                .map(c -> new CategoryDto(c.getName()))
                .collect(Collectors.toSet());
        return new PreferenceResponseDto(userId, categoryDtos);
    }
}
