package roomescape.theme.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.exception.ConflictException;
import roomescape.exception.ForbiddenException;
import roomescape.exception.NotFoundException;
import roomescape.theme.dto.*;
import roomescape.theme.model.Theme;
import roomescape.theme.repository.ThemeRepository;
import roomescape.exception.ErrorCode;
import roomescape.user.model.Role;
import roomescape.user.model.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public ThemesResponse findAll() {
        List<Theme> themes = themeRepository.findAll();
        return ThemesResponse.from(themes);
    }

    public ThemesResponse findAllByStoreId(Long storeId) {
        List<Theme> themes = themeRepository.findAllByStoreId(storeId);
        return ThemesResponse.from(themes);
    }

    public ThemesResponse findAllByUser(User user) {
        if (user.getRole() == Role.MANAGER) {
            return findAllByStoreId(user.getStoreId());
        }
        return findAll();
    }

    @Transactional
    public Long create(ThemeRequest request, User user) {
        Long storeId = user.getRole() == Role.MANAGER ? user.getStoreId() : null;
        Theme theme = new Theme(null, storeId, request.name(), request.description(), request.imageUrl(), request.requiredTime());
        return themeRepository.create(theme);
    }

    @Transactional
    public void delete(Long id, User user) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.THEME_NOT_FOUND));

        if (user.getRole() == Role.MANAGER && !user.getStoreId().equals(theme.getStoreId())) {
            throw new ForbiddenException(ErrorCode.INSUFFICIENT_PERMISSIONS);
        }

        try {
            themeRepository.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(ErrorCode.THEME_IN_USE);
        }
    }

    public PopularThemesResponse findPopularThemes(String sort, int limit, int days) {
        List<PopularThemeResponse> responses = themeRepository.findPopularThemes(sort, limit, days);
        return PopularThemesResponse.from(responses);
    }

    public Theme findById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.THEME_NOT_FOUND));
    }
}
